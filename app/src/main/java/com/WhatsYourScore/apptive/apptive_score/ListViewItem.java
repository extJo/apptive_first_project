package com.WhatsYourScore.apptive.apptive_score;

/**
 * Created by apptive on 2016-11-13.
 */

import android.graphics.drawable.Drawable;

public class ListViewItem {
  private Drawable iconDrawable;
  private String titleStr;
  private String descStr;

  public Drawable getIcon() {
    return this.iconDrawable;
  }

  public void setIcon(Drawable icon) {
    iconDrawable = icon;
  }

  public String getTitle() {
    return this.titleStr;
  }

  public void setTitle(String title) {
    titleStr = title;
  }

  public String getDesc() {
    return this.descStr;
  }

  public void setDesc(String desc) {
    descStr = desc;
  }
}