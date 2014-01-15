package com.minesweeperToolkit.init;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableCellRenderer;

import com.minesweeperToolkit.Const;
import com.minesweeperToolkit.common.CommonUtil;

/**
 * 启动程序的主方法
 * @author zhangye
 * @date 2014-1-10
 */
public class MainPanel {
	/**主界面的Frame */
	public static JFrame frame = null;
	/**主界面的Label 显示在左下角 */
	public static JLabel label = null;
	/**主界面的Panel */
	public static JScrollPane scrollPane = null;
	/**单元格cellRender */
	public static DefaultTableCellRenderer cellRenderer = null;
	/**
	 * 监听事件
	 */
	private static ActionListener actionListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			JMenuItem jMenuItem=(JMenuItem)e.getSource();
			int mnemonic= jMenuItem.getMnemonic();
			// 根据mnemonic调用方法
			CommonUtil.getMthodByMnemonic(mnemonic,frame);
		}
	};
	/**
	 * 主方法
	 * 
	 * @param args 参数
	 */
	public static void main(String[] args) {
	    //创建window  
		createWindow();
		//首次进入显示
		CommonUtil.firstTimeShow(frame);
	}

	private static void createWindow() {
		frame = new JFrame(Const.TITLE + " " + Const.VERSION);
		frame.setIconImage(new ImageIcon("icon.png").getImage());
		frame.setDefaultCloseOperation(3);

		CommonUtil.readDir();

		JMenuBar menuBar = new JMenuBar();
		JMenu bsKit = new JMenu(Const.BASIC);
		JMenuItem open = new JMenuItem(Const.OPEN, 100);
		open.addActionListener(actionListener);
		JMenuItem export = new JMenuItem(Const.EXPORT, 101);
		export.addActionListener(actionListener);
		JMenuItem quit = new JMenuItem(Const.QUIT, 102);
		quit.addActionListener(actionListener);
		bsKit.add(open);
		bsKit.add(export);
		bsKit.add(quit);
		menuBar.add(bsKit);

		JMenu exKit = new JMenu(Const.EXTRA);
		JMenuItem fixHistory = new JMenuItem(Const.LOADHISTORY, 103);
		fixHistory.addActionListener(actionListener);
		JMenuItem switchlang = new JMenuItem(Const.SWITCHLANGUAGE, 104);
		switchlang.addActionListener(actionListener);
		exKit.add(fixHistory);
		exKit.add(switchlang);
		menuBar.add(exKit);

		JMenu about = new JMenu(Const.ABOUT);
		JMenuItem aboutSoftware = new JMenuItem(Const.ABOUTSOFT, 105);
		aboutSoftware.addActionListener(actionListener);
		JMenuItem aboutAuthor = new JMenuItem(Const.ABOUTAUTHOR, 106);
		aboutAuthor.addActionListener(actionListener);
		JMenuItem saolei = new JMenuItem(Const.GOTOSAOLEINET, 107);
		saolei.addActionListener(actionListener);
		JMenuItem author = new JMenuItem(Const.GOTOAUTHORS, 108);
		author.addActionListener(actionListener);
		JMenuItem test = new JMenuItem("test", 109);
		test.addActionListener(actionListener);
		about.add(aboutAuthor);
		about.add(aboutSoftware);
		about.add(saolei);
		about.add(author);
		about.add(test);
		menuBar.add(about);

		menuBar.setPreferredSize(new Dimension(720, 24));

		label = new JLabel(Const.WELCOME, 2);
		label.setPreferredSize(new Dimension(720, 24));

		cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setHorizontalAlignment(0);

		scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(720, 576));
		frame.getContentPane().add(scrollPane, "Center");

		frame.getContentPane().add(menuBar, "North");
		frame.getContentPane().add(label, "South");

		frame.setSize(720, 576);
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);
	}
}
