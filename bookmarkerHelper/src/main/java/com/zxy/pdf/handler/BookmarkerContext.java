/**
 * Kuaidadi.com Inc.
 * Copyright (c) 2012-2015 All Rights Reserved.
 */
package com.zxy.pdf.handler;

/**
 * 
 * @author zxy
 * @version $Id: BookmarkerContext.java, v 0.1 2015-10-26 下午8:12:57 Administrator Exp $
 */
public class BookmarkerContext {
    private static Handler handler;

    public static void registerHandler(Handler handler) {
        BookmarkerContext.handler = handler;
    }

    public static void clear() {
        BookmarkerContext.handler = null;
    }

    public static Handler getHandler() {
        return handler;
    }
}
