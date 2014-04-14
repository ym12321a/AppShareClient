package com.ym.appshare.adapter;

import java.util.List;


import com.ym.appshare.activity.R;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.InstrumentationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridViewAdapter extends BaseAdapter {               //   网格显示全部app

	LayoutInflater inflater;
	List<PackageInfo> pkInfos;
	PackageManager pm ;
	public GridViewAdapter(Context context,List<PackageInfo> packageInfos,PackageManager pm) {
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
		View view = inflater.inflate(R.layout.appshow_gritem, null);
		TextView tv = (TextView) view.findViewById(R.id.gv_item_appname);
		ImageView iv = (ImageView) view.findViewById(R.id.gv_item_icon);
		
		//tv.setText(packageInfos.get(position).packageName);
		tv.setText(pkInfos.get(position).applicationInfo.loadLabel(pm));
		
		iv.setImageDrawable(pkInfos.get(position).applicationInfo.loadIcon(pm));
		
		return view;
	}

}
