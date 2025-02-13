package com.fantasy.interfaces

import com.fantasy.models.CharacterLevel

abstract class Character(
    open val name: String,
    open val health: Int,
    open val attackPower: Int,
    open val level: CharacterLevel
): Recoverable {

    protected var currentHealth = health
    protected val isAlive: Boolean
        get() = currentHealth > 0


    open fun receiveAttack(attackPower: Int){
        currentHealth -= attackPower

        if(currentHealth <= 0) {
            println("$name has been defeated")
            currentHealth = 0
        }
        else {
            println("$name has $currentHealth remaining")
        }
    }

    abstract fun attack(target: Character)
}
