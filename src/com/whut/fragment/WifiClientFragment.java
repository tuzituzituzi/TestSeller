package com.whut.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSONObject;
import com.whut.activity.WifiClientActivity;
import com.whut.activity.WifiClientActivity.UserListAdapter;
import com.whut.config.RequestParam;
import com.whut.data.model.APClient;
import com.whut.data.model.APClient.ClientDetail;
import com.whut.interfaces.IBaseView;
import com.whut.presenter.WifiClientPresent;
import com.whut.seller.R;
import com.whut.util.JsonUtils;
import com.whut.util.PullToRefreshListView;
import com.whut.util.PullToRefreshBase.OnLastItemVisibleListener;
import com.whut.util.PullToRefreshBase.OnRefreshListener;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class WifiClientFragment extends Fragment implements IBaseView{
	
	private ListView userlist;
	private UserListAdapter adapter;
	private int id;
	private List<APClient> list;
	private TextView wifiName;
	private WifiClientPresent presenter;
	private PullToRefreshListView pullToRefreshListView;
	private int TIME = 50000;
	private ProgressDialog pd;
	private LayoutInflater inflater;
	private Context context;
	private View view;
	private int clickPosition = -1;
	private String name;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View clientFragment = inflater.inflate(R.layout.wifi_userlist_old, container, false);
		this.inflater = inflater;
		this.view = clientFragment;
		context = getActivity();
		initdata();
		userlist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				if (position != clickPosition) {
					clickPosition = position;
				} else {
					clickPosition = -1;
				}
				adapter.notifyDataSetChanged();
				
			}
		});
