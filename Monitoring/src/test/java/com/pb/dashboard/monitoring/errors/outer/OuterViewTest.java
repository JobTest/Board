package com.pb.dashboard.monitoring.errors.outer;

import com.pb.dashboard.core.error.DashSQLException;
import com.pb.dashboard.monitoring.errors.all.db.mongo.ErrorsDBManagerAbs;
import com.pb.dashboard.monitoring.errors.outer.table.OuterSessionData;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ErrorsDBManagerAbs.class})
public class OuterViewTest {

    private static final String SESSION_ID_EMPTY = "session_id_empty";
    private static final int YEAR = 2014;
    private static final int MONTH = 10;
    private static final int JAVA_MONTH = 9;
    private static final int DAY = 10;
    private static final String CLICKED = "well_done";
    private static final int FIRST_ELEMENT = 0;
    private static final int SECOND_ELEMENT = 1;
    private static final int THIRD_ELEMENT = 2;

    private static Date date;
    private static OuterView view;
    private static ErrorsDBManagerAbs errorsDBManager;
    private static OuterController controller;
    private static List<OuterSessionData> list;
    private static OuterSessionData data;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(ErrorsDBManagerAbs.class);
        PowerMockito.when(ErrorsDBManagerAbs.getInstance()).thenReturn(errorsDBManager);
        view = new OuterView();
        controller = new OuterController();
    }

    @BeforeClass
    public static void globalSetup() throws Exception {
        list = new ArrayList<OuterSessionData>();
        data = new OuterSessionData("sid", "inm", "str", "dur", "stat", "comp", "errc");
        list.add(data.clone());
        list.add(data.clone());
        list.add(data.clone());
        Calendar calendar = Calendar.getInstance();
        calendar.set(YEAR, MONTH, DAY);
        date = calendar.getTime();
        mockIQDBManager();
    }

    public static void mockIQDBManager() throws DashSQLException {
        errorsDBManager = mock(ErrorsDBManagerAbs.class);
        when(errorsDBManager.getDataByOuterSession(SESSION_ID_EMPTY, date)).thenReturn(list);
    }

    @After
    public void tearDown() {
        view = null;
    }

    @Test
    public void testTextField() {
        String value = "620272f8263cdea4f4bf074e4f358bd3";
        view.setSearchValue(value);
        assertEquals(value, view.getSearchValue());
    }

    @Test
    public void testTableVisible() {
        Component table = view.getComponent(SECOND_ELEMENT);
        assertFalse(table.isVisible());
    }

    @Test
    public void testVisibleAfterAction() {
        List<OuterSessionData> sessionList = controller.getSessionList(SESSION_ID_EMPTY, date);
        OuterView view = controller.getView();
        view.setSessions(sessionList);
        Component table = view.getComponent(SECOND_ELEMENT);
        assertTrue(table.isVisible());
    }

    @Test
    public void testButtonListener() {
        final String[] buttonClick = new String[1];
        final Button button = (Button) ((HorizontalLayout) view.getComponent(FIRST_ELEMENT)).getComponent(THIRD_ELEMENT);
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                buttonClick[0] = CLICKED;
            }
        });
        assertEquals(null, buttonClick[0]);
        button.click();
        assertEquals(CLICKED, buttonClick[0]);
    }
}