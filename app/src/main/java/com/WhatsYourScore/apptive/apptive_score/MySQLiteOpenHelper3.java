package com.WhatsYourScore.apptive.apptive_score;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kgm13 on 2016-11-27.
 */

public class MySQLiteOpenHelper3 extends SQLiteOpenHelper {
    public MySQLiteOpenHelper3(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table Sortdata3 (" + "_id integer primary key autoincrement, " + "Depart text, " + "Subject_div text, " +
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
