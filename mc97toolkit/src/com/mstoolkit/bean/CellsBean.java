package com.mstoolkit.bean;

/**
 * CellsBean
 * 
 * @author zhangye
 * 
 */
public class CellsBean
{
    /** -1 Wall, 0 closed, 1 flagged, 2 marked, 3 opened */
    public int status = 0;
    /** 0~8 the numbers of mines around, 9 mine */
    public int what = 0;
    public String sta = "_";

    public CellsBean(int what)
    {
        status = 0;
        sta = "_";
        this.what = what;
    }
}