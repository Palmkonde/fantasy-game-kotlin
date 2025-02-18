package com.exercise

import com.fantasy.interfaces.*
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {  }

internal class MatchResult(
    private val challenger: Character,
    private val opponent: Character,
    private val rounds: Int,
    private val winner: Character?
) {
    fun getResult() {
        logger.info {"Challenger: ${challenger.name} vs Opponent: ${opponent.name}"}

        if(winner == null){
            logger.info {"\n\nDRAW!!"}
            return
        }

        logger.info{"\n\nWinner: ${winner.name} health left: ${winner.health}"}
        logger.info{"Used round: $rounds"}
    }
}
