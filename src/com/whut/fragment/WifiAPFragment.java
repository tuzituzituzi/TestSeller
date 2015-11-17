package com.whut.fragment;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whut.activity.WebPageActivity;
import com.whut.activity.WiFiManageActivity;
import com.whut.activity.WifiClientActivity;
import com.whut.activity.WiFiManageActivity.ViewHolder;
import com.whut.activity.WiFiManageActivity.WifiAdapter;
import com.whut.config.RequestParam;
import com.whut.data.model.APListModel;
import com.whut.interfaces.IBaseView;
import com.whut.presenter.WifiManagePresenter;
import com.whut.seller.R;
import com.whut.util.JsonUtils;
import com.whut.util.PullToRefreshListView;
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

public class WifiAPFragment extends Fragment implements OnClickListener,IBaseView{
	
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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View apFragment = inflater.inflate(R.layout.wifi_aplist,container,false);
		pullToRefreshListView = (PullToRefreshListView) apFragment.findViewById(R.id.wifi_list);
		initRefreshListView();
		wifiList = pullToRefreshListView.getRefreshableView();
		
		this.inflater = inflater;
		initData();
		//定时刷新
//		handler.postDelayed(runnable, TIME);
		wifiList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				if (list.size() < position) {
					
				} else {
//					WifiClientFragment clientFragment = new WifiClientFragment();
//					FragmentTransaction ft = getFragmentManager().beginTransaction();
//					Bundle bundle = new Bundle();
//					bundle.putString("mac", list.get(position).getMac());
//					clientFragment.setArguments(bundle);
//					ft.replace(R.id.wifi_frame, clientFragment,"apFragment");
//					ft.addToBackStack("apFragment");
//					ft.commit();
					Bundle bundle = new Bundle();
					Intent i = new Intent(getActivity(),WifiClientActivity.class);
					bundle.putString("mac", list.get(position).getMac());
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
				view = inflater.inflate(R.layout.wifi_aplist_item,
						null);
				
				holder.wifiName = (TextView) view.findViewById(R.id.wifi_name);
				holder.wifiOnlineNumber = (TextView) view
						.findViewById(R.id.wifi_online_number);
				holder.wifiEditFlag = (ImageView) view
						.findViewById(R.id.wifi_edit_flag);
				holder.wifiUpload = (TextView) view.findViewById(R.id.wifi_upload);
				holder.wifiDownload = (TextView) view.findViewById(R.id.wifi_download);
				holder.wifiManage = (Button) view.findViewById(R.id.btn_ap_manage);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}

			holder.wifiName.setText(list.get(position).getMac());
			System.out.println(list.get(position).getOnline());
			holder.wifiOnlineNumber.setText(""+list.get(position).getOnline());
//			holder.wifiEditFlag.setBackgroundResource(R.drawable.wifi_edit_flag);
			holder.wifiUpload.setText(list.get(position).getUpload()+"KB/s");
			holder.wifiDownload.setText(list.get(position).getDownload()+"KB/s");

			holder.wifiManage.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					//上一个版本的
//					WifiModifyFragment modifyFragment = new WifiModifyFragment();
//					FragmentTransaction ft = getFragmentManager().beginTransaction();
//					
//					Bundle bundle = new Bundle();
//					bundle.putString("mac", list.get(position).getMac());
//					modifyFragment.setArguments(bundle);
//					ft.replace(R.id.wifi_frame, modifyFragment,"apFragment");
//					ft.addToBackStack("apFragment");
//					ft.commit();
					
//					AlertDialog.Builder builder = new AlertDialog.Builder(context);
//					View view = inflater.inflate(R.layout.wifi_dialog, null);
//					
//					builder.setTitle("修改管理");
//					builder.setView(view);
//					builder.setPositiveButton("登录", null);
//					builder.setNegativeButton("取消", null);
//					System.out.println("dianlemei");
//					
//					AlertDialog dialog = builder.create();
//					dialog.show();
					WifiModifyDialog.Builder builder = new WifiModifyDialog.Builder(context);
					builder.setWifiName("信曾哥，得永生");
					builder.setWifiPwd("123456");
					builder.setMac("fc:d7:33:51:74:4c");
					builder.setOnlineTime("5天10小时45分钟");
					builder.setPositiveButton(new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
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
			});
			
			holder.wifiEditFlag.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					
					final EditText edit = new EditText(context); 
					// TODO Auto-generated method stub
					new AlertDialog.Builder(context).setTitle("修改AP名").setIcon(
							android.R.drawable.ic_dialog_info).setView(
							edit).setPositiveButton("确定", null).setNegativeButton("取消", null).show();
				}
			});
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
	}

	/**
	 * 返回
	 */
	public void onBack(View v) {
		handler.removeCallbacks(runnable);
		getActivity().finish();;
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
		return null;
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
			Toast.makeText(context, "获取AP列表失败",
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

