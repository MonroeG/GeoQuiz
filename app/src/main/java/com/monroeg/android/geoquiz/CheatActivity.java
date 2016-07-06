package com.monroeg.android.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by MonroeG on 2016/7/6.
 */
public class CheatActivity extends Activity{

    private static final String TAG = "CheatActivity";
    private static final String KEY_ANSWER = "answer";

    //使用包名来修饰extra数据信息，可以避免来着不同应用的extra间发生命名冲突
    public static final String EXTRA_ANSWER_IS_TRUE =
            "com.monroeg.android.geoquiz.answer_is_true";
    public static final String EXTRA_ANSWER_SHOWN =
            "com.monroeg.android.geoquiz.answer_shown";

    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    private Button mShowAnswerBotton;

    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "CheateActivity onCreate()");
        setContentView(R.layout.activity_cheat);

        mAnswerTextView = (TextView)findViewById(R.id.answer_text_view);

        /**
         * 1、解决旋转屏幕时，答案消失的问题
         * 2、解决用户查看答案后，通过旋转屏幕来清除作弊痕迹
         */
        if (savedInstanceState != null) {
            mAnswerIsTrue = savedInstanceState.getBoolean(KEY_ANSWER);
            if (mAnswerIsTrue) {
                mAnswerTextView.setText(R.string.true_button);
            } else {
                mAnswerTextView.setText(R.string.false_button);
            }
            setAnswerShownResult(true);
        } else {
            /**
             * 获取QuizActivity传递的extra信息
             * 1、getIntent()方法返回了由startActivity(Intent)方法转发的Intent对象
             * 2、getBooleanExtra()方法的第二个参数false为默认值，在无法获取有效key值时使用
             */
            mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
            setAnswerShownResult(false);
        }

        //实现点击显示答案按钮可获取答案并将其显示在TextView上
        mShowAnswerBotton = (Button)findViewById(R.id.show_answer_button);
        mShowAnswerBotton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnswerIsTrue) {
                    mAnswerTextView.setText(R.string.true_button);
                } else {
                    mAnswerTextView.setText(R.string.false_button);
                }
                setAnswerShownResult(true);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_ANSWER, mAnswerIsTrue);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "CheatActivity onStart()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "CheatActivity onRestart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "CheatActivity onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "CheatActivity onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "CheatActivity onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "CheatActivity onDestroy()");
    }
}
