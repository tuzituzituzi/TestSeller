package com.whut.activity;

import com.whut.fragment.WifiAPFragment;
import com.whut.fragment.WifiClientFragment;
import com.whut.fragment.WifiPortalFragment;
import com.whut.seller.R;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class WifiManageActivityNew extends Activity implements OnClickListener{
	
	private FragmentManager fragmentManager;
	private WifiPortalFragment portalFragment;
	private WifiAPFragment apFragment;
	
	private ImageView apManage;
	private ImageView apPortal;
	private ImageView apNone;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wifimanage_new);
		initViews();
		fragmentManager = getFragmentManager();
		setTabSelection(0);
		
	}
	
	private void initViews() {
		// TODO Auto-generated method stub
		((TextView) findViewById(R.id.activity_title)).setText("AP管理");
		apManage = (ImageView) findViewById(R.id.wifi_ap_manage);
		apPortal = (ImageView) findViewById(R.id.wifi_ap_portal);
		apNone = (ImageView) findViewById(R.id.wifi_ap_none);
		apManage.setOnClickListener(this);
		apPortal.setOnClickListener(this);
		apManage.setImageResource(R.drawable.apmanage_1);
	}

	private void setTabSelection(int i) {
		// TODO Auto-generated method stub
		System.out.println("i="+i);
		FragmentTransaction tran = fragmentManager.beginTransaction();
		hideFragments(tran);
		switch (i) {
		case 0:
			if(apFragment == null){
				apFragment = new WifiAPFragment();
				tran.add(R.id.wifi_frame, apFragment);
			}else{
				
				tran.show(apFragment);
			}
			apManage.setImageResource(R.drawable.apmanage_1);
			apPortal.setImageResource(R.drawable.portal_2);
			break;
			
		case 1:
			if(portalFragment == null){
				portalFragment = new WifiPortalFragment();
				tran.add(R.id.wifi_frame,portalFragment);
			}else {
				
				tran.show(portalFragment);
			}
			apManage.setImageResource(R.drawable.apmanage_2);
			apPortal.setImageResource(R.drawable.portal_1);
			break;
			

	
		default:
			break;
		}
		tran.commit();
	}

	

	private void hideFragments(FragmentTransaction tran) {
		// TODO Auto-generated method stub
		if(apFragment!=null){
			tran.hide(apFragment);
		}
		if(portalFragment!=null){
			tran.hide(portalFragment);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.wifi_ap_manage:
			setTabSelection(0);
			break;
		case R.id.wifi_ap_portal:
			setTabSelection(1);
			break;
			
		case R.id.wifi_ap_none:
			startActivity(new Intent(this,WifiWBManageActivity.class));
			break;
		default:
			break;
		}
		
	}
	
	public void onBack(View v) {
		this.finish();
	}

}
