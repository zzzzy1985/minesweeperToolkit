package com.minesweeperToolkit.videoUtil;

import java.util.List;

import com.minesweeperToolkit.bean.CellsBean;
import com.minesweeperToolkit.bean.EventBean;
import com.minesweeperToolkit.bean.RawBoardBean;
import com.minesweeperToolkit.bean.RawEventDetailBean;
import com.minesweeperToolkit.bean.RawVideoBean;

/**
 * event解析
 * 
 * @author zhangYe
 * @date 2013-11-3
 */
public class EventCommon {
	/**
	 * 转换录像 这段代码采取的方式是先竖再横？
	 * 
	 * @param rawVideoBean
	 * @param videoDisplayBean
	 */
	public static EventBean getEventBean(RawVideoBean rawVideoBean) {
		RawBoardBean rawBoardBean = rawVideoBean.getRawBoardBean();
		int height = rawBoardBean.height;
		int width = rawBoardBean.width;
		List<RawEventDetailBean> eventLst = rawVideoBean.rawEventDetailBean;
		int ax = 0;
		int ay = 0;

		int lstatus = 0;
		int rstatus = 0;
		int l = 0;
		int d = 0;
		int r = 0;
		int flags = 0;
		// 计算1.5click
		int holds = 0;
		double path = 0.0d;
		double saoleiTime = 0.0d;
		String mouseTypeNomv = "";
		CellsBean[] cells = rawBoardBean.cells;
	
		for (int i = 1; i < (height + 1); i++) {
			for (int j = 1; j < (width + 1); j++) {

				if (cells[(width + 2) * i + j].what != 9) {
					cells[(width + 2) * i + j].what = (cells[(width + 2)
							* (i - 1) + j - 1].what == 9 ? 1 : 0)
							+ (cells[(width + 2) * (i - 1) + j].what == 9 ? 1
									: 0)
							+ (cells[(width + 2) * (i - 1) + j + 1].what == 9 ? 1
									: 0)
							+ (cells[(width + 2) * i + j - 1].what == 9 ? 1
									: 0)
							+ (cells[(width + 2) * i + j + 1].what == 9 ? 1
									: 0)
							+ (cells[(width + 2) * (i + 1) + j - 1].what == 9 ? 1
									: 0)
							+ (cells[(width + 2) * (i + 1) + j].what == 9 ? 1
									: 0)
							+ (cells[(width + 2) * (i + 1) + j + 1].what == 9 ? 1
									: 0);
				}
			}
		}
		CellsBean[] tempCells = new CellsBean[(height) * (width)];
		for (int i = 0; i < tempCells.length; i++) {
			tempCells[i] = new CellsBean(0);
		}
		int tempR = 0;
		// 计算click 和 path
		for (int i = 0; i < eventLst.size() ; i++) {
			RawEventDetailBean rawEventDetailBean = eventLst.get(i);
			if(rawEventDetailBean.eventTime<0){
				continue;
			}
			// 为了计算准确的右键数 需要模拟录像操作
			int mouse = rawEventDetailBean.mouseType;
			int nx = 0;
			int ny = 0;
			int olstatus = 0;
			int orstatus = 0;
			nx = rawEventDetailBean.x;
			ny = rawEventDetailBean.y;
			boolean flag = true;
			if ("rr".equals(mouseTypeNomv)) {
				flag = false;
			}
			int lact = 0;
			int ract = 0;
			if (mouse == 1 && nx == ax && ny == ay) {

				continue;
			}
			if (mouse < 0) {
				mouse = mouse + 256;
			}
			String mouseType = "";

			switch (mouse) {
			case 1:
				mouseType = "mv";
				lact = 0;
				ract = 0;
				break;
			case 3:
				mouseType = "lc";
				lact = 1;
				ract = 0;
				break;
			case 5:
				mouseType = "lr";
				lact = -1;
				ract = 0;
				break;
			case 9:
				mouseType = "rc";
				lact = 0;
				ract = 1;
				break;
			case 17:
				mouseType = "rr";
				lact = 0;
				ract = -1;
				break;
			case 33:
				mouseType = "mc";
				lact = 1;
				ract = 1;
				break;
			case 65:
				mouseType = "mr";
				lact = -1;
				ract = -1;
				break;
			//
			case 145:
				mouseType = "rr";
				lact = 0;
				ract = -1;
				break;
			case 193:
				mouseType = "mr";
				lact = 0;
				ract = 0;
				break;
			default:
				mouseType = "rr";
				lact = 0;
				ract = -1;
			}
			if (!"mv".equals(mouseType)) {
				mouseTypeNomv = mouseType;
			}
			lstatus += lact;
			rstatus += ract;
			olstatus = lstatus - lact;
			orstatus = rstatus - ract;
			if (lact == -1 && orstatus == 0 && flag) {
				l++;
				// l++的时候
				int qx = (nx) / 16 + 1;
				int qy = (ny) / 16 + 1;
				if (qx <= width && qy <= height) {
					int xx = tempCells[(qy - 1) * width + qx - 1].status;
					if (xx == 0) {
						digg(qx, qy, tempCells, cells,height,width);
					}
				}
			}

			if ((lact == -1 ? 1 : 0 + ract == -1 ? 1 : 0)
					* (olstatus == 1 ? 1 : 0) * (orstatus == 1 ? 1 : 0) > 0) {
				d++;
				if (tempR == 1) {
					r--;
					tempR = 0;
				}
				int qx = (nx) / 16 + 1;
				int qy = (ny) / 16 + 1;

				if (qx <= width && qy <= height) {
					int thiswhat = cells[(qy) * (width + 2) + (qx)].what;
					// 计算点击位置周围一圈雷数
					if (thiswhat != 0) {
						int arroundFlag = 0;
						// 存在左上格 如果不在第一行或第一列
						if (!((qx == 1) || (qy == 1))) {
							arroundFlag += (("F".equals(tempCells[(qy - 2)
									* width + qx - 2].sta)) ? 1 : 0);
						}
						// 存在上格 如果不在第一行
						if (qy != 1) {
							arroundFlag += (("F".equals(tempCells[(qy - 2)
									* width + qx - 1].sta)) ? 1 : 0);
						}
						// 存在右上格 如果不在第一行或最后一列
						if (!((qx == width) || (qy == 1))) {
							arroundFlag += (("F".equals(tempCells[(qy - 2)
									* width + qx].sta)) ? 1 : 0);
						}
						// 存在左格 如果不在第一列
						if (qx != 1) {
							arroundFlag += (("F".equals(tempCells[(qy - 1)
									* width + qx - 2].sta)) ? 1 : 0);
						}
						// 存在右格 如果不在最后一列
						if (qx != width) {
							arroundFlag += (("F".equals(tempCells[(qy - 1)
									* width + qx].sta)) ? 1 : 0);
						}
						// 存在左下格 如果不在最后一行或最后一列

						if (!((qx == 1) || (qy == height))) {
							arroundFlag += (("F".equals(tempCells[(qy) * width
									+ qx - 2].sta)) ? 1 : 0);
						}
						// 存在下格 如果不在最后一行
						if (qy != height) {
							arroundFlag += (("F".equals(tempCells[(qy) * width
									+ qx - 1].sta)) ? 1 : 0);
						}
						// 存在右下格 如果不在最后一行或最后一列
						if (!((qx == width) || (qy == height))) {
							arroundFlag += (("F".equals(tempCells[(qy) * width
									+ qx].sta)) ? 1 : 0);
						}

						// 计算这个数字是否等于周围一圈雷数
						if (arroundFlag == thiswhat) {
							digg(qx - 1, qy - 1, tempCells, cells,height,width);
							digg(qx - 1, qy, tempCells, cells,height,width);
							digg(qx - 1, qy + 1, tempCells, cells,height,width);
							digg(qx, qy - 1, tempCells, cells,height,width);
							digg(qx, qy + 1, tempCells, cells,height,width);
							digg(qx + 1, qy - 1, tempCells, cells,height,width);
							digg(qx + 1, qy, tempCells, cells,height,width);
							digg(qx + 1, qy + 1, tempCells, cells,height,width);
						}
					}
				}
			}
			if (ract == 1) {
				if (olstatus == 0) {
					r++;
					int qx = (nx) / 16 + 1;
					int qy = (ny) / 16 + 1;
					if (qx <= width && qy <= height) {
						int xx = tempCells[(qy - 1) * width + qx - 1].status;

						if (xx == 0) {
							tempR = 0;
							tempCells[(qy - 1) * width + qx - 1].status = 2;
							tempCells[(qy - 1) * width + qx - 1].sta = "F";
						} else if (xx == 2) {
							tempR = 0;
							tempCells[(qy - 1) * width + qx - 1].status = 0;
							tempCells[(qy - 1) * width + qx - 1].sta = " ";
						} else {
							tempR = 1;

						}
					}
				} else {
					tempR = 0;
				}
			}
			if ((orstatus == 1 ? 1 : 0) * (lact == 1 ? 1 : 0) > 0) {
				holds++;
			}

			if (i > 0 &&saoleiTime>0) {
				path += Math
						.sqrt((nx - ax) * (nx - ax) + (ny - ay) * (ny - ay));
			}

			saoleiTime=rawEventDetailBean.eventTime;
			ax = nx;
			ay = ny;

		}
		for (int i = 0; i < tempCells.length; i++) {
			if ("F".equals(tempCells[i].sta)) {
				flags++;
			}
		}
		EventBean eventBean =new EventBean();
		eventBean.setD(d);
		eventBean.setL(l);
		eventBean.setR(r);
		eventBean.setHolds(holds);
		eventBean.setDistance(path);
		eventBean.setFlags(flags);
		eventBean.setSaoleiTime(saoleiTime);
		return eventBean;
	}

