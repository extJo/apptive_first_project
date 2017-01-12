package com.Caregrade.Apptive.Apptive7th;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by User on 2016-11-25.
 */

public class Notice extends BaseActivity {

  private BackPressCloseHandler backPressCloseHandler = new BackPressCloseHandler(this);

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.notice);

    new Intent(this.getIntent());

    Button button2 = (Button) findViewById(R.id.noticeButton);
    button2.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent1 = new Intent(Notice.this, Final.class);
        startActivity(intent1);
        finish();
      }
    });
  }

  @Override
  public void onBackPressed() {
    //super.onBackPressed();
    backPressCloseHandler.onBackPressed();
  }
}
