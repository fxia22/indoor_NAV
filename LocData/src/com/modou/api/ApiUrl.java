package com.modou.api;

import com.modou.utils.MLog;

/**
 * 访问数据URL类
 * @author changqiang.li
 *
 */
public class ApiUrl {

	private Configuration config;
	
	public ApiUrl(Configuration config) {
		this.config = config;
	}
	
	/**获取注册URL*/
	public String getRegisterUrl() {
		return config.getHttpURL() + "/regist.action";
	}
	
	/**获取登录URL*/
	public String getLoginUrl() {
		return config.getHttpURL() + "/login.action";
	}
	
	/**修改个人信息URL*/
	public String getUpdateUserInfoUrl() {
		return config.getHttpURL() + "/upuserinfo.action";
	}
	
	/**获取修改密码URL*/
	public String getUpdatePwdUrl() {
		return config.getHttpURL() + "/updatepassword.action";
	}
	
	/**获取意见反馈URL*/
	public String getSuggestionUrl() {
		return config.getHttpURL() + "/addfeedback.action";
	}
	
	/**获取图片列表URL*/
	public String getImgsInfoUrl() {
		return config.getHttpURL() + "/api/imgs";
	}
	
	/**获取物品详情URL*/
	public String getDetailItemUrl() {
		return config.getHttpURL() + "/getdetailitem.action";
	}
	
	/**获取图文详情URL*/
	public String getDetailInformationUrl() {
		return config.getHttpURL() + "/getdetailinformationurl.action";
	}
	
	/**获取分类数据URL*/
	public String getCategoryInfoUrl() {
		return config.getHttpURL() + "/getcategory.action";
	}
	
	/**分类子项数据URL*/
	public String getItemByCategoryUrl() {
		return config.getHttpURL() + "/getitembycategory.action";
	}
	
	/**我的评论项数据URL*/
	public String getMyCommentUrl() {
		return config.getHttpURL() + "/getalldiscussbyuserid.action";
	}
	
	/**搜索商品URL*/
	public String getSearchUrl() {
		return config.getHttpURL() + "/getitemsbykeywords.action";
	}
	
	/**推荐商品接口*/
	public String getCollectInfoUrl() {
		return config.getHttpURL() + "/getrec.action";
	}
	
	/**新品专区商品接口*/
	public String getNewAreaInfoUrl() {
		return config.getHttpURL() + "/getnew.action";
	}
	
	/**检查新版本URL*/
	public String getCheckNewClientUrl() {
		MLog.d("mylog", "检查新版本URL==" + config.getHttpURL() + "/checkversion.action");
		return config.getHttpURL() + "/checkversion.action";
	}
	
	/**获取配置URL*/
	public String getShopConfigUrl() {
		return config.getHttpURL() + "/getconfig.action";
	}
	
	/**获取下载URL*/
	public String getDownloadApkUrl() {
		return config.getHttpURL() + "/upgradeclient.action";
	}

	/**获取新品通知URL*/
	public String getNotificationUrl() {
		return config.getHttpURL() + "/getnotice.action";
	}
	
	/**发送订单URL*/
	public String getSendOrderUrl() {
		return config.getHttpURL() + "/sendorder.action";
	}
	
	/**获取订单列表URL*/
	public String getOrderInfoUrl() {
		return config.getHttpURL() + "/getorderinfo.action";
	}
	
	/**获取订单详情URL*/
	public String getOrderDetailUrl() {
		return config.getHttpURL() + "/getorderdetail.action";
	}
	
	/**修改订单信息URL*/
	public String getUpdateOrderInfoUrl() {
		return config.getHttpURL() + "/uporderstatus.action";
	}

	/**放入商品到购物车URL*/
	public String getAddShopCartUrl() {
		return config.getHttpURL() + "/addshopcart.action";
	}

	/**删除购物车商品URL*/
	public String getDelShopCartItemUrl() {
		return config.getHttpURL() + "/deleteshopcart.action";
	}
	
	/**修改购物车商品URL*/
	public String getUpdateShopCartItemUrl() {
		return config.getHttpURL() + "/upshopcart.action";
	}

	/**获取购物车商品集合URL*/
	public String getShopCartItemsUrl() {
		return config.getHttpURL() + "/getshopcart.action";
	}

	/**获取LOADING页图片的URL*/
	public String getLoadingImgUrl() {
		return config.getHttpURL() + "/getloadimage.action";
	}
	
	/**获取商品信息URL*/
	public String getPromotionInfoUrl() {
		return config.getHttpURL() + "/getpromotionbygroupid.action";
	}
	
	/**订单成功URL*/
	public String getSendPaySuccessURL() {
		return config.getHttpURL() + "/clientorderpay.action";
	}

	/**获取评论列表URL*/
	public String getCommentListUrl() {
		return config.getHttpURL() + "/getdiscussbyitemid.action";
	}
	
	/**添加评论URL*/
	public String addCommentUrl() {
		return config.getHttpURL() + "/additemdiscuss.action";
	}

	
	//============================================以下是真正用到的=========================================
	/**获取下载地图URL*/
	public String getDlMapURL() {
		return config.getHttpURL() + "要下载的地图URL";
	}
}
