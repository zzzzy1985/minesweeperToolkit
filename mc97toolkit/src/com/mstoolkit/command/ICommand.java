package com.mstoolkit.command;

import javax.swing.JFrame;

/**
 * 命令启动接口
 * 
 * @author zhangYe
 * @version 2013-11-3
 */
public interface ICommand
{
    /**
     * 执行
     * @param frame frame
     */
    void execute(JFrame frame);
}
