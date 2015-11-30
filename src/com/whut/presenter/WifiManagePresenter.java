package com.whut.presenter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;

import com.pgyersdk.utils.p;
import com.whut.config.Constants;
import com.whut.config.HttpMethod;
import com.whut.config.RequestParam;
import com.whut.data.model.APListModel;
import com.whut.interfaces.IBasePresenter;
import com.whut.interfaces.IBaseView;
import com.whut.presenter.WifiClientPresent.APAsync;
import com.whut.util.NetConnection;
import com.whut.util.WebHelper;

public class WifiManagePresenter implements IBasePresenter {



	private IBaseView view;

	public WifiManagePresenter(IBaseView v) {
		// TODO Auto-generated constructor stub
		this.view = v;
	}

	@Override
	public void request(int requestCode) {
		// TODO Auto-generated method stub


		if (requestCode == RequestParam.REQUEST_QUERY) {
			view.getInfo(RequestParam.REQUEST_QUERY);
//			 new WifiAsyncRequest(this).execute(RequestParam.GET_AP_LIST);
			 new NetConnection(this, RequestParam.GET_AP_LIST+"?shopId="+Constants.STORE_ID, HttpMethod.GET, requestCode);
//			new WifiAsyncRequest(this).execute("http://jsonstub.com/ap/list");
		} else if (requestCode == RequestParam.REQUEST_UPDATE) {
			List<String> ssidAndpwd = (List<String>) view.getInfo(RequestParam.REQUEST_UPDATE);
//			new SetSsidPwdAsyncTask(this).execute(ssidAndpwd.get(0),ssidAndpwd.get(1));
			new NetConnection(this, RequestParam.UPDATE_AP_SSID, HttpMethod.POST, requestCode, "ssid",ssidAndpwd.get(0),"shopId",ssidAndpwd.get(1));
		} else if(requestCode == RequestParam.REQUEST_QUERY_ONE){
			List<String> upDown = (List<String>) view.getInfo(RequestParam.REQUEST_QUERY_ONE);
			new UpDownAsyncTask(this).execute(upDown.get(0),upDown.get(1));
//			new NetConnection(this,RequestParam.UPDATE_AP_SSID,HttpMethod.POST,requestCode,"mac",upDown.get(0),"time",upDown.get(1));
			
		}else if(requestCode == RequestParam.REQUEST_QUERY_TWO){
			String shopId = (String) view.getInfo(RequestParam.REQUEST_QUERY_TWO);
//			new GetSsidAsyncTask(this).execute(shopId);
			String url = RequestParam.GET_AP_SSID+"?shopId="+shopId;
			new NetConnection(this, url, HttpMethod.GET, requestCode, "");
		}else if(requestCode == RequestParam.REQUEST_QUERY_THREE){
			String maclist = (String) view.getInfo(RequestParam.REQUEST_QUERY_THREE);
			new APStatusAsyncTask(this).execute(maclist);
		}
		
	}

	@Override
	public void response(String data, int respondCode) {
		view.setInfo(data, respondCode);
	}

	public class WifiAsyncRequest extends AsyncTask<String, Void, String> {

		private IBasePresenter presenter;

		public WifiAsyncRequest(IBasePresenter iBasePresenter) {
			// TODO Auto-generated constructor stub
			this.presenter = iBasePresenter;
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			StringBuilder res = new StringBuilder("");
			try {
				HttpGet get = new HttpGet(params[0]);
				/***
				 * 实际项目的头 
				 */
				get.addHeader("Cookie", Constants.USER_COOKIE);
//				get.addHeader("JsonStub-User-Key",
//						"97c55e90-cda4-4175-94cc-7bbc01120879");
//				get.addHeader("JsonStub-Project-Key",
//						"10b717d9-f875-4e1f-9d34-3fb2efdba6d7");
//				get.addHeader("Content-Type", "application/json");
				HttpClient client = new DefaultHttpClient();
				HttpResponse response = client.execute(get);
				HttpEntity respondEntity = response.getEntity();
				InputStream is = respondEntity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is));
				String line = "";
				while (null != (line = reader.readLine())) {
					res.append(line);
				}
			} catch (Exception e) {
				return "{\"code\":0,\"msg\":\"" + e.toString() + "\"}";
			}
			return res.toString();
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			System.out.println(result);
			presenter.response(result, RequestParam.REQUEST_QUERY);
			super.onPostExecute(result);
		}

	}
	
