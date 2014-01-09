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
	public String rawVFVersion="Rev5";
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
	public String getRawVFVersion() {
		return rawVFVersion;
	}
	public void setRawVFVersion(String rawVFVersion) {
		this.rawVFVersion = rawVFVersion;
	}
	public String getProgram() {
		return program;
	}
	public void setProgram(String program) {
		this.program = program;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getPlayer() {
		return player;
	}
	public void setPlayer(String player) {
		this.player = player;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getMines() {
		return mines;
	}
	public void setMines(String mines) {
		this.mines = mines;
	}
	public String getSkin() {
		return skin;
	}
	public void setSkin(String skin) {
		this.skin = skin;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getQm() {
		return qm;
	}
	public void setQm(String qm) {
		this.qm = qm;
	}
	
}