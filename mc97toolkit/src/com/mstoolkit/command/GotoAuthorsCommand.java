package com.mstoolkit.command;

import javax.swing.JFrame;

import com.mstoolkit.Const;
import com.mstoolkit.common.CommonUtil;

/**
 * 拆分方法该方法主要内容为对mvf文件解析
 * 
 * @author zhangye
 * @date 2013-11-3
 */
public class GotoAuthorsCommand implements ICommand
{
    public void execute(JFrame frame)
    {
        CommonUtil.setLabel(Const.GOTOAUTHORS);
        CommonUtil.goTourl("http://www.saolei.net/Player/Index.asp?Id=4843");
    }
}
