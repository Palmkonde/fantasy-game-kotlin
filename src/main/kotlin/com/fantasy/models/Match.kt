package com.fantasy.models

import com.fantasy.interfaces.Character
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {  }

class Match(
    private val rounds: Int,
    private val challenger: Character,
    private val opponent: Character
) {
     fun fight(): Character? {
        var round = 1
        while( challenger.getHealth() > 0 && opponent.getHealth() > 0 && round <= rounds) {
            println("\nROUND $round:")
            logger.info { "\nROUND $round:" }

            challenger.beforeRounds()
            opponent.beforeRounds()

            challenger.attack(opponent)
            opponent.attack(challenger)

            challenger.afterRound()
            opponent.afterRound()

            round++
        }
        when {
            challenger.getHealth() <= 0 && opponent.getHealth() > 0 -> return opponent
            opponent.getHealth() <= 0 && challenger.getHealth() > 0 -> return challenger
            else -> return null
        }
    }
}