package com.whut.activity;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whut.config.RequestParam;
import com.whut.data.model.APListModel;
import com.whut.interfaces.IBaseView;
import com.whut.presenter.WifiManagePresenter;
import com.whut.seller.R;
import com.whut.util.JsonUtils;
import com.whut.util.PullToRefreshListView;
import com.whut.util.PullToRefreshBase.OnLastItemVisibleListener;
import com.whut.util.PullToRefreshBase.OnRefreshListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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

/**
 * 查看连接AP用户信息
 * 
 * @author lx
 */
public class WiFiManageActivity extends Activity implements IBaseView,OnClickListener {

	
	private PullToRefreshListView pullToRefreshListView;
	private Context context;
	private ListView wifiList;
	private WifiAdapter adapter;
	private List<APListModel> list;
	private WifiManagePresenter presenter;
	private String Mallid;
	private int TIME = 50000;
	private ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wifimanage);
		((TextView) findViewById(R.id.activity_title)).setText("AP列表");

		pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.wifi_list);
		initRefreshListView();
		wifiList = pullToRefreshListView.getRefreshableView();

		initData();

		handler.postDelayed(runnable, TIME);
//		wifiList.setOnItemClickListener(new OnItemClickListener() {

//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1,
//					int position, long arg3) {
				// TODO Auto-generated method stub
//				if (list.size() < position) {
//					Toast.makeText(WiFiManageActivity.this, "hahaha",
//							Toast.LENGTH_SHORT).show();
//				} else {
//					Intent i = new Intent(WiFiManageActivity.this,
//							WifiClientActivity.class);
//					System.out.println(list.get(position).getMac()+","+list.get(position).getId());
//					i.putExtra("apName", list.get(position).getMac());
//					i.putExtra("apId", list.get(position).getId());
//					startActivity(i);
//				}

//			}
//		});
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
		context = this;
		list = new ArrayList<APListModel>();
		adapter = new WifiAdapter();
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
				view = getLayoutInflater().inflate(R.layout.wifi_aplist_item,
						null);
				if (position % 2 == 0) {
					view.setBackgroundResource(R.drawable.corners_wifi2);
				} else {
					view.setBackgroundResource(R.drawable.corners_wifi1);
				}
				holder.wifiName = (TextView) view.findViewById(R.id.wifi_name);
				holder.wifiOnlineNumber = (TextView) view
						.findViewById(R.id.wifi_online_number);
				holder.wifiEditFlag = (ImageView) view
						.findViewById(R.id.wifi_edit_flag);
				holder.wifiUpload = (TextView) view.findViewById(R.id.wifi_upload);
				holder.wifiDownload = (TextView) view.findViewById(R.id.wifi_download);
				holder.wifiManage = (Button) view.findViewById(R.id.btn_ap_manage);
//				holder.wifiPoter = (ImageButton) view.findViewById(R.id.wifi_poter);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}

			holder.wifiName.setText(list.get(position).getMac());
			System.out.println(list.get(position).getOnline());
			holder.wifiOnlineNumber.setText(list.get(position).getOnline()+"");
			holder.wifiEditFlag
					.setBackgroundResource(R.drawable.wifi_edit_flag);
			holder.wifiUpload.setText(list.get(position).getUpload()+"KB/s");
			holder.wifiDownload.setText(list.get(position).getDownload()+"KB/s");
			

			holder.wifiManage.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if (list.size() < position) {
						Toast.makeText(WiFiManageActivity.this, "hahaha",
								Toast.LENGTH_SHORT).show();
					} else {
						Intent i = new Intent(WiFiManageActivity.this,
								WifiClientActivity.class);
						System.out.println(list.get(position).getMac()+","+list.get(position).getId());
						i.putExtra("apName", list.get(position).getMac());
						i.putExtra("apId", list.get(position).getId());
						startActivity(i);
					}
				}
			});
//			holder.wifiPoter.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View arg0) {
//					// TODO Auto-generated method stub
//					Intent intent = new Intent(WiFiManageActivity.this,WebPageActivity.class);
//					startActivity(intent);
//				}
//			});
//			holder.wifiEditFlag.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View arg0) {
//					
//					final EditText edit = new EditText(context); 
//					// TODO Auto-generated method stub
//					new AlertDialog.Builder(context).setTitle("修改AP名").setIcon(
//							android.R.drawable.ic_dialog_info).setView(
//							edit).setPositiveButton("确定", null).setNegativeButton("取消", null).show();
//				}
//			});
			return view;
		}
	}

	public final class ViewHolder {
		public TextView wifiName;
		public TextView wifiOnlineNumber;
		public ImageView wifiEditFlag;
		/**
		 * 模拟数据
		 */
		public TextView wifiUpload;
		public TextView wifiDownload;
		public Button wifiManage;
//		public ImageButton wifiPoter;
	}

	/**
	 * 返回
	 */
	public void onBack(View v) {
		handler.removeCallbacks(runnable);
		this.finish();
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
		pd = new ProgressDialog(WiFiManageActivity.this);
		pd.show();
		return null;
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		handler.removeCallbacks(runnable);
		super.onPause();
	}

	@Override
	public void setInfo(Object obj, int code) {
		// TODO Auto-generated method stub
		pd.dismiss();
		JSONObject json = JsonUtils.parseJson((String) obj);
		if (json.getIntValue("code") == 1) {
			list.clear();
			JSONArray aplistArray = json.getJSONArray("list");
			JSONObject apObject;
			for (int i = 0; i < aplistArray.size(); i++) {
				apObject = aplistArray.getJSONObject(i);
				list.add(new APListModel(apObject.getIntValue("id"), apObject
						.getString("mac"), apObject.getIntValue("upload"),apObject.getIntValue("download"),apObject.getIntValue("online")));
			}
			adapter.notifyDataSetChanged();

		} else {
			list = null;
			System.out.println("获取AP列表失败");
			Toast.makeText(WiFiManageActivity.this, "获取AP列表失败",
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.wifi_edit_flag:

			
			final EditText edit = new EditText(context); 
			// TODO Auto-generated method stub
			new AlertDialog.Builder(context).setTitle("修改AP名").setIcon(
					android.R.drawable.ic_dialog_info).setView(
					edit).setPositiveButton("确定", null).setNegativeButton("取消", null).show();
		
			break;

		default:
			break;
		}
		
	}

}
