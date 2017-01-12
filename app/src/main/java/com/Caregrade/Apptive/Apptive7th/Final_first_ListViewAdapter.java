package com.Caregrade.Apptive.Apptive7th;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

import static com.Caregrade.Apptive.Apptive7th.Final.context_final;
import static com.Caregrade.Apptive.Apptive7th.Final.grade1;
import static com.Caregrade.Apptive.Apptive7th.Final.grade_total;
import static com.Caregrade.Apptive.Apptive7th.Final.textView1;
import static com.Caregrade.Apptive.Apptive7th.Final.textView_total;
import static com.Caregrade.Apptive.Apptive7th.Final_first_fragment.db;
import static com.Caregrade.Apptive.Apptive7th.Final_first_fragment.temp_temp;

/**
 * Created by Apptive on 2016-11-15.
 */
public class Final_first_ListViewAdapter extends BaseAdapter implements View.OnClickListener {

  Context mContext;
  LayoutInflater inflater;
  LayoutInflater inflater_final;
  private List<Subject> Subjectlist = null;
  private ArrayList<Subject> arraylist;


  // ListViewAdapter의 생성자

  public Final_first_ListViewAdapter(Context context_, List<Subject> Subjectlist_) {
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

  //view를 불러오는 과정
  public View getView(final int position, View view, final ViewGroup parent) {
    final Final_first_ListViewAdapter.ViewHolder holder;
    if (view == null) {
      holder = new Final_first_ListViewAdapter.ViewHolder();
      view = inflater.inflate(R.layout.activity_final_first_listview, null);
      // Locate the TextViews in listview_item.xml
      holder.subject = (TextView) view.findViewById(R.id.subject_textview);
      holder.grade = (TextView) view.findViewById(R.id.grade_textview);
      holder.button = (ToggleButton) view.findViewById(R.id.subject_button);
      view.setTag(holder);
    } else {
      holder = (ViewHolder) view.getTag();
    }
    // Set the results into TextViews

    holder.subject.setText(Subjectlist.get(position).getSubject());
    holder.grade.setText(Subjectlist.get(position).getGrade());

    //db를 이용하여 listview를 불러올경우 : db의 check_num이 1인지 0인지를 확인하여 check상태를 true,false만 표시
    if (temp_temp) {
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
    // button에 click event가 들어올경우에 대해서 행하는 과정
    // check를 하게되면 button 배경이 표시로 바뀌서 전공기초의 숫자와 전체 학점의 숫자를 그 subject에 대한 grade만큼 올려줌
    // check가 되었으니, db의 check_num 도 1로 update해줍니다
    holder.button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        temp_temp = false; // db를 이용하여서 다 불러왔다는 표시로 위 96line의 if안에 들어가지 말라는 표시:false
        if (holder.button.isChecked()) {
          holder.button.setChecked(true);
          holder.button.setBackgroundResource(R.drawable.ic_button_check_icon_total);
          holder.subject.setTextColor(Color.parseColor("#FFA500"));
          int num = getGradepoint(position);
          grade1 += num;
          grade_total += num;
          textView1.setText(String.valueOf(grade1));
          textView_total.setText(String.valueOf(grade_total));
          update(holder.subject.getText().toString(), 1);
        }
        // check를 풀게되면 button 배경이 표시로 바뀌서 전공기초의 숫자와 전체 학점의 숫자를 그 subject에 대한 grade만큼 내려줌
        // check가 풀리게되면, db의 check_num 도 0로 update해줌
        else {
          holder.button.setChecked(false);
          holder.button.setBackgroundResource(R.drawable.ic_button_box_icon);
          holder.subject.setTextColor(Color.parseColor("#E1E1E1"));
          int num = getGradepoint(position);
          grade1 -= num;
          grade_total -= num;
          textView1.setText(String.valueOf(grade1));
          textView_total.setText(String.valueOf(grade_total));
          update(holder.subject.getText().toString(), 0);
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

  @Override
  public void onClick(View v) {

  }

  //////////////////////////////////db의 update 부분
  public void update(String subject, int check) {
    // 쓰기 권한인 DB 객체를 얻어온다.

    ContentValues values = new ContentValues();
    values.put("Check_num", check);
    db.update("Sortdata", values, "Subject=?", new String[]{subject});
  }

  public class ViewHolder {
    TextView subject;
    TextView grade;
    ToggleButton button;
  }
}
