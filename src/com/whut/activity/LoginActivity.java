package com.whut.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.pgyersdk.update.PgyUpdateManager;
import com.whut.component.service.VipNoticeService;
import com.whut.config.Constants;
import com.whut.config.RequestParam;
import com.whut.data.model.UserModel;
import com.whut.interfaces.IBaseView;
import com.whut.presenter.LoginPresenter;
import com.whut.seller.R;
import com.whut.util.JsonUtils;
import com.whut.util.BackAction;


/**
 * 登录界面
 * @author lx
 */
public class LoginActivity extends Activity implements IBaseView,OnClickListener{
	
	
	private Context context;
	//用户名
	private EditText userName;
	//密码
	private EditText password;
	//登录进度框
	private ProgressDialog dialog;
	//登录管理器
	private LoginPresenter presenter;
	
	private String userString;
	private String psdString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		PgyUpdateManager.register(this, Constants.APP_ID);
		initData();
		presenter = new LoginPresenter(this);
		
	}

	
	/**
	 * 初始化
	 */
	private void initData() {
		context = this;
		userName = (EditText)findViewById(R.id.user_name);
		password = (EditText)findViewById(R.id.password);
		dialog = new ProgressDialog(context);
		userString = null;
		psdString = null;
		getNamePwd();
	}



	/**
	 * 登录
	 */
	public void logIn(View v){
		dialog.show();
		dialog.setCancelable(false);
		if(!TextUtils.isEmpty(userName.getText())&& !TextUtils.isEmpty(password.getText())){
			userString = userName.getText().toString();
			psdString = password.getText().toString();
			dialog.cancel();
		}else{
			Toast.makeText(context, "用户名或密码不能为空！", Toast.LENGTH_SHORT).show();
			dialog.cancel();
			return;
		}
		System.out.println(userString+"  "+psdString);
		presenter.request(RequestParam.REQUEST_QUERY);
//		startActivity(new Intent(context,MainActivity.class));
	}
	
	/**
	 * 查看协议
	 */
	public void checkProtocol(View v){
		startActivity(new Intent(context,ProtocolActivity.class));
	}
	
	
	/**
	 * 解析返回结果
	 * @param json
	 * @return 是否登录成功
	 */
	private boolean parseResult(String json){
		boolean flag = false;
		JSONObject obj = JsonUtils.parseJson(json);
		if(obj!=null&&obj.getIntValue("code")==1){
			flag = true;
		}else{
			Toast.makeText(context, "登录失败!", Toast.LENGTH_SHORT).show();
		}
		return flag;
	}


	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		BackAction.slipToExit(this, ev);
		return super.dispatchTouchEvent(ev);
	}


	@Override
	public Object getInfo(int code){
		UserModel user = new UserModel();
		user.setUserName(userString);
		user.setPassword(psdString);
		System.out.println(userString+"  "+psdString);
		return user;
	}


	@Override
	public void setInfo(Object obj, int code) {
		dialog.cancel();
		if(parseResult((String)obj)){
			startActivity(new Intent(context,MainActivity.class));
//			Intent intent = new Intent(context,VipNoticeService.class);
//			startService(intent);
			LoginActivity.this.finish();
		}
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.save_pwd:
			saveNamePwd();
			break;

		default:
			break;
		}
	}

//使用SharedPreferences
	private void saveNamePwd() {
		// TODO Auto-generated method stub
		if(!TextUtils.isEmpty(userName.getText())&& !TextUtils.isEmpty(password.getText())){
			userString = userName.getText().toString();
			psdString = password.getText().toString();
			dialog.cancel();
		}else{
			Toast.makeText(context, "用户名或密码不能为空！", Toast.LENGTH_SHORT).show();
			dialog.cancel();
			return;
		}
		SharedPreferences sharedPreferences = getSharedPreferences("NamePwd", MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString("username", userString);
		editor.putString("password", psdString);
		System.out.println(userString+" "+psdString+" 传成功");
		editor.commit();
	}
	
	
	private void getNamePwd() {
		// TODO Auto-generated method stub
		SharedPreferences sharedPreferences = getSharedPreferences("NamePwd", MODE_PRIVATE);
		String name = sharedPreferences.getString("username", "");
		String pwd = sharedPreferences.getString("password", "");
		if((name == "")||(pwd == "")){
			return;
		}else{
			userName.setText(name);
			password.setText(pwd);
		}
		
	}
}
