package com.petlink.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.petlink.data.model.LeaderboardEntry
import com.petlink.data.repo.InMemoryRepo
import kotlin.math.roundToInt

@Composable
fun LeaderboardScreen(repo: InMemoryRepo) {
    val pets by repo.pets.collectAsState()
    val places by repo.places.collectAsState()
    val walks by repo.walks.collectAsState()

    var selectedPlaceId by remember { mutableStateOf(places.firstOrNull()?.id) }

    val entries = remember(walks, pets, selectedPlaceId) {
        val petMap = pets.associateBy { it.id }
        val filtered = walks.filter { it.placeId == selectedPlaceId && it.calories > 0 }
        val sumByPet = filtered.groupBy { it.petId }.mapValues { (_, ws) -> ws.sumOf { it.calories } }
        sumByPet.entries
            .sortedByDescending { it.value }
            .take(20)
            .mapIndexed { idx, e ->
                val p = petMap[e.key]
                LeaderboardEntry(
                    rank = idx + 1,
                    petName = p?.name ?: "未知",
                    breed = p?.breed ?: "-",
                    calories = e.value
                )
            }
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("地点排行榜", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))

        if (places.isEmpty()) {
            Text("暂无地点：去“附近-地图选点”添加一个")
            return
        }

        Dropdown(selected = places.firstOrNull { it.id == selectedPlaceId }?.name ?: "选择地点") {
            places.forEach { pl -> item("${pl.city}·${pl.name}") { selectedPlaceId = pl.id } }
        }

        Spacer(Modifier.height(12.dp))
        if (entries.isEmpty()) {
            Text("暂无数据：去“遛狗”开始一段并结束，榜单会自动出现。")
        } else {
            LazyColumn {
                items(entries) { e ->
                    Card(Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
                        Row(Modifier.padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                            Column {
                                Text("No.${e.rank}  ${e.petName}", style = MaterialTheme.typography.titleMedium)
                                Text(e.breed)
                            }
                            Text("${e.calories.roundToInt()} kcal")
                        }
                    }
                }
            }
        }
    }
}
