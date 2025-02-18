package com.exercise

import com.fantasy.interfaces.Character
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {  }

internal interface CharacterService {
    fun findChallenger(strategy: CharacterMatchingStrategy? = null): Character
    fun findOpponent(challenger: Character, strategy: CharacterMatchingStrategy? = null): Character
}

internal interface MatchService {
    fun match(rounds: Int, matchingStrategy: CharacterMatchingStrategy? = null): MatchResult
}

internal class RandomCharacter: CharacterService {
    override fun findChallenger(strategy: CharacterMatchingStrategy?): Character {
        val challenger = CharacterRepository.getCharacters().random()
        return challenger
    }

    override fun findOpponent(challenger: Character, strategy: CharacterMatchingStrategy?): Character {
        val opponent = CharacterRepository.getCharacters().filterNot { it == challenger }.random()
        return opponent
    }
}

internal class RandomMatchService(
    private val service: CharacterService
): MatchService {
    override fun match(rounds: Int, matchingStrategy: CharacterMatchingStrategy?): MatchResult {
        val challenger = service.findChallenger()
        val opponent = service.findOpponent(challenger)

        var round = 1

        while( challenger.getCurrentHeath() > 0 && opponent.getCurrentHeath() > 0 && round <= rounds) {
            logger.info{"\nROUND $round:"}

            challenger.beforeRounds()
            opponent.beforeRounds()

            challenger.attack(opponent)
            opponent.attack(challenger)

            round++
        }
        val winner: com.fantasy.interfaces.Character? = when {
            challenger.getCurrentHeath() <= 0 && opponent.getCurrentHeath() > 0 -> opponent
            opponent.getCurrentHeath() <= 0 && challenger.getCurrentHeath() > 0 -> challenger
            else -> null
        }

        return MatchResult(
            challenger,
            opponent,
            round,
            winner
        )
    }
}
