package com.sharprewards.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    private TextView pointsTextView;
    private RewardManager rewardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rewardManager = new RewardManager(this);
        
        pointsTextView = findViewById(R.id.pointsTextView);
        updatePointsDisplay();
        
        // Game buttons
        CardView dailyStreakCard = findViewById(R.id.dailyStreakCard);
        CardView spinWheelCard = findViewById(R.id.spinWheelCard);
        CardView quizCard = findViewById(R.id.quizCard);
        CardView tapCollectCard = findViewById(R.id.tapCollectCard);
        
        dailyStreakCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DailyStreakActivity.class);
            startActivity(intent);
        });
        
        spinWheelCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SpinWheelActivity.class);
            startActivity(intent);
        });
        
        quizCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, QuizActivity.class);
            startActivity(intent);
        });
        
        tapCollectCard.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TapCollectActivity.class);
            startActivity(intent);
        });
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        updatePointsDisplay();
    }
    
    private void updatePointsDisplay() {
        int points = rewardManager.getTotalPoints();
        pointsTextView.setText("Total Points: " + points);
    }
}
