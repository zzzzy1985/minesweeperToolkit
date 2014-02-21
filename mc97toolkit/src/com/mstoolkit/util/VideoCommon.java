package com.mstoolkit.util;

import java.util.HashMap;
import java.util.Map;

import com.mstoolkit.bean.BoardBean;
import com.mstoolkit.bean.EventBean;
import com.mstoolkit.bean.RawBaseBean;
import com.mstoolkit.bean.RawBoardBean;
import com.mstoolkit.bean.RawVideoBean;
import com.mstoolkit.bean.VideoDisplayBean;
import com.mstoolkit.common.CommonUtil;

/**
 * 文件解析接口
 * 
 * @author zhangYe
 * @version 2013-11-3
 */
public class VideoCommon
{
    /**
     * 转换录像
     * 
     * @param rawVideoBean
     *            传入录像bean
     * @param videoDisplayBean
     *            转换录像bean
     */
    public static void convertVideoDisplay(RawVideoBean rawVideoBean, VideoDisplayBean videoDisplayBean)
    {
        if (rawVideoBean.isCheckFlag())
        {
            // 设定基本信息
            setBaseInfo(rawVideoBean, videoDisplayBean);
            // 设定board信息
            setBoardInfo(rawVideoBean, videoDisplayBean);
            // 设定event信息
            setEventInfo(rawVideoBean, videoDisplayBean);
            // 设定计算信息
            setCalcInfo(rawVideoBean, videoDisplayBean);
        }
        else
        {
            CommonUtil.fillBean(videoDisplayBean, "ZZV5");
        }
    }

    /**
     * 设定问号模式
     * 
     * @param markFlag
     *            问号标记值
     * @return string 问号标记对应值
     */
    public static String setMarkFlag(String markFlag)
    {
        Map<String, String> map = new HashMap<String, String>();
        map.put("0", "UNMARK");
        map.put("1", "MARK");
        return map.get(markFlag);
    }

    /**
     * 设定模式
     * 
     * @param mode
     *            模式值
     * @param type
     *            类型
     * @return string 模式对应值
     */
    public static String setMode(String mode, String type)
    {
        Map<String, String> map = new HashMap<String, String>();
        if (type.equals("clone"))
        {
            map.put("0", "null");
            map.put("1", "classic");
            map.put("2", "density");
            map.put("3", "UPK");
            map.put("4", "cheat");
        }
        else
        {
            map.put("0", "null");
            map.put("1", "classic");
            map.put("2", "classic");
            map.put("3", "classic");
            map.put("4", "density");
        }
        return map.get(mode);
    }

    /**
     * 设定级别
     * 
     * @param level
     *            级别值
     * @return string 级别对应值
     */
    public static String setLevel(String level)
    {
        Map<String, String> map = new HashMap<String, String>();
        map.put("0", "null");
        map.put("1", "beg");
        map.put("2", "int");
        map.put("3", "exp");
        map.put("4", "custom");
        map.put("5", "custom");
        return map.get(level);
    }

    /**
     * 出现错误情况
     * 
     * @param rawVideoBean
     *            传入录像bean
     * @param errMessage
     *            出错信息
     * @return rawVideoBean 传入录像bean
     */
    public static RawVideoBean errorVideo(RawVideoBean rawVideoBean, String errMessage)
    {
        rawVideoBean.setCheckFlag(false);
        rawVideoBean.setErrorMessage(errMessage);
        return rawVideoBean;
    }

    /**
     * 设置基本信息
     * 
     * @param rawVideoBean
     *            传入录像bean
     * @param videoDisplayBean
     *            转换录像bean
     */
    private static void setBaseInfo(RawVideoBean rawVideoBean, VideoDisplayBean videoDisplayBean)
    {
        RawBaseBean rawBaseBean = rawVideoBean.getRawBaseBean();
        // 设定程序名称
        videoDisplayBean.setMvfType(rawBaseBean.getProgram());
        // 设定程序版本
        videoDisplayBean.setVersion(rawBaseBean.getVersion());
        // 设定用户标识
        videoDisplayBean.setUserID(rawBaseBean.getPlayer());
        // 设定时间
        videoDisplayBean.setDate(rawBaseBean.getTimeStamp());
        // 设定级别
        videoDisplayBean.setLevel(setLevel(rawBaseBean.getLevel()));
        // 设定模式
        videoDisplayBean.setMode(setMode(rawBaseBean.getMode(), rawBaseBean.getProgram()));
        // 设定问号模式
        videoDisplayBean.setMarkFlag(setMarkFlag(rawBaseBean.getQm()));
    }

