package com.pb.dashboard.monitoring.components.navigator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by vlad
 * Date: 21.11.14_14:52
 */
public class NavigatorControllerTest {

    private NavigatorModelI model;
    private NavigatorControllerI controller;

    private List<ContentItem> items1 = Arrays.asList(new ContentItem[]{
            new ContentItem("i1", "v1"),
            new ContentItem("i2", "v2"),
            new ContentItem("i3", "v3")
    });
    private List<ContentItem> items2 = Arrays.asList(new ContentItem[]{
            new ContentItem("i11", "v11"),
            new ContentItem("i22", "v22"),
            new ContentItem("i33", "v33"),
            new ContentItem("i44", "v44")
    });

    @Before
    public void setUp() throws Exception {
        model = new NavigatorModel();
        controller = new NavigatorController(model);
    }

    @After
    public void tearDown() throws Exception {
        model = null;
        controller = null;
    }

    @Test
    public void testGetViewNotNull() throws Exception {
        assertNotNull(controller.getView());
    }

    @Test
    public void testEnabledTrue() throws Exception {
        testEnabled(true);
    }

    @Test
    public void testEnabledFalse() throws Exception {
        testEnabled(false);
    }

    private void testEnabled(boolean enabled) {
        controller.setEnabled(2, enabled);
        NavigatorContent content = model.getContent(2);
        assertEquals(enabled, content.isEnabled());
    }

    @Test
    public void testSetItems() throws Exception {
        controller.setItems(2, items2);
        controller.setItems(3, items1);
        NavigatorContent content = model.getContent(3);
        assertEquals(items1, content.getItems());
    }
}