package com.pb.dashboard.monitoring.components.observers;

import com.pb.dashboard.monitoring.components.navigator.ContentItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ObserversTest {

    private Observers<Observer, ContentItem> observers;

    @Before
    public void setUp() throws Exception {
        observers = new Observers<>();
    }

    @After
    public void tearDown() throws Exception {
        observers = null;
    }

    @Test
    public void testNotify() throws Exception {
        final ContentItem[] mod = new ContentItem[2];
        for (int i = 0; i < mod.length; i++) {
            final int finalI = i;
            observers.add(new Observer() {
                @Override
                public void modified(Object obj) {
                    mod[finalI] = (ContentItem) obj;
                }
            });
        }
        ContentItem item = new ContentItem();
        observers.notifyModified(item);
        for (int i = 0; i < mod.length; i++) {
            assertEquals(item, mod[i]);
        }
    }
}