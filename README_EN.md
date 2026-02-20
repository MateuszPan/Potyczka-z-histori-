### "Clash with History"
A mobile educational application for Android. This is an engineering thesis project at ISSP, University of Wrocław.
Goal: to support primary school students in reinforcing their history knowledge by combining educational content with interactive games. The application works offline and uses a local database to store questions and answers.
welcomeScreen avatar era chapter
level potyczka_chase answer_wrong summary
duel_jump master_answer_correct master_defeat
### Game Modes

1 — Clash (Potyczka)

A mode combining arcade elements with an educational quiz.
Features:
•	selection of character, era, chapter, and difficulty level,
•	chase phase (the player escapes from the opponent),
•	random bonuses and traps,
•	single-choice questions (4 answers) from selected material and difficulty level,
•	score dependent on difficulty level,
•	local high score saving (SharedPreferences).
Gameplay ends after achieving a streak of 5 correct answers or when the player exits the game manually.

2 — Master Duel

A dynamic runner-type mode intended for more advanced history players.
Features:
•	one-touch control (jump),
•	randomly generated obstacles,
•	jump physics independent of FPS,
•	questions drawn from the entire database,
•	game loop paused during questions,
Win/lose conditions: points, knowledge level, number of mistakes, time limit.

### Educational Features
•	questions compliant with the history curriculum (any curriculum changes can be easily introduced into the code),
•	randomized order of questions and answers,
•	immediate feedback (colors + sound),
•	possibility of repeated practice,
•	no internet connection required.

### Application Architecture
Logical division into three layers:
•	UI – Jetpack Compose (declarative interface),
•	Logic and navigation – Navigation Compose,
•	Data – Room + SQLite.
Data is passed explicitly between screens using navigation arguments, without using global application state.

### Database
•	local relational SQLite database,
•	handled via the Room library,
•	entities: EpochEntity, ChapterEntity, QuestionEntity, AnswerEntity,
•	foreign key relations with cascade deletion,
•	data initialization via DatabaseSeeder.

### Sound
•	background music played globally,
•	sound effects: correct answer, wrong answer, collision, bonus/trap,
•	ability to adjust/mute sound,
•	effects implemented using SoundPool.

### Technologies
•	Kotlin
•	Jetpack Compose
•	Navigation Compose
•	Room (SQLite)
•	Coroutines
•	SoundPool
•	SharedPreferences
