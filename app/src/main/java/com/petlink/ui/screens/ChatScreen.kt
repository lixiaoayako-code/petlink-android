package com.petlink.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ChatScreen(chatId: String) {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("聊天 #$chatId（占位）", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        Text("后续接入：消息拉取/发送、图片语音、举报拉黑等")
    }
}
