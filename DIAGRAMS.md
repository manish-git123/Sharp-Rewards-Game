# Visual Diagrams - Sharp Rewards App

This document contains all visual diagrams in text format for easy reference and inclusion in presentations.

---

## 1. Entity Relationship Diagram (ERD)

### Simplified Data Model

```
                    USER ENTITY
    ┌──────────────────────────────────────────┐
    │  Attributes:                             │
    │  • totalPoints (int)                     │
    │  • currentStreak (int)                   │
    │  • lastCheckInDate (string)              │
    │  • spinsUsedToday (int)                  │
    │  • lastSpinDate (string)                 │
    │  • quizPlayedToday (boolean)             │
    │  • lastQuizDate (string)                 │
    └──────────────────────────────────────────┘
            │         │         │         │
            │         │         │         │
      ┌─────┴───┐ ┌───┴───┐ ┌───┴───┐ ┌──┴────┐
      │  DAILY  │ │ SPIN  │ │ QUIZ  │ │  TAP  │
      │ STREAK  │ │ WHEEL │ │ GAME  │ │COLLECT│
      ├─────────┤ ├───────┤ ├───────┤ ├───────┤
      │streak   │ │spins  │ │played │ │points │
      │count    │ │used   │ │today  │ │earned │
      │lastDate │ │max=5  │ │1/day  │ │per tap│
      └─────────┘ └───────┘ └───────┘ └───────┘
```

---

## 2. System Architecture Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                      PRESENTATION LAYER                      │
├─────────────────────────────────────────────────────────────┤
│                                                               │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐     │
│  │   Main       │  │   Daily      │  │   Spin       │     │
│  │  Activity    │  │   Streak     │  │   Wheel      │     │
│  │              │  │   Activity   │  │   Activity   │     │
│  └──────────────┘  └──────────────┘  └──────────────┘     │
│                                                               │
│  ┌──────────────┐  ┌──────────────┐                        │
│  │   Quiz       │  │   Tap        │                        │
│  │   Activity   │  │   Collect    │                        │
│  │              │  │   Activity   │                        │
│  └──────────────┘  └──────────────┘                        │
│                                                               │
└───────────────────────┬─────────────────────────────────────┘
                        │
                        │ Uses
                        │
┌───────────────────────▼─────────────────────────────────────┐
│                    BUSINESS LOGIC LAYER                      │
├─────────────────────────────────────────────────────────────┤
│                                                               │
│              ┌──────────────────────┐                       │
│              │   RewardManager      │                       │
│              │                      │                       │
│              │  • getTotalPoints()  │                       │
│              │  • addPoints()       │                       │
│              │  • updateStreak()    │                       │
│              │  • canSpinToday()    │                       │
│              │  • canPlayQuizToday()│                       │
│              │  • useSpin()         │                       │
│              └──────────────────────┘                       │
│                                                               │
└───────────────────────┬─────────────────────────────────────┘
                        │
                        │ Stores/Retrieves
                        │
┌───────────────────────▼─────────────────────────────────────┐
│                      DATA LAYER                              │
├─────────────────────────────────────────────────────────────┤
│                                                               │
│           ┌──────────────────────────┐                      │
│           │   SharedPreferences      │                      │
│           │   "SharpRewardsPrefs"    │                      │
│           │                          │                      │
│           │  Key-Value Storage:      │                      │
│           │  • total_points          │                      │
│           │  • current_streak        │                      │
│           │  • last_streak_date      │                      │
│           │  • spins_used_today      │                      │
│           │  • spins_date            │                      │
│           │  • quiz_played_today     │                      │
│           │  • quiz_date             │                      │
│           └──────────────────────────┘                      │
│                                                               │
└─────────────────────────────────────────────────────────────┘
```

---

## 3. User Journey Flow

### Complete User Journey

```
START
  │
  ▼
┌─────────────────────┐
│  Launch Application │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│   MainActivity      │
│  ┌───────────────┐  │
│  │ Total Points  │  │
│  └───────────────┘  │
│  ┌───────────────┐  │
│  │ Game Options  │  │
│  │               │  │
│  │ [Daily Streak]│  │
│  │ [Spin Wheel]  │  │
│  │ [Quiz Game]   │  │
│  │ [Tap Collect] │  │
│  └───────────────┘  │
└──────────┬──────────┘
           │
     ┌─────┴─────┬──────────┬──────────┐
     │           │          │          │
     ▼           ▼          ▼          ▼
┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐
│  Daily  │ │  Spin   │ │  Quiz   │ │   Tap   │
│ Streak  │ │ Wheel   │ │ Game    │ │ Collect │
└────┬────┘ └────┬────┘ └────┬────┘ └────┬────┘
     │           │          │          │
     │           │          │          │
     ▼           ▼          ▼          ▼
┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐
│ Check In│ │  Spin   │ │ Answer  │ │   Tap   │
│         │ │  Wheel  │ │ Question│ │  Button │
│ Earn    │ │         │ │         │ │         │
│ Points  │ │ Win     │ │ Earn    │ │ Earn    │
│         │ │ Points  │ │ Points  │ │ Points  │
└────┬────┘ └────┬────┘ └────┬────┘ └────┬────┘
     │           │          │          │
     └───────────┴──────────┴──────────┘
                 │
                 ▼
        ┌────────────────┐
        │ RewardManager  │
        │  Updates Data  │
        └────────┬───────┘
                 │
                 ▼
        ┌────────────────┐
        │SharedPreferences│
        │   Save State   │
        └────────┬───────┘
                 │
                 ▼
        ┌────────────────┐
        │ Return to Main │
        │  (Points Updated)│
        └────────────────┘
```

---

## 4. Daily Streak Flow

```
User Opens Daily Streak
          │
          ▼
    ┌──────────┐
    │  Check   │
    │  if can  │
    │  check in│
    └────┬─────┘
         │
    ┌────┴────┐
    │         │
    ▼         ▼
┌───────┐ ┌──────────┐
│  YES  │ │    NO    │
└───┬───┘ └────┬─────┘
    │          │
    │          ▼
    │    ┌──────────┐
    │    │  Show    │
    │    │ "Already │
    │    │  Checked │
    │    │  In"     │
    │    └──────────┘
    │
    ▼
┌──────────┐
│  Update  │
│  Streak  │
└────┬─────┘
     │
     ▼
┌──────────┐
│ Calculate│
│  Points  │
│(streak×10)│
└────┬─────┘
     │
     ▼
┌──────────┐
│ Add to   │
│  Total   │
└────┬─────┘
     │
     ▼
┌──────────┐
│  Show    │
│ Success  │
└──────────┘
```

---

## 5. Spin Wheel Flow

```
User Opens Spin Wheel
          │
          ▼
    ┌──────────┐
    │  Check   │
    │  Spins   │
    │  Left    │
    └────┬─────┘
         │
    ┌────┴────┐
    │         │
    ▼         ▼
┌───────┐ ┌──────────┐
│  YES  │ │    NO    │
│(< 5)  │ │  (≥ 5)   │
└───┬───┘ └────┬─────┘
    │          │
    │          ▼
    │    ┌──────────┐
    │    │  Show    │
    │    │ "No      │
    │    │  Spins   │
    │    │  Left"   │
    │    └──────────┘
    │
    ▼
┌──────────┐
│  User    │
│  Clicks  │
│  Spin    │
└────┬─────┘
     │
     ▼
┌──────────┐
│  Start   │
│ Animation│
│  (3 sec) │
└────┬─────┘
     │
     ▼
┌──────────┐
│ Calculate│
│  Prize   │
│ (25-200) │
└────┬─────┘
     │
     ▼
┌──────────┐
│ Add      │
│ Points   │
└────┬─────┘
     │
     ▼
┌──────────┐
│  Update  │
│ Spin Count│
└────┬─────┘
     │
     ▼
┌──────────┐
│  Show    │
│  Prize   │
└──────────┘
```

---

## 6. Quiz Game Flow

```
User Opens Quiz
          │
          ▼
    ┌──────────┐
    │  Check   │
    │  if      │
    │  played  │
    │  today   │
    └────┬─────┘
         │
    ┌────┴────┐
    │         │
    ▼         ▼
┌───────┐ ┌──────────┐
│  NO   │ │   YES    │
└───┬───┘ └────┬─────┘
    │          │
    │          ▼
    │    ┌──────────┐
    │    │  Show    │
    │    │ "Already │
    │    │  Played" │
    │    └──────────┘
    │
    ▼
┌──────────┐
│  Load    │
│ Question │
│  (1-10)  │
└────┬─────┘
     │
     ▼
┌──────────┐
│  User    │
│ Selects  │
│  Answer  │
└────┬─────┘
     │
     ▼
┌──────────┐
│  Check   │
│  Answer  │
└────┬─────┘
     │
 ┌───┴───┐
 │       │
 ▼       ▼
┌─────┐ ┌─────┐
│ YES │ │ NO  │
│ +20 │ │ +0  │
│pts  │ │pts  │
└──┬──┘ └──┬──┘
   │       │
   └───┬───┘
       │
       ▼
┌──────────┐
│  More    │
│Questions?│
└────┬─────┘
     │
 ┌───┴───┐
 │       │
 ▼       ▼
┌─────┐ ┌──────────┐
│ YES │ │    NO    │
│(1-9)│ │  (10/10) │
└──┬──┘ └────┬─────┘
   │         │
   │         ▼
   │    ┌──────────┐
   │    │ Calculate│
   │    │  Bonus   │
   │    └────┬─────┘
   │         │
   │         ▼
   │    ┌──────────┐
   │    │   Mark   │
   │    │  Played  │
   │    └────┬─────┘
   │         │
   │         ▼
   │    ┌──────────┐
   │    │   Show   │
   │    │   Score  │
   │    └──────────┘
   │
   └─────────┘
   (Loop back)
