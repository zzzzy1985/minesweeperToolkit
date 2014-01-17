package com.mstoolkit;

public class HistoryInfo {
	public String date;
	public String level;
	public String style;
	public double time;
	public int bbbv;
	public double bbbvs;
	public int lClick;
	public int dClick;
	public int rClick;
	public double clicks;
	public double rqp;
	public double ioe;
	public double qg;

	public HistoryInfo() {
	}

	public HistoryInfo(String date, String level, double time, int bbbv,
			double bbbvs, int lClick, int dClick, int rClick, double clicks,
			double rqp, double ioe, double qg) {
		this.date = date;
		this.level = level;
		this.time = time;
		this.bbbv = bbbv;
		this.bbbvs = bbbvs;
		this.lClick = lClick;
		this.dClick = dClick;
		this.rClick = rClick;
		this.clicks = clicks;
		this.rqp = rqp;
		this.ioe = ioe;
		this.qg = qg;
	}
}