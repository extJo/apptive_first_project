package com.Caregrade.Apptive.Apptive7th;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Locale;

public class Third extends BaseActivity {

  public static Activity third;
  String[] items;
  ArrayList<Admission> arrayList = new ArrayList<Admission>();
  Third_ListViewAdapter adapter;
  ListView listView;
  EditText editText;
  int i = 0;
  private BackPressCloseHandler backPressCloseHandler = new BackPressCloseHandler(this);

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_third);
    new Intent(this.getIntent());

    // 다음 액티비티에 가서 이 액티비티를 죽이기 위함
    third = Third.this;
    editText = (EditText) findViewById(R.id.admin_text);
    listView = (ListView) findViewById(R.id.admin_listview);

    initList();

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

  public void initList() {
    items = new String[]{"2012", "2013", "2014", "2015", "2016"};


    do {
      Admission ad = new Admission(items[i++]);
      arrayList.add(ad);
    } while (i < 5);
    adapter = new Third_ListViewAdapter(this, arrayList);
    listView.setAdapter(adapter);
  }

  @Override
  public void onBackPressed() {

    backPressCloseHandler.onBackPressed();
  }


}


