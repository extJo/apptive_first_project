package com.WhatsYourScore.apptive.apptive_score;
//쿼리 찾아보기

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

// Final view의 첫번째 fragment 를 표시하는 class(전공기초란)
public class Final_first_fragment extends Fragment {
    // DB관련 변수들(sqlite를 썼습니다)
    static SQLiteDatabase db; // sqlite를 사용한 db
    static MySQLiteOpenHelper1 helper; // db의 table
    static boolean temp_temp = false; // listview들을 현재 excel에서 들고오는지(초기)
    View view; // view
    // listview관련 변수들
    String[] items = new String[100]; //listview의 과목명
    String[] items_grade = new String[100]; //listview의 학점
    int[] items_button = new int[100];  // listview의 togglebutton
    ArrayList<Subject> arrayList = new ArrayList<Subject>(); // list들의 집합
    Final_first_ListViewAdapter adapter; // list들의 집합들의 adapter
    ListView listView;
    // JXl library 변수들
    Sheet sheet; //액셀에서의 sheet에 접근합니다 (첫번째 sheet : 0, 두번째 sheet : 1 ..)
    Cell cell;  // 엑셀의 sheet내의 cell data에 접근합니다 (col,row)순
    int i = 0;

    // db에서 들고오는지에 따라서 수행을 달리하는 것을 알려주는 boolean
    // (초기 excel를 부르고 나서 그 list를 db에 넣어 두번째 이후엔 db를 이용하여 실행)
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_final_first_fragment, container, false);
        listView = (ListView) view.findViewById(R.id.final_first_listview);

        helper = new MySQLiteOpenHelper1(getActivity(), "Sortdata.db", null, 1); // Sortdata라는 명을 가진 db파일를 new

        SharedPreferences pref = getContext().getSharedPreferences("Preference", 0); //0: 읽고쓰고가 가능, MODE_WORLD_READABLE : 읽기공유, MODE_WORLD_WRITEABLE : 읽기공유
        String depart = pref.getString("과명칭", "error"); // 학과명
        String admission = pref.getString("입학", "error"); // 입학년도
        boolean dbcheck1 = pref.getBoolean("First_DB", false); // 처음 불러오는지 기존에 있는 db를 들고올건지 확인
        // 처음이 아닐시에 false를 들고와서 기존의 excel에 접근하여 list를 부름

        //db를 이용하여서 listview를 생성하는 코드 (추가적으로 닫고 다시 실행될때 불러올 코드)
        if (dbcheck1) {
            temp_temp = true; //db를 이용하여서 불러올거라는 뜻
            db = helper.getReadableDatabase();
            Cursor c = db.query("Sortdata", null, null, null, null, null, null);
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
            adapter = new Final_first_ListViewAdapter(getActivity().getApplicationContext(), arrayList);
            listView.setAdapter(adapter);
        }
        //기존 excel를 이용하여서 listview를 생성하는 코드 (맨 처음일시 실행되는 부분)
        else {
            try {
                int sheet_num = sheetnum(admission);
                AssetManager am = getActivity().getAssets();
                InputStream is = am.open("apptive_doc.xls"); // assets-> apptive_doc.xls를 불러 리스트를 부릅니다.
                Workbook wb = Workbook.getWorkbook(is);
                sheet = wb.getSheet(sheet_num);             // 입학년도에 따라서 sheet를 달리 부릅니다(ex.2016: 0번 sheet, 2015; 1번 sheet)
                i = initI_first(depart);                          // 입학년도에 대한 과명칭에 대한 row를 찾습니다.
                initList(i, depart);                        // list를 부르기 위한 initiate과정입니다 : 과명칭에 대한 row를 이용하여서 list 생성
                wb.close();
            } catch (Exception e) {

            }
        }

        return view;
    }

    //excel sheet의 숫자를 불러옴
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

    // sheet에 대해서 과의 데이터가 몇 행에 있는지(row)를 불러옴
    public int initI_first(String temp) {
        String temp_depart = "";
        int i = 0;

        while (!Objects.equals(temp, temp_depart)) {
            temp_depart = sheet.getCell(1, ++i).getContents();
        }
        return i;
    }

    // row를 불러온 값을 이용하여서 전공기초에 대한 데이터를 sort하여 listview 생성 이때 db에도 같이 생성
    public void initList(int num, String depart) {
        int row = num;
        String string;
        String temp;


        int item_num = 0;
        do {
            SharedPreferences pref = getContext().getSharedPreferences("Preference", 0);
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("First_DB", true);
            editor.commit();
            temp = sheet.getCell(4, row).getContents();
            cell = sheet.getCell(6, row);
            if (Objects.equals("전공기초", temp)) {
                //일단 전공기초
                string = cell.getContents(); //교과목 명
                items[item_num] = string;    // item에 넣는과정
                items_grade[item_num++] = sheet.getCell(7, row).getContents(); //  학점 넣는거
                insert(depart, temp, string, sheet.getCell(7, row).getContents(), 0); // db에 list가 추가되는 그 라인을 똑같이 생성
            }
        } while (sheet.getCell(1, ++row).getContents().equals(depart));

        int temp_num = item_num;
        item_num = 0;

        do {
            Subject sj = new Subject(items[item_num], items_grade[item_num]);
            arrayList.add(sj);
        } while (++item_num < temp_num);

        adapter = new Final_first_ListViewAdapter(getActivity().getApplicationContext(), arrayList);

        listView.setAdapter(adapter);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////db부분
    //db 테이블에 insert
    //  Id        Depart        Subject_div       Subject      Grade        Check_num
    // Depart : 과명칭, suject_div : 전공기초와 같은 전공 분류, Subject : 과목명 Grade: 과목에 대한 학점(1,2,3) check_num :버튼 on1, off0
    // 순으로 테이블 추가
    public void insert(String depart, String subject_div, String subject, String grade, int check) {
        // 쓰기 권한인 DB 객체를 얻어온다.
        db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        // db.insert의 매개변수인 values가 ContentValues 변수이므로 그에 맞춤
        // 데이터의 삽입은 put을 이용한다.
        values.put("Depart", depart);
        values.put("Subject_div", subject_div);
        values.put("Subject", subject);
        values.put("Grade", grade);
        values.put("Check_num", check); //초기값은 0

        db.insert("Sortdata", null, values);
        // tip : 마우스를 db.insert에 올려보면 매개변수가 어떤 것이 와야 하는지 알 수 있다.
    }

    public void update(String subject, int check) {
        // 쓰기 권한인 DB 객체를 얻어온다.
        db = helper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Check_num", check);
        db.update("Sortdata", values, "Subject=?", new String[]{subject});

    }

    public void delete(String depart) {
        db = helper.getWritableDatabase();
        db.delete("Sortdata", "Depart=?", new String[]{depart});
        Log.i("db", depart + " 정상적으로 삭제 되었습니다.");
    }

    public void select() {

        db = helper.getReadableDatabase();
        Cursor c = db.query("Sortdata", null, null, null, null, null, null);

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
