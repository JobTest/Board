package com.pb.dashboard.monitoring.components.observers;

import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 * Created by vlad
 * Date: 23.12.14_11:21
 */
public class Observers<T extends Observer, D> extends LinkedHashSet<T> {

    public void notifyModified(D obj) {
        for (Iterator<T> iter = iterator(); iter.hasNext(); ) {
            iter.next().modified(obj);
        }
    }
}