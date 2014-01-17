package com.minesweeperToolkit.videoUtil;
import java.util.ArrayList;
import java.util.List;

import com.minesweeperToolkit.MVFInfo;
import com.minesweeperToolkit.bean.CellBean;
import com.minesweeperToolkit.bean.CellsBean;
import com.minesweeperToolkit.bean.MvfEventDetailBean;
import com.minesweeperToolkit.bean.RawBaseBean;
import com.minesweeperToolkit.bean.RawBoardBean;
import com.minesweeperToolkit.bean.RawEventDetailBean;
import com.minesweeperToolkit.bean.RawVideoBean;
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
		RawVideoBean rawVideoBean = convertRawVideo(byteStream);
		
		VideoCommon.convertVideoDisplay(rawVideoBean,bean);
	}
	/**
	 * 解析录像版本
	 扫雷网录像以97为主
	 偶见97之前版本
	 */
	public MVFInfo analyzingVideo(byte[] byteStream,String name ){
		return null;
	}
	/**
	 * 这段代码来自C语言的改写
	 * 
	 * @param byteStream
	 * @return
	 */
	private RawVideoBean convertRawVideo(byte[] byteStream) {
		RawVideoBean rawVideoBean = new RawVideoBean();
		RawBaseBean rawBaseBean= new RawBaseBean();
		rawBaseBean.setProgram("clone");
		rawVideoBean.setRawBaseBean(rawBaseBean);
		int offset = 0x00;
		int firstByte = byteStream[offset++] & 0xFF;
		int secondByte = byteStream[offset++] & 0xFF;
		/** So it is a newer MVF.
		   Then we read the offset 27 byte. 
		   It is a part of the string representing the year of the release
		   "2005" means it's 0.97, "2006" or "2007" mean it is 2006 or 2007
		   it also can be "2008" -- then it's from Abiu's "funny mode" release */
		if (firstByte == markFor97First && secondByte == markFor97Second) {
			
			offset+=25;
			int flag = byteStream[offset] & 0xFF;
			// 0x35 5
			if(flag==0x35){
				rawBaseBean.version= VERSION97;
				offset=74;
				rawVideoBean=read097(byteStream,rawVideoBean,offset);
			}
			// 0x36 6
			else if(flag==0x36){
				rawBaseBean.version= "2006";
				offset=71;
				rawVideoBean=read07(byteStream,rawVideoBean,offset);
			}
			// 0x37 7
			else if(flag==0x37){
				rawBaseBean.version= "2007";
				offset=71;
				rawVideoBean=read07(byteStream,rawVideoBean,offset);
			}
			// 0x38 8
			else if(flag==0x38){
				rawBaseBean.version= "97biu";
				rawVideoBean=read097(byteStream,rawVideoBean,offset);
			}
		} else if (firstByte == byteZero && secondByte == byteZero) {
			rawBaseBean.version = VERSION97NH;
			rawVideoBean=read097(byteStream,rawVideoBean,offset);
		}else{
			rawBaseBean.version = VERSION96;
			rawVideoBean=read097(byteStream,rawVideoBean,offset);
		}
		return rawVideoBean;
	}
	/**
	 * 
	 * @param byteStream
	 * @param rawVideoBean
	 * @param offset
	 * @return
	 */
	private RawVideoBean read07(byte[] byteStream, RawVideoBean rawVideoBean,
			int offset) {
		RawBaseBean rawBaseBean= rawVideoBean.rawBaseBean;
		/** The 2007 header contains only date, level and mode */
		
		return rawVideoBean;
	}
	/**
	 * 
	 * @param byteStream
	 * @param rawVideoBean
	 * @param offset
	 * @return
	 */
	private RawVideoBean read097(byte[] byteStream, RawVideoBean rawVideoBean,
			int offset) {
		RawBaseBean rawBaseBean= rawVideoBean.rawBaseBean;
		/** The 0.97 header contains date, level, mode, */
		/** score, 3bv and solved 3bv, number of clicks */
		int qm;
		int has_date;
		int month,year,year1,year2,day,hour,minute,second;
		int has_info;
		int level,mode;
		has_date=has_info=1;
		month=(byteStream[offset++] & 0xFF);
		day=(byteStream[offset++] & 0xFF);
		year1=(byteStream[offset++] & 0xFF);
		year2=(byteStream[offset++] & 0xFF);
		year=year1*256+year2;
		hour=(byteStream[offset++] & 0xFF);
		minute=(byteStream[offset++] & 0xFF);
		second=(byteStream[offset++] & 0xFF);
		level=(byteStream[offset++] & 0xFF);
		mode=(byteStream[offset++] & 0xFF);
		int score1=(byteStream[offset++] & 0xFF);
		int score2=(byteStream[offset++] & 0xFF);
		int sec=score1*256+score2;
		int score3=(byteStream[offset++] & 0xFF);
		int thun=score3*10;
		int bbbv1=(byteStream[offset++] & 0xFF);
		int bbbv2=(byteStream[offset++] & 0xFF);
		int bbbv=bbbv1*256+bbbv2;
		int solbbbv1=(byteStream[offset++] & 0xFF);
		int solbbbv2=(byteStream[offset++] & 0xFF);
		int solbbbv=solbbbv1*256+solbbbv2;
		int lclick1=(byteStream[offset++] & 0xFF);
		int lclick2=(byteStream[offset++] & 0xFF);
		int lclick=lclick1*256+lclick2;
		int dclick1=(byteStream[offset++] & 0xFF);
		int dclick2=(byteStream[offset++] & 0xFF);
		int dclick=dclick1*256+dclick2;
		int rclick1=(byteStream[offset++] & 0xFF);
		int rclick2=(byteStream[offset++] & 0xFF);
		int rclick=rclick1*256+rclick2;
		qm=(byteStream[offset++] & 0xFF);
		/** Now, the board and the player's name */
		int width=(byteStream[offset++] & 0xFF);
		int height=(byteStream[offset++] & 0xFF);
		int boardSz=width*height;
		int m1=(byteStream[offset++] & 0xFF);
		int m2=(byteStream[offset++] & 0xFF);
		int m=m1*256+m2;
		List<Integer> board = new ArrayList<Integer>();
		CellBean[] cbBoard =new CellBean[boardSz] ;
		CellsBean[] cells = new CellsBean[(height + 2) * (width + 2)];
		for (int i = 0; i < (height + 2) * (width + 2); i++) {
			cells[i] = new CellsBean(0);
		}
		for (int i = 0; i < boardSz; ++i) {
			int temp = 0;
			board.add(temp);
			cbBoard[i] = new CellBean();
			cbBoard[i].mine = (cbBoard[i].opened = cbBoard[i].flagged = cbBoard[i].opening = cbBoard[i].opening2 = 0);
		}
		for(int i=0;i<m;i++){
			// y为横坐标 x为纵坐标
			int y=(byteStream[offset++] & 0xFF)-1;
			int x=(byteStream[offset++] & 0xFF)-1;
			board.set(x * width + y, 1);
			int pos = (y) * height + x;
			cbBoard[pos].mine = 1;
			int posbean = (x+1) * (width + 2) + y+1;
			cells[posbean].what = 9;
		}
		int len =(byteStream[offset++] & 0xFF);
		String userID = new String(byteStream, offset++,
				len);
		offset+=(len-1);
		//permutation排列
		/* The two leading bytes determine the permutation */
		int leading1=(byteStream[offset++] & 0xFF);
		int leading2=(byteStream[offset++] & 0xFF);
		int leading=leading1*256+leading2;
		double num1=Math.sqrt(leading);
		double num2=Math.sqrt(leading+1000.0);
		double num3=Math.sqrt(num1+1000.0);
		int mult=100000000;
		double expression = Math
				.cos(Math.sqrt(Math.sqrt(leading) + 1000.0D) + 1000.0D);
		double num5 = Math.sin(Math.sqrt(Math.sqrt(leading + 1000)));
		double num6 = Math.cos(Math.sqrt(Math.sqrt(leading) + 1000.0D));
		double num7 = Math.sin(Math.sqrt(Math.sqrt(leading)) + 1000.0D);
		double num8 = Math.cos(Math.sqrt(Math.sqrt(leading + 1000) + 1000.0D));
		String str2 = String.format("%.8f",
				new Object[] { Double.valueOf(expression) });
		String str3 = String.format("%.8f",
				new Object[] { Double.valueOf(num5) });
		String str4 = String.format("%.8f",
				new Object[] { Double.valueOf(num6) });
		String str5 = String.format("%.8f",
				new Object[] { Double.valueOf(num7) });
		String str6 = String.format("%.8f",
				new Object[] { Double.valueOf(num8) });

		String x000 = str2.substring(str2.length() - 8)
				+ str3.substring(str3.length() - 8)
				+ str4.substring(str4.length() - 8)
				+ str5.substring(str5.length() - 8)
				+ str6.substring(str6.length() - 8);
		int cur=0;
		int[] byt=new int[41];
		int[] bit=new int[41];
		for(int i=0;i<=9;++i)
			for(int j=0;j<40;++j)
				if((x000.charAt(j) - 0x30) == i)
				{
					byt[cur]=j/8;
					bit[cur++]=1<<(j%8);
				}
		int size1=(byteStream[offset++] & 0xFF);
		int size2=(byteStream[offset++] & 0xFF);
		int size3=(byteStream[offset++] & 0xFF);
		int size=size1*65536+size2*256+size3;
		//mvf的录像格式记录比较特殊
		List<MvfEventDetailBean> lst = new ArrayList<MvfEventDetailBean>();
		for(int i=0;i<size;++i)
		{MvfEventDetailBean bean = new MvfEventDetailBean();
			int e1=(byteStream[offset++] & 0xFF);
			int e2=(byteStream[offset++] & 0xFF);
			int e3=(byteStream[offset++] & 0xFF);
			int e4=(byteStream[offset++] & 0xFF);
			int e5=(byteStream[offset++] & 0xFF);
			int[] e=new int[]{e1,e2,e3,e4,e5};
			bean.rb=applyPerm(0,byt,bit,e);
			bean.mb=applyPerm(1,byt,bit,e);
			bean.lb=applyPerm(2,byt,bit,e);
			bean.x=bean.y=bean.ths=bean.sec=0;
			for(int j=0;j<9;++j)
			{
				bean.x|=(applyPerm(12+j,byt,bit,e)<<j);
				bean.y|=(applyPerm(3+j,byt,bit,e)<<j);
			}
			for(int j=0;j<7;++j) bean.ths|=(applyPerm(21+j,byt,bit,e)<<j);
			bean.ths*=10;
			for(int j=0;j<10;++j) bean.sec|=(applyPerm(28+j,byt,bit,e)<<j);
			lst.add(bean);
		}
		List<RawEventDetailBean> rawLst =new ArrayList<RawEventDetailBean>();
		MvfEventDetailBean firstBean=lst.get(0);
		RawEventDetailBean firstRawBean =new RawEventDetailBean();
		int firstMouseType=0;
		 if(firstBean.lb>0){
			 firstMouseType=3;
		}
		else if(firstBean.rb>0){
			firstMouseType=9;
		}
		else if(firstBean.mb>0){
			firstMouseType=33;
		}
		else{
			firstMouseType=1;
		}
		 firstRawBean.mouseType=firstMouseType;
		 firstRawBean.x=firstBean.x;
		 firstRawBean.y=firstBean.y;
		 firstRawBean.eventTime=(double)firstBean.sec+(double)firstBean.hun/100.0d;
			rawLst.add(firstRawBean);
		for(int i=1;i<size;++i){
			MvfEventDetailBean bean=lst.get(i);
			MvfEventDetailBean prebean=lst.get(i-1);
			int mouseType=0;
			// mouseType 1 mv 3lc  9rc  33mc 5lr 17rr 65 mr
			if(bean.x!=prebean.x||bean.y!=prebean.y){
				mouseType=1;
				RawEventDetailBean rawBean =new RawEventDetailBean();
				rawBean.mouseType=mouseType;
				rawBean.x=bean.x;
				rawBean.y=bean.y;
				rawBean.eventTime=(double)bean.sec+(double)bean.ths/1000.0d;
				rawLst.add(rawBean);
			}if(bean.lb>0&&prebean.lb==0){
				mouseType=3;
			}
			else if(bean.rb>0&&prebean.rb==0){
				mouseType=9;
			}
			else if(bean.mb>0&&prebean.mb==0){
				mouseType=33;
			}
			else if(bean.lb==0&&prebean.lb>0){
				mouseType=5;
			}
			else if(bean.rb==0&&prebean.rb>0){
				mouseType=17;
			}
			else if(bean.mb==0&&prebean.mb>0){
				mouseType=65;
			}
			else{
				continue;
				}
			RawEventDetailBean rawBean =new RawEventDetailBean();
			rawBean.mouseType=mouseType;
			rawBean.x=bean.x;
			rawBean.y=bean.y;
			rawBean.eventTime=(double)bean.sec+(double)bean.ths/1000.0d;
			rawLst.add(rawBean);
		}
		// 标识
		rawBaseBean.setPlayer(userID);
		// int month,year,year1,year2,day,hour,minute,second;
		rawBaseBean.setTimeStamp(String.format(
				"%02d/%02d/%02d %02d:%02d:%02d",
				new Object[] { Integer.valueOf(year),
						Integer.valueOf(month),
						Integer.valueOf(day),
						Integer.valueOf(hour),
						Integer.valueOf(minute),
						Integer.valueOf(second) }));
		rawBaseBean.setMode(String.valueOf(mode));
		rawBaseBean.setLevel(String.valueOf(level));
		rawBaseBean.setQm(String.valueOf(qm));
		RawBoardBean rawBoardBean=new RawBoardBean();
		rawBoardBean.setHeight(height);
		rawBoardBean.setWidth(width);
		rawBoardBean.setBoard(board);
		rawBoardBean.setCbBoard(cbBoard);
		rawBoardBean.setCells(cells);
		rawBoardBean.setMines(m);
		rawVideoBean.setRawBoardBean(rawBoardBean);
		rawVideoBean.setRawEventDetailBean(rawLst);
		return rawVideoBean;
	}
	private int applyPerm(int num, int[] byt, int[] bit, int[] e) {
		return ((e[byt[num]]&bit[num])>0)?1:0;
	}
}
