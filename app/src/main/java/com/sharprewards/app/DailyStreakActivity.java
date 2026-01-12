package com.sharprewards.app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DailyStreakActivity extends AppCompatActivity {

    private TextView streakTextView;
    private TextView pointsEarnedTextView;
    private Button checkInButton;
    private RewardManager rewardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_streak);

        rewardManager = new RewardManager(this);
        
        streakTextView = findViewById(R.id.streakTextView);
        pointsEarnedTextView = findViewById(R.id.pointsEarnedTextView);
        checkInButton = findViewById(R.id.checkInButton);
        
        updateStreakDisplay();
        
        checkInButton.setOnClickListener(v -> performCheckIn());
    }
    
    private void updateStreakDisplay() {
        int streak = rewardManager.getCurrentStreak();
        streakTextView.setText("🔥 Current Streak: " + streak + " days");
        
        boolean canCheckIn = rewardManager.canCheckInToday();
        if (!canCheckIn) {
            pointsEarnedTextView.setText("You've already checked in today! Come back tomorrow!");
            checkInButton.setEnabled(false);
            checkInButton.setText("Checked In ✓");
        } else {
            int nextStreak = streak + 1;
            pointsEarnedTextView.setText("Check in to earn " + (nextStreak * 10) + " points!");
            checkInButton.setEnabled(true);
            checkInButton.setText("Check In Today");
        }
    }
    
    private void performCheckIn() {
        if (!rewardManager.canCheckInToday()) {
            Toast.makeText(this, "You've already checked in today! Come back tomorrow!", Toast.LENGTH_SHORT).show();
            return;
        }
        
        rewardManager.updateStreak();
        int streak = rewardManager.getCurrentStreak();
        int points = streak * 10; // More days = more points (FOMO + Grow)
        
        rewardManager.addPoints(points);
        
        streakTextView.setText("🔥 Current Streak: " + streak + " days");
        pointsEarnedTextView.setText("Earned: " + points + " points!");
        checkInButton.setEnabled(false);
        checkInButton.setText("Checked In ✓");
        
        Toast.makeText(this, "Streak updated! Earned " + points + " points!", Toast.LENGTH_SHORT).show();
    }
}
