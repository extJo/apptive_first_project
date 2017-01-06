package com.WhatsYourScore.apptive.apptive_score;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class Final_third_fragment extends Fragment {
  static SQLiteDatabase db3; //db1,db2 이런식으로 새로줄것
  static MySQLiteOpenHelper3 helper3; //helper1, helper2 이런식으로 새로줄것
  static boolean temp_temp3 = false;
  View view;
  String[] items = new String[100];
  String[] items_grade = new String[100];
  int[] items_button = new int[100];  // 새로 추가 : 버튼이 1인지 0인지 구분
  ArrayList<Subject> arrayList = new ArrayList<Subject>();
  Final_third_ListViewAdapter adapter;
  ListView listView;
  EditText editText;
  Sheet sheet;
  Cell cell;
  int i = 0;
  String depart;
  String admission;

  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.activity_final_third_fragment, container, false);
    listView = (ListView) view.findViewById(R.id.final_third_listview);

    helper3 = new MySQLiteOpenHelper3(getActivity(), "Sortdata3.db", null, 1);

    SharedPreferences pref = getContext().getSharedPreferences("Preference", 0); //0: 읽고쓰고가 가능, MODE_WORLD_READABLE : 읽기공유, MODE_WORLD_WRITEABLE : 읽기공유
    String depart = pref.getString("과명칭", "error");
    String admission = pref.getString("입학", "error");
    boolean dbcheck3 = pref.getBoolean("Third_DB", false);

    if (dbcheck3) {
      temp_temp3 = true;
      db3 = helper3.getReadableDatabase();
      Cursor c = db3.query("Sortdata3", null, null, null, null, null, null);
      int item_num = 0;
      while (c.moveToNext()) {
        String subject = c.getString(c.getColumnIndex("Subject"));
        String grade = c.getString(c.getColumnIndex("Grade"));
        int check = c.getInt(c.getColumnIndex("Check_num"));
        items[item_num] = subject;    // 교과목명
        items_grade[item_num] = grade; //  학점
        items_button[item_num] = check;
        Subject sj = new Subject(items[item_num], items_grade[item_num], items_button[item_num]);
        arrayList.add(sj);
        item_num++;
      }
      adapter = new Final_third_ListViewAdapter(getActivity().getApplicationContext(), arrayList);
      listView.setAdapter(adapter);
    } else {
      try {
        int sheet_num = sheetnum(admission);
        AssetManager am = getActivity().getAssets();
        InputStream is = am.open("apptive_doc.xls");
        Workbook wb = Workbook.getWorkbook(is);
        sheet = wb.getSheet(sheet_num);
        i = initI_third(depart);
        initList(i, depart);
        wb.close();

      } catch (Exception e) {

      }
    }
    return view;
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

  public int initI_third(String temp) {
    String temp_depart = "";
    int i = 0;
    while (!Objects.equals(temp, temp_depart))
      temp_depart = sheet.getCell(1, ++i).getContents();
    return i;
  }

  public void initList(int num, String depart) {
    int row = num;
    String string;
    String grade;
    String temp;

    int item_num = 0;
    do {
      SharedPreferences pref = getContext().getSharedPreferences("Preference", 0);
      SharedPreferences.Editor editor = pref.edit();
      editor.putBoolean("Third_DB", true);
      editor.commit();
      temp = sheet.getCell(4, row).getContents();
      cell = sheet.getCell(6, row);
      if (Objects.equals("전공선택", temp)) {
        string = cell.getContents();
        items[item_num] = string;
        items_grade[item_num++] = sheet.getCell(7, row).getContents();
        insert(depart, temp, string, sheet.getCell(7, row).getContents(), 0);
      }
    } while (sheet.getCell(1, ++row).getContents().equals(depart));

    row = num;
    int temp_num = item_num;
    item_num = 0;

    do {
      Subject sj = new Subject(items[item_num], items_grade[item_num]);
      arrayList.add(sj);
    } while (++item_num < temp_num);

    adapter = new Final_third_ListViewAdapter(getActivity().getApplicationContext(), arrayList);

    listView.setAdapter(adapter);
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////////db란

  public void insert(String depart, String subject_div, String subject, String grade, int check) {
    // 쓰기 권한인 DB 객체를 얻어온다.
    db3 = helper3.getWritableDatabase();

    ContentValues values = new ContentValues();
    // db.insert의 매개변수인 values가 ContentValues 변수이므로 그에 맞춤
    // 데이터의 삽입은 put을 이용한다.
    values.put("Depart", depart);
    values.put("Subject_div", subject_div);
    values.put("Subject", subject);
    values.put("Grade", grade);
    values.put("Check_num", check);

    db3.insert("Sortdata3", null, values);
    // tip : 마우스를 db.insert에 올려보면 매개변수가 어떤 것이 와야 하는지 알 수 있다.
  }

  public void update(String subject, int check) {
    // 쓰기 권한인 DB 객체를 얻어온다.
    db3 = helper3.getWritableDatabase();

    ContentValues values = new ContentValues();
    values.put("Check_num", check);
    db3.update("Sortdata3", values, "Subject=?", new String[]{subject});

  }

  public void delete(String depart) {
    db3 = helper3.getWritableDatabase();
    db3.delete("Sortdata3", "Depart=?", new String[]{depart});
  }

  public void select() {


    // 읽기 권한인 DB 객체를 얻어온다.
    db3 = helper3.getReadableDatabase();
    Cursor c = db3.query("Sortdata3", null, null, null, null, null, null);

        /*
         * 위 결과는 select * from student가 된다. Cursor는 DB결과를 저장한다. public Cursor
         * query(String table, String[] columns, String selection, String[]
         * selectionArgs, String groupBy, String having, String orderBy)
         */

    // c에 있는 모든 것을 얻기 위해 moveToNext를 사용하였다.
    while (c.moveToNext()) {
      // c.get[자료형](c.getColumnIndex([키 값]));
      int _id = c.getInt(c.getColumnIndex("_id"));
      String depart = c.getString(c.getColumnIndex("Depart"));
      String subject_div = c.getString(c.getColumnIndex("Subject_div"));
      String subject = c.getString(c.getColumnIndex("Subject"));
      String grade = c.getString(c.getColumnIndex("Grade"));
      int check = c.getInt(c.getColumnIndex("Check_num"));

    }
  }
}
