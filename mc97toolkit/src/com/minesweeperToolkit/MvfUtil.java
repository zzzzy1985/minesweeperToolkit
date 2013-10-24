package com.minesweeperToolkit;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

public class MvfUtil {
	private static String TITLE = "Minesweeper Clone 0.97 Distance Calculator";
	private static String VERSION = "V0.1";
	private static String WELCOME = "Welcome to use the Distance Calculator for Minesweeper Clone 0.97~";
	private static String AUTHOR = "Made by crazyks!";
	private static String DIATITLE = "Please choose a folder with MVF Files";
	private static String NOTADIRECTORY = "is not a directory!";
	private static String CANNOTACCESS = "can not access!";
	private static String EMPTYFOLDER = "There is no any MVF file in";
	private static String FOUNDMVF = "MVF File(s) in";
	private static String FILE = "File";
	private static String ABOUT = "About";
	private static String OPEN = "Open";
	private static String EXPORT = "Export";
	private static String QUIT = "Quit";
	private static String ABOUTSOFT = "Software";
	private static String ABOUTAUTHOR = "Author";
	private static String CALCULATING = "Calculating...";
	private static String INTVALID = "Invalid!";
	private static String FILENOFOUND = "No File!";
	private static String NOTHINGEXPORTED = "Nothing can be exported!";
	private static String EXPORTFAILED = "Export failed!";

	private static String LEVEL_BEG = "Beginner";
	private static String LEVEL_INT = "Intermediate";
	private static String LEVEL_EXP = "Expert";
	private static String LEVEL_CUS = "Custom";

	private static String MODE_CLS = "Classic";
	private static String MODE_DEN = "Density";
	private static String MODE_UPK = "UPK";
	private static String MODE_CHT = "Cheat";

	private static String[] name = { "No.", "File Name", "ID", "Level", "Mode",
			"Time", "3bv", "3bv/s", "Distance", "Completion" };

	private static int[] power2 = { 1, 2, 4, 8, 16, 32, 64, 128, 256, 512,
			1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144,
			524288, 1048576, 2097152, 4194304 };
	private static int x;
	private static int y;
	private static String stamp = "0.000";

	private static JFrame frame = null;
	private static JLabel label = null;
	private static JTable table = null;
	private static JScrollPane scrollPane = null;

