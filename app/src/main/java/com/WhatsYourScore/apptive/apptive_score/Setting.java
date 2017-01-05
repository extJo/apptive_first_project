package com.WhatsYourScore.apptive.apptive_score;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import static com.WhatsYourScore.apptive.apptive_score.Final_first_fragment.db;
import static com.WhatsYourScore.apptive.apptive_score.Final_fourth_fragment.db4;
import static com.WhatsYourScore.apptive.apptive_score.Final_fourth_fragment.temp_temp4;
import static com.WhatsYourScore.apptive.apptive_score.Final_second_fragment.db2;
import static com.WhatsYourScore.apptive.apptive_score.Final_third_fragment.db3;

/**
 * Created by User on 2016-11-27.
 */

public class Setting extends BaseActivity {

  private CustomDialog mCustomDialog;

  // custom dialog 의 왼쪽버튼 setting
  private View.OnClickListener leftListener = new View.OnClickListener() {
    public void onClick(View v) {
      SharedPreferences pref = getSharedPreferences("Preference", 0); //0: 읽고쓰고가 가능, MODE_WORLD_READABLE : 읽기공유, MODE_WORLD_WRITEABLE : 읽기공유
      SharedPreferences.Editor editor = pref.edit();
      editor.putInt("설정완료", 0);
      editor.putInt("grade1", 0);
      editor.putInt("grade2", 0);
      editor.putInt("grade3", 0);
      editor.putInt("grade4", 0);
      editor.putInt("grade_total", 0);
      editor.putBoolean("First_DB", false);
      editor.putBoolean("Second_DB", false);
      editor.putBoolean("Third_DB", false);
      editor.putBoolean("Fourth_DB", false); // 4번째뷰 db를 지울때 쓸것!
      editor.commit();

      db.execSQL("delete from " + "Sortdata");
      db2.execSQL("delete from " + "Sortdata2");
      db3.execSQL("delete from " + "Sortdata3");
      if (temp_temp4)
        db4.execSQL("delete from " + "Sortdata4");   // 4번째뷰 db를 지울때 쓸것!
      Intent intent = new Intent(Setting.this, Third.class);
      startActivity(intent);

      // 이전 액티비티가 죽지않아서 죽임
      Final killFinal = (Final) Final.last;
      killFinal.finish();
      finish();

      mCustomDialog.dismiss();
    }
  };

  // custom dialog의 오른쪽 버튼을 setting
  private View.OnClickListener rightListener = new View.OnClickListener() {
    public void onClick(View v) {
      mCustomDialog.dismiss();
    }
  };

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_setting);
    SharedPreferences preference = getSharedPreferences("Preference", 0); //0: 읽고쓰고가 가능, MODE_WORLD_READABLE : 읽기공유, MODE_WORLD_WRITEABLE : 읽기공유
    TextView major = (TextView) findViewById(R.id.setting_major);
    major.setText(preference.getString("과명칭", "error"));


    // back button
    ImageButton set_back_butn = (ImageButton) findViewById(R.id.setting_back);
    set_back_butn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });

    // feedback button
    ViewGroup feedback = (ViewGroup) findViewById(R.id.setting_feedback);
    feedback.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + getPackageName()));
        startActivity(intent);
      }
    });

    // reset button
    ViewGroup reset = (ViewGroup) findViewById(R.id.setting_reset);
    reset.setOnClickListener(new View.OnClickListener() {
      @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(Setting.this, MainActivity.class);

        mCustomDialog = new CustomDialog(v.getContext(), "리셋 하시겠습니까?", getDrawable(R.drawable.ic_color_reset_button),
            leftListener, rightListener);
        mCustomDialog.show();


      }
    });


    // credit button
    ViewGroup credit = (ViewGroup) findViewById(R.id.setting_credit);
    credit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(Setting.this, Credit.class);
        startActivity(intent);
      }
    });


    // notice button
    ViewGroup notice = (ViewGroup) findViewById(R.id.setting_notice);
    notice.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(Setting.this, SettingNotice.class);
        startActivity(intent);
      }
    });
  }
}