package com.example.jovanranst.chapter5;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_ANSWERED = "answered";
    private static final String KEY_ANSWERS = "answers";
    private static final String KEY_CHEATED = "cheated";

    private static final int REQUEST_CODE_CHEAT = 0;

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPrevButton;
    private Button mCheatButton;

    private TextView mQuestionTextView;

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
            new Question(R.string.question_australia, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_oceans, true),
    };

    private int mAnswers[] = {0,0,0,0,0,0};

    private int mCurrentIndex = 0;
    private boolean mIsCheater;
    private boolean mAlreadyPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState != null) {
            mCurrentIndex =savedInstanceState.getInt(KEY_INDEX, 0);
            mIsCheater = savedInstanceState.getBoolean(KEY_CHEATED);
            mAlreadyPressed = savedInstanceState.getBoolean(KEY_ANSWERED);
            mAnswers = savedInstanceState.getIntArray(KEY_ANSWERS);
        }

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

        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start CheatActivity
                Intent intent = CheatActivity.newIntent(QuizActivity.this, mQuestionBank[mCurrentIndex].isAnswerTrue());
                //startActivity(intent);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        Log.i(TAG, "onSaveInstanceState(Bundle) called");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBoolean(KEY_ANSWERED, mAlreadyPressed);
        savedInstanceState.putBoolean(KEY_CHEATED, mIsCheater);
        savedInstanceState.putIntArray(KEY_ANSWERS, mAnswers);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    private void scrollQuestions(boolean nextQuestion) {
        mAlreadyPressed = false;

        if (nextQuestion) {
            mCurrentIndex += 1;
        } else {
            mCurrentIndex -= 1;
        }

        if (mCurrentIndex < 0) {
            mCurrentIndex = mQuestionBank.length-1;
        } else {
            mCurrentIndex %= mQuestionBank.length;
        }
        updateQuestion();
    }

    private void updateQuestion() {
        int QuestionID = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(QuestionID);
    }

    private boolean allQuestionsAnswered() {
        for (int i=0; i<6; i++) {
            if (mAnswers[i] == 0) {
                return false;
            }
        }
        return true;
    }

    private void printScore() {
        int total = 0;

        for (int i=0; i<6; i++) {
            if (mAnswers[i] == 1) {
                total++;
            }
        }

        StringBuilder text = new StringBuilder();
        text.append("You had this question ");
        text.append(mAnswers[mCurrentIndex] == 1?"Correct":"Incorrect");
        text.append(". \nMaking your total score ");
        text.append(total);
        text.append(" out of 6");

        Toast toast = Toast.makeText(QuizActivity.this, text.toString(), Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.show();
    }

    private void checkAnswer(boolean userPressedTrue) {
        int answerID = 0;

        if(mAnswers[mCurrentIndex] == 0) {
            if (userPressedTrue == mQuestionBank[mCurrentIndex].isAnswerTrue()) {
                if (mIsCheater) {
                    answerID = R.string.judgment_toast;
                } else {
                    answerID = R.string.correct_toast;
                }
                mAnswers[mCurrentIndex] = 1;
            } else {
                if (mIsCheater) {
                    answerID = R.string.incompetent_toast;
                } else {
                    answerID = R.string.incorrect_toast;
                }
                mAnswers[mCurrentIndex] = -1;
            }

            if (!allQuestionsAnswered()) {
                if (!mAlreadyPressed) {
                    Toast toast = Toast.makeText(QuizActivity.this, answerID, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP, 0, 0);
                    toast.show();
                    mAlreadyPressed = true;
                    mIsCheater = false;
                } else {
                    // Already gave an answer
                }
            } else {
                // Print score toast
                printScore();
                mAlreadyPressed = true;
            }
        } else {
            // Already gave an answer
            if (allQuestionsAnswered()) {
                printScore();
            } else {
                /// Maybe tell them their prev answer
            }
        }
    }
}
