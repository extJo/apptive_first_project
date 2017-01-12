package com.Caregrade.Apptive.Apptive7th;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.tsengvn.typekit.TypekitContextWrapper;

/**
 * Created by User on 2016-11-20.
 */

public class BaseActivity extends AppCompatActivity {

  @Override
  protected void attachBaseContext(Context newBase) {

    super.attachBaseContext(TypekitContextWrapper.wrap(newBase));

  }
}
