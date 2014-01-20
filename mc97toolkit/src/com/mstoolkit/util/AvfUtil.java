package com.mstoolkit.util;

import java.util.ArrayList;
import java.util.List;

import com.mstoolkit.MVFInfo;
import com.mstoolkit.bean.CellBean;
import com.mstoolkit.bean.CellsBean;
import com.mstoolkit.bean.RawBaseBean;
import com.mstoolkit.bean.RawBoardBean;
import com.mstoolkit.bean.RawEventDetailBean;
import com.mstoolkit.bean.RawVideoBean;
import com.mstoolkit.bean.VideoCheckBean;
import com.mstoolkit.bean.VideoDisplayBean;

/**
 * 拆分方法该方法主要内容为对avf文件解析
 *  关于avf 介绍
 * 
 * @author zhangye
 * @version 2013-11-20
 */
public class AvfUtil implements VideoUtil {

	
	public VideoCheckBean checkVersion(byte[] byteStream) {
		VideoCheckBean bean = new VideoCheckBean();
		return bean;
	}

	/**
	 * 解析AVF录像版本
	 */
	public void analyzeVideo(byte[] byteStream, VideoDisplayBean bean) {
		RawVideoBean rawVideoBean = convertRawVideo(byteStream);
		
		VideoCommon.convertVideoDisplay(rawVideoBean,bean);
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
		rawBaseBean.setProgram("arbiter");
		int offset = 0x00;
		int version = byteStream[0x00] & 0xFF;
		int l = 0;
		int ll = 0;
		int ls = 0;
		int c = 0;
		int mode = 0;
		int w = 0;
		int h = 0;
		int m = 0;
		int cr[] = new int[8];
		int timestamp[] = new int[100];
		String ts="";
		int qm = 0;
		int fs = 0;
		if (version != 0) {
			fs = 1;
			// meaningless bytes 原解释
		} else {
			version = byteStream[0x01] & 0xFF;
			c = byteStream[0x02] & 0xff;
			l = c & 0x1;
			ll = c & 0x8;
			ls = c & 0x10;
			// int
		}
		offset += 5;
		c = byteStream[offset] & 0xff;
		mode = c - 2;
		switch (mode) {
		case 1:
			w = h = 8;
			m = 10;
			break;
		case 2:
			w = h = 16;
			m = 40;
			break;
		case 3:
			w = 30;
			h = 16;
			m = 99;
			break;
		case 4:
			w = (byteStream[offset] & 0xff) + 1;
			h = (byteStream[offset + 1] & 0xff) + 1;
			m = (byteStream[offset + 2] & 0xff);
			m = m * 256 + (byteStream[offset + 3] & 0xff);
			offset += 4;
			break;
		default:
			return VideoCommon.errorVideo(rawVideoBean, "mode不正确");
		}
		List<Integer> board = new ArrayList<Integer>();
		CellBean[] cbBoard =new CellBean[w*h] ;
		CellsBean[] cells = new CellsBean[(h + 2) * (w + 2)];
		for (int i = 0; i < (h + 2) * (w + 2); i++) {
			cells[i] = new CellsBean(0);
		}
		for (int i = 0; i < w * h; ++i) {
			int temp = 0;
			board.add(temp);
			cbBoard[i] = new CellBean();
			cbBoard[i].mine = (cbBoard[i].opened = cbBoard[i].flagged = cbBoard[i].opening = cbBoard[i].opening2 = 0);
		}
		for (int i = 0; i < m; ++i) {
			offset++;
			// 分别为x坐标 和y坐标
			c = (byteStream[offset] & 0xff) - 1;
			offset++;
			int d = (byteStream[offset] & 0xff )- 1;
			board.set(c * w + d, 1);
			int pos = (d) * h + c;
			cbBoard[pos].mine = 1;
			int posbean = (c+1) * (w + 2) + d+1;
			cells[posbean].what = 9;
		}
		
		
		// question marks | length of timestamp | [timestamp]
		for (int i = 0; i < 7; ++i)
			cr[i] = 0;
		while (true) {
			while (cr[3] != 0x5b) // search for timestamp [
			{
				cr[0] = cr[1];
				cr[1] = cr[2];
				cr[2] = cr[3];
				offset++;
				cr[3] = byteStream[offset] & 0xff;
			}
			cr[0] = cr[1];
			cr[1] = cr[2];
			cr[2] = cr[3];
			offset++;
			cr[3] = byteStream[offset] & 0xff;
			if (cr[3] - 47 == mode)
				break;
		}
		if (cr[0] != 17 && cr[0] != 127) {
			return VideoCommon.errorVideo(rawVideoBean, "qm不正确");
		}
		qm = (cr[0] == 17 ? 1 : 0);
		offset++;
		int i = 0;
		int tsS=offset;
		while (i < 100) {
			offset++;
			// 0x7c |
			if ((timestamp[i++] = byteStream[offset] & 0xff) == 0x7c) {
				timestamp[--i] = 0;
				break;
			}
		}
		int tsE=offset;
		ts= new String(byteStream, tsS+1, tsE-tsS-1);
		while ((int) (byteStream[offset] & 0xff) != 0x5d) {
			offset++;
		}
		for (i = 0; i < 7; ++i)
			cr[i] = 0;
		while (cr[2] != 1 || cr[1] > 1) // cr[2]=time=1; cr[1]=position_x div
										// 256<=30*16 div 256 = 1
		{
			cr[0] = cr[1];
			cr[1] = cr[2];
			offset++;
			cr[2] = byteStream[offset] & 0xff;
		}
		for (i = 3; i < 8; ++i) {
			offset++;
			cr[i] = byteStream[offset] & 0xff;
		}
		// events
		List<RawEventDetailBean> lst = new ArrayList<RawEventDetailBean>();
		int cur = 0;
		while (true) {
			RawEventDetailBean bean = new RawEventDetailBean();
			bean.mouseType = cr[0]& 0xff;
			bean.cur = cur;
			bean.x = (int) (cr[1]& 0xff)* 256 + (cr[3]& 0xff);
			bean.y = (int) (cr[5]& 0xff) * 256 + (cr[7]& 0xff);
			bean.sec = (int) (cr[6]& 0xff) * 256 + (cr[2]& 0xff) - 1;
			bean.hun =(int)cr[4]& 0xff;
			bean.eventTime=(double)(bean.sec)+(double)(bean.hun)/100.0d;
			lst.add(bean);
			if (bean.sec < 0)
				break;

			for (i = 0; i < 8; ++i) {
				offset++;
				cr[i] = byteStream[offset] & 0xff;
			}
			++cur;
		}
		// let's find player's name
		for (i = 0; i < 3; ++i)
			cr[i] = 0;
		// cs=
		while (cr[0] != 'c' || cr[1] != 's' || cr[2] != '=') {
			cr[0] = cr[1];
			cr[1] = cr[2];
			offset++;
			cr[2] = byteStream[offset] & 0xff;
		}
		if (fs == 0) {
			for (i = 0; i < cur; ++i) {
				offset++;
				lst.get(i).ths = byteStream[offset] & 0xF;
			}
			for (i = 0; i < 17; ++i) {
				offset++;
			}
			while ((int) (byteStream[offset] & 0xff) != 13) {
				offset++;
			}
		} else {
			for (i = 0; i < 17; ++i) {
				offset++;
			}
		}
		offset++;
		// 下面是skin 签名 和 版本
		int next = 0;
		// real time
		while (byteStream[offset] != 13) {
			offset++;
		}
		// skin
		while (byteStream[offset] != 0x3a) {
			offset++;
		}
		offset++;
		int nt = offset;
		// skin
		while (byteStream[nt+next] != 13) {
			offset++;
			next++;
		}
		String skin = new String(byteStream, nt, next);
		offset++;
		nt= offset;
		next = 0;
		// skin
		while (byteStream[nt+next] != 13) {
			offset++;
			next++;
		}
		String userID = new String(byteStream, nt, next);
		String ver = new String(byteStream, offset+21, 6);
		// 设定版本
		rawBaseBean.setVersion(ver);
	    //  设定用户id
		rawBaseBean.setPlayer(userID);
	    // 设定时间戳
		rawBaseBean.setTimeStamp(ts);
		// 设定级别
		rawBaseBean.setLevel(String.valueOf(mode));
		// 设定宽
		rawBaseBean.setWidth(String.valueOf(w));
		// 设定高
		rawBaseBean.setHeight(String.valueOf(h));
		// 设定雷数
		rawBaseBean.setMines(String.valueOf(m));
		// 设定皮肤
		rawBaseBean.setSkin(skin);
		if(l==0){
		// 设定模式
		rawBaseBean.setMode(String.valueOf(mode));}
		else{
			rawBaseBean.setMode("Lucky");
			rawBaseBean.setLuckLibrary(String.valueOf(ll));
			rawBaseBean.setLuckSolver(String.valueOf(ls));
		}
		rawBaseBean.setQm(String.valueOf(qm));
		rawVideoBean.setRawBaseBean(rawBaseBean);
		RawBoardBean rawBoardBean=new RawBoardBean();
		rawBoardBean.setHeight(h);
		rawBoardBean.setWidth(w);
		rawBoardBean.setBoard(board);
		rawBoardBean.setCbBoard(cbBoard);
		rawBoardBean.setCells(cells);
		rawBoardBean.setMines(m);
		rawVideoBean.setRawBoardBean(rawBoardBean);
		rawVideoBean.setRawEventDetailBean(lst);
		return rawVideoBean;
	}

	/**
	 * 解析录像版本 头4位为版本 正常的显示为 *rmv（0x2A 0x72 0x6D 0x76） 之后2为是录像type 正常的为 1(0x00
	 * 0x01)
	 */
	public MVFInfo analyzingVideo(byte[] byteStream, String name) {
		return null;
	}
}
