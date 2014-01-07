package com.minesweeperToolkit.command;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.minesweeperToolkit.Const;


/**
 * 拆分方法该方法主要内容为对mvf文件解析
 * @author zhangye
 * @date 2013-11-3
 */
public class SwitchLanguageCommand implements  ICommand {
	public void execute(JFrame frame){
		boolean success = false;
		boolean redo = false;
		if ((Const.useChinese) && (Const.lang.exists()))
			success = Const.lang.delete();
		else if ((!Const.useChinese) && (!Const.lang.exists()))
			try {
				success = Const.lang.createNewFile();
			} catch (IOException e1) {
				success = false;
			}
		else {
			redo = true;
		}
		if (redo)
			JOptionPane.showMessageDialog(frame,
					Const.RESWITCHLANGUAGE, Const.WARNING, 2);
		else if (success)
			JOptionPane.showMessageDialog(frame,
					Const.SWITCHLANGUAGESUCCESS, Const.INFORMATION, 1);
		else
			JOptionPane.showMessageDialog(frame,
					Const.SWITCHLANGUAGEFAILED, Const.ERROR, 0);
	}
}
