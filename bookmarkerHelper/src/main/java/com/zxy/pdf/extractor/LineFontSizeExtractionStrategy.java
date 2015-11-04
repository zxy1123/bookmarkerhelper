/**
 * Kuaidadi.com Inc.
 * Copyright (c) 2012-2015 All Rights Reserved.
 */
package com.zxy.pdf.extractor;

import java.util.TreeSet;

import com.itextpdf.text.pdf.parser.TextRenderInfo;
import com.zxy.pdf.BlockInfo;

/**
 * 
 * @author zxy
 * @version $Id: Ab.java, v 0.1 2015-10-27 下午2:50:12 Administrator Exp $
 */
public class LineFontSizeExtractionStrategy extends AbstractExtractionStrategy {

    private float                      lineFontSize = STARTSIZE;
    public static final TreeSet<Float> T_SET        = new TreeSet<>();
    public static final TreeSet<Float> INDENT_SET        = new TreeSet<>();

    /** 
     * @see com.zxy.pdf.extractor.AbstractExtractionStrategy#doParseText(com.itextpdf.text.pdf.parser.TextRenderInfo)
     */
    @Override
    void doParseText(TextRenderInfo renderInfo) {
        if (isNewLine(renderInfo)) {
            float indent = renderInfo.getBaseline().getStartPoint().get(0);
            INDENT_SET.add(indent);
            T_SET.add(lineFontSize);
            lineFontSize = STARTSIZE;
        }
        float curFontSize = getFontSize(renderInfo);
        lineFontSize = Math.min(curFontSize, lineFontSize);
    }

    /** 
     * @see com.zxy.pdf.extractor.AbstractExtractionStrategy#doParseBlock(com.zxy.pdf.BlockInfo)
     */
    @Override
    void doParseBlock(BlockInfo blockInfo) {
    }

}
