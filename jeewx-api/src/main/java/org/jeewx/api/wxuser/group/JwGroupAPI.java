package org.jeewx.api.wxuser.group;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jeewx.api.core.exception.WexinReqException;
import org.jeewx.api.core.req.WeiXinReqService;
import org.jeewx.api.core.req.model.user.BatchGroupMembersUpdate;
import org.jeewx.api.core.req.model.user.Group;
import org.jeewx.api.core.req.model.user.GroupCreate;
import org.jeewx.api.core.req.model.user.GroupGet;
import org.jeewx.api.core.req.model.user.GroupGetId;
import org.jeewx.api.core.req.model.user.GroupMembersUpdate;
import org.jeewx.api.core.req.model.user.GroupUpdate;
import org.jeewx.api.core.util.WeiXinConstant;

/**
 * 微信--用户
 * 
 * @author lizr
 * 
 */
public class JwGroupAPI {

	public static String RETURN_INFO_NAME = "groups";
	
	/**
	 * 创建分组信息
	 * @param accesstoken
	 * @param groupName
	 * @return
	 * @throws WexinReqException
	 */
	public static GroupCreate createGroup(String accesstoken ,String groupName ) throws WexinReqException{
		GroupCreate c = new GroupCreate();
		c.setAccess_token(accesstoken);
		Group g = new Group();
		g.setName(groupName);
		c.setGroup(g);
		JSONObject result = WeiXinReqService.getInstance().doWeinxinReqJson(c);
		Object error = result.get(WeiXinConstant.RETURN_ERROR_INFO_CODE);
		GroupCreate groupCreate = null;
		if(error == null){
			groupCreate = (GroupCreate) JSONObject.toBean(result, GroupCreate.class);
		}
		return groupCreate;
	}
	
	/**
	 * 获取所有的分组信息
	 * @param accesstoken
	 * @return
	 * @throws WexinReqException
	 */
	public static List<Group> getAllGroup(String accesstoken) throws WexinReqException{
		GroupGet c = new GroupGet();
		c.setAccess_token(accesstoken);
		JSONObject result = WeiXinReqService.getInstance().doWeinxinReqJson(c);
		Object error = result.get(WeiXinConstant.RETURN_ERROR_INFO_CODE);
		List<Group> lstGroup = null;
		if(error == null){
			JSONArray infoArray = result.getJSONArray(RETURN_INFO_NAME);
			lstGroup = new ArrayList<Group>(infoArray.size());
			for(int i=0;i<infoArray.size();i++){
				lstGroup.add((Group)JSONObject.toBean(infoArray.getJSONObject(i), Group.class));
			}
		}
		return lstGroup;
	}
	
	/**
	 * 获取用户分组id
	 * @param accesstoken
	 * @param openid
	 * @return
	 * @throws WexinReqException
	 */
	public static String getUserGroup(String accesstoken,String openid) throws WexinReqException{
		GroupGetId g = new GroupGetId();
		g.setAccess_token(accesstoken);
		g.setOpenid(openid);
		JSONObject result = WeiXinReqService.getInstance().doWeinxinReqJson(g);
		Object error = result.get(WeiXinConstant.RETURN_ERROR_INFO_CODE);
		String groupId = "";
		if(error == null){
			groupId = result.getString("groupid");
		}else{
			groupId = result.getString(WeiXinConstant.RETURN_ERROR_INFO_MSG);
		}
		return groupId;
	}
	
	/**
	 * 更新分组    正常返回ok
	 * @param accesstoken
	 * @param groupId
	 * @param groupNewName
	 * @throws WexinReqException 
	 */
	public static String updateGroup(String accesstoken,String groupId,String groupNewName) throws WexinReqException{
		GroupUpdate groupUpdate = new GroupUpdate();
		groupUpdate.setAccess_token(accesstoken);
		Group g = new Group();
		g.setId(groupId);
		g.setName(groupNewName);
		groupUpdate.setGroup(g);
		JSONObject result = WeiXinReqService.getInstance().doWeinxinReqJson(groupUpdate);
		return result.getString("errmsg");
	}
	
	/**
	 * 修改用户分组
	 * @param accesstoken
	 * @param openid
	 * @param to_groupid
	 * @return
	 * @throws WexinReqException
	 */
	public static String groupMemberMove(String accesstoken,String openid,String to_groupid) throws WexinReqException{
		GroupMembersUpdate u = new GroupMembersUpdate();
		u.setAccess_token(accesstoken);
		u.setOpenid(openid);;
		u.setTo_groupid(to_groupid);
		JSONObject result = WeiXinReqService.getInstance().doWeinxinReqJson(u);
		return result.getString("errmsg");
	}
	
	/**
	 * 批量更新用户分组
	 * @param accesstoken
	 * @param openid_list
	 * @param to_groupid
	 * @return
	 * @throws WexinReqException
	 */
	public static String batchGroupMemberMove(String accesstoken,List<String> openid_list,String to_groupid) throws WexinReqException{
		BatchGroupMembersUpdate u = new BatchGroupMembersUpdate();
		u.setAccess_token(accesstoken);
		u.setOpenid_list(openid_list);
		u.setTo_groupid(to_groupid);
		JSONObject result = WeiXinReqService.getInstance().doWeinxinReqJson(u);
		return result.getString("errmsg");
	}
	
	
	public static void main(String[] args){
		 
		try {
			/*GroupCreate d = WeixinGroupService.createGroup(
					"kY9Y9rfdcr8AEtYZ9gPaRUjIAuJBvXO5ZOnbv2PYFxox__uSUQcqOnaGYN1xc4N1rI7NDCaPm_0ysFYjRVnPwCJHE7v7uF_l1hI6qi6QBsA",
					"测试-yI");
			System.out.println(d.getGroup().getId());
					*/
			List<Group> d = JwGroupAPI.getAllGroup(
					"kY9Y9rfdcr8AEtYZ9gPaRUjIAuJBvXO5ZOnbv2PYFxox__uSUQcqOnaGYN1xc4N1rI7NDCaPm_0ysFYjRVnPwCJHE7v7uF_l1hI6qi6QBsA"
					);
			for(Group g : d){
				System.out.println(g.getName()+"----"+g.getId()+"----"+g.getCount());
			}
		} catch (WexinReqException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
