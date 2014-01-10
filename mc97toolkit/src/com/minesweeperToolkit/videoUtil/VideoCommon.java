package com.minesweeperToolkit.videoUtil;

import com.minesweeperToolkit.bean.RawBaseBean;
import com.minesweeperToolkit.bean.RawVideoBean;
import com.minesweeperToolkit.bean.VideoDisplayBean;
import com.minesweeperToolkit.common.CommonUtil;

/**
 * 文件解析接口
 * @author zhangYe
 * @date 2013-11-3
 */
public class VideoCommon {
	public static void convertVideoDisplay(RawVideoBean rawVideoBean ,VideoDisplayBean videoDisplayBean){
		if(rawVideoBean.checkFlag){
			RawBaseBean rawBaseBean=rawVideoBean.rawBaseBean;
			videoDisplayBean.setMvfType(rawBaseBean.program);
			videoDisplayBean.setLevel(rawBaseBean.level);
		}
		else{
			CommonUtil.fillBean(videoDisplayBean,"ZZV5");
		}
		
	}
public static RawVideoBean errorVideo(RawVideoBean rawVideoBean,String errMessage){
	rawVideoBean.checkFlag=false;
	rawVideoBean.errorMessage=errMessage;
		return rawVideoBean;
	}
}
