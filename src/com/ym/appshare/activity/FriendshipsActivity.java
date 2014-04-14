package com.ym.appshare.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.api.FriendshipsAPI;
import com.weibo.sdk.android.api.StatusesAPI;
import com.weibo.sdk.android.api.WeiboAPI.FEATURE;
import com.weibo.sdk.android.net.RequestListener;
import com.ym.appshare.adapter.FriendshipAdapter;
import com.ym.appshare.util.ToolsUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class FriendshipsActivity extends Activity implements RequestListener,OnScrollListener{
	
	private static final int ONCOMPLETE = 0;
	private ListView lv ;
	FriendshipAdapter adapter ;
	public static Oauth2AccessToken accessToken;
	public ProgressDialog pd ;
	String temp ;
	// List<HashMap<String, Object>> lists  ;
	List<HashMap<String, Object>> list  = new ArrayList<HashMap<String,Object>>() ;
	List<HashMap<String, Object>> newlist ;
	View footerView ;
	
	 Handler handler = new Handler(){

		@Override
		public void handleMessage(android.os.Message msg) {
			// TODO Auto-generated method stub
			
				list.addAll(list.size(), (List<HashMap<String, Object>>) msg.obj) ;
				adapter.setData(list) ;
				adapter.notifyDataSetChanged();
				if(lv.getFooterViewsCount() > 0){
					lv.removeFooterView(footerView) ;
				}
				isEnd = true ;
				pd.dismiss() ;
				
			
		};
		 
	 } ;
	 
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_friendship) ;
	//	System.out.println("-----------f userId------"+ToolsUtil.saveUser.getScreenName());
		//System.out.println("-----------f userId------"+ToolsUtil.userId);
		lv = (ListView) super.findViewById(R.id.lv_friendship) ;
		footerView = LayoutInflater.from(this).inflate(R.layout.friendship_footer, null) ;
		this.accessToken = new Oauth2AccessToken(ToolsUtil.saveUser.getToken(), ToolsUtil.saveUser.getExpires_in()) ;
	//	FriendshipsAPI friendshipsAPI = new FriendshipsAPI(accessToken) ;
		friendshipsAPI = new FriendshipsAPI(accessToken) ;
		friendshipsAPI.friends(Long.parseLong(ToolsUtil.saveUser.getUid()), 20, 0, true,this) ;
		pd = ProgressDialog.show(this, "提示","正在加载,请稍后......",true,false) ;
		adapter = new FriendshipAdapter(this) ;
		lv.addFooterView(footerView) ;
		lv.setAdapter(adapter) ;
		lv.removeFooterView(footerView) ;
		lv.setOnItemClickListener(new MyListListener()) ;
		lv.setOnScrollListener(this);
		
