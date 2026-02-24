package com.petlink.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.petlink.data.repo.InMemoryRepo

@Composable
fun PetsScreen(repo: InMemoryRepo, nav: NavHostController) {
    val pets by repo.pets.collectAsState()
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("我的宠物", style = MaterialTheme.typography.titleLarge)
            Button(onClick = { nav.navigate("pets/add") }) { Text("新增") }
        }
        Spacer(Modifier.height(12.dp))
        LazyColumn {
            items(pets) { pet ->
                Card(Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
                    Column(Modifier.padding(12.dp)) {
                        Text("${pet.name} · ${pet.breed}", style = MaterialTheme.typography.titleMedium)
                        Text("体重 ${pet.weightKg}kg · ${pet.ageMonths}月龄 · ${pet.size}")
                    }
                }
            }
        }
    }
}
