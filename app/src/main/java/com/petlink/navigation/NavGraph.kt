package com.petlink.navigation

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.petlink.data.repo.InMemoryRepo
import com.petlink.ui.screens.*

sealed class Tab(val route: String, val label: String) {
    data object Nearby : Tab("nearby", "附近")
    data object Walk : Tab("walk", "遛狗")
    data object Leaderboard : Tab("leaderboard", "排行")
    data object Chats : Tab("chats", "消息")
    data object Profile : Tab("profile", "我的")
}

@Composable
fun NavGraph(repo: InMemoryRepo) {
    val navController = rememberNavController()
    val tabs = listOf(Tab.Nearby, Tab.Walk, Tab.Leaderboard, Tab.Chats, Tab.Profile)

    Scaffold(
        bottomBar = {
            NavigationBar {
                val current = currentRoute(navController)
                tabs.forEach { tab ->
                    NavigationBarItem(
                        selected = current == tab.route,
                        onClick = { navController.navigate(tab.route) { launchSingleTop = true } },
                        label = { Text(tab.label) },
                        icon = {}
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Tab.Nearby.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(Tab.Nearby.route) { NearbyScreen(repo, navController) }
            composable("map_select") { MapSelectScreen(repo, navController) }

            composable("pets") { PetsScreen(repo, navController) }
            composable("pets/add") { AddPetScreen(repo, navController) }

            composable(Tab.Walk.route) { WalkScreen(repo) }
            composable(Tab.Leaderboard.route) { LeaderboardScreen(repo) }
            composable(Tab.Chats.route) { ChatListScreen(navController) }
            composable("chat/{id}") { backStack ->
                ChatScreen(chatId = backStack.arguments?.getString("id") ?: "0")
            }
            composable(Tab.Profile.route) { ProfileScreen(navController) }
        }
    }
}

@Composable
private fun currentRoute(navController: NavHostController): String? {
    val entry by navController.currentBackStackEntryAsState()
    return entry?.destination?.route
}
