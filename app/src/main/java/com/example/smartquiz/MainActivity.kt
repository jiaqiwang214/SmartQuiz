package com.example.smartquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import com.example.smartquiz.ui.viewmodel.QuizViewModel
import com.example.smartquiz.ui.viewmodel.QuizUiState
import com.example.smartquiz.ui.theme.SmartQuizTheme
import com.example.smartquiz.ui.viewmodel.QuizResultViewModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.smartquiz.data.preferences.QuizPreferences
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.material3.ButtonDefaults
import com.example.smartquiz.util.QuizScoreCalculator


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SmartQuizTheme {
                SmartQuizApp()
            }
        }
    }
}

@Composable
fun SmartQuizApp() {
    val navController = rememberNavController()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") {
                HomeScreen(
                    onStartQuiz = { navController.navigate("quiz") },
                    onStatistics = { navController.navigate("statistics") },
                    onSettings = { navController.navigate("settings") }
                )
            }

            composable("quiz") {
                QuizScreen(onBack = { navController.popBackStack() })
            }

            composable("statistics") {
                StatisticsScreen(onBack = { navController.popBackStack() })
            }

            composable("settings") {
                SettingsScreen(onBack = { navController.popBackStack() })
            }
        }
    }
}

@Composable
fun HomeScreen(
    onStartQuiz: () -> Unit,
    onStatistics: () -> Unit,
    onSettings: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Smart Quiz",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Learn something new every day!",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = onStartQuiz,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Start Quiz")
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = onStatistics,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("My Statistics")
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = onSettings,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Settings")
        }
    }
}


data class QuizQuestion(
    val question: String,
    val options: List<String>,
    val correctAnswerIndex: Int
)

private val sampleQuestions = listOf(
    QuizQuestion(
        "What is the capital of France?",
        listOf("London", "Paris", "Berlin", "Rome"),
        1
    ),
    QuizQuestion(
        "Which planet is known as the Red Planet?",
        listOf("Earth", "Mars", "Venus", "Jupiter"),
        1
    ),
    QuizQuestion(
        "What is 5 × 6?",
        listOf("11", "25", "30", "35"),
        2
    ),
    QuizQuestion(
        "Which language is used for Android development?",
        listOf("HTML", "Kotlin", "CSS", "SQL"),
        1
    ),
    QuizQuestion(
        "How many days are there in a week?",
        listOf("5", "6", "7", "8"),
        2
    )
)


@Composable
fun QuizScreen(
    onBack: () -> Unit,
    quizViewModel: QuizViewModel = viewModel()
) {
    val uiState by quizViewModel.uiState.collectAsState()

    when (val state = uiState) {

        is QuizUiState.Idle -> {
            QuizMessageScreen(
                message = "Preparing your quiz...",
                onBack = onBack
            )
        }

        is QuizUiState.Loading -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()

                Spacer(modifier = Modifier.height(20.dp))

                Text("Loading online questions...")

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedButton(onClick = onBack) {
                    Text("Back to Home")
                }
            }
        }

        is QuizUiState.Error -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Unable to load quiz",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = state.message,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { quizViewModel.loadQuestions() }
                ) {
                    Text("Retry")
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedButton(onClick = onBack) {
                    Text("Back to Home")
                }
            }
        }

        is QuizUiState.Success -> {
            OnlineQuizContent(
                questions = state.questions,
                onBack = onBack,
                onTryAgain = { quizViewModel.loadQuestions() }
            )
        }
    }
}

@Composable
fun QuizMessageScreen(
    message: String,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(message)

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onBack) {
            Text("Back to Home")
        }
    }
}


@Composable
fun OnlineQuizContent(
    questions: List<com.example.smartquiz.data.repository.QuizItem>,
    onBack: () -> Unit,
    onTryAgain: () -> Unit,
    resultViewModel: QuizResultViewModel =
        androidx.lifecycle.viewmodel.compose.viewModel()
) {

    var questionIndex by rememberSaveable(questions) {
        mutableIntStateOf(0)
    }

    var selectedAnswer by rememberSaveable(questions) {
        mutableIntStateOf(-1)
    }

    var score by rememberSaveable(questions) {
        mutableIntStateOf(0)
    }

    var quizFinished by rememberSaveable(questions) {
        mutableStateOf(false)
    }

    if (questions.isEmpty()) {
        QuizMessageScreen(
            message = "No questions available.",
            onBack = onBack
        )
        return
    }

    val currentQuestion = questions[
        questionIndex.coerceIn(questions.indices)
    ]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Smart Quiz",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (!quizFinished) {

            Text(
                text = "Question ${questionIndex + 1} / ${questions.size}",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            LinearProgressIndicator(
                progress = {
                    (questionIndex + 1).toFloat() / questions.size
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = currentQuestion.question,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            currentQuestion.options.forEachIndexed { index, option ->

                OutlinedButton(
                    onClick = {
                        if (selectedAnswer == -1) {
                            selectedAnswer = index


                            score = QuizScoreCalculator.calculateScore(
                                currentScore = score,
                                selectedAnswer = option,
                                correctAnswer = currentQuestion.options[
                                    currentQuestion.correctAnswerIndex
                                ]
                            )

                        }
                    },
                    enabled = selectedAnswer == -1,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(option)
                }

                Spacer(modifier = Modifier.height(12.dp))
            }

            if (selectedAnswer != -1) {

                val isCorrect =
                    selectedAnswer == currentQuestion.correctAnswerIndex

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = if (isCorrect) {
                        "Correct! Well done!"
                    } else {
                        "Incorrect. Correct answer: " +
                                currentQuestion.options[
                                    currentQuestion.correctAnswerIndex
                                ]
                    },
                    color = if (isCorrect) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.error
                    },
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {

                        if (questionIndex < questions.lastIndex) {
                            questionIndex++
                            selectedAnswer = -1
                        } else {
                            if (!quizFinished) {
                                quizFinished = true

                                resultViewModel.saveResult(
                                    score = score,
                                    totalQuestions = questions.size
                                )
                            }
                        }

                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        if (questionIndex == questions.lastIndex) {
                            "Finish Quiz"
                        } else {
                            "Next Question"
                        }
                    )
                }
            }

        } else {

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Quiz Completed!",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Your Score: $score / ${questions.size}",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Accuracy: ${score * 100 / questions.size}%",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onTryAgain,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Try Again")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back to Home")
        }
    }
}




