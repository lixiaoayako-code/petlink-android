package com.petlink.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun ProfileScreen(nav: NavHostController) {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("我的", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(12.dp))
        Text("账号设置、隐私（是否可被发现/遛狗中可见）、我的小区、黑名单、客服等")
        Spacer(Modifier.height(16.dp))
        Button(onClick = { nav.navigate("pets") }) { Text("我的宠物") }
    }
}
