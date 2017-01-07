package com.WhatsYourScore.apptive.apptive_score;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by joseong-yun on 2017. 1. 5..
 */

public class MySQLiteOpenHelper4 extends SQLiteOpenHelper {
  public MySQLiteOpenHelper4(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
    super(context, name, factory, version);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    String sql = "create table Sortdata4 (" + "_id integer primary key autoincrement, " + "Depart text, " + "Subject_div text, " +
        "Subject text, " + "Grade text, " + "Check_num int, " + "Delete_num int);";
    db.execSQL(sql);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    String sql = "drop table if exists Sortdata";
    db.execSQL(sql);
    onCreate(db);
  }
}

