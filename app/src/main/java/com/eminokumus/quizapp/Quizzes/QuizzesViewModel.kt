package com.eminokumus.quizapp.Quizzes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eminokumus.quizapp.vo.Question
import com.eminokumus.quizapp.vo.Quiz
import javax.inject.Inject

class QuizzesViewModel @Inject constructor() : ViewModel() {

    private val _quizList = MutableLiveData<List<Quiz>>()
    val quizList: LiveData<List<Quiz>> get() = _quizList

    init {
        _quizList.value = generateQuizList()
    }

}

private fun generateQuizList(): List<Quiz> {
    return listOf(
        Quiz("Quiz 1: General Knowledge", generateGeneralKnowledgeQuestions()),
        Quiz("Quiz 2: Science", generateScienceQuestions()),
        Quiz("Quiz 3: History", generateHistoryQuestions()),
        Quiz("Quiz 4: Geography", generateGeographyQuestions()),
        Quiz("Quiz 5: Literature", generateLiteratureQuestions()),
        Quiz("Quiz 6: Mathematics", generateMathematicsQuestions()),
        Quiz("Quiz 7: Movies", generateMoviesQuestions()),
        Quiz("Quiz 8: Music", generateMusicQuestions()),
        Quiz("Quiz 9: Sports", generateSportsQuestions()),
        Quiz("Quiz 10: Technology", generateTechnologyQuestions()),

    )
}

private fun generateGeneralKnowledgeQuestions(): List<Question> {
    val questionList = listOf(
        Question(
            "What is the capital city of Turkey?",
            listOf("Istanbul", "Ankara", "Izmir", "Bursa"),
            "Ankara"
        ),
        Question(
            "In which museum is the Mona Lisa displayed?",
            listOf("Louvre Museum", "British Museum", "Prado Museum", "Hermitage Museum"),
            "Louvre Museum"
        ),
        Question(
            "Which theory is Albert Einstein famous for?",
            listOf("Quantum Theory", "Theory of Relativity", "Theory of Evolution", "Germ Theory"),
            "Theory of Relativity"
        ),
        Question(
            "Which planet is known as the Red Planet?",
            listOf("Mars", "Jupiter", "Saturn", "Venus"),
            "Mars"
        ),
        Question(
            "What is the largest mammal in the world?",
            listOf("Elephant", "Blue Whale", "Great White Shark", "Giraffe"),
            "Blue Whale"
        )
    )
    return questionList
}

private fun generateScienceQuestions(): List<Question> {
    val questionList = listOf(
        Question(
            "What is the chemical symbol for water?",
            listOf("O", "H2O", "CO2", "NaCl"),
            "H2O"
        ),
        Question(
            "What is the main gas found in the air we breathe?",
            listOf("Oxygen", "Hydrogen", "Nitrogen", "Carbon Dioxide"),
            "Nitrogen"
        ),
        Question(
            "Who is known as the father of modern physics?",
            listOf("Isaac Newton", "Albert Einstein", "Galileo Galilei", "Niels Bohr"),
            "Isaac Newton"
        ),
        Question(
            "What is the hardest natural substance on Earth?",
            listOf("Gold", "Diamond", "Iron", "Platinum"),
            "Diamond"
        ),
        Question(
            "What planet is closest to the sun?",
            listOf("Earth", "Venus", "Mercury", "Mars"),
            "Mercury"
        )
    )
    return questionList

}

private fun generateHistoryQuestions(): List<Question> {
    val questionList = listOf(
        Question(
            "Who was the first president of the United States?",
            listOf("Thomas Jefferson", "Abraham Lincoln", "George Washington", "John Adams"),
            "George Washington"
        ),
        Question(
            "In which year did World War II end?",
            listOf("1940", "1942", "1945", "1948"),
            "1945"
        ),
        Question(
            "Who discovered America in 1492?",
            listOf("Christopher Columbus", "Leif Erikson", "Marco Polo", "Ferdinand Magellan"),
            "Christopher Columbus"
        ),
        Question(
            "What was the name of the ship on which the Pilgrims traveled to America in 1620?",
            listOf("Santa Maria", "Mayflower", "Titanic", "Beagle"),
            "Mayflower"
        ),
        Question(
            "Who was the famous Egyptian queen known for her beauty and her relationship with Julius Caesar?",
            listOf("Nefertiti", "Cleopatra", "Hatshepsut", "Ankhesenamun"),
            "Cleopatra"
        )
    )
    return questionList

}

