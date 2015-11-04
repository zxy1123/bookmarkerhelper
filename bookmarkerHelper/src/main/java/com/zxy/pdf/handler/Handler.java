/**
 * Kuaidadi.com Inc.
 * Copyright (c) 2012-2015 All Rights Reserved.
 */
package com.zxy.pdf.handler;

import com.itextpdf.text.pdf.PdfReader;
import com.zxy.pdf.Node;

/**
 * 
 * @author zxy
 * @version $Id: Handler.java, v 0.1 2015-10-26 下午4:48:58 Administrator Exp $
 */
public interface Handler {
    
    
    public Node creatTree(PdfReader reader);
}
