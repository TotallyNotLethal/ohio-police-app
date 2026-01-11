package com.ohiopolice.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ohiopolice.app.data.FavoriteStatute
import com.ohiopolice.app.data.FavoritesDao
import com.ohiopolice.app.data.OrcRepository
import com.ohiopolice.app.data.OrcStatute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class OrcViewModel(
    private val repository: OrcRepository,
    private val favoritesDao: FavoritesDao
) : ViewModel() {
    private val _query = MutableStateFlow("")
    private val _statutes = MutableStateFlow<List<OrcStatute>>(emptyList())

    val query: StateFlow<String> = _query.asStateFlow()
    val statutes: StateFlow<List<OrcStatute>> = _statutes.asStateFlow()

    val results: StateFlow<List<OrcStatute>> = combine(_query, _statutes) { query, statutes ->
        if (query.isBlank()) {
            statutes
        } else {
            statutes.filter {
                it.title.contains(query, true) ||
                    it.chapter.contains(query, true) ||
                    it.section.contains(query, true) ||
                    it.heading.contains(query, true) ||
                    it.body.contains(query, true)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        viewModelScope.launch {
            _statutes.value = repository.loadOrcStatutes()
        }
    }

    fun updateQuery(value: String) {
        _query.value = value
    }

    suspend fun isFavorite(section: String): Boolean {
        return favoritesDao.getFavorite(section) != null
    }

    fun toggleFavorite(statute: OrcStatute, isFavorite: Boolean) {
        viewModelScope.launch {
            if (isFavorite) {
                favoritesDao.removeFavorite(
                    FavoriteStatute(
                        section = statute.section,
                        title = statute.title,
                        chapter = statute.chapter,
                        heading = statute.heading,
                        body = statute.body
                    )
                )
            } else {
                favoritesDao.upsertFavorite(
                    FavoriteStatute(
                        section = statute.section,
                        title = statute.title,
                        chapter = statute.chapter,
                        heading = statute.heading,
                        body = statute.body
                    )
                )
            }
        }
    }
}
