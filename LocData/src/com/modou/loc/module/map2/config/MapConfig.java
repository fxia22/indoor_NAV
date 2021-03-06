package com.modou.loc.module.map2.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlPullParserException;

import com.modou.utils.MLog;
import com.modou.utils.MethodUtils;
import com.modou.utils.StorageUtil;
import com.modou.utils.StringUtils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * @author 作者 E-mail: xyylchq@163.com
 * @version 创建时间: 2014-8-2 下午9:01:40
 * 类说明:
 * 地图文件配置类
 */
public class MapConfig {
	/**建筑物ID*/
	private String buildID;
	/**建筑物名称*/
	private String buildName;
	/**节点名称*/
	private String note;
	/**楼层数量*/
	private int floorCount;
	/**楼层集合*/
	private Map<Integer, Floor> floorList;
	/**缩放比例*/
	private double scale;
	
	private MapParser mapParser = null;
	private static MapConfig instance = null;
	private Context mContext; // 活动上下文,应用启动时赋值
	
	private MapConfig() {
		floorList = new HashMap<Integer, Floor>();
	}
	
	public String getBuildName() {
		return buildName;
	}
	
	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
	public int getFloorCount() {
		return floorCount;
	}
	
	public void setFloorCount(int floorCount) {
		this.floorCount = floorCount;
	}
	
	public Map<Integer, Floor> getFloorList() {
		return floorList;
	}
	
	public double getScale() {
		return scale;
	}
	
	public void setScale(double scale) {
		this.scale = scale;
	}
	
	public String getBuildID() {
		return buildID;
	}

	public void setBuildID(String buildID) {
		this.buildID = buildID;
	}

	public void init(Context ctx) {
		this.mContext = ctx;
	}
	
	/**数据初始化*/
	public void initMapData() {
		if (StringUtils.isEmpty(buildID)) {
			throw new RuntimeException("初始化地图时 buildID为空");
		}
		
		String parentPath = StorageUtil.getExternalStoragePath(mContext) + buildID;
		File parentFile = new File(parentPath);
		MLog.d("楼层对应文件数：" + parentPath);
		int len = parentFile.listFiles() == null ? 0 : parentFile.listFiles().length;
		MLog.d("当前楼层[ " + buildID + " ]下共有" + len + "个楼层.");
		if (parentFile.isDirectory()) {
			File[] files = parentFile.listFiles();
			for (File f : files) {
				parseXml(f);
			}
		}
		
	}
	
	/**
	 * 解析楼层xml
	 * @param file	楼层对应文件
	 */
	private void parseXml(File file) {
		if (file == null || !file.exists()) {
			if(MLog.DEBUG) {
				throw new RuntimeException("初始化加载地图信息数据时，没有找到相应的地图文件 file:" + file.getName());
			} else {
//				MethodUtils.showToast(mContext, "没有找到相应的地图文件");
				MLog.d("没有找到相应的地图文件");
			}
//			return;
		}
		try {
			mapParser = new MapParser();
			mapParser.parseMapFile(file);
			
			//TODO 打印测试数据
//			Log.d("mylog", "=====显示的测试数据====" + toString());
			//============================测试数据===================================
			int size = floorList.keySet().size();
			List<Integer> floorKeyArr = new ArrayList<Integer>(floorList.keySet());
			Floor floor = null;
			List<Cell> cellList = null;
			Cell cell = null;
			for (int i = 0; i < size; i++) {
				int key = floorKeyArr.get(i);
				floor = floorList.get(key);
				cellList = floor.getCellList();
				int cellSize = cellList.size();
				for (int j = 0; j < cellSize; j++) {
					cell = cellList.get(j);
//					Log.d("mylog", j + " == " + cell.toString());
				}
			}
			//========================log测试数据输出完毕===============================
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
	}
	
	public static MapConfig getInstance() {
		if (instance == null) {
			instance = new MapConfig();
		}
		return instance;
	}

	@Override
	public String toString() {
		return "MapConfig [buildID=" + buildID + ", buildName=" + buildName
				+ ", note=" + note + ", floorCount=" + floorCount
				+ ", floorList=" + floorList + ", scale=" + scale + "]";
	}

	/**
	 * 增加楼层
	 * @param floorIndex	楼层索引
	 * @param floorItem		楼层对应实体
	 */
	public void addFloor(int floorIndex, Floor floorItem) {
		floorList.put(floorIndex, floorItem);
	}
	
	
	
}
