/**
 * Kuaidadi.com Inc.
 * Copyright (c) 2012-2015 All Rights Reserved.
 */
package com.zxy.pdf.extractor;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
import com.zxy.pdf.BlockInfo;

/**
 * 
 * @author zxy
 * @version $Id: Bookmarker.java, v 0.1 2015-10-27 下午3:24:55 Administrator Exp $
 */
public class BookmarkersExtractionStrategy extends AbstractExtractionStrategy {

   
    private Rectangle            pageSize;


    public BookmarkersExtractionStrategy() {

    }

    /** 
     * @see com.zxy.pdf.extractor.AbstractExtractionStrategy#doParseText(com.itextpdf.text.pdf.parser.TextRenderInfo)
     */
    @Override
    void doParseText(TextRenderInfo renderInfo) {
    }




    /** 
     * @see com.zxy.pdf.extractor.AbstractExtractionStrategy#doParseBlock(com.zxy.pdf.BlockInfo)
     */
    @Override
    void doParseBlock(BlockInfo blockInfo) {
    }

    public Rectangle getPageSize() {
        return pageSize;
    }

    public void setPageSize(Rectangle pageSize) {
        this.pageSize = pageSize;
    }


}
