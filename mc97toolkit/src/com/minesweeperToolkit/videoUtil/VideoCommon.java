package com.minesweeperToolkit.videoUtil;

import java.util.HashMap;
import java.util.Map;

import com.minesweeperToolkit.bean.BoardBean;
import com.minesweeperToolkit.bean.RawBaseBean;
import com.minesweeperToolkit.bean.RawBoardBean;
import com.minesweeperToolkit.bean.RawVideoBean;
import com.minesweeperToolkit.bean.VideoDisplayBean;
import com.minesweeperToolkit.common.CommonUtil;

/**
 * 文件解析接口
 * 
 * @author zhangYe
 * @date 2013-11-3
 */
public class VideoCommon {
	/**
	 * 转换录像
	 * @param rawVideoBean
	 * @param videoDisplayBean
	 */
	public static void convertVideoDisplay(RawVideoBean rawVideoBean,
			VideoDisplayBean videoDisplayBean) {
		if (rawVideoBean.checkFlag) {
			RawBaseBean rawBaseBean = rawVideoBean.rawBaseBean;
			// 设定程序名称
			videoDisplayBean.setMvfType(rawBaseBean.program);
			// 设定程序版本
			videoDisplayBean.setVersion(rawBaseBean.version);
			// 设定用户标识
			videoDisplayBean.setUserID(rawBaseBean.player);
			// 设定时间
			videoDisplayBean.setDate(rawBaseBean.timeStamp);
			// 设定级别
			videoDisplayBean.setLevel(setLevel(rawBaseBean.level));
			setBoardInfo(rawVideoBean,videoDisplayBean);
		} else {
			CommonUtil.fillBean(videoDisplayBean, "ZZV5");
		}

	}
	/**
	 * 设定级别
	 * @param level
	 * @return levelString
	 */
	public static String setLevel(String level){
			Map<String,String> map =new HashMap<String,String>();
			map.put("0", "null");
			map.put("1", "beg");
			map.put("2", "int");
			map.put("3", "exp");
			map.put("4", "custom");
			return map.get(level);
	}
	/**
	 * 出现错误情况
	 * @param rawVideoBean
	 * @param errMessage
	 * @return
	 */
	public static RawVideoBean errorVideo(RawVideoBean rawVideoBean,
			String errMessage) {
		rawVideoBean.checkFlag = false;
		rawVideoBean.errorMessage = errMessage;
		return rawVideoBean;
	}
	/**
	 * 根据board读取基本信息
	 * @param level
	 * @return levelString
	 */
	public static void setBoardInfo(RawVideoBean rawVideoBean,
			VideoDisplayBean videoDisplayBean){
		RawBoardBean rawBoardBean=rawVideoBean.getRawBoardBean();
		BoardBean  boardBean  = BoardCommon.getBoardBean(rawBoardBean.width, rawBoardBean.height,
				rawBoardBean.mines, rawBoardBean.cbBoard);
		videoDisplayBean.setBbbv(String.valueOf(boardBean.bbbv));
	}
	
}
