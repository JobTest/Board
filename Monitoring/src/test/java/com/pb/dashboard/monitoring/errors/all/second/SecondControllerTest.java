package com.pb.dashboard.monitoring.errors.all.second;

import com.pb.dashboard.dao.entity.biplanepl.ErrorDetail;
import com.pb.dashboard.dao.entity.biplanepl.ErrorDetailI;
import com.pb.dashboard.monitoring.errors.all.strategy.PageType;
import com.pb.dashboard.monitoring.errors.all.table.model.ErrorModel;
import com.pb.dashboard.monitoring.errors.all.table.model.RecipientModel;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class SecondControllerTest {

    private static final String ERROR = "error";
    private static final int FIRST_PANEL = 0;
    private static final int BUTTON_BACK = 0;
    private static final int BUTTON_CODE = 1;

    private SecondModelI model;
    private SecondControllerI controller;
    private ErrorDetailI errorDetail;

    @Before
    public void setUp() throws Exception {
        new ClassPathXmlApplicationContext("monitoring-context.xml");
        controller = new SecondController();
        model = controller.getModel();

        errorDetail = new ErrorDetail();
    }

    @Test
    public void testChangeVisible() throws Exception {
        boolean visible = model.isDescriptionVisible();
        controller.setDescriptionVisible(!visible);
        assertNotSame(visible, model.isDescriptionVisible());
    }

    @Test
    public void testSetCode() throws Exception {
        String code = "code";
        controller.setCode(code);
        assertEquals(code, model.getCode());
    }

    @Test
    public void testListWithoutGeneric() throws Exception {
        List<String> list = new ArrayList<String>();
        list.add("132");
        controller.setList(list, PageType.ERROR);
        assertEquals(Collections.emptyList(), model.getList());
    }

    @Test
    public void testSetListRecipient() throws Exception {
        List<RecipientModel> list = new ArrayList<>();
        list.add(new RecipientModel());
        controller.setList(list, PageType.ERROR);
        assertEquals(list, model.getList());
    }

    @Test
    public void testSetListError() throws Exception {
        List<ErrorModel> list = new ArrayList<>();
        list.add(new ErrorModel());
        controller.setList(list, PageType.ERROR);
        assertEquals(list, model.getList());
    }

    @Test
    public void testSetListEmpty() throws Exception {
        List list = new ArrayList();
        controller.setList(list, PageType.ERROR);
        assertEquals(list, model.getList());
    }

    @Test
    public void testClickCode() throws Exception {
        controller.setList(new ArrayList(), PageType.RECIPIENT);
        Button buttonCode = getButtonCode();
        buttonCode.click();
        assertEquals(errorDetail, model.getErrorDetail());
    }

    @Test
    public void testEnabledButtonBack() throws Exception {
        final boolean[] click = {false};
        controller.addBackClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                click[0] = true;
            }
        });
        Button buttonBack = getButtonBack();
        buttonBack.click();
        assertTrue(click[0]);
    }

    @Test
    public void testCrashBD() throws Exception {
        controller.setCode(ERROR);
        controller.setList(new ArrayList(), PageType.RECIPIENT);
        Button buttonCode = getButtonCode();
        buttonCode.click();
        assertNotNull(model.getErrorDetail());
    }

    private Button getButtonBack() {
        return (Button) getFirstPanel().getComponent(BUTTON_BACK);
    }

    private Button getButtonCode() {
        HorizontalLayout inPanel = (HorizontalLayout) getFirstPanel().getComponent(1);
        return (Button) inPanel.getComponent(BUTTON_CODE);
    }

    private HorizontalLayout getFirstPanel() {
        SecondView view = controller.getView();
        return (HorizontalLayout) view.getComponent(FIRST_PANEL);
    }
}