package online_qualification

import java.io.File


fun main() {
    File("input/online_qualification").listFiles()?.forEach {
        computeSolution(it)
    }

    //computeSolution(File(File("input/online_qualification"), "a_example.txt"))
}

fun computeSolution(file: File) {

    file.bufferedReader().use { reader ->
        val lineOneArr = reader.readLine().split(" ".toRegex()).toTypedArray()
        val totalNoOfBooks = lineOneArr[0].toInt()
        val noOfLibraries = lineOneArr[1].toInt()

        val totalGlobalNoOfDays = lineOneArr[2].toInt()


        val allBooks = mutableListOf<Book>()    // list of All available books

        val allLibraries = mutableListOf<Library>()  // list of all libraries with their books

        val bookScoreTable = HashMap<String, Int>()   // Map of Book Id to score

        val scannedBooks = mutableListOf<Book>() //

        //second line
        reader.readLine().split(" ".toRegex()).toTypedArray().also {
            it.forEachIndexed { index, score ->
                bookScoreTable[index.toString()] = score.toInt()
                allBooks.add(Book(index, score.toInt()))
            }
        }

        for (i in 0 until noOfLibraries) {
            val lineThreeArr = reader.readLine().split(" ".toRegex()).toTypedArray()
            val noOfBooksInTheLibrary = lineThreeArr[0].toInt()
            val noOfDaysToSignUp = lineThreeArr[1].toInt()
            val noOfShipableBooksPerDay = lineThreeArr[2].toInt()
            val booksInLibrary = mutableListOf<Book>()

            reader.readLine().split(" ".toRegex()).toTypedArray().also { array ->
                array.forEach {
                    booksInLibrary.add(Book(it.toInt(), bookScoreTable[it]!!))
                }
            }

            allLibraries.add(Library(i, noOfBooksInTheLibrary, noOfDaysToSignUp, noOfShipableBooksPerDay, booksInLibrary))
        }


        ///// output


        File(File("output/online_qualification").apply { if (!exists()) mkdir() }, "${file.name}_out.txt").printWriter().use { out ->

            //Start work

            // Sort the libraries

            allLibraries.sortByDescending {
                it.getPoint()
            }


            val librariesToUseIdStr = StringBuilder()
            var totalShippingCount = 0
            val librariesToUse = mutableListOf<Library>()




            allLibraries.forEach {
                if (totalShippingCount + it.noOfDaysToSignUp < totalGlobalNoOfDays) {
                    totalShippingCount += it.noOfDaysToSignUp
                    librariesToUse.add(it)
                    librariesToUseIdStr.append("${it.id} ")
                }
            }


            out.println(librariesToUse.size) //first line



            librariesToUse.forEachIndexed { index, library ->
                val libraryIdAndNoOfBooksStr = StringBuilder()
                val booksToSendStr = StringBuilder()

                libraryIdAndNoOfBooksStr.append("${library.id} ")

                var booksCount = 0

                library.listOfBooks.forEachIndexed { index, book ->
                    if (!scannedBooks.contains(book)){
                        scannedBooks.add(book)
                        booksToSendStr.append("${book.id} ")
                        booksCount++
                    }
                }

                libraryIdAndNoOfBooksStr.append("$booksCount")

                out.println(libraryIdAndNoOfBooksStr.toString()) // Second line -->repeated
                out.println(booksToSendStr.toString().trimEnd())

            }

        }

    }

}


data class Book(val id: Int,
                val score: Int)

data class Library(
        val id: Int,
        val noOfBooks: Int,
        val noOfDaysToSignUp: Int,
        val noOfBooksShippablePerDay: Int,
        val listOfBooks: MutableList<Book>) {

    init {
        listOfBooks.sortByDescending { it.score }
    }

    fun getPoint(): Double {
        return ((noOfBooksShippablePerDay * getBookValues()) / noOfDaysToSignUp)
    }

    fun getBookValues(): Double {
        var values = 0
        listOfBooks.forEach {
            values += it.score
        }
        return values.toDouble()
    }
}

