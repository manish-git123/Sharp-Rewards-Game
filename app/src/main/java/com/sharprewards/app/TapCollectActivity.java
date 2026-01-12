package com.sharprewards.app;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class TapCollectActivity extends AppCompatActivity {

    private Button tapButton;
    private TextView scoreTextView;
    private TextView timerTextView;
    private TextView highScoreTextView;
    private RewardManager rewardManager;
    
    private int tapCount = 0;
    private int sessionPoints = 0;
    private CountDownTimer timer;
    private boolean gameActive = false;
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tap_collect);

        rewardManager = new RewardManager(this);
        random = new Random();
        
        tapButton = findViewById(R.id.tapButton);
        scoreTextView = findViewById(R.id.scoreTextView);
        timerTextView = findViewById(R.id.timerTextView);
        highScoreTextView = findViewById(R.id.highScoreTextView);
        
        updateDisplay();
        
        tapButton.setOnClickListener(v -> startGame());
    }
    
    private void startGame() {
        if (gameActive) {
            onTap();
            return;
        }
        
        gameActive = true;
        tapCount = 0;
        sessionPoints = 0;
        tapButton.setText("TAP FAST!");
        
        timer = new CountDownTimer(30000, 1000) { // 30 seconds
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText("Time: " + millisUntilFinished / 1000 + "s");
            }
            
            @Override
            public void onFinish() {
                endGame();
            }
        }.start();
    }
    
    private void onTap() {
        if (!gameActive) return;
        
        tapCount++;
        int pointsEarned = 1 + random.nextInt(5); // 1-5 points per tap (Time to Earn)
        sessionPoints += pointsEarned;
        
        updateDisplay();
        
        // Visual feedback
        tapButton.setScaleX(0.95f);
        tapButton.setScaleY(0.95f);
        tapButton.animate().scaleX(1.0f).scaleY(1.0f).setDuration(100).start();
    }
    
    private void endGame() {
        gameActive = false;
        timer.cancel();
        
        rewardManager.addPoints(sessionPoints);
        
        tapButton.setText("Play Again");
        timerTextView.setText("Time's Up!");
        
        Toast.makeText(this, "Game Over! Earned " + sessionPoints + " points!", Toast.LENGTH_LONG).show();
    }
    
    private void updateDisplay() {
        scoreTextView.setText("Taps: " + tapCount + "\nPoints: " + sessionPoints);
        highScoreTextView.setText("Total Points: " + rewardManager.getTotalPoints());
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }
}