```

---

## 7. Tap & Collect Flow

```
User Opens Tap & Collect
          │
          ▼
    ┌──────────┐
    │  User    │
    │  Clicks  │
    │ "START"  │
    └────┬─────┘
         │
         ▼
    ┌──────────┐
    │  Start   │
    │  Timer   │
    │ (30 sec) │
    └────┬─────┘
         │
         ▼
    ┌──────────┐
    │  User    │
    │  Taps    │
    │  Button  │
    └────┬─────┘
         │
         ▼
    ┌──────────┐
    │  Add     │
    │  Points  │
    │ (1-5 pts)│
    └────┬─────┘
         │
         ▼
    ┌──────────┐
    │  Update  │
    │  Display │
    └────┬─────┘
         │
         ▼
    ┌──────────┐
    │  Time    │
    │  Left?   │
    └────┬─────┘
         │
    ┌────┴────┐
    │         │
    ▼         ▼
┌───────┐ ┌──────────┐
│  YES  │ │    NO    │
│(> 0s) │ │  (= 0s)  │
└───┬───┘ └────┬─────┘
    │          │
    │          ▼
    │    ┌──────────┐
    │    │   End    │
    │    │   Game   │
    │    └────┬─────┘
    │         │
    │         ▼
    │    ┌──────────┐
    │    │   Add    │
    │    │  Total   │
    │    │  Points  │
    │    └────┬─────┘
    │         │
    │         ▼
    │    ┌──────────┐
    │    │   Show   │
    │    │   Score  │
    │    └──────────┘
    │
    └─────────┘
    (Loop back)
```

---

## 8. Data Flow Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                    DATA FLOW DIAGRAM                         │
└─────────────────────────────────────────────────────────────┘

    USER ACTION
         │
         ▼
┌─────────────────┐
│   Activity      │
│  (UI Component) │
└────────┬────────┘
         │
         │ Calls Method
         │
┌────────▼────────────────────────┐
│      RewardManager              │
│                                 │
│  Method: addPoints()            │
│  Method: updateStreak()         │
│  Method: canSpinToday()         │
│  Method: getTotalPoints()       │
└────────┬────────────────────────┘
         │
         │ Read/Write
         │
┌────────▼────────────────────────┐
│    SharedPreferences            │
│    "SharpRewardsPrefs"          │
│                                 │
│    Key-Value Operations:        │
│    • getInt()                   │
│    • putInt()                   │
│    • getString()                │
│    • putString()                │
│    • getBoolean()               │
│    • putBoolean()               │
│    • apply()                    │
└────────┬────────────────────────┘
         │
         │ Persistent Storage
         │
┌────────▼────────────────────────┐
│    Device Storage               │
│    (Local File System)          │
└─────────────────────────────────┘
```

---

## 9. State Management Flow

```
APP STATE MANAGEMENT

┌─────────────────────────────────────────────────────────┐
│                  APPLICATION STATE                       │
├─────────────────────────────────────────────────────────┤
│                                                          │
│  Points State:                                           │
│  ┌─────────────────────────┐                            │
│  │ totalPoints: int        │                            │
│  └─────────────────────────┘                            │
│                                                          │
│  Streak State:                                           │
│  ┌─────────────────────────┐                            │
│  │ currentStreak: int      │                            │
│  │ lastCheckInDate: string │                            │
│  └─────────────────────────┘                            │
│                                                          │
│  Spin Wheel State:                                       │
│  ┌─────────────────────────┐                            │
│  │ spinsUsedToday: int     │                            │
│  │ lastSpinDate: string    │                            │
│  └─────────────────────────┘                            │
│                                                          │
│  Quiz State:                                             │
│  ┌─────────────────────────┐                            │
│  │ quizPlayedToday: boolean│                            │
│  │ lastQuizDate: string    │                            │
│  └─────────────────────────┘                            │
│                                                          │
└─────────────────────────────────────────────────────────┘
                        │
                        │ Managed By
                        │
┌───────────────────────▼───────────────────────────────────┐
│              RewardManager (Singleton-like)                │
│                                                            │
│  • Centralized state management                           │
│  • Thread-safe operations                                 │
│  • Automatic persistence                                  │
│  • Date-based validation                                  │
└───────────────────────────────────────────────────────────┘
```

---

## Diagram Formats

All diagrams are provided in:
1. **Mermaid format** (for GitHub rendering) - See ARCHITECTURE.md
2. **ASCII/text format** (for presentations) - This document
3. **Descriptive format** (for documentation)

---

*These diagrams can be used in documentation, presentations, or converted to visual formats using tools like draw.io, Lucidchart, or Mermaid live editor.*
