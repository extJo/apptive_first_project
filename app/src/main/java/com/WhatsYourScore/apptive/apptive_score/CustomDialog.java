package com.WhatsYourScore.apptive.apptive_score;

/**
 * Created by User on 2016-12-03.
 */

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomDialog extends Dialog {


  private TextView mTitleView;
  private ImageView mContentView;
  private TextView mLeftButton;
  private TextView mRightButton;
  private String mTitle;
  private Drawable mContent;
  private View.OnClickListener mLeftClickListener;
  private View.OnClickListener mRightClickListener;

  // 클릭버튼이 하나일때 생성자 함수로 클릭이벤트를 받는다.
  public CustomDialog(Context context, String title,
                      View.OnClickListener singleListener) {
    super(context, android.R.style.Theme_Translucent_NoTitleBar);
    this.mTitle = title;
    this.mLeftClickListener = singleListener;
  }


  // 클릭버튼이 확인과 취소 두개일때 생성자 함수로 이벤트를 받는다
  public CustomDialog(Context context, String title,
                      Drawable drawable, View.OnClickListener leftListener,
                      View.OnClickListener rightListener) {
    super(context, android.R.style.Theme_Translucent_NoTitleBar);
    this.mTitle = title;
    this.mContent = drawable;
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

    setContentView(R.layout.activity_massagebox);

    mTitleView = (TextView) findViewById(R.id.massage_text);
    mContentView = (ImageView) findViewById(R.id.massage_image);
    mLeftButton = (TextView) findViewById(R.id.reset_yes); //기존의 textview
    mRightButton = (TextView) findViewById(R.id.reset_no);

    // 제목과 내용을 생성자에서 셋팅한다.
    mTitleView.setText(mTitle);
    mContentView.setImageDrawable(mContent);

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



