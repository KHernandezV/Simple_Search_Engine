package search
import java.io.File

fun search(lines: List<String>, words: Map<String, List<Int>>) {
        println("Select a matching strategy: ALL, ANY, NONE")
        when (readln()) {
            "ANY" -> anySearch(lines, words)
            "ALL" -> allSearch(lines, words)
            "NONE" -> noneSearch(lines, words)
        }
}

fun anySearch(lines: List<String>, words: Map<String, List<Int>>) {
    println("Enter query:")
    val stringToFind = readln().lowercase().split(" ")
    val results = mutableListOf<List<Int>>()
    stringToFind.forEach { if (it in words) words[it]?.let { it -> results.add(it) } }
    val finalResults = results.flatten().distinct()
    if (results.size != 0) {
        println("Results found:")
        lines.forEachIndexed { index, s -> if (index in finalResults) println(s)  }
    } else {
        println("No matching results")
    }
}

fun allSearch(lines: List<String>, words: Map<String, List<Int>>) {
    println("Enter query:")
    val stringToFind = readln().lowercase().split(" ")
    val resultsPartial = mutableListOf<List<Int>>()
    val results = mutableListOf<Int>()
    stringToFind.forEach { if (it in words) words[it]?.let { it -> resultsPartial.add(it) } }
    if (resultsPartial.isEmpty()) {
        println("No matching results")
        return
    }
    for (number in resultsPartial[0]) {
        var includesAll = true
        for (list in resultsPartial) {
            if (number !in list) {
                includesAll = false
            }
        }
        if (includesAll) results.add(number)
    }
    if (results.isNotEmpty()) {
        println("Results found:")
        lines.forEachIndexed { index, s -> if (index in results) println(s) }
    }  else println("No matching results")
}

fun noneSearch(lines: List<String>, words: Map<String, List<Int>>) {
    println("Enter query:")
    val stringToFind = readln().lowercase().split(" ")
    val results = mutableListOf<List<Int>>()
    stringToFind.forEach { if (it in words) words[it]?.let { it -> results.add(it) } }
    val finalResults = results.flatten().distinct()
    if (results.size != lines.size) {
        println("Results found:")
        lines.forEachIndexed { index, s -> if (index !in finalResults) println(s)  }
    } else {
        println("No matching results")
    }
}

fun printAll(lines: List<String>){
    lines.map { println(it) }
}

fun printMenu() {
    println(
        """=== Menu ===
1. Find a person
2. Print all people
0. Exit""".trimIndent()
    )
}

fun uniqueWords(lines: List<String>): List<String> {
    val returnWords = mutableListOf<String>()
    for (line in lines) {
        line.lowercase().split(" ").forEach { returnWords.add(it)}
    }
    return returnWords.distinct()
}

fun linesIn(lines: List<String>, word: String): Pair<String, List<Int>> {
    val linesAppearing = mutableListOf<Int>()
    for (line in lines) {
        if ( word in line.lowercase() ) linesAppearing.add(lines.indexOf(line))
    }
    return word to linesAppearing
}


fun main(args: Array<String>) {
    val allLines = File(args[1]).readLines()
    val uniqueWords = uniqueWords(allLines)
    val wordsIndex = uniqueWords.associate { linesIn(allLines, it) }
    while (true) {
        printMenu()
        when (readln().toInt()) {
            0 -> break
            1 -> search(allLines, wordsIndex)
            2 -> printAll(allLines)
            else-> println("Incorrect option! Try again.")
        }
    }
    println("Bye!")
}
