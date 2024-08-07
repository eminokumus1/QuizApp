package com.eminokumus.quizapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eminokumus.quizapp.vo.SolvedQuiz
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyDatabase @Inject constructor() {

    private val _solvedQuizList = MutableLiveData<List<SolvedQuiz>>()
    val solvedQuizList : LiveData<List<SolvedQuiz>> get() = _solvedQuizList

    private val mutableSolvedQuizList = mutableListOf<SolvedQuiz>()





    fun addSolvedQuizToList(solvedQuiz: SolvedQuiz){
        removeIfContains(solvedQuiz)
        mutableSolvedQuizList.add(solvedQuiz)

        _solvedQuizList.value = mutableSolvedQuizList.sortedBy { it.name }
    }

    private fun removeIfContains(solvedQuiz: SolvedQuiz) {
        for (item in mutableSolvedQuizList) {
            if (item.name == solvedQuiz.name) {
                mutableSolvedQuizList.remove(item)
            }
        }
    }

//    fun generateList(){
//        val solvedQuestionList = listOf(
//            SolvedQuestion(
//                Question(
//                "What is the capital city of Turkey?",
//                listOf("Istanbul", "Ankara", "Izmir", "Bursa"),
//                "Ankara"
//            ), "Ankara", QuestionStatus.CORRECT
//            ),
//            SolvedQuestion(Question(
//                "In which museum is the Mona Lisa displayed?",
//                listOf("Louvre Museum", "British Museum", "Prado Museum", "Hermitage Museum"),
//                "Louvre Museum"
//            ), "British Museum", QuestionStatus.WRONG
//            ),
//            SolvedQuestion(Question(
//                "Which theory is Albert Einstein famous for?",
//                listOf("Quantum Theory", "Theory of Relativity", "Theory of Evolution", "Germ Theory"),
//                "Theory of Relativity"
//            ),"Theory of Relativity", QuestionStatus.CORRECT),
//            SolvedQuestion(Question(
//                "Which planet is known as the Red Planet?",
//                listOf("Mars", "Jupiter", "Saturn", "Venus"),
//                "Mars"
//            ),"Jupiter", QuestionStatus.WRONG),
//            SolvedQuestion(Question(
//                "What is the largest mammal in the world?",
//                listOf("Elephant", "Blue Whale", "Great White Shark", "Giraffe"),
//                "Blue Whale"
//            ), "Elephant", QuestionStatus.WRONG)
//        )
//        _solvedQuizList.value = listOf(SolvedQuiz("Quiz 1: General Knowledge", solvedQuestionList))
//    }
}