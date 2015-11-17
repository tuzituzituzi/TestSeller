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

import com.whut.config.Constants;
import com.whut.config.RequestParam;
import com.whut.data.model.APListModel;
import com.whut.interfaces.IBasePresenter;
import com.whut.interfaces.IBaseView;
import com.whut.presenter.WifiClientPresent.APAsync;
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

		view.getInfo(0);

		// 0代表是刷新页面
		if (requestCode == RequestParam.REQUEST_QUERY) {
			// new WifiAsyncRequest(this).execute(RequestParam.GET_AP_LIST);
			new WifiAsyncRequest(this).execute("http://jsonstub.com/ap/list");
		} else if (requestCode == RequestParam.REQUEST_UPDATE) {
			String ssid = (String) view.getInfo(1);
			new SsidAsyncTask(this).execute(ssid);
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
				 * 实际项目的头 get.addHeader("Cookie", Constants.USER_COOKIE);
				 */
				get.addHeader("JsonStub-User-Key",
						"97c55e90-cda4-4175-94cc-7bbc01120879");
				get.addHeader("JsonStub-Project-Key",
						"10b717d9-f875-4e1f-9d34-3fb2efdba6d7");
				get.addHeader("Content-Type", "application/json");
				System.out.println("test1");
				HttpClient client = new DefaultHttpClient();
				HttpResponse response = client.execute(get);
				System.out.println("test2");
				HttpEntity respondEntity = response.getEntity();
				InputStream is = respondEntity.getContent();
				System.out.println("test3");
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
			presenter.response(result, 0);
			super.onPostExecute(result);
		}

	}
	
	public class SsidAsyncTask extends AsyncTask<String, Void, String>{
		
		private IBasePresenter presenter;

		public SsidAsyncTask(IBasePresenter iBasePresenter) {
			// TODO Auto-generated constructor stub
			this.presenter = iBasePresenter;
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String web = "";
			List<NameValuePair> ssidNameValue = new ArrayList<NameValuePair>();
			NameValuePair ssidPair = new BasicNameValuePair("ssid","haha");
			ssidNameValue.add(ssidPair);
			NameValuePair shopidPair = new BasicNameValuePair("shopId",params[0]);
			ssidNameValue.add(shopidPair);
			System.out.println(ssidNameValue);
			try {
				web = WebHelper.getJsonString("http://219.153.20.141/web/store/service/201/node-tair-web/ap/ssid/set", ssidNameValue);
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
			presenter.response(result, 1);
			super.onPostExecute(result);
		}

	}


}
