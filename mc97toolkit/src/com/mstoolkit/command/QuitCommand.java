package com.mstoolkit.command;

import javax.swing.JFrame;


/**
 * 拆分方法该方法主要内容为对mvf文件解析
 * @author zhangye
 * @date 2013-11-3
 */
public class QuitCommand implements  ICommand {
	public void execute(JFrame frame){

		if (frame != null) {
			frame.dispose();
			System.exit(0);
		}
	
	}
}
