package cn.haixiao

data class Person(val name: String, val age: Int)

val persons = listOf(
        Person("Peter", 16),
        Person("Anna", 23),
        Person("Anna", 28),
        Person("Sonya", 39)
)

fun main(args: Array<String>) {
    val names = persons
            .asSequence()
            .filter { it.age > 18 }
            .map { it.name }
            .distinct()
            .sorted()
            .toList()
    println(names)

    val result = sequenceOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 0)
            .filter { it % 2 == 0 }
            .toList()
    println(result)

    sequenceOf("A", "B", "C")
            .filter {
                println("filter: $it")
                true
            }
            .forEach { println("forEach: $it") }

    val result2 = sequenceOf("a", "b", "c")
            .map {
                println("map: $it")
                it.toUpperCase()
            }
            .any {
                println("any: $it")
                it.startsWith("B")
            }

    println(result2)

}