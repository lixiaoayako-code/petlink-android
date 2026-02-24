package com.petlink.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.petlink.data.model.*
import com.petlink.data.repo.InMemoryRepo

@Composable
fun AddPetScreen(repo: InMemoryRepo, nav: NavHostController) {
    var name by remember { mutableStateOf("") }
    var breed by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("10.0") }
    var age by remember { mutableStateOf("18") }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("新增宠物", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(12.dp))

        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("昵称") })
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = breed, onValueChange = { breed = it }, label = { Text("品种（如：柴犬）") })
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = weight, onValueChange = { weight = it }, label = { Text("体重kg") })
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(value = age, onValueChange = { age = it }, label = { Text("月龄") })

        Spacer(Modifier.height(16.dp))
        Button(
            enabled = name.isNotBlank() && breed.isNotBlank(),
            onClick = {
                repo.addPet(
                    Pet(
                        name = name.trim(),
                        breed = breed.trim(),
                        sex = Sex.MALE,
                        ageMonths = age.toIntOrNull() ?: 12,
                        weightKg = weight.toDoubleOrNull() ?: 10.0,
                        size = PetSize.MEDIUM
                    )
                )
                nav.popBackStack()
            }
        ) { Text("保存") }
    }
}
