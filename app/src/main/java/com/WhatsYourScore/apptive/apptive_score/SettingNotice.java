package com.WhatsYourScore.apptive.apptive_score;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * Created by User on 2016-12-03.
 */

public class SettingNotice extends BaseActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_setting_notice);

    new Intent(this.getIntent());

    ImageButton setButn = (ImageButton) findViewById(R.id.setting_notice_back);
    setButn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });

    Button button2 = (Button) findViewById(R.id.setting_notice_page);
    button2.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });

  }
}
