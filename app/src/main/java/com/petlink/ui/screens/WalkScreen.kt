package com.petlink.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.petlink.data.repo.InMemoryRepo
import com.petlink.domain.Calories
import java.time.Duration
import java.time.Instant
import kotlin.random.Random

@Composable
fun WalkScreen(repo: InMemoryRepo) {
    val pets by repo.pets.collectAsState()
    val places by repo.places.collectAsState()
    val walks by repo.walks.collectAsState()

    var selectedPetId by remember { mutableStateOf(pets.firstOrNull()?.id ?: "") }
    var selectedPlaceId by remember { mutableStateOf(places.firstOrNull()?.id) }
    var activeWalkId by remember { mutableStateOf<String?>(null) }

    val activeWalk = walks.firstOrNull { it.id == activeWalkId }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("遛狗", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(12.dp))

        if (pets.isEmpty()) {
            Text("请先新增宠物（附近 → 管理我的宠物）")
            return
        }

        Text("选择宠物")
        Dropdown(selected = pets.first { it.id == selectedPetId }.name) {
            pets.forEach { p -> item(p.name) { selectedPetId = p.id } }
        }

        Spacer(Modifier.height(8.dp))
        Text("选择地点（用于排行榜归属）")
        Dropdown(selected = places.firstOrNull { it.id == selectedPlaceId }?.name ?: "不选择") {
            item("不选择") { selectedPlaceId = null }
            places.forEach { pl -> item("${pl.city}·${pl.name}") { selectedPlaceId = pl.id } }
        }

        Spacer(Modifier.height(16.dp))

        if (activeWalk == null) {
            Button(onClick = {
                val ws = repo.startWalk(selectedPetId, selectedPlaceId)
                activeWalkId = ws.id
            }) { Text("开始遛狗") }
        } else {
            val seconds = Duration.between(activeWalk.startAt, Instant.now()).seconds
            Text("进行中：已 ${seconds}s")
            Spacer(Modifier.height(8.dp))

            Row {
                Button(onClick = {
                    // MVP：用随机距离模拟（后续替换为真实定位轨迹累计）
                    val pet = pets.first { it.id == selectedPetId }
                    val distanceKm = (0.3 + Random.nextDouble() * 2.5)
                    val intensity = 0.9 + Random.nextDouble() * 0.4
                    val kcal = Calories.calc(distanceKm, pet.weightKg, pet.breed, intensity)
                    repo.updateWalk(activeWalk.copy(distanceKm = distanceKm, calories = kcal))
                }) { Text("刷新模拟数据") }

                Spacer(Modifier.width(8.dp))
                Button(onClick = {
                    repo.endWalk(activeWalk.id)
                    activeWalkId = null
                }) { Text("结束") }
            }

            Spacer(Modifier.height(12.dp))
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(12.dp)) {
                    Text("本次数据（MVP模拟）", style = MaterialTheme.typography.titleMedium)
                    Text("距离：${"%.2f".format(activeWalk.distanceKm)} km")
                    Text("卡路里：${"%.0f".format(activeWalk.calories)} kcal")
                }
            }
        }
    }
}

@Composable
private fun Dropdown(selected: String, content: @Composable DropdownScope.() -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        OutlinedButton(onClick = { expanded = true }) { Text(selected) }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownScopeImpl(this, onSelect = { expanded = false }).content()
        }
    }
}

private class DropdownScopeImpl(
    private val menu: DropdownMenuScope,
    private val onSelect: () -> Unit
) : DropdownScope {
    override fun item(text: String, onClick: () -> Unit) {
        menu.DropdownMenuItem(
            text = { Text(text) },
            onClick = { onClick(); onSelect() }
        )
    }
}

private interface DropdownScope {
    fun item(text: String, onClick: () -> Unit)
}
