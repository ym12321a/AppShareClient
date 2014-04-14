package com.ym.appshare.adapter;

import java.util.List;

import com.ym.appshare.activity.R;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {                 //   列表显示全部app
	LayoutInflater inflater;
	List<PackageInfo> pkInfos;
	PackageManager pm ;
	public ListViewAdapter(Context context,List<PackageInfo> packageInfos,PackageManager pm ) {
		inflater = LayoutInflater.from(context);
		this.pkInfos = packageInfos;
		this.pm = pm ;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pkInfos.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return pkInfos.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = inflater.inflate(R.layout.appshow_listitem, null);
		TextView tv_appname = (TextView) view.findViewById(R.id.lv_item_appname);
		TextView tv_versioncode = (TextView) view.findViewById(R.id.lv_item_versionCode) ;
		ImageView iv = (ImageView) view.findViewById(R.id.lv_icon);
		//tv.setText(packageInfos.get(position).packageName);
		tv_appname.setText("应用名："+pkInfos.get(position).applicationInfo.loadLabel(pm));
	//	tv_versioncode.setText(pkInfos.get(position).versionCode);
		tv_versioncode.setText("版本号： "+pkInfos.get(position).versionCode) ;      //  显示版本   
		iv.setImageDrawable(pkInfos.get(position).applicationInfo.loadIcon(pm));
		
		return view;
	}
}
