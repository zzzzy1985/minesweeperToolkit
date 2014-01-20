package com.mstoolkit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import com.mstoolkit.common.CommonUtil;
import com.mstoolkit.util.RmvUtil;

/**
 * 解析MVF文件和AVF文件的工具
 * 
 * @author ZhangYe
 * @version 0.7
 */
public class ToolKit {

	private static int x;
	private static int y;
	private static int left;
	private static int right;
	private static int middle;
	private static int height;
	private static int width;
	private static String stamp = "0.000";

	private static JFrame frame = null;
	private static JLabel label = null;
	public static JTable table = null;
	private static JScrollPane scrollPane = null;

	private static int percent = 100;

	private static String curMVFDir = ".";
	private static String curHistoryDir = ".";
	public static String curExportDir = ".";

	private static DefaultTableCellRenderer cellRenderer = null;

	

	private static int closed_cells = 0;
	private static int zini = 0;

	/**
	 * 导出excel文件
	 * 
	 * @param table
	 * @param file
	 * @throws IOException
	 */
	private static void exportTable(JTable table, File file) throws IOException {
		TableModel model = table.getModel();
		FileWriter out = new FileWriter(file);

		for (int i = 0; i < model.getColumnCount(); i++) {
			out.write(model.getColumnName(i) + "\t");
		}
		out.write("\n");
		for (int i = 0; i < model.getRowCount(); i++) {
			for (int j = 0; j < model.getColumnCount(); j++) {
				out.write(model.getValueAt(i, j).toString() + "\t");
			}
			out.write("\n");
		}
		out.close();
	}
	/**
	 * 
	 * @param file
	 * @return
	 */
	private static MVFInfo parseMVF(File file) {
		String name = file.getName();
		String mvfType = "";
		if (file.getName().toLowerCase().endsWith(".mvf")) {
			mvfType = "MVF";
		} else if (file.getName().toLowerCase().endsWith(".avf"))  {
			mvfType = "AVF";
		}else{
			mvfType = "RMV";
		}
		String userID = Const.CALCULATING;
		String date = Const.CALCULATING;
		String level = Const.CALCULATING;
		String style = Const.CALCULATING;
		String mode = Const.CALCULATING;
		String time = Const.CALCULATING;
		String bbbv = Const.CALCULATING;
		String bbbvs = Const.CALCULATING;
		String distance = Const.CALCULATING;
		String clicks = Const.CALCULATING;
		String zini = Const.CALCULATING;
		String islands = Const.CALCULATING;
		String rqp = Const.CALCULATING;
		String ioe = Const.CALCULATING;
		String completion = Const.CALCULATING;
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
		String qg = Const.CALCULATING;
		String flags = Const.CALCULATING;
		String markFlag = Const.CALCULATING;
		String hold = Const.CALCULATING;
		if (file.exists()) {
			byte[] byteStream = readFile(file);
			if (mvfType == "MVF") {
				try {
					
					/**
					 * MVF 97 4a 01 月份 4b 19 25 日期 4c-4d 07DA 2010 年份 4e4f50 时分秒
					 * 51 02level 级别 中级 52 01 classical 模式 02为den模式 53-54 0011
					 * 17 时间 55 30 48 毫秒 56-57 005D 93 3bv 58-59 005D 93 完成3bv
					 * 5a-5b 0012 18 左键 5c-5d 0045 69 双键 5e-5f 0027 39 右键 60 00
					 * MARK标记
					 * before97版本
					 * 
					 */
					if (byteStream[27] == 53) {
						int month = byteStream[0x4a]& 0xFF;
						int day = byteStream[0x4b]& 0xFF;
						int year1 = byteStream[0x4c] & 0xFF;
						int year2 = byteStream[0x4d] & 0xFF;
						int year = year1 * 256 + year2;
						int hour = byteStream[0x4e]& 0xFF;
						int minute = byteStream[0x4f]& 0xFF;
						int second = byteStream[0x50]& 0xFF;
						date = String.format(
								"%02d/%02d/%02d %02d:%02d:%02d",
								new Object[] { Integer.valueOf(year),
										Integer.valueOf(month),
										Integer.valueOf(day),
										Integer.valueOf(hour),
										Integer.valueOf(minute),
										Integer.valueOf(second) });
						switch (byteStream[0x51]) {
						case 1:
							level = Const.LEVEL_BEG;
							break;
						case 2:
							level = Const.LEVEL_INT;
							break;
						case 3:
							level = Const.LEVEL_EXP;
							break;
						default:
							level = Const.LEVEL_CUS;
						}

						switch (byteStream[0x52]) {
						case 1:
							mode = Const.MODE_CLS;
							break;
						case 2:
							mode = Const.MODE_DEN;
							break;
						case 3:
							mode = Const.MODE_UPK;
							break;
						default:
							mode = Const.MODE_CHT;
						}
						// time
						int iTime1 = byteStream[0x53] & 0xFF;
						int iTime2 = byteStream[0x54] & 0xFF;
						int iTime3 = byteStream[0x55] & 0xFF;
						double iTime = iTime1 * 256 + iTime2 + 1 + iTime3
								* 1.0D / 100.0D;
						time = String.format("%.3f",
								new Object[] { Double.valueOf(iTime) });
						// 3bv
						int i3bv1 = byteStream[0x56] & 0xFF;
						int i3bv2 = byteStream[0x57] & 0xFF;
						int i3bv = i3bv1 * 256 + i3bv2;
						bbbv = String.format("%d",
								new Object[] { Integer.valueOf(i3bv) });
						// comp3bv
						int iComp3bv1 = byteStream[0x58] & 0xFF;
						int iComp3bv2 = byteStream[0x59] & 0xFF;
						int iComp3bv = iComp3bv1 * 256 + iComp3bv2;
						completion = String.format(
								"%.1f%%",
								new Object[] { Double.valueOf(iComp3bv * 100.0D
										/ i3bv) });

						bbbvs = String.format(
								"%.3f",
								new Object[] { Double.valueOf(iComp3bv
										/ (iTime - 1.0D)) });

						int l1 = byteStream[90] & 0xFF;
						int l2 = byteStream[91] & 0xFF;
						int l = l1 * 256 + l2;
						int d1 = byteStream[92] & 0xFF;
						int d2 = byteStream[93] & 0xFF;
						int d = d1 * 256 + d2;
						int r1 = byteStream[94] & 0xFF;
						int r2 = byteStream[95] & 0xFF;
						int r = r1 * 256 + r2;
						int click = l + d + r;
						lclicks = String.format("%d",
								new Object[] { Integer.valueOf(l) });
						dclicks = String.format("%d",
								new Object[] { Integer.valueOf(d) });
						rclicks = String.format("%d",
								new Object[] { Integer.valueOf(r) });
						clicks = String.format(
								"%.3f",
								new Object[] { Double.valueOf(click
										/ (iTime - 1.0D)) });
						allClicks = String.format("%d", new Object[] { click });
						style = r == 0 ? Const.STYLE_NF : Const.STYLE_FL;

						rqp = String.format(
								"%.2f",
								new Object[] { Double.valueOf((iTime - 1.0D)
										* iTime / iComp3bv) });
						qg = String.format(
								"%.3f",
								new Object[] { Double.valueOf((Math.pow(
										(iTime - 1.0D), 1.7D)) / iComp3bv) });

						ioe = String.format(
								"%.3f",
								new Object[] { Double.valueOf(iComp3bv * 1.0D
										/ click) });

						width = byteStream[0x61] & 0xFF;
						height = byteStream[0x62] & 0xFF;
						int size = width * height;
						Cell[] mvfboard = new Cell[size];
						int mines1 = byteStream[0x63] & 0xFF;
						int mines2 = byteStream[0x64] & 0xFF;
						int mines = mines1 * 256 + mines2;
						for (int i = 0; i < size; i++) {
							mvfboard[i] = new Cell();
							mvfboard[i].mine = (mvfboard[i].opened = mvfboard[i].flagged = mvfboard[i].opening = mvfboard[i].opening2 = 0);
						}
// 这里的排列为逆排列 比如1 6
						for (int i = 0; i < mines; i++) {
							int posX = byteStream[(101 + i * 2)] & 0xFF;
							int posY = byteStream[(102 + i * 2)] & 0xFF;
							int pos = (posX - 1) * height + posY - 1;
							mvfboard[pos].mine = 1;
						}
						
					
						 
						Cells[] cells = new Cells[(height + 2) * (width + 2)];
						for (int i = 0; i < (height + 2) * (width + 2); i++) {
							cells[i] = new Cells(0);
						}
						for (int i = 0; i < mines; i++) {
							int posX = byteStream[(102 + i * 2)] & 0xFF;
							int posY = byteStream[(101 + i * 2)] & 0xFF;
							// int posX = byteStream[(6 + i * 2)] & 0xFF;
							// int posY = byteStream[(7 + i * 2)] & 0xFF;

							int pos = (posX) * (width + 2) + posY;
							cells[pos].what = 9;
						}
						for (int i = 1; i < (height + 1); i++) {
							for (int j = 1; j < (width + 1); j++) {

								if (cells[(width + 2) * i + j].what != 9) {
									cells[(width + 2) * i + j].what = (cells[(width + 2)
											* (i - 1) + j - 1].what == 9 ? 1
											: 0)
											+ (cells[(width + 2) * (i - 1) + j].what == 9 ? 1
													: 0)
											+ (cells[(width + 2) * (i - 1) + j
													+ 1].what == 9 ? 1 : 0)
											+ (cells[(width + 2) * i + j - 1].what == 9 ? 1
													: 0)
											+ (cells[(width + 2) * i + j + 1].what == 9 ? 1
													: 0)
											+ (cells[(width + 2) * (i + 1) + j
													- 1].what == 9 ? 1 : 0)
											+ (cells[(width + 2) * (i + 1) + j].what == 9 ? 1
													: 0)
											+ (cells[(width + 2) * (i + 1) + j
													+ 1].what == 9 ? 1 : 0);
								}

							}
						}
						
						for (int ii = 1; ii < height + 1; ii++) {
							for (int j = 1; j < width + 1; j++) {
								System.out
										.print(cells[(ii) * (width + 2) + j].what);
							}
							System.out.println("");
						}
						ZiniNum iZini = calcZini(width, height, mines, mvfboard);

						zini = String.valueOf(iZini.zini);
						islands = String.valueOf(iZini.islands);
						num0 = String.valueOf(iZini.num0);
						num1 = String.valueOf(iZini.num1);
						num2 = String.valueOf(iZini.num2);
						num3 = String.valueOf(iZini.num3);
						num4 = String.valueOf(iZini.num4);
						num5 = String.valueOf(iZini.num5);
						num6 = String.valueOf(iZini.num6);
						num7 = String.valueOf(iZini.num7);
						num8 = String.valueOf(iZini.num8);
						// 全部数字
						numAll = String.valueOf(iZini.num1 + 2 * iZini.num2 + 3
								* iZini.num3 + 4 * iZini.num4 + 5 * iZini.num5
								+ 6 * iZini.num6 + 7 * iZini.num7 + 8
								* iZini.num8);

						// HZOE =ZINI/clicks
						hzoe = String.format(
								"%.3f",
								new Object[] { Double.valueOf(iZini.zini * 1.0D
										/ click) });
						// NUM速度=numAll/time
						numSpeed = String
								.format("%.3f",
										new Object[] { Double
												.valueOf(((iZini.num1 + 2
														* iZini.num2 + 3
														* iZini.num3 + 4
														* iZini.num4 + 5
														* iZini.num5 + 6
														* iZini.num6 + 7
														* iZini.num7 + 8 * iZini.num8) * 1.0D))
												/ (iTime - 1.0D) });
						// ZINIS=ZINI/time
						zinis = String.format(
								"%.3f",
								new Object[] { Double
										.valueOf(((iZini.zini) * 1.0D))
										/ (iTime - 1.0D) });
						// OCCAM =3bvs*IOE
						occam = String.format("%.3f", new Object[] { Double
								.valueOf((iComp3bv / (iTime - 1.0D))
										* Double.valueOf(iComp3bv * 1.0D
												/ click)) });
						openings = String.valueOf(iZini.openings);

						int idLen = byteStream[(101 + mines * 2)] & 0xFF;
						userID = new String(byteStream, 101 + mines * 2 + 1,
								idLen);

						int keyIndexSet = 101 + mines * 2 + 1 + idLen;
						int key1 = byteStream[keyIndexSet] & 0xFF;
						int key2 = byteStream[(keyIndexSet + 1)] & 0xFF;
						int key = key1 * 256 + key2;
						int steps1 = byteStream[(keyIndexSet + 2)] & 0xFF;
						int steps2 = byteStream[(keyIndexSet + 3)] & 0xFF;
						int steps3 = byteStream[(keyIndexSet + 4)] & 0xFF;
						int steps = steps1 * 65536 + steps2 * 256 + steps3;

						int operIndexSet = keyIndexSet + 5;

						double fDistance = 0.0D;
						int oldx = 0;
						int oldy = 0;
						int oldleft = 0;
						int oldmiddle = 0;
						int oldright = 0;
						int[] actions = new int[5];
						int flagCount = 0;
						// MVFInfo info = new MVFInfo(height, width, cells);
						// Board board = new Board(info);
						List<ActionCheck> actionCheckList = new ArrayList<ActionCheck>();
						for (int i = 0; i < steps; i++) {
							for (int j = 0; j < 5; j++) {
								actions[j] = (byteStream[(operIndexSet + i * 5 + j)] & 0xFF);
							}
							descriptografa097(key, actions);
							// Action action = new Action(stamp, x, y, left,
							// middle, right);
							// 判定lc lr rc rr mc mr mv
							// 如果当前left=1 oldleft =0 则判定lc 1次
							if (oldx != x || oldy != y) {
								ActionCheck actionCheck = new ActionCheck(
										stamp, "mv", x, y);
								actionCheckList.add(actionCheck);
							}
							if (left == 1 && oldleft == 0) {
								ActionCheck actionCheck = new ActionCheck(
										stamp, "lc", x, y);
								actionCheckList.add(actionCheck);
							}
							if (right == 1 && oldright == 0) {
								ActionCheck actionCheck = new ActionCheck(
										stamp, "rc", x, y);
								actionCheckList.add(actionCheck);
							}
							if (middle == 1 && oldmiddle == 0) {
								ActionCheck actionCheck = new ActionCheck(
										stamp, "mc", x, y);
								actionCheckList.add(actionCheck);
							}
							if (left == 0 && oldleft == 1) {
								ActionCheck actionCheck = new ActionCheck(
										stamp, "lr", x, y);
								actionCheckList.add(actionCheck);
							}
							if (right == 0 && oldright == 1) {
								ActionCheck actionCheck = new ActionCheck(
										stamp, "rr", x, y);
								actionCheckList.add(actionCheck);
							}
							if (middle == 0 && oldmiddle == 1) {
								ActionCheck actionCheck = new ActionCheck(
										stamp, "mr", x, y);
								actionCheckList.add(actionCheck);
							}

							if (Double.parseDouble(stamp) > 0.01) {
								fDistance = fDistance
										+ Math.sqrt((x - oldx) * (x - oldx)
												+ (y - oldy) * (y - oldy));

							}
							oldx = x;
							oldy = y;
							oldleft = left;
							oldmiddle = middle;
							oldright = right;
							oldy = y;
						}
						int acLength = actionCheckList.size();
						String mouseTypeNomv = "";
						int lstatus = 0;
						int rstatus = 0;
						@SuppressWarnings("unused")
						int ll = 0;
						@SuppressWarnings("unused")
						int dd = 0;
						@SuppressWarnings("unused")
						int rr = 0;
						// 计算1.5click
						@SuppressWarnings("unused")
						int holds = 0;

						Cells[] tempCells = new Cells[(height) * (width)];
						for (int i = 0; i < tempCells.length; i++) {
							tempCells[i] = new Cells(0);
						}
						int tempR = 0;
						if (acLength > 0) {
							for (int i = 1; i < acLength; i++) {
								int olstatus = 0;
								int orstatus = 0;
								boolean flag = true;
								if ("rr".equals(mouseTypeNomv)) {
									flag = false;
								}
								int lact = 0;
								int ract = 0;

								ActionCheck actionCheck = actionCheckList
										.get(i);
								if (!"mv".equals(actionCheck.check)) {
									mouseTypeNomv = actionCheck.check;
								}
								switch (actionCheck.check) {
								case "mv":
									lact = 0;
									ract = 0;
									break;
								case "lc":
									lact = 1;
									ract = 0;
									break;
								case "lr":
									lact = -1;
									ract = 0;
									break;
								case "rc":
									lact = 0;
									ract = 1;
									break;
								case "rr":
									lact = 0;
									ract = -1;
									break;
								case "mc":
									lact = 1;
									ract = 1;
									break;
								case "mr":
									lact = -1;
									ract = -1;
									break;
								default:
									lact = 0;
									ract = 0;
								}
								lstatus += lact;
								rstatus += ract;
								olstatus = lstatus - lact;
								orstatus = rstatus - ract;
								if (lact == -1 && orstatus == 0 && flag) {
									ll++;
									// l++的时候
									int qx = (actionCheck.y) / 16 + 1;
									int qy = (actionCheck.x) / 16 + 1;
									int xx = tempCells[(qy - 1) * width + qx
											- 1].status;
									if (xx == 0) {
										digg(qx, qy, tempCells, cells);
									}
								}

								if ((lact == -1 ? 1 : 0 + ract == -1 ? 1 : 0)
										* (olstatus == 1 ? 1 : 0)
										* (orstatus == 1 ? 1 : 0) > 0) {
									dd++;
									if (tempR == 1) {
										rr--;
										tempR = 0;
									}
									int qx = (actionCheck.y) / 16 + 1;
									int qy = (actionCheck.x) / 16 + 1;
									// D操作的条件应该是当前值= 周围一圈的标旗数
									if (qx <= height && qy <= width) {
										if (cells[(qy) * (width + 2) + (qx)].what != 0) {

											int thiswhat = cells[(qy)
													* (width + 2) + (qx)].what;
											// 计算点击位置周围一圈雷数
											if (thiswhat != 0) {
												int arroundFlag = 0;
												// 存在左上格 如果不在第一行或第一列
												if (!((qx == 1) || (qy == 1))) {
													arroundFlag += (("F"
															.equals(tempCells[(qy - 2)
																	* width
																	+ qx - 2].sta)) ? 1
															: 0);
												}
												// 存在上格 如果不在第一行
												if (qy != 1) {
													arroundFlag += (("F"
															.equals(tempCells[(qy - 2)
																	* width
																	+ qx - 1].sta)) ? 1
															: 0);
												}
												// 存在右上格 如果不在第一行或最后一列
												if (!((qx == width) || (qy == 1))) {
													arroundFlag += (("F"
															.equals(tempCells[(qy - 2)
																	* width
																	+ qx].sta)) ? 1
															: 0);
												}
												// 存在左格 如果不在第一列
												if (qx != 1) {
													arroundFlag += (("F"
															.equals(tempCells[(qy - 1)
																	* width
																	+ qx - 2].sta)) ? 1
															: 0);
												}
												// 存在右格 如果不在最后一列
												if (qx != width) {
													arroundFlag += (("F"
															.equals(tempCells[(qy - 1)
																	* width
																	+ qx].sta)) ? 1
															: 0);
												}
												// 存在左下格 如果不在最后一行或最后一列

												if (!((qx == 1) || (qy == height))) {
													arroundFlag += (("F"
															.equals(tempCells[(qy)
																	* width
																	+ qx - 2].sta)) ? 1
															: 0);
												}
												// 存在下格 如果不在最后一行
												if (qy != height) {
													arroundFlag += (("F"
															.equals(tempCells[(qy)
																	* width
																	+ qx - 1].sta)) ? 1
															: 0);
												}
												// 存在右下格 如果不在最后一行或最后一列
												if (!((qx == width) || (qy == height))) {
													arroundFlag += (("F"
															.equals(tempCells[(qy)
																	* width
																	+ qx].sta)) ? 1
															: 0);
												}
												// 计算这个数字是否等于周围一圈雷数
												if (arroundFlag == thiswhat) {
													digg(qx - 1, qy - 1,
															tempCells, cells);
													digg(qx - 1, qy, tempCells,
															cells);
													digg(qx - 1, qy + 1,
															tempCells, cells);
													digg(qx, qy - 1, tempCells,
															cells);
													digg(qx, qy + 1, tempCells,
															cells);
													digg(qx + 1, qy - 1,
															tempCells, cells);
													digg(qx + 1, qy, tempCells,
															cells);
													digg(qx + 1, qy + 1,
															tempCells, cells);
												}
											}
										}
									}
								}
								if (ract == 1) {
									if (olstatus == 0) {
										rr++;
										int qx = (actionCheck.y) / 16 + 1;
										int qy = (actionCheck.x) / 16 + 1;
										int xx = tempCells[(qy - 1) * width
												+ qx - 1].status;

										if (xx == 0) {
											tempR = 0;
											tempCells[(qy - 1) * width + qx - 1].status = 2;
											tempCells[(qy - 1) * width + qx - 1].sta = "F";
										} else if (xx == 2) {
											tempR = 0;
											tempCells[(qy - 1) * width + qx - 1].status = 0;
											tempCells[(qy - 1) * width + qx - 1].sta = "_";
										} else {
											tempR = 1;

										}
								
									} else {
										tempR = 0;
									}
								}
								if ((orstatus == 1 ? 1 : 0)
										* (lact == 1 ? 1 : 0) > 0) {
									holds++;
								}
							}
						
						}
						for (int i = 0; i < tempCells.length; i++) {
							if ("F".equals(tempCells[i].sta)) {
								flagCount++;
							}
						}
						distance = String.format("%.3f",
								new Object[] { Double.valueOf(fDistance) });

						// 移动速度 =distance/time
						disSpeed = String.format(
								"%.3f",
								new Object[] { Double.valueOf(fDistance
										/ (iTime - 1.0D)) });
						// 每BV移动 =distance/3bv
						disBv = String.format(
								"%.3f",
								new Object[] { Double.valueOf(fDistance
										/ iComp3bv) });
						// 每NUM移动 =distance/numAll
						disNum = String
								.format("%.3f",
										new Object[] { Double
												.valueOf(fDistance
														/ ((iZini.num1 + 2
																* iZini.num2
																+ 3
																* iZini.num3
																+ 4
																* iZini.num4
																+ 5
																* iZini.num5
																+ 6
																* iZini.num6
																+ 7
																* iZini.num7 + 8 * iZini.num8) * 1.0D)) });
						flags = String.valueOf(flagCount);
						return new MVFInfo(name, mvfType, userID, date, level,
								style, mode, time, bbbv, bbbvs, distance,
								clicks, zini, rqp, ioe, completion, num0, num1,
								num2, num3, num4, num5, num6, num7, num8,
								numAll, disSpeed, allClicks, disBv, disNum,
								hzoe, numSpeed, zinis, occam, openings,
								lclicks, dclicks, rclicks, qg, flags, markFlag,
								hold,islands);
					}

					userID = Const.INVALID;
					date = Const.INVALID;
					level = Const.INVALID;
					style = Const.INVALID;
					mode = Const.INVALID;
					time = Const.INVALID;
					bbbv = Const.INVALID;
					bbbvs = Const.INVALID;
					distance = Const.INVALID;
					clicks = Const.INVALID;
					zini = Const.INVALID;
					rqp = Const.INVALID;
					ioe = Const.INVALID;
					completion = Const.INVALID;
					num0 = Const.INVALID;
					num1 = Const.INVALID;
					num2 = Const.INVALID;
					num3 = Const.INVALID;
					num4 = Const.INVALID;
					num5 = Const.INVALID;
					num6 = Const.INVALID;
					num7 = Const.INVALID;
					num8 = Const.INVALID;
					numAll = Const.INVALID;
					disSpeed = Const.INVALID;
					openings = Const.INVALID;
					allClicks = Const.INVALID;
					disBv = Const.INVALID;
					disNum = Const.INVALID;
					hzoe = Const.INVALID;
					numSpeed = Const.INVALID;
					zinis = Const.INVALID;
					occam = Const.INVALID;
					lclicks = Const.INVALID;
					dclicks = Const.INVALID;
					rclicks = Const.INVALID;
					qg = Const.INVALID;
					flags = Const.INVALID;
					islands= Const.INVALID;
				} catch (Exception e) {
					userID = Const.INVALID;
					date = Const.INVALID;
					level = Const.INVALID;
					style = Const.INVALID;
					mode = Const.INVALID;
					time = Const.INVALID;
					bbbv = Const.INVALID;
					bbbvs = Const.INVALID;
					distance = Const.INVALID;
					clicks = Const.INVALID;
					zini = Const.INVALID;
					rqp = Const.INVALID;
					ioe = Const.INVALID;
					completion = Const.INVALID;
					num0 = Const.INVALID;
					num1 = Const.INVALID;
					num2 = Const.INVALID;
					num3 = Const.INVALID;
					num4 = Const.INVALID;
					num5 = Const.INVALID;
					num6 = Const.INVALID;
					num7 = Const.INVALID;
					num8 = Const.INVALID;
					numAll = Const.INVALID;
					disSpeed = Const.INVALID;
					openings = Const.INVALID;
					allClicks = Const.INVALID;
					disBv = Const.INVALID;
					disNum = Const.INVALID;
					hzoe = Const.INVALID;
					numSpeed = Const.INVALID;
					zinis = Const.INVALID;
					occam = Const.INVALID;
					lclicks = Const.INVALID;
					dclicks = Const.INVALID;
					rclicks = Const.INVALID;
					qg = Const.INVALID;
					flags = Const.INVALID;
					markFlag = Const.INVALID;
					islands= Const.INVALID;
				}
			} else if (mvfType == "AVF") {
				/**
				 * AVF 49 00 31 -49 版本 01-04 随机字符 05 03 初级 04 中级 05 高级 06自定义
				 * 自定义情况下 接下来是 w h m1 m2 m=m1*256+m2 非06情况下 下面接雷的位置 例如 07 01 前面是
				 * 行 后面是 列 遇到 [之前的倒2位 7F( 127) 表示为非mark标志 如果为11 (17)表示为mark标志
				 * 然后有20-30个位置不明 直到遇到 [为止 ASCII码 58 取这个[的前一位 比如35 然后从这个开始取
				 * 这么长的位置 结尾是] 然后再取1位
				 * 
				 * 32 int day = byteStream[0x4a];日期 33 2e . 分隔符 34 int month =
				 * byteStream[0x4a];月份 35 2e . 分隔符 36-39 年份 3a 2e . 分隔符
				 */
				int next = 0;
				int mines = 0;
				int tt = 0;

				switch (byteStream[0x05]) {
				case 3:
					level = Const.LEVEL_BEG;
					tt = 0x30;
					height = 8;
					width = 8;
					mines = 10;
					next = 5 + 20 + 10;
					break;
				case 4:
					tt = 0x31;
					level = Const.LEVEL_INT;
					height = 16;
					width = 16;
					mines = 40;
					next = 5 + 80 + 10;
					break;
				case 5:
					tt = 0x32;
					level = Const.LEVEL_EXP;
					height = 16;
					width = 30;
					mines = 99;
					next = 198;
					break;
				default:
					level = Const.LEVEL_CUS;

				}
				// 计算H.zini

				int avfSize = width * height;
				Cell[] mvfboard = new Cell[avfSize];

				for (int i = 0; i < avfSize; i++) {
					mvfboard[i] = new Cell();
					mvfboard[i].mine = (mvfboard[i].opened = mvfboard[i].flagged = mvfboard[i].opening = mvfboard[i].opening2 = 0);
				}
// 列 比如1 6
				for (int i = 0; i < mines; i++) {
					// posX 表示行1
					// posY 表示列6
					int posX = byteStream[(7 + i * 2)] & 0xFF;
					int posY = byteStream[(6+ i * 2)] & 0xFF;
					int pos = (posX - 1) * height + posY - 1;
					mvfboard[pos].mine = 1;
				}
				
				Cells[] cells = new Cells[(height + 2) * (width + 2)];
				for (int i = 0; i < (height + 2) * (width + 2); i++) {
					cells[i] = new Cells(0);
				}
				for (int i = 0; i < mines; i++) {
					int posX = byteStream[(6 + i * 2)] & 0xFF;
					int posY = byteStream[(7 + i * 2)] & 0xFF;
				
					int pos = (posX) * (width + 2) + posY;
					cells[pos].what = 9;
				}
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
				ZiniNum iZini = calcZini(width, height, mines, mvfboard);
				zini = String.valueOf(iZini.zini);
				islands= String.valueOf(iZini.islands);
				num0 = String.valueOf(iZini.num0);
				num1 = String.valueOf(iZini.num1);
				num2 = String.valueOf(iZini.num2);
				num3 = String.valueOf(iZini.num3);
				num4 = String.valueOf(iZini.num4);
				num5 = String.valueOf(iZini.num5);
				num6 = String.valueOf(iZini.num6);
				num7 = String.valueOf(iZini.num7);
				num8 = String.valueOf(iZini.num8);
				openings = String.valueOf(iZini.openings);
				// 全部数字
				numAll = String.valueOf(iZini.num1 + 2 * iZini.num2 + 3
						* iZini.num3 + 4 * iZini.num4 + 5 * iZini.num5 + 6
						* iZini.num6 + 7 * iZini.num7 + 8 * iZini.num8);

				// 如果不是"[" ASCII码0x5b,寻找"["
				// [0, 4.6.2013.15:50:38:6620, 4.15:50:46:4441, HS, B15T8.81]
				while (byteStream[next] != 0x5b) {
					next++;
				}

				if (byteStream[next + 1] != tt) {
					next = next + 1;
					while (byteStream[next] != 0x5b) {
						next++;
					}
				}
				int length = byteStream[next - 1] & 0xFF;
				int mark = byteStream[next - 2] & 0xFF;
				if (mark == 0x7F) {
					markFlag = "UNMARK";
				} else {
					markFlag = "MARK";
				}
				String many = new String(byteStream, next + 1, length - 2);
				String yearS = "";
				String monthS = "";
				String dayS = "";
				String timeS = "";
				String hourS = "";
				String minS = "";
				String secS = "";
				String bvtime = "";
				String saoleitime = "";
				String bv = "";
				/**
				 * many 的格式
				 */
				if (many.contains("HS")) {
					/**
					 * 20131031 对应科长录像解析bug问题
					 */
					String[] ms = many.split("\\|");
					String splitdate = ms[1];
					// String splitdetail = ms[2];
					// String[] splitdetailas = splitdetail.split("B");
					String[] ymd = splitdate.split("\\.");
					if( ymd[0].length()==4){
						yearS = ymd[1];
						monthS = ymd[0].substring(2);
						dayS = ymd[0].substring(0,2);
						timeS = ymd[2];
					}else if(ymd[0].length()==3){
						yearS = ymd[1];
						monthS = ymd[0].substring(1);
						dayS = ymd[0].substring(0,1);
						timeS = ymd[2];
					}else{
					yearS = ymd[2];
					monthS = ymd[1];
					dayS = ymd[0];
					
					timeS = ymd[3];
					}
					String[] hms = timeS.split("\\:");
					hourS = hms[0];
					minS = hms[1];
					secS = hms[2];
					bvtime = ms[4];
					String[] bvtimeS = bvtime.split("T");
					bv = bvtimeS[0].substring(1);
					saoleitime = bvtimeS[1];
					
				} else {
					String[] ms = many.split("\\|");
					if (ms.length == 3) {
						String splitdate = ms[1];
						String splitdetail = ms[2];
						String[] splitdetailas = splitdetail.split("B");
						String[] ymd = splitdate.split("\\.");
						if (ymd[0].length() == 4) {
							yearS = ymd[1];
							monthS = ymd[0].substring(2);
							dayS = ymd[0].substring(0, 2);
							timeS = ymd[2];
						} else if (ymd[0].length() == 3) {
							yearS = ymd[1];
							monthS = ymd[0].substring(1);
							dayS = ymd[0].substring(0, 1);
							timeS = ymd[2];
						} else {
							yearS = ymd[2];
							monthS = ymd[1];
							dayS = ymd[0];
							timeS = ymd[3];
						}
						String[] hms = timeS.split("\\:");
						hourS = hms[0];
						minS = hms[1];
						secS = hms[2];
						// String minsecS=hms[3];
						bvtime = splitdetailas[1];
						String[] bvtimeS = bvtime.split("T");
						bv = bvtimeS[0];
						saoleitime = bvtimeS[1];
						// avf 0.52的场合
						}else if(ms.length == 4){
							String splitdate = ms[1];
							String splitdetail = ms[3];
							String[] splitdetailas = splitdetail.split("B");
							String[] ymd = splitdate.split("\\.");
							if (ymd[0].length() == 4) {
								yearS = ymd[1];
								monthS = ymd[0].substring(2);
								dayS = ymd[0].substring(0, 2);
								timeS = ymd[2];
							} else if (ymd[0].length() == 3) {
								yearS = ymd[1];
								monthS = ymd[0].substring(1);
								dayS = ymd[0].substring(0, 1);
								timeS = ymd[2];
							} else {
								yearS = ymd[2];
								monthS = ymd[1];
								dayS = ymd[0];
								timeS = ymd[3];
							}
							String[] hms = timeS.split("\\:");
							hourS = hms[0];
							minS = hms[1];
							secS = hms[2];
							// String minsecS=hms[3];
							bvtime = splitdetailas[1];
							String[] bvtimeS = bvtime.split("T");
							bv = bvtimeS[0];
							saoleitime = bvtimeS[1];
							// avf 0.52的场合
						}
				
				
				}
				int nowLength = next + length;
				int[] cr = new int[8];
				while (cr[2] != 1 || cr[1] > 1) // cr[2]=time=1;
												// cr[1]=position_x div
												// 256<=30*16 div 256 = 1
				{
					cr[0] = cr[1];
					cr[1] = cr[2];
					cr[2] = byteStream[nowLength];
					nowLength++;
				}
				for (int i = 3; i < 8; ++i) {
					cr[i] = byteStream[nowLength];
					nowLength++;
				}
				List<AvfVideo> avfVideoList = new ArrayList<AvfVideo>();

				while (true) {
					int sec = (int) cr[6] * 256 + cr[2] - 1;
					int x1 = 0;
					if (cr[3] == 256) {
						x1 = 0;
					} else if (cr[3] > 0) {
						x1 = cr[3];
					} else {
						x1 = cr[3] + 256;
					}
					if (x1 == 256) {
						x1 = 0;
					}
					int x2 = 0;
					if (cr[7] == 256) {
						x2 = 0;
					} else if (cr[7] > 0) {
						x2 = cr[7];
					} else {
						x2 = cr[7] + 256;
					}
					if (x2 == 256) {
						x2 = 0;
					}
					int x = cr[1] * 256 + x1;
					int y = cr[5] * 256 + x2;
					// int sec, int hun, int x, int y, int mouse
					AvfVideo avfVideo = new AvfVideo(sec, cr[4], x, y, cr[0],
							cr[1], x1, cr[5], x2);

					avfVideoList.add(avfVideo);
					if (sec < 0)
						break;

					for (int i = 0; i < 8; ++i) {
						cr[i] = byteStream[nowLength];
						nowLength++;
					}
				}
				int size = avfVideoList.size();
				for (int i = 0; i < 3; ++i)
					cr[i] = 0;
				while (cr[0] != 'c' || cr[1] != 's' || cr[2] != '=') {
					cr[0] = cr[1];
					cr[1] = cr[2];
					cr[2] = byteStream[nowLength];
					nowLength++;
				}
				boolean fs = false;
				if (fs) {
					for (int i = 0; i < size; ++i) {
						avfVideoList.get(i).ths = byteStream[nowLength] & 0xF;
						nowLength++;
					}
					for (int i = 0; i < 17; ++i) {
						nowLength++;
					}
					while (byteStream[nowLength] != 13) {
						nowLength++;
					}
				} else {
					for (int i = 0; i < 17; ++i) {
						nowLength++;
					}
				}
				// skin[0]=0;
				int nt = nowLength;
				next = 0;
				for (int i = 0; i < 2; i++) {

					while (byteStream[nt + next] != 13) {
						next++;
					}
					nt = nt + next + 1;
					next = 0;
				}
				while (byteStream[nt + next] != 13) {
					next++;
				}
				userID = new String(byteStream, nt, next);
				nt = nt + next + 21;
				next = 6;

				String version = new String(byteStream, nt, next);
				mvfType += version;
				int ax = 0;
				int ay = 0;

				int lstatus = 0;
				int rstatus = 0;
				int l = 0;
				int d = 0;
				int r = 0;
				// 计算1.5click
				int holds = 0;
				double path = 0.0d;
				String mouseTypeNomv = "";
				Cells[] tempCells = new Cells[(height) * (width)];
				for (int i = 0; i < tempCells.length; i++) {
					tempCells[i] = new Cells(0);
				}
				int tempR = 0;
				// 计算click 和 path
				for (int i = 0; i < avfVideoList.size() - 1; i++) {
					// 为了计算准确的右键数 需要模拟录像操作
					int mouse = avfVideoList.get(i).mouse;
					int nx = 0;
					int ny = 0;
					int olstatus = 0;
					int orstatus = 0;
					nx = avfVideoList.get(i).x1 * 256 + avfVideoList.get(i).x2;
					ny = avfVideoList.get(i).y1 * 256 + avfVideoList.get(i).y2;
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
								digg(qx, qy, tempCells, cells);
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
									arroundFlag += (("F"
											.equals(tempCells[(qy - 2) * width
													+ qx - 2].sta)) ? 1 : 0);
								}
								// 存在上格 如果不在第一行
								if (qy != 1) {
									arroundFlag += (("F"
											.equals(tempCells[(qy - 2) * width
													+ qx - 1].sta)) ? 1 : 0);
								}
								// 存在右上格 如果不在第一行或最后一列
								if (!((qx == width) || (qy == 1))) {
									arroundFlag += (("F"
											.equals(tempCells[(qy - 2) * width
													+ qx].sta)) ? 1 : 0);
								}
								// 存在左格 如果不在第一列
								if (qx != 1) {
									arroundFlag += (("F"
											.equals(tempCells[(qy - 1) * width
													+ qx - 2].sta)) ? 1 : 0);
								}
								// 存在右格 如果不在最后一列
								if (qx != width) {
									arroundFlag += (("F"
											.equals(tempCells[(qy - 1) * width
													+ qx].sta)) ? 1 : 0);
								}
								// 存在左下格 如果不在最后一行或最后一列

								if (!((qx == 1) || (qy == height))) {
									arroundFlag += (("F".equals(tempCells[(qy)
											* width + qx - 2].sta)) ? 1 : 0);
								}
								// 存在下格 如果不在最后一行
								if (qy != height) {
									arroundFlag += (("F".equals(tempCells[(qy)
											* width + qx - 1].sta)) ? 1 : 0);
								}
								// 存在右下格 如果不在最后一行或最后一列
								if (!((qx == width) || (qy == height))) {
									arroundFlag += (("F".equals(tempCells[(qy)
											* width + qx].sta)) ? 1 : 0);
								}

								System.out.println(qx + " " + qy + " "
										+ arroundFlag + " " + thiswhat);

								// 计算这个数字是否等于周围一圈雷数
								if (arroundFlag == thiswhat) {
									digg(qx - 1, qy - 1, tempCells, cells);
									digg(qx - 1, qy, tempCells, cells);
									digg(qx - 1, qy + 1, tempCells, cells);
									digg(qx, qy - 1, tempCells, cells);
									digg(qx, qy + 1, tempCells, cells);
									digg(qx + 1, qy - 1, tempCells, cells);
									digg(qx + 1, qy, tempCells, cells);
									digg(qx + 1, qy + 1, tempCells, cells);
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

					if (i > 0) {
						path += Math.sqrt((nx - ax) * (nx - ax) + (ny - ay)
								* (ny - ay));
					}
					ax = nx;
					ay = ny;

				}
				/**
				 * for (int ii = 0; ii < height; ii++) { for (int j = 0; j <
				 * width; j++) { System.out.print(tempCells[(ii) * width +
				 * j].sta); } System.out.println(""); }
				 **/
				int flagCount = 0;
				for (int i = 0; i < tempCells.length; i++) {
					if ("F".equals(tempCells[i].sta)) {
						flagCount++;
					}
				}
				bbbvs = String.format("%.3f", new Object[] { Double.valueOf(bv)
						/ (Double.valueOf(saoleitime) - 1.0D) });
				int allClick = l + d + r;
				lclicks = String.format("%d",
						new Object[] { Integer.valueOf(l) });
				dclicks = String.format("%d",
						new Object[] { Integer.valueOf(d) });
				rclicks = String.format("%d",
						new Object[] { Integer.valueOf(r) });
				allClicks = String.format("%d",
						new Object[] { Integer.valueOf(allClick) });
				clicks = String.format(
						"%.3f",
						new Object[] { Double.valueOf(allClicks)
								/ (Double.valueOf(saoleitime) - 1.0D) });
				distance = String.format("%.3f",
						new Object[] { Double.valueOf(path) });
				date = String.format(
						"%02d/%02d/%02d %02d:%02d:%02d",
						new Object[] { Integer.valueOf(yearS),
								Integer.valueOf(monthS), Integer.valueOf(dayS),
								Integer.valueOf(hourS), Integer.valueOf(minS),
								Integer.valueOf(secS),

						});
				rqp = String.format(
						"%.2f",
						new Object[] { (Double.valueOf(saoleitime) - 1.0D)
								* (Double.valueOf(saoleitime))
								/ Double.valueOf(bv) });
				qg = String.format(
						"%.3f",
						new Object[] { Double.valueOf((Math.pow(
								(Double.valueOf(saoleitime) - 1.0D), 1.7D))
								/ Double.valueOf(bv)) });

				ioe = String.format(
						"%.3f",
						new Object[] { Double.valueOf(Double.valueOf(bv) * 1.0D
								/ allClick) });
				// HZOE =ZINI/clicks
				hzoe = String.format(
						"%.3f",
						new Object[] { Double.valueOf(iZini.zini * 1.0D
								/ allClick) });
				// NUM速度=numAll/time
				numSpeed = String.format(
						"%.3f",
						new Object[] { Double.valueOf(((iZini.num1 + 2
								* iZini.num2 + 3 * iZini.num3 + 4 * iZini.num4
								+ 5 * iZini.num5 + 6 * iZini.num6 + 7
								* iZini.num7 + 8 * iZini.num8) * 1.0D))
								/ (Double.valueOf(saoleitime) - 1.0D) });
				// ZINIS=ZINI/time
				zinis = String.format("%.3f",
						new Object[] { Double.valueOf(((iZini.zini) * 1.0D))
								/ (Double.valueOf(saoleitime) - 1.0D) });
				// OCCAM =3bvs*IOE
				occam = String
						.format("%.3f",
								new Object[] { (Double.valueOf((Double
										.valueOf(bv) / (Double
										.valueOf(saoleitime) - 1.0D))))
										* (Double.valueOf((Double.valueOf(bv) * 1.0D / allClick))) });
				// 移动速度 =distance/time
				disSpeed = String.format(
						"%.3f",
						new Object[] { Double.valueOf(path
								/ (Double.valueOf(saoleitime) - 1.0D)) });
				// 每BV移动 =distance/3bv
				disBv = String.format("%.3f", new Object[] { Double
						.valueOf(path / Double.valueOf(bv)) });
				// 每NUM移动 =distance/numAll
				disNum = String
						.format("%.3f",
								new Object[] { Double
										.valueOf(path
												/ ((iZini.num1 + 2 * iZini.num2
														+ 3 * iZini.num3 + 4
														* iZini.num4 + 5
														* iZini.num5 + 6
														* iZini.num6 + 7
														* iZini.num7 + 8 * iZini.num8) * 1.0D)) });
				flags = String.valueOf(flagCount);
				style = r == 0 ? Const.STYLE_NF : Const.STYLE_FL;
				// 循环坐标 取得arbiter的数组
				mode = "经典";
				hold = String.format("%d",
						new Object[] { Integer.valueOf(holds) });

				time = saoleitime;
				bbbv = bv;
				completion = "100.0%";
				return new MVFInfo(name, mvfType, userID, date, level, style,
						mode, time, bbbv, bbbvs, distance, clicks, zini, rqp,
						ioe, completion, num0, num1, num2, num3, num4, num5,
						num6, num7, num8, numAll, disSpeed, allClicks, disBv,
						disNum, hzoe, numSpeed, zinis, occam, openings,
						lclicks, dclicks, rclicks, qg, flags, markFlag, hold,islands);
			} else if (mvfType == "RMV") {
				/**
				 * RMV
				 * 
				 * 
				 */
				RmvUtil rmvUtil =new RmvUtil();
				return rmvUtil.analyzingVideo(byteStream, name);
			}
		} else {
			userID = Const.FILENOFOUND;
			date = Const.FILENOFOUND;
			level = Const.FILENOFOUND;
			mode = Const.FILENOFOUND;
			style = Const.FILENOFOUND;
			time = Const.FILENOFOUND;
			bbbv = Const.FILENOFOUND;
			bbbvs = Const.FILENOFOUND;
			distance = Const.FILENOFOUND;
			clicks = Const.FILENOFOUND;
			zini = Const.FILENOFOUND;
			rqp = Const.FILENOFOUND;
			ioe = Const.FILENOFOUND;
			completion = Const.FILENOFOUND;
			num0 = Const.FILENOFOUND;
			num1 = Const.FILENOFOUND;
			num2 = Const.FILENOFOUND;
			num3 = Const.FILENOFOUND;
			num4 = Const.FILENOFOUND;
			num5 = Const.FILENOFOUND;
			num6 = Const.FILENOFOUND;
			num7 = Const.FILENOFOUND;
			num8 = Const.FILENOFOUND;
			numAll = Const.FILENOFOUND;
			disSpeed = Const.FILENOFOUND;
			openings = Const.FILENOFOUND;
			allClicks = Const.FILENOFOUND;
			disBv = Const.FILENOFOUND;
			disNum = Const.FILENOFOUND;
			hzoe = Const.FILENOFOUND;
			numSpeed = Const.FILENOFOUND;
			zinis = Const.FILENOFOUND;
			occam = Const.FILENOFOUND;
			lclicks = Const.FILENOFOUND;
			dclicks = Const.FILENOFOUND;
			rclicks = Const.FILENOFOUND;
			qg = Const.FILENOFOUND;
			flags = Const.FILENOFOUND;
			markFlag = Const.FILENOFOUND;
			hold = Const.FILENOFOUND;
			islands = Const.FILENOFOUND;
		}

		return new MVFInfo(name, mvfType, userID, date, level, style, mode,
				time, bbbv, bbbvs, distance, clicks, zini, rqp, ioe,
				completion, num0, num1, num2, num3, num4, num5, num6, num7,
				num8, numAll, disSpeed, allClicks, disBv, disNum, hzoe,
				numSpeed, zinis, occam, openings, lclicks, dclicks, rclicks,
				qg, flags, markFlag, hold,islands);
	}

