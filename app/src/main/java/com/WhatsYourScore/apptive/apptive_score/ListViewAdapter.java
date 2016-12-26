package com.WhatsYourScore.apptive.apptive_score;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by apptive on 2016-11-13.
 */

public class ListViewAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
//    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>() ;

    Context mContext;
    LayoutInflater inflater;
    private List<Department> departmentlist = null;
    private ArrayList<Department> arraylist;

    // ListViewAdapter의 생성자
    public ListViewAdapter(Context context, List<Department> departmentlist) {
        mContext = context;
        this.departmentlist = departmentlist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Department>();
        this.arraylist.addAll(departmentlist);
    }

    @Override
    public int getCount() {
        return departmentlist.size();
    }

    @Override
    public Department getItem(int position) {
        return departmentlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
//        if (view == null) {
        holder = new ViewHolder();
        view = inflater.inflate(R.layout.activity_fourth_listview, null);
        // Locate the TextViews in listview_item.xml
        holder.dapart = (TextView) view.findViewById(R.id.depart_textview);
//            holder.subject = (TextView) view.findViewById(R.id.country);
//            holder.gradepoint = (TextView) view.findViewById(R.id.population);
        view.setTag(holder);
//        }
//        else {
//            holder = (ViewHolder) view.getTag();
//        }
        // Set the results into TextViews
        holder.dapart.setText(departmentlist.get(position).getDapart());
//        holder.subject.setText(departmentlist.get(position).getCountry());
//        holder.gradepoint.setText(departmentlist.get(position).getPopulation());

        // 클릭하면 다음 액티비티로 감
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Fifth.class); // 클릭 하면 다음 xml로
                // Pass all data rank
                String admin_num = departmentlist.get(position).getDapart();
                SharedPreferences pref = mContext.getSharedPreferences("Preference", 0); //0: 읽고쓰고가 가능, MODE_WORLD_READABLE : 읽기공유, MODE_WORLD_WRITEABLE : 읽기공유
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("과명칭", admin_num);
                editor.commit();

                // Pass all data flag
                // Start SingleItemView Class
                mContext.startActivity(intent);
            }
        });


        return view;
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        departmentlist.clear();
        if (charText.length() == 0) {
            departmentlist.addAll(arraylist);
        } else {
            for (Department dp : arraylist) {
                if (dp.getDapart().toLowerCase(Locale.getDefault()).contains(charText)) {
                    departmentlist.add(dp);
                }
            }
        }
        notifyDataSetChanged();
    }

    //    public class ViewHolder {
//        TextView dapart;
//        TextView subject;
//        TextView gradepoint;
//    }
    public class ViewHolder {
        TextView dapart;
    }
}



