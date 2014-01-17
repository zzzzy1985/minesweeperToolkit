package com.mstoolkit.bean;

public class CellsBean {
	public int status = 0; // -1 Wall, 0 closed, 1 flagged, 2 marked, 3 opened
	public int what = 0; // 0~8 the numbers of mines around, 9 mine
	public String sta = "_";
	public CellsBean(int what) {
		status = 0;
		sta="_";
		this.what = what;
	}
}