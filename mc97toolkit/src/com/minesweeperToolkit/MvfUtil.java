package com.minesweeperToolkit;
import java.util.Random;
/**
 * 拆分方法该方法主要内容为对mvf文件解析
 * @author zhangye
 * @date 2013-11-3
 */
public class MvfUtil {
	/**
	 * 检查录像版本
	 扫雷网录像以97为主
	 偶见97之前版本
	 */
	public void checkVersion(){
		
	}
	public static void main(String arg) {
		long time = System.currentTimeMillis();
		System.out.println(time);
		for (long ij=0;ij<1;ij++){
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
		    	System.out.print(ss[i]);
		    }
		 return;
					
		   /* 
		    for (int i = 0; i < size; i++) {
				mvfboard[i] = new Cell();
				mvfboard[i].mine = (mvfboard[i].opened = mvfboard[i].flagged = mvfboard[i].opening = mvfboard[i].opening2 = 0);
			}

			for (int i = 0; i < mines; i++) {
				
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
			ZiniNum iZini = ToolKit.calcZini(width, height, 99, mvfboard)
		   System.out.println("ranArr:" +ij+"  "+ iZini.bbbv);;*/
		}
		long time2 = System.currentTimeMillis();
		double time3 = (time2 - time) / 1000d;
	
		System.out.println(time2);
		System.out.println(time3);
	}
	
}
