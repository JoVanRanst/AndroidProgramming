package com.example.jovanranst.chapter5;

/**
 * Created by Jo Van Ranst on 07/02/2018.
 */

public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public Question(int textResId, boolean anwserTrue) {
        mTextResId = textResId;
        mAnswerTrue = anwserTrue;
    }
}
