package com.whut.util;

import com.whut.seller.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class WifiPopupDialog extends Dialog{

	public WifiPopupDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
	}

	protected WifiPopupDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		// TODO Auto-generated constructor stub
	}

	public WifiPopupDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public static class Builder{
		private Context context;
		private String addText;
		private DialogInterface.OnClickListener positiveButtonClickListener;
		private DialogInterface.OnClickListener negativeButtonClickListener;
		
		public Builder(Context context){
			this.context = context;
		}
		
		public String getAddText() {
			return addText;
		}
		
		public Builder setAddText(String addText) {
			this.addText = addText;
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
        
        public WifiPopupDialog create(){
        	
        	LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			final WifiPopupDialog dialog = new WifiPopupDialog(context,R.style.Dialog);
			View layout = inflater.inflate(R.layout.wifi_popup, null);
			dialog.addContentView(layout, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        	final TextView addWB = (TextView) layout.findViewById(R.id.tv_add_wb);
        	addWB.setText("确认添加至"+addText);
        	if(positiveButtonClickListener != null){
        		layout.findViewById(R.id.wb_positive_btn).setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						positiveButtonClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
					}
				});
        	}
        	if(negativeButtonClickListener != null){
        		layout.findViewById(R.id.wb_negative_btn).setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						negativeButtonClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
					}
				});
        	}
			
			return dialog;
        	
        }
		
		
	}

}
