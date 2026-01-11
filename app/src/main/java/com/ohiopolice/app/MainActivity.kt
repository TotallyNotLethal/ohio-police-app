package com.ohiopolice.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ohiopolice.app.ui.AiChatScreen
import com.ohiopolice.app.ui.DashboardScreen
import com.ohiopolice.app.ui.FavoritesScreen
import com.ohiopolice.app.ui.OhioPoliceTheme
import com.ohiopolice.app.ui.OrcDetailScreen
import com.ohiopolice.app.ui.OrcSearchScreen
import com.ohiopolice.app.ui.PinLoginScreen
import com.ohiopolice.app.ui.AiChatViewModelFactory
import com.ohiopolice.app.ui.FavoritesViewModelFactory
import com.ohiopolice.app.ui.OrcViewModelFactory
import com.ohiopolice.app.ui.PinViewModelFactory
import com.ohiopolice.app.viewmodel.AiChatViewModel
import com.ohiopolice.app.viewmodel.FavoritesViewModel
import com.ohiopolice.app.viewmodel.OrcViewModel
import com.ohiopolice.app.viewmodel.PinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val app = application as OhioPoliceApp
        setContent {
            OhioPoliceTheme(darkTheme = isSystemInDarkTheme()) {
                OhioPoliceNavGraph(app)
            }
        }
    }
}

@Composable
fun OhioPoliceNavGraph(app: OhioPoliceApp) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val pinViewModel: PinViewModel = viewModel(factory = PinViewModelFactory(app))
    val orcViewModel: OrcViewModel = viewModel(factory = OrcViewModelFactory(app))
    val favoritesViewModel: FavoritesViewModel = viewModel(factory = FavoritesViewModelFactory(app))
    val aiChatViewModel: AiChatViewModel = viewModel(
        factory = AiChatViewModelFactory(
            app,
            context,
            apiKey = BuildConfig.OPENAI_API_KEY
        )
    )

    NavHost(navController = navController, startDestination = "pin") {
        composable("pin") {
            PinLoginScreen(pinViewModel = pinViewModel) {
                navController.navigate("dashboard")
            }
        }
        composable("dashboard") {
            DashboardScreen(
                onSearch = { navController.navigate("orc_search") },
                onFavorites = { navController.navigate("favorites") },
                onAi = { navController.navigate("ai") }
            )
        }
        composable("orc_search") {
            OrcSearchScreen(
                orcViewModel = orcViewModel,
                onSelect = { section -> navController.navigate("orc_detail/$section") },
                onBack = { navController.popBackStack() }
            )
        }
        composable(
            "orc_detail/{section}",
            arguments = listOf(navArgument("section") { type = NavType.StringType })
        ) { backStackEntry ->
            val section = backStackEntry.arguments?.getString("section").orEmpty()
            OrcDetailScreen(
                section = section,
                orcViewModel = orcViewModel,
                onBack = { navController.popBackStack() }
            )
        }
        composable("favorites") {
            FavoritesScreen(
                favoritesViewModel = favoritesViewModel,
                onSelect = { section -> navController.navigate("orc_detail/$section") },
                onBack = { navController.popBackStack() }
            )
        }
        composable("ai") {
            AiChatScreen(
                aiChatViewModel = aiChatViewModel,
                orcViewModel = orcViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