//	public class SetSsidPwdAsyncTask extends AsyncTask<String, Void, String>{
//		
//		private IBasePresenter presenter;
//
//		public SetSsidPwdAsyncTask(IBasePresenter iBasePresenter) {
//			// TODO Auto-generated constructor stub
//			this.presenter = iBasePresenter;
//		}
//
//		@Override
//		protected String doInBackground(String... params) {
//			// TODO Auto-generated method stub
//			String web = "";
//			List<NameValuePair> ssidNameValue = new ArrayList<NameValuePair>();
//			NameValuePair ssidPair = new BasicNameValuePair("ssid",params[0]);
//			ssidNameValue.add(ssidPair);
//			NameValuePair shopidPair = new BasicNameValuePair("shopId","25");
//			ssidNameValue.add(shopidPair);
//			System.out.println(ssidNameValue);
//			try {
//				web = WebHelper.postJsonString(RequestParam.UPDATE_AP_SSID, ssidNameValue);
//				System.out.println(web);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				web = e.toString();
//			}
//			return web;
//		}
//		
//		@Override
//		protected void onPostExecute(String result) {
//			// TODO Auto-generated method stub
//			System.out.println("result="+result);
//			presenter.response(result, RequestParam.REQUEST_UPDATE);
//			super.onPostExecute(result);
//		}
//
//	}

	
	public class UpDownAsyncTask extends AsyncTask<String, Void, String>{
		
		private IBasePresenter presenter;

		public UpDownAsyncTask(IBasePresenter wifiManagePresenter) {
			// TODO Auto-generated constructor stub
			this.presenter = wifiManagePresenter;
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String web = "";
			List<NameValuePair> upDownPairs = new ArrayList<NameValuePair>();
			NameValuePair mac = new BasicNameValuePair("mac",params[0]);
			upDownPairs.add(mac);
			NameValuePair time = new BasicNameValuePair("time",params[1]);
			upDownPairs.add(time);
			System.out.println(upDownPairs);
			try {
				web = WebHelper.postJsonString(RequestParam.UPDATE_AP_SSID, upDownPairs);
				System.out.println(web);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				web = e.toString();
			}
			return web;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
//			System.out.println("result="+result);
			result = "{\"code\":1,\"msg\":{}}";
			presenter.response(result, RequestParam.REQUEST_QUERY_ONE);
			super.onPostExecute(result);
		}

	}
	
//	public class GetSsidAsyncTask extends AsyncTask<String, Void, String>{
//		
//		public IBasePresenter presenter;
//
//		public GetSsidAsyncTask(IBasePresenter wifiManagePresenter) {
//			// TODO Auto-generated constructor stub
//			this.presenter = wifiManagePresenter;
//		}
//
//		@Override
//		protected String doInBackground(String... params) {
//			// TODO Auto-generated method stub
//			String ssid = "";
//			try {
//				ssid = WebHelper.getJsonString(RequestParam.GET_AP_SSID+"?shopId="+params[0]);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				ssid = e.toString();
//			}
//			
//			return ssid;
//		}
//		
//		@Override
//		protected void onPostExecute(String result) {
//			// TODO Auto-generated method stub
//			System.out.println(result);
//			presenter.response(result, RequestParam.REQUEST_QUERY_TWO);
//			super.onPostExecute(result);
//		}
//
//	}
	
public class APStatusAsyncTask extends AsyncTask<String, Void, String>{
		
		private IBasePresenter presenter;

		public APStatusAsyncTask(IBasePresenter wifiManagePresenter) {
			// TODO Auto-generated constructor stub
			this.presenter = wifiManagePresenter;
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String web = "";
			List<NameValuePair> upDownPairs = new ArrayList<NameValuePair>();
			
			try {
				HttpPost post = new HttpPost("http://jsonstub.com/oa/ap/run-status");
				post.addHeader("JsonStub-User-Key",
						"97c55e90-cda4-4175-94cc-7bbc01120879");
				post.addHeader("JsonStub-Project-Key",
						"10b717d9-f875-4e1f-9d34-3fb2efdba6d7");
				post.addHeader("Content-Type", "application/json");
				List<NameValuePair> list = new ArrayList<NameValuePair>();
				NameValuePair pair = new BasicNameValuePair("maclist", params[0]);
				list.add(pair);
				HttpEntity entity = new UrlEncodedFormEntity(list);
				post.setEntity(entity);
				HttpClient client = new DefaultHttpClient();
				HttpResponse response = client.execute(post);
				if (response == null){
					return "error";
				}
				HttpEntity entity2 = response.getEntity();
				InputStream is = entity2.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				String line = "";
				StringBuilder res = new StringBuilder("");
				while (null != (line = reader.readLine())) {
					res.append(line);
				}
				web =  res.toString();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				web = e.toString();
			}
			return web;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			System.out.println("result="+result);
			presenter.response(result, RequestParam.REQUEST_QUERY_THREE);
			super.onPostExecute(result);
		}

	}

}
