package com.fantasy.interfaces

interface Defender {
    val defensePower: Int
    val stamina: Int

    fun defend(attackPower: Int): Int
}

interface Healer {
    val healingPower: Int
    val mana: Int

    fun heal()
}