	public static void digg(int x2, int y2, CellsBean[] tempCells, CellsBean[] cells,int height, int width) {
	
		if ((x2 > 0) && (x2 <= width) && (y2 > 0) && (y2 <= height)) {
			if (tempCells[(y2 - 1) * width + x2 - 1].status == 0) {
				if (cells[(y2) * (width + 2) + x2].what != 9) {
					if (cells[(y2) * (width + 2) + (x2)].what != 0) {
						tempCells[(y2 - 1) * width + x2 - 1].status = 3;
						tempCells[(y2 - 1) * width + x2 - 1].sta = String
								.valueOf(cells[(y2) * (width + 2) + (x2)].what);
					} else {
						tempCells[(y2 - 1) * width + x2 - 1].status = 3;
						tempCells[(y2 - 1) * width + x2 - 1].sta = String
								.valueOf(cells[(y2) * (width + 2) + (x2)].what);
						digg(x2 - 1, y2 - 1, tempCells, cells,height,width);
						digg(x2 - 1, y2, tempCells, cells,height,width);
						digg(x2 - 1, y2 + 1, tempCells, cells,height,width);
						digg(x2, y2 - 1, tempCells, cells,height,width);
						digg(x2, y2 + 1, tempCells, cells,height,width);
						digg(x2 + 1, y2 - 1, tempCells, cells,height,width);
						digg(x2 + 1, y2, tempCells, cells,height,width);
						digg(x2 + 1, y2 + 1, tempCells, cells,height,width);
					}
				}
			}
		}
	}
}
