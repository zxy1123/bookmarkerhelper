/**
 * Kuaidadi.com Inc.
 * Copyright (c) 2012-2015 All Rights Reserved.
 */
package com.zxy.pdf.stats;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * thread unsafe
 * @author zxy
 * @version $Id: His.java, v 0.1 2015-10-28 下午3:51:50 Administrator Exp $
 */
public class His<T> {
    private final Map<T, Strip<T>> STRIPS = new HashMap<T, Strip<T>>();

    public void inr(T node) {
        Strip<T> strip = STRIPS.get(node);
        if (strip == null) {
            Strip<T> value = new Strip<T>();
            value.setValue(node);
            value.incr();
            STRIPS.put(node, value);
        } else {
            strip.incr();
        }
    }

    private List<Strip<T>> getMaxMin() {
        List<Strip<T>> ret = new ArrayList<>();
        Collection<Strip<T>> values = STRIPS.values();
        Strip<T> max = null;
        Strip<T> min = null;
        if (values != null&&!values.isEmpty()) {
            max = values.iterator().next();
            min = max;
            for (Strip<T> strip : values) {
                if (strip.getNote() > max.getNote()) {
                    max = strip;
                }
                if (strip.getNote() < min.getNote()) {
                    min = strip;
                }
            }
        }
        ret.add(0, max);
        ret.add(1, min);
        return ret;
    }

    public T getMax() {
        List<Strip<T>> maxMin = getMaxMin();
        Strip<T> strip = maxMin.get(0);
        return strip == null ? null : strip.getValue();
    }
    
    public T getMin() {
        List<Strip<T>> maxMin = getMaxMin();
        Strip<T> strip = maxMin.get(1);
        return strip == null ? null : strip.getValue();
    }
}
