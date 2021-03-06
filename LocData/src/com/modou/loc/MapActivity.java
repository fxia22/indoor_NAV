package com.modou.loc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.modou.loc.adapter.FloorAdapter;
import com.modou.loc.data.transfer.DataTransferMgr2;
import com.modou.loc.db.DBDao;
import com.modou.loc.entity.Building;
import com.modou.loc.entity.Floor;
import com.modou.loc.module.map2.ShowSurfaceView;
import com.modou.loc.module.map2.config.MapConfig;
import com.modou.loc.task.SensorDataTask;
import com.modou.loc.task.WifiDataTask;
import com.modou.utils.MLog;
import com.modou.widget.BuildSelPopupWin;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author 作者 E-mail: xyylchq@163.com
 * @version 创建时间: 2014-8-15 上午8:05:11 
 * 类说明: 地图显示页面
 */
public class MapActivity extends BaseActivity {

	// 标题组件
	private TextView txtTitle;
	private BuildSelPopupWin buildSelPopWin;
	private List<Building> datas;
	
	
	private ShowSurfaceView mSurfaceView;
	
	private WifiDataTask wifiDataTask;
	private SensorDataTask sensorDataTask;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.fragment_category);

		initView();
		initData();
	}
	
	private void initView() {
		setLeftBtn(R.drawable.btn_back_bg, R.string.back);
//		setRightBtn(R.drawable.btn_confirm_bg, R.string.locationing);
		txtTitle = setTxtTitle(R.string.loc_navigation);
		txtTitle.setOnClickListener(this);
		
		mSurfaceView = new ShowSurfaceView(this);
		RelativeLayout panelView = (RelativeLayout) findViewById(R.id.layer_room_map);
		panelView.addView(mSurfaceView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		MLog.d("MapActivity---------------initView=====================");
	}
	
	/**
	 * 设置建筑物名称
	 * 当选择了建筑物时，名称跟着相应改变
	 * @param buildName
	 */
	private void setTitleName(String buildName) {
		txtTitle.setText(buildName);
	}
	
	private void initData() {
		// 地图信息初始化，应用启动一定先调用init方法
		MapConfig.getInstance().init(getApplicationContext());
		MLog.d("=======11111=========" + DBDao.getInstance());
		//TODO ====测试数据=====
		boolean isExist = DBDao.getInstance().isHasDatas("1000020");
		if (!isExist) {
			DBDao.getInstance().addShop("1000020", "地图1", "www.baidu.com");
		}
		//===============
		datas = DBDao.getInstance().getShops();
		if (datas != null && datas.size() > 0) {
			buildSelPopWin = new BuildSelPopupWin(this, datas);
			Building build = datas.get(0);
			String buildId = build.getId();
			String buildName = build.getName();
			setTitleName(buildName);
			MapConfig.getInstance().setBuildID(buildId);
			MapConfig.getInstance().initMapData();
			
			initFloorView();
		} else {
			showToast("还未下载任何地图数据，快去下载吧");
		}
	}
	
	private void initFloorView() {
		int size = MapConfig.getInstance().getFloorList() == null ? 0 : MapConfig.getInstance().getFloorList().size();
		if (size == 0) {
			MLog.e("没有取到对应的楼层信息 , 楼层数为0");
			return;
		}
		final ArrayList<Floor> datas = new ArrayList<Floor>(size); 
		Floor tmp = null;
		for (com.modou.loc.module.map2.config.Floor f : MapConfig.getInstance().getFloorList().values()) {
			int id = f.getFloorId();
			tmp = new Floor(id, "F"+ id);
			datas.add(tmp);
		}
		Comparator<Floor> comparator = new Comparator<Floor>() {
			@Override
			public int compare(Floor f1, Floor f2) {
				if (f1 != null && f2 != null)
					return f1.getId() - f2.getId();
				else 
					return 0;
			}
		};
		Collections.sort(datas, comparator);
		
		// 默认第一个楼层是选中的
		Floor fl = datas.get(0);
		fl.setSelected(true);
		
    	final Button btnSel = (Button) findViewById(R.id.btn_sel);
		ListView listview = (ListView) findViewById(R.id.listview_floor);
		final FloorAdapter adapter = new FloorAdapter(this, datas);
		listview.setAdapter(adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Floor fl = datas.get(position);
				String text = fl.getName();
				btnSel.setText(text);
				
				// 更改选中颜色
				adapter.setSelectItem(position);
				mSurfaceView.changeFloor(fl.getId());
			}
			
		});
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		int viewId = v.getId();
		if (viewId == R.id.btn_left) {
			finish();
		} else if (viewId == R.id.btn_right) {
			showToast("开始定位操作");
		} else if (viewId == R.id.txtview_title) {
			buildSelPopWin.show(txtTitle);
		}
	}
	
	/**
	 * 加载建筑物地图
	 * @param buildId	建筑物ID
	 */
	public void loadBuildMap(String buildId) {
		showToast("加载建筑物地图:buildId=" + buildId);
		MapConfig.getInstance().setBuildID(buildId);
		MapConfig.getInstance().initMapData();
		mSurfaceView.reLoadMap();
	}

	public void startLoc() {
		wifiDataTask = new WifiDataTask(this);
		wifiDataTask.start();
		sensorDataTask = new SensorDataTask(this);
		sensorDataTask.start();
		DataTransferMgr2.getInstance().init(mSurfaceView);
		DataTransferMgr2.getInstance().start();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		mSurfaceView.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		mSurfaceView.onPause();
	}
	
	@Override
	public void onDestroy() {
		MLog.d("销毁MapActivity====");
		if (wifiDataTask != null)
			wifiDataTask.cancel();
		if (sensorDataTask != null)
			sensorDataTask.cancel();
		DataTransferMgr2.getInstance().cancel();
		super.onDestroy();
	}
	
}
