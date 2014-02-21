package com.mstoolkit.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import com.mstoolkit.Const;
import com.mstoolkit.bean.VideoDisplayBean;
import com.mstoolkit.init.MainPanel;
/**
 * CommonUtil
 * @author zhangye
 *
 */
public class CommonUtil
{
    /** curMVFDir*/ 
    public static String curMVFDir = ".";
    /** curHistoryDir*/
    public static String curHistoryDir = ".";
    /** curExportDir*/
    public static String curExportDir = ".";
    /** percent*/
    public static int percent = 100;
    public static JLabel label = null;
    public static JTable table = null;

    /**
     * 读取目录
     */
    public static void readDir()
    {
        BufferedReader br = null;
        FileReader fr = null;
        try
        {
            fr = new FileReader(Const.f);
            br = new BufferedReader(fr);
            curMVFDir = br.readLine();
            curHistoryDir = br.readLine();
            curExportDir = br.readLine();
        }
        catch (Exception e)
        {
            curMVFDir = null;
            curHistoryDir = null;
            curExportDir = null;
            try
            {
                br.close();
            }
            catch (Exception localException1)
            {
            }
            try
            {
                fr.close();
            }
            catch (Exception localException2)
            {
            }
        }
        finally
        {
            try
            {
                br.close();
            }
            catch (Exception localException3)
            {
            }
            try
            {
                fr.close();
            }
            catch (Exception localException4)
            {
            }
        }
        if ((curMVFDir == null) || (curMVFDir.equals("")))
        {
            curMVFDir = ".";
        }
        if ((curHistoryDir == null) || (curHistoryDir.equals("")))
        {
            curHistoryDir = ".";
        }
        if ((curExportDir == null) || (curExportDir.equals("")))
            curExportDir = ".";
    }

    public static void setLabel(String text)
    {
        if (MainPanel.label != null)
        {
            MainPanel.label.setText(text);
            MainPanel.label.setVisible(true);
        }
    }

    public static void goTourl(String url)
    {
        try
        {
            Runtime.getRuntime().exec("cmd /c start " + url);
        }
        catch (IOException e1)
        {
            // JOptionPane.showMessageDialog(frame, Const.OPENBROWSERFAILED,
            // Const.ERROR, 0);
        }
    }

    public static void keepDir()
    {
        BufferedWriter bw = null;
        FileWriter fw = null;
        try
        {
            fw = new FileWriter(Const.f);
            bw = new BufferedWriter(fw);
            bw.write(curMVFDir + "\n" + curHistoryDir + "\n" + curExportDir);
        }
        catch (Exception localException)
        {
            try
            {
                bw.close();
            }
            catch (Exception localException1)
            {
            }
            try
            {
                fw.close();
            }
            catch (Exception localException2)
            {
            }
        }
        finally
        {
            try
            {
                bw.close();
            }
            catch (Exception localException3)
            {
            }
            try
            {
                fw.close();
            }
            catch (Exception localException4)
            {
            }
        }
    }

