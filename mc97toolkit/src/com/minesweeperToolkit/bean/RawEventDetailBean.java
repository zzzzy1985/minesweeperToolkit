package com.minesweeperToolkit.bean;

import java.io.Serializable;
/**
 * 文件检验BEAN
 * @author zhangYe
 *
 */
public class RawEventDetailBean implements Serializable {
	/**
	 * UID
	 */
	private static final long serialVersionUID = -2730987152467866530L;
	public double eventTime;
	public int mouseType;
	public int cur;
	public String mouse;
	public int sec;
	public int hun;
	public int ths;
	public int x;
	public int y;
	public int qx;
	public int qy;
	public String info;
	public double getEventTime() {
		return eventTime;
	}
	public void setEventTime(double eventTime) {
		this.eventTime = eventTime;
	}
	public String getMouse() {
		return mouse;
	}
	public void setMouse(String mouse) {
		this.mouse = mouse;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getQx() {
		return qx;
	}
	public void setQx(int qx) {
		this.qx = qx;
	}
	public int getQy() {
		return qy;
	}
	public void setQy(int qy) {
		this.qy = qy;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public int getMouseType() {
		return mouseType;
	}
	public void setMouseType(int mouseType) {
		this.mouseType = mouseType;
	}
	public int getSec() {
		return sec;
	}
	public void setSec(int sec) {
		this.sec = sec;
	}
	public int getHun() {
		return hun;
	}
	public void setHun(int hun) {
		this.hun = hun;
	}
	public int getCur() {
		return cur;
	}
	public void setCur(int cur) {
		this.cur = cur;
	}
	public int getThs() {
		return ths;
	}
	public void setThs(int ths) {
		this.ths = ths;
	}
	
}