package com.minesweeperToolkit.bean;

import java.io.Serializable;
import java.util.List;
/**
 * 文件检验BEAN
 * @author zhangYe
 *
 */
public class RawBoardBean implements Serializable {
	/**
	 * UID
	 */
	private static final long serialVersionUID = -2730987152467866530L;
	/**检验标志默认为true */
	public boolean checkFlag = true;
	/**软件版本 */
	public String width;
	/**软件版本 */
	public String height;
	/**软件版本 */
	public List<Integer> board;
}