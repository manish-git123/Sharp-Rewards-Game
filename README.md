# Sharp Rewards - Android Application

A Gen Z-focused rewards application featuring engaging games that utilize FOMO (Fear Of Missing Out), Time to Earn, and Growth mechanics.

## 📚 Documentation

- **[ARCHITECTURE.md](ARCHITECTURE.md)** - Complete architecture documentation including ERD, DRD, and User Journey diagrams
- **[DIAGRAMS.md](DIAGRAMS.md)** - Visual diagrams in text format for presentations
- **[PROJECT_SUMMARY.md](PROJECT_SUMMARY.md)** - Technical project summary
- **[SETUP_GUIDE.txt](SETUP_GUIDE.txt)** - Quick setup instructions

## Game Features

### 🔥 Daily Streak
- Check in daily to build your streak
- Longer streaks = More points (Growth mechanic)
- FOMO: Missing a day breaks your streak
- Earn 10 points per day of streak

### 🎰 Spin Wheel
- 5 spins per day (FOMO)
- Win random rewards (25-200 points)
- Time-based limitation encourages daily return

### 🧠 Quiz Game
- Test your knowledge with 10 questions
- One quiz per day (FOMO)
- Earn 20 points per correct answer + bonus
- Growth: Improve your knowledge over time

### 👆 Tap & Collect
- 30-second time-based game
- Tap as fast as you can to earn points
- Immediate rewards (Time to Earn)
- Unlimited plays (but time-limited sessions)

## Setup Instructions for Android Studio

### Prerequisites
1. **Android Studio** (latest version recommended - Hedgehog | 2023.1.1 or later)
2. **JDK 8 or higher** (JDK 11+ recommended)
3. **Android SDK** (API Level 24 or higher)

### Step-by-Step Setup

1. **Open Android Studio**
   - Launch Android Studio on your computer

2. **Open the Project**
   - Click on `File` → `Open`
   - Navigate to the `SharpRewardsApp` folder
   - Select the folder and click `OK`
   - Android Studio will automatically sync Gradle files (this may take a few minutes on first open)

3. **Wait for Gradle Sync**
   - Android Studio will download necessary dependencies automatically
   - Wait for the sync to complete (check the bottom status bar)
   - If sync fails, click `Sync Now` or `Retry`

4. **Configure SDK (if needed)**
   - Go to `File` → `Project Structure` → `SDK Location`
   - Ensure Android SDK is properly configured
   - If not set, click `Edit` and specify your SDK location

5. **Create AVD (Android Virtual Device) - For Emulator**
   - Click on `Tools` → `Device Manager`
   - Click `Create Device`
   - Select a device (e.g., Pixel 5)
   - Select a system image (API 34 recommended)
   - Click `Finish`

6. **Run the Application**
   - Connect an Android device via USB (with USB debugging enabled) OR
   - Start an emulator from Device Manager
   - Click the green `Run` button (▶) or press `Shift + F10`
   - Select your device/emulator
   - The app will build and install automatically

### Build Configuration

- **Minimum SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)
- **Compile SDK**: 34
- **Language**: Java

### Dependencies (Automatically Included)

- AndroidX AppCompat
- Material Design Components
- ConstraintLayout
- CardView

## Project Structure

```
SharpRewardsApp/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/sharprewards/app/
│   │       │   ├── MainActivity.java
│   │       │   ├── DailyStreakActivity.java
│   │       │   ├── SpinWheelActivity.java
│   │       │   ├── QuizActivity.java
│   │       │   ├── TapCollectActivity.java
│   │       │   └── RewardManager.java
│   │       ├── res/
│   │       │   ├── layout/
│   │       │   ├── values/
│   │       │   └── drawable/
│   │       └── AndroidManifest.xml
│   └── build.gradle
├── build.gradle
├── settings.gradle
└── README.md
```

## Key Features Implementation

### FOMO (Fear Of Missing Out)
- Daily limitations on all games
- Streak-based rewards that break if missed
- Limited spins per day
- One quiz per day

### Gen Z Appeal
- Modern UI with Material Design
- Emoji usage in UI
- Gamification elements
- Quick, engaging gameplay

### Time to Earn
- All games reward time investment
- Immediate feedback and rewards
- Progress tracking
- Session-based earning

### Growth
- Increasing streak rewards
- Progress tracking
- Cumulative point system
- Skill improvement (quiz knowledge)

## Data Storage

The application uses `SharedPreferences` for local data storage. No API keys or external services are required.

**Stored Data:**
- Total points
- Current streak
- Last check-in date
- Daily game usage limits
- Quiz completion status

## Troubleshooting

### Gradle Sync Issues
- Ensure you have a stable internet connection
- Go to `File` → `Invalidate Caches` → `Invalidate and Restart`
- Check if you have sufficient disk space

### Build Errors
- Clean the project: `Build` → `Clean Project`
- Rebuild: `Build` → `Rebuild Project`
- Ensure JDK is properly configured in `File` → `Project Structure` → `SDK Location`

### Emulator Issues
- Ensure HAXM or Hyper-V is enabled (for Windows)
- Allocate at least 2GB RAM to emulator
- Use x86_64 system images for better performance

### Runtime Errors
- Ensure minimum SDK 24 is supported on your device/emulator
- Check logcat for detailed error messages
- Verify all permissions are granted if needed

## Notes

- **No API Keys Required**: All functionality works offline
- **Local Storage Only**: Data is stored on device using SharedPreferences
- **No Internet Required**: Fully functional offline application

## License

This project is created for educational purposes.

## Contact

## 🎥 Demo Video
Download and watch the project demo here:  
https://github.com/manish123/SharpRewardsApp/releases


For issues or questions, please refer to the project repository.