    /**
     * 根据board读取board信息
     * 
     * @param rawVideoBean
     *            传入录像bean
     * @param videoDisplayBean
     *            转换录像bean
     */
    private static void setBoardInfo(RawVideoBean rawVideoBean, VideoDisplayBean videoDisplayBean)
    {
        RawBoardBean rawBoardBean = rawVideoBean.getRawBoardBean();
        // 根据board 解析board内容
        BoardBean boardBean = BoardCommon.getBoardBean(rawBoardBean.width, rawBoardBean.height, rawBoardBean.mines, rawBoardBean.cbBoard);
        videoDisplayBean.setBbbv(String.valueOf(boardBean.bbbv));
        videoDisplayBean.setZini(String.valueOf(boardBean.zini));
        videoDisplayBean.setHzini(String.valueOf(boardBean.hzini));
        videoDisplayBean.setNum0(String.valueOf(boardBean.num0));
        videoDisplayBean.setNum1(String.valueOf(boardBean.num1));
        videoDisplayBean.setNum2(String.valueOf(boardBean.num2));
        videoDisplayBean.setNum3(String.valueOf(boardBean.num3));
        videoDisplayBean.setNum4(String.valueOf(boardBean.num4));
        videoDisplayBean.setNum5(String.valueOf(boardBean.num5));
        videoDisplayBean.setNum6(String.valueOf(boardBean.num6));
        videoDisplayBean.setNum7(String.valueOf(boardBean.num7));
        videoDisplayBean.setNum8(String.valueOf(boardBean.num8));
        int numall = boardBean.num1 * 1 + boardBean.num2 * 2 + boardBean.num3 * 3 + boardBean.num4 * 4 + boardBean.num5 * 5 + boardBean.num6 * 6 + boardBean.num7 * 7 + boardBean.num8 * 8;
        videoDisplayBean.setNumAll(String.valueOf(numall));
        videoDisplayBean.setOpenings(String.valueOf(boardBean.openings));
        videoDisplayBean.setIslands(String.valueOf(boardBean.islands));
    }

    /**
     * 根据event读取event信息
     * 
     * @param rawVideoBean
     *            传入录像bean
     * @param videoDisplayBean
     *            转换录像bean
     */
    private static void setEventInfo(RawVideoBean rawVideoBean, VideoDisplayBean videoDisplayBean)
    {
        EventBean eventBean = EventCommon.getEventBean(rawVideoBean,videoDisplayBean);
        videoDisplayBean.setLclicks(String.valueOf(eventBean.getL()));
        videoDisplayBean.setDclicks(String.valueOf(eventBean.getD()));
        videoDisplayBean.setRclicks(String.valueOf(eventBean.getR()));
        videoDisplayBean.setCloneR(String.valueOf(eventBean.getR() + eventBean.getCloneR()));
        int allClicks = eventBean.getL() + eventBean.getD() + eventBean.getR();
        videoDisplayBean.setAllClicks(String.valueOf(allClicks));
        videoDisplayBean.setFlags(String.valueOf(eventBean.getFlags()));
        videoDisplayBean.setTime(String.format("%.3f", new Object[] { eventBean.getSaoleiTime() }));
        videoDisplayBean.setDistance(String.format("%.3f", new Object[] { eventBean.getDistance() }));
        videoDisplayBean.setHold(String.valueOf(eventBean.getHolds()));
        videoDisplayBean.setEventSize(String.valueOf(eventBean.getEventSize()));
        videoDisplayBean.setMvsize(String.valueOf(eventBean.getMvsize()));
        videoDisplayBean.setLcsize(String.valueOf(eventBean.getLcsize()));
        videoDisplayBean.setLrsize(String.valueOf(eventBean.getLrsize()));
        videoDisplayBean.setRcsize(String.valueOf(eventBean.getRcsize()));
        videoDisplayBean.setRrsize(String.valueOf(eventBean.getRrsize()));
        videoDisplayBean.setMcsize(String.valueOf(eventBean.getMcsize()));
        videoDisplayBean.setMrsize(String.valueOf(eventBean.getMrsize()));
        videoDisplayBean.setWasteflags(String.valueOf(eventBean.getWastedflags()));
        videoDisplayBean.setFirstLx(String.valueOf(eventBean.getFirstlx()));
        videoDisplayBean.setFirstLy(String.valueOf(eventBean.getFirstly()));
    }

