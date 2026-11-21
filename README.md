
# Smart Quiz

Smart Quiz is an Android educational application developed for CP3406 Assessment 3.

The app helps students practise their knowledge through online multiple-choice questions. Users can choose a difficulty level, complete quizzes, review their scores, and track their learning progress.

## Features

- Online quiz questions from Open Trivia DB
- Easy, Medium, and Hard difficulty settings
- Multiple-choice questions with answer feedback
- Automatic score calculation
- Quiz history and learning statistics
- Local score storage using Room Database
- Clear quiz history with a confirmation dialog
- Persistent difficulty preferences using SharedPreferences
- Loading and error messages for API requests

## Application Screens

1. **Home:** Main menu with navigation to other screens.
2. **Quiz:** Online questions, answer selection, feedback, and final scores.
3. **My Statistics:** Quiz history, total quizzes, average accuracy, and best score.
4. **Settings:** Difficulty selection and quiz history management.

## Technologies Used

- Kotlin
- Jetpack Compose
- Material Design 3
- Android Navigation Component
- MVVM Architecture
- Repository Pattern
- Manual Constructor Dependency Injection
- Retrofit
- Open Trivia DB API
- Room Database
- Kotlin Coroutines and StateFlow
- SharedPreferences
- JUnit
- Android Instrumented Testing
- Jetpack Compose UI Testing

## Application Architecture

Smart Quiz follows the MVVM architecture.

### UI Layer
Jetpack Compose is used to build the application screens and handle user interactions.

### ViewModel Layer
QuizViewModel manages online quiz loading and UI states.

QuizResultViewModel manages saved quiz results and provides data for the statistics screen.

### Repository Layer
QuizRepository retrieves and processes questions from Open Trivia DB.

QuizResultRepository manages quiz results through Room Database.

### Data Layer
Retrofit handles network requests.

Room Database stores quiz results locally.

SharedPreferences stores the selected difficulty.

### Dependency Injection
The project uses manual constructor-based dependency injection.

Repositories can receive their dependencies through constructors.

QuizViewModel and QuizResultViewModel support externally supplied repository dependencies while retaining compatible constructors for existing Android ViewModel creation.

A dedicated dependency injection framework such as Hilt is not used.

## Testing

The project has successfully completed 38 automated tests.

### Unit Tests (11)
- QuizScoreCalculatorTest: 5 tests
- QuizQuestionMapperTest: 6 tests

### Instrumented and GUI Tests (27)
- HtmlDecoderTest: 6 tests
- QuizDatabaseTest: 6 tests
- HomeScreenTest: 4 tests
- SettingsScreenTest: 6 tests
- StatisticsScreenTest: 5 tests

The tests cover score calculation, question processing, HTML decoding, database operations, screen elements, button interactions, difficulty selection, and navigation.

The complete quiz flow has also been manually tested.

## How to Run

1. Clone or download this repository.
2. Open the SmartQuiz project in Android Studio.
3. Wait for Gradle synchronization to finish.
4. Start an Android emulator or connect an Android device.
5. Run the app.
6. Ensure the device has internet access to load online questions.

## Data Storage and Privacy

Quiz results are stored locally using Room Database.

Difficulty preferences are saved using SharedPreferences.

Users can clear their quiz history from the Settings screen.

The application retrieves quiz questions from Open Trivia DB. An internet connection is required for online quizzes.

## Project Information

- Course: CP3406
- Assessment: Assessment 3 - Education App
- Platform: Android
- Programming Language: Kotlin
