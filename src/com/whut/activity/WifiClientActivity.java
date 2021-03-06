package com.whut.activity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.zip.Inflater;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pgyersdk.conf.b;
import com.pgyersdk.feedback.l;
import com.whut.config.Constants;
import com.whut.config.RequestParam;
import com.whut.data.model.APClient;
import com.whut.data.model.APClient.ClientDetail;
import com.whut.interfaces.IBaseView;
import com.whut.presenter.WifiClientPresent;
import com.whut.seller.R;
import com.whut.util.JsonUtils;
import com.whut.util.MacBrand;
import com.whut.util.PullToRefreshListView;
import com.whut.util.WifiModifyDialog;
import com.whut.util.PullToRefreshBase.OnLastItemVisibleListener;
import com.whut.util.PullToRefreshBase.OnRefreshListener;
import com.whut.util.WifiPopupDialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ResourceAsColor")
public class WifiClientActivity extends Activity implements IBaseView  {

	private ListView userlist;
	private UserListAdapter adapter;
	private String name;
	private int id;
	private List<APClient> list;
	private TextView wifiName;
	private WifiClientPresent presenter;
	private PullToRefreshListView pullToRefreshListView;
	private int TIME = 50000;
	private ProgressDialog pd;
	private int clickPosition = -1;
	private List<String> wbList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wifi_userlist);
		((TextView) findViewById(R.id.activity_title)).setText("查看");
		// name = getIntent().getStringExtra("apName");
		// id = getIntent().getIntExtra("apId", 0);
		name = getIntent().getExtras().getString("mac");
		System.out.println(name);
		id = 21;

		initdata();
		userlist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				System.out.println("position = " + position);
				if (position != clickPosition) {
					clickPosition = position;
				} else {
					clickPosition = -1;
				}
				adapter.notifyDataSetChanged();

			}
		});
		// 定时刷新
		// handler.postDelayed(runnable, TIME);
	}

	private void initdata() {
		// TODO Auto-generated method stub
		wifiName = (TextView) findViewById(R.id.this_wifi_name);
		wifiName.setText(name);
		presenter = new WifiClientPresent(this);
		list = new ArrayList<APClient>();
		wbList = new ArrayList<String>();

		pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.wifi_userlist);
		initRefreshListView();
		userlist = pullToRefreshListView.getRefreshableView();

		adapter = new UserListAdapter();
		presenter.request(RequestParam.REQUEST_QUERY);
		userlist.setAdapter(adapter);

	}

	private void initRefreshListView() {
		// TODO Auto-generated method stub

		pullToRefreshListView.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				// 下拉刷新
				Toast.makeText(WifiClientActivity.this, "下拉刷新",
						Toast.LENGTH_SHORT).show();
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
						Toast.makeText(WifiClientActivity.this, "上拉刷新",
								Toast.LENGTH_SHORT).show();
					}
				});
	}

	/*
	 * 每5秒刷新一次
	 */
	Handler handler = new Handler();
	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				handler.postDelayed(this, TIME);
				presenter.request(RequestParam.REQUEST_QUERY);
				System.out.println("shua2");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				System.out.println("exception");
			}

		}
	};

	public void onBack(View v) {
		handler.removeCallbacks(runnable);
		this.finish();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		handler.removeCallbacks(runnable);
		super.onPause();
	}

	public class UserListAdapter extends BaseAdapter {

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
			return arg0;
		}

		@Override
		public View getView(final int position, View view, ViewGroup arg2) {
			// TODO Auto-generated method stub
			UserWifi userWifi = null;
			if (view == null) {
				userWifi = new UserWifi();
				view = getLayoutInflater().inflate(R.layout.wifi_userlist_item,
						null);
				userWifi.userIcon = (ImageView) view
						.findViewById(R.id.wifi_user_icon);
				userWifi.userName = (TextView) view
						.findViewById(R.id.wifi_user_name);
				userWifi.connectTime = (TextView) view
						.findViewById(R.id.wifi_user_connect);
				// userWifi.userMac = (TextView)
				// view.findViewById(R.id.wifi_userlist_mac);
				userWifi.isBlack = view.findViewById(R.id.wifi_add_black);
				userWifi.isWhite = view.findViewById(R.id.wifi_add_white);
				userWifi.isVip = view.findViewById(R.id.wifi_add_vip);
				userWifi.detail = view.findViewById(R.id.wifi_user_detail);
				userWifi.upload = (TextView) view
						.findViewById(R.id.wifi_upload_speed);
				userWifi.download = (TextView) view
						.findViewById(R.id.wifi_down_speed);
				view.setTag(userWifi);
			} else {
				userWifi = (UserWifi) view.getTag();
			}
			userWifi.userName.setText(list.get(position).getClient().getName());

			// userWifi.userMac.setText(list.get(position).getClientName());
			int cnnTime = list.get(position).getClient().getCnnTime();
			int authTime = list.get(position).getClient().getAuthTime();
			String time = getTime(cnnTime, authTime);
			userWifi.connectTime.setText(time);
			userWifi.upload.setText(list.get(position).getClient().getUpload()
					+ "KB/s");
			userWifi.download.setText(list.get(position).getClient()
					.getDownload()
					+ "KB/s");
			if (list.get(position).getClient().getIsBlack() == 0) {
				userWifi.isBlack.setClickable(false);
				userWifi.isBlack.setBackgroundColor(R.color.gray);
				// userWifi.isBlack.setText("已加黑名单");
				// userWifi.isBlack.setTextColor(getResources().getColor(R.color.gray));
			}
			if (list.get(position).getClient().getIsWhite() == 0) {
				userWifi.isWhite.setClickable(false);
				userWifi.isWhite.setBackgroundColor(R.color.gray);
				// userWifi.isWhite.setText("已加白名单");
				// userWifi.isWhite.setTextColor(getResources().getColor(R.color.gray));
			}
			if (list.get(position).getClient().getIsVip() == 0) {
				userWifi.isVip.setClickable(false);
				userWifi.isVip.setBackgroundColor(R.color.gray);
				// userWifi.isVip.setText("已加VIP");
				// userWifi.isVip.setTextColor(getResources().getColor(R.color.gray));
			}
			userWifi.isBlack.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					alterAddDialog(list.get(position).getClientName(), "black");
				}
			});
			userWifi.isWhite.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					alterAddDialog(list.get(position).getClientName(), "white");
				}
			});
			userWifi.isVip.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					alterAddDialog(list.get(position).getClientName(), "vip");
				}
			});

			switch (new MacBrand().getCompany(list.get(position).getClientName())) {
			case 0:
				userWifi.userIcon.setBackgroundResource(R.drawable.meizu);
				break;
			case 2:
				userWifi.userIcon.setBackgroundResource(R.drawable.xiaomi);
				break;
			case 3:
				userWifi.userIcon.setBackgroundResource(R.drawable.huawei);
				break;
			default:
				userWifi.userIcon.setBackgroundResource(R.drawable.user_icon1);
				break;
			}
			if (position + 1 == clickPosition) {
				userWifi.detail.setVisibility(View.VISIBLE);
			} else {
				userWifi.detail.setVisibility(View.GONE);
			}
			return view;
		}

	}

	private final class UserWifi {
		private ImageView userIcon;
		private TextView userName;
		private TextView connectTime;
		// private TextView userMac;
		private View isWhite;
		private View isBlack;
		private View isVip;
		private View detail;
		private TextView upload;
		private TextView download;
	}

	private void alterAddDialog(String mac, String WBsign) {
		// TODO Auto-generated method stub
		String DecimalMac = getDecimalMac(mac);
		wbList.clear();
		wbList.add(DecimalMac);
		wbList.add(WBsign);
		String text = "";
		
		final WifiPopupDialog.Builder builder = new WifiPopupDialog.Builder(this);
		if(WBsign == "black"){
			text = "黑名单";
		}else{
			text = "白名单";
		}
		builder.setAddText("确认添加至"+text);
		
		builder.setPositiveButton(new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				presenter.request(RequestParam.REQUEST_UPDATE);
				dialog.dismiss();
			}
		});
		
		builder.setNegativeButton(new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		WifiPopupDialog dialog = builder.create();
		dialog.show();

	}

	//获取十进制mac
	protected String getDecimalMac(String mac) {
		// TODO Auto-generated method stub
		return mac;
	}

	@Override
	public Object getInfo(int code) {
		// TODO Auto-generated method stub
		pd = new ProgressDialog(WifiClientActivity.this);
		pd.show();
		if (code == 0) {

			return id;
		} else {
			return wbList;
		}

	}


	public String getTime(int cnnTime, int authTime) {
		// TODO Auto-generated method stub
		int time = cnnTime - authTime;
		int hour = time / 60;
		int second = time - hour * 60;
		return "" + hour + ":" + second;
	}

	@Override
	public void setInfo(Object obj, int code) {
		// TODO Auto-generated method stub
		pd.dismiss();
		JSONObject json = JsonUtils.parseJson((String) obj);
		if (code == 0) {
			if (json.getIntValue("code") == 1) {
				list.clear();
				JSONObject clients = json.getJSONObject("client");
				for (Entry<String, Object> entity : clients.entrySet()) {
					JSONObject clientdetail = clients.getJSONObject(entity
							.getKey());

					int cnnTime = clientdetail.getIntValue("cnnTime");
					int authTime = clientdetail.getIntValue("authTime");
					int state = clientdetail.getIntValue("state");
					int upload = clientdetail.getIntValue("upload");
					int download = clientdetail.getIntValue("download");
					String userName = clientdetail.getString("name");
					int black = clientdetail.getIntValue("black");
					int white = clientdetail.getIntValue("white");
					int vip = clientdetail.getIntValue("vip");

					ClientDetail clientDetail = new ClientDetail();
					clientDetail.setCnnTime(cnnTime);
					clientDetail.setAuthTime(authTime);
					clientDetail.setState(state);
					clientDetail.setDownload(download);
					clientDetail.setUpload(upload);
					clientDetail.setIsBlack(black);
					clientDetail.setIsWhite(white);
					clientDetail.setIsVip(vip);
					clientDetail.setName(userName);

					APClient apClient = new APClient();
					apClient.setClientName(entity.getKey());
					apClient.setClient(clientDetail);
					list.add(apClient);
				}
				adapter.notifyDataSetChanged();
			}
		} else {

			if (json.getIntValue("code") == 1){
				
				System.out.print("添加成功");
				Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
			}else{
				System.out.print("添加失败");
				Toast.makeText(this, "添加失败", Toast.LENGTH_SHORT).show();
			}
		}

	}

}
