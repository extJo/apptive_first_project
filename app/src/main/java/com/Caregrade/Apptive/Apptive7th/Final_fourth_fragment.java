package com.Caregrade.Apptive.Apptive7th;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

import static com.Caregrade.Apptive.Apptive7th.Final.grade4;
import static com.Caregrade.Apptive.Apptive7th.Final.grade4_delete;
import static com.Caregrade.Apptive.Apptive7th.Final.grade_total;
import static com.Caregrade.Apptive.Apptive7th.Final.textView4;
import static com.Caregrade.Apptive.Apptive7th.Final.textView_total;

// 그 외 부분
public class Final_fourth_fragment extends Fragment {
  // DB관련 변수들(sqlite를 썼습니다)
  static SQLiteDatabase db4; // sqlite를 사용한 db
  static MySQLiteOpenHelper4 helper4; // db의 table
  static boolean temp_temp4 = false;  //DB가 생성 되었을시, 그 DB파일 들고와서 listview를 작성시, 체크상태를 확인해 주는 변수
  //DB를 이용하여서 초기에 다시 생성할시 true;
  static boolean dbcheck4 = false;           //4번째 DB가 생성되었는지 확인, 생성이 되었을시 true; sharedpreference에 생성 여부 save,load
                                             //setting java 파일에서 DB를 지울떄 생성되었는지 안되었는지 체크하기 위해서 static 선언

  // listview관련 변수들
  String[] items_fourth = new String[100];            //listview의 과목명
  String[] items_grade_fourth = new String[100];      //listview의 학점
  int[] items_button_fourth = new int[100];           // listview의 togglebutton

  boolean listview_check = false;
  int[] items_choose_button_fourth = new int[100];        // livtview_delete의 delete를 할건지 물어보는 체크, 지우면 1
  String[] items_fourth_delete = new String[100];         // livtview_delete의 과목명
  String[] items_grade_fourth_delete = new String[100];   // livtview_delete의 학점
  int[] items_button_fourth_delete = new int[100];        // livtview_delete의 togglebutton


  boolean listview_delete_check = false;
  ArrayList<Subject> arrayList = new ArrayList<Subject>();            // list : 일반 버젼
  ArrayList<Subject> arrayList_delete = new ArrayList<Subject>();     // list :delete 버젼
  Final_fourth_ListViewAdapter adapter;                               // listadapter : delete 버전
  Final_fourth_ListViewAdapter_delete adapter_delete;                 // listadapter : delet)버젼
  ListView listView;                  // 일반생성되는 listview
  ListView listView_delete;           // delete를 할때 생성되는 listview(delete) : delete 할 버튼 생성
  int item_num = 0;                   // listview에 들어갈 item의 개수 (몇번째의미)

  //view 관련 변수
  View view;                                  // view : fourth_fragment의 inflate를 들고오는 변수
  LinearLayout layout_dafault, layout_delete; // visiable, invisiable 하는 변수
  Button button_confirm, button_cancel;       // delete를 체크 한후 확인과 취소에 대한 button
  //Sharedpreference 관련 변수
  SharedPreferences pref;             //preference에 접근할 방 이름
  String depart;                      // pref의 학과명
  String admission;                   // pref의 입학년도
  SharedPreferences.Editor editor;    // pref의 editor
  private Context fourth_context;     //context를 의미
  // dialog 관련 변수
  private Add_box mAdd_box;           //Fourth fragment에 들어갈 dialog
  // Button 관련 변수
  private ImageButton add;
  private ImageButton delete;


  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    // view의 변수에 대한 id 부여
    view = inflater.inflate(R.layout.activity_final_fourth_fragment, container, false);
    button_confirm = (Button) view.findViewById(R.id.etc_add_button_delete_page);
    button_cancel = (Button) view.findViewById(R.id.etc_delete_button_delete_page);
    fourth_context = this.getContext();

    Visiable_default(); //view의 초기는 default

