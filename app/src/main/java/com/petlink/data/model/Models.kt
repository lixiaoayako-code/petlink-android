package com.petlink.data.model

import java.time.Instant
import java.util.UUID

enum class PetSize { SMALL, MEDIUM, LARGE }
enum class Sex { MALE, FEMALE }

data class Pet(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val breed: String,
    val sex: Sex,
    val ageMonths: Int,
    val weightKg: Double,
    val size: PetSize,
)

data class Place(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val city: String,
    val lat: Double,
    val lng: Double,
)

data class WalkSession(
    val id: String = UUID.randomUUID().toString(),
    val petId: String,
    val startAt: Instant,
    val endAt: Instant? = null,
    val distanceKm: Double = 0.0,
    val calories: Double = 0.0,
    val placeId: String? = null,
)

data class LeaderboardEntry(
    val rank: Int,
    val petName: String,
    val breed: String,
    val calories: Double
)
