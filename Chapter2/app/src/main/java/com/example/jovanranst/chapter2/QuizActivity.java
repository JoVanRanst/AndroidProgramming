package com.example.jovanranst.chapter2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPrevButton;
    private TextView mQuestionTextView;

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
            new Question(R.string.question_australia, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_oceans, true),
    };

    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        updateQuestion();

        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollQuestions(true);
            }
        });

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollQuestions(true);
            }
        });

        mPrevButton = (Button) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollQuestions(false);
            }
        });
    }

    private void scrollQuestions(boolean nextQuestion) {
        if (nextQuestion) {
            mCurrentIndex += 1;
        } else {
            mCurrentIndex -= 1;
        }

        mCurrentIndex %= mQuestionBank.length;
        updateQuestion();
    }

    private void updateQuestion() {
        int QuestionID = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(QuestionID);
    }

    private void checkAnswer(boolean userPressedTrue) {
        int answerID = 0;

        if (userPressedTrue == mQuestionBank[mCurrentIndex].isAnswerTrue()) {
            answerID = R.string.correct_toast;
        } else {
            answerID = R.string.incorrect_toast;
        }

        Toast toast = Toast.makeText(QuizActivity.this, answerID, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.show();
    }
}
