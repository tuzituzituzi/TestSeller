package com.whut.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pgyersdk.tasks.c;
import com.whut.activity.WebPageActivity;
import com.whut.activity.WiFiManageActivity;
import com.whut.activity.WifiClientActivity;
import com.whut.activity.WiFiManageActivity.ViewHolder;
import com.whut.activity.WiFiManageActivity.WifiAdapter;
import com.whut.config.Constants;
import com.whut.config.RequestParam;
import com.whut.data.model.APListModel;
import com.whut.interfaces.IBaseView;
import com.whut.presenter.WifiManagePresenter;
import com.whut.seller.R;
import com.whut.util.JsonUtils;
import com.whut.util.PullToRefreshListView;
import com.whut.util.WebHelper;
import com.whut.util.WifiModifyDialog;
import com.whut.util.PullToRefreshBase.OnLastItemVisibleListener;
import com.whut.util.PullToRefreshBase.OnRefreshListener;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

//模拟的数据"mac": "60:cd:a9:00:9c:90",
//"time" 14479264058
//post  "time"  "mac"

public class WifiAPFragment extends Fragment implements OnClickListener,
		IBaseView {

	private PullToRefreshListView pullToRefreshListView;
	private Context context;
	private ListView wifiList;
	private WifiAdapter adapter;
	private List<APListModel> list;
	private WifiManagePresenter presenter;
	private String Mallid;
	private int TIME = 50000;
	private ProgressDialog pd;
	private LayoutInflater inflater;
	private List<String> ssidAndpwd;
	private List<String> updownList;
	private int listNum;
	private String ssid;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View apFragment = inflater.inflate(R.layout.wifi_aplist, container,
				false);
		pullToRefreshListView = (PullToRefreshListView) apFragment
				.findViewById(R.id.wifi_list);
		initRefreshListView();
		wifiList = pullToRefreshListView.getRefreshableView();

		this.inflater = inflater;
		initData();
		// 定时刷新
		// handler.postDelayed(runnable, TIME);
		wifiList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				System.out.println(position);
				if (list.size() < position) {

				} else {
					// 旧版的Fragment
					// WifiClientFragment clientFragment = new
					// WifiClientFragment();
					// FragmentTransaction ft =
					// getFragmentManager().beginTransaction();
					// Bundle bundle = new Bundle();
					// bundle.putString("mac", list.get(position).getMac());
					// clientFragment.setArguments(bundle);
					// ft.replace(R.id.wifi_frame, clientFragment,"apFragment");
					// ft.addToBackStack("apFragment");
					// ft.commit();
					Bundle bundle = new Bundle();
					Intent i = new Intent(getActivity(),
							WifiClientActivity.class);
					bundle.putString("mac", list.get(position-1).getMac());
					i.putExtras(bundle);
					startActivity(i);

				}

			}
		});
		return apFragment;
	}

	private void initRefreshListView() {
		// TODO Auto-generated method stub

		pullToRefreshListView.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				// 下拉刷新
				Toast.makeText(context, "下拉刷新", Toast.LENGTH_SHORT).show();
				pullToRefreshListView.onRefreshComplete();
				presenter.request(RequestParam.REQUEST_QUERY);
				System.out.println("下拉刷新");
			}
		});

		pullToRefreshListView
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
					@Override
					public void onLastItemVisible() {
						// TODO Auto-generated method stub
						// 上拉刷新
						Toast.makeText(context, "上拉刷新", Toast.LENGTH_SHORT)
								.show();
					}
				});
	}

	private void initData() {
		// TODO Auto-generated method stub
		context = getActivity();
		list = new ArrayList<APListModel>();
		adapter = new WifiAdapter();
		ssidAndpwd = new ArrayList<String>();
		updownList = new ArrayList<String>();
		ssid = "";
		presenter = new WifiManagePresenter(this);
		presenter.request(RequestParam.REQUEST_QUERY);
		wifiList.setAdapter(adapter);
	}

	public class WifiAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return list.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(final int position, View view, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;
			if (view == null) {
				holder = new ViewHolder();
				view = inflater.inflate(R.layout.wifi_aplist_item, null);

				holder.wifiAlias = (TextView) view
						.findViewById(R.id.wifi_name);
				holder.wifiOnlineNumber = (TextView) view
						.findViewById(R.id.wifi_online_number);
				holder.wifiEditFlag = (ImageView) view
						.findViewById(R.id.wifi_edit_flag);
				holder.wifiUpload = (TextView) view
						.findViewById(R.id.wifi_upload);
				holder.wifiDownload = (TextView) view
						.findViewById(R.id.wifi_download);
				holder.wifiManage = (Button) view
						.findViewById(R.id.btn_ap_manage);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}

			holder.wifiAlias.setText(list.get(position).getAlias());
			System.out.println(list.get(position).getOnline());
			holder.wifiOnlineNumber
					.setText("" + list.get(position).getOnline());
			// holder.wifiEditFlag.setBackgroundResource(R.drawable.wifi_edit_flag);
			
			holder.wifiUpload.setText(list.get(position).getUpload() + "KB/s");
			holder.wifiDownload.setText(list.get(position).getDownload()
					+ "KB/s");

			holder.wifiManage.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					// 上一个版本的
					// WifiModifyFragment modifyFragment = new
					// WifiModifyFragment();
					// FragmentTransaction ft =
					// getFragmentManager().beginTransaction();
					//
					// Bundle bundle = new Bundle();
					// bundle.putString("mac", list.get(position).getMac());
					// modifyFragment.setArguments(bundle);
					// ft.replace(R.id.wifi_frame, modifyFragment,"apFragment");
					// ft.addToBackStack("apFragment");
					// ft.commit();

					// AlertDialog.Builder builder = new
					// AlertDialog.Builder(context);
					// View view = inflater.inflate(R.layout.wifi_dialog, null);
					//
					// builder.setTitle("修改管理");
					// builder.setView(view);
					// builder.setPositiveButton("登录", null);
					// builder.setNegativeButton("取消", null);
					// System.out.println("dianlemei");
					//
					// AlertDialog dialog = builder.create();
					// dialog.show();
					System.out.println(position);
					listNum =position;
					presenter.request(RequestParam.REQUEST_QUERY_TWO);
					
					
				}
			});

			holder.wifiEditFlag.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {

					final EditText edit = new EditText(context);
					// TODO Auto-generated method stub
					new AlertDialog.Builder(context).setTitle("修改AP名")
							.setIcon(android.R.drawable.ic_dialog_info)
							.setView(edit).setPositiveButton("确定", null)
							.setNegativeButton("取消", null).show();
				}
			});
			return view;
		}
	}

	public final class ViewHolder {

		public TextView wifiName;
		public TextView wifiOnlineNumber;
		public ImageView wifiEditFlag;
		public EditText wifiSsid;
		/**
		 * 模拟数据
		 */
		public TextView wifiAlias;
		public TextView wifiUpload;
		public TextView wifiDownload;
		public Button wifiManage;
	}

	/**
	 * 返回
	 */
	public void onBack(View v) {
		handler.removeCallbacks(runnable);
		getActivity().finish();
		;
	}

	/*
	 * 每五秒刷新一次
	 */
	Handler handler = new Handler();
	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				handler.postDelayed(this, TIME);
				presenter.request(RequestParam.REQUEST_QUERY);
				System.out.println("shua1");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				System.out.println("exception");
			}

		}
	};

	@Override
	public Object getInfo(int code) {
		// TODO Auto-generated method stub
		pd = new ProgressDialog(context);
		pd.show();
		if (code == RequestParam.REQUEST_UPDATE) {
			return ssidAndpwd;
		}else if(code == RequestParam.REQUEST_QUERY_ONE){
			return updownList;
		}else if(code == RequestParam.REQUEST_QUERY_TWO) {
			return Constants.STORE_ID;
		}else{
			return null;
		}
		

	}

	@Override
	public void setInfo(Object obj, int code) {
		// TODO Auto-generated method stub
		APListModel ap = null; 
		pd.dismiss();
		JSONObject json = JsonUtils.parseJson((String) obj);
		if (code == RequestParam.REQUEST_QUERY) {
			if (json.getIntValue("code") == 1) {
				list.clear();
				JSONArray aplistArray = json.getJSONArray("list");
				JSONObject apObject;
				for (int i = 0; i < aplistArray.size(); i++) {
					apObject = aplistArray.getJSONObject(i);
					ap = new APListModel(apObject.getIntValue("id"),
							apObject.getIntValue("shopId"), apObject
									.getString("ssid"), apObject
									.getString("nickname"), apObject
									.getString("mac"), apObject
									.getIntValue("upload"), apObject
									.getIntValue("download"), apObject
									.getIntValue("online"));
					
					list.add(ap);
				}
				
				Constants.STORE_ID = list.get(0).getShopId() + "";
				System.out.println("sotreId:"+Constants.STORE_ID);
				for(int i = 0;i<list.size();i++){
					listNum = i;
					updownList.clear();
					updownList.add("60:cd:a9:00:9c:90");
					updownList.add("14479264058");
					presenter.request(RequestParam.REQUEST_QUERY_ONE);
				}
				adapter.notifyDataSetChanged();

			} else {
				list = null;
				System.out.println("获取AP列表失败");
				Toast.makeText(context, "获取AP列表失败", Toast.LENGTH_SHORT).show();
			}
		} else if(code == RequestParam.REQUEST_UPDATE){
			if (json.getIntValue("code") == 1) {
				System.out.println("更新ssid成功");
				Toast.makeText(context, "更新ssid成功", Toast.LENGTH_SHORT).show();
			}
			else{
				System.out.println("更新ssid失败");
				Toast.makeText(context, "更新ssid失败", Toast.LENGTH_SHORT).show();
			}
			
		}else if(code == RequestParam.REQUEST_QUERY_ONE){
			if (json.getIntValue("code") == 1) {
				list.get(listNum).setUpload(15);
				list.get(listNum).setDownload(33);
				System.out.println("获取上传下载流量成功");
				Toast.makeText(context, "获取上传下载流量成功", Toast.LENGTH_SHORT).show();
			}
			else{
				System.out.println("获取上传下载流量失败");
				Toast.makeText(context, "获取上传下载流量失败", Toast.LENGTH_SHORT).show();
			}
		}else if(code == RequestParam.REQUEST_QUERY_TWO){
			if (json.getIntValue("code") == 1) {
				JSONObject jsonObject= json.getJSONObject("value");
				ssid = jsonObject.getString("ssid");
				if(ssid != ""){
					System.out.println("listNum="+listNum);
					wifiModify();
				}
				System.out.println(ssid);
			}
			 
		}
		
		adapter.notifyDataSetChanged();

		
	}

	private void wifiModify() {
		// TODO Auto-generated method stub
		final WifiModifyDialog.Builder builder = new WifiModifyDialog.Builder(
				context);
		
//		builder.setWifiName(list.get(position).getSsid());
		System.out.println(ssid);
		builder.setWifiName(ssid);
		builder.setWifiPwd("123456");
		builder.setMac(list.get(listNum).getMac());
		builder.setOnlineTime("5天10小时45分钟");
		builder.setPositiveButton(new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				String ssid = builder.getWifiName();
				ssidAndpwd.add(ssid);
				String password = builder.getWifiPwd();
				ssidAndpwd.add(password);
				presenter.request(RequestParam.REQUEST_UPDATE);
				dialog.dismiss();
			}
		});
		builder.setNegativeButton(new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		WifiModifyDialog dialog = builder.create();
		dialog.show();
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.wifi_edit_flag:

			final EditText edit = new EditText(context);
			// TODO Auto-generated method stub
			new AlertDialog.Builder(context).setTitle("修改AP名")
					.setIcon(android.R.drawable.ic_dialog_info).setView(edit)
					.setPositiveButton("确定", null)
					.setNegativeButton("取消", null).show();

			break;

		default:
			break;
		}

	}

}
