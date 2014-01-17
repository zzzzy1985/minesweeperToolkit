package com.minesweeperToolkit.command;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import com.minesweeperToolkit.Const;
import com.minesweeperToolkit.ToolKit;
import com.minesweeperToolkit.common.CommonUtil;


/**
 * 拆分方法该方法主要内容为对mvf文件解析
 * @author zhangye
 * @date 2013-11-3
 */
public class LoadHistoryCommand implements  ICommand {
	public void execute(JFrame frame){
		if (CommonUtil.percent != 100) {
			JOptionPane.showMessageDialog(frame,
					Const.DONOTINTERRUPT, Const.WARNING, 2);
		} else {
			CommonUtil.setLabel(Const.LOADHISTORY);
			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new File(CommonUtil.curHistoryDir));
			fc.setDialogTitle(Const.CHOOSEHISTORYFILE);
			fc.setFileSelectionMode(0);
			fc.setMultiSelectionEnabled(false);
			fc.setFileFilter(new FileFilter() {
				public String getDescription() {
					return null;
				}
				public boolean accept(File f) {
					return (f.isDirectory())
							|| (f.getName().equals("history.inf"));
				}
			});
			int ret = fc.showOpenDialog(frame);
			if (ret == 0) {
				CommonUtil.curHistoryDir = fc.getSelectedFile()
						.getParent();
				CommonUtil.keepDir();
				CommonUtil.table = null;
				//frame.remove(ToolKit.scrollPane);
				//ToolKit.updateList(fc.getSelectedFile());
			}
		}
	}
}
