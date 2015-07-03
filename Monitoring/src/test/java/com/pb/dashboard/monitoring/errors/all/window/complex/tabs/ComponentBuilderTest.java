package com.pb.dashboard.monitoring.errors.all.window.complex.tabs;

import com.pb.dashboard.core.error.DashSQLException;
import com.pb.dashboard.core.error.ErrorCode;
import com.pb.dashboard.core.model.Complex;
import com.pb.dashboard.dao.entity.biplanesupport.db.SessionErrorsData;
import com.pb.dashboard.dao.entity.biplanesupport.db.TimingData;
import com.pb.dashboard.monitoring.errors.all.db.container.TimingT2Data;
import com.pb.dashboard.monitoring.errors.all.db.mongo.ErrorsDBManagerAbs;
import com.pb.dashboard.monitoring.errors.all.db.mongo.TimingLevel;
import com.vaadin.ui.Component;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.vaadin.aceeditor.AceEditor;
import org.vaadin.aceeditor.AceMode;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by vlad
 * Date: 14.11.14_12:30
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ErrorsDBManagerAbs.class})
public class ComponentBuilderTest {

    private static final String SESSION_ID = "sessionId";
    private static final String SESSION_ID_NOT_RET = "sessionIdNotRet";
    private static final String SESSION_ID_EXC = "sessionIdExc";
    private static final String XML_QUERY = "xml_query";
    private static final String XML_ANSWER = "xml_answer";
    private TabsModel model;
    private ComponentBuilder builder;
    private static ErrorsDBManagerAbs errorsDB;
//    private static IqDbManager iqDb;

    private static List<TimingData> timingDatas;
    private static List<TimingT2Data> timingT2Datas;
    private static List<SessionErrorsData> errorsDatas;

    @BeforeClass
    public static void upBD() {
        initData();

        errorsDB = mock(ErrorsDBManagerAbs.class);
        try {
            when(errorsDB.getResultBySession(TimingLevel.QUERY, Complex.BIPLANE_API2X, SESSION_ID)).thenReturn(XML_QUERY);
            when(errorsDB.getResultBySession(TimingLevel.QUERY, Complex.BIPLANE_API2X, SESSION_ID_NOT_RET)).thenThrow(new DashSQLException(ErrorCode.SQL.QUERY_DOES_NOT_RETURN_ANYTHING));
            when(errorsDB.getResultBySession(TimingLevel.QUERY, Complex.BIPLANE_API2X, SESSION_ID_EXC)).thenThrow(new DashSQLException(ErrorCode.SQL.ERROR_EXECUTE));

            when(errorsDB.getResultBySession(TimingLevel.ANSWER, Complex.BIPLANE_API2X, SESSION_ID)).thenReturn(XML_ANSWER);

            when(errorsDB.getTimingT2(Complex.BIPLANE_API2X, SESSION_ID)).thenReturn(timingT2Datas);
        } catch (Exception ignore) {
        }

//        iqDb = mock(IqDbManager.class);
//        try {
//            when(iqDb.getTimingsData(Complex.BIPLANE_API2X, SESSION_ID)).thenReturn(timingDatas);
//            when(iqDb.getSessionErrors(SESSION_ID)).thenReturn(errorsDatas);
//        } catch (Exception ignore) {
//        }
    }

    private static void initData() {
        timingDatas = new ArrayList<>();
        timingDatas.add(new TimingData());

        timingT2Datas = new ArrayList<>();
        timingT2Datas.add(new TimingT2Data());

        errorsDatas = new ArrayList<>();
        errorsDatas.add(new SessionErrorsData());
    }

    @Before
    public void setUp() throws Exception {
        model = new TabsModel();
        builder = new ComponentBuilder(model);

        PowerMockito.mockStatic(ErrorsDBManagerAbs.class);
        PowerMockito.when(ErrorsDBManagerAbs.getInstance()).thenReturn(errorsDB);

//        PowerMockito.mockStatic(IqDbManager.class);
//        PowerMockito.when(IqDbManager.getInstance()).thenReturn(iqDb);
    }

    @After
    public void tearDown() throws Exception {
        model = null;
        builder = null;
    }

    @Test
    public void testQueryPositive() throws Exception {
        model.setComplex(Complex.BIPLANE_API2X);
        model.setSessionId(SESSION_ID);
        Component component = builder.loadComponent(TabType.QUERY);
        String value = ((AceEditor) component).getValue();
        assertEquals(XML_QUERY, value);
    }

    @Test
    public void testQueryReturnAnything() throws Exception {
        model.setComplex(Complex.BIPLANE_API2X);
        model.setSessionId(SESSION_ID_NOT_RET);
        Component component = builder.loadComponent(TabType.QUERY);
        String value = ((AceEditor) component).getValue();
        assertEquals(ErrorCode.SQL.QUERY_DOES_NOT_RETURN_ANYTHING.getMessage(), value);
    }

    @Test
    public void testQueryReturnException() throws Exception {
        model.setComplex(Complex.BIPLANE_API2X);
        model.setSessionId(SESSION_ID_EXC);
        Component component = builder.loadComponent(TabType.QUERY);
        String value = ((AceEditor) component).getValue();
        assertEquals(ErrorCode.SQL.ERROR_EXECUTE.getMessage(), value);
    }

    @Test
    public void testAnswerPositive() throws Exception {
        model.setComplex(Complex.BIPLANE_API2X);
        model.setSessionId(SESSION_ID);
        Component component = builder.loadComponent(TabType.ANSWER);
        String value = ((AceEditor) component).getValue();
        assertEquals(XML_ANSWER, value);
    }

    @Test
    public void testCreateAceEditorForEmpty() throws Exception {
        AceEditor aceEditor = ComponentBuilder.createAceEditor("", AceMode.text);
        String value = aceEditor.getValue();
        assertEquals(ErrorCode.SQL.QUERY_DOES_NOT_RETURN_ANYTHING.getMessage(), value);
    }

    @Test
    public void testCreateAceEditorForNull() throws Exception {
        AceEditor aceEditor = ComponentBuilder.createAceEditor(null, AceMode.text);
        String value = aceEditor.getValue();
        assertEquals(ErrorCode.SQL.QUERY_DOES_NOT_RETURN_ANYTHING.getMessage(), value);
    }

    @Test
    public void testCreateAceEditorForText() throws Exception {
        String text = "123567";
        AceEditor aceEditor = ComponentBuilder.createAceEditor(text, AceMode.text);
        String value = aceEditor.getValue();
        assertEquals(text, value);
    }
}
