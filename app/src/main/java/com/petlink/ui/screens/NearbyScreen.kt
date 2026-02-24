package com.petlink.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.amap.api.maps.model.LatLng
import com.petlink.data.repo.InMemoryRepo

@Composable
fun NearbyScreen(repo: InMemoryRepo, nav: NavHostController) {
    val places by repo.places.collectAsState()
    val first = places.firstOrNull()
    val center = if (first != null) LatLng(first.lat, first.lng) else LatLng(39.9, 116.4)

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("附近（宠物优先）", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        Text("展示：附近正在遛狗的宠物/同路线重合/同小区推荐（当前为业务占位 + 高德地图演示）")

        Spacer(Modifier.height(12.dp))
        Row {
            Button(onClick = { nav.navigate("pets") }) { Text("管理我的宠物") }
            Spacer(Modifier.width(8.dp))
            OutlinedButton(onClick = { nav.navigate("map_select") }) { Text("地图选点") }
        }

        Spacer(Modifier.height(12.dp))
        Card(Modifier.fillMaxWidth().height(220.dp)) {
            AMapView(
                modifier = Modifier.fillMaxSize(),
                center = center,
                zoom = 14f,
                markerTitle = first?.name ?: "中心点"
            )
        }

        Spacer(Modifier.height(12.dp))
        Card(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(12.dp)) {
                Text("推荐：豆豆（柴犬）", style = MaterialTheme.typography.titleMedium)
                Text("理由：同小区 + 同时间段（示例）")
                Text("操作：关注/打招呼/约一起遛狗（后续加）")
            }
        }
    }
}
