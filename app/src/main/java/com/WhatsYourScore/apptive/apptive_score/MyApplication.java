package com.WhatsYourScore.apptive.apptive_score;

import android.app.Application;

import com.tsengvn.typekit.Typekit;

/**
 * Created by User on 2016-11-20.
 */


// application class는 클래스간의 공통된 data를 공유하기 위한 class입니다
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "Spoqa Han Sans Thin_win_subset.ttf"));
    }
}
