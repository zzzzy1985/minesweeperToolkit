package com.minesweeperToolkit.command;

import java.io.File;
import java.io.IOException;

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
public class ExportCommand implements  ICommand {
	public void execute(JFrame frame){

		if (CommonUtil.percent != 100) {
			JOptionPane.showMessageDialog(frame,
					Const.DONOTINTERRUPT, Const.WARNING, 2);
		} else if (ToolKit.table == null) {
			CommonUtil.setLabel(Const.NOTHINGEXPORTED);
			JOptionPane.showMessageDialog(frame,
					Const.NOTHINGEXPORTED, Const.ERROR, 0);
		} else {
			CommonUtil.setLabel(Const.EXPORTTABLE);
			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new File(ToolKit.curExportDir));
			fc.setDialogTitle(Const.CHOOSEXLSFILE);
			fc.setFileSelectionMode(0);
			fc.setMultiSelectionEnabled(false);
			fc.setFileFilter(new FileFilter() {
				public String getDescription() {
					return null;
				}
				public boolean accept(File f) {
					return (f.isDirectory())
							|| (f.getName().toLowerCase()
									.endsWith(".xls"));
				}
			});
			int ret = fc.showOpenDialog(frame);
			if (ret == 0) {
				ToolKit.curExportDir = fc.getSelectedFile()
						.getParent();
				CommonUtil.keepDir();
				File f = fc.getSelectedFile();
				String fileName = f.getAbsolutePath();
				if (!fileName.toLowerCase().endsWith(".xls")) {
					f = new File(fileName + ".xls");
				}
				boolean saveOrNot = true;
				if (f.exists()) {
					int chooseRet = JOptionPane.showConfirmDialog(
							frame, Const.WANNAOVERWRITE,
							Const.QUESTION, 0);
					if (chooseRet == 0)
						saveOrNot = true;
					else
						saveOrNot = false;
				} else {
					saveOrNot = true;
				}
				if (saveOrNot)
					//try {
						//ToolKit.exportTable(ToolKit.table, f);
						CommonUtil.setLabel(Const.EXPORTTO + " \""
								+ f.getAbsolutePath() + "\" "
								+ Const.SUCCESS);
						/*} catch (IOException e1) {
						CommonUtil.setLabel(Const.EXPORTFAILED);
						JOptionPane.showMessageDialog(
								frame, Const.EXPORTFAILED,
								Const.WARNING, 2);
					}*/
				else {
					CommonUtil.setLabel(Const.WELCOME);
				}
			}
		}
	
	}
}
