package com.whut.activity;

import com.whut.fragment.WifiBlackFragment;
import com.whut.fragment.WifiWhiteFragment;
import com.whut.interfaces.IBaseView;
import com.whut.seller.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

@SuppressLint("ResourceAsColor")
public class WifiWBManageActivity extends Activity implements OnClickListener{
	
	private FragmentManager fragmentManager;
	private WifiWhiteFragment whiteFragment;
	private WifiBlackFragment blackFragment;
	
	private View getBlackList;
	private View getWhiteList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wb_manage);
		initview();
		fragmentManager = getFragmentManager();
		setTabSelection(0);
	}


	private void initview() {
		// TODO Auto-generated method stub
		getBlackList = findViewById(R.id.get_black_list);
		getWhiteList = findViewById(R.id.get_white_list);
	}
	
	private void setTabSelection(int i) {
		// TODO Auto-generated method stub
		System.out.println("i="+i);
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideFragment(transaction);
		switch (i) {
		case 0:
			if(blackFragment == null){
				blackFragment = new WifiBlackFragment();
				transaction.add(R.id.wb_list, blackFragment);
			}else{
				transaction.show(blackFragment);
			}
//			System.out.println("black-gray");
//			getBlackList.setBackgroundColor(R.color.wifi_bg);
			break;

		case 1:
			if(whiteFragment == null){
				whiteFragment = new WifiWhiteFragment();
				transaction.add(R.id.wb_list, whiteFragment);
			}else{
				transaction.show(whiteFragment);
			}
			System.out.println("white-gray");
//			getWhiteList.setBackgroundColor(R.color.wifi_bg);
		default:
			break;
		}
		transaction.commit();
	}

	private void hideFragment(FragmentTransaction transaction) {
		// TODO Auto-generated method stub
//		System.out.println("white");
//		getWhiteList.setBackgroundColor(R.color.white);
//		getBlackList.setBackgroundColor(R.color.white);
		if(blackFragment != null){
			transaction.hide(blackFragment);
		}
		if(whiteFragment != null){
			transaction.hide(whiteFragment);
		}
		
	}

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.get_black_list:
			setTabSelection(0);
			break;
		case R.id.get_white_list:
			setTabSelection(1);
			break;
		default:
			break;
		}
	}
	
	public void onBack(View v) {
		this.finish();
	}


	
}
