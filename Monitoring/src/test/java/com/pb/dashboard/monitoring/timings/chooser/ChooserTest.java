package com.pb.dashboard.monitoring.timings.chooser;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class ChooserTest {

    private Chooser chooser;

    @Before
    public void setUp() throws Exception {
        chooser = new Chooser();
    }

    @Test
    public void testListenerNull() throws Exception {
        chooser.setValue(ChooserItem.ARITHMETIC);
    }

    @Test
    public void testCallListener() throws Exception {
        ChooserListener mockListener = Mockito.mock(ChooserListener.class);
        chooser.setListener(mockListener);
        chooser.setValue(ChooserItem.LOGARITHMIC);
        Mockito.verify(mockListener).change(ChooserItem.LOGARITHMIC);
    }

    @Test
    public void testInitItems() throws Exception {
        List<String> list = new ArrayList<String>();
        for (Object id : chooser.getItemIds()) {
            String item = chooser.getItemCaption(id);
            list.add(item);
        }
        for (ChooserItem chooserItem : ChooserItem.values()) {
            assertTrue(list.contains(chooserItem.getName()));
        }
    }
}