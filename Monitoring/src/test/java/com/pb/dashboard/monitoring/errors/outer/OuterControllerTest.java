package com.pb.dashboard.monitoring.errors.outer;

import com.pb.dashboard.core.error.DashSQLException;
import com.pb.dashboard.monitoring.errors.all.db.mongo.ErrorsDBManagerAbs;
import com.pb.dashboard.monitoring.errors.all.db.mongo.ErrorsDBManagerI;
import com.pb.dashboard.monitoring.errors.outer.table.OuterSessionData;
import org.junit.*;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ErrorsDBManagerAbs.class})
public class OuterControllerTest {

    private static final String SESSION_ID_EMPTY = "session_id_empty";
    private static final int YEAR = 2014;
    private static final int MONTH = 10;
    private static final int DAY = 10;

    private static OuterController controller;
    private static OuterSessionData data;
    private static List<OuterSessionData> list;

    private static Date date;
    private static ErrorsDBManagerI errorsDBManager;

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
        errorsDBManager = mock(ErrorsDBManagerI.class);
        when(errorsDBManager.getDataByOuterSession(SESSION_ID_EMPTY, date)).thenReturn(list);
    }

    @AfterClass
    public static void tearDown() {
        errorsDBManager = null;
    }

    @Before
    public void setUp() {
        PowerMockito.mockStatic(ErrorsDBManagerAbs.class);
        PowerMockito.when(ErrorsDBManagerAbs.getInstance()).thenReturn(errorsDBManager);
    }

    @After
    public void tearDownController() {
        controller = null;
    }

    @Test
    public void testGetOuterSessionList() throws Exception {
        controller = new OuterController();
        List<OuterSessionData> sessionList = controller.getSessionList(SESSION_ID_EMPTY, date);
        int size = sessionList.size();
        assertEquals(3, size);
    }

    @Test
    public void testGetOuterSessionListEmpty() throws Exception {
        controller = new OuterController();
        Date time = Calendar.getInstance().getTime();
        List<OuterSessionData> sessionList = controller.getSessionList(SESSION_ID_EMPTY, time);
        int size = sessionList.size();
        assertEquals(0, size);
    }
}