package com.pb.dashboard.monitoring.components.navigator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class NavigatorModelTest {

    private NavigatorModelI model;
    private NavigatorView view;

    @Before
    public void setUp() throws Exception {
        model = new NavigatorModel();
        view = Mockito.spy(new NavigatorView(null, model));
    }

    @Test
    public void testAddContent() throws Exception {
        model.setContent(1, new ContentItem());
        model.setEnabled(0, true);
        assertEquals(2, model.getCount());
    }

    @Test
    public void testAddObserver() throws Exception {
        ContentItem item = new ContentItem();
        model.setListener(view);
        model.setContent(2, item);
        Mockito.verify(view).setItem(2, item);
    }

    @Test
    public void testDeleteObserver() throws Exception {
        final Boolean[] call = {false};
        model = new NavigatorModel();
        view = new NavigatorView(null, model) {
            @Override
            public void setItem(int navigatorId, ContentItem item) {
                call[0] = true;
            }
        };

        ContentItem item = new ContentItem();
        model.setListener(view);
        model.setListener(null);
        model.setContent(2, item);
        assertFalse(call[0]);
    }

    @Test
    public void testCountLine() throws Exception {
        model.setPointsNewLine();
        model.setPointsNewLine();
//        assertEquals(2, model.currentLine());
    }

    @Test
    public void testGetContent() throws Exception {
        model.setEnabled(2, false);
        NavigatorContent content = model.getContent(2);
        assertNotNull(content);
        assertFalse(content.isEnabled());
        assertNull(content.getItem());
        assertTrue(content.getItems().isEmpty());
    }

    @Test
    public void testGetItem() throws Exception {
        model.setEnabled(2, false);
        ContentItem actual = model.getItem(2);
        ContentItem expected = model.getContent(2).getItem();
        assertEquals(expected, actual);
    }
}