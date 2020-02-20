package online_qualification

import java.io.File


fun main() {
    File("input/online_qualification").listFiles()?.forEach {
        computeSolution(it)
    }
}

fun computeSolution(file: File) {

    file.bufferedReader().use { reader ->

    }


    File(File("output/online_qualification").apply { if (!exists()) mkdir() }, "${file.name}_out.txt").printWriter().use { out ->

    }
}
