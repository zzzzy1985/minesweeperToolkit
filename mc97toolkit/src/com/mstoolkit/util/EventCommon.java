package com.mstoolkit.util;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mstoolkit.bean.CellsBean;
import com.mstoolkit.bean.EventBean;
import com.mstoolkit.bean.RawBoardBean;
import com.mstoolkit.bean.RawEventDetailBean;
import com.mstoolkit.bean.RawVideoBean;
import com.mstoolkit.bean.VideoDisplayBean;

/**
 * event解析
 * 
 * @author zhangYe
 * @version 2013-11-3
 */
public class EventCommon
{

    private static Logger logger = LogManager.getLogger("EventCommon");  
    /**
     * 转换录像 这段代码采取的方式是先竖再横？
     * 
     * @param rawVideoBean
     *            录像bean
     * @param videoDisplayBean
     *            录像bean
     * @return EventBean 事件bean
     */
    public static EventBean getEventBean(RawVideoBean rawVideoBean, VideoDisplayBean videoDisplayBean)
    {
        String markFlag = videoDisplayBean.getMarkFlag();
        RawBoardBean rawBoardBean = rawVideoBean.getRawBoardBean();
        int height = rawBoardBean.getHeight();
        int width = rawBoardBean.getWidth();
        List<RawEventDetailBean> eventLst = rawVideoBean.getRawEventDetailBean();
        int ax = 0;
        int ay = 0;
        // 左键状态
        int lstatus = 0;
        // 右键状态
        int rstatus = 0;
        // 中键状态
        int mstatus = 0;
        int mvsize = 0;
        int lcsize = 0;
        int lrsize = 0;
        int rcsize = 0;
        int rrsize = 0;
        int mcsize = 0;
        int mrsize = 0;
        int firstLx = 0;
        int firstLy = 0;

        int misscl = 0;
        int missclL0 = 0;
        int missclL1 = 0;
        int missclD0 = 0;
        int missclD1= 0;
        int missclD2= 0;
        int missclR= 0;
        int outcl = 0;
        int outclL = 0;
        int outclD = 0;
        int cloneR = 0;
        int l = 0;
        int d = 0;
        int r = 0;

        int flags = 0;
        int wastedflags = 0;
        // 计算1.5click
        int holds = 0;
        double path = 0.0d;
        double saoleiTime = 0.0d;
        String mouseTypeNomv = "";
        int eventSize = eventLst.size();
        CellsBean[] cells = rawBoardBean.getCells();
        for (int i = 1; i < (height + 1); i++)
        {
            for (int j = 1; j < (width + 1); j++)
            {
                if (cells[(width + 2) * i + j].what != 9)
                {
                    cells[(width + 2) * i + j].what = (cells[(width + 2) * (i - 1) + j - 1].what == 9 ? 1 : 0) + (cells[(width + 2) * (i - 1) + j].what == 9 ? 1 : 0)
                            + (cells[(width + 2) * (i - 1) + j + 1].what == 9 ? 1 : 0) + (cells[(width + 2) * i + j - 1].what == 9 ? 1 : 0) + (cells[(width + 2) * i + j + 1].what == 9 ? 1 : 0)
                            + (cells[(width + 2) * (i + 1) + j - 1].what == 9 ? 1 : 0) + (cells[(width + 2) * (i + 1) + j].what == 9 ? 1 : 0)
                            + (cells[(width + 2) * (i + 1) + j + 1].what == 9 ? 1 : 0);
                }
            }
        }
        CellsBean[] tempCells = new CellsBean[(height) * (width)];
        for (int i = 0; i < tempCells.length; i++)
        {
            tempCells[i] = new CellsBean(0);
        }
        int tempR = 0;
        // 计算click 和 path
        for (int i = 0; i < eventSize; i++)
        {
            RawEventDetailBean rawEventDetailBean = eventLst.get(i);
            if (rawEventDetailBean.getEventTime() < 0d)
            {
                continue;
            }
            // 为了计算准确的右键数 需要模拟录像操作
            int mouse = rawEventDetailBean.getMouseType();
            // 如果操作造成局面变化 记为有效点击
            //
            int nx = 0;
            int ny = 0;
            int olstatus = 0;
            int orstatus = 0;
            int omstatus = 0;
            nx = rawEventDetailBean.getX();
            ny = rawEventDetailBean.getY();
            boolean boom = false;
            boolean flag = true;
            if ("rr".equals(mouseTypeNomv))
            {
                flag = false;
            }
            int lact = 0;
            int ract = 0;
            int mact = 0;
            if (mouse == 1 && nx == ax && ny == ay)
            {
                continue;
            }
            if (mouse < 0)
            {
                mouse = mouse + 256;
            }
            String mouseType = "";
            switch (mouse) {
                case 1:
                    mouseType = "mv";
                    mvsize++;
                    lact = 0;
                    ract = 0;
                    mact = 0;
                    break;
                case 3:
                    mouseType = "lc";
                    lcsize++;
                    lact = 1;
                    ract = 0;
                    mact = 0;
                    break;
                case 5:
                    mouseType = "lr";
                    lrsize++;
                    lact = -1;
                    ract = 0;
                    mact = 0;
                    break;
                case 9:
                    mouseType = "rc";
                    rcsize++;
                    lact = 0;
                    ract = 1;
                    mact = 0;
                    break;
                case 17:
                    mouseType = "rr";
                    rrsize++;
                    lact = 0;
                    ract = -1;
                    mact = 0;
                    break;
                case 33:
                    mouseType = "mc";
                    mcsize++;
                    lact = 0;
                    ract = 0;
                    mact = 1;
                    break;
                case 65:
                    mouseType = "mr";
                    mrsize++;
                    lact = 0;
                    ract = 0;
                    mact = -1;
                    break;
                case 145:
                    mouseType = "rr";
                    rrsize++;
                    lact = 0;
                    ract = -1;
                    mact = 0;
                    break;
                case 193:
                    mouseType = "mr";
                    mrsize++;
                    lact = 0;
                    ract = 0;
                    mact = -1;
                    break;
                default:
                    mouseType = "rr";
                    rrsize++;
                    lact = 0;
                    ract = -1;
                    mact = 0;
            }
            if (!"mv".equals(mouseType))
            {
                mouseTypeNomv = mouseType;
            }
            lstatus += lact;
            rstatus += ract;
            mstatus += mact;
            olstatus = lstatus - lact;
            orstatus = rstatus - ract;
            omstatus = mstatus - mact;
            if (lact == -1 && orstatus == 0 && flag)
            {
                // 求首次点击位置
                if (l == 0)
                {
                    firstLx = nx;
                    firstLy = ny;
                }
                l++;
                // l++的时候
                int qx = (nx) / 16 + 1;
                int qy = (ny) / 16 + 1;
                if (qx <= width && qy <= height)
                {
                    int what = tempCells[(qy - 1) * width + qx - 1].what;
                    int status = tempCells[(qy - 1) * width + qx - 1].status;
                    if (what == 9)
                    {
                        boom = true;
                    }
                    if (status == 0)
                    {
                        int digboard = 0;
                        digboard +=digg(qx, qy, tempCells, cells, height, width);
                        logger.debug("L0: " + digboard + " " + rawEventDetailBean.getEventTime());
                        if(digboard == 0){
                        missclL0++;
                        }
                    }
                    else
                    {
                        logger.debug("L: "+misscl+" "+rawEventDetailBean.getEventTime() );
                        missclL1++;
                    }
                }
                else
                {
                    outcl++;
                }
            }
            // d
            if (((omstatus == 0) && (lact == -1 ? 1 : 0 + ract == -1 ? 1 : 0) * (olstatus == 1 ? 1 : 0) * (orstatus == 1 ? 1 : 0) > 0) || ((omstatus == 1) && (mact == -1)))
            {
                d++;
                if (tempR == 1)
                {
                    cloneR++;
                    r--;
                    tempR = 0;
                }
                int qx = (nx) / 16 + 1;
                int qy = (ny) / 16 + 1;

                if (qx <= width && qy <= height)
                {
                    int thiswhat = cells[(qy) * (width + 2) + (qx)].what;
                    // 计算点击位置周围一圈雷数
                    if (thiswhat != 0)
                    {
                        int arroundFlag = 0;
                        // 存在左上格 如果不在第一行或第一列
                        if (!((qx == 1) || (qy == 1)))
                        {
                            arroundFlag += (("F".equals(tempCells[(qy - 2) * width + qx - 2].sta)) ? 1 : 0);
                        }
                        // 存在上格 如果不在第一行
                        if (qy != 1)
                        {
                            arroundFlag += (("F".equals(tempCells[(qy - 2) * width + qx - 1].sta)) ? 1 : 0);
                        }
                        // 存在右上格 如果不在第一行或最后一列
                        if (!((qx == width) || (qy == 1)))
                        {
                            arroundFlag += (("F".equals(tempCells[(qy - 2) * width + qx].sta)) ? 1 : 0);
                        }
                        // 存在左格 如果不在第一列
                        if (qx != 1)
                        {
                            arroundFlag += (("F".equals(tempCells[(qy - 1) * width + qx - 2].sta)) ? 1 : 0);
                        }
                        // 存在右格 如果不在最后一列
                        if (qx != width)
                        {
                            arroundFlag += (("F".equals(tempCells[(qy - 1) * width + qx].sta)) ? 1 : 0);
                        }
                        // 存在左下格 如果不在最后一行或最后一列

                        if (!((qx == 1) || (qy == height)))
                        {
                            arroundFlag += (("F".equals(tempCells[(qy) * width + qx - 2].sta)) ? 1 : 0);
                        }
                        // 存在下格 如果不在最后一行
                        if (qy != height)
                        {
                            arroundFlag += (("F".equals(tempCells[(qy) * width + qx - 1].sta)) ? 1 : 0);
                        }
                        // 存在右下格 如果不在最后一行或最后一列
                        if (!((qx == width) || (qy == height)))
                        {
                            arroundFlag += (("F".equals(tempCells[(qy) * width + qx].sta)) ? 1 : 0);
                        }

                        // 计算这个数字是否等于周围一圈雷数
                        if (arroundFlag == thiswhat)
                        {
                            int digboard = 0;
                            digboard += digg(qx - 1, qy - 1, tempCells, cells, height, width);
                            digboard += digg(qx - 1, qy, tempCells, cells, height, width);
                            digboard += digg(qx - 1, qy + 1, tempCells, cells, height, width);
                            digboard += digg(qx, qy - 1, tempCells, cells, height, width);
                            digboard += digg(qx, qy + 1, tempCells, cells, height, width);
                            digboard += digg(qx + 1, qy - 1, tempCells, cells, height, width);
                            digboard += digg(qx + 1, qy, tempCells, cells, height, width);
                            digboard += digg(qx + 1, qy + 1, tempCells, cells, height, width);
                            if(digboard==0){
                                logger.debug("D0: " + misscl + " " + rawEventDetailBean.getEventTime());
                                misscl++;
                            }
                            
                        }
                        else
                        {
                            logger.debug("D1: "+misscl+" "+rawEventDetailBean.getEventTime() );
                            misscl++;
                        }
                    }
                    else
                    {
                        logger.debug("D2: "+misscl+" "+rawEventDetailBean.getEventTime() );
                        misscl++;
                    }
                }
                else
                {
                    outclD++;
                }
            }
            else if (ract == -1)
            {
                tempR = 0;
            }

            if (ract == 1)
            {
                if (olstatus == 0)
                {
                    r++;
                    int qx = (nx) / 16 + 1;
                    int qy = (ny) / 16 + 1;
                    if (qx <= width && qy <= height)
                    {
                        int xx = tempCells[(qy - 1) * width + qx - 1].status;
                        if (xx == 0)
                        {
                            tempR = 0;
                            tempCells[(qy - 1) * width + qx - 1].status = 2;
                            tempCells[(qy - 1) * width + qx - 1].sta = "F";
                        }
                        else if (xx == 2)
                        {
                            if (markFlag.equals("UNMARK"))
                            {
                                wastedflags++;
                                tempR = 0;
                                tempCells[(qy - 1) * width + qx - 1].status = 0;
                                tempCells[(qy - 1) * width + qx - 1].sta = " ";
                            }
                            else
                            {
                                wastedflags++;
                                tempR = 0;
                                tempCells[(qy - 1) * width + qx - 1].status = 4;
                                tempCells[(qy - 1) * width + qx - 1].sta = "?";
                            }
                        }
                        else if (xx == 4)
                        {
                            wastedflags++;
                            tempR = 0;
                            tempCells[(qy - 1) * width + qx - 1].status = 0;
                            tempCells[(qy - 1) * width + qx - 1].sta = " ";
                        }
                        else
                        {
                            tempR = 1;
                            logger.debug("R: "+xx+" "+misscl+" "+rawEventDetailBean.getEventTime() );
                            misscl++;
                        }
                    }
                    else
                    {
                        outcl++;
                    }
                }
                else
                {
                    tempR = 0;
                }
            }
            if ((orstatus == 1 ? 1 : 0) * (lact == 1 ? 1 : 0) > 0)
            {
                holds++;
            }

            if (i > 0 && rawEventDetailBean.getEventTime() > 0d)
            {
                path += Math.sqrt((nx - ax) * (nx - ax) + (ny - ay) * (ny - ay));
            }

            saoleiTime = rawEventDetailBean.getEventTime();
            ax = nx;
            ay = ny;

        }
        for (int i = 0; i < tempCells.length; i++)
        {
            if ("F".equals(tempCells[i].sta))
            {
                flags++;
            }
        }
        EventBean eventBean = new EventBean();
        eventBean.setD(d);
        eventBean.setL(l);
        eventBean.setR(r);
        eventBean.setCloneR(cloneR);
        eventBean.setHolds(holds);
        eventBean.setDistance(path);
        eventBean.setWastedflags(wastedflags);
        eventBean.setFirstlx(firstLx);
        eventBean.setFirstly(firstLy);
        eventBean.setMisscl(misscl);
        eventBean.setMissclL0(missclL0);
        eventBean.setMissclL1(missclL1);
        eventBean.setMissclD0(missclD0);
        eventBean.setMissclD1(missclD1);
        eventBean.setMissclD2(missclD2);
        eventBean.setMissclR(missclR);
        eventBean.setOutcl(outcl);
        eventBean.setFlags(flags);
        eventBean.setSaoleiTime(saoleiTime);
        eventBean.setEventSize(eventSize);
        eventBean.setMvsize(mvsize);
        eventBean.setLcsize(lcsize);
        eventBean.setLrsize(lrsize);
        eventBean.setRcsize(rcsize);
        eventBean.setRrsize(rrsize);
        eventBean.setMrsize(mrsize);
        eventBean.setMcsize(mcsize);
        return eventBean;
    }

