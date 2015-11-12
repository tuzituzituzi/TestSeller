package com.whut.fragment;

import com.whut.seller.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class WifiPortalFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View portalFragment = inflater.inflate(R.layout.common_web_view,container, false);
		WebView webView = (WebView)portalFragment.findViewById(R.id.common_web_view);
		webView.setBackgroundResource(R.drawable.potal_bg);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl("http://219.153.20.141/web/store/index.html#/");
		return portalFragment;
	}
}
