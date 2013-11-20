package com.minesweeperToolkit.videoUtil;
import java.util.Random;

import com.minesweeperToolkit.Cell;
import com.minesweeperToolkit.Const;
import com.minesweeperToolkit.MVFInfo;
import com.minesweeperToolkit.VideoInfo;
import com.minesweeperToolkit.ZiniNum;
import com.minesweeperToolkit.bean.VideoCheckBean;
/**
 * 拆分方法该方法主要内容为对rmv文件解析
 * 关于rmv 介绍
 * rmv是用VSweeper 扫的，一般用在线上比赛上
 * 早期版本后缀名为umv
 * @author zhangye
 * @date 2013-1120
 */
public class RmvUtil implements  VideoUtil {
	/**byte00*/
	private int byteZero=0x00;
	/**MVF97第一位标志*/
	private int markFor97First=0x11;
	/**MVF97第一位标志*/
	private int markFor97Second=0x4D;
	/**类型rmv*/
	private String TYPE_RMV="rmv";
	/**版本2.0*/
	private String VERSION2="2.0";
	/**
	 *  检查录像版本
		 扫雷网录像以97为主
		 偶见97之前版本
		 初版不保证健壮性 即认为传入文件一定为录像文件
		 <table style="color: blue; margin-left: 20px" border="1" >
			 <tr>
				 <th>条件1</th>
				 <th>条件2</th>
				 <th >判定结果</th>
			 </tr>
			 <tr>
				 <td rowspan="4">第一位是0x11 且第2位是0x4D</td>
				 <td>第27位（0x1b）值0x35 (字符5)</td>
				 <td>97版本</td>
			 </tr>
			  <tr>
				 
				 <td>第27位（0x1b）值0x36 (字符6)</td>
				 <td>06版本</td>
			 </tr>
			  <tr>
				 
				 <td>第27位（0x1b）值0x37 (字符7)</td>
				 <td>07版本</td>
			 </tr>
			  <tr>
				
				 <td>第27位（0x1b）值0x38 (字符8)</td>
				 <td>biu版本</td>
			 </tr>
			 <tr>
			 <td colspan=2>第一位是0x00 且第2位是0x00的情况</td>
			 <td >97版本（缺少头部）</td>
			 </tr>
			  <tr>
			 <td colspan=2>非该情况</td>
			 <td >pre97的版本（96或更早）</td>
			 </tr>
		 </table>
		 <br/>
	
		 @param byteStream 文件流
		 @see <a href="http://www.minesweeper.info/forum/viewtopic.php?f=26&t=86">
		 http://www.minesweeper.info/forum/viewtopic.php?f=26&t=86</a>
	 */
	public VideoCheckBean checkVersion(byte[] byteStream ){
		VideoCheckBean bean = new VideoCheckBean();
		String videoType = new String(byteStream, 1,
				4);
		
		bean.videoType = TYPE_RMV;
		bean.videoVersion = VERSION2;
		return bean;
	}
	/**
	 * 解析录像版本
		头4位为版本 正常的显示为 *rmv（0x2A 0x72 0x6D 0x76）
		之后2为是录像type 正常的为 1(0x00 0x01)
	 */
	public MVFInfo analyzingVideo(byte[] byteStream ,String name){
		//VideoInfo videoInfo=new VideoInfo();
		VideoCheckBean checkBean=checkVersion(byteStream);
		if (checkBean.checkFlag){
			
		}
		// type 必须为1
		int type1=byteStream[0x04] & 0xFF;
		int type2=byteStream[0x05] & 0xFF;
		int type =type1*256+type2;
		if(type!=1){
			
		}
		//fs 再取4位
		int fs1=byteStream[0x06] & 0xFF;
		int fs2=byteStream[0x07] & 0xFF;
		int fs3=byteStream[0x08] & 0xFF;
		int fs4=byteStream[0x09] & 0xFF;
		long fs= fs1*65536+fs2*16777216+fs3+fs4*256;
		//result_string_size
		int rs1=byteStream[0x0A] & 0xFF;
		int rs2=byteStream[0x0B] & 0xFF;
		int rs =rs1*256+rs2;
		//version_info_size
		int vi1=byteStream[0x0C] & 0xFF;
		int vi2=byteStream[0x0D] & 0xFF;
		int vi =vi1*256+vi2;
		//play_info_size
		int pi1=byteStream[0x0E] & 0xFF;
		int pi2=byteStream[0x0F] & 0xFF;
		int pi =pi1*256+pi2;
		//board_size
		int bo1=byteStream[0x10] & 0xFF;
		int bo2=byteStream[0x11] & 0xFF;
		int bo =bo1*256+bo2;
		//preflags_size
		int pf1=byteStream[0x12] & 0xFF;
		int pf2=byteStream[0x13] & 0xFF;
		int pf =pf1*256+pf2;
		// properties_size
		int pp1=byteStream[0x14] & 0xFF;
		int pp2=byteStream[0x15] & 0xFF;
		int pp =pp1*256+pp2;
		//vid_size
		int vid1=byteStream[0x16] & 0xFF;
		int vid2=byteStream[0x17] & 0xFF;
		int vid3=byteStream[0x18] & 0xFF;
		int vid4=byteStream[0x19] & 0xFF;
		long vid= vid1*65536+vid2*16777216+vid3+vid4*256;
		// cs_size
		int cs1=byteStream[0x1A] & 0xFF;
		int cs2=byteStream[0x1B] & 0xFF;
		int cs =cs1*256+cs2;
		// 0x1C // newline
		//result_string
		String  resultString= new String(byteStream, 0x1D,
				rs-1);
		String[] rsSplit=resultString.split("#");
		// 级别
		String rsSplit1=rsSplit[0].split(":")[1];
		if ("Beginner".equals(rsSplit1)){
			rsSplit1=Const.LEVEL_BEG;
		}else if("Intermediate".equals(rsSplit1)){
			rsSplit1=Const.LEVEL_INT;
		}else{
			rsSplit1=Const.LEVEL_EXP;
		}
		// SCORE
		String rsSplit2=rsSplit[1].split(":")[1];
		// NAME
		String rsSplit3=rsSplit[2].split(":")[1];
		// NICK
		String rsSplit4=rsSplit[3].split(":")[1];
		// 3BV
		String rsSplit5=rsSplit[4].split(":")[1];
		// nf
		String rsSplit6=rsSplit[5].split(":")[1];
		
		String style="1".equals(rsSplit6) ?Const.STYLE_NF : Const.STYLE_FL;
		int startLength=0x1D+rs-1;
		// 版本信息
		String versionInfo= new String(byteStream, startLength,
				vi);
		startLength+=vi;
		int pis1=byteStream[startLength] & 0xFF;
		int pis2=byteStream[startLength+1] & 0xFF;
		int pis =pis1*256+pis2;
		int playInfoLength=byteStream[startLength+2] & 0xFF;
		// playInfo信息
		String playInfo= new String(byteStream, startLength+3,
				playInfoLength);
		startLength+=playInfoLength+7;
		// board信息 width height
		int width =byteStream[startLength] & 0xFF;
		int height =byteStream[startLength+1] & 0xFF;
		int mines1=byteStream[startLength+2] & 0xFF;
		int mines2=byteStream[startLength+3] & 0xFF;
		int mine =mines1*256+mines2;
		int rmvSize = width * height;
		Cell[] rmvBoard = new Cell[rmvSize];
		for (int i = 0; i < rmvSize; i++) {
			rmvBoard[i] = new Cell();
			rmvBoard[i].mine = (rmvBoard[i].opened = rmvBoard[i].flagged = rmvBoard[i].opening = rmvBoard[i].opening2 = 0);
		}
		startLength+=4;
		for (int i = 0; i < mine; i++) {
			// posX 表示行1
			// posY 表示列6
			int c = byteStream[(startLength+ i * 2)] & 0xFF;
			int d = byteStream[(startLength+1+ i * 2)] & 0xFF;
			int pos =d*width+c;
			rmvBoard[pos].mine = 1;
		}
		startLength+=(mine*2);
		// preflags
		if (pf>0){
			int num_preflags1=byteStream[startLength] & 0xFF;
			int num_preflags2=byteStream[startLength+1] & 0xFF;
			int num_preflags=num_preflags1*256+num_preflags2;
			for(int i=0;i<num_preflags;i++){
				//TODO
/*
				c=_fgetc(RMV);d=_fgetc(RMV);
				
				video[cur].event=4;
				video[cur].x=square_size/2+c*square_size;
				video[cur].y=square_size/2+d*square_size;
				video[cur++].time=0;
							
				video[cur].event=5;
				video[cur].x=square_size/2+c*square_size;
				video[cur].y=square_size/2+d*square_size;
				video[cur++].time=0;*/
			}
		}
		startLength+=3;
		// properties
		int qm=byteStream[startLength] & 0xFF;
		int nf=byteStream[startLength+1] & 0xFF;
		int mmode=byteStream[startLength+2] & 0xFF;
		int llevel=byteStream[startLength+3] & 0xFF;
		startLength+=pp;
		//  下面是 video信息
		
		String mvfType = checkBean.videoType;
		String userID =playInfo;
		String date = Const.CALCULATING;
		String level = rsSplit1;
		String mode = Const.CALCULATING;
		String time = rsSplit2;
		String bbbv = rsSplit5;
		String bbbvs =  String.format("%.3f", new Object[] { Double.valueOf(bbbv)     
				/ (Double.valueOf(time) ) });
		String distance = Const.CALCULATING;
		String clicks = Const.CALCULATING;
		String zini = Const.CALCULATING;
		String islands = Const.CALCULATING;
		String rqp = Const.CALCULATING;
		String ioe = Const.CALCULATING;
		String completion ="100%";
		String num0 = Const.CALCULATING;
		String num1 = Const.CALCULATING;
		String num2 = Const.CALCULATING;
		String num3 = Const.CALCULATING;
		String num4 = Const.CALCULATING;
		String num5 = Const.CALCULATING;
		String num6 = Const.CALCULATING;
		String num7 = Const.CALCULATING;
		String num8 = Const.CALCULATING;
		String numAll = Const.CALCULATING;
		String disSpeed = Const.CALCULATING;
		String openings = Const.CALCULATING;
		String allClicks = Const.CALCULATING;
		String disBv = Const.CALCULATING;
		String disNum = Const.CALCULATING;
		String hzoe = Const.CALCULATING;
		String numSpeed = Const.CALCULATING;
		String zinis = Const.CALCULATING;
		String occam = Const.CALCULATING;
		String lclicks = Const.CALCULATING;
		String dclicks = Const.CALCULATING;
		String rclicks = Const.CALCULATING;
		String qg = String.format(
				"%.3f",
				new Object[] { Double.valueOf((Math.pow(
						(Double.valueOf(time)), 1.7D))
						/ Double.valueOf(bbbv)) });
		String flags = Const.CALCULATING;
		String markFlag = Const.CALCULATING;
		String hold = Const.CALCULATING;
		
		return new MVFInfo(name, mvfType, userID, date, level, style,
				mode, time, bbbv, bbbvs, distance, clicks, zini, rqp,
				ioe, completion, num0, num1, num2, num3, num4, num5,
				num6, num7, num8, numAll, disSpeed, allClicks, disBv,
				disNum, hzoe, numSpeed, zinis, occam, openings,
				lclicks, dclicks, rclicks, qg, flags, markFlag, hold,islands);
	}
	public static void main( String[] args) {
		long time = System.currentTimeMillis();
		System.out.println(time);
		for (long ij=0;ij<25;ij++){
			int width = 8;
			int height = 8;
			int mines = 10;
			int size = width * height;
			Cell[] mvfboard = new Cell[size];
			// 生成随机数算法 
			 // 种子你可以随意生成，但不能重复
			int[] seed ;
			seed = new int[size] ;  
			for(int t=1;t<=size;t++){
				seed[t-1]=t;
			}
		    int[] ranArr = new int[size];
		    Random ran = new Random();
		    // 数量你可以自己定义。
		    for (int i = 0; i <mines; i++) {
		      // 得到一个位置
		      int j = ran.nextInt(seed.length - i);
		      // 得到那个位置的数值
		      ranArr[i] = seed[j];
		      // 将最后一个未用的数字放到这里
		      seed[j] = seed[seed.length - 1 - i];
		    }
		    
		    int[] ss=new int [mines];
		    for (int i = 0; i <mines; i++) {
		    	ss[i]=ranArr[i];
		    //	 System.out.print(ss[i]+" ");
		    }
		    
		   for (int i = 0; i < size; i++) {
				mvfboard[i] = new Cell();
				mvfboard[i].mine = (mvfboard[i].opened = mvfboard[i].flagged = mvfboard[i].opening = mvfboard[i].opening2 = 0);
			}
		    //System.out.println(" ");
			for (int i = 0; i < mines; i++) {
				
				int posX = ss[i]/width+1;
				int posY = ss[i]%width;
				int pos = (posX - 1) * height + posY - 1;
				
				// System.out.print(pos+" ");
				mvfboard[pos].mine = 1;
			}
			/*Cells[] cells = new Cells[(height + 2) * (width + 2)];
			for (int i = 0; i < (height + 2) * (width + 2); i++) {
				cells[i] = new Cells(0);
			}
			for (int i = 0; i < mines; i++) {
				int posX = ss[i]/width;
				int posY =  ss[i]%width;
				int pos = (posX) * height + posY;
				cells[pos].what = 9;
			}*/
		/*	for (int i = 1; i < (height + 1); i++) {
				for (int j = 1; j < (width + 1); j++) {
					if (cells[j * i + j].what != 9) {
						cells[j * i + j].what = cells[(j) * (i - 1)
								+ j - 1].what == 9 ? 1
								: 0 + cells[(j) * (i - 1) + j].what == 9 ? 1
										: 0 + cells[(j) * (i - 1)
												+ j + 1].what == 9 ? 1
												: 0 + cells[(j) * i
														+ j - 1].what == 9 ? 1
														: 0 + cells[(j)
																* i
																+ j
																+ 1].what == 9 ? 1
																: 0 + cells[(j)
																		* (i + 1)
																		+ j
																		- 1].what == 9 ? 1
																		: 0 + cells[(j)
																				* (i + 1)
																				+ j].what == 9 ? 1
																				: 0 + cells[(j)
																						* (i + 1)
																						+ j
																						+ 1].what == 9 ? 1
																						: 0;
					}

				}
			}*/
			calcingbbbvs(width, height, 10, mvfboard);
		//   System.out.println("ranArr:" +ij+"  "+ iZini.openings);
		}
		long time2 = System.currentTimeMillis();
		double time3 = (time2 - time) / 1000d;
	
		System.out.println(time2);
		System.out.println(time3);
	}
	public static ZiniNum calcingbbbvs(int width,int height,int mines,Cell[] board){
		int size = width * height;
		int closed_cells = size;
		int openings = 0;
		int bbbv = 0;
		int num0 = 0;
		int num1 = 0;
		int num2 = 0;
		int num3 = 0;
		int num4 = 0;
		int num5 = 0;
		int num6 = 0;
		int num7 = 0;
		int num8 = 0;
    int islands=0;
		for (int r = 0; r < height; r++) {
			for (int c = 0; c < width; c++) {
				int index = c * height + r;
				board[index].rb = (r != 0 ? r - 1 : r);
				board[index].re = (r == height - 1 ? r : r + 1);
				board[index].cb = (c != 0 ? c - 1 : c);
				board[index].ce = (c == width - 1 ? c : c + 1);
			}

		}

		for (int i = 0; i < size; i++) {
			board[i].premium = (-(board[i].number = getnumber(board, height, i)) - 2);
		}
		for (int i = 0; i < size; i++)
			if ((board[i].number == 0) && (board[i].opening == 0)) {
				openings++;
				processopening(board, height, openings, i);
			}
		for (int i = 0; i < size; i++){
			if(board[i].mine == 1){
				board[i].openingAr=2;
			}
			if ((board[i].opening >0) && (board[i].mine == 0) ){
				board[i].openingAr=1;
				for (int rr = board[i].rb; rr <= board[i].re; rr++)
					for (int cc = board[i].cb; cc <= board[i].ce; cc++)
						if(board[(cc * height + rr)].openingAr!=2){
						board[(cc * height + rr)].openingAr=1;
						}
			}
		}
		
		for (int i = 0; i < size; i++){
			if(board[i].openingAr ==0){
				board[i].openingAr =3;
				islands(board,height,size,i);
				islands++;
			}
		}
	
		bbbv = openings;
		for (int i = 0; i < size; i++) {
			if ((board[i].opening == 0) && (board[i].mine == 0))
				bbbv++;
			board[i].premium += getadj3bv(board, height, i);
		}
System.out.println(bbbv);
		return null;
	}
	private static int getnumber(Cell[] board, int height, int index) {
		int res = 0;
		for (int rr = board[index].rb; rr <= board[index].re; rr++) {
			for (int cc = board[index].cb; cc <= board[index].ce; cc++) {
				res += board[(cc * height + rr)].mine;
			}
		}
		return res;
	}
	private static void setopeningborder(Cell[] board, int op_id, int index) {
		if (board[index].opening != 0)
			board[index].opening = op_id;
		else if (board[index].opening != op_id)
			board[index].opening2 = op_id;
	}

