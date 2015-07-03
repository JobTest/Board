package com.pb.dashboard.monitoring.errors.all.search;

import com.vaadin.ui.TextField;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by vlad
 * Date: 11.09.14
 */
public class SearchViewTest {

    private static final String PROMPT = "prompt";
    private static final String SEARCH = "search";
    private SearchView view;
    private SearchControllerI controller;
    private SearchModel model;

    @Before
    public void setUp() throws Exception {
        model = new SearchModel(PROMPT, SEARCH);
        controller = spy(new SearchController(model));
        view = new SearchView(controller, model);
    }

    @After
    public void tearDown() throws Exception {
        view = null;
        model = null;
        controller = null;
    }

    @Test
    public void testInitPrompt() {
        TextField field = view.getField();
        String format = String.format(SearchView.SEARCH_BY_ID_SESSION, PROMPT);
        assertEquals(format, field.getInputPrompt());
    }

    @Test
    public void testInitSearch() throws Exception {
        TextField field = view.getField();
        assertEquals(SEARCH, field.getValue());
    }

    @Test
    public void testChangeModel() throws Exception {
        String searchText = "searchText";
        String changePrompt = "promptNew";
        model.setSearchText(searchText);
        model.setPrompt(changePrompt);
        TextField field = view.getField();
        assertEquals(searchText, field.getValue());
        String format = String.format(SearchView.SEARCH_BY_ID_SESSION, changePrompt);
        assertEquals(format, field.getInputPrompt());
    }

    @Test
    public void testClickButton() throws Exception {
        view.getFind().click();
        verify(controller, times(1)).setSearchText(SEARCH);
    }
}
