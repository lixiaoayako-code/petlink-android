package com.petlink

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.petlink.ui.screens.AMapPrivacy
import com.petlink.ui.theme.PetLinkTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // 高德隐私合规：必须在使用任何地图/定位 API 之前调用
        AMapPrivacy.init(applicationContext)
        super.onCreate(savedInstanceState)

        setContent {
            PetLinkTheme {
                PetLinkApp()
            }
        }
    }
}
