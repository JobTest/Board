package com.pb.dashboard.monitoring.errors.all.window.complex.tabs;

import com.pb.dashboard.core.error.DashSQLException;
import com.pb.dashboard.core.error.ErrorCode;
import com.pb.dashboard.core.model.Complex;
import com.pb.dashboard.monitoring.errors.all.db.container.SessionInfo;
import com.pb.dashboard.monitoring.errors.all.db.container.SessionStackTraceData;
import com.pb.dashboard.monitoring.errors.all.db.mongo.ErrorsDBManagerAbs;
import com.pb.dashboard.monitoring.errors.all.db.mongo.TimingLevel;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import org.junit.*;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.vaadin.aceeditor.AceEditor;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by vlad
 * Date: 30.09.14
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({ErrorsDBManagerAbs.class})
public class TabsControllerTest {

    private static final String QUERY_RESULT = "QueryText";
    private static final String ANSWER_RESULT = "AnswerText";
    private static final String SESSION_ID = "session_id";
    private static final String SESSION_ID_EMPTY = "session_id_empty";
    private static final String SESSION_ID_EXC = "session_id_exc";
    private static final String EMPTY_STRING = "";
    private static final int FIRST_ELEMENT = 0;

    private TabsControllerI controller;
    private static ErrorsDBManagerAbs errorsManager;

    @BeforeClass
    public static void setUpTests() throws Exception {
        new ClassPathXmlApplicationContext("monitoring-context.xml");
        mockErrorsManager();
    }