//		FriendshipsAPI friendshipsAPI = new FriendshipsAPI(accessToken) ;
//		friendshipsAPI.friends(Long.parseLong(ToolsUtil.saveUser.getUid()), 20, 0, true, listener) ;
	//	new MyTask().execute() ;
	}
	@Override
	public void onComplete(String arg0) {
		// TODO Auto-generated method stub
		//System.out.println("--------------1---------------"+arg0) ;
	//	pd = ProgressDialog.show(this, "提示","正在加载,请稍后......",true,false) ;
		   newlist = new ArrayList<HashMap<String,Object>>() ;
			HashMap<String, Object> map = null ;
			try {
				JSONObject jsonObject = new JSONObject(arg0) ;
				JSONArray jsonArray = jsonObject.getJSONArray("users") ;
				for(int i=0;i<jsonArray.length();i++){
					JSONObject jsonObject2 = jsonArray.getJSONObject(i) ;
					map = new HashMap<String, Object>() ;
					map.put("screen_name",jsonObject2.getString("screen_name")) ;
					map.put("description", jsonObject2.getString("description")) ;
					map.put("image_url", jsonObject2.getString("profile_image_url")) ;
					newlist.add(map) ;
				}	
				Message message = new Message() ;
				message.obj = newlist ;
				handler.sendMessage(message) ;
				/*list.addAll(list.size(), newlist) ;
				adapter.setData(list) ;
			//	lv.removeFooterView(footerView) ;
				handler.sendEmptyMessage(ONCOMPLETE);*/
			//	System.out.println("--------------2---------------"+list.get(0).get("screen_name").toString()) ;
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	@Override
	public void onError(WeiboException arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onIOException(IOException arg0) {
		// TODO Auto-generated method stub
		
	}

	class MyListListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
	//		Toast.makeText(FriendshipsActivity.this, list.get(position).get("screen_name").toString(), 1).show() ;
			Toast.makeText(FriendshipsActivity.this,position+"", 1).show() ;
		}
		
	}
	int number = 20 ;
	int totalPage = 5 ;
	private boolean isEnd = true ;
    private FriendshipsAPI friendshipsAPI ;
    
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount,  int totalItemCount) {
		// TODO Auto-generated method stub
		System.out.println("----firstVisibleItem="+firstVisibleItem+"、-----visibleItemCount="+visibleItemCount+"、----totalItemCount="+totalItemCount) ;
		int lastPosition = lv.getLastVisiblePosition() ;             
		int currentPage = 1 ;
		int nextPage ;
		final int totalCount = totalItemCount ;
		if(totalItemCount > 0){
			if((lastPosition+1) == totalItemCount){  
				currentPage = totalItemCount%number==0?totalItemCount/number:totalItemCount/number+1 ;    //   100%20==0?100/20:100/20+1
				nextPage = currentPage + 1 ;
				if(nextPage <= totalPage && isEnd ){
					lv.addFooterView(footerView) ;
					isEnd = false ;
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							friendshipsAPI.friends(Long.parseLong(ToolsUtil.saveUser.getUid()), 20, totalCount+1, true,FriendshipsActivity.this) ;	
						}
					}).start() ;
				
					}
				}
		}
		
		}
		
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
		
	}
	
 /* class	MyTask extends AsyncTask<Void, Void, List<HashMap<String, Object>>>{
	 
	@Override
	protected List<HashMap<String, Object>> doInBackground(Void... params) {
		// TODO Auto-generated method stub
	//	  final List<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>() ;
		FriendshipsAPI friendshipsAPI = new FriendshipsAPI(accessToken) ;
		friendshipsAPI.friends(Long.parseLong(ToolsUtil.saveUser.getUid()), 20, 0, true, new RequestListener() {
			
			@Override
			public void onIOException(IOException arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onError(WeiboException arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onComplete(String arg0) {
				// TODO Auto-generated method stub
				  List<HashMap<String, Object>> list = new ArrayList<HashMap<String,Object>>() ;
				HashMap<String, Object> map = null ;
				try {
					JSONObject jsonObject = new JSONObject(arg0) ;
					JSONArray jsonArray = jsonObject.getJSONArray("users") ;
					for(int i=0;i<jsonArray.length();i++){
						JSONObject jsonObject2 = jsonArray.getJSONObject(i) ;
						map = new HashMap<String, Object>() ;
						map.put("screen_name",jsonObject2.getString("screen_name") ) ;
						map.put("description", jsonObject2.getString("description")) ;
						map.put("image_url", jsonObject2.getString("profile_image_url")) ;
						list.add(map) ;
					}	
					ToolsUtil.lists = list ;
					System.out.println("--------------2---------------"+ToolsUtil.lists.size()) ;
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}) ;
		System.out.println("--------------1---------------"+ToolsUtil.lists.size()) ;
		return ToolsUtil.lists ;
	}

	@Override
	protected void onPostExecute(List<HashMap<String, Object>> result) {
		// TODO Auto-generated method stub
		adapter.setData(result) ;
	//	adapter.notifyDataSetChanged() ;
		System.out.println("-------------result --------"+result.size()) ;
		lv.setAdapter(adapter) ;
		super.onPostExecute(result);
	}
	  
  }*/
	
}
