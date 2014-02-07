package com.mstoolkit.command;

import java.io.File;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.mstoolkit.Const;
import com.mstoolkit.bean.VideoDisplayBean;
import com.mstoolkit.common.CommonUtil;
import com.mstoolkit.init.MainPanel;

/**
 * 点击打开操作时事件
 * 
 * @author zhangye date 2014-1-10
 */
public class OpenCommand implements ICommand
{
    public void execute(JFrame frame)
    {
        if (CommonUtil.percent != 100)
        {
            JOptionPane.showMessageDialog(frame, Const.DONOTINTERRUPT, Const.WARNING, 2);
        }
        else
        {
            JFileChooser fc = new JFileChooser();
            fc.setCurrentDirectory(new File(CommonUtil.curMVFDir));
            fc.setDialogTitle(Const.DIATITLE);
            fc.setFileSelectionMode(1);
            fc.setMultiSelectionEnabled(false);
            int ret = fc.showOpenDialog(frame);
            if (ret == 0)
            {
                CommonUtil.curMVFDir = fc.getSelectedFile().getParent();
                CommonUtil.readDir();

                frame.remove(MainPanel.scrollPane);
                updateList(fc.getSelectedFile(), frame);
            }
        }

    }

    protected static void updateList(File selectedFile, JFrame frame)
    {
        if (!selectedFile.isDirectory())
        {/*
          * if (isHistroyValid(selectedFile)) { CommonUtil.setLabel(Const.FINDHISTORYFILE); parseHistory(selectedFile); } else { int ret = JOptionPane.showConfirmDialog(MainPanel.frame,
          * Const.DOYOUWANNAFIX, Const.QUESTION, 0); if (ret == 0) fixHistroy(selectedFile); else CommonUtil.setLabel(Const.WELCOME); }
          */
        }
        else
        {
            File[] fileList = selectedFile.listFiles();
            if (fileList == null)
            {
                CommonUtil.setLabel(selectedFile.getName() + " " + Const.CANNOTACCESS);
                MainPanel.scrollPane = new JScrollPane();
                frame.getContentPane().add(MainPanel.scrollPane, "Center");
            }
            else
            {
                File[] mvfList = filterVideo(fileList);
                if (mvfList.length <= 0)
                {
                    CommonUtil.setLabel(Const.EMPTYFOLDER + " \"" + selectedFile.getName() + "\"");
                    MainPanel.scrollPane = new JScrollPane();
                    frame.getContentPane().add(MainPanel.scrollPane, "Center");
                }
                else
                {
                    CommonUtil.percent = 0;
                    CommonUtil.setLabel(mvfList.length + " " + Const.FOUNDMVF + " " + selectedFile.getName());
                    String[][] tableData = new String[mvfList.length][Const.name.length];
                    for (int i = 0; i < mvfList.length; i++)
                    {
                        tableData[i][0] = String.valueOf(i + 1);
                        tableData[i][1] = mvfList[i].getName();
                        for (int j = 2; j < Const.name.length; j++)
                        {
                            tableData[i][j] = Const.CALCULATING;
                        }
                    }
                    CommonUtil.table = new JTable(tableData, Const.name);
                    CommonUtil.table.setAutoResizeMode(4);
                    CommonUtil.table.setDefaultRenderer(Object.class, MainPanel.cellRenderer);
                    MainPanel.scrollPane = new JScrollPane(CommonUtil.table);
                    // frame.getContentPane().add(scrollPane, "Center");
                    frame.add(MainPanel.scrollPane, "Center");
                    long time1 = System.currentTimeMillis();
                    updateUI(mvfList, time1, CommonUtil.table);

                }
            }
        }
    }

