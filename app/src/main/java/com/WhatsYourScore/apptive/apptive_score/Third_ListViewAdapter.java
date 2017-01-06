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

public class Third_ListViewAdapter extends BaseAdapter {
  Context mContext;
  LayoutInflater inflater;
  private List<Admission> Admissionlist = null;
  private ArrayList<Admission> arraylist;

  // ListViewAdapter의 생성자
  public Third_ListViewAdapter(Context context_, List<Admission> Admissionlist_) {
    mContext = context_;
    Admissionlist = Admissionlist_;
    inflater = LayoutInflater.from(mContext);
    this.arraylist = new ArrayList<Admission>();
    this.arraylist.addAll(Admissionlist);
  }

  @Override
  public int getCount() {
    return Admissionlist.size();
  }

  @Override
  public Admission getItem(int position) {
    return Admissionlist.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  public View getView(final int position, View view, ViewGroup parent) {
    final Third_ListViewAdapter.ViewHolder holder;
//        if (view == null) {
    holder = new ViewHolder();
    view = inflater.inflate(R.layout.activity_third_listview, null);
    // Locate the TextViews in listview_item.xml
    holder.admin = (TextView) view.findViewById(R.id.admin_textview);

    view.setTag(holder);

    // Set the results into TextViews
    holder.admin.setText(Admissionlist.get(position).getAdmin());


    view.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(mContext, Fourth.class); // 클릭 하면 다음 xml로
        // Pass all data rank
        String admin_num = Admissionlist.get(position).getAdmin();
        SharedPreferences pref = mContext.getSharedPreferences("Preference", 0); //0: 읽고쓰고가 가능, MODE_WORLD_READABLE : 읽기공유, MODE_WORLD_WRITEABLE : 읽기공유
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("입학", admin_num);
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
    Admissionlist.clear();
    if (charText.length() == 0) {
      Admissionlist.addAll(arraylist);
    } else {
      for (Admission ad : arraylist) {
        if (ad.getAdmin().toLowerCase(Locale.getDefault()).contains(charText)) {
          Admissionlist.add(ad);
        }
      }
    }
    notifyDataSetChanged();
  }

  public class ViewHolder {
    TextView admin;
  }

}