//		handler.postDelayed(runnable, TIME);
		return clientFragment;
	}

	private void initdata() {
		// TODO Auto-generated method stub
//		name = getArguments().getString("mac");
		wifiName = (TextView) view.findViewById(R.id.this_wifi_name);
		wifiName.setText(name);
		
		presenter = new WifiClientPresent(this);
		list = new ArrayList<APClient>();

		pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.wifi_userlist);
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
				Toast.makeText(context, "下拉刷新",
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
						Toast.makeText(context, "上拉刷新",
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
		getActivity().finish();;
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
		public View getView(int position, View view, ViewGroup arg2) {
			// TODO Auto-generated method stub
			UserWifi userWifi = null;
			if (view == null) {
				userWifi = new UserWifi();
				view = inflater.inflate(R.layout.wifi_userlist_item_old,
						null);
				userWifi.userIcon = (ImageView) view
						.findViewById(R.id.wifi_user_icon);
				userWifi.userName = (TextView) view
						.findViewById(R.id.wifi_user_name);
				userWifi.connectTime = (TextView) view
						.findViewById(R.id.wifi_user_connect);
				userWifi.userMac = (TextView) view.findViewById(R.id.wifi_userlist_mac);
				userWifi.isBlack = (Button) view.findViewById(R.id.wifi_add_black);
				userWifi.isWhite = (Button) view.findViewById(R.id.wifi_add_white);
				userWifi.isVip = (Button) view.findViewById(R.id.wifi_add_vip);
				userWifi.detail = view.findViewById(R.id.wifi_user_detail);
				userWifi.upload = (TextView) view.findViewById(R.id.wifi_upload_speed);
				userWifi.download = (TextView) view.findViewById(R.id.wifi_down_speed);
				view.setTag(userWifi);
			} else {
				userWifi = (UserWifi) view.getTag();
			}
			userWifi.userName.setText(list.get(position).getClient().getName());
			
			userWifi.userMac.setText(list.get(position).getClientName());
			int cnnTime = list.get(position).getClient().getCnnTime();
			int authTime = list.get(position).getClient().getAuthTime();
			String time = getTime(cnnTime, authTime);
			userWifi.connectTime.setText(time);
			userWifi.upload.setText(list.get(position).getClient().getUpload()+"KB/s");
			userWifi.download.setText(list.get(position).getClient().getDownload()+"KB/s");
			if(list.get(position).getClient().getIsBlack() == 0){
				userWifi.isBlack.setClickable(false);
				userWifi.isBlack.setText("已加黑名单");
				userWifi.isBlack.setTextColor(getResources().getColor(R.color.gray));
			}
			if(list.get(position).getClient().getIsWhite() == 0){
				userWifi.isWhite.setClickable(false);
				userWifi.isWhite.setText("已加白名单");
				userWifi.isWhite.setTextColor(getResources().getColor(R.color.gray));
			}
			if(list.get(position).getClient().getIsVip() == 0){
				userWifi.isVip.setClickable(false);
				userWifi.isVip.setText("已加VIP");
				userWifi.isVip.setTextColor(getResources().getColor(R.color.gray));
			}
			
			
			switch (getCompany(list.get(position).getClientName())) {
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
			if(position+1 == clickPosition){
				userWifi.detail.setVisibility(View.VISIBLE);
			}else {
				userWifi.detail.setVisibility(View.GONE);
			}
			return view;
		}

	}

	private final class UserWifi {
		private ImageView userIcon;
		private TextView userName;
		private TextView connectTime;
		private TextView userMac;
		private Button isWhite;
		private Button isBlack;
		private Button isVip;
		private View detail;
		private TextView upload;
		private TextView download;
	}
	
	
//	@Override
//	public void onClick(View arg0) {
//		// TODO Auto-generated method stub
//		switch (arg0.getId()) {
//		case R.id.wifi_add_black:
//			alterAddDialog();
//			break;
//
//		case R.id.wifi_add_white:
//			break;
//		case R.id.wifi_add_vip:
//			break;
//		default:
//			break;
//		}
//	}

	@Override
	public Object getInfo(int code) {
		// TODO Auto-generated method stub
		pd = new ProgressDialog(context);
		pd.show();
		return id;
	}

	

	public int getCompany(String clientName) {
		// TODO Auto-generated method stub

		String mac = clientName.substring(0, 8).replace(":", "").toUpperCase();
		String[] apple = { "00CDFE", "18AF61", "CC4463", "6C72E7", "60FEC5",
				"00A040", "000D93", "7CF05F", "A4B197", "0C74C2", "403004",
				"4860BC", "50EAD6", "28E02C", "60C547", "7C11BE", "003EE1",
				"44D884", "001CB3", "64B9E8", "34159E", "58B035", "F0B479",
				"109ADD", "40A6D9", "087402", "285AEB", "28F076", "EC852F",
				"286ABA", "705681", "7CD1C3", "F0DCE2", "B065BD", "A82066",
				"BC6778", "68967B", "848506", "B4F0AB", "10DDB1", "04F7E4",
				"34C059", "F0D1A9", "BC3BAF", "D0E140", "F832E4 ", "8C7C92",
				"7831C1", "F437B7" };

		String[] xiaomi = { "F8A45F", "8CBEBE", "640980", "98FAE3", "185936",
				"9C99A0", "14F65A", "0C1DAF", "28E31F", "F0B429", "D4970B",
				"F48B32", "ACF7F3", "009EC8", "7C1DD9", "A086C6", "584498",
				"FC64BA", "C46AB7", "68DFDD", "64B473", "7451BA", "3480B3",
				"2082C0" };

		String[] sony = { "AC9B0A", "BC6E64", "A0E453", "1C7B21", "709E29",
				"00EB2D", "205476", "303926", "B8F934", "FC0FE6", "6C23B9",
				"58170C", "A8E3EE", "2421AB",  "00219E", "001FA7",
				"001E45", "001813", "001315", "0013A9", "4040A7", "40B837",
				"C43ABE", "307512", "4C21D0", "94CE2C", "D05162", "5453ED" };
		for (int i = 0; i < apple.length; i++) {
			if (mac.equals(apple[i]))
				return 0;
		}
		for (int i = 0; i < xiaomi.length; i++) {
			if (mac.equals(xiaomi[i]))
				return 2;
		}
		for (int i = 0; i < sony.length; i++) {
			if (mac.equals(sony[i]))
				return 3;
		}
		return -1;
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
		if (json.getIntValue("code") == 1) {
			list.clear();
			JSONObject clients = json.getJSONObject("client");
			for (Entry<String, Object> entity : clients.entrySet()) {
				JSONObject clientdetail = clients
						.getJSONObject(entity.getKey());
				
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
	}

}
