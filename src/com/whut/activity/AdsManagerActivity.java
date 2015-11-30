package com.whut.activity;

import com.whut.seller.R;
import com.whut.util.SelectImage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * 广告管理界面
 * @author lx
 */
public class AdsManagerActivity extends Activity {
	
	private ImageView imageAdd;
	private ImageView firstImg;
	private ImageView secondImg;
	private ImageView thirdImg;
	private ImageView fourthImg;
	private ImageView fifthImg;
	private ImageView sixthImg;
	private ImageView seventhImg;
	//选择图片
	private SelectImage selectImage;
	//是否更换图片
			private boolean changeImage = false;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ads_manager);
		initdata();
	}


	private void initdata() {
		// TODO Auto-generated method stub
		imageAdd = (ImageView) findViewById(R.id.ads_add_img);
		firstImg = (ImageView) findViewById(R.id.first_pic);
		secondImg = (ImageView) findViewById(R.id.second_pic);
		thirdImg = (ImageView) findViewById(R.id.third_pic);
		fourthImg = (ImageView) findViewById(R.id.fourth_pic);
		fifthImg = (ImageView) findViewById(R.id.fifth_pic);
		sixthImg = (ImageView) findViewById(R.id.sixth_pic);
		seventhImg = (ImageView) findViewById(R.id.seventh_pic);
		selectImage = new SelectImage(this);
	}
	
	/**
	 * 点击ImageView事件处理函数
	 * @param v(ImageView)
	 */
	public void selectImage(View v){
		selectImage.selectWay();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Bitmap temp = null;
		temp = selectImage.getImage(requestCode, resultCode, data, false);
		if(temp!=null){
			imageAdd.setImageBitmap(temp);
			
			changeImage = true;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}
