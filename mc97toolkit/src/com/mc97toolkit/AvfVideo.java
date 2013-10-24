package com.mc97toolkit;

public class AvfVideo {
	public int sec,hun,ths = 0;
	public int x,y = 0;
	public int x1,y1 = 0;
	public int x2,y2 = 0;
	public int mouse = 0;
	
	public AvfVideo( int sec, int hun, int ths, int x, int y, int mouse) {
		this.sec = sec;
		this.hun = hun;
		this.ths = ths;
		this.x = x;
		this.y = y;
		this.mouse = mouse;
	}
	public AvfVideo( int sec,  int hun,int x, int y, int mouse,int x1, int x2, int y1, int y2) {
		this.sec = sec;
		this.hun = hun;
		this.x = x;
		this.y = y;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.mouse = mouse;
	}
}
