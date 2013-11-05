package com.minesweeperToolkit.videoUtil;
import java.util.Random;

import com.minesweeperToolkit.Cell;
import com.minesweeperToolkit.Cells;
import com.minesweeperToolkit.ToolKit;
import com.minesweeperToolkit.ZiniNum;
import com.minesweeperToolkit.bean.VideoCheckBean;
/**
 * 拆分方法该方法主要内容为对mvf文件解析
 * @author zhangye
 * @date 2013-11-3
 */
public class MvfUtil implements  VideoUtil {
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
		} else {
			bean.videoVersion = VERSION96;
		}
		return bean;
	}
	/**
	 * 解析录像版本
	 扫雷网录像以97为主
	 偶见97之前版本
	 */
	public void analyzingVideo(byte[] byteStream ){
		
	}
	protected static void gettt() {
		long time = System.currentTimeMillis();
		System.out.println(time);
		for (long ij=0;ij<1;ij++){
			int width = 16;
			int height = 30;
			
			int size = width * height;
			Cell[] mvfboard = new Cell[size];
			// 生成随机数算法 
			 // 种子你可以随意生成，但不能重复
			int[] seed ;
			seed = new int[480] ;  
			for(int t=1;t<=480;t++){
				seed[t-1]=t;
			}
		    int[] ranArr = new int[480];
		    Random ran = new Random();
		    // 数量你可以自己定义。
		    for (int i = 0; i <99; i++) {
		      // 得到一个位置
		      int j = ran.nextInt(seed.length - i);
		      // 得到那个位置的数值
		      ranArr[i] = seed[j];
		      // 将最后一个未用的数字放到这里
		      seed[j] = seed[seed.length - 1 - i];
		    }
		    
		    int[] ss=new int [99];
		    for (int i = 0; i <99; i++) {
		    	ss[i]=ranArr[i];
		    }
		    for (int i = 0; i < size; i++) {
				mvfboard[i] = new Cell();
				mvfboard[i].mine = (mvfboard[i].opened = mvfboard[i].flagged = mvfboard[i].opening = mvfboard[i].opening2 = 0);
			}

			for (int i = 0; i < 99; i++) {
				
				int posX = ss[i]/30+1;
				int posY = ss[i]%30;
				int pos = (posX - 1) * height + posY - 1;
			
				mvfboard[pos].mine = 1;
			}
			Cells[] cells = new Cells[(height + 2) * (width + 2)];
			for (int i = 0; i < (height + 2) * (width + 2); i++) {
				cells[i] = new Cells(0);
			}
			for (int i = 0; i < 99; i++) {
				int posX = ss[i]/30;
				int posY =  ss[i]%30;
				int pos = (posX) * height + posY;
				cells[pos].what = 9;
			}
			for (int i = 1; i < (height + 1); i++) {
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
			}
			ZiniNum iZini = ToolKit.calcZini(width, height, 99, mvfboard);
		   System.out.println("ranArr:" +ij+"  "+ iZini.bbbv);
		}
		long time2 = System.currentTimeMillis();
		double time3 = (time2 - time) / 1000d;
	
		System.out.println(time2);
		System.out.println(time3);
	}
	
}
