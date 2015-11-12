package com.whut.fragment;

import com.whut.seller.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class WifiModifyFragment extends Fragment{
	
	private String name;
	private EditText apName;
	private LayoutInflater inflater;
	private View modifyView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View modifyFragment = inflater.inflate(R.layout.wifi_modify, container, false);
		name = getArguments().getString("mac");
		
		this.inflater = inflater;
		this.modifyView = modifyFragment;
		initData();
		
		return modifyFragment;
	}

	private void initData() {
		// TODO Auto-generated method stub
		apName = (EditText) modifyView.findViewById(R.id.ed_ap_name);
		apName.setText(name);
	}

}
