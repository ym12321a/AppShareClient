package com.ym.appshare.adapter;

import java.util.List;

import com.ym.appshare.activity.R;
import com.ym.appshare.entity.WeiboUser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SpListViewAdapter extends BaseAdapter {                  //   微博账号登陆页面    多个用户下拉列表显示 （头像，用户名）

	List<WeiboUser> list ;
	Context context ;
	LayoutInflater inflater ;
	
	public SpListViewAdapter(Context context,List<WeiboUser> list){
		this.context = context ;
		this.list = list ;
		inflater = LayoutInflater.from(context) ;
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size() ;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position) ;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position ;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
			View view = inflater.inflate(R.layout.spuname_item, null) ;
			ImageView spitem_uhead = (ImageView) view.findViewById(R.id.spitem_uhead) ;
			TextView spitem_uname = (TextView) view.findViewById(R.id.spitem_uname) ;
			spitem_uhead.setImageDrawable(list.get(position).getUhead()) ;
			spitem_uname.setText(list.get(position).getScreenName()) ;
			return view;
	}

}
