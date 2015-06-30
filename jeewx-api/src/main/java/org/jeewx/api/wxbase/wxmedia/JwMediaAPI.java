package org.jeewx.api.wxbase.wxmedia;

import net.sf.json.JSONObject;

import org.jeewx.api.core.exception.WexinReqException;
import org.jeewx.api.core.req.WeiXinReqService;
import org.jeewx.api.core.req.model.DownloadMedia;
import org.jeewx.api.core.req.model.UploadMedia;
import org.jeewx.api.core.util.WeiXinConstant;
import org.jeewx.api.wxbase.wxmedia.model.WxDwonload;
import org.jeewx.api.wxbase.wxmedia.model.WxUpload;

/**
 * 微信--token信息
 * 
 * @author lizr
 * 
 */
public class JwMediaAPI {

	/**
	 * 
	 * @param accessToke
	 * @param type  媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb）
	 * @param fileNamePath  上传的文件目录
	 * @return
	 * @throws WexinReqException
	 */
	public static WxUpload uploadMedia(String accessToke,String type,String fileNamePath) throws WexinReqException{
		UploadMedia uploadMedia = new UploadMedia();
		uploadMedia.setAccess_token(accessToke);
		uploadMedia.setFilePathName(fileNamePath);
		uploadMedia.setType(type);
		JSONObject result = WeiXinReqService.getInstance().doWeinxinReqJson(uploadMedia);
		Object error = result.get(WeiXinConstant.RETURN_ERROR_INFO_CODE);
		WxUpload wxMedia = null;
		if(error == null){
			wxMedia = (WxUpload) JSONObject.toBean(result, WxUpload.class);
		}
		return wxMedia;
	}
	
	
	/**
	 * 下载多媒体
	 * @param accessToke
	 * @param media_id
	 * @param filePath
	 * @return
	 * @throws WexinReqException
	 */
	public static WxDwonload downMedia(String accessToke,String media_id,String filePath) throws WexinReqException{
		DownloadMedia downloadMedia = new DownloadMedia();
		downloadMedia.setAccess_token(accessToke);
		downloadMedia.setFilePath(filePath);
		downloadMedia.setMedia_id(media_id);
		JSONObject result = WeiXinReqService.getInstance().doWeinxinReqJson(downloadMedia);
		Object error = result.get(WeiXinConstant.RETURN_ERROR_INFO_CODE);
		WxDwonload wxMedia = null;
		if(error == null){
			wxMedia = (WxDwonload) JSONObject.toBean(result, WxDwonload.class);
		}
		return wxMedia;
	}
	
	
	public static void main(String[] args){
		 
		try {
			/*WxUpload s = WeixinMediaService.uploadMedia(
					"kY9Y9rfdcr8AEtYZ9gPaRUjIAuJBvXO5ZOnbv2PYFxox__uSUQcqOnaGYN1xc4N1rI7NDCaPm_0ysFYjRVnPwCJHE7v7uF_l1hI6qi6QBsA",
					"image","C:/Users/sfli.sir/Desktop/temp/2457331_160355071353_2.jpg");*/
			WxDwonload d = JwMediaAPI.downMedia(
					"kY9Y9rfdcr8AEtYZ9gPaRUjIAuJBvXO5ZOnbv2PYFxox__uSUQcqOnaGYN1xc4N1rI7NDCaPm_0ysFYjRVnPwCJHE7v7uF_l1hI6qi6QBsA",
					"wBSDL0sz3zqOSGEXG9kIht48V9W7pAQBK50rFKFx1dv6FXsVNROxcxLPMUa9L-yI",
					"C:/Users/sfli.sir/Desktop/temp/");
			System.out.println(d.getFileName());
		} catch (WexinReqException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
