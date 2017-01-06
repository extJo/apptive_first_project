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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

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
    int[] items_choose_button_fourth = new int[100];
    String[] items_fourth_delete = new String[100]; //listview의 과목명
    String[] items_grade_fourth_delete = new String[100]; //listview의 학점
    int[] items_button_fourth_delete = new int[100];  // listview의 togglebutton


    ArrayList<Subject> arrayList = new ArrayList<Subject>(); // list들의 집합
    ArrayList<Subject> arrayList_delete = new ArrayList<Subject>();
    Final_fourth_ListViewAdapter adapter; // list들의 집합들의 adapter
    Final_fourth_ListViewAdapter_delete adapter_delete;
    ListView listView;
    ListView listView_delete;
    static boolean temp_temp4 = false;
    View view; // view
    int item_num = 0;
    private Add_box mAdd_box;
    private Context fourth_context;
    boolean dbcheck4 = false;

    Fragment frg = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_final_fourth_fragment, container, false);
        LinearLayout view2 = (LinearLayout)view.findViewById(R.id.fourth_fragment);
        LinearLayout view3 = (LinearLayout)view.findViewById(R.id.fourth_fragment_delete);
        view2.setVisibility(View.VISIBLE);
        view3.setVisibility(View.GONE);
        listView = (ListView) view.findViewById(R.id.final_fourth_listview);
        listView_delete = (ListView) view.findViewById(R.id.final_fourth_listview_delete_page);
        helper4 = new MySQLiteOpenHelper4(getActivity(), "Sortdata4.db", null, 1); // etc라는 명을 가진 db파일를 생성
        fourth_context = this.getContext();

        SharedPreferences pref = getContext().getSharedPreferences("Preference", 0); //0: 읽고쓰고가 가능, MODE_WORLD_READABLE : 읽기공유, MODE_WORLD_WRITEABLE : 읽기공유
        String depart = pref.getString("과명칭", "error"); // 학과명
        String admission = pref.getString("입학", "error"); // 입학년도
        dbcheck4 = pref.getBoolean("Fourth_DB", false);
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
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //4번째 DB에 있는 내용을 listview로 불러오는 과정(delete뷰에 맞는 양식으로 들고옴)
                if (dbcheck4) {
                    System.out.println("====================진입====================");
                    db4 = helper4.getReadableDatabase();
                    Cursor c = db4.query("Sortdata4", null, null, null, null, null, null);
                    int item_num = 0;
                    c.moveToFirst(); // db의 포인터를 맨 앞으로 바꿔줌 // 이미 db를 읽어 와서 맨 뒤를 가리키켰을때 다시 앞으로 돌려주는과정
                    do {
                        String subject = c.getString(c.getColumnIndex("Subject"));
                        String grade = c.getString(c.getColumnIndex("Grade"));
                        int check = c.getInt(c.getColumnIndex("Check_num"));
                        items_choose_button_fourth[item_num] = 0;
                        items_fourth_delete[item_num] = subject;    // 교과목명
                        items_grade_fourth_delete[item_num] = grade; //  학점
                        items_button_fourth_delete[item_num] = check;
                        Subject sj = new Subject(items_choose_button_fourth[item_num], items_fourth_delete[item_num], items_grade_fourth_delete[item_num], items_button_fourth_delete[item_num]);
                        arrayList_delete.add(sj);
                        item_num++;
                    } while (c.moveToNext());
                    adapter_delete = new Final_fourth_ListViewAdapter_delete(getActivity().getApplicationContext(), arrayList_delete);
                    listView_delete.setAdapter(adapter_delete);
                    adapter_delete.notifyDataSetChanged();
                    LinearLayout view2 = (LinearLayout)view.findViewById(R.id.fourth_fragment);
                    LinearLayout view3 = (LinearLayout)view.findViewById(R.id.fourth_fragment_delete);
                    view2.setVisibility(View.GONE);
                    view3.setVisibility(View.VISIBLE);
                }

//                // dialog로 리스트가 없음을 표시 ... 어째 해야하노?
//                frg = getFragmentManager().findFragmentByTag("my_Fragment");
//                final FragmentTransaction ft = getFragmentManager().beginTransaction();
//                //final FragmentTransaction ft = getFragmentManager().beginTransaction();
//                ft.detach(frg);
//                ft.attach(frg);
//                ft.commit();


            }
        });
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



    public class ViewHolder {
        ToggleButton check_button;
        TextView subject;
        TextView grade;
        ToggleButton button;
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

