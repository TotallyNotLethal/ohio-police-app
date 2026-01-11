package com.ohiopolice.app.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ohiopolice.app.OhioPoliceApp
import com.ohiopolice.app.network.OpenAiClient
import com.ohiopolice.app.viewmodel.AiChatViewModel
import com.ohiopolice.app.viewmodel.FavoritesViewModel
import com.ohiopolice.app.viewmodel.OrcViewModel
import com.ohiopolice.app.viewmodel.PinViewModel

class PinViewModelFactory(private val app: OhioPoliceApp) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PinViewModel(app.pinRepository) as T
    }
}

class OrcViewModelFactory(private val app: OhioPoliceApp) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return OrcViewModel(app.orcRepository, app.database.favoritesDao()) as T
    }
}

class FavoritesViewModelFactory(private val app: OhioPoliceApp) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoritesViewModel(app.database.favoritesDao()) as T
    }
}

class AiChatViewModelFactory(
    private val app: OhioPoliceApp,
    private val context: Context,
    private val apiKey: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AiChatViewModel(
            context,
            app.database.aiChatDao(),
            OpenAiClient(apiKey)
        ) as T
    }
}
