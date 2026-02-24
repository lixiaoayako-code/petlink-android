package com.petlink

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.petlink.data.repo.InMemoryRepo
import com.petlink.navigation.NavGraph

@Composable
fun PetLinkApp() {
    val repo = remember { InMemoryRepo() }
    NavGraph(repo)
}
