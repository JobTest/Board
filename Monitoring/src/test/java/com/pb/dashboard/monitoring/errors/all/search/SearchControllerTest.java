package com.pb.dashboard.monitoring.errors.all.search;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SearchControllerTest {

    private SearchControllerI controller;
    private SearchModel model;

    @Before
    public void setUp() throws Exception {
        model = new SearchModel();
        controller = new SearchController(model);
    }

    @Test
    public void testModel() throws Exception {
        assertEquals(model, controller.getModel());
    }

    @Test
    public void testSearchTextValidate() throws Exception {
        model.setSearchText("123456");
        String searchText = "search";
        controller.setSearchText(searchText);
        assertEquals(searchText, model.getText());
    }

    @Test
    public void testSearchTextNotValidateEmpty() throws Exception {
        model.setSearchText("142546");
        controller.setSearchText("");
        assertNotSame("", model.getText());
    }

    @Test
    public void testSearchTextNotValidateNull() throws Exception {
        model.setSearchText("142546");
        controller.setSearchText(null);
        assertNotNull(model.getText());
    }

    @Test
    public void testNotNullView() throws Exception {
        assertNotNull(controller.getView());
    }
}