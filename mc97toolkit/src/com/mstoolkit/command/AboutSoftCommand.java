package com.mstoolkit.command;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.mstoolkit.Const;
import com.mstoolkit.common.CommonUtil;

/**
 * 拆分方法该方法主要内容为对mvf文件解析
 * 
 * @author zhangye
 * @date 2013-11-3
 */
public class AboutSoftCommand implements ICommand
{
    public void execute(JFrame frame)
    {

        CommonUtil.setLabel(Const.WELCOME);
        JOptionPane.showMessageDialog(frame, Const.HOWTOUSE, Const.ABOUTSOFT, 1);

    }
}
