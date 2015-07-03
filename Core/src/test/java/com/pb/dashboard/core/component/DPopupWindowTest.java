package com.pb.dashboard.core.component;

import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;

@RunWith(PowerMockRunner.class)
@PrepareForTest(UI.class)
public class DPopupWindowTest {

    private DPopupWindow popup;
    private static UI ui;

    @BeforeClass
    public static void setUpTest() throws Exception {
        ui = Mockito.mock(UI.class);
    }

    @Before
    public void setUp() throws Exception {
        popup = new DPopupWindow();
        mockUI();
    }

    private static void mockUI() {
        PowerMockito.mockStatic(UI.class);
        PowerMockito.when(UI.getCurrent()).thenReturn(ui);
    }

    @Test
    public void testOpenPosition() throws Exception {
        int x = 100;
        int y = 15;
        Window open = popup.open(x, y, null);
        assertEquals(x, open.getPositionX());
        assertEquals(y, open.getPositionY());
    }

    @Test
    public void testSetContent() throws Exception {
        VerticalLayout layout = new VerticalLayout();
        popup.setContent(layout);
    }

    @Test
    public void testOneWindow() throws Exception {
        int x = 10;
        int y = 15;
        Window window = popup.open(x, y, null);
        VerticalLayout layout = new VerticalLayout();
        popup.setContent(layout);
        assertEquals(layout, window.getContent());
    }

    @Test
    public void testCloseNullWindow() throws Exception {
        popup.close();
    }
}