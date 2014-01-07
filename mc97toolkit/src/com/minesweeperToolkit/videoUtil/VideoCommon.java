package com.minesweeperToolkit.videoUtil;

import com.minesweeperToolkit.MVFInfo;
import com.minesweeperToolkit.bean.VideoCheckBean;
import com.minesweeperToolkit.bean.VideoDisplayBean;

/**
 * 文件解析接口
 * @author zhangYe
 * @date 2013-11-3
 */
public class VideoCommon {
	public VideoCheckBean checkVersion(byte[] byteStream ){
		VideoCheckBean bean = new VideoCheckBean();
		String videoType = new String(byteStream, 1,
				4);
		bean.videoType = TYPE_RMV;
		bean.videoVersion = VERSION2;
		return bean;
	}
}
