"Potyczka z historią"

Mobilna aplikacja edukacyjna na system Android. To rpojekt pracy inżynierskiej na ISSP UWr.  
Cel - wsparcie uczniów szkoły podstawowej w utrwalaniu wiedzy z przedmiotu historia, poprzez połączenie treści edukacyjnych z interaktywnymi grami. Aplikacja działa offline wykorzystuje lokalną bazę danych do przechowywania pytań i opowiedzi.
<img width="210" height="422" alt="welcomeScreen" src="https://github.com/user-attachments/assets/17aedbf6-1f53-46a7-a981-ccf0c525e58d" />   <img width="210" height="422" alt="pojedynek_skok" src="https://github.com/user-attachments/assets/0f884923-4c60-4520-b06b-423343cfb811" />   <img width="210" height="422" alt="potyczka_gonitwa" src="https://github.com/user-attachments/assets/f42a9c1a-1f90-495e-abe1-a0aa7f38d62c" />   <img width="210" height="422" alt="epoka" src="https://github.com/user-attachments/assets/6034cc11-5cae-4955-8736-fc15ca882790" />
<img width="210" height="422" alt="rozdzial" src="https://github.com/user-attachments/assets/4a66b1ca-dff8-4770-8da9-9ae0a03ff610" />   <img width="210" height="422" alt="poziom" src="https://github.com/user-attachments/assets/0e729908-b2f3-4a7c-93b6-b74c7f3ec264" />   <img width="210" height="422" alt="odp_nie" src="https://github.com/user-attachments/assets/f8a6559b-cb26-4761-9b6b-e450ff6e56fe" />   <img width="210" height="422" alt="podsumowanie" src="https://github.com/user-attachments/assets/5eeae636-9338-4410-9517-fe6b2ef9bdfd" />
<img width="210" height="422" alt="mistrz_odp_tak" src="https://github.com/user-attachments/assets/0107a571-af18-439b-b963-109f7791fd6f" />

Dwa tryby gry:
1 -Potyczka
Tryb łączący elementy zręcznościowe z quizem edukacyjnym.Cechy to:
- wybór postaci, epoki, rozdziału i poziomu trudności,
- faza gonitwy (gracz ucieka przed przeciwnikiem),
- losowe bonusy i pułapki,
- pytania jednokrotnego wyboru (4 odpowiedzi) z wybranego   materiału i stopnia trudności,
- punktacja zależna od poziomu trudności,
- zapis najlepszego wyniku lokalnie (SharedPreferences).
Rozgrywka kończy się po uzyskaniu serii 5 poprawnych odpowiedzi lub po ręcznym wyjściu z gry.

2 - Pojedynek Mistrza
Dynamiczny tryb typu runner, skierowany do bardziej zaawansowanych w historii graczy. Cechy:
- sterowanie jednym dotknięciem (skok),
- losowo generowane przeszkody,
- fizyka skoku niezależna od FPS,
- pytania losowane z całej bazy danych,
- pauzowanie pętli gry podczas pytania,
- warunki zwycięstwa/porażki:
  - punkty,
  - poziom wiedzy,
  - liczba błędów,
  - limit czasu.

Funkcje edukacyjne:
- pytania zgodne z podstawą programową historii, (ewentualne zmiany w podstawie łatwo wprowadzić do kodu)
- losowa kolejność pytań i odpowiedzi,
- natychmiastowa informacja zwrotna (kolory + dźwięk),
- możliwość wielokrotnego powtarzania materiału,
- brak konieczności połączenia z Internetem.

Architektura aplikacji
Logiczny podział na trzy warstwy:
- UI – Jetpack Compose (deklaratywny interfejs),
- Logika i nawigacja – Navigation Compose,
- Dane – Room + SQLite.
Dane przekazywane są jawnie pomiędzy ekranami za pomocą argumentów nawigacyjnych, bez stosowania globalnego stanu aplikacji.

Baza danych
- lokalna relacyjna baza danych SQLite,
- obsługa przez bibliotekę Room,
- encje:EpochEntity, ChapterEntity, QuestionEntity, AnswerEntity,
- relacje z kluczami obcymi i usuwaniem kaskadowym,
- inicjalizacja danych przez DatabaseSeeder.

Dźwięk
- muzyka tła odtwarzana globalnie,
- efekty dźwiękowe:
  - poprawna odpowiedź,
  - błędna odpowiedź,
  - kolizja,
  - bonus/pułapka,
- możliwość regulacji/zastopowania dźwięku,
- efekty realizowane przy użyciu `SoundPool`.

Technologie
- Kotlin
- Jetpack Compose
- Navigation Compose
- Room (SQLite)
- Coroutines
- SoundPool
- SharedPreferences
