package com.WhatsYourScore.apptive.apptive_score;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Fifth extends BaseActivity {
  private BackPressCloseHandler backPressCloseHandler = new BackPressCloseHandler(this);

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_fifth);
    new Intent(this.getIntent());

    Fourth killFourth = (Fourth) Fourth.fourth;
    killFourth.finish();

    SharedPreferences pref = getSharedPreferences("Preference", 0); //0: 읽고쓰고가 가능, MODE_WORLD_READABLE : 읽기공유, MODE_WORLD_WRITEABLE : 쓰기공유
    String preference = pref.getString("과명칭", "error");
    String preference2 = pref.getString("입학", "error");
    Toast.makeText(this, "입학년도 : " + preference2 + "\n과명칭 : " + preference, Toast.LENGTH_SHORT).show();

    Button button3 = (Button) findViewById(R.id.Fifth);
    button3.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent1 = new Intent(Fifth.this, Notice.class);
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
