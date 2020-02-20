package practice

import java.io.File

data class Pizza(val type: Int, val noOfSlices: Int)

fun main() {
    File("input/practice").listFiles()?.forEach {
        computePizza(it)
    }
}

fun computePizza(file: File) {
    val allPizzaType = mutableListOf<Pizza>()
    var maxNoOfSlices = 0
    file.bufferedReader().use { reader ->
        val firstLine = reader.readLine().split(" ".toRegex()).toTypedArray()
        maxNoOfSlices = firstLine.first().toInt()
        //second line
        reader.readLine().split(" ".toRegex()).toTypedArray().also {
            it.forEachIndexed { index, s ->
                allPizzaType.add(Pizza(index, s.toInt()))
            }
        }
    }

    var currentPizzaCount = 0
    val pizzaTypesToOrder = StringBuilder()
    var noOfPizzaTypesToOrder = 0

    for (i in allPizzaType.size - 1 downTo 0) {
        if (currentPizzaCount >= maxNoOfSlices) break
        val currentPizza = allPizzaType[i]
        if ((currentPizza.noOfSlices + currentPizzaCount) < maxNoOfSlices) {
            currentPizzaCount += currentPizza.noOfSlices
            noOfPizzaTypesToOrder++
            pizzaTypesToOrder.append("${currentPizza.type.toString().reversed()} ")
        }
    }

    File(File("output/practice").apply { if (!exists()) mkdir() }, "${file.name}_out.txt").printWriter().use { out ->
        out.println("$noOfPizzaTypesToOrder")
        out.println(pizzaTypesToOrder.toString().trimEnd().reversed())
    }
}
