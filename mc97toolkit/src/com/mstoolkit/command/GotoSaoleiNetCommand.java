package com.mstoolkit.command;

import javax.swing.JFrame;

import com.mstoolkit.Const;
import com.mstoolkit.common.CommonUtil;

/**
 * 拆分方法该方法主要内容为对mvf文件解析
 * 
 * @author zhangye
 * @version 2013-11-3
 */
public class GotoSaoleiNetCommand implements ICommand
{
    /**
     * execute
     * @param frame frame
     */
    public void execute(JFrame frame)
    {
        CommonUtil.setLabel(Const.GOTOSAOLEINET);
        CommonUtil.goTourl("http://www.saolei.net");
    }
}
