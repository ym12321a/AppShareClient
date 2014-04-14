package com.ym.appshare.adapter;

import java.util.ArrayList;


import java.util.HashMap;
import java.util.List;

import com.ym.appshare.activity.R;
import com.ym.appshare.util.LoadImage;
import com.ym.appshare.util.LoadImage.LimageCallBack;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FriendshipAdapter extends BaseAdapter {

	List<HashMap<String,Object>> list = null ;
	LayoutInflater layout = null ;
	public FriendshipAdapter(Context context) {
		list = new ArrayList<HashMap<String,Object>>() ;
		layout = LayoutInflater.from(context) ;
	}

	public void setData(List<HashMap<String,Object>> list){
		this.list = list ;
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
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null ;
		if(convertView == null){
			view  = layout.inflate(R.layout.friendship_item, null) ;
		}else{
			view = convertView ;
		}
	//	System.out.println("---------------->"+list.size()) ;
		TextView tv_fhname = (TextView) view.findViewById(R.id.tv_fhname) ;
		TextView tv_fhdes = (TextView) view.findViewById(R.id.tv_fhdes) ;
		tv_fhname.setText(list.get(position).get("screen_name").toString()) ;
		tv_fhdes.setText(list.get(position).get("description").toString()) ;
		final ImageView iv_fhuhead = (ImageView) view.findViewById(R.id.iv_fhuhead) ;
		LoadImage loadImage = new LoadImage(list.get(position).get("image_url").toString()) ;
		loadImage.getImage(new LimageCallBack() {
			
			@Override
			public void getDrawable(Drawable drawable) {
				// TODO Auto-generated method stub
				iv_fhuhead.setImageDrawable(drawable) ;
			}
		}) ;
	
  	return view ;
	}

}
