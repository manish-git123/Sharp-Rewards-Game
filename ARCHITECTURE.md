# Architecture Documentation - Sharp Rewards App

## Table of Contents
1. [System Architecture](#system-architecture)
2. [Entity Relationship Diagram (ERD)](#entity-relationship-diagram-erd)
3. [Data Relationship Diagram (DRD)](#data-relationship-diagram-drd)
4. [User Journey / Flow Diagrams](#user-journey--flow-diagrams)

---

## System Architecture

### Overview
Sharp Rewards App is a native Android application built with Java, using local data storage through SharedPreferences. The app follows a simple Activity-based architecture with a centralized RewardManager for data management.

### Architecture Pattern
- **Pattern**: Single Activity Pattern (with multiple Activities for navigation)
- **Data Storage**: SharedPreferences (Key-Value storage)
- **UI Framework**: Android XML Layouts with Material Design
- **State Management**: SharedPreferences-based persistence

### Components
1. **Activities**: UI components for each screen
2. **RewardManager**: Centralized data management class
3. **SharedPreferences**: Local data persistence layer

---

## Entity Relationship Diagram (ERD)

### Data Model Overview
Since the app uses SharedPreferences (key-value storage) rather than a relational database, the data model is simplified but can be represented as entities:

```mermaid
erDiagram
    USER ||--o{ DAILY_STREAK : "maintains"
    USER ||--o{ SPIN_WHEEL : "plays"
    USER ||--o{ QUIZ : "plays"
    USER ||--o{ TAP_COLLECT : "plays"
    
    USER {
        int totalPoints PK
        string lastCheckInDate
        int currentStreak
        int spinsUsedToday
        string lastSpinDate
        boolean quizPlayedToday
        string lastQuizDate
    }
    
    DAILY_STREAK {
        int streakCount
        string lastCheckInDate
        int pointsEarned
    }
    
    SPIN_WHEEL {
        int spinsUsedToday
        string lastSpinDate
        int maxSpinsPerDay
    }
    
    QUIZ {
        boolean playedToday
        string lastPlayDate
        int questionsAnswered
        int correctAnswers
    }
    
    TAP_COLLECT {
        int sessionPoints
        int totalTaps
        int sessionDuration
    }
```

### Text-Based ERD (Alternative)

```
┌─────────────────────────────────────────────────────────────┐
│                         USER                                │
├─────────────────────────────────────────────────────────────┤
│ PK  totalPoints: int                                        │
│     lastCheckInDate: string                                 │
│     currentStreak: int                                      │
│     spinsUsedToday: int                                     │
│     lastSpinDate: string                                    │
│     quizPlayedToday: boolean                                │
│     lastQuizDate: string                                    │
└─────────────────────────────────────────────────────────────┘
         │                    │                    │
         │                    │                    │
    ┌────▼────┐          ┌───▼────┐          ┌────▼─────┐
    │  DAILY  │          │ SPIN   │          │   QUIZ   │
    │ STREAK  │          │ WHEEL  │          │          │
    ├─────────┤          ├────────┤          ├──────────┤
    │ streak  │          │ spins  │          │ played   │
    │ count   │          │ used   │          │ today    │
    │ last    │          │ last   │          │ last     │
    │ date    │          │ date   │          │ date     │
    └─────────┘          └────────┘          └──────────┘
                                │
                                │
                         ┌──────▼───────┐
                         │ TAP COLLECT  │
                         ├──────────────┤
                         │ session      │
                         │ points       │
                         │ total taps   │
                         └──────────────┘
```

---

## Data Relationship Diagram (DRD)

### Data Flow and Relationships

```mermaid
graph TD
    A[User Opens App] --> B[MainActivity]
    B --> C[RewardManager]
    C --> D[SharedPreferences]
    
    B --> E[DailyStreakActivity]
    B --> F[SpinWheelActivity]
    B --> G[QuizActivity]
    B --> H[TapCollectActivity]
    
    E --> C
    F --> C
    G --> C
    H --> C
    
    C --> D
    
    D --> I[Key-Value Pairs]
    I --> J[total_points]
    I --> K[current_streak]
    I --> L[last_streak_date]
    I --> M[spins_used_today]
    I --> N[spins_date]
    I --> O[quiz_played_today]
    I --> P[quiz_date]
    
    style C fill:#6C63FF
    style D fill:#FF6B6B
    style B fill:#4ECDC4
```

### Data Structure Details

#### SharedPreferences Keys and Values

```
SHAREPREFERENCES: "SharpRewardsPrefs"
├── KEY_TOTAL_POINTS: int
│   └── Stores cumulative points earned
│
├── KEY_CURRENT_STREAK: int
│   └── Stores consecutive daily check-ins
│
├── KEY_LAST_STREAK_DATE: string
│   └── Format: "YYYY-M-D" (e.g., "2024-1-12")
│
├── KEY_SPINS_USED_TODAY: int
│   └── Tracks spins used in current day
│
├── KEY_SPINS_DATE: string
│   └── Last date spins were used
│
├── KEY_QUIZ_PLAYED_TODAY: boolean
│   └── Whether quiz was played today
│
└── KEY_QUIZ_DATE: string
    └── Last date quiz was played
```

### Data Flow Diagram

```
┌──────────────┐
│   Activities │
│  (UI Layer)  │
└──────┬───────┘
       │
       │ Read/Write
       │
┌──────▼───────────────┐
│   RewardManager      │
│  (Business Logic)    │
│                      │
│  - getTotalPoints()  │
│  - addPoints()       │
│  - updateStreak()    │
│  - canSpinToday()    │
│  - canPlayQuizToday()│
└──────┬───────────────┘
       │
       │ Store/Retrieve
       │
┌──────▼───────────────┐
│  SharedPreferences   │
│   (Data Layer)       │
│                      │
│  Key-Value Storage   │
└──────────────────────┘
```

---

## User Journey / Flow Diagrams

### Main User Flow

```mermaid
flowchart TD
    Start([App Launch]) --> Main[MainActivity]
    Main --> Display[Display Total Points]
    Main --> Menu[Show 4 Game Options]
    
    Menu --> DS[Daily Streak]
    Menu --> SW[Spin Wheel]
    Menu --> QZ[Quiz Game]
    Menu --> TC[Tap & Collect]
    
    DS --> DS1{Can Check In?}
    DS1 -->|Yes| DS2[Update Streak]
    DS2 --> DS3[Add Points]
    DS3 --> DS4[Show Success]
    DS1 -->|No| DS5[Show Already Checked In]
    DS4 --> Main
    DS5 --> Main
    
    SW --> SW1{Spins Left?}
    SW1 -->|Yes| SW2[Spin Wheel]
    SW2 --> SW3[Calculate Prize]
    SW3 --> SW4[Add Points]
    SW4 --> SW5[Update Spin Count]
    SW5 --> Main
    SW1 -->|No| SW6[Show No Spins Message]
    SW6 --> Main
    
    QZ --> QZ1{Played Today?}
    QZ1 -->|No| QZ2[Load Questions]
    QZ2 --> QZ3[User Answers]
    QZ3 --> QZ4[Check Answer]
    QZ4 --> QZ5{More Questions?}
    QZ5 -->|Yes| QZ3
    QZ5 -->|No| QZ6[Calculate Score]
    QZ6 --> QZ7[Add Points]
    QZ7 --> QZ8[Mark Quiz Played]
    QZ8 --> Main
    QZ1 -->|Yes| QZ9[Show Already Played]
    QZ9 --> Main
    
    TC --> TC1[Start Timer 30s]
    TC1 --> TC2[User Taps]
    TC2 --> TC3[Add Points Per Tap]
    TC3 --> TC4{Time Left?}
    TC4 -->|Yes| TC2
    TC4 -->|No| TC5[End Game]
    TC5 --> TC6[Add Total Points]
    TC6 --> TC7[Show Score]
    TC7 --> Main
    
    style Main fill:#6C63FF
    style DS fill:#FF6B6B
    style SW fill:#4ECDC4
    style QZ fill:#FFE66D
    style TC fill:#A8E6CF
```

### Daily Streak Flow (Detailed)

```mermaid
sequenceDiagram
    participant U as User
    participant DA as DailyStreakActivity
    participant RM as RewardManager
    participant SP as SharedPreferences
    
    U->>DA: Opens Daily Streak
    DA->>RM: canCheckInToday()
    RM->>SP: Get last_streak_date
    SP-->>RM: Return date string
    RM->>RM: Compare with today
    RM-->>DA: Return boolean
    
    alt Can Check In
        U->>DA: Clicks Check In
        DA->>RM: updateStreak()
        RM->>RM: Calculate streak
        RM->>SP: Save streak & date
        RM->>RM: Get current streak
        DA->>RM: addPoints(streak * 10)
        RM->>SP: Update total_points
        DA-->>U: Show success message
    else Already Checked In
        DA-->>U: Show "Already checked in"
    end
```

### Spin Wheel Flow (Detailed)

```mermaid
sequenceDiagram
    participant U as User
    participant SW as SpinWheelActivity
    participant RM as RewardManager
    participant SP as SharedPreferences
    
    U->>SW: Opens Spin Wheel
    SW->>RM: canSpinToday()
    RM->>SP: Get spins_used_today
    RM->>SP: Get spins_date
    RM->>RM: Compare dates
    RM-->>SW: Return boolean
    
    alt Has Spins Left
        U->>SW: Clicks Spin Button
        SW->>SW: Start Animation (3s)
        SW->>SW: Calculate Prize
        SW->>RM: addPoints(prize)
        RM->>SP: Update total_points
        SW->>RM: useSpin()
        RM->>SP: Update spins_used_today
        RM->>SP: Update spins_date
        SW-->>U: Show Prize Won
    else No Spins Left
        SW-->>U: Show "No spins left"
    end
```

### Quiz Game Flow (Detailed)

```mermaid
flowchart LR
    Start([Start Quiz]) --> Check{Played Today?}
    Check -->|Yes| Blocked[Show Already Played]
    Check -->|No| Load[Load Question]
    
    Load --> Display[Display Question & Options]
    Display --> Answer[User Selects Answer]
    Answer --> Validate{Correct?}
    
    Validate -->|Yes| Correct[Add 20 Points]
    Validate -->|No| Wrong[Show Correct Answer]
    
    Correct --> Next{More Questions?}
    Wrong --> Next
    
    Next -->|Yes| Load
    Next -->|No| Calculate[Calculate Bonus]
    
    Calculate --> AddBonus[Add Bonus Points]
    AddBonus --> MarkPlayed[Mark Quiz Played Today]
    MarkPlayed --> ShowResult[Show Final Score]
    
    Blocked --> End([Return to Main])
    ShowResult --> End
    
    style Start fill:#FFE66D
    style Correct fill:#A8E6CF
    style Wrong fill:#FF6B6B
    style End fill:#6C63FF
```

### Complete User Journey Map

```mermaid
journey
    title User Journey: Sharp Rewards App
    section First Launch
      Open App: 5: User
      View Main Menu: 4: User
      See Zero Points: 3: User
      Choose First Game: 5: User
    section Daily Streak
      Check In: 5: User
      Earn Points: 5: User
      View Streak: 4: User
      Return Tomorrow: 4: User
    section Spin Wheel
      Spin Wheel: 5: User
      Win Points: 5: User
      Use All Spins: 4: User
      Wait for Next Day: 3: User
    section Quiz
      Start Quiz: 5: User
      Answer Questions: 4: User
      Earn Points: 5: User
      Complete Quiz: 5: User
    section Tap & Collect
      Start Tapping: 5: User
      Earn Points: 4: User
      Finish Session: 4: User
      Play Again: 4: User
    section Engagement
      Check Total Points: 4: User
      Try All Games: 5: User
      Build Streak: 5: User
      Daily Return: 4: User
```

### Navigation Flow Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                    APPLICATION LAUNCH                        │
└──────────────────────┬──────────────────────────────────────┘
                       │
                       ▼
              ┌─────────────────┐
              │  MainActivity   │
              │  (Home Screen)  │
              │                 │
              │  • Total Points │
              │  • Game Cards   │
              └────────┬────────┘
                       │
        ┌──────────────┼──────────────┬──────────────┐
        │              │              │              │
        ▼              ▼              ▼              ▼
┌─────────────┐ ┌───────────┐ ┌───────────┐ ┌─────────────┐
│   Daily     │ │   Spin    │ │   Quiz    │ │     Tap     │
│   Streak    │ │   Wheel   │ │   Game    │ │   Collect   │
│             │ │           │ │           │ │             │
│ • Check In  │ │ • Spin    │ │ • 10 Q's  │ │ • 30 sec    │
│ • View      │ │ • Win     │ │ • Points  │ │ • Tap Fast  │
│   Streak    │ │ • 5/day   │ │ • 1/day   │ │ • Unlimited │
└──────┬──────┘ └─────┬─────┘ └─────┬─────┘ └──────┬──────┘
       │              │              │              │
       └──────────────┴──────────────┴──────────────┘
                       │
                       ▼
              ┌─────────────────┐
              │  RewardManager  │
              │  Updates Data   │
              └─────────────────┘
                       │
                       ▼
              ┌─────────────────┐
              │ SharedPreferences│
              │   Save State    │
              └─────────────────┘
                       │
                       ▼
              ┌─────────────────┐
              │  Return to Main │
              │   (Updated)     │
              └─────────────────┘
```

---

## Summary

### Data Storage Architecture
- **Storage Type**: SharedPreferences (Key-Value pairs)
- **Data Persistence**: Local device storage
- **No Database**: Simple key-value storage is sufficient
- **No API**: Fully offline functionality

### User Flow Characteristics
1. **Entry Point**: MainActivity displays all game options
2. **Navigation**: Simple Activity-based navigation
3. **State Management**: Centralized through RewardManager
4. **Data Flow**: Activities → RewardManager → SharedPreferences

### Key Design Decisions
1. **SharedPreferences over Database**: Simple data model doesn't require SQLite
2. **Centralized RewardManager**: Single source of truth for data operations
3. **Daily Limitations**: Implemented for FOMO mechanics
4. **Point System**: Cumulative points tracked across all games

---

## Diagram Legend

### ERD Symbols
- `PK`: Primary Key
- `||--o{`: One-to-Many relationship
- Entities represent logical data groupings

### Flow Diagram Symbols
- Rectangles: Processes/Actions
- Diamonds: Decision points
- Rounded rectangles: Start/End points
- Arrows: Flow direction

---

*Note: This documentation uses Mermaid diagrams which are natively supported by GitHub. The diagrams will render automatically when viewed on GitHub.*
