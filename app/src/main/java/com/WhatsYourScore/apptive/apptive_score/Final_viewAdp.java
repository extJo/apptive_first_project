package com.WhatsYourScore.apptive.apptive_score;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by apptive on 2016-11-15.
 */

public class Final_viewAdp extends FragmentPagerAdapter {

  private String[] textResId = {
      "전공기초",
      "전공선택",
      "전공필수",
      "그외"
  };

  public Final_viewAdp(FragmentManager fm) {
    super(fm);
  }

  @Override
  public Fragment getItem(int position) {
    switch (position) {
      case 0:
        Bundle arguments = new Bundle();
        arguments.putInt("position1", position);
        Final_first_fragment fragment = new Final_first_fragment();
        fragment.setArguments(arguments);
        return fragment;

      case 1:
        return new Final_second_fragment();

      case 2:
        return new Final_third_fragment();

      case 3:
        return new Final_fourth_fragment();
      default:
        return null;
    }
  }

  @Override
  public int getCount() {
    return textResId.length;
  }
}