    private static void mockErrorsManager() throws DashSQLException {
        errorsManager = mock(ErrorsDBManagerAbs.class);

        SessionInfo info = new SessionInfo();
        when(errorsManager.getSessionInfo(Complex.BIPLANE_API2X, SESSION_ID_EMPTY)).thenReturn(info.clone());
        info.setBpInterface("bpInterface");
        info.setDuration("duration");
        info.setStartTimestamp("startTimestamp");
        when(errorsManager.getSessionInfo(Complex.BIPLANE_API2X, SESSION_ID)).thenReturn(info.clone());
        when(errorsManager.getSessionInfo(Complex.BIPLANE_API2X, SESSION_ID_EXC)).thenThrow(new DashSQLException(ErrorCode.SQL.ERROR_EXECUTE));


        ArrayList<SessionStackTraceData> list = new ArrayList<>();
        when(errorsManager.getStackTraces(Complex.BIPLANE_API2X, SESSION_ID_EMPTY)).thenReturn(new ArrayList<>(list));
        list.add(new SessionStackTraceData("trace1"));
        list.add(new SessionStackTraceData("trace2"));
        when(errorsManager.getStackTraces(Complex.BIPLANE_API2X, SESSION_ID)).thenReturn(new ArrayList<>(list));
        when(errorsManager.getStackTraces(Complex.BIPLANE_API2X, SESSION_ID_EXC)).thenThrow(new DashSQLException(ErrorCode.SQL.ERROR_EXECUTE));

        when(errorsManager.getResultBySession(TimingLevel.ANSWER, Complex.DEBT, "session_debt")).thenReturn("message_debt");

        when(errorsManager.getResultBySession(TimingLevel.QUERY, Complex.BIPLANE_API2X, SESSION_ID)).thenReturn(QUERY_RESULT);
        when(errorsManager.getResultBySession(TimingLevel.QUERY, Complex.BIPLANE_API2X, SESSION_ID_EMPTY)).thenReturn(EMPTY_STRING);
        when(errorsManager.getResultBySession(TimingLevel.QUERY, Complex.BIPLANE_API2X, SESSION_ID_EXC)).thenThrow(new DashSQLException(ErrorCode.SQL.ERROR_EXECUTE));
        when(errorsManager.getResultBySession(TimingLevel.ANSWER, Complex.BIPLANE_API2X, SESSION_ID)).thenReturn(ANSWER_RESULT);
        when(errorsManager.getResultBySession(TimingLevel.ANSWER, Complex.BIPLANE_API2X, SESSION_ID_EMPTY)).thenReturn(EMPTY_STRING);
        when(errorsManager.getResultBySession(TimingLevel.ANSWER, Complex.BIPLANE_API2X, SESSION_ID_EXC)).thenThrow(new DashSQLException(ErrorCode.SQL.ERROR_EXECUTE));
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
    public void testLoadQuery() throws Exception {
        controller = new TabsController(Complex.BIPLANE_API2X);
        controller.setSessionId(SESSION_ID);

        String text = getSelectedAceEditorFirst().getValue();
        assertEquals(QUERY_RESULT, text);
        assertEquals(TabType.QUERY.getName(), getSelectedTab().getCaption());
    }

    @Test
    public void testLoadQueryEmpty() throws Exception {
        controller = new TabsController(Complex.BIPLANE_API2X);
        controller.setSessionId(SESSION_ID_EMPTY);

        String text = getSelectedAceEditorFirst().getValue();
        assertEquals(ErrorCode.SQL.QUERY_DOES_NOT_RETURN_ANYTHING.getMessage(), text);
    }

    @Test
    public void testLoadQueryExc() throws Exception {
        controller = new TabsController(Complex.BIPLANE_API2X);
        controller.setSessionId(SESSION_ID_EXC);

        String text = getSelectedAceEditorFirst().getValue();
        assertEquals(ErrorCode.SQL.ERROR_EXECUTE.getMessage(), text);
    }

    @Test
    public void testLoadAnswer() throws Exception {
        controller = new TabsController(Complex.BIPLANE_API2X);
        controller.setSessionId(SESSION_ID);
        setSelectedTab(TabType.ANSWER);

        String text = getSelectedAceEditorFirst().getValue();
        assertEquals(ANSWER_RESULT, text);
        assertEquals(TabType.ANSWER.getName(), getSelectedTab().getCaption());
    }

    @Test
    public void testLoadAnswerEmpty() throws Exception {
        controller = new TabsController(Complex.BIPLANE_API2X);
        controller.setSessionId(SESSION_ID_EMPTY);
        setSelectedTab(TabType.ANSWER);

        String text = getSelectedAceEditorFirst().getValue();
        assertEquals(ErrorCode.SQL.QUERY_DOES_NOT_RETURN_ANYTHING.getMessage(), text);
    }

    @Test
    public void testLoadAnswerExc() throws Exception {
        controller = new TabsController(Complex.BIPLANE_API2X);
        controller.setSessionId(SESSION_ID_EXC);
        setSelectedTab(TabType.ANSWER);

        String text = getSelectedAceEditorFirst().getValue();
        assertEquals(ErrorCode.SQL.ERROR_EXECUTE.getMessage(), text);
    }

    @Test
    public void testLoadStackTrace() throws Exception {
        controller = new TabsController(Complex.BIPLANE_API2X);
        controller.setSessionId(SESSION_ID);
        setSelectedTab(TabType.STACK_TRACE);

        AceEditor editor1 = (AceEditor) ((VerticalLayout) ((VerticalLayout) getSelectedTab().getComponent()).getComponent(0)).getComponent(0);
        AceEditor editor2 = (AceEditor) ((VerticalLayout) ((VerticalLayout) getSelectedTab().getComponent()).getComponent(0)).getComponent(1);
        assertEquals("trace1", editor1.getValue());
        assertEquals("trace2", editor2.getValue());
        assertEquals(TabType.STACK_TRACE.getName(), getSelectedTab().getCaption());
    }

    @Test
    public void testLoadStackTraceEmpty() throws Exception {
        controller = new TabsController(Complex.BIPLANE_API2X);
        controller.setSessionId(SESSION_ID_EMPTY);
        setSelectedTab(TabType.STACK_TRACE);

        String text = getSelectedAceEditorFirst().getValue();
        assertEquals(ErrorCode.SQL.QUERY_DOES_NOT_RETURN_ANYTHING.getMessage(), text);
    }

    @Test
    public void testLoadStackTraceExc() throws Exception {
        controller = new TabsController(Complex.BIPLANE_API2X);
        controller.setSessionId(SESSION_ID_EXC);
        setSelectedTab(TabType.STACK_TRACE);

        String text = getSelectedAceEditorFirst().getValue();
        assertEquals(ErrorCode.SQL.ERROR_EXECUTE.getMessage(), text);
    }

    @Test
    public void testLoadErrorsEmpty() throws Exception {
        controller = new TabsController(Complex.BIPLANE_API2X);
        controller.setSessionId(SESSION_ID_EMPTY);
        setSelectedTab(TabType.ERRORS);

        String text = getSelectedAceEditorFirst().getValue();
        assertEquals(ErrorCode.SQL.QUERY_DOES_NOT_RETURN_ANYTHING.getMessage(), text);
        assertEquals(TabType.ERRORS.getName(), getSelectedTab().getCaption());
    }

    @Test
    public void testLoadErrorsExc() throws Exception {
        controller = new TabsController(Complex.BIPLANE_API2X);
        controller.setSessionId(SESSION_ID_EXC);
        setSelectedTab(TabType.ERRORS);

        String text = getSelectedAceEditorFirst().getValue();
        assertEquals(ErrorCode.SQL.ERROR_EXECUTE.getMessage(), text);
    }

    @Test
    public void testLoadTime() throws Exception {
        controller = new TabsController(Complex.BIPLANE_API2X);
        controller.setSessionId(SESSION_ID);
        setSelectedTab(TabType.TIME);

        String text = getSelectedAceEditorFirst().getValue();
        assertEquals(new SessionInfo("startTimestamp", "duration", "bpInterface").toString(), text);
    }

    @Test
    public void testLoadTimeEmpty() throws Exception {
        controller = new TabsController(Complex.BIPLANE_API2X);
        controller.setSessionId(SESSION_ID_EMPTY);
        setSelectedTab(TabType.TIME);

        String text = getSelectedAceEditorFirst().getValue();
        assertEquals(new SessionInfo().toString(), text);
    }

    @Test
    public void testLoadTimeExc() throws Exception {
        controller = new TabsController(Complex.BIPLANE_API2X);
        controller.setSessionId(SESSION_ID_EXC);
        setSelectedTab(TabType.TIME);

        String text = getSelectedAceEditorFirst().getValue();
        assertEquals(ErrorCode.SQL.ERROR_EXECUTE.getMessage(), text);
    }

    @Test
    public void testEnabled() throws Exception {
        controller = new TabsController(Complex.DEBT);

        assertFalse(controller.getView().isEnabled());
        controller.setSessionId(SESSION_ID);
        assertTrue(controller.getView().isEnabled());
    }

    @Test
    public void testLoadDebtAnswer() throws Exception {
        String session_debt = "session_debt";
        String message_debt = "message_debt";
        controller = new TabsController(Complex.DEBT);
        controller.setSessionId(session_debt);
        setSelectedTab(TabType.ANSWER);

        String text = getSelectedAceEditorFirst().getValue();
        assertEquals(message_debt, text);
        assertEquals(TabType.ANSWER.getName(), getSelectedTab().getCaption());
    }

    private AceEditor getSelectedAceEditorFirst() {
        return (AceEditor) getSelectedComponentFirst();
    }

    private Component getSelectedComponentFirst() {
        VerticalLayout layout = (VerticalLayout) getSelectedTab().getComponent();
        return layout.getComponent(FIRST_ELEMENT);
    }

    private TabSheet.Tab getSelectedTab() {
        TabsView tabs = controller.getView();
        return tabs.getTab(tabs.getSelectedTab());
    }

    private void setSelectedTab(TabType tab) {
        TabsView tabs = controller.getView();
        String caption;
        for (int i = 0; i < tabs.getComponentCount(); i++) {
            caption = tabs.getTab(i).getCaption();
            if (tab.getName().equals(caption)) {
                tabs.setSelectedTab(i);
                return;
            }
        }
    }
}
