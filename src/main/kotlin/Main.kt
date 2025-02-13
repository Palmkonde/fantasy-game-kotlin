import com.fantasy.interfaces.Character
import com.fantasy.models.*
import java.io.File
import kotlinx.serialization.json.Json

fun convertToCharacter(data: CharacterDataWrapper): Character {
    return when (data.type) {
        "Warrior" -> Warrior(
            name = data.name,
            health = data.health,
            attackPower = data.attackPower,
            stamina = data.stamina ?: 0,
            defensePower = data.defensePower ?: 0,
            level = data.level
        )
        "Sorcerer" -> Sorcerer(
            name = data.name,
            health = data.health,
            attackPower = data.attackPower,
            mana = data.stamina ?: 0,
            healingPower = data.defensePower ?: 0,
            level = data.level
        )
        else -> throw IllegalArgumentException("Unknown character type: ${data.type}")
    }
}

fun loadCharactersFromFile(fileName: String): List<Character> {
    val file = File(fileName)
    if (!file.exists()) error("File not found: $fileName")

    val rawData = file.readText().trimIndent()
    val json =  Json { ignoreUnknownKeys = true }
    val characterDataList = json.decodeFromString<List<CharacterDataWrapper>>(rawData)

    val characters = characterDataList.map { convertToCharacter(it) }
    return characters
}

fun main() {
    val characters = loadCharactersFromFile("src/main/resources/character.json")
    val winner = Match(
        challenger = characters[0],
        opponent = characters[1],
        rounds = 20
    ).fight()
}