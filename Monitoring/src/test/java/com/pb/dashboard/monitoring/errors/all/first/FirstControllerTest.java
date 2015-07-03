package com.pb.dashboard.monitoring.errors.all.first;

import com.pb.dashboard.monitoring.errors.all.table.model.ErrorModel;
import com.pb.dashboard.monitoring.errors.all.table.model.RecipientModel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FirstControllerTest {

    private FirstController controller;
    private FirstModel model;

    @Before
    public void setUp() throws Exception {
        model = new FirstModel();
        controller = new FirstController(model);
    }

    @Test
    public void testModel() throws Exception {
        assertEquals(model, controller.getModel());
    }

    @Test
    public void testSetListEmpty() throws Exception {
        List list = new ArrayList();
        controller.setList(list);
        assertEquals(list, model.getList());
    }

    @Test
    public void testSetErrorList() throws Exception {
        List<ErrorModel> list = new ArrayList<>();
        list.add(new ErrorModel());
        controller.setList(list);
        assertEquals(list, model.getList());
    }

    @Test
    public void testSetPromptSession() throws Exception {
        List<ErrorModel> list = new ArrayList<>();
        list.add(new ErrorModel());
        controller.setList(list);
        assertEquals(PromptType.SESSION, model.getPrompt());
    }

    @Test
    public void testSetRecipientList() throws Exception {
        List<RecipientModel> list = new ArrayList<>();
        list.add(new RecipientModel());
        controller.setList(list);
        assertEquals(list, model.getList());
    }

    @Test
    public void testSetPromptCompany() throws Exception {
        List<RecipientModel> list = new ArrayList<>();
        list.add(new RecipientModel());
        controller.setList(list);
        assertEquals(PromptType.COMPANY, model.getPrompt());
    }
}