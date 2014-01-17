package com.mstoolkit.command;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.mstoolkit.Const;
import com.mstoolkit.common.CommonUtil;
import com.mstoolkit.init.MainPanel;
import com.mstoolkit.videoUtil.VideoTest;

/**
 * 点击打开操作时事件
 * 
 * @author zhangye
 * @date 2014-1-10
 */
public class TestCommand implements ICommand {
	public void execute(JFrame frame) {
		CommonUtil.setLabel("test");
		String strid = JOptionPane.showInputDialog(
				"参数，依次为宽,长,雷,样本数(长,宽1-2位数字,雷2位数字,样本最大为1亿)", "8,8,10,1000");
		if (strid == null) {
			return;
		}
		if (strid.equals("")) {
			JOptionPane.showMessageDialog(frame, "请输入参数", "提示信息",
					JOptionPane.ERROR_MESSAGE);
			return;
		} else if (strid
				.matches("^[1-9]([0-9]{0,1}),[1-9]([0-9]{0,1}),[1-9]([0-9]{1,1}),[1-9]([0-9]{0,8})$")) {
			String[] lst=strid.split(",");
			int height=Integer.parseInt(lst[0]);
			int width=Integer.parseInt(lst[1]);
			int mines=Integer.parseInt(lst[2]);
			int calc=Integer.parseInt(lst[3]);
			
			int[][] lsts= VideoTest.calcBv(height, width, mines, calc,frame);
			String[][] tableData = new String[lsts.length][2];
			for (int i = 0; i < lsts.length; i++) {
				tableData[i][0] = String.valueOf(lsts[i][0]);
				tableData[i][1] =  String.valueOf(lsts[i][1]);
			}
			frame.remove(MainPanel.scrollPane);
			CommonUtil.table = new JTable(tableData, new String[] {"bv","count"});
			CommonUtil.table.setAutoResizeMode(4);
			DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
			cellRenderer.setHorizontalAlignment(0);
			CommonUtil.table.setDefaultRenderer(Object.class, cellRenderer);
			MainPanel.scrollPane = new JScrollPane(CommonUtil.table);
			
			//frame.getContentPane().add(scrollPane, "Center");
			//frame.getContentPane().add(scrollPane, "Center");
			frame.add(MainPanel.scrollPane , "Center");
			frame.setVisible(true);
		} else {
			JOptionPane.showMessageDialog(frame, "格式不正确", "提示信息",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

}
