package com.WhatsYourScore.apptive.apptive_score;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import static com.WhatsYourScore.apptive.apptive_score.Final_fourth_fragment.adapter;
import static com.WhatsYourScore.apptive.apptive_score.Final_fourth_fragment.arrayList;
import static com.WhatsYourScore.apptive.apptive_score.Final_fourth_fragment.items_button_fourth;
import static com.WhatsYourScore.apptive.apptive_score.Final_fourth_fragment.items_fourth;
import static com.WhatsYourScore.apptive.apptive_score.Final_fourth_fragment.items_grade_fourth;
import static com.WhatsYourScore.apptive.apptive_score.Final_fourth_fragment.listView_fourth;

/**
 * Created by kgm13 on 2017-01-05.
 */

public class Add_box extends Dialog {
  private TextView mTitleView;
  private EditText mSubText;
  private EditText mGradeText;
  private TextView mLeftButton;
  private TextView mRightButton;

  private View.OnClickListener mLeftClickListener;
  private View.OnClickListener mRightClickListener;

  // 클릭버튼이 확인과 취소 두개일때 생성자 함수로 이벤트를 받는다
  public Add_box(Context context, View.OnClickListener leftListener, View.OnClickListener rightListener) {
    super(context, android.R.style.Theme_Translucent_NoTitleBar);

    this.mLeftClickListener = leftListener;
    this.mRightClickListener = rightListener;
  }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      // 다이얼로그 외부 화면 흐리게 표현
      WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
      lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
      lpWindow.dimAmount = 0.8f;
      getWindow().setAttributes(lpWindow);

      setContentView(R.layout.activity_add_box);

      mTitleView = (TextView) findViewById(R.id.add_text);
      mSubText = (EditText) findViewById(R.id.add_sub);
      mGradeText = (EditText) findViewById(R.id.add_grade);
      mLeftButton = (TextView) findViewById(R.id.add_yes); //기존의 textview
      mRightButton = (TextView) findViewById(R.id.add_no);


      // 클릭 이벤트 셋팅
      if (mLeftClickListener != null && mRightClickListener != null) {
        mLeftButton.setOnClickListener(mLeftClickListener);
        mRightButton.setOnClickListener(mRightClickListener);
      } else if (mLeftClickListener != null
          && mRightClickListener == null) {
        mLeftButton.setOnClickListener(mLeftClickListener);
      } else {

      }
    }
}
