package com.petlink.data.repo

import com.petlink.data.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.Instant

class InMemoryRepo {
    private val _pets = MutableStateFlow(listOf(
        Pet(name="豆豆", breed="柴犬", sex=Sex.MALE, ageMonths=18, weightKg=10.5, size=PetSize.MEDIUM),
        Pet(name="Momo", breed="边牧", sex=Sex.FEMALE, ageMonths=26, weightKg=16.2, size=PetSize.MEDIUM),
    ))
    val pets: StateFlow<List<Pet>> = _pets

    private val _places = MutableStateFlow(listOf(
        Place(name="朝阳公园", city="北京", lat=39.957, lng=116.478),
        Place(name="世纪公园", city="上海", lat=31.215, lng=121.557),
        Place(name="人民公园", city="广州", lat=23.130, lng=113.264),
    ))
    val places: StateFlow<List<Place>> = _places

    private val _walks = MutableStateFlow<List<WalkSession>>(emptyList())
    val walks: StateFlow<List<WalkSession>> = _walks

    fun addPet(pet: Pet) { _pets.value = _pets.value + pet }

    fun startWalk(petId: String, placeId: String?): WalkSession {
        val ws = WalkSession(petId = petId, startAt = Instant.now(), placeId = placeId)
        _walks.value = _walks.value + ws
        return ws
    }

    fun updateWalk(updated: WalkSession) {
        _walks.value = _walks.value.map { if (it.id == updated.id) updated else it }
    }

    fun endWalk(walkId: String): WalkSession? {
        val w = _walks.value.find { it.id == walkId } ?: return null
        val ended = w.copy(endAt = Instant.now())
        updateWalk(ended)
        return ended
    }

    fun addPlace(place: Place) {
        _places.value = _places.value + place
    }
}
