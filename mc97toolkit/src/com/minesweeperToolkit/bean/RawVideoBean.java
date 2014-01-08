package com.minesweeperToolkit.bean;

import java.io.Serializable;
import java.util.List;
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
	/**检验标志默认为true */
	public boolean checkFlag = true;
	/**录像基本信息 */
	public RawBaseBean rawBaseBean;
	/**录像board信息 */
	public RawBoardBean rawBoardBean;
	/**录像详细信息*/
	public List<RawEventDetailBean> rawEventDetailBean;
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
	public List<RawEventDetailBean> getRawEventDetailBean() {
		return rawEventDetailBean;
	}
	public void setRawEventDetailBean(List<RawEventDetailBean> rawEventDetailBean) {
		this.rawEventDetailBean = rawEventDetailBean;
	}
	
	
	
}