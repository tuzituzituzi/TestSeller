package com.whut.fragment;

import com.whut.interfaces.IBaseView;
import com.whut.seller.R;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class WifiBlackFragment extends Fragment implements IBaseView{
	
	private LayoutInflater inflater;
	private Context context;
	private View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View blackFragment = inflater.inflate(R.layout.wifi_black_list, null);
		this.inflater = inflater;
		context = getActivity();
		this.view = blackFragment;
		
		
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public Object getInfo(int code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInfo(Object obj, int code) {
		// TODO Auto-generated method stub
		
	}

}
