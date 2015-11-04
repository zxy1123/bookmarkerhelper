/**
 * Kuaidadi.com Inc.
 * Copyright (c) 2012-2015 All Rights Reserved.
 */
package com.zxy.pdf.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.zxy.pdf.Marker;
import com.zxy.pdf.Node;
import com.zxy.pdf.PageInfo;
import com.zxy.pdf.TextInfo;
import com.zxy.pdf.extractor.AbstractExtractionStrategy;
import com.zxy.pdf.extractor.BookmarkersExtractionStrategy;

/**
 * 
 * @author zxy
 * @version $Id: GenneralHandler.java, v 0.1 2015-10-26 下午9:22:58 Administrator Exp $
 */
public class GeneralHandler implements Handler {
    private static final Pattern SPACEP = Pattern.compile(" |-|\\.|\\?|？|\\!|！|：|:|;|；");
    private static final Pattern P      = Pattern.compile("^(\\d+)?\\w+$");
    private static final Pattern P2     = Pattern.compile("^\\d+\\w+$");

    /** 
     * @see com.zxy.pdf.handler.Handler#creatTree()
     */
    @Override
    public Node creatTree(PdfReader reader) {
        Node root = creatTree(getMarkers(reader));
        return root;
    }

    /**
     * 
     * @param markers
     * @param root
     * @param stack
     * @return
     */
    private Node creatTree(List<Marker> markers) {
        Node root = new Node();
        for (Marker marker : markers) {
            Node n = new Node();
            n.setPage(marker.getPage());
            n.setTitle(marker.getTitle());
            n.setIndent(marker.getIndent());
            root.getKids().add(n);
        }
        return root;
    }

    /**
     * 
     * @param markers
     * @param root
     * @param stack
     * @return
     */
    @SuppressWarnings("unused")
    private Node creatTree1(List<Marker> markers) {
        Node root = new Node();
        Stack<Node> stack = new Stack<>();
        stack.add(root);
        for (Marker marker : markers) {
            Node n = new Node();
            n.setPage(marker.getPage());
            n.setTitle(marker.getTitle());
            n.setIndent(marker.getIndent());
            int indent = marker.getIndent();
            Node peek = stack.peek();
            if (peek == null) {
                // should not happen
                root = n;
                stack.add(n);
            }
            while (indent <= peek.getIndent()) {
                stack.pop();
            }
            stack.add(n);
            peek.getKids().add(n);

        }
        return root;
    }

    /** 
     * @see com.zxy.pdf.handler.Handler#getMarkers(com.lowagie.text.pdf.PdfReader)
     */
    private List<Marker> getMarkers(PdfReader reader) {
        List<Marker> markers = new ArrayList<>();
        int numberOfPages = reader.getNumberOfPages();
        BookmarkersExtractionStrategy bookmarkersExtractionStrategy = new BookmarkersExtractionStrategy();
        for (int i = 1; i < numberOfPages; i++) {
            Rectangle pageSize = reader.getPageSize(i);
            bookmarkersExtractionStrategy.setPageSize(pageSize);
            PageInfo pageInfo = new PageInfo(i);
            bookmarkersExtractionStrategy.setPageInfo(pageInfo);
            parse(reader, bookmarkersExtractionStrategy, i);
            addMarker(pageInfo, markers);
        }
        return markers;
    }

    /**
     * 
     * @param pageInfo
     * @param markers
     */
    private void addMarker(PageInfo pageInfo, List<Marker> markers) {
        int lineNum = pageInfo.getLineNum();
        if (lineNum < 5) {
            return;
        }
        Float lineLeft = pageInfo.getLineLeft().getMax();
        Float lineRight = pageInfo.getLineRight().getMax();
        List<TextInfo> textInfos = pageInfo.getTextInfos();
        fundTitle(pageInfo, textInfos, lineLeft, lineRight, markers);
    }

    /**
     * 
     * @param mem
     */
    private void fundTitle(PageInfo pageInfo, List<TextInfo> mem, Float pageLeft, Float pageRight, List<Marker> markers) {
        if (mem.size() == 1) {
            return;
        }
        int down = mem.get(0).getDown();
        LinkedList<Pair> pairs = new LinkedList<>();
        int start = 0;
        for (int i = 1; i < mem.size(); i++) {
            TextInfo next = mem.get(i);
            if (next.getTop() < down) {
                pairs.add(new Pair(start, i - 1));
                start = i;
            }
        }
        for (int j = 0; j < pairs.size(); j++) {
            if (mem.get(pairs.get(j).y).getRight() < pageRight - 100) {
                if (j > 2 && j < pairs.size() - 2) {
                    int beforeBeforeDown = mem.get(pairs.get(j - 2).y).getDown();
                    int beforeTop = mem.get(pairs.get(j - 1).y).getTop();
                    int beforeDown = mem.get(pairs.get(j - 1).y).getDown();
                    int currentTop = mem.get(pairs.get(j).y).getTop();
                    if (Math.abs(beforeBeforeDown - beforeTop) < Math.abs(beforeDown - currentTop) + 4) {
                        return;
                    }
                }
                if (j == 0) {
                    if (mem.get(pairs.get(j + 1).y).getRight() > pageRight - 80) {
                        addMarker(pageInfo, markers, pairs, mem, j);
                    }
                } else if (j < pairs.size() - 1) {
                    if ((mem.get(pairs.get(j + 1).y).getRight() > pageRight - 80)
                        && (mem.get(pairs.get(j - 1).y).getRight() > pageRight - 80)) {
                        addMarker(pageInfo, markers, pairs, mem, j);
                    }
                }
            }
        }
    }

    /**
     * 
     * @param markers
     * @param pairs
     * @param j
     */
    private void addMarker(PageInfo pageInfo, List<Marker> markers, LinkedList<Pair> pairs, List<TextInfo> mem, int j) {
        Pair pair = pairs.get(j);
        StringBuilder sb = new StringBuilder();
        for (int i = pair.x; i <= pair.y; i++) {
            sb.append(mem.get(i).getRenderInfo().getText());
        }
        String b = sb.toString();
        String withoutSpace = SPACEP.matcher(b).replaceAll("");
        if (!P.matcher(withoutSpace).matches() || withoutSpace.length() < 6) {
            return;
        }
        Marker e = new Marker();
        e.setIndent(0);
        e.setTitle(b);
        e.setPage(pageInfo.getPageNum());
        markers.add(e);
    }

    private static class Pair {
        int x;
        int y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * 
     * @param reader
     * @param semTextExtractionStrategy
     * @param i
     * @throws IOException
     */
    private void parse(PdfReader reader, AbstractExtractionStrategy semTextExtractionStrategy, int i) {
        try {
            PdfTextExtractor.getTextFromPage(reader, i, semTextExtractionStrategy);
        } catch (IOException e) {
            throw new ParseException(e);
        }
    }

    public static class ParseException extends RuntimeException {

        /**  */
        private static final long serialVersionUID = 3619309598295353856L;

        public ParseException() {
            super();
        }

        public ParseException(String msg) {
            super(msg);
        }

        public ParseException(Throwable e) {
            super(e);
        }

        public ParseException(String msg, Throwable e) {
            super(msg, e);
        }
    }

}
