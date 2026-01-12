package com.sharprewards.app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    private TextView questionTextView;
    private Button option1Button, option2Button, option3Button, option4Button;
    private TextView scoreTextView;
    private RewardManager rewardManager;
    
    private String[][] questions = {
        {"What is the capital of France?", "Paris", "London", "Berlin", "Madrid", "Paris"},
        {"Which planet is known as the Red Planet?", "Venus", "Mars", "Jupiter", "Saturn", "Mars"},
        {"What is 2 + 2?", "3", "4", "5", "6", "4"},
        {"Which animal is known as the King of the Jungle?", "Lion", "Tiger", "Elephant", "Bear", "Lion"},
        {"What is the largest ocean?", "Atlantic", "Pacific", "Indian", "Arctic", "Pacific"},
        {"Which year did World War II end?", "1943", "1944", "1945", "1946", "1945"},
        {"What is the chemical symbol for gold?", "Go", "Gd", "Au", "Ag", "Au"},
        {"Which country is home to the kangaroo?", "South Africa", "Australia", "Brazil", "India", "Australia"},
        {"What is the speed of light?", "300,000 km/s", "150,000 km/s", "450,000 km/s", "600,000 km/s", "300,000 km/s"},
        {"Who painted the Mona Lisa?", "Van Gogh", "Picasso", "Leonardo da Vinci", "Michelangelo", "Leonardo da Vinci"}
    };
    
    private int currentQuestionIndex = 0;
    private int correctAnswers = 0;
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        rewardManager = new RewardManager(this);
        random = new Random();
        
        if (!rewardManager.canPlayQuizToday()) {
            setContentView(R.layout.activity_quiz_completed);
            TextView messageTextView = findViewById(R.id.messageTextView);
            messageTextView.setText("You've already played today! Come back tomorrow for a new quiz!");
            return;
        }
        
        questionTextView = findViewById(R.id.questionTextView);
        option1Button = findViewById(R.id.option1Button);
        option2Button = findViewById(R.id.option2Button);
        option3Button = findViewById(R.id.option3Button);
        option4Button = findViewById(R.id.option4Button);
        scoreTextView = findViewById(R.id.scoreTextView);
        
        loadQuestion();
        
        option1Button.setOnClickListener(v -> checkAnswer(option1Button.getText().toString()));
        option2Button.setOnClickListener(v -> checkAnswer(option2Button.getText().toString()));
        option3Button.setOnClickListener(v -> checkAnswer(option3Button.getText().toString()));
        option4Button.setOnClickListener(v -> checkAnswer(option4Button.getText().toString()));
    }
    
    private void loadQuestion() {
        if (currentQuestionIndex >= questions.length) {
            finishQuiz();
            return;
        }
        
        String[] question = questions[currentQuestionIndex];
        questionTextView.setText(question[0]);
        
        // Shuffle options
        String[] options = {question[1], question[2], question[3], question[4]};
        shuffleArray(options);
        
        option1Button.setText(options[0]);
        option2Button.setText(options[1]);
        option3Button.setText(options[2]);
        option4Button.setText(options[3]);
        
        scoreTextView.setText("Question " + (currentQuestionIndex + 1) + "/" + questions.length);
    }
    
    private void checkAnswer(String selectedAnswer) {
        String correctAnswer = questions[currentQuestionIndex][5];
        
        if (selectedAnswer.equals(correctAnswer)) {
            correctAnswers++;
            Toast.makeText(this, "Correct! +20 points", Toast.LENGTH_SHORT).show();
            rewardManager.addPoints(20);
        } else {
            Toast.makeText(this, "Wrong! The answer was: " + correctAnswer, Toast.LENGTH_SHORT).show();
        }
        
        currentQuestionIndex++;
        
        new android.os.Handler().postDelayed(() -> loadQuestion(), 1000);
    }
    
    private void finishQuiz() {
        rewardManager.setQuizPlayedToday();
        
        int finalScore = correctAnswers * 50; // Bonus points
        rewardManager.addPoints(finalScore);
        
        setContentView(R.layout.activity_quiz_completed);
        TextView messageTextView = findViewById(R.id.messageTextView);
        messageTextView.setText("Quiz Complete!\n\nCorrect Answers: " + correctAnswers + "/" + questions.length + 
                              "\nPoints Earned: " + (correctAnswers * 20 + finalScore) + 
                              "\n\nGreat job! Come back tomorrow for more!");
    }
    
    private void shuffleArray(String[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            String temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }
}
