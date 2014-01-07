package com.minesweeperToolkit.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.minesweeperToolkit.Const;
import com.minesweeperToolkit.bean.VideoDisplayBean;
import com.minesweeperToolkit.init.MainPanel;

public class CommonUtil {
	public static  String curMVFDir = ".";
	public static  String curHistoryDir = ".";
	public static  String curExportDir = ".";
	public static int percent = 100;
	private static JLabel label = null;
	/**
	 * 读取目录
	 */
	public static void readDir() {
		BufferedReader br = null;
		FileReader fr = null;
		try {
			fr = new FileReader(Const.f);
			br = new BufferedReader(fr);
			curMVFDir = br.readLine();
			curHistoryDir = br.readLine();
			curExportDir = br.readLine();
		} catch (Exception e) {
			curMVFDir = null;
			curHistoryDir = null;
			curExportDir = null;
			try {
				br.close();
			} catch (Exception localException1) {
			}
			try {
				fr.close();
			} catch (Exception localException2) {
			}
		} finally {
			try {
				br.close();
			} catch (Exception localException3) {
			}
			try {
				fr.close();
			} catch (Exception localException4) {
			}
		}
		if ((curMVFDir == null) || (curMVFDir.equals(""))) {
			curMVFDir = ".";
		}
		if ((curHistoryDir == null) || (curHistoryDir.equals(""))) {
			curHistoryDir = ".";
		}
		if ((curExportDir == null) || (curExportDir.equals("")))
			curExportDir = ".";
	}
	public static void setLabel(String text) {
		if (MainPanel.label != null) {
			MainPanel.label.setText(text);
			MainPanel.label.setVisible(true);
		}
	}

	public static void goTourl(String url) {
		try {
			Runtime.getRuntime().exec("cmd /c start " + url);
		} catch (IOException e1) {
			//JOptionPane.showMessageDialog(frame, Const.OPENBROWSERFAILED,
			//		Const.ERROR, 0);
		}
	}
	public static void keepDir() {
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {
			fw = new FileWriter(Const.f);
			bw = new BufferedWriter(fw);
			bw.write(curMVFDir + "\n" + curHistoryDir + "\n" + curExportDir);
		} catch (Exception localException) {
			try {
				bw.close();
			} catch (Exception localException1) {
			}
			try {
				fw.close();
			} catch (Exception localException2) {
			}
		} finally {
			try {
				bw.close();
			} catch (Exception localException3) {
			}
			try {
				fw.close();
			} catch (Exception localException4) {
			}
		}
	}
	public static void getMthodByMnemonic(int mnemonic,JFrame frame) {
		Map<Integer,String> map =new HashMap<Integer,String>();
		//
		map.put(100, "Open");
		//
		map.put(101, "Export");
		//
		map.put(102, "Quit");
		//
		map.put(103, "LoadHistory");
		//
		map.put(104, "SwitchLanguage");
		//
		map.put(105, "AboutSoft");
		//
		map.put(106, "AboutAuthor");
		//
		map.put(107, "GotoSaoleiNet");
		//
		map.put(108, "gotoAuthors");
		String methodName =(String)map.get(mnemonic);
		Class<?> classMethod = null;
        try {
        	String classNameStr="com.minesweeperToolkit.command."+methodName+"Command";
        	classMethod = Class.forName(classNameStr);
        	  Method method=classMethod.getMethod("execute",JFrame.class);
              method.invoke(classMethod.newInstance(),frame);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	/**
	 * 第一次进入页面
	 */
	public static void firstTimeShow(JFrame frame) {
		if (!Const.f.exists()) {
			try {
				Const.f.createNewFile();
			} catch (IOException localIOException) {
			}
			JOptionPane.showMessageDialog(frame, Const.HOWTOUSE,
					Const.ABOUTSOFT, 1);
		}
	}
	public static boolean isHistroyValid(File selectedFile) {
		if (!selectedFile.getName().toLowerCase().equals("history.inf")) {
			return false;
		}
		long fileSize = selectedFile.length();

		return fileSize >= 35L;
	}

	public static void fixHistroy(File selectedFile) {
		JOptionPane.showMessageDialog(MainPanel.frame, Const.FORBIDDEN, Const.SORRY, 2);
		CommonUtil.setLabel(Const.WELCOME);
	}
	public static void fillBean(VideoDisplayBean bean,String str) {
		Class<?> classMethod = null;
        try {
        	String classNameStr="com.minesweeperToolkit.bean.VideoDisplayBean";
        	classMethod = Class.forName(classNameStr);
        	  Method[] methods=classMethod.getMethods();
        	  for(Method method:methods){
        		  String name=method.getName();
        		  if(!"setName".equals(name) &&name.startsWith("set") ){
        			  Method setMethod=classMethod.getMethod(name,String.class);
        			  setMethod.invoke(bean,str);
        		  }
        	  }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	public static byte[] readFile(File file) {
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

	
}