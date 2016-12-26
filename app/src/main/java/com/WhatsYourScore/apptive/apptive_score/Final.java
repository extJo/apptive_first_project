package com.WhatsYourScore.apptive.apptive_score;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tsengvn.typekit.TypekitContextWrapper;

import java.io.InputStream;
import java.util.Objects;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class Final extends BaseActivity {
    public static Activity last;
    public static Context context_final;
    static TextView textView1;
    static TextView textView2;
    static TextView textView3;
    static TextView textView4;
    static TextView textView_total;
    static int grade1 = 0;
    static int grade2 = 0;
    static int grade3 = 0;
    static int grade4 = 0;
    static int grade_total = 0;
    TextView textView;
    int i = 0;
    Workbook wb;
    Sheet sheet;
    Cell cell;
    private BackPressCloseHandler backPressCloseHandler = new BackPressCloseHandler(this);
    private TabLayout tabLayout;
    private TabLayout tabLayout_indicator;
    private ViewPager viewPager;
    private Final_viewAdp viewadapter;

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("currentItem", viewPager.getCurrentItem());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        last = this;
        context_final = this;
        new Intent(this.getIntent());


        SharedPreferences pref = getSharedPreferences("Preference", 0); //0: 읽고쓰고가 가능, MODE_WORLD_READABLE : 읽기공유, MODE_WORLD_WRITEABLE : 읽기공유
        String depart = pref.getString("과명칭", "error");
        String admission = pref.getString("입학", "error");
        grade1 = pref.getInt("grade1", 0);
        grade2 = pref.getInt("grade2", 0);
        grade3 = pref.getInt("grade3", 0);
        grade4 = pref.getInt("grade4", 0);
        grade_total = pref.getInt("grade_total", 0);

        textView = (TextView) findViewById(R.id.final_text);
        textView1 = (TextView) findViewById(R.id.final_grade_current1);
        textView2 = (TextView) findViewById(R.id.final_grade_current2);
        textView3 = (TextView) findViewById(R.id.final_grade_current3);
        textView4 = (TextView) findViewById(R.id.final_grade_current4);
        textView_total = (TextView) findViewById(R.id.final_grade_current);

        final LinearLayout firstLinear = (LinearLayout) findViewById(R.id.final_tab_first);
        final LinearLayout secondLinear = (LinearLayout) findViewById(R.id.final_tab_second);
        final LinearLayout thirdLinear = (LinearLayout) findViewById(R.id.final_tab_third);
        final LinearLayout fourthLinear = (LinearLayout) findViewById(R.id.final_tab_fourth);

        textView1.setText(String.valueOf(grade1));
        textView2.setText(String.valueOf(grade2));
        textView3.setText(String.valueOf(grade3));
        textView4.setText(String.valueOf(grade4));
        textView_total.setText(String.valueOf(grade_total));

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(3);
        viewadapter = new Final_viewAdp(getSupportFragmentManager());
        viewPager.setAdapter(viewadapter);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorHeight(0);

        tabLayout_indicator = (TabLayout) findViewById(R.id.tab_layout_indicator);
        tabLayout_indicator.setupWithViewPager(viewPager);
        tabLayout_indicator.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.tab_color_selector));


        tabLayout.setTabTextColors(ContextCompat.getColorStateList(this, R.color.white));
        firstLinear.setBackgroundColor(Color.parseColor("#C88200"));


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()) {
                    case 0:
                        textView.setText("전공기초");
                        viewPager.setCurrentItem(tab.getPosition());
                        firstLinear.setBackgroundColor(Color.parseColor("#C88200"));
                        secondLinear.setBackgroundColor(Color.parseColor("#222222"));
                        thirdLinear.setBackgroundColor(Color.parseColor("#222222"));
                        fourthLinear.setBackgroundColor(Color.parseColor("#222222"));
                        break;
                    case 1:
                        textView.setText("전공필수");
                        viewPager.setCurrentItem(tab.getPosition());
                        firstLinear.setBackgroundColor(Color.parseColor("#222222"));
                        secondLinear.setBackgroundColor(Color.parseColor("#C88200"));
                        thirdLinear.setBackgroundColor(Color.parseColor("#222222"));
                        fourthLinear.setBackgroundColor(Color.parseColor("#222222"));
                        break;
                    case 2:
                        textView.setText("전공선택");
                        viewPager.setCurrentItem(tab.getPosition());
                        firstLinear.setBackgroundColor(Color.parseColor("#222222"));
                        secondLinear.setBackgroundColor(Color.parseColor("#222222"));
                        thirdLinear.setBackgroundColor(Color.parseColor("#C88200"));
                        fourthLinear.setBackgroundColor(Color.parseColor("#222222"));                        break;
                    case 3:
                        textView.setText("그 외");
                        viewPager.setCurrentItem(tab.getPosition());
                        firstLinear.setBackgroundColor(Color.parseColor("#222222"));
                        secondLinear.setBackgroundColor(Color.parseColor("#222222"));
                        thirdLinear.setBackgroundColor(Color.parseColor("#222222"));
                        fourthLinear.setBackgroundColor(Color.parseColor("#C88200"));                        break;
                    default:
                        textView.setText("1");
                        viewPager.setCurrentItem(tab.getPosition());
                        break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

        });


        try {
            int sheet_num = sheetnum(admission);
            AssetManager am = getAssets();
            InputStream is = am.open("Depart_eg.xls");
            wb = Workbook.getWorkbook(is);
            sheet = wb.getSheet(sheet_num);
            i = initI(depart);
            initList(i, depart);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (savedInstanceState != null) {
            viewPager.setCurrentItem(savedInstanceState.getInt("currentItem", 0));
        }

        ImageButton setButn = (ImageButton) findViewById(R.id.setting_button);
        setButn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Final.this, Setting.class);
                startActivity(intent1);
            }
        });

    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }

    public int sheetnum(String temp) {
        switch (temp) {
            case "2016":
                return 0;
            case "2015":
                return 1;
            case "2014":
                return 2;
            case "2013":
                return 3;
            case "2012":
                return 4;
            default:
                return 0;
        }
    }

    public int initI(String temp) {
        String temp_depart = "";
        while (!Objects.equals(temp, temp_depart))
            temp_depart = sheet.getCell(3, ++i).getContents();
        return i;
    }

    public void initList(int num, String depart) {
        int row = num;
        String string1;
        String string2;
        String string3;
        String string;

        string = sheet.getCell(7, num).getContents();
        string1 = sheet.getCell(8, num).getContents();
        string2 = sheet.getCell(9, num).getContents();
        string3 = sheet.getCell(10, num).getContents();

        TextView textView_total = (TextView) findViewById(R.id.final_grade);
        TextView textView1 = (TextView) findViewById(R.id.final_grade1);
        TextView textView2 = (TextView) findViewById(R.id.final_grade2);
        TextView textView3 = (TextView) findViewById(R.id.final_grade3);
        textView_total.setText(string);
        textView1.setText(string1);
        textView2.setText(string2);
        textView3.setText(string3);
    }


    @Override
    protected void onStop() {

        SharedPreferences pref = getSharedPreferences("Preference", 0); //0: 읽고쓰고가 가능, MODE_WORLD_READABLE : 읽기공유, MODE_WORLD_WRITEABLE : 읽기공유
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("설정완료", 1);
        editor.putInt("grade1", grade1);
        editor.putInt("grade2", grade2);
        editor.putInt("grade3", grade3);
        editor.putInt("grade4", grade4);
        editor.putInt("grade_total", grade_total);
        editor.commit();
        super.onStop();
    }

    @Override
    protected void attachBaseContext(Context newBase) {

        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));

    }
}