	private static ActionListener actionListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			if (cmd.equals(MvfUtil.OPEN)) {
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle(MvfUtil.DIATITLE);
				fc.setFileSelectionMode(1);
				fc.setMultiSelectionEnabled(false);
				int ret = fc.showOpenDialog(MvfUtil.frame);
				if (ret == 0)
					MvfUtil.updateList(fc.getSelectedFile());
			} else if (cmd.equals(MvfUtil.EXPORT)) {
				if (MvfUtil.table == null) {
					if (MvfUtil.label != null) {
						MvfUtil.label.setText(MvfUtil.NOTHINGEXPORTED);
						MvfUtil.label.setVisible(true);
					}
				} else {
					try {
						MvfUtil.exportTable(MvfUtil.table, new File(
								"mvfParser.xls"));
					} catch (IOException e1) {
						if (MvfUtil.label == null)
							return;
					}
					MvfUtil.label.setText(MvfUtil.EXPORTFAILED);
					MvfUtil.label.setVisible(true);
				}

			} else if (cmd.equals(MvfUtil.QUIT)) {
				if (MvfUtil.frame != null) {
					MvfUtil.frame.dispose();
					System.exit(0);
				}
			} else if (cmd.equals(MvfUtil.ABOUTAUTHOR)) {
				if (MvfUtil.label != null) {
					MvfUtil.label.setText(MvfUtil.AUTHOR);
					MvfUtil.label.setVisible(true);
				}
			} else if ((cmd.equals(MvfUtil.ABOUTSOFT))
					&& (MvfUtil.label != null)) {
				MvfUtil.label.setText(MvfUtil.WELCOME);
				MvfUtil.label.setVisible(true);
			}
		}
	};

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
		System.out.println("write out to: " + file);
	}

	public static void main(String[] args) {
		gettt();
		//createWindow();
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
	protected static void updateList(File selectedFile) {
		if (!selectedFile.isDirectory()) {
			if (label != null) {
				label.setText(selectedFile.getName() + " " + NOTADIRECTORY);
				label.setVisible(true);
			}
		} else {
			File[] fileList = selectedFile.listFiles();
			if (fileList == null) {
				if (label != null) {
					label.setText(selectedFile.getName() + " " + CANNOTACCESS);
					label.setVisible(true);
				}
			} else {
				File[] mvfList = filterMVF(fileList);
				if (mvfList.length == 0) {
					if (label != null) {
						label.setText(EMPTYFOLDER + " "
								+ selectedFile.getName());
						label.setVisible(true);
					}
				} else {
					if (label != null) {
						label.setText(mvfList.length + " " + FOUNDMVF + " "
								+ selectedFile.getName());
						label.setVisible(true);
					}
					String[][] tableData = new String[mvfList.length][name.length];
					for (int i = 0; i < mvfList.length; i++) {
						tableData[i][0] = String.valueOf(i + 1);
						tableData[i][1] = mvfList[i].getName();
						for (int j = 2; j < name.length; j++) {
							tableData[i][j] = CALCULATING;
						}
					}
					table = new JTable(tableData, name);
					table.setAutoResizeMode(4);
					label.setVisible(false);
					scrollPane = new JScrollPane(table);
					frame.getContentPane().add(scrollPane, "Center");
					updateUI(mvfList);
				}
			}
		}
	}

	private static void updateUI(final File[] mvfList) {
		new Thread() {
			public void run() {
				int size = mvfList.length;
				MVFInfo mi = null;
				for (int i = 0; i < size; i++) {
					mi = MvfUtil.parseMVF(mvfList[i]);
					MvfUtil.table.setValueAt(mi.name, i, 1);
					MvfUtil.table.setValueAt(mi.userID, i, 2);
					MvfUtil.table.setValueAt(mi.level, i, 3);
					MvfUtil.table.setValueAt(mi.mode, i, 4);
					MvfUtil.table.setValueAt(mi.time, i, 5);
					MvfUtil.table.setValueAt(mi.bbbv, i, 6);
					MvfUtil.table.setValueAt(mi.bbbvs, i, 7);
					MvfUtil.table.setValueAt(mi.distance, i, 8);
					MvfUtil.table.setValueAt(mi.completion, i, 9);
					try {
						Thread.sleep(100L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();

	}

	public static MVFInfo parseMVF(File file) {
		String name = file.getName();
		String userID = CALCULATING;
		String level = CALCULATING;
		String mode = CALCULATING;
		String time = CALCULATING;
		String bbbv = CALCULATING;
		String bbbvs = CALCULATING;
		String distance = CALCULATING;
		String completion = CALCULATING;

		if (file.exists()) {
			byte[] byteStream = readFile(file);
			try {
				if (byteStream[27] == 53) {
					switch (byteStream[81]) {
					case 1:
						level = LEVEL_BEG;
						break;
					case 2:
						level = LEVEL_INT;
						break;
					case 3:
						level = LEVEL_EXP;
						break;
					default:
						level = LEVEL_CUS;
					}

					switch (byteStream[82]) {
					case 1:
						mode = MODE_CLS;
						break;
					case 2:
						mode = MODE_DEN;
						break;
					case 3:
						mode = MODE_UPK;
						break;
					default:
						mode = MODE_CHT;
					}

					int iTime1 = byteStream[83] & 0xFF;
					int iTime2 = byteStream[84] & 0xFF;
					int iTime3 = byteStream[85] & 0xFF;
					double iTime = iTime1 * 256 + iTime2 + 1 + iTime3 * 1.0D
							/ 100.0D;
					time = String.format("%.3f",
							new Object[] { Double.valueOf(iTime) });

					int i3bv1 = byteStream[86] & 0xFF;
					int i3bv2 = byteStream[87] & 0xFF;
					int i3bv = i3bv1 * 256 + i3bv2;
					bbbv = String.format("%d",
							new Object[] { Integer.valueOf(i3bv) });

					int iComp3bv1 = byteStream[88] & 0xFF;
					int iComp3bv2 = byteStream[89] & 0xFF;
					int iComp3bv = iComp3bv1 * 256 + iComp3bv2;
					completion = String.format(
							"%.1f%%",
							new Object[] { Double.valueOf(iComp3bv * 100.0D
									/ i3bv) });

					bbbvs = String.format(
							"%.3f",
							new Object[] { Double.valueOf(iComp3bv
									/ (iTime - 1.0D)) });

					int mines1 = byteStream[99] & 0xFF;
					int mines2 = byteStream[100] & 0xFF;
					int mines = mines1 * 256 + mines2;
					int idLen = byteStream[(101 + mines * 2)] & 0xFF;
					userID = new String(byteStream, 101 + mines * 2 + 1, idLen);

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
					int[] actions = new int[5];
					for (int i = 0; i < steps; i++) {
						for (int j = 0; j < 5; j++) {
							actions[j] = (byteStream[(operIndexSet + i * 5 + j)] & 0xFF);
						}
						descriptografa097(key, actions);
						if (stamp.equals("0.000")) {
							oldx = x;
							oldy = y;
						} else {
							fDistance = fDistance
									+ Math.sqrt((x - oldx) * (x - oldx)
											+ (y - oldy) * (y - oldy));
							oldx = x;
							oldy = y;
						}
					}

					distance = String.format("%.3f",
							new Object[] { Double.valueOf(fDistance) });
					return null;
					/*return new MVFInfo(name, userID, level, mode, time, bbbv,
							bbbvs, distance, completion);*/
					
				}

				userID = INTVALID;
				level = INTVALID;
				mode = INTVALID;
				time = INTVALID;
				bbbv = INTVALID;
				bbbvs = INTVALID;
				distance = INTVALID;
				completion = INTVALID;
			} catch (Exception e) {
				userID = INTVALID;
				level = INTVALID;
				mode = INTVALID;
				time = INTVALID;
				bbbv = INTVALID;
				bbbvs = INTVALID;
				distance = INTVALID;
				completion = INTVALID;
			}
		} else {
			userID = FILENOFOUND;
			level = FILENOFOUND;
			mode = FILENOFOUND;
			time = FILENOFOUND;
			bbbv = FILENOFOUND;
			bbbvs = FILENOFOUND;
			distance = FILENOFOUND;
			completion = FILENOFOUND;
		}
		/*return new MVFInfo(name, userID, level, mode, time, bbbv, bbbvs,
				distance, completion);*/
		return null;
		/*
		 * label869: return new MVFInfo(name, userID, level, mode, time, bbbv,
		 * bbbvs, distance, completion);
		 */
	}

	private static int criaByte(int inicio, int L, int[] chunk) {
		int ret = 0;
		int i = 0;
		for (i = 0; i < L; i++) {
			ret += power2[i] * (short) (chunk[(inicio + i)] & 0xFFFF);
		}
		return ret;
	}

	private static void binario(int action, int n, int[] flags) {
		int what = action;
		int index = n;
		while (--index >= 0) {
			flags[index + 1] = (what / power2[index]) > 0 ? 1 : 0;
			what = what % power2[index];
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
			e.printStackTrace();
			ret = (byte[]) null;

			if (fis != null)

				try {
					fis.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
		} catch (IOException e) {
			e.printStackTrace();
			ret = (byte[]) null;

			if (fis != null)
				try {
					fis.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return ret;
	}

	private static File[] filterMVF(File[] fileList) {
		ArrayList<File> al = new ArrayList<File>();
		File[] arrayOfFile1 = fileList;
		int j = fileList.length;
		for (int i = 0; i < j; i++) {
			File f = arrayOfFile1[i];
			if (f.getName().toLowerCase().endsWith(".mvf")) {
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

	private static void createWindow() {
		frame = new JFrame(TITLE + " " + VERSION);
		frame.setDefaultCloseOperation(3);

		JMenuBar menuBar = new JMenuBar();

		JMenu menu = new JMenu(FILE);
		JMenuItem open = new JMenuItem(OPEN, 111);
		open.addActionListener(actionListener);
		JMenuItem export = new JMenuItem(EXPORT, 101);
		export.addActionListener(actionListener);
		JMenuItem quit = new JMenuItem(QUIT, 113);
		quit.addActionListener(actionListener);
		menu.add(open);
		menu.add(export);
		menu.add(quit);
		menuBar.add(menu);

		JMenu about = new JMenu(ABOUT);
		JMenuItem aboutSoftware = new JMenuItem(ABOUTSOFT);
		aboutSoftware.addActionListener(actionListener);
		JMenuItem aboutAuthor = new JMenuItem(ABOUTAUTHOR);
		aboutAuthor.addActionListener(actionListener);
		about.add(aboutAuthor);
		about.add(aboutSoftware);
		menuBar.add(about);

		menuBar.setPreferredSize(new Dimension(720, 24));

		label = new JLabel(WELCOME, 0);
		label.setPreferredSize(new Dimension(720, 576));

		frame.getContentPane().add(menuBar, "North");
		frame.getContentPane().add(label, "Center");

		frame.setSize(720, 576);
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);
	}
}
