package com.pb.dashboard.monitoring.errors.all.window.complex;

import com.pb.dashboard.core.model.Complex;
import com.pb.dashboard.dao.service.MonitoringServiceMock;
import com.pb.dashboard.monitoring.errors.all.db.mongo.ErrorsDBManagerAbs;
import com.pb.dashboard.monitoring.errors.all.db.mongo.TimingLevel;
import com.pb.dashboard.monitoring.errors.all.table.SessionSessionsTable;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import org.junit.*;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.vaadin.aceeditor.AceEditor;

import java.util.Collection;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by vlad
 * Date: 25.09.14
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({ErrorsDBManagerAbs.class})
public class ComplexControllerTest {

    private static final String QUERY = "query";
    private static final int FIRST_ELEMENT = 0;

    private static ErrorsDBManagerAbs errorsManager;
    private ComplexControllerI controller;

    @BeforeClass
    public static void setUpTests() throws Exception {
        new ClassPathXmlApplicationContext("/monitoring-context.xml");
        mockErrorsManager();
    }

    private static void mockErrorsManager() throws Exception {
        errorsManager = mock(ErrorsDBManagerAbs.class);
        when(errorsManager.getResultBySession(TimingLevel.QUERY, Complex.DEBT, MonitoringServiceMock.SESSION_ID)).thenReturn(QUERY);
    }

    @AfterClass
    public static void tearDownTests() throws Exception {
        errorsManager = null;
    }

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(ErrorsDBManagerAbs.class);
        PowerMockito.when(ErrorsDBManagerAbs.getInstance()).thenReturn(errorsManager);
    }

    @After
    public void tearDown() throws Exception {
        controller = null;
    }

    @Test
    public void testCountSessionId() throws Exception {
        controller = new ComplexController(Complex.BIPLANE_API2X, Complex.DEBT, MonitoringServiceMock.SESSION_ID);
        controller.update();

        Collection<?> itemIds = controller.getView().getTable().getItemIds();
        assertEquals(2, itemIds.size());
    }

    @Test
    public void testCountInfo() throws Exception {
        controller = new ComplexController(Complex.BIPLANE_API2X, Complex.DEBT, MonitoringServiceMock.SESSION_ID);
        controller.update();

        SessionSessionsTable tableSession = controller.getView().getTable();
        Table t1 = (Table) tableSession.getItem("111").getItemProperty(SessionSessionsTable.INFO).getValue();
        Table t2 = (Table) tableSession.getItem("12345").getItemProperty(SessionSessionsTable.INFO).getValue();
        assertEquals(2, t1.size());
        assertEquals(4, t2.size());
    }

    @Test
    public void testLoadFirstTabForDebt() throws Exception {
        controller = new ComplexController(Complex.DEBT, Complex.DEBT, MonitoringServiceMock.SESSION_ID);
        controller.update();
        ComplexView view = controller.getView();

        VerticalLayout component = (VerticalLayout) view.getTabs().getSelectedTab();
        AceEditor firstElement = (AceEditor) component.getComponent(FIRST_ELEMENT);

        assertEquals(QUERY, firstElement.getValue());
    }

    @Test
    public void testNotHaveTable() throws Exception {
        controller = new ComplexController(Complex.DEBT, Complex.DEBT, MonitoringServiceMock.SESSION_ID);
        controller.update();
        ComplexView view = controller.getView();

        assertNull(view.getTable());
    }

    @Test
    public void testHaveTable() throws Exception {
        controller = new ComplexController(Complex.DEBT, Complex.TEMPLATES, MonitoringServiceMock.SESSION_ID);
        controller.update();
        ComplexView view = controller.getView();

        assertNotNull(view.getTable());
    }
}
