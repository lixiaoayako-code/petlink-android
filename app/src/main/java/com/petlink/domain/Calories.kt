package com.petlink.domain

import kotlin.math.max
import kotlin.math.min

object Calories {
    private val breedFactor = mapOf(
        "边牧" to 1.3,
        "哈士奇" to 1.2,
        "金毛" to 1.0,
        "拉布拉多" to 1.0,
        "柴犬" to 0.9,
        "法斗" to 0.6
    )

    fun calc(distanceKm: Double, weightKg: Double, breed: String, intensity: Double): Double {
        val bf = breedFactor[breed] ?: 1.0
        val safeIntensity = min(1.3, max(0.7, intensity))
        return distanceKm * weightKg * bf * safeIntensity * 10.0
    }
}