    public static void getMthodByMnemonic(int mnemonic, JFrame frame)
    {
        Map<Integer, String> map = new HashMap<Integer, String>();
        //
        map.put(100, "Open");
        //
        map.put(101, "Export");
        //
        map.put(102, "Quit");
        //
        map.put(103, "LoadHistory");
        //
        map.put(104, "SwitchLanguage");
        //
        map.put(105, "AboutSoft");
        //
        map.put(106, "AboutAuthor");
        //
        map.put(107, "GotoSaoleiNet");
        //
        map.put(108, "GotoAuthors");
        //
        map.put(109, "Test");
        String methodName = (String) map.get(mnemonic);
        Class<?> classMethod = null;
        try
        {
            String classNameStr = "com.mstoolkit.command." + methodName + "Command";
            classMethod = Class.forName(classNameStr);
            Method method = classMethod.getMethod("execute", JFrame.class);
            method.invoke(classMethod.newInstance(), frame);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 第一次进入页面
     */
    public static void firstTimeShow(JFrame frame)
    {
        if (!Const.f.exists())
        {
            try
            {
                Const.f.createNewFile();
            }
            catch (IOException localIOException)
            {
            }
            JOptionPane.showMessageDialog(frame, Const.HOWTOUSE, Const.ABOUTSOFT, 1);
        }
    }

    public static boolean isHistroyValid(File selectedFile)
    {
        if (!selectedFile.getName().toLowerCase().equals("history.inf"))
        {
            return false;
        }
        long fileSize = selectedFile.length();

        return fileSize >= 35L;
    }

    public static void fixHistroy(File selectedFile)
    {
        JOptionPane.showMessageDialog(MainPanel.FRAME, Const.FORBIDDEN, Const.SORRY, 2);
        CommonUtil.setLabel(Const.WELCOME);
    }

    public static void fillBean(VideoDisplayBean bean, String str)
    {
        Class<?> classMethod = null;
        try
        {
            String classNameStr = "com.mstoolkit.bean.VideoDisplayBean";
            classMethod = Class.forName(classNameStr);
            Method[] methods = classMethod.getMethods();
            for (Method method : methods)
            {
                String name = method.getName();
                if (!"setName".equals(name) && name.startsWith("set"))
                {
                    Method setMethod = classMethod.getMethod(name, String.class);
                    setMethod.invoke(bean, str);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static byte[] readFile(File file)
    {
        byte[] ret = (byte[]) null;
        FileInputStream fis = null;
        try
        {
            fis = new FileInputStream(file);
            long len = file.length();
            ret = new byte[(int) len];
            fis.read(ret);
        }
        catch (FileNotFoundException e)
        {
            ret = (byte[]) null;

            if (fis != null)
            {
                try
                {
                    fis.close();
                }
                catch (IOException localIOException1)
                {
                }
            }
        }
        catch (IOException e)
        {
            ret = (byte[]) null;

            if (fis != null)
            {
                try
                {
                    fis.close();
                }
                catch (IOException localIOException2)
                {
                }
            }
        }
        finally
        {
            if (fis != null)
            {
                try
                {
                    fis.close();
                }
                catch (IOException localIOException3)
                {
                }
            }
        }
        return ret;
    }

    /**
     * 一个公共的用于将简单的bean中的值转化为map的方法。 简单bean说明：bean中只包含基本对象，并且含有get方法。 不会对bean中的list，Map，这些对象赋值。
     * 
     * @param <T>
     * @param t
     * @return
     */
    public static <T> Map<String, Object> changeSimpleBeanToMap(T t)
    {
        Map<String, Object> map = new HashMap<String, Object>();

        Class cls = t.getClass();
        Field[] fieldlist = cls.getDeclaredFields();
        for (int i = 0; i < fieldlist.length; i++)
        {
            Field f = fieldlist[i];
            String name = f.getName();
            Class<?> cs = f.getType();
            try
            {
                String methodNme = changeStrToGetMethodNames(name);
                Method meth = null;
                Method[] mds = cls.getDeclaredMethods();
                for (int j = 0; j < mds.length; j++)
                {
                    if (mds[j].getName().equals(methodNme))
                    {
                        meth = cls.getMethod(methodNme);
                        Object rtcs = meth.invoke(t, null);
                        map.put(name, rtcs);
                        break;
                    }
                }
                if (meth == null)
                {
                    System.out.println("在" + t.getClass().getName() + "中没有找到" + methodNme + "方法");
                }

            }
            catch (SecurityException e)
            {
                e.printStackTrace();
            }
            catch (NoSuchMethodException e)
            {
                e.printStackTrace();
            }
            catch (IllegalArgumentException e)
            {
                e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
            catch (InvocationTargetException e)
            {
                e.printStackTrace();
            }
        }
        return map;
    }

    public static String changeStrToSetMethodNames(String str)
    {
        if (str == null || "".equals(str.trim()))
        {
            return null;
        }
        char firstChar = str.toCharArray()[0];
        String upstr = String.valueOf(firstChar).toUpperCase();
        return "set" + upstr + str.substring(1);
    }

    private static String changeStrToGetMethodNames(String str)
    {
        if (str == null || "".equals(str.trim()))
        {
            return null;
        }
        char firstChar = str.toCharArray()[0];
        String upstr = String.valueOf(firstChar).toUpperCase();
        return "get" + upstr + str.substring(1);
    }

    public void getObjMethodsWithExtends(Class<?> cls, List<Method> list)
    {
        if (cls.equals(Object.class))
        {
            return;
        }
        Method[] mds = cls.getDeclaredMethods();
        for (int i = 0; i < mds.length; i++)
        {
            list.add(mds[i]);
        }
        getObjMethodsWithExtends(cls.getSuperclass(), list);
    }

    /**
     * 导出excel文件
     * 
     * @param table table
     * @param file file
     * @throws IOException
     */
    public static void exportTable(JTable table, File file) throws IOException
    {
        TableModel model = table.getModel();
        FileWriter out = new FileWriter(file);

        for (int i = 0; i < model.getColumnCount(); i++)
        {
            out.write(model.getColumnName(i) + "\t");
        }
        out.write("\n");
        for (int i = 0; i < model.getRowCount(); i++)
        {
            for (int j = 0; j < model.getColumnCount(); j++)
            {
                if (model.getValueAt(i, j) != null)
                {
                    out.write(model.getValueAt(i, j).toString().trim() + "\t");
                }
            }
            out.write("\n");
        }
        out.close();
    }
}