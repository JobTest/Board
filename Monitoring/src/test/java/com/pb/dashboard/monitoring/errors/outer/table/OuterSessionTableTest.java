package com.pb.dashboard.monitoring.errors.outer.table;

import com.pb.dashboard.monitoring.errors.outer.table.listener.ClickSessionListener;
import com.vaadin.data.Item;
import com.vaadin.ui.Button;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OuterSessionTableTest {

    private static final String CLICKED = "well_done";
    private static final int SECOND_ELEMENT = 1;

    private static OuterSessionTable outerSessionTable;
    private static List<OuterSessionData> list;
    private static OuterSessionData data;

    @Before
    public void setUp() {
        outerSessionTable = new OuterSessionTable();
        list = new ArrayList<>();
        data = new OuterSessionData("sid", "inm", "str", "dur", "stat", "comp", "errc");
    }

    @After
    public void tearDown() {
        outerSessionTable = null;
    }

    @Test(expected = NullPointerException.class)
    public void testSetOuterTableNull() {
        outerSessionTable.setOuterSession(null);
    }

    @Test
    public void testSetOuterTableButton() {
        list.add(data.clone());
        list.add(data.clone());
        list.add(data.clone());
        list.add(data.clone());
        outerSessionTable.setOuterSession(list);
        for (Object itemId : outerSessionTable.getItemIds()) {
            Item item = outerSessionTable.getItem(itemId);
            Object button = item.getItemProperty(OuterSessionTable.SESSION_ID).getValue();
            assertEquals(Button.class, button.getClass());
        }
    }

    @Test
    public void testListener() {
        final String[] buttonClick = new String[1];
        list.add(data.clone());
        outerSessionTable.setOuterSession(list);
        outerSessionTable.setListener(new ClickSessionListener() {
            @Override
            public void clickSession(String code) {
                buttonClick[0] = CLICKED;
            }
        });
        Button button = (Button) outerSessionTable.getItem(SECOND_ELEMENT).getItemProperty(OuterSessionTable.SESSION_ID).getValue();
        assertEquals(null, buttonClick[0]);
        button.click();
        assertEquals(CLICKED, buttonClick[0]);
    }
}