private fun generateGeographyQuestions(): List<Question> {
    val questionList = listOf(
        Question(
            "Which is the longest river in the world?",
            listOf("Amazon River", "Nile River", "Yangtze River", "Mississippi River"),
            "Nile River"
        ),
        Question(
            "What is the largest desert in the world?",
            listOf("Sahara Desert", "Arabian Desert", "Gobi Desert", "Kalahari Desert"),
            "Sahara Desert"
        ),
        Question(
            "Which country is also known as the Land of the Rising Sun?",
            listOf("China", "South Korea", "Japan", "Thailand"),
            "Japan"
        ),
        Question(
            "What is the capital of Australia?",
            listOf("Sydney", "Melbourne", "Canberra", "Brisbane"),
            "Canberra"
        ),
        Question(
            "Which continent is the smallest by land area?",
            listOf("Europe", "Australia", "Antarctica", "South America"),
            "Australia"
        )
    )
    return questionList

}

private fun generateLiteratureQuestions(): List<Question> {
    val questionList = listOf(
        Question(
            "Who wrote 'Pride and Prejudice'?",
            listOf("Emily Brontë", "Charlotte Brontë", "Jane Austen", "Mary Shelley"),
            "Jane Austen"
        ),
        Question(
            "What is the title of the first Harry Potter book?",
            listOf(
                "Harry Potter and the Chamber of Secrets",
                "Harry Potter and the Goblet of Fire",
                "Harry Potter and the Prisoner of Azkaban",
                "Harry Potter and the Philosopher's Stone"
            ),
            "Harry Potter and the Philosopher's Stone"
        ),
        Question(
            "Who wrote '1984'?",
            listOf("Aldous Huxley", "George Orwell", "Ray Bradbury", "J.R.R. Tolkien"),
            "George Orwell"
        ),
        Question(
            "Who is the author of 'To Kill a Mockingbird'?",
            listOf("Harper Lee", "J.D. Salinger", "F. Scott Fitzgerald", "Mark Twain"),
            "Harper Lee"
        ),
        Question(
            "Which famous playwright wrote 'Romeo and Juliet'?",
            listOf("Christopher Marlowe", "William Shakespeare", "Ben Jonson", "John Webster"),
            "William Shakespeare"
        )
    )
    return questionList

}

private fun generateMathematicsQuestions(): List<Question> {
    val questionList = listOf(
        Question(
            "What is the value of π (pi) rounded to two decimal places?",
            listOf("3.12", "3.14", "3.16", "3.18"),
            "3.14"
        ),
        Question(
            "What is the square root of 144?",
            listOf("10", "11", "12", "13"),
            "12"
        ),
        Question(
            "What is 5 factorial (5!)?",
            listOf("60", "100", "120", "150"),
            "120"
        ),
        Question(
            "What is the sum of the interior angles of a triangle?",
            listOf("90 degrees", "180 degrees", "270 degrees", "360 degrees"),
            "180 degrees"
        ),
        Question(
            "What is the value of 2^5?",
            listOf("16", "24", "32", "40"),
            "32"
        )
    )
    return questionList

}

