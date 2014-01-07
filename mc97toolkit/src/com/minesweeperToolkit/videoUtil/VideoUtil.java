package com.minesweeperToolkit.videoUtil;

import com.minesweeperToolkit.MVFInfo;
import com.minesweeperToolkit.bean.VideoCheckBean;
import com.minesweeperToolkit.bean.VideoDisplayBean;

/**
 * 文件解析接口
 * @author zhangYe
 * @date 2013-11-3
 */
public interface VideoUtil {
	
	/**
	 * 检查录像版本
	 * @param byteStream 字节流
	 */
	public VideoCheckBean checkVersion(byte[] byteStream );
	/**
	 * 分析录像版本
	 * @param byteStream 字节流
	 */
	public void analyzeVideo(byte[] byteStream ,VideoDisplayBean bean);
	public MVFInfo analyzingVideo(byte[] byteStream ,String name);
}
