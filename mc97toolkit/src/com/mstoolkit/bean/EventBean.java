package com.mstoolkit.bean;

import java.io.Serializable;

/**
 * event相关BEAN
 * 
 * @author zhangYe
 * 
 */
public class EventBean implements Serializable
{
    /**
     * UID
     */
    private static final long serialVersionUID = -2730987152467866530L;
    /** 左键数 */
    private int l;
    /** 双键数 */
    private int d;
    /** 右键数 */
    private int r;
    /** clone右键数 */
    private int cloneR;
    /** holds数 */
    private int holds;
    /** 标旗数 */
    private int flags;
    /** firstlx*/
    private int firstlx;
    /** firstly */
    private int firstly;
    /** wasted标旗数 */
    private int wastedflags;
    /** 事件数 */
    private int eventSize;
    /** 事件数 */
    private int mvsize;
    /** 事件数 */
    private int lcsize;
    /** 事件数 */
    private int lrsize;
    /** 事件数 */
    private int mcsize;
    /** 事件数 */
    private int mrsize;
    /** 事件数 */
    private int rcsize;
    /** 事件数 */
    private int rrsize;
   
    /** 距离 */
    private double distance;
    /** 用时 */
    private double saoleiTime;

    public int getL()
    {
        return l;
    }

    public void setL(int l)
    {
        this.l = l;
    }

    public int getD()
    {
        return d;
    }

    public void setD(int d)
    {
        this.d = d;
    }

    public int getR()
    {
        return r;
    }

    public void setR(int r)
    {
        this.r = r;
    }

    public int getHolds()
    {
        return holds;
    }

    public void setHolds(int holds)
    {
        this.holds = holds;
    }

    public double getDistance()
    {
        return distance;
    }

    public void setDistance(double distance)
    {
        this.distance = distance;
    }

    public double getSaoleiTime()
    {
        return saoleiTime;
    }

    public void setSaoleiTime(double saoleiTime)
    {
        this.saoleiTime = saoleiTime;
    }

    public int getFlags()
    {
        return flags;
    }

    public void setFlags(int flags)
    {
        this.flags = flags;
    }

    public int getEventSize()
    {
        return eventSize;
    }

    public void setEventSize(int eventSize)
    {
        this.eventSize = eventSize;
    }

    public int getCloneR()
    {
        return cloneR;
    }

    public void setCloneR(int cloneR)
    {
        this.cloneR = cloneR;
    }

    public int getMvsize()
    {
        return mvsize;
    }

    public void setMvsize(int mvsize)
    {
        this.mvsize = mvsize;
    }

    public int getLcsize()
    {
        return lcsize;
    }

    public void setLcsize(int lcsize)
    {
        this.lcsize = lcsize;
    }

    public int getLrsize()
    {
        return lrsize;
    }

    public void setLrsize(int lrsize)
    {
        this.lrsize = lrsize;
    }

    public int getMcsize()
    {
        return mcsize;
    }

    public void setMcsize(int mcsize)
    {
        this.mcsize = mcsize;
    }

    public int getMrsize()
    {
        return mrsize;
    }

    public void setMrsize(int mrsize)
    {
        this.mrsize = mrsize;
    }

    public int getRcsize()
    {
        return rcsize;
    }

    public void setRcsize(int rcsize)
    {
        this.rcsize = rcsize;
    }

    public int getRrsize()
    {
        return rrsize;
    }

    public void setRrsize(int rrsize)
    {
        this.rrsize = rrsize;
    }

    public int getWastedflags()
    {
        return wastedflags;
    }

    public void setWastedflags(int wastedflags)
    {
        this.wastedflags = wastedflags;
    }

    public int getFirstlx()
    {
        return firstlx;
    }

    public void setFirstlx(int firstlx)
    {
        this.firstlx = firstlx;
    }

    public int getFirstly()
    {
        return firstly;
    }

    public void setFirstly(int firstly)
    {
        this.firstly = firstly;
    }

}