    // list의 변수에 대해 id 부여
    listView = (ListView) view.findViewById(R.id.final_fourth_listview);
    listView_delete = (ListView) view.findViewById(R.id.final_fourth_listview_delete_page);

    // 버튼의 변수에 대해 id 부여
    add = (ImageButton) view.findViewById(R.id.etc_add_button);
    delete = (ImageButton) view.findViewById(R.id.etc_delete_button);

    // DB에 대해서 Sortdata4 create
    helper4 = new MySQLiteOpenHelper4(getActivity(), "Sortdata4.db", null, 1); // etc라는 명을 가진 db파일를 생성

    // SgaredPreference에대한 값을 불러오는 과정 : Preference에 저장된 값을 불러옴
    pref = getContext().getSharedPreferences("Preference", 0);
    dbcheck4 = pref.getBoolean("Fourth_DB", false);

    if (dbcheck4) {
      temp_temp4 = true; //db를 이용하여서 불러올거라는 뜻
      db4 = helper4.getReadableDatabase();
      Cursor c = db4.query("Sortdata4", null, null, null, null, null, null, null);
      item_num = 0;
      int delete_num;
      while (c.moveToNext()) { //db의 id를 하나씩 이동하면서 list를 추가합니다
        delete_num = c.getInt(c.getColumnIndex("Delete_num"));
        if (delete_num == 0) {
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
      }
      adapter = new Final_fourth_ListViewAdapter(getActivity().getApplicationContext(), arrayList);
      listView.setAdapter(adapter);
    }


    // add 버튼(+)에 대한 click listener
    add.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mAdd_box = new Add_box(v.getContext(), leftListener, rightListener);
        mAdd_box.show();
      }
    });

    // delete 버튼(-)에 대한 click listener
    delete.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //4번째 DB에 있는 내용을 listview로 불러오는 과정(delete뷰에 맞는 양식으로 들고옴)
        // listview_delete가 생성되었을시, 그 listview에 대해서 clear해줌
        if (listview_delete_check) {
          arrayList_delete.clear();
          adapter_delete.notifyDataSetChanged();
        }

        db4 = helper4.getReadableDatabase();
        Cursor c = db4.query("Sortdata4", null, null, null, null, null, null, null);
        ;
        int item_num = 0;
        int delete_num;
        grade4_delete = 0;
        while (c.moveToNext()) { //db의 id를 하나씩 이동하면서 list를 추가합니다
          delete_num = c.getInt(c.getColumnIndex("Delete_num"));
          String subject = c.getString(c.getColumnIndex("Subject"));
          String grade = c.getString(c.getColumnIndex("Grade"));
          String check_confirm = c.getString(c.getColumnIndex("Subject_div"));
          int check = c.getInt(c.getColumnIndex("Check_num"));

          if (delete_num == 0) {
            //DB의 내용을 list에 넣는과정
            items_choose_button_fourth[item_num] = 0;
            items_fourth_delete[item_num] = subject;    // 교과목명
            items_grade_fourth_delete[item_num] = grade; //  학점
            items_button_fourth_delete[item_num] = check;
            Subject sj = new Subject(items_choose_button_fourth[item_num], items_fourth_delete[item_num], items_grade_fourth_delete[item_num], items_button_fourth_delete[item_num]);
            arrayList_delete.add(sj);
            item_num++;
            if (Objects.equals(check_confirm, "1")) {
              update_subject_div(subject, "0");
            }
          }

          //Subject_div : 4번째 DB에서는 Delete여부를 체크하는 용도, 0으로 다 초기화
        }
        adapter_delete = new Final_fourth_ListViewAdapter_delete(getActivity().getApplicationContext(), arrayList_delete);
        listView_delete.setAdapter(adapter_delete);
        listview_delete_check = true;           //listview_delete가 만들어 졌음을 의미
        adapter_delete.notifyDataSetChanged();

        Visiable_delete();
        // dialog로 리스트가 없음을 표시 ... 어째 해야하노?
      }
    });

    //확인 버튼을 눌렸을때에 대한 eventlistener
    //DB의 Delete_num에 0인 부분에 대해서 원래 listview에 대해서 다시 재생성
    //total학점과, 그외 학점을 삭제한 학점만큼 빼서 적용
    button_confirm.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(listview_check) {
          arrayList.clear();
          adapter.notifyDataSetChanged();


          Cursor c = db4.query("Sortdata4", null, null, null, null, null, null, null);
          temp_temp4 = true; // 아이콘이 체크됐는지 확인
          item_num = 0;
          while (c.moveToNext()) {
            String check_confirm = c.getString(c.getColumnIndex("Subject_div"));
            String subject = c.getString(c.getColumnIndex("Subject"));
            String grade = c.getString(c.getColumnIndex("Grade"));
            int check = c.getInt(c.getColumnIndex("Check_num"));
            if (Objects.equals(check_confirm, "1")) {
              update_delete_num(subject, 1);
            } else {
              items_fourth[item_num] = subject;    // 교과목명
              items_grade_fourth[item_num] = grade; //  학점
              items_button_fourth[item_num] = check;
              Subject sj = new Subject(items_fourth[item_num], items_grade_fourth[item_num], items_button_fourth[item_num]);
              arrayList.add(sj);
              item_num++;
            }
          }

          adapter = new Final_fourth_ListViewAdapter(getActivity().getApplicationContext(), arrayList);
          listView.setAdapter(adapter);
          grade4 += grade4_delete;
          grade_total += grade4_delete;
          textView4.setText(String.valueOf(grade4));
          textView_total.setText(String.valueOf(grade_total));

        }
        //temp_temp4 = false; //아이콘 체크표시확인을 원래대로 돌려놈
        if(item_num > 0) {  // list가 한개라도 있을시 check true 표시
          listview_check = true;
        }
        Visiable_default();
      }

    });

    //취소 버튼을 눌렸을때에 대한 eventlistner
    button_cancel.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        arrayList_delete.clear();
        adapter_delete.notifyDataSetChanged();
        Visiable_default();
      }
    });

    return view;
  }

  // 과목 추가(add_Box) dialog의 왼쪽버튼(추가) setting
  private View.OnClickListener leftListener = new View.OnClickListener() {
    public void onClick(View v) {

      // view 에 민감하므로 잘 봐야함. 현재 뷰에 대한 EditText가 아니므로 Add_box의 EditText를 가지고와야함
      EditText Edit_subject = (EditText) mAdd_box.findViewById(R.id.add_sub);
      EditText Edit_grade = (EditText) mAdd_box.findViewById(R.id.add_grade);

      String subject = Edit_subject.getText().toString();
      String grade = Edit_grade.getText().toString();

      // 과목명이 입력되지 않았을 경우
      if (Objects.equals(subject, "")) {
        Edit_subject.setHint("반드시 과목명을 입력해 주세요");
        Edit_subject.setBackground(getResources().getDrawable(R.drawable.edit_bottom_warnning));
      }

      // 학점이 입력되지 않았을 경우
      if (Objects.equals(grade, "")) {
        Edit_grade.setHint("반드시 학점을 입력해 주세요");
        Edit_grade.setBackground(getResources().getDrawable(R.drawable.edit_bottom_warnning));
      }

      if (!Objects.equals(subject, "") && !Objects.equals(grade, "")) {
        double temp = Double.parseDouble(grade);
        DecimalFormat temp_decimal = new DecimalFormat("#.#");         // 소수점 변환
        temp_decimal.format(temp);
        grade = String.valueOf(temp);

        items_fourth[item_num] = subject;    // 교과목명
        items_grade_fourth[item_num] = grade; //  학점
        items_button_fourth[item_num] = 0;
        Subject sj = new Subject(items_fourth[item_num], items_grade_fourth[item_num], items_button_fourth[item_num]);
        arrayList.add(sj);
        item_num++;

        pref = getContext().getSharedPreferences("Preference", 0);
        depart = pref.getString("과명칭", "error"); // 학과명
        admission = pref.getString("입학", "error"); // 입학년도
        editor = pref.edit();
        editor.putBoolean("Fourth_DB", true);
        editor.commit();
        insert(depart, "0", subject, grade, 0);

        if (listview_check || dbcheck4) {
          adapter.notifyDataSetChanged();
        } else {
          listview_check = true;
          adapter = new Final_fourth_ListViewAdapter(fourth_context, arrayList);
          listView.setAdapter(adapter);
        }
        dbcheck4 = true;
        mAdd_box.dismiss();
      }

    }
  };

  // 과목 추가(add_Box) dialog의 오른쪽 setting
  private View.OnClickListener rightListener = new View.OnClickListener() {
    public void onClick(View v) {
      mAdd_box.dismiss();
    }
  };


  ////////////////////////////////////////////////////////////////////////////////////////////////////일반 함수

  //원래창 : visiable, delete창 : invisiable
  private void Visiable_default() {
    layout_dafault = (LinearLayout) view.findViewById(R.id.fourth_fragment);
    layout_delete = (LinearLayout) view.findViewById(R.id.fourth_fragment_delete);
    layout_dafault.setVisibility(View.VISIBLE);
    layout_delete.setVisibility(View.GONE);
  }

  //원래창 : invisiable, delete창 : visiable
  private void Visiable_delete() {
    layout_dafault = (LinearLayout) view.findViewById(R.id.fourth_fragment);
    layout_delete = (LinearLayout) view.findViewById(R.id.fourth_fragment_delete);
    layout_dafault.setVisibility(View.GONE);
    layout_delete.setVisibility(View.VISIBLE);
  }


  ////////////////////////////////////////////////////////////////////////////////////////////////////db부분
  //db 테이블에 insert
  //  Id        Depart        Subject_div       Subject      Grade        Check_num
  // Depart : 과명칭, suject_div : 전공기초와 같은 전공 분류, Subject : 과목명 Grade: 과목에 대한 학점(1,2,3) check_num :버튼 on1, off0
  // 순으로 테이블 추가

  public void insert(String depart, String subject_div, String subject, String grade, double check) {
    // 쓰기 권한인 DB 객체를 얻어온다.
    db4 = helper4.getWritableDatabase();
    int delete = 0;
    ContentValues values = new ContentValues();
    // db.insert의 매개변수인 values가 ContentValues 변수이므로 그에 맞춤
    // 데이터의 삽입은 put을 이용한다.
    values.put("Depart", depart);
    values.put("Subject_div", subject_div);
    values.put("Subject", subject);
    values.put("Grade", grade);
    values.put("Check_num", check); //초기값은 0
    values.put("Delete_num", delete);

    db4.insert("Sortdata4", null, values);
    // tip : 마우스를 db.insert에 올려보면 매개변수가 어떤 것이 와야 하는지 알 수 있다.
  }

  public void insert(String depart, String subject_div, String subject, String grade, int check, int delete) {
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
    values.put("Delete_num", delete);

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

  public void update_subject_div(String subject, String check) {
    // 쓰기 권한인 DB 객체를 얻어온다.
    db4 = helper4.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put("Subject_div", check);
    db4.update("Sortdata4", values, "Subject=?", new String[]{subject});
  }

  //DB의 Delete_num -> 0일시 list로 존재, Delete_num -> 1일시 삭제 의미
  //num이 1이면 삭제후 listview를 부르면 그 부분은 건너뛰고 list생성을 하게됨
  public void update_delete_num(String subject, int check) {
    // 쓰기 권한인 DB 객체를 얻어온다.
    db4 = helper4.getWritableDatabase();

    ContentValues values = new ContentValues();
    values.put("Delete_num", check);
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
      int delete = c.getInt(c.getColumnIndex("Delete_num"));
    }
  }

}