    private static void updateUI(final File[] mvfList, final long time, final JTable table)
    {
        new Thread()
        {
            public void run()
            {
                int size = mvfList.length;
                VideoDisplayBean mi = null;
                for (int i = 0; i < size; i++)
                {
                    mi = parseVideo(mvfList[i]);
                    setValueAt(mi, table, i);
                    CommonUtil.percent = (i + 1) * 100 / size;
                    long time2 = System.currentTimeMillis();
                    double time3 = (time2 - time) / 1000d;
                    double time4 = (time3 / (i + 1)) * (size - i - 1);
                    double x = i + 1;
                    double speed = (x / time3);
                    DecimalFormat dcmFmt = new DecimalFormat("0.000");
                    CommonUtil.setLabel(Const.CALCULATING + " " + CommonUtil.percent + " %" + " " + (i + 1) + "/" + size + " 用时：" + dcmFmt.format(time3) + "秒" + " 剩余用时" + dcmFmt.format(time4) + "秒"
                            + " 速度：" + dcmFmt.format(speed) + "个/秒");
                    try
                    {
                        Thread.sleep(0L);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public static File[] filterVideo(File[] fileList)
    {
        ArrayList<File> al = new ArrayList<File>();
        File[] arrayOfFile1 = fileList;
        int j = fileList.length;
        for (int i = 0; i < j; i++)
        {
            File f = arrayOfFile1[i];
            // 追加支持avf
            if (f.getName().toLowerCase().endsWith(".mvf") || f.getName().toLowerCase().endsWith(".avf") || f.getName().toLowerCase().endsWith(".rmv"))
            {
                al.add(f);
            }
        }
        int size = al.size();
        File[] resList = new File[size];
        for (int i = 0; i < size; i++)
        {
            resList[i] = ((File) al.get(i));
        }
        return resList;
    }

    /**
     * 
     * @param file
     *            file
     * @return VideoDisplayBean VideoDisplayBean
     */
    public static VideoDisplayBean parseVideo(File file)
    {
        String name = file.getName();
        VideoDisplayBean bean = new VideoDisplayBean();
        bean.setName(name);
        CommonUtil.fillBean(bean, Const.CALCULATING);
        String videoType = "";
        if (file.getName().toLowerCase().endsWith(".mvf"))
        {
            videoType = "Mvf";
        }
        else if (file.getName().toLowerCase().endsWith(".avf"))
        {
            videoType = "Avf";
        }
        else
        {
            videoType = "Rmv";
        }
        // 各种录像信息在这里 给转化成RAW格式
        if (file.exists())
        {
            byte[] byteStream = CommonUtil.readFile(file);
            Class<?> classMethod = null;
            try
            {
                String classNameStr = "com.mstoolkit.util." + videoType + "Util";
                classMethod = Class.forName(classNameStr);
                Method method = classMethod.getMethod("analyzeVideo", byte[].class, VideoDisplayBean.class);
                method.invoke(classMethod.newInstance(), byteStream, bean);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            CommonUtil.fillBean(bean, Const.FILENOFOUND);
        }
        // 根据RAW格式计算信息
        return bean;
    }

    /**
     * 
     * @param mi
     *            mi
     * @param table
     *            table
     * @param i
     *            i
     */
    private static void setValueAt(VideoDisplayBean mi, final JTable table, int i)
    {
        String[] lst = { "name", "mvfType", "version", "userID", "date", "level", "style", "mode", "time", "bbbv", "bbbvs", "distance", "clicks", "zini", "hzini", "rqp", "ioe", "completion", "num0",
                "num1", "num2", "num3", "num4", "num5", "num6", "num7", "num8", "numAll", "disSpeed", "openings", "allClicks", "disBv", "disNum", "hzoe", "zoe", "numSpeed", "hzinis", "zinis",
                "occam", "lclicks", "dclicks", "rclicks", "qg", "flags", "markFlag", "hold","cloneR",  "islands" };
        Map<String, Object> map = CommonUtil.changeSimpleBeanToMap(mi);
        int j = 1;
        for (String tmp : lst)
        {
            table.setValueAt(map.get(tmp), i, j);
            j++;
        }

    }
}
