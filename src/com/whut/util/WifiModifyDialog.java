package com.whut.util;


import com.whut.seller.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class WifiModifyDialog extends Dialog{

	public WifiModifyDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
	}

	public WifiModifyDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	
	public static class Builder{
		private Context context;
		private String wifiName;
		private String wifiPwd;
		private String mac;
		private String onlineTime;
		private View view;
		private DialogInterface.OnClickListener positiveButtonClickListener;
		private DialogInterface.OnClickListener negativeButtonClickListener;
		
		public Builder(Context context){
			this.context = context;
		}
		
		public Builder setWifiName(String wifiname){
			this.wifiName = wifiname;
			return this;
		}
		
		public Builder setWifiPwd(String wifiPwd){
			this.wifiPwd = wifiPwd;
			return this;
		}
		
		public Builder setMac(String mac){
			this.mac = mac;
			return this;
		}
		
		public Builder setOnlineTime(String onlineTime){
			this.onlineTime = onlineTime;
			return this;
		}
		
		public Builder setPositiveButton( 
                DialogInterface.OnClickListener listener) {  
            this.positiveButtonClickListener = listener;  
            return this;  
        }  
  
        public Builder setNegativeButton( 
                DialogInterface.OnClickListener listener) {  
            this.negativeButtonClickListener = listener;  
            return this;  
        }  
		
		public WifiModifyDialog create(){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final WifiModifyDialog dialog = new WifiModifyDialog(context,R.style.Dialog);
			View layout = inflater.inflate(R.layout.wifi_dialog, null);
			dialog.addContentView(layout, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			
			((EditText)layout.findViewById(R.id.ed_ap_name)).setText(wifiName);
			((EditText)layout.findViewById(R.id.ed_ap_pwd)).setText(wifiPwd);
			((TextView)layout.findViewById(R.id.tv_ap_mac)).setText(mac);
			((TextView)layout.findViewById(R.id.tv_online_time)).setText(onlineTime);
			
			if(positiveButtonClickListener != null){
				((Button)layout.findViewById(R.id.wifi_positive_btn)).setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
					}
				});
			}
			
			if(negativeButtonClickListener != null){
				((Button)layout.findViewById(R.id.wifi_negative_btn)).setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
					}
				});
			}
			return dialog;
			
		}
		
	}

}
