/**
 * Kuaidadi.com Inc.
 * Copyright (c) 2012-2015 All Rights Reserved.
 */
package com.zxy.pdf;

import java.util.ArrayList;
import java.util.List;

import com.zxy.pdf.stats.His;

/**
 * 
 * @author zxy
 * @version $Id: PageInfo.java, v 0.1 2015-10-27 下午8:49:29 Administrator Exp $
 */
public class PageInfo {
//    private final List<BlockInfo> blockInfos = new ArrayList<>();
    
    private final List<TextInfo> textInfos = new ArrayList<>(); 

    private final int             pageNum;
    private int                   lineNum;

    private float                 pageLeft;

    private float                 pageRight;

    private float                 pageUp;

    private float                 pageDown;

    private final His<Float>      lineLeft   = new His<>();

    private final His<Float>      lineRight  = new His<>();

    public PageInfo(int pageNum) {
        this.pageNum = pageNum;
    }


    /**
     * Getter method for property <tt>lineNum</tt>.
     * 
     * @return property value of lineNum
     */
    public int getLineNum() {
        return lineNum;
    }

    /**
     * Setter method for property <tt>lineNum</tt>.
     * 
     * @param lineNum value to be assigned to property lineNum
     */
    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }

    /**
     * Getter method for property <tt>pageLeft</tt>.
     * 
     * @return property value of pageLeft
     */
    public float getPageLeft() {
        return pageLeft;
    }

    /**
     * Setter method for property <tt>pageLeft</tt>.
     * 
     * @param pageLeft value to be assigned to property pageLeft
     */
    public void setPageLeft(float pageLeft) {
        this.pageLeft = pageLeft;
    }

    /**
     * Getter method for property <tt>pageRight</tt>.
     * 
     * @return property value of pageRight
     */
    public float getPageRight() {
        return pageRight;
    }

    /**
     * Setter method for property <tt>pageRight</tt>.
     * 
     * @param pageRight value to be assigned to property pageRight
     */
    public void setPageRight(float pageRight) {
        this.pageRight = pageRight;
    }

    /**
     * Getter method for property <tt>pageUp</tt>.
     * 
     * @return property value of pageUp
     */
    public float getPageUp() {
        return pageUp;
    }

    /**
     * Setter method for property <tt>pageUp</tt>.
     * 
     * @param pageUp value to be assigned to property pageUp
     */
    public void setPageUp(float pageUp) {
        this.pageUp = pageUp;
    }

    /**
     * Getter method for property <tt>pageDown</tt>.
     * 
     * @return property value of pageDown
     */
    public float getPageDown() {
        return pageDown;
    }

    /**
     * Setter method for property <tt>pageDown</tt>.
     * 
     * @param pageDown value to be assigned to property pageDown
     */
    public void setPageDown(float pageDown) {
        this.pageDown = pageDown;
    }

    public His<Float> getLineLeft() {
        return lineLeft;
    }

    public His<Float> getLineRight() {
        return lineRight;
    }

    public int getPageNum() {
        return pageNum;
    }


    public List<TextInfo> getTextInfos() {
        return textInfos;
    }

}
