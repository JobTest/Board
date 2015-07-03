package com.pb.dashboard.itdashbord.errors.error.content.table;

import com.jensjansson.pagedtable.PagedTable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.Reindeer;

import java.util.ArrayList;
import java.util.List;

public class Pages extends HorizontalLayout {

    private static final int MAX_COUNT = 10;
    private Label description;
    private Button prev;
    private Button next;
    private List<Button> pages;
    private int numberPage = 1;
    private int count;
    private PagedTable table;
    private int currentPageIndex = 1;

    public Pages(PagedTable table) {
        this.table = table;
        init();
    }



    private void init() {
        initDescription();
        initPrevButton();
        initNumberButtons();
        initNextButton();
        setCount();
    }

    private void initDescription() {
        description = new Label("0(0)");
        addComponent(description);
        setComponentAlignment(description, Alignment.MIDDLE_CENTER);
    }

    private void initPrevButton() {
        prev = new Button("<<");
        prev.setVisible(false);
        prev.setStyleName(Reindeer.BUTTON_LINK);
        prev.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                String textNumber = pages.get(0).getCaption();
                int number = Integer.parseInt(textNumber) - MAX_COUNT;
                for (int i = 0; i < pages.size(); i++) {
                    pages.get(i).setCaption(String.valueOf(number + i));
                    pages.get(i).setVisible(true);
                }
                if (number <= MAX_COUNT) {
                    prev.setVisible(false);
                }
                next.setVisible(true);

                if (number + MAX_COUNT <= numberPage && number + MAX_COUNT + MAX_COUNT - 1 >= numberPage) {
                    int numberInCollect = numberPage - (number + MAX_COUNT);
                    pages.get(numberInCollect).setStyleName("pages");
                }
                if (number <= numberPage && number + MAX_COUNT - 1 >= numberPage) {
                    int numberInCollect = numberPage - number;
                    pages.get(numberInCollect).setStyleName("page-select");
                }
            }
        });
        addComponent(prev);
        setComponentAlignment(prev, Alignment.MIDDLE_CENTER);
    }

    private void initNumberButtons() {
        pages = new ArrayList<Button>(MAX_COUNT);
        for (int i = 0; i < MAX_COUNT; i++) {
            final Button button = new Button(String.valueOf(i + 1));
            button.setVisible(false);
            button.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                selectButton(button);
                int newPageIndex = Integer.valueOf(button.getCaption());
                nextPage(newPageIndex);
                }
            });
            button.setStyleName("pages");
            pages.add(button);
            addComponent(button);
        }
    }

    private void nextPage(int newPageIndex) {
        if (newPageIndex > currentPageIndex){
            int id = newPageIndex - currentPageIndex;
            for (int i = 0; i < id; i++) {
                table.nextPage();
                currentPageIndex = newPageIndex;
            }
        }
        if (newPageIndex < currentPageIndex){
            int id = currentPageIndex - newPageIndex;
            for (int i = 0; i < id; i++) {
                table.previousPage();
                currentPageIndex = newPageIndex;
            }
        }
    }

    private void initNextButton() {
        next = new Button(">>");
        next.setStyleName(Reindeer.BUTTON_LINK);
        next.setVisible(false);
        next.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                String numberText = pages.get(0).getCaption();
                int number = Integer.parseInt(numberText) + MAX_COUNT;

                for (int i = 0; i < pages.size(); i++) {
                    Button button = pages.get(i);
                    button.setCaption(String.valueOf(number + i));
                    button.setVisible(number + i <= count);
                }
                prev.setVisible(true);
                if (number + MAX_COUNT - 1 >= count) {
                    next.setVisible(false);
                }
                if (number - MAX_COUNT <= numberPage && number - MAX_COUNT + MAX_COUNT - 1 >= numberPage) {
                    int numberInCollect = numberPage - (number - MAX_COUNT);
                    pages.get(numberInCollect).setStyleName("pages");
                }
                if (number <= numberPage && number + MAX_COUNT - 1 >= numberPage) {
                    int numberInCollect = numberPage - number;
                    pages.get(numberInCollect).setStyleName("page-select");
                }
            }
        });
        addComponent(next);
        setComponentAlignment(next, Alignment.MIDDLE_CENTER);
    }

    private void selectButton(Button button) {
        Button buttonPrev = pages.get((numberPage - 1) % MAX_COUNT);
        if (button != buttonPrev) {
            buttonPrev.setStyleName("pages");
            numberPage = Integer.valueOf(button.getCaption());
            button.setStyleName("page-select");
        }
    }

    public void setCount() {
        count = table.getTotalAmountOfPages();

        pages.get(0).setStyleName("page-select");

        for (int i = 0; i < Math.min(count, MAX_COUNT); i++) {
            pages.get(i).setVisible(true);
        }
        if (count > MAX_COUNT) {
            next.setVisible(true);
        }
        setCommonCountLabel(Math.min(MAX_COUNT * 10, count));
    }

    public void setCommonCountLabel(int count) {
        description.setValue(this.count + "(" + count + ")");
    }
}