    /**
     * digg
     * 
     * @param x2
     *            x2
     * @param y2
     *            y2
     * @param tempCells
     *            tempCells
     * @param cells
     *            cells
     * @param height
     *            height
     * @param width
     *            width
     */
    public static int digg(int x2, int y2, CellsBean[] tempCells, CellsBean[] cells, int height, int width)
    {
        int digBoard=0;
        if ((x2 > 0) && (x2 <= width) && (y2 > 0) && (y2 <= height))
        {
            if (tempCells[(y2 - 1) * width + x2 - 1].status == 0)
            {
                if (cells[(y2) * (width + 2) + x2].what != 9)
                {
                    if (cells[(y2) * (width + 2) + (x2)].what != 0)
                    {
                        digBoard++;
                        tempCells[(y2 - 1) * width + x2 - 1].status = 3;
                        tempCells[(y2 - 1) * width + x2 - 1].sta = String.valueOf(cells[(y2) * (width + 2) + (x2)].what);
                    }
                    else
                    {
                        digBoard++;
                        tempCells[(y2 - 1) * width + x2 - 1].status = 3;
                        tempCells[(y2 - 1) * width + x2 - 1].sta = String.valueOf(cells[(y2) * (width + 2) + (x2)].what);
                        digBoard+=digg(x2 - 1, y2 - 1, tempCells, cells, height, width);
                        digBoard+=digg(x2 - 1, y2, tempCells, cells, height, width);
                        digBoard+=digg(x2 - 1, y2 + 1, tempCells, cells, height, width);
                        digBoard+=digg(x2, y2 - 1, tempCells, cells, height, width);
                        digBoard+=digg(x2, y2 + 1, tempCells, cells, height, width);
                        digBoard+=digg(x2 + 1, y2 - 1, tempCells, cells, height, width);
                        digBoard+=digg(x2 + 1, y2, tempCells, cells, height, width);
                        digBoard+=digg(x2 + 1, y2 + 1, tempCells, cells, height, width);
                    }
                }
            }
        }
        return digBoard;
    }
}
