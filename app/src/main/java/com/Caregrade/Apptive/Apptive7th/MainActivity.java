package com.Caregrade.Apptive.Apptive7th;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tsengvn.typekit.TypekitContextWrapper;

public class MainActivity extends BaseActivity {
  //SharedPreferences pref = getSharedPreferences("Preference", MODE_PRIVATE);
  private BackPressCloseHandler backPressCloseHandler = new BackPressCloseHandler(this);


  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);


    SharedPreferences pref = getSharedPreferences("Preference", 0);
    int save_activity = pref.getInt("설정완료", 0);

    if (save_activity == 1) {
      Intent intent = new Intent(MainActivity.this, Final.class);
      startActivity(intent);
      finish();
    }
    Button button1 = (Button) findViewById(R.id.first);
    button1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, Second.class);
        startActivity(intent);
        finish();
      }
    });
  }

  @Override
  public void onBackPressed() {
    backPressCloseHandler.onBackPressed();
  }


}