	private static void processopening(Cell[] board, int height, int op_id,
			int index) {
		board[index].opening = op_id;
		for (int rr = board[index].rb; rr <= board[index].re; rr++)
			for (int cc = board[index].cb; cc <= board[index].ce; cc++) {
				int i = cc * height + rr;
				if (board[i].number != 0)
					setopeningborder(board, op_id, i);
				else if (board[i].opening == 0)
					processopening(board, height, op_id, i);
			}
	}
	private static int getadj3bv(Cell[] board, int height, int index) {
		int res = 0;
		if (board[index].number == 0)
			return 1;
		for (int rr = board[index].rb; rr <= board[index].re; rr++)
			for (int cc = board[index].cb; cc <= board[index].ce; cc++) {
				int i = cc * height + rr;
				res += ((board[i].mine == 0) && (board[i].opening == 0) ? 1 : 0);
			}
		if (board[index].opening != 0) {
			res++;
		}
		if (board[index].opening2 != 0) {
			res++;
		}
		return res;
	}
	private static void islands(Cell[] board, int height, int size, int index) {
		for (int rr = board[index].rb; rr <= board[index].re; rr++)
			for (int cc = board[index].cb; cc <= board[index].ce; cc++)
			{
				if(board[(cc * height + rr)].openingAr==0){
					board[(cc * height + rr)].openingAr=3;
					islands(board,height,size,(cc * height + rr));
			
				}
			}
				
	}
}
