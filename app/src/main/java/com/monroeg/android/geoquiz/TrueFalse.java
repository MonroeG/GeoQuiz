package com.monroeg.android.geoquiz;

/**
 * Created by MonroeG on 2016/7/4.
 */
public class TrueFalse {

    private int mQuestion;
    private boolean mTrueQuestion;

    public TrueFalse(int Question, boolean TrueQuestion) {
        this.mQuestion = Question;
        this.mTrueQuestion = TrueQuestion;
    }

    public int getQuestion() {
        return mQuestion;
    }

    public void setQuestion(int question) {
        mQuestion = question;
    }

    public boolean isTrueQuestion() {
        return mTrueQuestion;
    }

    public void setTrueQuestion(boolean trueQuestion) {
        mTrueQuestion = trueQuestion;
    }
}
