package com.pb.dashboard.monitoring.components.navigator;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.vaadin.hene.popupbutton.PopupButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * Created by vlad
 * Date: 21.11.14_16:40
 */
public class NavigatorViewTest {

    private NavigatorView view;
    private NavigatorModelI model;
    private NavigatorControllerI controller;
    private NavigatorContent content;

    private List<ContentItem> items1 = Arrays.asList(new ContentItem[]{
            new ContentItem("i1", "v1"),
            new ContentItem("i2", "v2"),
            new ContentItem("i3", "v3")
    });

    @Before
    public void setUp() throws Exception {
        model = new NavigatorModel();
        controller = new NavigatorController(model);
        view = new NavigatorView(controller, model);
        content = new NavigatorContent();
        content.setItems(items1);
        content.setEnabled(true);
        content.setItem(items1.get(0));
    }

    @After
    public void tearDown() throws Exception {
        model = null;
        controller = null;
        view = null;
    }

    @Test
    public void testInitEmpty() throws Exception {
        assertNull(getLabel(0, 0));
        assertFalse(isSeparator(0, 0));
    }

    @Test
    public void testInitLabel() throws Exception {
        int navigationId = 1;
        int numberItem = 2;
        model.setContent(navigationId, items1.get(numberItem));
        view = new NavigatorView(controller, model);

        String label = getLabel(0, 0);
        String expectedName = items1.get(numberItem).getName();
        assertEquals(expectedName, label);
    }

    @Test
    public void testInitContents() throws Exception {
        int navigationId = 1;
        model.setContents(navigationId, items1);
        view = new NavigatorView(controller, model);

        List<String> strings = getContent(0, 0);
        for (int i = 0; i < strings.size(); i++) {
            assertEquals(items1.get(i).getName(), strings.get(i));
        }
    }

    @Test
    public void testInitEnabled() throws Exception {
        int navigationId = 1;
        model.setEnabled(navigationId, false);
        view = new NavigatorView(controller, model);

        PopupButton popupButton = getPopupButton(0, 0);
        assertFalse(popupButton.isEnabled());
    }

    @Test
    public void testSetItem() throws Exception {
        String itemValue = "name";
        view.setItem(3, new ContentItem(3, itemValue));
        String label = getLabel(0, 0);
        assertEquals(itemValue, label);
    }

    @Test
    public void testTwoSetItemInOneCell() throws Exception {
        String itemValue = "name";
        view.setItem(3, new ContentItem(3, "otherName"));
        view.setItem(3, new ContentItem(3, itemValue));
        String label = getLabel(0, 0);
        assertEquals(itemValue, label);
    }

    @Test
    public void testTwoSetItem() throws Exception {
        String firstValue = "firstValue";
        String secondValue = "secondValue";
        view.setItem(2, new ContentItem(3, firstValue));
        view.setItem(3, new ContentItem(3, secondValue));
        assertEquals(firstValue, getLabel(0, 0));
        assertEquals(secondValue, getLabel(0, 1));
    }

    @Test
    public void testSeparator() throws Exception {
        view.setItem(2, new ContentItem(3, "1"));
        view.setItem(3, new ContentItem(3, "2"));
        assertTrue(isSeparator(0, 0));
    }

    @Test
    public void testEnabled() throws Exception {
        view.setEnabled(3, true);
        view.setItem(3, new ContentItem(3, "2"));
        assertTrue(getPopupButton(0, 0).isEnabled());

        view.setEnabled(4, false);
        view.setItem(4, new ContentItem(3, "3"));
        assertFalse(getPopupButton(0, 1).isEnabled());
    }

    @Test
    public void testSetList() throws Exception {
        view.setItems(3, items1);
        List<String> strings = getContent(0, 0);
        for (int i = 0; i < strings.size(); i++) {
            assertEquals(items1.get(i).getName(), strings.get(i));
        }
    }

    @Test
    public void testNextLine() throws Exception {
        view.nextLine();
        view.nextLine();
        view.setItem(2, new ContentItem(1, "2"));
        assertNull(getPopupButton(0, 0));
        assertNull(getPopupButton(1, 0));
        assertNotNull(getPopupButton(2, 0));
    }

    @Test
    public void testSelectedItem() throws Exception {
        controller = spy(new NavigatorController(model));
        view = new NavigatorView(controller, model);
        view.setItems(2, items1);
        view.setItem(2, items1.get(0));

        List<Button> buttons = getButtons(0, 0);
        buttons.get(0).click();

        verify(controller).setItem(2, items1.get(0));
    }

    private String getLabel(int line, int number) {
        PopupButton popupButton = getPopupButton(line, number);
        if (popupButton == null) {
            return null;
        }
        return popupButton.getCaption();
    }

    private List<String> getContent(int line, int number) {
        List<Button> buttons = getButtons(line, number);
        List<String> list = new ArrayList<String>();
        for (Button button : buttons) {
            list.add(button.getCaption());
        }
        return list;
    }

    private List<Button> getButtons(int line, int number) {
        PopupButton popupButton = getPopupButton(line, number);
        VerticalLayout layout = (VerticalLayout) popupButton.getContent();
        List<Button> buttons = new ArrayList<Button>();
        for (int i = 0; i < layout.getComponentCount(); i++) {
            Button button = (Button) layout.getComponent(i);
            buttons.add(button);
        }
        return buttons;
    }

    private boolean isSeparator(int line, int number) {
        HorizontalLayout layout = (HorizontalLayout) view.getComponent(line);
        if (layout == null || layout.getComponentCount() <= number * 2 + 1) {
            return false;
        }
        Label label = (Label) layout.getComponent(number * 2 + 1);
        return NavigatorView.SEPARATOR_CHART.equals(label.getValue());
    }

    private PopupButton getPopupButton(int line, int number) {
        HorizontalLayout layout = (HorizontalLayout) view.getComponent(line);
        if (layout == null || layout.getComponentCount() <= number * 2) {
            return null;
        }
        return (PopupButton) layout.getComponent(number * 2);
    }
}