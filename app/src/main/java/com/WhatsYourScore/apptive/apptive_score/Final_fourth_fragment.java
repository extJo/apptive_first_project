package com.WhatsYourScore.apptive.apptive_score;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

// 그 외 부분
public class Final_fourth_fragment extends Fragment {
  // DB관련 변수들(sqlite를 썼습니다)
  static SQLiteDatabase db4; // sqlite를 사용한 db
  static MySQLiteOpenHelper4 helper4; // db의 table
  View view; // view
  // listview관련 변수들
  String[] items = new String[100]; //listview의 과목명
  String[] items_grade = new String[100]; //listview의 학점
  int[] items_button = new int[100];  // listview의 togglebutton
  ArrayList<Subject> arrayList = new ArrayList<Subject>(); // list들의 집합
  Final_fourth_ListViewAdapter adapter; // list들의 집합들의 adapter
  ListView listView;
  static boolean temp_temp4 = false;



  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.activity_final_fourth_fragment, container, false);
    listView = (ListView) view.findViewById(R.id.final_fourth_listview);

    helper4 = new MySQLiteOpenHelper4(getActivity(), "Sortdata4.db", null, 1); // etc라는 명을 가진 db파일를 생성


    SharedPreferences pref = getContext().getSharedPreferences("Preference", 0); //0: 읽고쓰고가 가능, MODE_WORLD_READABLE : 읽기공유, MODE_WORLD_WRITEABLE : 읽기공유
    String depart = pref.getString("과명칭", "error"); // 학과명
    String admission = pref.getString("입학", "error"); // 입학년도

    //db를 이용하여서 listview를 생성하는 코드 (추가적으로 닫고 다시 실행될때 불러올 코드)
    db4 = helper4.getReadableDatabase();
    Cursor c = db4.query("Sortdata4", null, null, null, null, null, null);
    int item_num = 0;
    while (c.moveToNext()) { //db의 id를 하나씩 이동하면서 list를 추가합니다
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
    adapter = new Final_fourth_ListViewAdapter(getActivity().getApplicationContext(), arrayList);
    listView.setAdapter(adapter);

    return view;

  }


  ////////////////////////////////////////////////////////////////////////////////////////////////////db부분
  //db 테이블에 insert
  //  Id        Depart        Subject_div       Subject      Grade        Check_num
  // Depart : 과명칭, suject_div : 전공기초와 같은 전공 분류, Subject : 과목명 Grade: 과목에 대한 학점(1,2,3) check_num :버튼 on1, off0
  // 순으로 테이블 추가
  public void insert(String depart, String subject_div, String subject, String grade, int check) {
    // 쓰기 권한인 DB 객체를 얻어온다.
    db4 = helper4.getWritableDatabase();

    ContentValues values = new ContentValues();
    // db.insert의 매개변수인 values가 ContentValues 변수이므로 그에 맞춤
    // 데이터의 삽입은 put을 이용한다.
    values.put("Depart", depart);
    values.put("Subject_div", subject_div);
    values.put("Subject", subject);
    values.put("Grade", grade);
    values.put("Check_num", check); //초기값은 0

    db4.insert("etc", null, values);
    // tip : 마우스를 db.insert에 올려보면 매개변수가 어떤 것이 와야 하는지 알 수 있다.
  }

  public void update(String subject, int check) {
    // 쓰기 권한인 DB 객체를 얻어온다.
    db4 = helper4.getWritableDatabase();

    ContentValues values = new ContentValues();
    values.put("Check_num", check);
    db4.update("Sortdata4", values, "Subject=?", new String[]{subject});

  }

  public void delete(String depart) {
    db4 = helper4.getWritableDatabase();
    db4.delete("Sortdata4", "Depart=?", new String[]{depart});
    Log.i("db", depart + " 정상적으로 삭제 되었습니다.");
  }

  public void select() {

    db4 = helper4.getReadableDatabase();
    Cursor c = db4.query("Sortdata4", null, null, null, null, null, null);

    while (c.moveToNext()) {
      int _id = c.getInt(c.getColumnIndex("_id"));
      String depart = c.getString(c.getColumnIndex("Depart"));
      String subject_div = c.getString(c.getColumnIndex("Subject_div"));
      String subject = c.getString(c.getColumnIndex("Subject"));
      String grade = c.getString(c.getColumnIndex("Grade"));
      int check = c.getInt(c.getColumnIndex("Check_num"));

      Log.i("db", "id: " + _id + ", Depart: " + depart + ", Subject_div: " + subject_div + ", Subject: " + subject
          + ", Grade: " + grade + ", Check: " + check);
    }
  }
}
