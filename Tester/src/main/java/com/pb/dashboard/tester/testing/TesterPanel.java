package com.pb.dashboard.tester.testing;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

import java.util.ArrayList;

public class TesterPanel extends Panel {

    private static final String AUTO_TEST = "Автоматические тесты";
    private static final String MANUAL_TEST = "Ручные тесты";
    private static final String RATIO = "Соотношение";
    private static final String WEEK = "Неделя";
    private static final String MONTH = "Месяц";
    private static final String QUARTAR = "Квартал";
    private static final String HALF_YEAR = "Полугодие";
    private static final int HIGHT_ROW = 60;
    private static final int COUNT_COLUMN = 15;
    private int countRow = 2;
    private GridLayout layout;

    public void initLayout(int count) {
        layout = new GridLayout(COUNT_COLUMN, 2 + count);
        initTitle();
        layout.setWidth("99%");
        layout.setHeight((2 + count) * HIGHT_ROW, Unit.PIXELS);

        setContent(layout);
    }

    private void initTitle() {
        addLabel(AUTO_TEST, "", COUNT_COLUMN - 12, 0, COUNT_COLUMN - 9, 0);
        addLabel(MANUAL_TEST, "", COUNT_COLUMN - 8, 0, COUNT_COLUMN - 5, 0);
        addLabel(RATIO, "", COUNT_COLUMN - 4, 0, COUNT_COLUMN - 1, 0);
        addLabel("<span style='text-decoration:underline; font-size:26px'>Проект</span>", "", 0, 1, COUNT_COLUMN - 13, 1);

        for (int i = 0; i < 3; i++) {
            addLabel(WEEK, "", COUNT_COLUMN - 12 + i * 4, 1);
            addLabel(MONTH, "", COUNT_COLUMN - 11 + i * 4, 1);
            addLabel(QUARTAR, "", COUNT_COLUMN - 10 + i * 4, 1);
            addLabel(HALF_YEAR, "", COUNT_COLUMN - 9 + i * 4, 1);
        }
    }

    public void addProject(ArrayList<Cell> strings) {
        int column = 0;
        for (Cell cell : strings) {
            if (column == 0) {
                column += COUNT_COLUMN - 13;
                addLabel(cell.getText(), cell.getDescription(), 0, countRow, column, countRow);
            } else {
                addLabel(cell.getText(), cell.getDescription(), column, countRow);
            }
            column++;
        }
        countRow++;
    }

    private void addLabel(String text, String description, int column, int row) {
        addLabel("<span style='font-size:12px'>" + text + "</span>", description, column, row, column, row);
    }

    private void addLabel(String text, String description, int column1, int row1, int column2, int row2) {
        if (column2 < COUNT_COLUMN) {
            Label label = new Label(text, ContentMode.HTML);
            label.setDescription(description);
            label.addStyleName("label-tester");
            layout.addComponent(label, column1, row1, column2, row2);
            label.setSizeFull();
        }
    }

    public GridLayout getLayout() {
        return layout;
    }
}