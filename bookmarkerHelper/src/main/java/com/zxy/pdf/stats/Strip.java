/**
 * Kuaidadi.com Inc.
 * Copyright (c) 2012-2015 All Rights Reserved.
 */
package com.zxy.pdf.stats;

/**
 * thread unsafe
 * @author zxy
 * @version $Id: Strip.java, v 0.1 2015-10-28 下午3:52:19 Administrator Exp $
 */
public class Strip<T> {
    private int note;

    private T   value;

    /**
     * Getter method for property <tt>note</tt>.
     * 
     * @return property value of note
     */
    public int getNote() {
        return note;
    }

    /**
     * Setter method for property <tt>note</tt>.
     * 
     * @param note value to be assigned to property note
     */
    public void setNote(int note) {
        this.note = note;
    }

    public void incr() {
        this.note++;
    }

    public void decr() {
        this.note--;
    }

    /**
     * Getter method for property <tt>value</tt>.
     * 
     * @return property value of Value
     */
    public T getValue() {
        return value;
    }

    /**
     * Setter method for property <tt>value</tt>.
     * 
     * @param value value to be assigned to property value
     */
    public void setValue(T value) {
        this.value = value;
    }

    public String toString() {
        return new String(value + " : " + note);
    }

}
