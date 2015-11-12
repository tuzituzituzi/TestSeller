package com.whut.presenter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;

import com.whut.config.Constants;
import com.whut.config.RequestParam;
import com.whut.interfaces.IBasePresenter;
import com.whut.interfaces.IBaseView;
import com.whut.util.WebHelper;

public class WifiClientPresent implements IBasePresenter {



	public IBaseView view;

	public WifiClientPresent(IBaseView v) {
		// TODO Auto-generated constructor stub
		this.view = v;
	}

	@Override
	public void request(int requestCode) {
		// TODO Auto-generated method stub
		String id = view.getInfo(0).toString();
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		new APAsync(this).execute("http://jsonstub.com/ap/client/duration?id=21");
		
	}

	@Override
	public void response(String data, int respondCode) {
		// TODO Auto-generated method stub
		view.setInfo(data, 0);
		 
	}
	
	public class APAsync extends AsyncTask<String, Void, String> {
		
		private IBasePresenter presenter;
		

		public APAsync(IBasePresenter presenter) {
			// TODO Auto-generated constructor stub
			this.presenter = presenter;
		}



		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			StringBuilder res = new StringBuilder("");
			try {
				System.out.println(params[0]);
				HttpGet get = new HttpGet(params[0]);
				
				//get.addHeader("Cookie", Constants.USER_COOKIE);
				get.addHeader("JsonStub-User-Key",
						"97c55e90-cda4-4175-94cc-7bbc01120879");
				get.addHeader("JsonStub-Project-Key",
						"10b717d9-f875-4e1f-9d34-3fb2efdba6d7");
				get.addHeader("Content-Type", "application/json");
				
				HttpClient client = new DefaultHttpClient();
				HttpResponse response = client.execute(get);
				
				HttpEntity respondEntity = response.getEntity();
				InputStream is = respondEntity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				String line = "";
				while (null != (line = reader.readLine())) {
					res.append(line);
				}
			} catch (Exception e) {
				return "{\"code\":0,\"msg\":\""+e.toString()+"\"}";
			}
			return res.toString();

		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			System.out.println(result);
			super.onPostExecute(result);
			presenter.response(result,0);
		}


	}

}
