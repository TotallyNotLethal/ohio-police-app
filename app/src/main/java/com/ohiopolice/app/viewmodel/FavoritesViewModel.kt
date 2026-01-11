package com.ohiopolice.app.viewmodel

import androidx.lifecycle.ViewModel
import com.ohiopolice.app.data.FavoriteStatute
import com.ohiopolice.app.data.FavoritesDao
import kotlinx.coroutines.flow.Flow

class FavoritesViewModel(private val favoritesDao: FavoritesDao) : ViewModel() {
    val favorites: Flow<List<FavoriteStatute>> = favoritesDao.observeFavorites()
}
