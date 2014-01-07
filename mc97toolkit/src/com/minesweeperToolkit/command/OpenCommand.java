package com.minesweeperToolkit.command;


import java.io.File;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.minesweeperToolkit.Const;
import com.minesweeperToolkit.bean.VideoDisplayBean;
import com.minesweeperToolkit.common.CommonUtil;
import com.minesweeperToolkit.init.MainPanel;


/**
 * 点击打开操作时
 * @author zhangye
 * @date 2013-11-3
 */
public class OpenCommand implements  ICommand {
	public void execute(JFrame frame){
		if (CommonUtil.percent != 100) {
			JOptionPane.showMessageDialog(frame,
					Const.DONOTINTERRUPT, Const.WARNING, 2);
		} else {
			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new File(CommonUtil.curMVFDir));
			fc.setDialogTitle(Const.DIATITLE);
			fc.setFileSelectionMode(1);
			fc.setMultiSelectionEnabled(false);
			int ret = fc.showOpenDialog(frame);
			if (ret == 0) {
				CommonUtil.curMVFDir = fc.getSelectedFile()
						.getParent();
				CommonUtil.readDir();
				//table = null;
				frame.remove(MainPanel.scrollPane);
				updateList(fc.getSelectedFile(),frame);
			}
		}
	
	}
	
	protected static void updateList(File selectedFile,JFrame frame) {
		if (!selectedFile.isDirectory()) {/*
			if (isHistroyValid(selectedFile)) {
				CommonUtil.setLabel(Const.FINDHISTORYFILE);
				parseHistory(selectedFile);
			} else {
				int ret = JOptionPane.showConfirmDialog(MainPanel.frame,
						Const.DOYOUWANNAFIX, Const.QUESTION, 0);
				if (ret == 0)
					fixHistroy(selectedFile);
				else
					CommonUtil.setLabel(Const.WELCOME);
			}
		*/} else {
			File[] fileList = selectedFile.listFiles();
			if (fileList == null) {
				CommonUtil.setLabel(selectedFile.getName() + " " + Const.CANNOTACCESS);
				JScrollPane scrollPane = new JScrollPane();
				frame.getContentPane().add(scrollPane, "Center");
			} else {
				File[] mvfList = filterVideo(fileList);
				if (mvfList.length <= 0) {
					CommonUtil.setLabel(Const.EMPTYFOLDER + " \"" + selectedFile.getName()
							+ "\"");
					JScrollPane scrollPane = new JScrollPane();
					frame.getContentPane().add(scrollPane, "Center");
				} else {
					CommonUtil.percent = 0;
					CommonUtil.setLabel(mvfList.length + " " + Const.FOUNDMVF + " "
							+ selectedFile.getName());
					String[][] tableData = new String[mvfList.length][Const.name.length];
					for (int i = 0; i < mvfList.length; i++) {
						tableData[i][0] = String.valueOf(i + 1);
						tableData[i][1] = mvfList[i].getName();
						for (int j = 2; j < Const.name.length; j++) {
							tableData[i][j] = Const.CALCULATING;
						}
					}
					JTable table = new JTable(tableData, Const.name);
					table.setAutoResizeMode(4);
					table.setDefaultRenderer(Object.class, MainPanel.cellRenderer);
					JScrollPane scrollPane = new JScrollPane(table);
					frame.getContentPane().add(scrollPane, "Center");
					long time1 = System.currentTimeMillis();
					updateUI(mvfList, time1,table);
				}
			}
		}
	}

	private static void updateUI(final File[] mvfList, final long time,final JTable table ) {
		new Thread() {
			public void run() {
				int size = mvfList.length;
				VideoDisplayBean mi = null;
				for (int i = 0; i < size; i++) {
					mi = parseVideo(mvfList[i]);
					table.setValueAt(mi.name, i, 1);
					table.setValueAt(mi.mvfType, i, 2);
					table.setValueAt(mi.userID, i, 3);
					table.setValueAt(mi.date, i, 4);
					table.setValueAt(mi.level, i, 5);
					table.setValueAt(mi.style, i, 6);
					table.setValueAt(mi.mode, i, 7);
					table.setValueAt(mi.time, i, 8);
					table.setValueAt(mi.bbbv, i, 9);
					table.setValueAt(mi.bbbvs, i, 10);
					table.setValueAt(mi.distance, i, 11);
					table.setValueAt(mi.clicks, i, 12);
					table.setValueAt(mi.zini, i, 13);
					table.setValueAt(mi.rqp, i, 14);
					table.setValueAt(mi.ioe, i, 15);
					table.setValueAt(mi.completion, i, 16);
					table.setValueAt(mi.num0, i, 17);
					table.setValueAt(mi.num1, i, 18);
					table.setValueAt(mi.num2, i, 19);
					table.setValueAt(mi.num3, i, 20);
					table.setValueAt(mi.num4, i, 21);
					table.setValueAt(mi.num5, i, 22);
					table.setValueAt(mi.num6, i, 23);
					table.setValueAt(mi.num7, i, 24);
					table.setValueAt(mi.num8, i, 25);

					table.setValueAt(mi.numAll, i, 26);

					table.setValueAt(mi.disSpeed, i, 27);
					table.setValueAt(mi.openings, i, 28);
					table.setValueAt(mi.allClicks, i, 29);
					table.setValueAt(mi.disBv, i, 30);
					table.setValueAt(mi.disNum, i, 31);
					table.setValueAt(mi.hzoe, i, 32);
					table.setValueAt(mi.numSpeed, i, 33);
					table.setValueAt(mi.zinis, i, 34);
					table.setValueAt(mi.occam, i, 35);

					table.setValueAt(mi.lclicks, i, 36);
					table.setValueAt(mi.dclicks, i, 37);
					table.setValueAt(mi.rclicks, i, 38);
					table.setValueAt(mi.qg, i, 39);
					table.setValueAt(mi.flags, i, 40);
					table.setValueAt(mi.markFlag, i, 41);
					table.setValueAt(mi.islands, i, 42);
					CommonUtil.percent = (i + 1) * 100 / size;
					long time2 = System.currentTimeMillis();
					double time3 = (time2 - time) / 1000d;
					double time4 = (time3 / (i + 1)) * (size - i - 1);
					double x = i + 1;
					double speed = (x / time3);
					DecimalFormat dcmFmt = new DecimalFormat("0.000");
					CommonUtil.setLabel(Const.CALCULATING + " "
							+ CommonUtil.percent + " %" + " " + (i + 1) + "/"
							+ size + " 用时：" + dcmFmt.format(time3) + "秒"
							+ " 剩余用时" + dcmFmt.format(time4) + "秒" + " 速度："
							+ dcmFmt.format(speed) + "个/秒");
					try {
						Thread.sleep(0L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	public static File[] filterVideo(File[] fileList) {
		ArrayList<File> al = new ArrayList<File>();
		File[] arrayOfFile1 = fileList;
		int j = fileList.length;
		for (int i = 0; i < j; i++) {
			File f = arrayOfFile1[i];
			// 追加支持avf
			if (f.getName().toLowerCase().endsWith(".mvf")
					|| f.getName().toLowerCase().endsWith(".avf")
					|| f.getName().toLowerCase().endsWith(".rmv")) {
				al.add(f);
			}
		}
		int size = al.size();
		File[] resList = new File[size];
		for (int i = 0; i < size; i++) {
			resList[i] = ((File) al.get(i));
		}
		return resList;
	}
	public static VideoDisplayBean parseVideo(File file) {
		String name = file.getName();
		VideoDisplayBean bean =new VideoDisplayBean();
		bean.setName(name);
		CommonUtil.fillBean(bean,Const.CALCULATING);
		String videoType="";
		if (file.getName().toLowerCase().endsWith(".mvf")) {
			videoType = "Mvf";
		} else if (file.getName().toLowerCase().endsWith(".avf"))  {
			videoType = "Avf";
		}else{
			videoType = "Rmv";
		}
		// 各种录像信息在这里 给转化成RAW格式
		if (file.exists()) {
			byte[] byteStream = CommonUtil.readFile(file);
			Class<?> classMethod = null;
	        try {
	        	String classNameStr="com.minesweeperToolkit.videoUtil."+videoType+"Util";
	        	classMethod = Class.forName(classNameStr);
	        	  Method method=classMethod.getMethod("analyzeVideo",byte.class,VideoDisplayBean.class);
	              method.invoke(classMethod.newInstance(),byteStream,bean);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
		else{
			CommonUtil.fillBean(bean,Const.FILENOFOUND);
		}
		// 根据RAW格式计算信息
		return bean;
	}
}
