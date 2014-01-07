package com.minesweeperToolkit.bean;

import java.io.Serializable;
/**
 * 统一录像BEAN
 * @author zhangYe
 *
 */
public class RawVideoBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7358112206234839795L;
	/**录像基本信息 */
	public RawBaseBean rawBaseBean;
	/**录像board信息 */
	public RawBoardBean rawBoardBean;
	/**录像详细信息*/
	public RawDetailBean rawDetailBean;
	public RawBaseBean getRawBaseBean() {
		return rawBaseBean;
	}
	public void setRawBaseBean(RawBaseBean rawBaseBean) {
		this.rawBaseBean = rawBaseBean;
	}
	public RawBoardBean getRawBoardBean() {
		return rawBoardBean;
	}
	public void setRawBoardBean(RawBoardBean rawBoardBean) {
		this.rawBoardBean = rawBoardBean;
	}
	public RawDetailBean getRawDetailBean() {
		return rawDetailBean;
	}
	public void setRawDetailBean(RawDetailBean rawDetailBean) {
		this.rawDetailBean = rawDetailBean;
	}
	
}