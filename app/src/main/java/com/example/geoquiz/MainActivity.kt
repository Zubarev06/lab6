package com.example.geoquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.geoquiz.ui.theme.GeoQuizTheme
import androidx.compose.runtime.getValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GeoQuizTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                }
            }
        }
    }
}

data class Question(val text: String, val answer: Boolean)

@Composable
fun Quiz(modifier: Modifier = Modifier){
    val questions = remember {
        listOf(
            Question("Canberra is the capital of Australia.", true),
            Question("The Pacific Ocean is larger than the Atlantic Ocean.", true),
            Question("The Suez Canal connects the Red Sea and the Indian Ocean.", false),
            Question("The source of the Nile River is in Egypt.", false),
            Question("The Amazon River is the longest river in the Americas.", true),
            Question("Lake Baikal is the world's oldest and deepest freshwater lake.", true)
        )
    }
    var NumQuestion by remember { mutableStateOf(0) }
    var userAnswers by remember { mutableStateOf(List(questions.size) { null as Boolean? }) }
    var CorrectAns by remember {mutableStateOf(0)}
    var Result by remember {mutableStateOf(false)}

    LaunchedEffect(NumQuestion, userAnswers[NumQuestion]) {
        if (NumQuestion == questions.size - 1 && userAnswers[NumQuestion] != null) {
            Result = true
        }
    }

    if (Result) {
        ResultScreen(
            correctAnswers = CorrectAns,
            totalQuestions = questions.size,
            onRetry = {
                NumQuestion = 0
                userAnswers = List(questions.size) { null }
                NumQuestion = 0
                Result = false
            }
        )
    } else {
     QuizScreen(
         question = questions[NumQuestion],
         hasAnswered = userAnswers[NumQuestion] != null,
         isLastQuestion = NumQuestion == questions.size - 1,
         onAnswer = { userAnswer ->
             // Проверяем ответ и сохраняем его
             if (userAnswer == questions[NumQuestion].answer) {
                 NumQuestion++
             }
             userAnswers = userAnswers.toMutableList().also { it[NumQuestion] = userAnswer }
         },
         onNext = {
             if (NumQuestion < questions.size - 1) {
                 NumQuestion++
             }
         }
     )
    }
}

@Composable
fun QuizScreen(
    question: Question,
    hasAnswered: Boolean,
    isLastQuestion: Boolean,
    onAnswer: (Boolean) -> Unit,
    onNext: () -> Unit,
){

}

@Composable
fun ResultScreen(
    correctAnswers: Int,
    totalQuestions: Int,
    onRetry: () -> Unit
) {

}