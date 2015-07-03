package com.pb.dashboard.monitoring.errors.all.first;

import com.pb.dashboard.monitoring.errors.all.search.*;
import com.pb.dashboard.monitoring.errors.all.table.model.ErrorModel;
import com.pb.dashboard.monitoring.errors.all.table.model.RecipientModel;
import com.pb.dashboard.monitoring.errors.all.table.view.ErrorTable;
import com.pb.dashboard.monitoring.errors.all.table.view.RecipientTable;
import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * Created by vlad
 * Date: 17.09.14
 */
public class FirstViewTest {

    private static final int PANEL_SEARCH = 0;
    private static final int PANEL_TABLE = 1;

    private FirstModel model;
    private FirstControllerI controller;
    private FirstView view;

    @Before
    public void tearDown() throws Exception {
        model = new FirstModel();
        controller = new FirstController(model);
        view = new FirstView(controller, model);
    }

    @After
    public void setUp() throws Exception {
        view = null;
        controller = null;
        model = null;
    }

    @Test
    public void testAddSearchView() {
        SearchControllerI searchController = mock(SearchController.class);
        SearchModelI searchModel = mock(SearchModel.class);
        SearchView searchView = new SearchView(searchController, searchModel);
        view.setSearch(searchView);
        Component content = ((Panel) view.getComponent(PANEL_SEARCH)).getContent();
        assertEquals(searchView, content);
    }

    @Test
    public void testSetEmptyList() throws Exception {
        view.setList(new ArrayList());
        testEmptyErrorTable();
    }

    @Test
    public void testSetErrorList() throws Exception {
        List<ErrorModel> list = new ArrayList<>();
        list.add(new ErrorModel());
        view.setList(list);
        Panel panel = (Panel) view.getComponent(PANEL_TABLE);
        ErrorTable table = (ErrorTable) panel.getContent();
        assertEquals(list.size(), table.getItemIds().size());
    }

    @Test
    public void testSetRecipientList() throws Exception {
        List<RecipientModel> list = new ArrayList<>();
        list.add(new RecipientModel());
        view.setList(list);
        Panel panel = (Panel) view.getComponent(PANEL_TABLE);
        RecipientTable table = (RecipientTable) panel.getContent();
        assertEquals(list.size(), table.getItemIds().size());
    }

    @Test
    public void testEmptyErrorTable() {
        Panel panel = (Panel) view.getComponent(PANEL_TABLE);
        ErrorTable table = (ErrorTable) panel.getContent();
        assertEquals(0, table.getItemIds().size());
    }
}
