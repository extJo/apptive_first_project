package com.WhatsYourScore.apptive.apptive_score;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.text.DecimalFormat;
import java.util.ArrayList;
// 그 외 부분
public class Final_fourth_fragment extends Fragment {
  // DB관련 변수들(sqlite를 썼습니다)
  static SQLiteDatabase db4; // sqlite를 사용한 db
  static MySQLiteOpenHelper4 helper4; // db의 table
  // listview관련 변수들
  String[] items_fourth = new String[100]; //listview의 과목명
  String[] items_grade_fourth = new String[100]; //listview의 학점
  int[] items_button_fourth = new int[100];  // listview의 togglebutton
  ArrayList<Subject> arrayList = new ArrayList<Subject>(); // list들의 집합
  Final_fourth_ListViewAdapter adapter; // list들의 집합들의 adapter
  ListView listView;
  static boolean temp_temp4 = false;
  View view; // view
  int item_num = 0;
  private Add_box mAdd_box;
  private Context fourth_context;


  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.activity_final_fourth_fragment, container, false);
    listView = (ListView) view.findViewById(R.id.final_fourth_listview);
    helper4 = new MySQLiteOpenHelper4(getActivity(), "Sortdata4.db", null, 1); // etc라는 명을 가진 db파일를 생성
    fourth_context = this.getContext();

    SharedPreferences pref = getContext().getSharedPreferences("Preference", 0); //0: 읽고쓰고가 가능, MODE_WORLD_READABLE : 읽기공유, MODE_WORLD_WRITEABLE : 읽기공유
    String depart = pref.getString("과명칭", "error"); // 학과명
    String admission = pref.getString("입학", "error"); // 입학년도
    boolean dbcheck4 = pref.getBoolean("Fourth_DB", false);
    //db를 이용하여서 listview를 생성하는 코드 (추가적으로 닫고 다시 실행될때 불러올 코드)

    if (dbcheck4) {
      temp_temp4 = true; //db를 이용하여서 불러올거라는 뜻
      db4 = helper4.getReadableDatabase();
      Cursor c = db4.query("Sortdata4", null, null, null, null, null, null);
      int item_num = 0;
      while (c.moveToNext()) { //db의 id를 하나씩 이동하면서 list를 추가합니다
        String subject = c.getString(c.getColumnIndex("Subject"));
        String grade = c.getString(c.getColumnIndex("Grade"));
        int check = c.getInt(c.getColumnIndex("Check_num"));
        items_fourth[item_num] = subject;    // 교과목명
        items_grade_fourth[item_num] = grade; //  학점
        items_button_fourth[item_num] = check;
        Subject sj = new Subject(items_fourth[item_num], items_grade_fourth[item_num], items_button_fourth[item_num]);
        arrayList.add(sj);
        item_num++;
      }
      adapter = new Final_fourth_ListViewAdapter(getActivity().getApplicationContext(), arrayList);
      listView.setAdapter(adapter);
    }

    final ImageButton add = (ImageButton) view.findViewById(R.id.etc_add_button);
    final ImageButton delete = (ImageButton) view.findViewById(R.id.etc_delete_button);



    add.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mAdd_box = new Add_box(v.getContext(), leftListener, rightListener);
        mAdd_box.show();
      }
    });

    // delete 버튼에 대한 click listener


    return view;
  }




  // custom dialog 의 왼쪽버튼 setting
  private View.OnClickListener leftListener = new View.OnClickListener() {
    public void onClick(View v) {

      // view 에 민감하므로 잘 봐야함. 현재 뷰에 대한 EditText가 아니므로 Add_box의 EditText를 가지고와야함
      EditText Edit_subject = (EditText) mAdd_box.findViewById(R.id.add_sub);
      EditText Edit_grade = (EditText) mAdd_box.findViewById(R.id.add_grade);

      String subject = Edit_subject.getText().toString();
      String grade = Edit_grade.getText().toString();
      
      // 소수점 변환
      double temp = Double.parseDouble(grade);
      DecimalFormat temp_decimal = new DecimalFormat("#.#");
      temp_decimal.format(temp);
      grade = String.valueOf(temp);


      double temp = Double.parseDouble(grade);
      DecimalFormat temp_decimal = new DecimalFormat("#.#");
      temp_decimal.format(temp);
      grade = String.valueOf(temp);


      items_fourth[item_num] = subject;    // 교과목명
      items_grade_fourth[item_num] = grade; //  학점
      items_button_fourth[item_num] = 0;
      Subject sj = new Subject(items_fourth[item_num], items_grade_fourth[item_num], items_button_fourth[item_num]);
      arrayList.add(sj);
      item_num++;

      SharedPreferences pref = getContext().getSharedPreferences("Preference", 0);
      String depart = pref.getString("과명칭", "error"); // 학과명
      String admission = pref.getString("입학", "error"); // 입학년도
      SharedPreferences.Editor editor = pref.edit();
      editor.putBoolean("Fourth_DB", true);
      editor.commit();
      insert(depart, "0", subject, grade, 0);
      adapter = new Final_fourth_ListViewAdapter(fourth_context, arrayList);
      listView.setAdapter(adapter);

      mAdd_box.dismiss();
    }
  };

  // custom dialog의 오른쪽 버튼을 setting
  private View.OnClickListener rightListener = new View.OnClickListener() {
    public void onClick(View v) {
      mAdd_box.dismiss();
    }
  };




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

    db4.insert("Sortdata4", null, values);
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

    }
  }

}