private fun generateMoviesQuestions(): List<Question> {
    val questionList = listOf(
        Question(
            "Which movie won the Academy Award for Best Picture in 2020?",
            listOf("1917", "Joker", "Parasite", "Once Upon a Time in Hollywood"),
            "Parasite"
        ),
        Question(
            "Who played the character of Jack Dawson in 'Titanic'?",
            listOf("Brad Pitt", "Johnny Depp", "Tom Cruise", "Leonardo DiCaprio"),
            "Leonardo DiCaprio"
        ),
        Question(
            "In which movie does the character 'Forrest Gump' appear?",
            listOf("The Green Mile", "Cast Away", "Forrest Gump", "Saving Private Ryan"),
            "Forrest Gump"
        ),
        Question(
            "Who directed 'Pulp Fiction'?",
            listOf("Steven Spielberg", "Quentin Tarantino", "Martin Scorsese", "James Cameron"),
            "Quentin Tarantino"
        ),
        Question(
            "Which actress played Hermione Granger in the Harry Potter series?",
            listOf("Emma Watson", "Jennifer Lawrence", "Emma Stone", "Kristen Stewart"),
            "Emma Watson"
        )
    )
    return questionList

}

private fun generateMusicQuestions(): List<Question> {
    val questionList = listOf(
        Question(
            "Who is known as the 'King of Pop'?",
            listOf("Elvis Presley", "Michael Jackson", "Prince", "Justin Timberlake"),
            "Michael Jackson"
        ),
        Question(
            "What is the title of The Beatles' first album?",
            listOf("Help!", "Rubber Soul", "Please Please Me", "Abbey Road"),
            "Please Please Me"
        ),
        Question(
            "Which artist released the album 'Lemonade' in 2016?",
            listOf("Rihanna", "Taylor Swift", "Beyoncé", "Adele"),
            "Beyoncé"
        ),
        Question(
            "Which band is known for the song 'Bohemian Rhapsody'?",
            listOf("The Rolling Stones", "Led Zeppelin", "Queen", "Pink Floyd"),
            "Queen"
        ),
        Question(
            "Who is the lead singer of U2?",
            listOf("Bono", "Sting", "Mick Jagger", "Freddie Mercury"),
            "Bono"
        )
    )
    return questionList

}

private fun generateSportsQuestions(): List<Question> {
    val questionList = listOf(
        Question(
            "Which country won the FIFA World Cup in 2018?",
            listOf("Germany", "Brazil", "France", "Argentina"),
            "France"
        ),
        Question(
            "Who has won the most Grand Slam titles in men's tennis?",
            listOf("Roger Federer", "Rafael Nadal", "Novak Djokovic", "Pete Sampras"),
            "Roger Federer"
        ),
        Question(
            "Which basketball player is known as 'King James'?",
            listOf("Michael Jordan", "Kobe Bryant", "LeBron James", "Magic Johnson"),
            "LeBron James"
        ),
        Question(
            "Which country hosts the Tour de France?",
            listOf("Spain", "Italy", "Belgium", "France"),
            "France"
        ),
        Question(
            "Who won the gold medal in men's 100 meters at the 2008 Beijing Olympics?",
            listOf("Usain Bolt", "Yohan Blake", "Tyson Gay", "Justin Gatlin"),
            "Usain Bolt"
        )
    )
    return questionList

}

private fun generateTechnologyQuestions(): List<Question> {
    val questionList = listOf(
        Question(
            "Who is the founder of Microsoft?",
            listOf("Steve Jobs", "Bill Gates", "Mark Zuckerberg", "Larry Page"),
            "Bill Gates"
        ),
        Question(
            "What does 'HTTP' stand for?",
            listOf(
                "HyperText Transfer Protocol",
                "HyperText Transmission Protocol",
                "HighText Transfer Protocol",
                "HyperText Transfer Procedure"
            ),
            "HyperText Transfer Protocol"
        ),
        Question(
            "Which company developed the Android operating system?",
            listOf("Apple", "Microsoft", "Google", "IBM"),
            "Google"
        ),
        Question(
            "What year was the first iPhone released?",
            listOf("2005", "2007", "2009", "2010"),
            "2007"
        ),
        Question(
            "What does 'CPU' stand for in computing?",
            listOf(
                "Central Processing Unit",
                "Computer Personal Unit",
                "Central Performance Unit",
                "Computer Processing Unit"
            ),
            "Central Processing Unit"
        )
    )
    return questionList
}


