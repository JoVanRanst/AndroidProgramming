package com.example.jovanranst.chapter5;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE = "com.example.jovanranst.chapter5.answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = "com.example.jovanranst.chapter5.answer_shown";

    private static final String KEY_CHEATED = "cheated";

    private boolean mAnswerIsTrue;
    private boolean mHasCheated = false;

    private TextView mAswerTextView;
    private Button mShowAnswerButton;

    public static boolean wasAnswerShown(Intent intent) {
        return intent.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        if (savedInstanceState != null) {
            mHasCheated = savedInstanceState.getBoolean(KEY_CHEATED);
            setAnswerShownResult();
        }

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAswerTextView = (TextView) findViewById(R.id.answer_text_view);

        mShowAnswerButton = (Button) findViewById(R.id.show_answer_button);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHasCheated = true;
                if (mAnswerIsTrue) {
                    mAswerTextView.setText(R.string.true_button);
                } else {
                    mAswerTextView.setText(R.string.false_button);
                }
                setAnswerShownResult();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(KEY_CHEATED, mHasCheated);
    }

    private void setAnswerShownResult() {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, mHasCheated);
        setResult(RESULT_OK, data);
    }

    public static Intent newIntent(Context contextPackage, boolean answerIsTrue) {
        Intent intent = new Intent(contextPackage, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }
}
