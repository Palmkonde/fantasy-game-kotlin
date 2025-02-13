package com.fantasy.models

import kotlinx.serialization.*

@Serializable
data class WarriorData(
    val name: String,
    val health: Int,
    val attackPower: Int,
    val stamina: Int,
    val defensePower: Int,
    val level: CharacterLevel
)

@Serializable
data class SorcererData(
    val name: String,
    val health: Int,
    val attackPower: Int,
    val mana: Int,
    val healingPower: Int,
    val level: CharacterLevel
)

@Serializable
data class CharacterDataWrapper(
    val type: String,
    val name: String,
    val health: Int,
    val attackPower: Int,
    val stamina: Int? = null, // Only for Warrior
    val defensePower: Int? = null, // Only for Warrior
    val mana: Int? = null, // Only for Sorcerer
    val healingPower: Int? = null, // Only for Sorcerer
    val level: CharacterLevel
)