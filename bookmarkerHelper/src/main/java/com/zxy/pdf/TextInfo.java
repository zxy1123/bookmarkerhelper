/**
 * Kuaidadi.com Inc.
 * Copyright (c) 2012-2015 All Rights Reserved.
 */
package com.zxy.pdf;

import com.itextpdf.text.pdf.parser.TextRenderInfo;

/**
 * 
 * @author zxy
 * @version $Id: TextInfo.java, v 0.1 2015-10-29 下午7:48:19 Administrator Exp $
 */
public class TextInfo {
   private TextRenderInfo renderInfo;
   
   private int down;
   
   private int top;
   
   private int left;
   
   private int right;
   
   private boolean isNewLine;

/**
 * Getter method for property <tt>renderInfo</tt>.
 * 
 * @return property value of renderInfo
 */
public TextRenderInfo getRenderInfo() {
    return renderInfo;
}

/**
 * Setter method for property <tt>renderInfo</tt>.
 * 
 * @param renderInfo value to be assigned to property renderInfo
 */
public void setRenderInfo(TextRenderInfo renderInfo) {
    this.renderInfo = renderInfo;
}

/**
 * Getter method for property <tt>down</tt>.
 * 
 * @return property value of down
 */
public int getDown() {
    return down;
}

/**
 * Setter method for property <tt>down</tt>.
 * 
 * @param down value to be assigned to property down
 */
public void setDown(int down) {
    this.down = down;
}


/**
 * Getter method for property <tt>left</tt>.
 * 
 * @return property value of left
 */
public int getLeft() {
    return left;
}

/**
 * Setter method for property <tt>left</tt>.
 * 
 * @param left value to be assigned to property left
 */
public void setLeft(int left) {
    this.left = left;
}

/**
 * Getter method for property <tt>right</tt>.
 * 
 * @return property value of right
 */
public int getRight() {
    return right;
}

/**
 * Setter method for property <tt>right</tt>.
 * 
 * @param right value to be assigned to property right
 */
public void setRight(int right) {
    this.right = right;
}

public int getTop() {
    return top;
}

public void setTop(int top) {
    this.top = top;
}

public boolean isNewLine() {
    return isNewLine;
}

public void setNewLine(boolean isNewLine) {
    this.isNewLine = isNewLine;
}
   
   
}
