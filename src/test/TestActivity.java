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
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param args    设定文件 
	 * @return void    返回类型 
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
