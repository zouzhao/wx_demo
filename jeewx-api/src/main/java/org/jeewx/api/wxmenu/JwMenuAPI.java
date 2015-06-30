package org.jeewx.api.wxmenu;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jeewx.api.core.exception.WexinReqException;
import org.jeewx.api.core.req.WeiXinReqService;
import org.jeewx.api.core.req.model.menu.MenuCreate;
import org.jeewx.api.core.req.model.menu.MenuDelete;
import org.jeewx.api.core.req.model.menu.MenuGet;
import org.jeewx.api.core.req.model.menu.WeixinButton;
import org.jeewx.api.core.util.WeiXinConstant;

/**
 * 微信--menu
 * 
 * @author lizr
 * 
 */
public class JwMenuAPI {

	/**
	 * 创建菜单
	 *  button	是	一级菜单数组，个数应为1~3个
		sub_button	否	二级菜单数组，个数应为1~5个
		type	是	菜单的响应动作类型
		name	是	菜单标题，不超过16个字节，子菜单不超过40个字节
		key	click等点击类型必须	菜单KEY值，用于消息接口推送，不超过128字节
		url	view类型必须	网页链接，用户点击菜单可打开链接，不超过256字节
	 * @param accessToken
	 * @param button  的json字符串
	 * @throws WexinReqException
	 */
	public static String createMenu(String accessToken,List<WeixinButton> button) throws WexinReqException{
		MenuCreate m = new MenuCreate();
		m.setAccess_token(accessToken);
		m.setButton(button);
		JSONObject result = WeiXinReqService.getInstance().doWeinxinReqJson(m);
		Object error = result.get(WeiXinConstant.RETURN_ERROR_INFO_CODE);
		String msg = "";
		if(error == null){
			msg = result.getString("groupid");
		}else{
			msg = result.getString(WeiXinConstant.RETURN_ERROR_INFO_MSG);
		}
		return msg;
	}
	
	/**
	 * 获取所有的菜单
	 * @param accessToken
	 * @return
	 * @throws WexinReqException
	 */
	public static List<WeixinButton> getAllMenu(String accessToken) throws WexinReqException{
		MenuGet g = new MenuGet();
		g.setAccess_token(accessToken);
		JSONObject result = WeiXinReqService.getInstance().doWeinxinReqJson(g);
		Object error = result.get(WeiXinConstant.RETURN_ERROR_INFO_CODE);
		List<WeixinButton> lstButton = null;
		if(error == null){
			JSONObject menu = result.getJSONObject("menu");
			JSONArray buttons = menu.getJSONArray("button");
			JSONArray subButtons = null;
			lstButton = new ArrayList<WeixinButton>();
			WeixinButton btn = null;
			WeixinButton subBtn = null;
			List<WeixinButton> lstSubButton = null;
			for(int i = 0; i < buttons.size() ; i++){
				btn = (WeixinButton) JSONObject.toBean(buttons.getJSONObject(i), WeixinButton.class);
				subButtons = buttons.getJSONObject(i).getJSONArray("sub_button");
				if(subButtons != null){
					lstSubButton = new ArrayList<WeixinButton>();
					for(int j = 0; j < subButtons.size() ; j++){
						subBtn = (WeixinButton) JSONObject.toBean(subButtons.getJSONObject(j), WeixinButton.class);
						lstSubButton.add(subBtn);
					}
					btn.setSub_button(lstSubButton);
				}
				lstButton.add(btn);
			}
		}
		return lstButton;
	}
	
	/**
	 * 删除所有的菜单
	 * @param accessToken
	 * @return
	 * @throws WexinReqException
	 */
	public static String deleteMenu(String accessToken) throws WexinReqException{
		MenuDelete m = new MenuDelete();
		m.setAccess_token(accessToken);
		JSONObject result = WeiXinReqService.getInstance().doWeinxinReqJson(m);
		String msg = result.getString(WeiXinConstant.RETURN_ERROR_INFO_MSG);
		return msg;
	}
	
	public static void main(String[] args){
		String s="";
		try {
			s = "qf1wQXjyNhQNHMg6_rms3P-hkfsS9RcHzP1sixSdcKjgjgljws3YgP38InMJ_mE3yMNixB_8PXgiTYs8YUeQm4RKNaoaoCipyFpxHcx5H9M";
			//JwTokenAPI.getAccessToken("wx00737224cb9dbc7d","b9479ebdb58d1c6b6efd4171ebe718b5");
			System.out.println(s);
			//WeixinMenuService.createMenu(s, button)
			List<WeixinButton> b = JwMenuAPI.getAllMenu(s);
			/*
			List<WeixinButton> sub_button = new ArrayList<WeixinButton>();
			WeixinButton testsUb = new WeixinButton();
			testsUb.setName("测试sub菜单");
			sub_button.add(testsUb);*/
			b.get(0).setName("测01");;
			//s = getMenuButtonJson("button",b);
			/*Gson gson = new Gson();
			System.out.println(json);*/
			s= JwMenuAPI.createMenu(s,b);
			System.out.println(s);
		} catch (WexinReqException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
}
