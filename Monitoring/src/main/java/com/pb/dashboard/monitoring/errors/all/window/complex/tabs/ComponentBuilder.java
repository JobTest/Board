package com.pb.dashboard.monitoring.errors.all.window.complex.tabs;

import com.pb.dashboard.core.error.DashSQLException;
import com.pb.dashboard.core.error.ErrorCode;
import com.pb.dashboard.core.util.StringUtil;
import com.pb.dashboard.dao.entity.biplanesupport.db.SessionErrorsData;
import com.pb.dashboard.dao.entity.biplanesupport.db.TimingData;
import com.pb.dashboard.dao.service.ServiceFactory;
import com.pb.dashboard.monitoring.errors.all.db.container.SessionInfo;
import com.pb.dashboard.monitoring.errors.all.db.container.SessionStackTraceData;
import com.pb.dashboard.monitoring.errors.all.db.container.TimingT2Data;
import com.pb.dashboard.monitoring.errors.all.db.mongo.ErrorsDBManagerAbs;
import com.pb.dashboard.monitoring.errors.all.db.mongo.TimingLevel;
import com.pb.dashboard.monitoring.errors.all.table.SessionErrorsTable;
import com.pb.dashboard.monitoring.errors.all.table.TimingT2Table;
import com.pb.dashboard.monitoring.errors.all.table.TimingTable;
import com.pb.dashboard.monitoring.errors.all.table.TimingsTreeTable;
import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.vaadin.aceeditor.AceEditor;
import org.vaadin.aceeditor.AceMode;

import java.util.List;

/**
 * Created by vlad
 * Date: 14.11.14_9:48
 */
public class ComponentBuilder {

    private static final Logger log = Logger.getLogger(ComponentBuilder.class);
    private TabsModelI model;

    public ComponentBuilder(TabsModelI model) {
        this.model = model;
    }

    public Component loadComponent(TabType tab) {
        switch (tab) {
            case QUERY:
                return loadQuery();
            case ANSWER:
                return loadAnswer();
            case T0:
                return loadT0();
            case TIMINGS:
                return loadTimings();
            case STACK_TRACE:
                return loadStackTrace();
            case ERRORS:
                return loadErrors();
            case TIME:
                return loadTime();
            default:
                log.warn("TabType is not valid.[" + tab + "]");
                return new VerticalLayout();
        }
    }

    private Component loadQuery() {
        return loadXmlForAnsAndQuery(TimingLevel.QUERY);
    }

    private Component loadAnswer() {
        return loadXmlForAnsAndQuery(TimingLevel.ANSWER);
    }

    private Component loadXmlForAnsAndQuery(TimingLevel level) {
        try {
            String query = ErrorsDBManagerAbs.getInstance().getResultBySession(level, model.getComplex(), model.getSessionId());
            return createAceEditor(query, AceMode.xml);
        } catch (DashSQLException e) {
            return createAceEditorException(e);
        }
    }

    private Component loadTimings() {
        return new TimingsTreeTable() {
            @Override
            public Component loadComponentT0() {
                return loadT0();
            }

            @Override
            public Component loadComponentT2() {
                return loadT2();
            }
        };
    }

    private Component loadT0() {
        try {
            List<TimingData> timings = ServiceFactory.getMonitoring().getTimingsData(model.getComplex(), model.getSessionId());
            if (timings.isEmpty()) {
                return new Label(ErrorCode.SQL.QUERY_DOES_NOT_RETURN_ANYTHING.getMessage());
            }
            return new TimingTable(timings);
        } catch (Exception e) {
            return createAceEditorException(e);
        }
    }

    private Component loadT2() {
        try {
            List<TimingT2Data> timingT2 = ErrorsDBManagerAbs.getInstance().getTimingT2(model.getComplex(), model.getSessionId());
            if (timingT2.isEmpty()) {
                return new Label(ErrorCode.SQL.QUERY_DOES_NOT_RETURN_ANYTHING.getMessage());
            }
            return new TimingT2Table(timingT2);
        } catch (DashSQLException e) {
            return createAceEditorException(e);
        }
    }

    private Component loadStackTrace() {
        try {
            List<SessionStackTraceData> stackTraces = ErrorsDBManagerAbs.getInstance().getStackTraces(model.getComplex(), model.getSessionId());
            if (stackTraces.isEmpty()) {
                return createAceEditor(null, AceMode.text);
            }
            return wrapStackTrace(stackTraces);
        } catch (DashSQLException e) {
            return createAceEditorException(e);
        }
    }

    private VerticalLayout wrapStackTrace(List<SessionStackTraceData> stackTraces) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        for (SessionStackTraceData stackTrace : stackTraces) {
            AceEditor aceEditor = createAceEditor(stackTrace.getMessage(), AceMode.java);
            layout.addComponent(aceEditor);
        }
        return layout;
    }

    private Component loadErrors() {
        try {
            List<SessionErrorsData> errorLoggers = ServiceFactory.getMonitoring()
                    .getSessionErrors(model.getSessionId());
            if (errorLoggers.isEmpty()) {
                return createAceEditor(null, AceMode.text);
            }
            SessionErrorsTable table = new SessionErrorsTable();
            table.setErrors(errorLoggers);
            table.setWidth("2000px");
            return wrapPanel(table);
        } catch (Exception e) {
            return createAceEditorException(e);
        }
    }

    private Component loadTime() {
        try {
            SessionInfo sessionInfo = ErrorsDBManagerAbs.getInstance()
                    .getSessionInfo(model.getComplex(), model.getSessionId());
            return createAceEditor(String.valueOf(sessionInfo), AceMode.text);
        } catch (DashSQLException e) {
            return createAceEditorException(e);
        }
    }

    private CssLayout wrapPanel(Component component) {
        CssLayout cssLayout = new CssLayout();
        cssLayout.addStyleName("layout-panel");
        cssLayout.setSizeFull();
        Panel panel = new Panel();
        panel.setSizeFull();
        panel.setContent(component);
        cssLayout.addComponent(panel);
        return cssLayout;
    }

    public static AceEditor createAceEditorException(Exception e) {
        log.error(e.getMessage());
        return createAceEditor(e.getMessage(), AceMode.java);
    }

    public static AceEditor createAceEditor(String text, AceMode mode) {
        AceEditor editor = new AceEditor();
        editor.setSizeFull();
        String value = StringUtil.isEmptyOrNull(text) ? ErrorCode.SQL.QUERY_DOES_NOT_RETURN_ANYTHING.getMessage() : text;
        editor.setValue(value);
        editor.setWordWrap(true);
        editor.setReadOnly(true);
        editor.setMode(mode);
        editor.setShowGutter(false);
        return editor;
    }
}