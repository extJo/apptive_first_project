package com.WhatsYourScore.apptive.apptive_score;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class Fourth extends BaseActivity {
    public static Activity fourth;
    Context mContext;
    String[] items = new String[100];
    ArrayList<Department> arrayList = new ArrayList<Department>();
    ListViewAdapter adapter;
    ListView listView;
    EditText editText;
    String string = "1";
    Sheet sheet;
    Cell cell;
    String preference;
    int i = 0;
    private BackPressCloseHandler backPressCloseHandler = new BackPressCloseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);
        new Intent(this.getIntent());

        fourth = Fourth.this;
        // 이전 액티비티가 죽지않아서 죽임
        Third killThird = (Third) Third.third;
        killThird.finish();

        SharedPreferences pref = getSharedPreferences("Preference", 0); //0: 읽고쓰고가 가능, MODE_WORLD_READABLE : 읽기공유, MODE_WORLD_WRITEABLE : 읽기공유
        preference = pref.getString("입학", "error");
        Toast.makeText(this, preference, Toast.LENGTH_SHORT).show();

        // credit button
        ImageButton back = (ImageButton) findViewById(R.id.fourth_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Fourth.this, Third.class);
                startActivity(intent);
                finish();
            }
        });


        editText = (EditText) findViewById(R.id.search_text);
        listView = (ListView) findViewById(R.id.depart_listview);

        try {
            int sheet_num = sheetnum(preference);
            AssetManager am = getAssets();
            InputStream is = am.open("Depart_eg.xls");
            Workbook wb = Workbook.getWorkbook(is);
            sheet = wb.getSheet(sheet_num);
            initList();

        } catch (Exception e) {
        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = editText.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }
        });
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

    public void initList() {
        do {
            cell = sheet.getCell(3, i + 1);
            string = cell.getContents();
            items[i] = string;
        } while (!(items[i++].equals("end")));

        i = 0;

        do {
            Department dp = new Department(items[i]);
            arrayList.add(dp);
        } while (!(items[++i].equals("end")));
        adapter = new ListViewAdapter(this, arrayList);
        listView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {

        backPressCloseHandler.onBackPressed();
    }
}

