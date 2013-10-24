package com.minesweeperToolkit;

public class Action {
	public String timeStamp;
	public int x = 0;
	public int y = 0;
	public int l = 0;
	public int m = 0;
	public int r = 0;
	
	public Action(String time, int x, int y, int l, int m, int r) {
		this.timeStamp = time;
		this.x = x;
		this.y = y;
		this.l = l;
		this.m = m;
		this.r = r;
	}
}
