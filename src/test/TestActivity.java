package test;

import java.util.List;

import com.ym.appshare.activity.R;
import com.ym.appshare.entity.WeiboUser;
import com.ym.appshare.service.WeiboUserDao;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class TestActivity extends Activity {
	
	/**
	 * @Title: main 
	 * @Description: TODO(������һ�仰�����������������) 
	 * @param @param args    �趨�ļ� 
	 * @return void    �������� 
	 * @throws 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.test) ;
		
		WeiboUserDao dao = new WeiboUserDao(getApplication()) ;
		List<WeiboUser> list = dao.queryAll() ;
		System.out.println("--------------"+list.size()) ;
	/*	for(WeiboUser w:list){
			System.out.println(w.toString());
		}*/
		ImageView d = (ImageView) super.findViewById(R.id.i) ;
		
		WeiboUserDao dao2 = new WeiboUserDao(getBaseContext()) ;
		//System.out.println("--------------"+dao2.findUserByUser_id("2621981577") ) ;
		WeiboUser user = dao2.findUserByUser_id("2621981577") ;
		d.setImageDrawable(user.getUhead()) ;
	}

}
