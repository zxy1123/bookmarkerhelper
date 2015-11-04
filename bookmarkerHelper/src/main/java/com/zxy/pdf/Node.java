/**
 * Kuaidadi.com Inc.
 * Copyright (c) 2012-2015 All Rights Reserved.
 */
package com.zxy.pdf;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author zxy
 * @version $Id: Node.java, v 0.1 2015-10-26 下午7:34:00 Administrator Exp $
 */
public class Node {
    private String     title;
    private String     action = "GoTo";
    private int        page;
    private int        indent = -1;
    private List<Node> kids   = new ArrayList<>();

    /**
     * Getter method for property <tt>title</tt>.
     * 
     * @return property value of title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter method for property <tt>title</tt>.
     * 
     * @param title value to be assigned to property title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter method for property <tt>action</tt>.
     * 
     * @return property value of action
     */
    public String getAction() {
        return action;
    }

    /**
     * Setter method for property <tt>action</tt>.
     * 
     * @param action value to be assigned to property action
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * Getter method for property <tt>page</tt>.
     * 
     * @return property value of page
     */
    public int getPage() {
        return page;
    }

    /**
     * Setter method for property <tt>page</tt>.
     * 
     * @param page value to be assigned to property page
     */
    public void setPage(int page) {
        this.page = page;
    }

    public List<Node> getKids() {
        return kids;
    }

    public void setKids(List<Node> kids) {
        this.kids = kids;
    }

    public int getIndent() {
        return indent;
    }

    public void setIndent(int indent) {
        this.indent = indent;
    }

}
