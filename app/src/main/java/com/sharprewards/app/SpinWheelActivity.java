package com.sharprewards.app;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class SpinWheelActivity extends AppCompatActivity {

    private ImageView wheelImageView;
    private Button spinButton;
    private TextView pointsTextView;
    private TextView spinsLeftTextView;
    private RewardManager rewardManager;
    private Random random;
    private boolean isSpinning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spin_wheel);

        rewardManager = new RewardManager(this);
        random = new Random();
        
        wheelImageView = findViewById(R.id.wheelImageView);
        spinButton = findViewById(R.id.spinButton);
        pointsTextView = findViewById(R.id.pointsTextView);
        spinsLeftTextView = findViewById(R.id.spinsLeftTextView);
        
        updateSpinsDisplay();
        updatePointsDisplay();
        
        spinButton.setOnClickListener(v -> spinWheel());
    }
    
    private void updateSpinsDisplay() {
        int spinsUsed = rewardManager.getSpinsUsedToday();
        int spinsLeft = 5 - spinsUsed;
        spinsLeftTextView.setText("Spins Left Today: " + spinsLeft);
        
        if (spinsLeft <= 0) {
            spinButton.setEnabled(false);
            spinButton.setText("No Spins Left!");
            spinsLeftTextView.setText("Come back tomorrow for more spins!");
        }
    }
    
    private void updatePointsDisplay() {
        pointsTextView.setText("Your Points: " + rewardManager.getTotalPoints());
    }
    
    private void spinWheel() {
        if (isSpinning || !rewardManager.canSpinToday()) {
            return;
        }
        
        isSpinning = true;
        spinButton.setEnabled(false);
        
        // Rotate animation
        int spins = 5 + random.nextInt(5); // 5-9 full rotations
        float rotation = spins * 360f + (random.nextInt(360));
        
        ObjectAnimator animator = ObjectAnimator.ofFloat(wheelImageView, "rotation", 0f, rotation);
        animator.setDuration(3000);
        animator.start();
        
        new Handler().postDelayed(() -> {
            int prize = calculatePrize(rotation % 360);
            rewardManager.addPoints(prize);
            rewardManager.useSpin();
            
            updateSpinsDisplay();
            updatePointsDisplay();
            isSpinning = false;
            
            if (rewardManager.canSpinToday()) {
                spinButton.setEnabled(true);
            }
            
            Toast.makeText(this, "🎉 You won " + prize + " points!", Toast.LENGTH_LONG).show();
        }, 3000);
    }
    
    private int calculatePrize(float angle) {
        // Divide wheel into 8 segments with different prizes
        int segment = (int)(angle / 45);
        
        // Prize distribution (Gen Z friendly - mix of small and big wins for FOMO)
        int[] prizes = {50, 25, 100, 30, 200, 40, 150, 75};
        return prizes[segment % prizes.length];
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        updateSpinsDisplay();
        updatePointsDisplay();
    }
}
