package com.mc97toolkit;


public class MVFInfo {
	/**
	 * 
	 *     <table color="red" fontsize="15" border="2" >
	 *        <tr>
	 *           <th>Characteristic</th>
	 *           <th>Description</th>
	 *        </tr>
	 *        <tr>
	 *           <td><color ="red">Default size</color></td>
	 *           <td>Width is large enough to hold all of its children at the 
	 *               default or explicit widths of the children, plus any horizontal gap between the children, plus the left and 
	 *               right padding of the container. Height is the default or explicit height of the tallest child 
	 *               plus the top and bottom padding of the container.</td>
	 *        </tr>
	 *        <tr>
	 *           <td>Default padding</td>
	 *           <td>0 pixels for the top, bottom, left, and right values.</td>
	 *        </tr>
	 *        <tr>
	 *           <td>Default gap</td>
	 *           <td>10 pixels for the horizontal and vertical gaps.</td>
	 *        </tr>
	 *     </table>
	 *
	 */
	int width;
	/** 录像格子行数 */
	int height;
	Cells[] cells;
	public String name;
	public String mvfType;
	public String userID;
	public String date;
	public String level;
	public String style;
	public String mode;
	public String time;
	public String bbbv;
	public String bbbvs;
	public String distance;
	public String clicks;
	public String zini;
	public String rqp;
	public String ioe;
	public String completion;
	public String num0;
	public String num1;
	public String num2;
	public String num3;
	public String num4;
	public String num5;
	public String num6;
	public String num7;
	public String num8;
	public String numAll;
	public String disSpeed;
	public String allClicks;
	public String disBv;
	public String disNum;
	public String hzoe;
	public String numSpeed;
	public String zinis;
	public String occam;
	public String openings;
	public String lclicks;
	public String dclicks;
	public String rclicks;
	public String qg;
	public String flags;
	public String markFlag;
	// 1.5click 计算
	public String hold;
	// realD
	public String realD;
    // realClick
	public String realClick;
	  // islands
		public String islands;
	/**
	 * 
	 * @param name
	 * @param mvfType
	 * @param userID
	 * @param date
	 * @param level
	 * @param style
	 * @param mode
	 * @param time
	 * @param bbbv
	 * @param bbbvs
	 * @param distance
	 * @param clicks
	 * @param zini
	 * @param rqp
	 * @param ioe
	 * @param completion
	 * @param num1
	 * @param num2
	 * @param num3
	 * @param num4
	 * @param num5
	 * @param num6
	 * @param num7
	 * @param num8
	 * @param numAll
	 * @param disSpeed
	 * @param allClicks
	 * @param disBv
	 * @param disNum
	 * @param hzoe
	 * @param numSpeed
	 * @param zinis
	 * @param occam
	 * @param openings
	 * @param lclicks
	 * @param dclicks
	 * @param rclicks
	 * @param qg
	 * @param flags
	 */
	public MVFInfo(String name,String mvfType, String userID, String date, String level,
			String style, String mode, String time, String bbbv, String bbbvs,
			String distance, String clicks, String zini, String rqp,
			String ioe, String completion,String num0, String num1, String num2,
			String num3, String num4, String num5, String num6, String num7,
			String num8,
			String numAll,String disSpeed,String allClicks,String disBv,
			String disNum,String hzoe,String numSpeed,String zinis,String occam,
			String openings, String lclicks, String dclicks, String rclicks
			, String qg, String flags, String markFlag, String hold,String islands) {
		this.name = name;
		this.mvfType = mvfType;
		this.userID = userID;
		this.date = date;
		this.level = level;
		
		this.style = style;
		this.mode = mode;
		this.time = time;
		this.bbbv = bbbv;
		this.bbbvs = bbbvs;
		
		this.distance = distance;
		this.clicks = clicks;
		this.zini = zini;
		this.rqp = rqp;
		this.ioe = ioe;
		
		this.completion = completion;
		this.num0 = num0;
		this.num1 = num1;
		this.num2 = num2;
		this.num3 = num3;
		
		this.num4 = num4;
		this.num5 = num5;
		this.num6 = num6;
		this.num7 = num7;
		this.num8 = num8;
		
		this.numAll = numAll;
		this.disSpeed = disSpeed;
		this.allClicks = allClicks;
		this.disBv = disBv;
		this.disNum = disNum;
		
		this.hzoe = hzoe;
		this.numSpeed = numSpeed;
		this.zinis = zinis;
		this.occam = occam;
		this.openings = openings;
		
		this.openings = openings;
		this.lclicks = lclicks;
		this.dclicks = dclicks;
		this.rclicks = rclicks;
		this.qg = qg;
		
		this.flags = flags;
		this.markFlag = markFlag;
		this.islands = islands;
	}
	public MVFInfo(int width,
	int height,
	Cells[] cells){
		this.width = width;
		this.height = height;
		this.cells = cells;
	}
}