package com.whut.activity;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.whut.config.RequestParam;
import com.whut.data.model.WBModel;
import com.whut.interfaces.IBaseView;
import com.whut.presenter.WifiWBPresenter;
import com.whut.seller.R;
import com.whut.util.JsonUtils;
import com.whut.util.MacBrand;
import com.whut.util.PullToRefreshListView;
import com.whut.util.PullToRefreshBase.OnLastItemVisibleListener;
import com.whut.util.PullToRefreshBase.OnRefreshListener;


public class WifiWBManageActivityNew extends Activity implements IBaseView,OnClickListener {

	private WifiWBPresenter presenter;
	private Context context;
	private ProgressDialog pd;

	private PullToRefreshListView pullToRefreshListView;
	private ListView blackList;
	private BlackAdapter adapter;
	private List<WBModel> list;
	
	private View getBlackList;
	private View getWhiteList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wb_manage_new);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		((TextView) findViewById(R.id.activity_title)).setText("黑白名单");
		getBlackList = findViewById(R.id.get_black_list);
		getWhiteList = findViewById(R.id.get_white_list);
		presenter = new WifiWBPresenter(this);
		list = new ArrayList<WBModel>();
		context = this;
		
		pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.wb_list);
		initRefreshListView();
		blackList = pullToRefreshListView.getRefreshableView();
		
		presenter.request(RequestParam.REQUEST_QUERY_ONE);
		
		adapter = new BlackAdapter();
		blackList.setAdapter(adapter);
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

//				presenter.request(RequestParam.REQUEST_QUERY);
				System.out.println("下拉刷新");
			}
		});

		pullToRefreshListView
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
					@Override
					public void onLastItemVisible() {
						// TODO Auto-generated method stub
						
					}
				});
	}
	
	public class BlackAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			System.out.println("position = "+position);
			System.out.println("list.size() = "+list.size());
	
			WBList wbList = null;
			if(convertView == null){
				wbList = new WBList();
				LayoutInflater inflater = LayoutInflater.from(context);
				convertView = inflater.inflate(R.layout.wifi_black_list_item, null);
				wbList.userIcon = (ImageView) convertView.findViewById(R.id.black_user_icon);
				wbList.userName = (TextView) convertView.findViewById(R.id.black_user_name);
				wbList.userMac = (TextView) convertView.findViewById(R.id.black_mac);
				wbList.delete = (Button) convertView.findViewById(R.id.black_delete);
				convertView.setTag(wbList);
			}else{
				wbList = (WBList) convertView.getTag();
			}
			wbList.userName.setText(list.get(position).getWbName());
			
			switch (new MacBrand().getCompany(list.get(position).getWbMac())) {
			case 0:
				wbList.userIcon.setBackgroundResource(R.drawable.meizu);
				break;
			case 2:
				wbList.userIcon.setBackgroundResource(R.drawable.xiaomi);
				break;
			case 3:
				wbList.userIcon.setBackgroundResource(R.drawable.huawei);
				break;
			default:
				wbList.userIcon.setBackgroundResource(R.drawable.apple);
				break;
			}
			
			wbList.userMac.setText(list.get(position).getWbMac());
			wbList.delete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(context, "删除", Toast.LENGTH_SHORT).show();
				}
			});
			return convertView;
		}

	}
	
	private final class WBList{
		private ImageView userIcon;
		private TextView userName;
		private TextView userMac;
		private Button delete;
	}

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
		JSONObject json = JsonUtils.parseJson((String)obj);
		if(json.getIntValue("code") == 1){
			list.clear();
			JSONArray jsonList = json.getJSONArray("list");
			JSONObject jsonObject;
			for(int i=0;i<jsonList.size();i++){
				jsonObject = jsonList.getJSONObject(i);
				WBModel wList = new WBModel();
				wList.setWbName("普天小王子");
				wList.setWbMac(jsonObject.getString("mac"));
				list.add(wList);
			}
			
		adapter.notifyDataSetChanged();	
		}else{
			Toast.makeText(context, "获取白名单失败", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.get_black_list:
			getBlackList.setBackgroundDrawable(getResources().getDrawable(R.drawable.wifi_list_bg1));
			getWhiteList.setBackgroundDrawable(getResources().getDrawable(R.drawable.wifi_list_bg2));
			presenter.request(RequestParam.REQUEST_QUERY_ONE);
			break;
		case R.id.get_white_list:
			getBlackList.setBackgroundDrawable(getResources().getDrawable(R.drawable.wifi_list_bg2));
			getWhiteList.setBackgroundDrawable(getResources().getDrawable(R.drawable.wifi_list_bg1));
			presenter.request(RequestParam.REQUEST_QUERY_TWO);
			break;
		default:
			break;
		}
	}
	
	public void onBack(View v) {
		this.finish();
	}

}
