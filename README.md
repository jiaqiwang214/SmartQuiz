# Smart Quiz

Smart Quiz is an Android educational application developed for CP3406 Assessment 3.

The app allows users to answer online quiz questions, select difficulty levels, track their scores, and review their learning progress.

## Features

- Online quiz questions from Open Trivia DB
- Easy, Medium, and Hard difficulty settings
- Multiple-choice questions
- Automatic score calculation
- Quiz history and statistics
- Local score storage using Room Database
- Clear quiz history with confirmation
- Persistent difficulty preferences

## Technologies Used

- Kotlin
- Jetpack Compose
- Material Design 3
- Android Navigation Component
- MVVM Architecture
- Retrofit
- Open Trivia DB API
- Room Database
- Kotlin Coroutines and StateFlow
- SharedPreferences
- JUnit and Android Instrumented Testing

## Application Screens

1. Home: Main menu and navigation.
2. Quiz: Online questions and score calculation.
3. My Statistics: Quiz history, average accuracy, and best score.
4. Settings: Difficulty selection and clearing quiz history.

## Testing

The project includes 23 successfully executed automated tests.

- QuizScoreCalculatorTest: 5 tests
- QuizQuestionMapperTest: 6 tests
- HtmlDecoderTest: 6 tests
- QuizDatabaseTest: 6 tests

## How to Run

1. Clone or download this repository.
2. Open the project in Android Studio.
3. Allow Gradle to synchronize.
4. Start an Android emulator or connect an Android device.
5. Run the app.
6. Ensure internet access is available to load quiz questions.

## Data Storage and Privacy

Quiz results are stored locally using Room Database.
Difficulty preferences are stored using SharedPreferences.
Users can delete their saved quiz history in Settings.

The app uses the Open Trivia DB API to retrieve quiz questions.

## Project Information

Course: CP3406

Assessment: Assessment 3 - Education App

Platform: Android

Language: Kotlin
