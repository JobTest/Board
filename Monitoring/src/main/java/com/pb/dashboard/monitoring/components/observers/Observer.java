package com.pb.dashboard.monitoring.components.observers;

/**
 * Created by vlad
 * Date: 23.12.14_11:19
 */
public interface Observer<T> {

    void modified(T obj);
}
