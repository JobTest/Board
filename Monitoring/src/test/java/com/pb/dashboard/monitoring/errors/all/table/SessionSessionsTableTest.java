package com.pb.dashboard.monitoring.errors.all.table;

import com.pb.dashboard.monitoring.errors.all.table.listener.ClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by vlad
 * Date: 30.09.14
 */
public class SessionSessionsTableTest {

    private static final String SESSION = "session";
    private static final String SESSION1 = "session1";
    private static final String SESSION2 = "session2";

    private SessionSessionsTable table;

    @Before
    public void setUp() throws Exception {
        table = new SessionSessionsTable();
    }

    @After
    public void tearDown() throws Exception {
        table = null;
    }

    @Test
    public void testListener() throws Exception {
        final String[] buttonClick = new String[1];
        String sessionTest = "sessionTest";
        table.addSession(sessionTest, null);
        table.setListener(new ClickListener() {
            @Override
            public void clickCode(String code) {
                buttonClick[0] = "click!";
            }
        });
        Button button = (Button) table.getItem(sessionTest).getItemProperty(SessionSessionsTable.SESSION_ID).getValue();
        assertEquals(null, buttonClick[0]);
        button.click();
        assertEquals("click!", buttonClick[0]);
    }

    @Test
    public void testSessionId() throws Exception {
        table.addSession(SESSION1, null);
        table.addSession(SESSION2, null);
        table.addSession(SESSION1, null);

        assertEquals(2, table.getItemIds().size());

        Button button = (Button) table.getItem(SESSION2).getItemProperty(SessionSessionsTable.SESSION_ID).getValue();
        assertEquals(SESSION2, button.getCaption());
    }

    @Test
    public void testAddTable() throws Exception {
        Table tableTest = new Table();
        table.addSession(SESSION, tableTest);

        Object sessionTable = table.getItem(SESSION).getItemProperty(SessionSessionsTable.INFO).getValue();
        assertNotNull(sessionTable);
        assertTrue(tableTest == sessionTable);
    }
}
