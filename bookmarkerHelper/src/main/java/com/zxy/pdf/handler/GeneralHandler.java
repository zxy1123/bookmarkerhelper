/**
 * Kuaidadi.com Inc.
 * Copyright (c) 2012-2015 All Rights Reserved.
 */
package com.zxy.pdf.handler;

import java.io.IOException;
import java.util.ArrayList;
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
        for (int i = 2; i < numberOfPages; i++) {
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
        pageInfo.setPageLeft(lineLeft);
        pageInfo.setPageRight(lineRight);
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
        List<Line> lines = new ArrayList<>();
        int start = 0;
        for (int i = 1; i < mem.size(); i++) {
            TextInfo next = mem.get(i);
            if (next.getTop() < down) {
                lines.add(new Line(start, i - 1));
                start = i;
            }
            down = mem.get(i).getDown();
        }
        if (lines.size() < 5) {
            return;
        }
        for (int j = 0; j < lines.size(); j++) {
            TextInfo target = getLineTextInfo(mem, lines, j);
            if (!isInPage(pageInfo, mem, lines, j)) {
                continue;
            }

            if (isOutPage(pageInfo, mem, lines, j)) {
                continue;
            }
            if (j < 2) {
                TextInfo next = null;
                TextInfo nextPlus = null;
                TextInfo before = null;
                if (isInPage(pageInfo, mem, lines, j + 1)) {
                    next = getLineTextInfo(mem, lines, j + 1);
                }
                if (isInPage(pageInfo, mem, lines, j + 2)) {
                    nextPlus = getLineTextInfo(mem, lines, j + 2);
                }
                if (isInPage(pageInfo, mem, lines, j - 1)) {
                    before = getLineTextInfo(mem, lines, j - 1);
                }
                if (isDiffSpaceWithNext(target, next, nextPlus) && isBiggerSizeThanNextAndBefore(target, next, before)) {
                    addMarker(pageInfo, markers, lines, mem, j);
                }
            } else if (1 < j && j < lines.size() - 2) {
                TextInfo next = null;
                TextInfo nextPlus = null;
                TextInfo before = null;
                TextInfo beforePlus = null;
                if (isInPage(pageInfo, mem, lines, j + 1)) {
                    next = getLineTextInfo(mem, lines, j + 1);
                }
                if (isInPage(pageInfo, mem, lines, j + 2)) {
                    nextPlus = getLineTextInfo(mem, lines, j + 2);
                }

                if (isInPage(pageInfo, mem, lines, j - 1)) {
                    before = getLineTextInfo(mem, lines, j - 1);
                }
                if (isInPage(pageInfo, mem, lines, j - 2)) {
                    beforePlus = getLineTextInfo(mem, lines, j - 2);
                }
                boolean checkBeforeSpace = isDiffSpaceWithBefore(target, before, beforePlus);
                if (isDiffSpaceWithNext(target, next, nextPlus) && checkBeforeSpace) {
                    addMarker(pageInfo, markers, lines, mem, j);
                }
            } else if (j < lines.size() - 1) {
                TextInfo next = getLineTextInfo(mem, lines, j + 1);
                TextInfo before = getLineTextInfo(mem, lines, j - 1);
                if (isBiggerSizeThanNextAndBefore(target, next, before)) {
                    addMarker(pageInfo, markers, lines, mem, j);
                }
            }

        }
    }

    /**
     * 
     * @param target
     * @param before
     * @param beforePlus
     * @return
     */
    private boolean isDiffSpaceWithBefore(TextInfo target, TextInfo before, TextInfo beforePlus) {
        if (before == null) {
            return true;
        }
        if (beforePlus == null) {
            return false;
        }
        return before.getDown() - target.getTop() > beforePlus.getDown() - before.getTop() + 1;
    }

    /**
     * 
     * @param target
     * @param next
     * @param nextPlus
     * @return
     */
    private boolean isDiffSpaceWithNext(TextInfo target, TextInfo next, TextInfo nextPlus) {
        if (next == null) {
            return true;
        }
        if (nextPlus == null) {
            return false;
        }
        return target.getDown() - next.getTop() > next.getDown() - nextPlus.getTop() + 1;
    }

    /**
     * 
     * @param target
     * @param next
     * @return
     */
    private boolean isBiggerSizeThanNextAndBefore(TextInfo target, TextInfo next, TextInfo before) {
        boolean begigNext = next == null ? true : target.getTop() - target.getDown() > next.getTop() - next.getDown()
                                                                                       + 4;
        boolean begigfore = before == null ? true : target.getTop() - target.getDown() > before.getTop()
                                                                                         - before.getDown() + 4;
        return begigNext && begigfore;
    }

    /**
     * 
     * @param mem
     * @param lines
     * @param j
     */
    private TextInfo getLineTextInfo(List<TextInfo> mem, List<Line> lines, int j) {
        if (j >= lines.size() || j < 0) {
            return null;
        }
        return mem.get(lines.get(j).y);
    }

    /**
     * 
     * @param markers
     * @param lines
     * @param j
     */
    private void addMarker(PageInfo pageInfo, List<Marker> markers, List<Line> lines, List<TextInfo> mem, int j) {
        Line line = lines.get(j);
        StringBuilder sb = new StringBuilder();
        for (int i = line.x; i <= line.y; i++) {
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

    /**
     * 
     * @param pageInfo
     * @param mem
     * @param line
     * @return
     */
    private boolean isOutPage(PageInfo pageInfo, List<TextInfo> mem, List lines, int j) {
        Line line = null;
        if (j < 0 || j >= lines.size()) {
            return true;
        }
        line = (Line) lines.get(j);
        return (mem.get(line.y).getRight() > pageInfo.getPageRight())
               && mem.get(line.x).getLeft() < pageInfo.getPageLeft();
    }

    /**
     * 
     * @param pageInfo
     * @param mem
     * @param line
     * @return
     */
    private boolean isInPage(PageInfo pageInfo, List<TextInfo> mem, List lines, int j) {
        Line line = null;
        if (j < 0 || j >= lines.size()) {
            return false;
        }
        line = (Line) lines.get(j);
        boolean outPage = (mem.get(line.y).getRight() < pageInfo.getPageLeft())
                          || mem.get(line.x).getLeft() > pageInfo.getPageRight();
        return !outPage;
    }

    private static class Line {
        int x;
        int y;

        public Line(int x, int y) {
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
