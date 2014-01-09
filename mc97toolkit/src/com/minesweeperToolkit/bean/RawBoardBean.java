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
	/**软件版本 */
	public int width;
	/**软件版本 */
	public int height;
	/**软件版本 */
	public List<Integer> board;
	public List<Integer> getBoard() {
		return board;
	}
	public void setBoard(List<Integer> board) {
		this.board = board;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
}