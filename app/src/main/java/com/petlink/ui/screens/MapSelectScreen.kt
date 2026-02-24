package com.petlink.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.amap.api.maps.model.LatLng
import com.petlink.data.model.Place
import com.petlink.data.repo.InMemoryRepo

/**
 * 地图选点（业务讨论版）：
 * - 用户在地图上点击一个位置
 * - 输入地点名称
 * - 保存为“地点”（用于排行榜/打卡归属）
 */
@Composable
fun MapSelectScreen(repo: InMemoryRepo, nav: NavHostController) {
    var picked by remember { mutableStateOf(LatLng(39.9, 116.4)) }
    var name by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("地图选点", style = MaterialTheme.typography.titleLarge)
            TextButton(onClick = { nav.popBackStack() }) { Text("返回") }
        }
        Spacer(Modifier.height(8.dp))

        Card(Modifier.fillMaxWidth().height(320.dp)) {
            AMapView(
                modifier = Modifier.fillMaxSize(),
                center = picked,
                zoom = 15f,
                markerTitle = "选中点",
                onMapReady = { map ->
                    map.setOnMapClickListener { ll ->
                        picked = LatLng(ll.latitude, ll.longitude)
                    }
                }
            )
        }

        Spacer(Modifier.height(12.dp))
        OutlinedTextField(value = city, onValueChange = { city = it }, label = { Text("城市（如：北京）") })
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("地点名称（如：XX公园）") })

        Spacer(Modifier.height(12.dp))
        Text("当前坐标：${"%.6f".format(picked.latitude)}, ${"%.6f".format(picked.longitude)}")

        Spacer(Modifier.height(16.dp))
        Button(
            enabled = name.isNotBlank() && city.isNotBlank(),
            onClick = {
                repo.addPlace(
                    Place(name = name.trim(), city = city.trim(), lat = picked.latitude, lng = picked.longitude)
                )
                nav.popBackStack()
            }
        ) { Text("保存为地点") }
    }
}
