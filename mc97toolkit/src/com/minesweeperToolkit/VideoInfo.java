package com.minesweeperToolkit;


public class VideoInfo {
	/**
	 * 
	 *     <table color="red" fontsize="15" border="2" >
	 *        <tr>
	 *           <th>Characteristic</th>
	 *           <th>Description</th>
	 *        </tr>
	 *        <tr>
	 *           <td><color ="0x0fFFFF">Default size</color></td>
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
		 * @return the width
		 */
		public int getWidth() {
			return width;
		}
		/**
		 * @param width the width to set
		 */
		public void setWidth(int width) {
			this.width = width;
		}
		/**
		 * @return the height
		 */
		public int getHeight() {
			return height;
		}
		/**
		 * @param height the height to set
		 */
		public void setHeight(int height) {
			this.height = height;
		}
		/**
		 * @return the cells
		 */
		public Cells[] getCells() {
			return cells;
		}
		/**
		 * @param cells the cells to set
		 */
		public void setCells(Cells[] cells) {
			this.cells = cells;
		}
		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}
		/**
		 * @return the mvfType
		 */
		public String getMvfType() {
			return mvfType;
		}
		/**
		 * @param mvfType the mvfType to set
		 */
		public void setMvfType(String mvfType) {
			this.mvfType = mvfType;
		}
		/**
		 * @return the userID
		 */
		public String getUserID() {
			return userID;
		}
		/**
		 * @param userID the userID to set
		 */
		public void setUserID(String userID) {
			this.userID = userID;
		}
		/**
		 * @return the date
		 */
		public String getDate() {
			return date;
		}
		/**
		 * @param date the date to set
		 */
		public void setDate(String date) {
			this.date = date;
		}
		/**
		 * @return the level
		 */
		public String getLevel() {
			return level;
		}
		/**
		 * @param level the level to set
		 */
		public void setLevel(String level) {
			this.level = level;
		}
		/**
		 * @return the style
		 */
		public String getStyle() {
			return style;
		}
		/**
		 * @param style the style to set
		 */
		public void setStyle(String style) {
			this.style = style;
		}
		/**
		 * @return the mode
		 */
		public String getMode() {
			return mode;
		}
		/**
		 * @param mode the mode to set
		 */
		public void setMode(String mode) {
			this.mode = mode;
		}
		/**
		 * @return the time
		 */
		public String getTime() {
			return time;
		}
		/**
		 * @param time the time to set
		 */
		public void setTime(String time) {
			this.time = time;
		}
		/**
		 * @return the bbbv
		 */
		public String getBbbv() {
			return bbbv;
		}
		/**
		 * @param bbbv the bbbv to set
		 */
		public void setBbbv(String bbbv) {
			this.bbbv = bbbv;
		}
		/**
		 * @return the bbbvs
		 */
		public String getBbbvs() {
			return bbbvs;
		}
		/**
		 * @param bbbvs the bbbvs to set
		 */
		public void setBbbvs(String bbbvs) {
			this.bbbvs = bbbvs;
		}
		/**
		 * @return the distance
		 */
		public String getDistance() {
			return distance;
		}
		/**
		 * @param distance the distance to set
		 */
		public void setDistance(String distance) {
			this.distance = distance;
		}
		/**
		 * @return the clicks
		 */
		public String getClicks() {
			return clicks;
		}
		/**
		 * @param clicks the clicks to set
		 */
		public void setClicks(String clicks) {
			this.clicks = clicks;
		}
		/**
		 * @return the zini
		 */
		public String getZini() {
			return zini;
		}
		/**
		 * @param zini the zini to set
		 */
		public void setZini(String zini) {
			this.zini = zini;
		}
		/**
		 * @return the rqp
		 */
		public String getRqp() {
			return rqp;
		}
		/**
		 * @param rqp the rqp to set
		 */
		public void setRqp(String rqp) {
			this.rqp = rqp;
		}
		/**
		 * @return the ioe
		 */
		public String getIoe() {
			return ioe;
		}
		/**
		 * @param ioe the ioe to set
		 */
		public void setIoe(String ioe) {
			this.ioe = ioe;
		}
		/**
		 * @return the completion
		 */
		public String getCompletion() {
			return completion;
		}
		/**
		 * @param completion the completion to set
		 */
		public void setCompletion(String completion) {
			this.completion = completion;
		}
		/**
		 * @return the num0
		 */
		public String getNum0() {
			return num0;
		}
		/**
		 * @param num0 the num0 to set
		 */
		public void setNum0(String num0) {
			this.num0 = num0;
		}
		/**
		 * @return the num1
		 */
		public String getNum1() {
			return num1;
		}
		/**
		 * @param num1 the num1 to set
		 */
		public void setNum1(String num1) {
			this.num1 = num1;
		}
		/**
		 * @return the num2
		 */
		public String getNum2() {
			return num2;
		}
		/**
		 * @param num2 the num2 to set
		 */
		public void setNum2(String num2) {
			this.num2 = num2;
		}
		/**
		 * @return the num3
		 */
		public String getNum3() {
			return num3;
		}
		/**
		 * @param num3 the num3 to set
		 */
		public void setNum3(String num3) {
			this.num3 = num3;
		}
		/**
		 * @return the num4
		 */
		public String getNum4() {
			return num4;
		}
		/**
		 * @param num4 the num4 to set
		 */
		public void setNum4(String num4) {
			this.num4 = num4;
		}
		/**
		 * @return the num5
		 */
		public String getNum5() {
			return num5;
		}
		/**
		 * @param num5 the num5 to set
		 */
		public void setNum5(String num5) {
			this.num5 = num5;
		}
		/**
		 * @return the num6
		 */
		public String getNum6() {
			return num6;
		}
		/**
		 * @param num6 the num6 to set
		 */
		public void setNum6(String num6) {
			this.num6 = num6;
		}
		/**
		 * @return the num7
		 */
		public String getNum7() {
			return num7;
		}
		/**
		 * @param num7 the num7 to set
		 */
		public void setNum7(String num7) {
			this.num7 = num7;
		}
		/**
		 * @return the num8
		 */
		public String getNum8() {
			return num8;
		}
		/**
		 * @param num8 the num8 to set
		 */
		public void setNum8(String num8) {
			this.num8 = num8;
		}
		/**
		 * @return the numAll
		 */
		public String getNumAll() {
			return numAll;
		}
		/**
		 * @param numAll the numAll to set
		 */
		public void setNumAll(String numAll) {
			this.numAll = numAll;
		}
		/**
		 * @return the disSpeed
		 */
		public String getDisSpeed() {
			return disSpeed;
		}
		/**
		 * @param disSpeed the disSpeed to set
		 */
		public void setDisSpeed(String disSpeed) {
			this.disSpeed = disSpeed;
		}
		/**
		 * @return the allClicks
		 */
		public String getAllClicks() {
			return allClicks;
		}
		/**
		 * @param allClicks the allClicks to set
		 */
		public void setAllClicks(String allClicks) {
			this.allClicks = allClicks;
		}
		/**
		 * @return the disBv
		 */
		public String getDisBv() {
			return disBv;
		}
		/**
		 * @param disBv the disBv to set
		 */
		public void setDisBv(String disBv) {
			this.disBv = disBv;
		}
		/**
		 * @return the disNum
		 */
		public String getDisNum() {
			return disNum;
		}
		/**
		 * @param disNum the disNum to set
		 */
		public void setDisNum(String disNum) {
			this.disNum = disNum;
		}
		/**
		 * @return the hzoe
		 */
		public String getHzoe() {
			return hzoe;
		}
		/**
		 * @param hzoe the hzoe to set
		 */
		public void setHzoe(String hzoe) {
			this.hzoe = hzoe;
		}
		/**
		 * @return the numSpeed
		 */
		public String getNumSpeed() {
			return numSpeed;
		}
		/**
		 * @param numSpeed the numSpeed to set
		 */
		public void setNumSpeed(String numSpeed) {
			this.numSpeed = numSpeed;
		}
		/**
		 * @return the zinis
		 */
		public String getZinis() {
			return zinis;
		}
		/**
		 * @param zinis the zinis to set
		 */
		public void setZinis(String zinis) {
			this.zinis = zinis;
		}
		/**
		 * @return the occam
		 */
		public String getOccam() {
			return occam;
		}
		/**
		 * @param occam the occam to set
		 */
		public void setOccam(String occam) {
			this.occam = occam;
		}
		/**
		 * @return the openings
		 */
		public String getOpenings() {
			return openings;
		}
		/**
		 * @param openings the openings to set
		 */
		public void setOpenings(String openings) {
			this.openings = openings;
		}
		/**
		 * @return the lclicks
		 */
		public String getLclicks() {
			return lclicks;
		}
		/**
		 * @param lclicks the lclicks to set
		 */
		public void setLclicks(String lclicks) {
			this.lclicks = lclicks;
		}
		/**
		 * @return the dclicks
		 */
		public String getDclicks() {
			return dclicks;
		}
		/**
		 * @param dclicks the dclicks to set
		 */
		public void setDclicks(String dclicks) {
			this.dclicks = dclicks;
		}
		/**
		 * @return the rclicks
		 */
		public String getRclicks() {
			return rclicks;
		}
		/**
		 * @param rclicks the rclicks to set
		 */
		public void setRclicks(String rclicks) {
			this.rclicks = rclicks;
		}
		/**
		 * @return the qg
		 */
		public String getQg() {
			return qg;
		}
		/**
		 * @param qg the qg to set
		 */
		public void setQg(String qg) {
			this.qg = qg;
		}
		/**
		 * @return the flags
		 */
		public String getFlags() {
			return flags;
		}
		/**
		 * @param flags the flags to set
		 */
		public void setFlags(String flags) {
			this.flags = flags;
		}
		/**
		 * @return the markFlag
		 */
		public String getMarkFlag() {
			return markFlag;
		}
		/**
		 * @param markFlag the markFlag to set
		 */
		public void setMarkFlag(String markFlag) {
			this.markFlag = markFlag;
		}
		/**
		 * @return the hold
		 */
		public String getHold() {
			return hold;
		}
		/**
		 * @param hold the hold to set
		 */
		public void setHold(String hold) {
			this.hold = hold;
		}
		/**
		 * @return the realD
		 */
		public String getRealD() {
			return realD;
		}
		/**
		 * @param realD the realD to set
		 */
		public void setRealD(String realD) {
			this.realD = realD;
		}
		/**
		 * @return the realClick
		 */
		public String getRealClick() {
			return realClick;
		}
		/**
		 * @param realClick the realClick to set
		 */
		public void setRealClick(String realClick) {
			this.realClick = realClick;
		}
		/**
		 * @return the islands
		 */
		public String getIslands() {
			return islands;
		}
		/**
		 * @param islands the islands to set
		 */
		public void setIslands(String islands) {
			this.islands = islands;
		}
	
}