package com.fantasy.models

import kotlinx.serialization.*

@Serializable
enum class CharacterLevel(val points: Int) {
    LEVEL_1(5),
    LEVEL_2(10),
    LEVEL_3(15),
    LEVEL_4(20),
    LEVEL_5(30),
    LEVEL_6(40),
    LEVEL_7(50),
    LEVEL_8(65),
    LEVEL_9(80),
    LEVEL_10(100),
}