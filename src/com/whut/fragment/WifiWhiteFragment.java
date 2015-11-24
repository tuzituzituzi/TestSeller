package com.whut.fragment;

import java.util.ArrayList;
import java.util.List;

import com.whut.activity.WifiClientActivity;
import com.whut.config.RequestParam;
import com.whut.data.model.WBModel;
import com.whut.interfaces.IBaseView;
import com.whut.seller.R;
import com.whut.util.PullToRefreshListView;
import com.whut.util.PullToRefreshBase.OnLastItemVisibleListener;
import com.whut.util.PullToRefreshBase.OnRefreshListener;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class WifiWhiteFragment extends Fragment implements IBaseView{


	private LayoutInflater inflater;
	private Context context;
	private View view;
	private PullToRefreshListView pullToRefreshListView;
	private ListView blackList;
	private blackAdapter adapter;
	private List<WBModel> list;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View whiteFragment = inflater.inflate(R.layout.wifi_white_list, null);
		this.inflater = inflater;
		context = getActivity();
		this.view = whiteFragment;
		
		initview();
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	private void initview() {
		// TODO Auto-generated method stub
		pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.white_list);
		initRefreshListView();
		blackList = pullToRefreshListView.getRefreshableView();
		list = new ArrayList<WBModel>();
		setInfo("a",0);
		adapter = new blackAdapter();
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
	
	public class blackAdapter extends BaseAdapter{

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
			WBList wbList = null;
			if(convertView == null){
				wbList = new WBList();
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
			wbList.userIcon.setImageResource(R.drawable.xiaomi);
			wbList.userMac.setText(list.get(position).getWbMac());
			wbList.delete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(getActivity(), "删除", Toast.LENGTH_SHORT).show();
				}
			});
			return null;
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
		return null;
	}

	@Override
	public void setInfo(Object obj, int code) {
		// TODO Auto-generated method stub
		WBModel wList = new WBModel();
		wList.setWbName("普天小王子");
		wList.setWbMac("60:22:1c:20:ff:00");
		list.add(wList);
	}
}
