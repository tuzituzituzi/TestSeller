package com.whut.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;

import com.whut.config.HttpMethod;
import com.whut.interfaces.IBasePresenter;

public class NetConnection implements IBasePresenter {

	public IBasePresenter presenter;

	public NetConnection(IBasePresenter presenter, final String url,
			final HttpMethod method, final int requestCode, final String... keyvalue) {
		// TODO Auto-generated constructor stub
		this.presenter = presenter;
		new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... params) {
				// TODO Auto-generated method stub
				String result = "";
				try {

					switch (method) {
					case POST:
						List<NameValuePair> list = new ArrayList<NameValuePair>();
						for(int i = 0; i < keyvalue.length; i += 2){
							NameValuePair pair = new BasicNameValuePair(keyvalue[i],keyvalue[1+i]);
							list.add(pair);
						}
						result = WebHelper.postJsonString(url, list);
						break;

					case GET:
						result = WebHelper.getJsonString(url);
					default:
						break;
					}
				} catch (Exception e) {
					// TODO: handle exception
					result = e.toString();
				}
				return result;
			}
			
			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				response(result, requestCode);
				super.onPostExecute(result);
			}

		}.execute();

	}

	@Override
	public void request(int requestCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void response(String data, int respondCode) {
		// TODO Auto-generated method stub
		presenter.response(data,respondCode );
	}

}
