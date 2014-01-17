package com.mstoolkit.bean;

import java.io.Serializable;
/**
 * event相关BEAN
 * @author zhangYe
 *
 */
public class EventBean implements Serializable {
	/**
	 * UID
	 */
	private static final long serialVersionUID = -2730987152467866530L;
	public int l;
	public int d;
	public int r;
	public int holds;
	public int flags;
	public double distance;
	public double saoleiTime;
	public int getL() {
		return l;
	}
	public void setL(int l) {
		this.l = l;
	}
	public int getD() {
		return d;
	}
	public void setD(int d) {
		this.d = d;
	}
	public int getR() {
		return r;
	}
	public void setR(int r) {
		this.r = r;
	}
	
	public int getHolds() {
		return holds;
	}
	public void setHolds(int holds) {
		this.holds = holds;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public double getSaoleiTime() {
		return saoleiTime;
	}
	public void setSaoleiTime(double saoleiTime) {
		this.saoleiTime = saoleiTime;
	}
	public int getFlags() {
		return flags;
	}
	public void setFlags(int flags) {
		this.flags = flags;
	}
	
}