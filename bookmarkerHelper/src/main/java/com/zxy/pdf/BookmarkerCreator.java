/**
 * Kuaidadi.com Inc.
 * Copyright (c) 2012-2015 All Rights Reserved.
 */
package com.zxy.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.SimpleBookmark;
import com.zxy.pdf.handler.BookmarkerContext;
import com.zxy.pdf.handler.BookmarkerHandler;
import com.zxy.pdf.handler.Handler;

/**
 * 
 * @author zxy
 * @version $Id: BookmarkerCreator.java, v 0.1 2015-10-26 下午4:48:04 Administrator Exp $
 */
public class BookmarkerCreator {

    private PdfReader                  reader;
    ArrayList<HashMap<String, Object>> outlines ;
    private String                     fname;

    private BookmarkerHandler          bookmarkerHandler;
    /**  */
    private static final String        EXT      = ".pdf";

    public void createBookmarker(String fileName) throws Exception {
        bookmarkerHandler = new BookmarkerHandler(BookmarkerContext.getHandler());
        getFname(fileName);
        getReader(fileName);
        createOutlines();
        creatDestFile(fileName);
    }

    /**
     * 
     */
    @SuppressWarnings("unchecked")
    private void createOutlines() {
        Node root = getRootNode();
        outlines = (ArrayList<HashMap<String, Object>>) (createBookMarkerModel(root).get("Kids"));
    }

    /**
     * 
     * @return
     */
    private Node getRootNode() {
        Handler handler = bookmarkerHandler.getHandler();
        Node root = handler.creatTree(reader);
        return root;
    }

    /**
     * 
     * @param fileName
     */
    private void getFname(String fileName) {
        int indexOf = fileName.indexOf(EXT);
        if (indexOf > 0) {
            fname = fileName.substring(0, indexOf);
        } else {
            fname = fileName;
        }
    }

    /**
     * 
     * @param fileName
     * @throws Exception 
     */
    private void creatDestFile(String fileName) throws Exception {
        PdfStamper stamper;
        try {
            String dest = fname + "2.pdf";
            File destFile = new File(dest);
            destFile.delete();
            if (!destFile.exists()) {
                destFile.createNewFile();
            }
            stamper = new PdfStamper(reader, new FileOutputStream(dest));
            stamper.setOutlines(outlines);
            stamper.close();
        } catch (FileNotFoundException e) {
            System.out.println("can not find file:" + fileName + 2);
            throw e;
        } catch (Exception e) {
            System.out.println("encounter exception");
            throw e;
        }
    }

    /**
     * 
     * @param fileName
     * @throws IOException 
     */
    private void getReader(String fileName) throws IOException {
        try {
            reader = new PdfReader(fileName);
            List<HashMap<String, Object>> bookmark = SimpleBookmark.getBookmark(reader);
            System.out.println(bookmark);
        } catch (IOException e) {
            System.out.println("can not reader file:" + fileName);
            throw e;
        }
    }

    private HashMap<String, Object> createBookMarkerModel(Node root) {
        HashMap<String, Object> kid = new HashMap<String, Object>();
        kid.put("Title", root.getTitle());
        kid.put("Action", root.getAction());
        kid.put("Page", String.format("%d Fit", root.getPage()));
        List<Node> kidNodes = root.getKids();
        if (!kidNodes.isEmpty()) {
            List<HashMap<String, Object>> kids = new ArrayList<>();
            for (Node n : kidNodes) {
                HashMap<String, Object> createKid = createBookMarkerModel(n);
                kids.add(createKid);
            }

            kid.put("Kids", kids);
        }
        return kid;
    }

}
