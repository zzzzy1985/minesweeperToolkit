package com.minesweeperToolkit.command;

import javax.swing.JFrame;

import com.minesweeperToolkit.Const;
import com.minesweeperToolkit.common.CommonUtil;


/**
 * 拆分方法该方法主要内容为对mvf文件解析
 * @author zhangye
 * @date 2013-11-3
 */
public class GotoSaoleiNetCommand implements  ICommand {
	public void execute(JFrame frame){
		CommonUtil.setLabel(Const.GOTOSAOLEINET);
		CommonUtil.goTourl("http://www.saolei.net");
	}
}
