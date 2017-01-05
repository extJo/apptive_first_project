package com.WhatsYourScore.apptive.apptive_score;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static com.WhatsYourScore.apptive.apptive_score.Final_fourth_fragment.adapter;
import static com.WhatsYourScore.apptive.apptive_score.Final_fourth_fragment.arrayList;
import static com.WhatsYourScore.apptive.apptive_score.Final_fourth_fragment.items_button_fourth;
import static com.WhatsYourScore.apptive.apptive_score.Final_fourth_fragment.items_fourth;
import static com.WhatsYourScore.apptive.apptive_score.Final_fourth_fragment.items_grade_fourth;
import static com.WhatsYourScore.apptive.apptive_score.Final_fourth_fragment.listView_fourth;

/**
 * Created by kgm13 on 2017-01-05.
 */

public class Add_box extends BaseActivity {
   // static SQLiteDatabase db4; // sqlite를 사용한 db
   // static MySQLiteOpenHelper4 helper4; // db의 table
    View view; // view
    // listview관련 변수들

    //static boolean temp_temp4 = false;
    int item_num = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_box);

        Button button_yes = (Button) findViewById(R.id.add_yes);
        Button button_no = (Button) findViewById(R.id.add_no);

        button_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText Edit_subject = (EditText) findViewById(R.id.add_sub);
                EditText Edit_grade = (EditText) findViewById(R.id.add_grade);

                String subject = Edit_subject.getText().toString();
                String grade = Edit_grade.getText().toString();
                items_fourth[item_num] = subject;    // 교과목명
                items_grade_fourth[item_num] = grade; //  학점
                items_button_fourth[item_num] = 0;
                Subject sj = new Subject(items_fourth[item_num], items_grade_fourth[item_num], items_button_fourth[item_num]);
                arrayList.add(sj);
                item_num++;

                adapter = new Final_fourth_ListViewAdapter(getApplicationContext(), arrayList);
                listView_fourth.setAdapter(adapter);
               // finish();

            }
        });

        button_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
