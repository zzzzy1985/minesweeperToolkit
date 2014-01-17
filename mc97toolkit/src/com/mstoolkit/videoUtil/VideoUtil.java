package com.mstoolkit.videoUtil;

import com.mstoolkit.MVFInfo;
import com.mstoolkit.bean.VideoCheckBean;
import com.mstoolkit.bean.VideoDisplayBean;

/**
 * 文件解析接口
 * @author zhangYe
 * @since 2013-11-3
 */
public interface VideoUtil {
	
	/**
	 * 检查录像版本
	 * @param byteStream 字节流
	 * @return VideoCheckBean eban
	 */
	public VideoCheckBean checkVersion(byte[] byteStream );
	/**
	 * 分析录像版本
	 * @param byteStream 字节流
	 */
	public void analyzeVideo(byte[] byteStream ,VideoDisplayBean bean);
	public MVFInfo analyzingVideo(byte[] byteStream ,String name);
}
