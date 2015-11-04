/**
 * Kuaidadi.com Inc.
 * Copyright (c) 2012-2015 All Rights Reserved.
 */
package com.zxy.pdf;

import com.zxy.pdf.handler.BookmarkerContext;
import com.zxy.pdf.handler.GeneralHandler;

/**
 * 
 * @author zxy
 * @version $Id: Main.java, v 0.1 2015-10-26 下午4:47:04 Administrator Exp $
 */
public class Main {
    public static void main(String[] args) throws Exception {
        BookmarkerContext.registerHandler(new GeneralHandler());
        new BookmarkerCreator().createBookmarker("Artificial-Intelligence-A-Modern-Approach-3rd-Edition.pdf");
    }
}
