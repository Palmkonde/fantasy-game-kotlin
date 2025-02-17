package com.fantasy.models

import com.fantasy.interfaces.Character
import com.fantasy.interfaces.Healer
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {  }

class Sorcerer(
    name: String,
    health: Int,
    attackPower: Int,
    level: CharacterLevel,
    override val mana: Int,
    override val healingPower: Int,
): Character(
    name=name,
    health=health,
    attackPower=attackPower,
    level = level
), Healer {
    private var currnetMana = mana

    init {
        val totalPoints = attackPower + mana + healingPower
        require(totalPoints <= level.points) {
            logger.error {
                "Invaild totalPoints: $totalPoints only allowed ${level.points} at ${level.name}"
            }
        }
    }

    override fun attack(target: Character){
        if(!isAlive) {
            logger.info { "$name is dead and cannot attack" }
            return
        }
        if(mana <= 0){
            logger.info { "$name is too tired to attack" }
            return
        }
        else {
            logger.info { "$name casts a spell at ${target.name}" }
            target.receiveAttack(attackPower)
            currnetMana -= 1
            this.heal()
        }
    }

    override fun heal() {
        if(currnetMana <= 0) {
            println("$name is out of mana")
            logger.info { "$name is out of mana "}
        }
        else if(currnetMana > 0 && currentHealth < health) {
            currentHealth += healingPower
            logger.info { "$name heals self to $currentHealth health "}
            currnetMana -= 1
        }
    }

    override fun beforeRounds() {
        currnetMana++
    }

    override fun afterRound() {
        TODO("Not yet implemented")
    }

    override fun toString(): String {
        return super.toString()
    }
}