    /**
     * 得出计算参数
     * 
     * @param rawVideoBean
     *            传入录像bean
     * @param videoDisplayBean
     *            转换录像bean
     */
    private static void setCalcInfo(RawVideoBean rawVideoBean, VideoDisplayBean videoDisplayBean)
    {
        // 风格(非严格判定)
        if ("0".equals(videoDisplayBean.getRclicks()))
        {
            videoDisplayBean.setStyle("NF");
        }
        else
        {
            videoDisplayBean.setStyle("FL");
        }
        // 3bvs =3bv/time
        videoDisplayBean.setBbbvs(String.format("%.3f", new Object[] { Double.valueOf(videoDisplayBean.getBbbv()) / (Double.valueOf(videoDisplayBean.getTime())) }));
        // clicks =allclick/time
        videoDisplayBean.setClicks(String.format("%.3f", new Object[] { Double.valueOf(videoDisplayBean.getAllClicks()) / (Double.valueOf(videoDisplayBean.getTime())) }));
        // rqp =(time*(time+1))/3bv
        videoDisplayBean.setRqp(String.format("%.3f",
                new Object[] { (Double.valueOf(videoDisplayBean.getTime()) + 1.0D) * (Double.valueOf(videoDisplayBean.getTime())) / Double.valueOf(videoDisplayBean.getBbbv()) }));
        // ioe =3bv/allclick
        videoDisplayBean.setIoe(String.format("%.3f", new Object[] { (Double.valueOf(videoDisplayBean.getBbbv()) / Double.valueOf(videoDisplayBean.getAllClicks())) }));
        // dispeed=distance/time
        videoDisplayBean.setDisSpeed(String.format("%.3f", new Object[] { (Double.valueOf(videoDisplayBean.getDistance())) / Double.valueOf(videoDisplayBean.getTime()) }));
        // disbv=distance/3bv
        videoDisplayBean.setDisBv(String.format("%.3f", new Object[] { (Double.valueOf(videoDisplayBean.getDistance())) / Double.valueOf(videoDisplayBean.getBbbv()) }));
        // disNum=distance/numAll
        videoDisplayBean.setDisNum(String.format("%.3f", new Object[] { (Double.valueOf(videoDisplayBean.getDistance())) / Double.valueOf(videoDisplayBean.getNumAll()) }));
        // hzoe=hzini/allclick
        videoDisplayBean.setHzoe(String.format("%.3f", new Object[] { (Double.valueOf(videoDisplayBean.getHzini())) / Double.valueOf(videoDisplayBean.getAllClicks()) }));
        // zoe=zini/allclick
        videoDisplayBean.setZoe(String.format("%.3f", new Object[] { (Double.valueOf(videoDisplayBean.getZini())) / Double.valueOf(videoDisplayBean.getAllClicks()) }));
        // numspeed=numall/time
        videoDisplayBean.setNumSpeed(String.format("%.3f", new Object[] { (Double.valueOf(videoDisplayBean.getNumAll())) / Double.valueOf(videoDisplayBean.getTime()) }));
        // hzinis=hzini/time
        videoDisplayBean.setHzinis(String.format("%.3f", new Object[] { (Double.valueOf(videoDisplayBean.getHzini())) / Double.valueOf(videoDisplayBean.getTime()) }));
        // zinis=zini/time
        videoDisplayBean.setZinis(String.format("%.3f", new Object[] { (Double.valueOf(videoDisplayBean.getZini())) / Double.valueOf(videoDisplayBean.getTime()) }));
        // occam=3bvs*ioe
        videoDisplayBean.setOccam(String.format("%.3f", new Object[] { (Double.valueOf(videoDisplayBean.getBbbvs())) * Double.valueOf(videoDisplayBean.getIoe()) }));
        // qg=(time^1.7)/3bv
        videoDisplayBean.setQg(String.format("%.3f", new Object[] { Double.valueOf((Math.pow(Double.valueOf(videoDisplayBean.getTime()), 1.7D)) / Double.valueOf(videoDisplayBean.getBbbv())) }));
    }
}
