package com.minesweeperToolkit.bean;

import java.io.Serializable;
/**
 * 文件检验BEAN
 * @author zhangYe
 *
 */
public class RawBaseBean implements Serializable {
	/**
	 * UID
	 */
	private static final long serialVersionUID = -2730987152467866530L;
	
	/**录像解析工具版本 */
	public String rawVFVersion;
	/**录像软件 */
	public String program;
	/**软件版本 */
	public String version;
	/**id */
	public String player;
	/**时间戳 */
	public String timeStamp;
	/**等级 */
	public String level;
	/**宽 */
	public String width;
	/**高 */
	public String height;
	/**雷数 */
	public String mines;
	/**皮肤 */
	public String skin;
	/**模式 */
	public String mode;
	/**是否使用问号 */
	public String qm;
}