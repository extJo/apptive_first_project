package com.Caregrade.Apptive.Apptive7th;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kgm13 on 2016-11-27.
 */
//table를 만들기 위해선 helper를 많이 쓰는 방법으로 접근
public class MySQLiteOpenHelper1 extends SQLiteOpenHelper {
  public MySQLiteOpenHelper1(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
    super(context, name, factory, version);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    String sql = "create table Sortdata (" + "_id integer primary key autoincrement, " + "Depart text, " + "Subject_div text, " +
        "Subject text, " + "Grade text, " + "Check_num int);";
    db.execSQL(sql);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    String sql = "drop table if exists Sortdata";
    db.execSQL(sql);
    onCreate(db);
  }
}