	public static void digg(int x2, int y2, Cells[] tempCells, Cells[] cells) {
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
						digg(x2 - 1, y2 - 1, tempCells, cells);
						digg(x2 - 1, y2, tempCells, cells);
						digg(x2 - 1, y2 + 1, tempCells, cells);
						digg(x2, y2 - 1, tempCells, cells);
						digg(x2, y2 + 1, tempCells, cells);
						digg(x2 + 1, y2 - 1, tempCells, cells);
						digg(x2 + 1, y2, tempCells, cells);
						digg(x2 + 1, y2 + 1, tempCells, cells);
					}
				}
			}
		}
	}

	private static int criaByte(int inicio, int L, int[] chunk) {
		int ret = 0;
		int i = 0;
		for (i = 0; i < L; i++) {
			ret += Const.power2[i] * (short) (chunk[(inicio + i)] & 0xFFFF);
		}
		return ret;
	}

	private static void binario(int action, int n, int[] flags) {
		int what = action;
		int index = n;
		while (--index >= 0) {
			flags[index + 1] = (what / Const.power2[index]) > 0 ? 1 : 0;
			what = what % Const.power2[index];
		}

	}

	private static void embaralha097(int Key, int[] Ordem) {
		double expression = Math
				.cos(Math.sqrt(Math.sqrt(Key) + 1000.0D) + 1000.0D);
		double num5 = Math.sin(Math.sqrt(Math.sqrt(Key + 1000)));
		double num6 = Math.cos(Math.sqrt(Math.sqrt(Key) + 1000.0D));
		double num7 = Math.sin(Math.sqrt(Math.sqrt(Key)) + 1000.0D);
		double num8 = Math.cos(Math.sqrt(Math.sqrt(Key + 1000) + 1000.0D));

		int num3 = 1;
		int num = 0;

		int flag = 0;
		int start = 0;

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
		while (num <= 9) {
			flag = 0;
			start = 0;
			while (flag != 1) {
				while (!(flag == 1 || (x000.charAt(start) - 0x30) == num)) {
					start++;
					if (start >= x000.length()) {
						flag = 1;
					}
				}
				if (flag != 1) {
					Ordem[start + 1] = num3;
					num3++;
					start++;
					if (start >= x000.length()) {
						flag = 1;
					}
				}
			}
			num++;
		}

	}

	private static void descriptografa097(int key, int[] actions) {
		int[] flags = new int[41];
		int[] chunk = new int[41];
		int[] ordem = new int[41];
		int[] tmpFlag = new int[9];
		int i = 0;
		int j = 0;
		for (i = 0; i < 5; i++) {
			binario(actions[i], 8, tmpFlag);
			for (j = 1; j < 9; j++) {
				flags[((i << 3) + j)] = tmpFlag[j];
			}
		}
		embaralha097(key, ordem);

		for (i = 1; i <= 40; i++) {
			chunk[ordem[i]] = flags[i];
		}

		x = criaByte(4, 9, chunk);
		y = criaByte(13, 9, chunk);

		left = chunk[3];
		middle = chunk[2];
		right = chunk[1];

		stamp = String.format(
				"%.3f",
				new Object[] { Double.valueOf(criaByte(29, 10, chunk)
						+ criaByte(22, 7, chunk) / 100.0D) });
	}

	private static byte[] readFile(File file) {
		byte[] ret = (byte[]) null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			long len = file.length();
			ret = new byte[(int) len];
			fis.read(ret);
		} catch (FileNotFoundException e) {
			ret = (byte[]) null;

			if (fis != null)
				try {
					fis.close();
				} catch (IOException localIOException1) {
				}
		} catch (IOException e) {
			ret = (byte[]) null;

			if (fis != null)
				try {
					fis.close();
				} catch (IOException localIOException2) {
				}
		} finally {
			if (fis != null)
				try {
					fis.close();
				} catch (IOException localIOException3) {
				}
		}
		return ret;
	}

	




	private static void parseHistory(File selectedFile) {

		long fileSize = selectedFile.length();
		int recordNum = (int) (fileSize - 15L) / 20;
		String[][] tableData = new String[recordNum][Const.historyName.length];
		for (int i = 0; i < recordNum; i++) {
			tableData[i][0] = String.valueOf(i + 1);
			for (int j = 1; j < Const.historyName.length; j++) {
				tableData[i][j] = Const.CALCULATING;
			}
		}
		table = new JTable(tableData, Const.historyName);
		table.setAutoResizeMode(4);
		table.setDefaultRenderer(Object.class, cellRenderer);
		scrollPane = new JScrollPane(table);
		frame.getContentPane().add(scrollPane, "Center");
		// selectedFile, recordNum
		long time = System.currentTimeMillis();
		HistoryInfo hi = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(selectedFile);
		} catch (FileNotFoundException e) {
			fis = null;
			ToolKit.percent = 100;
			CommonUtil.setLabel(Const.LOADHISTORYFAILED);
			JOptionPane.showMessageDialog(ToolKit.frame, Const.ERROR,
					Const.ERROR, 0);
		}

		if (fis != null) {
			byte[] recordItem = new byte[20];
			for (int i = 0; i < recordNum; i++) {
				int lenRet = 0;
				try {
					lenRet = fis.read(recordItem, 0, 20);
				} catch (Exception e) {
					recordItem = (byte[]) null;
					lenRet = 0;
				}
				if ((recordItem == null) || (lenRet != 20)) {
					for (int j = 1; j < Const.historyName.length; j++)
						ToolKit.table.setValueAt(Const.INVALID, i, j);
				} else {
					hi = new HistoryInfo();
					int month = recordItem[0];
					int day = recordItem[1];
					int year1 = recordItem[2] & 0xFF;
					int year2 = recordItem[3] & 0xFF;
					int year = year1 * 256 + year2;
					int hour = recordItem[4];
					int minute = recordItem[5];
					int seconde = recordItem[6];
					hi.date = String.format(
							"%02d/%02d/%02d %02d:%02d:%02d",
							new Object[] { Integer.valueOf(year),
									Integer.valueOf(month),
									Integer.valueOf(day),
									Integer.valueOf(hour),
									Integer.valueOf(minute),
									Integer.valueOf(seconde) });
					switch (recordItem[7]) {
					case 1:
						hi.level = Const.LEVEL_BEG;
						break;
					case 2:
						hi.level = Const.LEVEL_INT;
						break;
					case 3:
						hi.level = Const.LEVEL_EXP;
						break;
					default:
						hi.level = Const.LEVEL_CUS;
					}

					int time1 = recordItem[8] & 0xFF;
					int time2 = recordItem[9] & 0xFF;
					int time3 = recordItem[10] & 0xFF;
					hi.time = (time1 * 256 + time2 + 1 + time3 / 100.0D);

					int bbbv1 = recordItem[11] & 0xFF;
					int bbbv2 = recordItem[12] & 0xFF;
					hi.bbbv = (bbbv1 * 256 + bbbv2);

					int lClick1 = recordItem[13] & 0xFF;
					int lClick2 = recordItem[14] & 0xFF;
					hi.lClick = (lClick1 * 256 + lClick2);
					int dClick1 = recordItem[15] & 0xFF;
					int dClick2 = recordItem[16] & 0xFF;
					hi.dClick = (dClick1 * 256 + dClick2);
					int rClick1 = recordItem[17] & 0xFF;
					int rClick2 = recordItem[18] & 0xFF;
					hi.rClick = (rClick1 * 256 + rClick2);

					ToolKit.table.setValueAt(hi.level, i, 1);
					ToolKit.table.setValueAt(
							hi.rClick == 0 ? Const.STYLE_NF : Const.STYLE_FL,
							i, 2);
					ToolKit.table.setValueAt(
							String.format("%.2f",
									new Object[] { Double.valueOf(hi.time) }),
							i, 3);
					ToolKit.table.setValueAt(hi.date, i, 4);
					ToolKit.table.setValueAt(String.valueOf(hi.bbbv), i, 5);
					ToolKit.table.setValueAt(
							String.format(
									"%.2f",
									new Object[] { Double.valueOf(hi.bbbv
											/ (hi.time - 1.0D)) }), i, 6);
					ToolKit.table.setValueAt(
							String.format(
									"%.2f",
									new Object[] { Double.valueOf((hi.lClick
											+ hi.rClick + hi.dClick)
											/ (hi.time - 1.0D)) }), i, 7);
					ToolKit.table.setValueAt(String.format(
							"%.2f",
							new Object[] { Double.valueOf((hi.time - 1.0D)
									* hi.time / hi.bbbv) }), i, 8);
					ToolKit.table.setValueAt(String.format(
							"%.3f",
							new Object[] { Double.valueOf(hi.bbbv * 1.0D
									/ (hi.lClick + hi.rClick + hi.dClick)) }),
							i, 9);
					ToolKit.table.setValueAt(String.valueOf(hi.lClick), i,
							10);
					ToolKit.table.setValueAt(String.valueOf(hi.rClick), i,
							11);
					ToolKit.table.setValueAt(String.valueOf(hi.dClick), i,
							12);
					ToolKit.table.setValueAt(String.format(
							"%.3f",
							new Object[] { Double.valueOf(Math.pow(
									(hi.time - 1.0D), 1.7) / (hi.bbbv)) }), i,
							13);
				}
				ToolKit.percent = (i + 1) * 100 / recordNum;
				long time2 = System.currentTimeMillis();
				long time3 = time2 - time;
				CommonUtil.setLabel(Const.CALCULATING + " "
						+ ToolKit.percent + " %" + " 用时" + time3 + "毫秒");

			}
			try {
				fis.close();
			} catch (Exception localException1) {
			}
		}

	}

	


	public static ZiniNum calcZini(int width, int height, int mines,
			Cell[] board) {
		int size = width * height;
		closed_cells = size;
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
		System.out.println("mine");
		for (int r = 0; r < height; r++) {
			for (int c = 0; c < width; c++) {
				int index = c * height + r;
				System.out.print(board[index].mine );
			}
			System.out.println();
		}
		System.out.println("number");
		for (int r = 0; r < height; r++) {
			for (int c = 0; c < width; c++) {
				int index = c * height + r;
				System.out.print(board[index].number );
			}
			System.out.println();
		}
		for (int i = 0; i < size; i++) {
			if (board[i].mine == 0) {
				switch (board[i].number) {
				case 0:
					num0++;
					break;
				case 1:
					num1++;
					break;
				case 2:
					num2++;
					break;
				case 3:
					num3++;
					break;
				case 4:
					num4++;
					break;
				case 5:
					num5++;
					break;
				case 6:
					num6++;
					break;
				case 7:
					num7++;
					break;
				case 8:
					num8++;
					break;
				}
			}
		}
		//=0*16+5
		//  现在=		5 *width 
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

		zini = 0;
		for (int j = 0; j < size; j++) {
			if ((board[j].number == 0) && (board[j].opened == 0)) {
				click(board, height, size, j);
			}
		}
		while (closed_cells > mines) {
			applyzini(board, height, size);
		}
		return new ZiniNum(zini, islands,bbbv, num0, num1, num2, num3, num4, num5,
				num6, num7, num8, openings);
	
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
	
	private static void click(Cell[] board, int height, int size, int index) {
		reveal(board, height, size, index);
		zini += 1;
	}

	private static void flag(Cell[] board, int height, int index) {
		if (board[index].flagged != 0) {
			return;
		}
		zini += 1;
		board[index].flagged = 1;
		for (int rr = board[index].rb; rr <= board[index].re; rr++)
			for (int cc = board[index].cb; cc <= board[index].ce; cc++)
				board[(cc * height + rr)].premium += 1;
	}

	private static void flagaround(Cell[] board, int height, int index) {
		for (int rr = board[index].rb; rr <= board[index].re; rr++)
			for (int cc = board[index].cb; cc <= board[index].ce; cc++) {
				int i = cc * height + rr;
				if (board[i].mine != 0)
					flag(board, height, i);
			}
	}

	private static void chord(Cell[] board, int height, int size, int index) {
		zini += 1;
		for (int rr = board[index].rb; rr <= board[index].re; rr++)
			for (int cc = board[index].cb; cc <= board[index].ce; cc++)
				reveal(board, height, size, cc * height + rr);
	}

	private static void applyzini(Cell[] board, int height, int size) {
		int maxp = -1;
		int curi = -1;
		for (int i = 0; i < size; i++) {
			if ((board[i].mine != 0) || (board[i].opened == 0)
					|| (board[i].premium <= maxp))
				continue;
			maxp = board[i].premium;
			curi = i;
		}

		if (curi != -1) {
			if (board[curi].opened == 0) {
				click(board, height, size, curi);
			}
			flagaround(board, height, curi);
			chord(board, height, size, curi);
		} else {
			for (int j = 0; j < size; j++) {
				if ((board[j].opened != 0) || (board[j].mine != 0)
						|| ((board[j].number != 0) && (board[j].opening != 0)))
					continue;
				curi = j;
				break;
			}
			click(board, height, size, curi);
		}
	}

	private static void reveal(Cell[] board, int height, int size, int index) {
		if (board[index].opened != 0) {
			return;
		}
		if (board[index].flagged != 0) {
			return;
		}
		if (board[index].number != 0) {
			open(board, height, index);
		} else {
			int op = board[index].opening;

			for (int i = 0; i < size; i++)
				if ((board[i].opening2 == op) || (board[i].opening == op)) {
					if (board[i].opened == 0) {
						open(board, height, i);
					}
					board[i].premium -= 1;
				}
		}
	}

	private static void open(Cell[] board, int height, int index) {
		board[index].opened = 1;
		board[index].premium += 1;
		if (board[index].opening == 0) {
			for (int rr = board[index].rb; rr <= board[index].re; rr++) {
				for (int cc = board[index].cb; cc <= board[index].ce; cc++) {
					board[(cc * height + rr)].premium -= 1;
				}
			}
		}
		closed_cells -= 1;
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
}