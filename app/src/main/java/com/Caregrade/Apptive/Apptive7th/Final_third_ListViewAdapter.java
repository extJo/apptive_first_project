package com.Caregrade.Apptive.Apptive7th;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

import static com.Caregrade.Apptive.Apptive7th.Final.context_final;
import static com.Caregrade.Apptive.Apptive7th.Final.grade3;
import static com.Caregrade.Apptive.Apptive7th.Final.grade_total;
import static com.Caregrade.Apptive.Apptive7th.Final.holdState;
import static com.Caregrade.Apptive.Apptive7th.Final.textView3;
import static com.Caregrade.Apptive.Apptive7th.Final.textView_total;
import static com.Caregrade.Apptive.Apptive7th.Final_third_fragment.db3;
import static com.Caregrade.Apptive.Apptive7th.Final_third_fragment.helper3;
import static com.Caregrade.Apptive.Apptive7th.Final_third_fragment.temp_temp3;

/**
 * Created by Apptive on 2016-11-19.
 */

public class Final_third_ListViewAdapter extends BaseAdapter {
  Context mContext;
  LayoutInflater inflater;
  LayoutInflater inflater_final;
  private List<Subject> Subjectlist = null;
  private ArrayList<Subject> arraylist;

  // ListViewAdapter의 생성자
  public Final_third_ListViewAdapter(Context context_, List<Subject> Subjectlist_) {
    mContext = context_;
    Subjectlist = Subjectlist_;
    inflater = LayoutInflater.from(mContext);
    inflater_final = LayoutInflater.from(context_final);
    this.arraylist = new ArrayList<Subject>();
    this.arraylist.addAll(Subjectlist);
  }

  @Override
  public int getViewTypeCount() {

    return getCount();
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
    final Final_third_ListViewAdapter.ViewHolder holder;
    if (view == null) {
      holder = new Final_third_ListViewAdapter.ViewHolder();
      view = inflater.inflate(R.layout.activity_final_third_listview, null);
      holder.subject = (TextView) view.findViewById(R.id.subject_textview3);
      holder.grade = (TextView) view.findViewById(R.id.grade_textview3);
      holder.button = (ToggleButton) view.findViewById(R.id.subject_button3);
      view.setTag(holder);
    } else {
      holder = (Final_third_ListViewAdapter.ViewHolder) view.getTag();
    }

    // Set the results into TextViews
    holder.subject.setText(Subjectlist.get(position).getSubject());
    holder.grade.setText(Subjectlist.get(position).getGrade());

    if (temp_temp3) {
      if (Subjectlist.get(position).getButton() == 1) {
        holder.subject.setTextColor(Color.parseColor("#FFA500"));
        holder.button.setBackgroundResource(R.drawable.ic_button_check_icon_total);
        holder.button.setChecked(true);
      } else {
        holder.subject.setTextColor(Color.parseColor("#E1E1E1"));
        holder.button.setBackgroundResource(R.drawable.ic_button_box_icon);
        holder.button.setChecked(false);
      }
    }

    final LinearLayout listview = (LinearLayout) view.findViewById(R.id.activity_final_third_listview);
    listview.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(!holdState) {
          temp_temp3 = false;
          if (holder.button.isChecked()) {
            holder.button.setChecked(false);
            holder.button.setBackgroundResource(R.drawable.ic_button_box_icon);
            holder.subject.setTextColor(Color.parseColor("#E1E1E1"));
            int num = getGradepoint(position);
            grade3 -= num;
            grade_total -= num;
            textView3.setText(String.valueOf(grade3));
            textView_total.setText(String.valueOf(grade_total));
            update(holder.subject.getText().toString(), 0);
          } else {
            holder.button.setChecked(true);
            holder.button.setBackgroundResource(R.drawable.ic_button_check_icon_total);
            holder.subject.setTextColor(Color.parseColor("#FFA500"));
            int num = getGradepoint(position);
            grade3 += num;
            grade_total += num;
            textView3.setText(String.valueOf(grade3));
            textView_total.setText(String.valueOf(grade_total));
            update(holder.subject.getText().toString(), 1);
          }
        }
      }
    });

    holder.button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(!holdState) {
          temp_temp3 = false;
          if (holder.button.isChecked()) {
            holder.button.setBackgroundResource(R.drawable.ic_button_check_icon_total);
            holder.subject.setTextColor(Color.parseColor("#FFA500"));
            int num = getGradepoint(position);
            grade3 += num;
            grade_total += num;
            textView3.setText(String.valueOf(grade3));
            textView_total.setText(String.valueOf(grade_total));
            update(holder.subject.getText().toString(), 1);
          } else {
            holder.button.setBackgroundResource(R.drawable.ic_button_box_icon);
            holder.subject.setTextColor(Color.parseColor("#E1E1E1"));
            int num = getGradepoint(position);
            grade3 -= num;
            grade_total -= num;
            textView3.setText(String.valueOf(grade3));
            textView_total.setText(String.valueOf(grade_total));
            update(holder.subject.getText().toString(), 0);
          }
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

  public void update(String subject, int check) {
    // 쓰기 권한인 DB 객체를 얻어온다.
    db3 = helper3.getWritableDatabase();

    ContentValues values = new ContentValues();
    values.put("Check_num", check);
    db3.update("Sortdata3", values, "Subject=?", new String[]{subject});

  }

  public class ViewHolder {
    TextView subject;
    TextView grade;
    ToggleButton button;
  }


}
