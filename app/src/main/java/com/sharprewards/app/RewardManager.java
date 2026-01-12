package com.sharprewards.app;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Calendar;

public class RewardManager {
    private static final String PREFS_NAME = "SharpRewardsPrefs";
    private static final String KEY_TOTAL_POINTS = "total_points";
    private static final String KEY_LAST_STREAK_DATE = "last_streak_date";
    private static final String KEY_CURRENT_STREAK = "current_streak";
    private static final String KEY_SPINS_USED_TODAY = "spins_used_today";
    private static final String KEY_SPINS_DATE = "spins_date";
    private static final String KEY_QUIZ_PLAYED_TODAY = "quiz_played_today";
    private static final String KEY_QUIZ_DATE = "quiz_date";
    
    private SharedPreferences prefs;
    private Context context;
    
    public RewardManager(Context context) {
        this.context = context;
        this.prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
    
    public int getTotalPoints() {
        return prefs.getInt(KEY_TOTAL_POINTS, 0);
    }
    
    public void addPoints(int points) {
        int currentPoints = getTotalPoints();
        prefs.edit().putInt(KEY_TOTAL_POINTS, currentPoints + points).apply();
    }
    
    public int getCurrentStreak() {
        return prefs.getInt(KEY_CURRENT_STREAK, 0);
    }
    
    public boolean canCheckInToday() {
        Calendar today = Calendar.getInstance();
        String lastDateStr = prefs.getString(KEY_LAST_STREAK_DATE, "");
        
        if (lastDateStr.isEmpty()) {
            return true;
        }
        
        return !lastDateStr.equals(getDateString(today));
    }
    
    public void updateStreak() {
        Calendar today = Calendar.getInstance();
        Calendar lastStreakDate = Calendar.getInstance();
        
        String lastDateStr = prefs.getString(KEY_LAST_STREAK_DATE, "");
        if (!lastDateStr.isEmpty()) {
            String[] parts = lastDateStr.split("-");
            lastStreakDate.set(Integer.parseInt(parts[0]), 
                             Integer.parseInt(parts[1]), 
                             Integer.parseInt(parts[2]));
        } else {
            lastStreakDate.add(Calendar.DAY_OF_YEAR, -2);
        }
        
        long daysDiff = (today.getTimeInMillis() - lastStreakDate.getTimeInMillis()) 
                       / (1000 * 60 * 60 * 24);
        
        int currentStreak = prefs.getInt(KEY_CURRENT_STREAK, 0);
        
        if (daysDiff == 0) {
            // Already checked in today
            return;
        } else if (daysDiff == 1) {
            // Consecutive day
            currentStreak++;
        } else {
            // Streak broken
            currentStreak = 1;
        }
        
        String todayStr = getDateString(today);
        
        prefs.edit()
            .putInt(KEY_CURRENT_STREAK, currentStreak)
            .putString(KEY_LAST_STREAK_DATE, todayStr)
            .apply();
    }
    
    public boolean canSpinToday() {
        Calendar today = Calendar.getInstance();
        String lastSpinDate = prefs.getString(KEY_SPINS_DATE, "");
        int spinsUsed = prefs.getInt(KEY_SPINS_USED_TODAY, 0);
        
        if (lastSpinDate.isEmpty() || !lastSpinDate.equals(getDateString(today))) {
            return true;
        }
        
        return spinsUsed < 5; // 5 spins per day
    }
    
    public void useSpin() {
        Calendar today = Calendar.getInstance();
        String todayStr = getDateString(today);
        String lastSpinDate = prefs.getString(KEY_SPINS_DATE, "");
        
        int spinsUsed = 0;
        if (todayStr.equals(lastSpinDate)) {
            spinsUsed = prefs.getInt(KEY_SPINS_USED_TODAY, 0);
        }
        
        prefs.edit()
            .putInt(KEY_SPINS_USED_TODAY, spinsUsed + 1)
            .putString(KEY_SPINS_DATE, todayStr)
            .apply();
    }
    
    public boolean canPlayQuizToday() {
        Calendar today = Calendar.getInstance();
        String lastQuizDate = prefs.getString(KEY_QUIZ_DATE, "");
        boolean playedToday = prefs.getBoolean(KEY_QUIZ_PLAYED_TODAY, false);
        
        if (lastQuizDate.isEmpty() || !lastQuizDate.equals(getDateString(today))) {
            return true;
        }
        
        return !playedToday; // One quiz per day
    }
    
    public void setQuizPlayedToday() {
        Calendar today = Calendar.getInstance();
        prefs.edit()
            .putBoolean(KEY_QUIZ_PLAYED_TODAY, true)
            .putString(KEY_QUIZ_DATE, getDateString(today))
            .apply();
    }
    
    private String getDateString(Calendar calendar) {
        return calendar.get(Calendar.YEAR) + "-" + 
               calendar.get(Calendar.MONTH) + "-" + 
               calendar.get(Calendar.DAY_OF_MONTH);
    }
    
    public int getSpinsUsedToday() {
        Calendar today = Calendar.getInstance();
        String todayStr = getDateString(today);
        String lastSpinDate = prefs.getString(KEY_SPINS_DATE, "");
        
        if (todayStr.equals(lastSpinDate)) {
            return prefs.getInt(KEY_SPINS_USED_TODAY, 0);
        }
        return 0;
    }
}
