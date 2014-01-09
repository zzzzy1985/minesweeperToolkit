package com.minesweeperToolkit.videoUtil;

import com.minesweeperToolkit.bean.RawVideoBean;
import com.minesweeperToolkit.bean.VideoDisplayBean;

/**
 * 文件解析接口
 * @author zhangYe
 * @date 2013-11-3
 */
public class VideoCommon {
	public static void convertVideoDisplay(RawVideoBean rawVideoBean ,VideoDisplayBean videoDisplayBean){
		if(rawVideoBean.checkFlag){
			
		}
		else{
			
		}
		
	}
public static RawVideoBean errorVideo(RawVideoBean rawVideoBean,String errMessage){
	rawVideoBean.checkFlag=false;
	rawVideoBean.errorMessage=errMessage;
		return rawVideoBean;
	}
}
