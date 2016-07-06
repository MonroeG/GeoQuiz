package com.monroeg.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends Activity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_ISCHEATER = "isCheater";

    //添加成员变量
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mCheatButton;
    private Button mPreviousButton;
    private Button mNextButton;
    private TextView mQuestionTextView;
    private TrueFalse[] mQuestionBank = new TrueFalse[] {
            new TrueFalse(R.string.question_oceans, true),
            new TrueFalse(R.string.question_africa, false),
            new TrueFalse(R.string.question_mideast, false),
            new TrueFalse(R.string.question_americas, true),
            new TrueFalse(R.string.question_asia, true)
    };
    //记录数组下标
    private int mCurrentIndex = 0;
    private boolean mIsCheater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "QuizActivity onCreate()");
        setContentView(R.layout.activity_quiz);

        //判断并取回保存的mCurrentIndex变量值
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            Log.i(TAG, "取回保存的mCurrentIndex的变量值为：" + mCurrentIndex);
            mIsCheater = savedInstanceState.getBoolean(KEY_ISCHEATER);
            Log.i(TAG, "取回保存的mIsCheater的变量值为：" + mIsCheater);
        }

        //引用TextView组件并设置监听器
        mQuestionTextView = (TextView)findViewById(R.id.question_text_view);
        updateQuestion();
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        //引用按钮组件并设置监听器
        mTrueButton = (Button)findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button)findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(QuizActivity.this, CheatActivity.class);
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();
                //将extra信息附加到intent上
                i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
                //startActivity(i);
                //若需要从子Activity获取返回信息，调用以下方法
                startActivityForResult(i, 1);
            }
        });

        //引用新增的previous按钮并设置监听器
        mPreviousButton = (Button)findViewById(R.id.previous_button);
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex--;
                while (mCurrentIndex < 0) {
                    Toast.makeText(QuizActivity.this,
                            "已经是第一个问题",
                            Toast.LENGTH_SHORT).show();
                    mCurrentIndex = 0;
                }
                updateQuestion();
            }
        });

        //引用新增的Next按钮并设置监听器
        mNextButton = (Button)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex++;
                while (mCurrentIndex == mQuestionBank.length) {
                    Toast.makeText(QuizActivity.this,
                            "已经是最后一个问题",
                            Toast.LENGTH_SHORT).show();
                    mCurrentIndex = mQuestionBank.length - 1;
                }
                updateQuestion();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "覆盖onSaveInstanceState方法来保存mCurrentIndex变量值：" + mCurrentIndex);
        outState.putInt(KEY_INDEX, mCurrentIndex);
        outState.putBoolean(KEY_ISCHEATER, mIsCheater);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "QuizActivity onStart()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "QuizActivity onRestart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "QuizActivity onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "QuizActivity onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "QuizActivity onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "QuizActivity onDestroy()");
    }

    /**
     * 更新问题的方法
     */
    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getQuestion();
        mQuestionTextView.setText(question);
    }

    /**
     * 检查问题是否正确的方法
     * @param userPressedTrue
     */
    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();

        int messageResId;

        if (mIsCheater) {
            messageResId = R.string.judgment_toast;
        } else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
                mCurrentIndex++;
                updateQuestion();
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }
        Toast.makeText(QuizActivity.this,
                messageResId,
                Toast.LENGTH_SHORT).show();
    }

}
