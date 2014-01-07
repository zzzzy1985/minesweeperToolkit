package com.minesweeperToolkit.videoUtil;
import java.util.Random;

import com.minesweeperToolkit.Cell;
import com.minesweeperToolkit.MVFInfo;
import com.minesweeperToolkit.ZiniNum;
import com.minesweeperToolkit.bean.VideoCheckBean;
import com.minesweeperToolkit.bean.VideoDisplayBean;
/**
 * 拆分方法该方法主要内容为对mvf文件解析
 * @author zhangye
 * @date 2013-11-3
 */
public class MvfUtil implements  VideoUtil {
	/**byte00*/
	private int byteZero=0x00;
	/**MVF97第一位标志*/
	private int markFor97First=0x11;
	/**MVF97第一位标志*/
	private int markFor97Second=0x4D;
	/**类型mvf*/
	private String TYPE_MVF="mvf";
	/**版本0.96*/
	private String VERSION96="0.96";
	/**版本0.97*/
	private String VERSION97="0.97";
	/**版本0.97NoHead*/
	private String VERSION97NH="0.97NH";
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
		bean.videoType = TYPE_MVF;
		int firstByte = byteStream[0x01] & 0xFF;
		int secondByte = byteStream[0x02] & 0xFF;
		if (firstByte == markFor97First && secondByte == markFor97Second) {
			bean.videoVersion = VERSION97;
		} else if (firstByte == byteZero && secondByte == byteZero) {
			bean.videoVersion = VERSION97NH;
		}else{
			bean.videoVersion = VERSION96;
		}
		return bean;
	}
	/**
	 * 解析录像版本
	 扫雷网录像以97为主
	 偶见97之前版本
	 */
	public void analyzeVideo(byte[] byteStream,VideoDisplayBean bean ){
	}
	/**
	 * 解析录像版本
	 扫雷网录像以97为主
	 偶见97之前版本
	 */
	public MVFInfo analyzingVideo(byte[] byteStream,String name ){
		return null;
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