@Composable
fun StatisticsScreen(
    onBack: () -> Unit,
    resultViewModel: QuizResultViewModel = viewModel()
) {
    val results by resultViewModel.allResults.collectAsState()

    val totalQuizzes = results.size

    val totalCorrect = results.sumOf { it.score }

    val totalQuestions = results.sumOf { it.totalQuestions }

    val averageAccuracy = if (totalQuestions > 0) {
        totalCorrect * 100.0 / totalQuestions
    } else {
        0.0
    }

    val bestResult = results.maxWithOrNull(
        compareBy<com.example.smartquiz.data.local.QuizResult> {
            if (it.totalQuestions > 0) {
                it.score.toDouble() / it.totalQuestions
            } else {
                0.0
            }
        }.thenBy { it.score }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        Text(
            text = "My Statistics",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Total Quizzes: $totalQuizzes",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Average Accuracy: %.1f%%".format(
                Locale.US,
                averageAccuracy
            ),
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = if (bestResult != null) {
                "Best Score: ${bestResult.score} / ${bestResult.totalQuestions}"
            } else {
                "Best Score: --"
            },
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Total Correct Answers: $totalCorrect",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        HorizontalDivider()

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Quiz History",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (results.isEmpty()) {

            Text(
                text = "No quiz history yet. Complete a quiz to see your results.",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

        } else {

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(
                    items = results,
                    key = { it.id }
                ) { result ->

                    val accuracy = if (result.totalQuestions > 0) {
                        result.score * 100 / result.totalQuestions
                    } else {
                        0
                    }

                    val dateText = SimpleDateFormat(
                        "dd MMM yyyy, HH:mm",
                        Locale.getDefault()
                    ).format(Date(result.completedAt))

                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            Text(
                                text = dateText,
                                style = MaterialTheme.typography.bodySmall
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Score: ${result.score} / ${result.totalQuestions}",
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = "Accuracy: $accuracy%"
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back to Home")
        }
    }
}




@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    resultViewModel: QuizResultViewModel = viewModel()
) {
    val context = LocalContext.current

    val quizPreferences = remember(context) {
        QuizPreferences(context)
    }

    var selectedDifficulty by remember {
        mutableStateOf(quizPreferences.getDifficulty())
    }

    var showClearDialog by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Quiz Difficulty",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Choose the difficulty for your next quiz.",
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        listOf("easy", "medium", "hard").forEach { difficulty ->

            OutlinedButton(
                onClick = {
                    selectedDifficulty = difficulty
                    quizPreferences.setDifficulty(difficulty)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (selectedDifficulty == difficulty) {
                        "✓ ${difficulty.replaceFirstChar { it.uppercase() }}"
                    } else {
                        difficulty.replaceFirstChar { it.uppercase() }
                    }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Selected Difficulty: ${
                selectedDifficulty.replaceFirstChar { it.uppercase() }
            }",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        HorizontalDivider()

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Quiz History",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Delete all saved quiz results from this device.",
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = {
                showClearDialog = true
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text("Clear Quiz History")
        }

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back to Home")
        }
    }

    if (showClearDialog) {

        AlertDialog(
            onDismissRequest = {
                showClearDialog = false
            },
            title = {
                Text("Clear Quiz History?")
            },
            text = {
                Text(
                    "This will permanently delete all saved quiz results. " +
                            "This action cannot be undone."
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        resultViewModel.clearResults()
                        showClearDialog = false
                    }
                ) {
                    Text(
                        text = "Delete All",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showClearDialog = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}



@Composable
fun BasicScreen(
    title: String,
    description: String,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = onBack) {
            Text("Back to Home")
        }
    }
}
