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
	public int mines;
	/**软件版本 */
	public List<Integer> board;
	/**软件版本 */
	public CellBean[] cbBoard;
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
	public int getMines() {
		return mines;
	}
	public void setMines(int mines) {
		this.mines = mines;
	}
	public CellBean[] getCbBoard() {
		return cbBoard;
	}
	public void setCbBoard(CellBean[] cbBoard) {
		this.cbBoard = cbBoard;
	}
	
}