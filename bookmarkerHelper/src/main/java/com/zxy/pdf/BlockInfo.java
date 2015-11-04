/**
 * Kuaidadi.com Inc.
 * Copyright (c) 2012-2015 All Rights Reserved.
 */
package com.zxy.pdf;

/**
 * 
 * @author zxy
 * @version $Id: BlockInfo.java, v 0.1 2015-10-27 下午6:04:51 Administrator Exp $
 */
public class BlockInfo {
    private StringBuilder text;
    private int           indent;
    private int           end;

    private float         up;
    private float         down;
    private float         minfontSize;

    /**
     * Getter method for property <tt>text</tt>.
     * 
     * @return property value of text
     */
    public StringBuilder getText() {
        return text;
    }

    /**
     * Setter method for property <tt>text</tt>.
     * 
     * @param text value to be assigned to property text
     */
    public void setText(StringBuilder text) {
        this.text = text;
    }

    /**
     * Getter method for property <tt>indent</tt>.
     * 
     * @return property value of indent
     */
    public int getIndent() {
        return indent;
    }

    /**
     * Setter method for property <tt>indent</tt>.
     * 
     * @param indent value to be assigned to property indent
     */
    public void setIndent(int indent) {
        this.indent = indent;
    }

    /**
     * Getter method for property <tt>minfontSize</tt>.
     * 
     * @return property value of minfontSize
     */
    public float getMinfontSize() {
        return minfontSize;
    }

    /**
     * Setter method for property <tt>minfontSize</tt>.
     * 
     * @param minfontSize value to be assigned to property minfontSize
     */
    public void setMinfontSize(float minfontSize) {
        this.minfontSize = minfontSize;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public float getDown() {
        return down;
    }

    public void setDown(float down) {
        this.down = down;
    }

    public float getUp() {
        return up;
    }

    public void setUp(float up) {
        this.up = up;
    }

}
