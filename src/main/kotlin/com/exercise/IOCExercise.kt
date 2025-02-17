package com.exercise

import com.fantasy.models.*
import com.fantasy.interfaces.*
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {  }

internal data class CharacterMatchingStrategy(
    val name: String?,
    val level: CharacterLevel?,
    val characterClass: String?
) {
    companion object {
        val ANY = CharacterMatchingStrategy(null, null, null)
    }
}

internal object CharacterRepository {

    private val harryPotterCharacters = listOf(
        Sorcerer(name = "Harry Potter", health = 100, attackPower = 40, mana = 30, healingPower = 30, level = CharacterLevel.LEVEL_1), // Balanced, more durable
        Sorcerer(name = "Hermione Granger", health = 90, attackPower = 40, mana = 40, healingPower = 30, level = CharacterLevel.LEVEL_1), // High mana for tactical advantage
        Sorcerer(name = "Ron Weasley", health = 120, attackPower = 50, mana = 20, healingPower = 10, level = CharacterLevel.LEVEL_1), // Higher health for tanking
        Sorcerer(name = "Severus Snape", health = 80, attackPower = 60, mana = 30, healingPower = 30, level = CharacterLevel.LEVEL_1), // Strong attack, slightly higher health
        Sorcerer(name = "Albus Dumbledore", health = 90, attackPower = 40, mana = 40, healingPower = 30, level = CharacterLevel.LEVEL_1), // Well-rounded, high healing power
        Sorcerer(name = "Lord Voldemort", health = 80, attackPower = 80, mana = 10, healingPower = 30, level = CharacterLevel.LEVEL_1), // Powerful, but needs more survival
        Sorcerer(name = "Minerva McGonagall", health = 100, attackPower = 40, mana = 30, healingPower = 30, level = CharacterLevel.LEVEL_1), // Balanced
        Sorcerer(name = "Bellatrix Lestrange", health = 80, attackPower = 70, mana = 20, healingPower = 30, level = CharacterLevel.LEVEL_1), // Aggressive, but not overpowered
        Sorcerer(name = "Draco Malfoy", health = 100, attackPower = 40, mana = 30, healingPower = 30, level = CharacterLevel.LEVEL_1), // Slightly more balanced
        Sorcerer(name = "Neville Longbottom", health = 130, attackPower = 30, mana = 10, healingPower = 30, level = CharacterLevel.LEVEL_1), // Tanky with healing ability
    )

    private val starWarsCharacters = listOf(
        Warrior(name = "Luke Skywalker", health = 110, attackPower = 40, stamina = 20, defensePower = 30, level = CharacterLevel.LEVEL_1), // Balanced, well-rounded
        Warrior(name = "Yoda", health = 80, attackPower = 30, stamina = 50, defensePower = 40, level = CharacterLevel.LEVEL_1), // High mana, strategic healer
        Warrior(name = "Han Solo", health = 120, attackPower = 40, stamina = 10, defensePower = 30, level = CharacterLevel.LEVEL_1), // Tanky and reliable
        Warrior(name = "Darth Vader", health = 100, attackPower = 60, stamina = 10, defensePower = 30, level = CharacterLevel.LEVEL_1), // High health and power, needs healing
        Warrior(name = "Obi-Wan Kenobi", health = 100, attackPower = 40, stamina = 30, defensePower = 30, level = CharacterLevel.LEVEL_1), // Strong defense and healing
        Warrior(name = "Emperor Palpatine", health = 80, attackPower = 80, stamina = 10, defensePower = 30, level = CharacterLevel.LEVEL_1), // Strong attack, low healing
        Warrior(name = "Mace Windu", health = 110, attackPower = 40, stamina = 20, defensePower = 30, level = CharacterLevel.LEVEL_1), // Well-rounded with decent attack
        Warrior(name = "Darth Maul", health = 90, attackPower = 60, stamina = 20, defensePower = 30, level = CharacterLevel.LEVEL_1), // Fast and aggressive, needs support
        Warrior(name = "Kylo Ren", health = 100, attackPower = 50, stamina = 20, defensePower = 30, level = CharacterLevel.LEVEL_1), // Balanced with raw power
        Warrior(name = "Finn", health = 130, attackPower = 20, stamina = 10, defensePower = 40, level = CharacterLevel.LEVEL_1), // High health and resilience, strategic healing
    )

    fun getCharacters() = harryPotterCharacters + starWarsCharacters
}

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
        val winner: Character? = when {
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

fun main() {
    val matchService = RandomMatchService(
        service = RandomCharacter()
    )

    matchService.match(rounds=20).getResult()
}