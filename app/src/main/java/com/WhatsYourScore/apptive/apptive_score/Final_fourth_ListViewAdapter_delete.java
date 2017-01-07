package com.WhatsYourScore.apptive.apptive_score;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

import static com.WhatsYourScore.apptive.apptive_score.Final.context_final;
import static com.WhatsYourScore.apptive.apptive_score.Final.grade4_delete;
import static com.WhatsYourScore.apptive.apptive_score.Final_fourth_fragment.db4;
import static com.WhatsYourScore.apptive.apptive_score.Final_fourth_fragment.helper4;

/**
 * Created by kgm13 on 2017-01-06.
 */

public class Final_fourth_ListViewAdapter_delete extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    LayoutInflater inflater_final;
    private List<Subject> Subjectlist = null;
    private ArrayList<Subject> arraylist;

    // ListViewAdapter의 생성자
    public Final_fourth_ListViewAdapter_delete(Context context_, List<Subject> Subjectlist_) {
        mContext = context_;
        Subjectlist = Subjectlist_;
        inflater = LayoutInflater.from(mContext);
        inflater_final = LayoutInflater.from(context_final);
        this.arraylist = new ArrayList<Subject>();
        this.arraylist.addAll(Subjectlist);

    }

    @Override
    public int getViewTypeCount() {

        return 1;
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public int getCount() {
        return Subjectlist.size();
    }

    @Override
    public Subject getItem(int position) {
        return Subjectlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final Final_fourth_ListViewAdapter_delete.ViewHolder holder;
        if (view == null) {
            holder = new Final_fourth_ListViewAdapter_delete.ViewHolder();
            view = inflater.inflate(R.layout.activity_final_fourth_listview_delete, null);
            holder.subject = (TextView) view.findViewById(R.id.subject_textview4_delete);
            holder.grade = (TextView) view.findViewById(R.id.grade_textview4_delete);
            holder.check_button = (ToggleButton) view.findViewById(R.id.check_button_delete);
            holder.button = (ToggleButton) view.findViewById(R.id.subject_button4_delete);         //button 체크 여부 확인하는용도로 원래 listview의 button을 들고옴
            view.setTag(holder);
        }
        else {
            holder = (Final_fourth_ListViewAdapter_delete.ViewHolder) view.getTag();
        }

        if(Subjectlist.get(position).getButton() == 0) {
            holder.button.setChecked(false);
        }
        else{
            holder.button.setChecked(true);
        }
        if(Subjectlist.get(position).getCheck_button() == 0) {
            holder.check_button.setChecked(false);
        }
        else{
            holder.check_button.setChecked(true);
        }

        holder.subject.setText(Subjectlist.get(position).getSubject());
        holder.grade.setText(Subjectlist.get(position).getGrade());
        holder.check_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.check_button.isChecked()) { // 누르므로써 버튼이 check가 될경우
                    holder.check_button.setChecked(true);
                    holder.check_button.setBackgroundResource(R.drawable.ic_button_27);
                    int num = getGradepoint(position);
                    if(holder.button.isChecked()) {
                        grade4_delete -= num;
                    }
                    update(holder.subject.getText().toString(), "1");
                } else {
                    holder.check_button.setChecked(false);
                    holder.check_button.setBackgroundResource(R.drawable.ic_button_28);
                    int num = getGradepoint(position);
                    if(holder.button.isChecked()) {
                        grade4_delete += num;
                    }
                    update(holder.subject.getText().toString(), "0");
                }
            }
        });
        return view;
    }


    public int getGradepoint(int position) {
        String string = Subjectlist.get(position).getGrade();
        double temp = Double.parseDouble(string);
        int return_num = (int) temp;
        return return_num;
    }

    public void update(String subject, String check) {
        // 쓰기 권한인 DB 객체를 얻어온다.
        db4 = helper4.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Subject_div", check);
        db4.update("Sortdata4", values, "Subject=?", new String[]{subject});
    }

    public class ViewHolder {
        ToggleButton check_button;
        TextView subject;
        TextView grade;
        ToggleButton button;
    }

}
