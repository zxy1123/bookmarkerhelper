/**
 * Kuaidadi.com Inc.
 * Copyright (c) 2012-2015 All Rights Reserved.
 */
package com.zxy.pdf.handler;

/**
 * 
 * @author zxy
 * @version $Id: BookmarkerContext.java, v 0.1 2015-10-26 下午8:06:12 Administrator Exp $
 */
public class BookmarkerHandler {
    private final Handler handler;

    public BookmarkerHandler(Handler handler) {
        if (handler == null) {
            throw new BookmarkerHanlderException("null handler");
        }
        this.handler = handler;
    }

    public Handler getHandler() {
        return handler;
    }

    public static class BookmarkerHanlderException extends RuntimeException {
        /**  */
        private static final long serialVersionUID = 2726392007697399478L;

        public BookmarkerHanlderException() {
            super();
        }

        public BookmarkerHanlderException(String desc) {
            super(desc);
        }
    }

}
