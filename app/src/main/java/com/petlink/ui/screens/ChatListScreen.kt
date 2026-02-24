package com.petlink.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun ChatListScreen(nav: NavHostController) {
    val mock = listOf("豆豆(柴犬)", "Momo(边牧)")
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("消息", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(12.dp))
        LazyColumn {
            items(mock.indices.toList()) { i ->
                Card(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable { nav.navigate("chat/$i") }
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Text(mock[i], style = MaterialTheme.typography.titleMedium)
                        Text("最后一条：嗨，今天几点遛？（示例）")
                    }
                }
            }
        }
    }
}
