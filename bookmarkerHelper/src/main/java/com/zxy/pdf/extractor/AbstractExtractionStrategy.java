/**
 * Kuaidadi.com Inc.
 * Copyright (c) 2012-2015 All Rights Reserved.
 */
package com.zxy.pdf.extractor;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.LineSegment;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
import com.itextpdf.text.pdf.parser.Vector;
import com.zxy.pdf.BlockInfo;
import com.zxy.pdf.PageInfo;
import com.zxy.pdf.TextInfo;

/**
 * 
 * @author zxy
 * @version $Id: Ab.java, v 0.1 2015-10-27 下午2:50:12 Administrator Exp $
 */
public abstract class AbstractExtractionStrategy implements TextExtractionStrategy {

    /**  */
    public static final float  STARTSIZE   = 10000f;
    /**  */
    public static final double DIFF        = 1.1;
    /**  */
    public static final float  START       = -10.0f;

    protected float            blineHieght = START;

    private int                lastLineEnd = -1;

    private PageInfo           pageInfo;

    @Override
    public void beginTextBlock() {
    }

    @Override
    public void renderText(TextRenderInfo renderInfo) {
        beforeParse(renderInfo);
        doParseText(renderInfo);
        afterParse(renderInfo);
    }

    /**
     * 
     * @param renderInfo
     */
    private void beforeParse(TextRenderInfo renderInfo) {
        TextInfo textInfo = new TextInfo();
        if (isNewLine(renderInfo)) {
            textInfo.setNewLine(true);
        }
        textInfo.setRenderInfo(renderInfo);
        float left = renderInfo.getBaseline().getStartPoint().get(0);
        float down = renderInfo.getBaseline().getStartPoint().get(1);
        float right = renderInfo.getBaseline().getEndPoint().get(0);
        float top = renderInfo.getAscentLine().getStartPoint().get(1);
        textInfo.setLeft((int) left);
        textInfo.setRight((int) right);
        textInfo.setDown((int) down);
        textInfo.setTop((int) top);
        pageInfo.getTextInfos().add(textInfo);
    }

    /**
     * 
     * @param renderInfo
     */
    private void afterParse(TextRenderInfo renderInfo) {
        lastLineEnd = (int) renderInfo.getBaseline().getEndPoint().get(0);
    }

    /**
     * 
     * @param renderInfo
     */
    abstract void doParseText(TextRenderInfo renderInfo);

    protected boolean isNewLine(TextRenderInfo renderInfo) {
        boolean ret = false;
        LineSegment baseline = renderInfo.getBaseline();
        LineSegment ascentLine = renderInfo.getAscentLine();
        float baseLineHeight = baseline.getStartPoint().get(1);
        float ascentLineHeight = ascentLine.getStartPoint().get(1);
        if (Math.abs(baseLineHeight - blineHieght) > DIFF * Math.abs(ascentLineHeight - baseLineHeight)) {
            if (blineHieght != START) {
                pageInfo.setLineNum(pageInfo.getLineNum() + 1);
                int left = (int) renderInfo.getBaseline().getStartPoint().get(0);
                pageInfo.getLineLeft().inr((float) (left / 10 * 10));
                if (lastLineEnd != -1) {
                    pageInfo.getLineRight().inr((float) ((lastLineEnd / 10) * 10));
                }
                ret = true;
            }
            blineHieght = baseLineHeight;
            return ret;
        }
        return ret;
    }

    protected float getFontSize(TextRenderInfo renderInfo) {
        LineSegment baseline = renderInfo.getBaseline();
        Vector curBaseline = baseline.getStartPoint();
        LineSegment ascentLine = renderInfo.getAscentLine();
        Vector topRight = ascentLine.getEndPoint();
        Rectangle rect = new Rectangle(curBaseline.get(0), curBaseline.get(1), topRight.get(0), topRight.get(1));
        float curFontSize = rect.getHeight();
        return curFontSize;
    }

    @Override
    public void endTextBlock() {
    }

    /**
     * 
     */
    abstract void doParseBlock(BlockInfo blockInfo);

    @Override
    public void renderImage(ImageRenderInfo renderInfo) {
    }

    @Override
    public String getResultantText() {
        return null;
    }

    public float getLastLineEnd() {
        return lastLineEnd;
    }

    public void setLastLineEnd(int lastLineEnd) {
        this.lastLineEnd = lastLineEnd;
    }

    /**
     * Getter method for property <tt>pageInfo</tt>.
     * 
     * @return property value of pageInfo
     */
    public PageInfo getPageInfo() {
        return pageInfo;
    }

    /**
     * Setter method for property <tt>pageInfo</tt>.
     * 
     * @param pageInfo value to be assigned to property pageInfo
     */
    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
}
