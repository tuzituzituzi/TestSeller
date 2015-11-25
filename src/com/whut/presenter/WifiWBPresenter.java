package com.whut.presenter;

import com.whut.config.Constants;
import com.whut.config.HttpMethod;
import com.whut.config.RequestParam;
import com.whut.interfaces.IBasePresenter;
import com.whut.interfaces.IBaseView;
import com.whut.util.NetConnection;

public class WifiWBPresenter implements IBasePresenter{
	
	private IBaseView view;
	
	public WifiWBPresenter(IBaseView view) {
		// TODO Auto-generated constructor stub
		this.view = view;
	}

	/***
	 * RequestParam.REQUEST_QUERY_ONE : 获取黑名单列表
	 * RequestParam.REQUEST_QUERY_TWO : 获取白名单列表
	 */

	@Override
	public void request(int requestCode) {
		// TODO Auto-generated method stub
		if(requestCode == RequestParam.REQUEST_QUERY_ONE){
			view.getInfo(requestCode);
			new NetConnection(this, RequestParam.GET_BLACK_WHITE_LIST+"?shopId="+Constants.STORE_ID+"&class=black", HttpMethod.GET, requestCode);
		}else if(requestCode == RequestParam.REQUEST_QUERY_TWO){
			view.getInfo(requestCode);
			new NetConnection(this, RequestParam.GET_BLACK_WHITE_LIST+"?shopId="+Constants.STORE_ID+"&class=white", HttpMethod.GET, requestCode);
		}
		
	}

	@Override
	public void response(String data, int respondCode) {
		// TODO Auto-generated method stub
		System.out.println(data);
		view.setInfo(data, respondCode);
	}

}
