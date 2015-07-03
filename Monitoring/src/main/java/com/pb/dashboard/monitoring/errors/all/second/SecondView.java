package com.pb.dashboard.monitoring.errors.all.second;

import com.pb.dashboard.monitoring.components.observers.Observer;
import com.pb.dashboard.monitoring.errors.all.table.ErrorTreeTable;
import com.pb.dashboard.monitoring.errors.all.table.InfoErrorTable;
import com.pb.dashboard.monitoring.errors.all.table.RecipientTreeTable;
import com.pb.dashboard.monitoring.errors.all.table.listener.TreeTableListener;
import com.pb.dashboard.monitoring.errors.all.table.model.ErrorModel;
import com.pb.dashboard.monitoring.errors.all.table.model.RecipientModel;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vlad
 * Date: 12.09.14
 */
public class SecondView extends VerticalLayout implements Observer<SecondModelI> {

    private static final String BACK = "Назад";
    private static final String CODE = "Код:";
    private static final String COMPANY = "Company:";
    private static final String NULL_VALUE = "[null]";

    private SecondControllerI controller;
    private SecondModelI model;

    private HorizontalLayout title;
    private Button back;

    private Label codeLabel;
    private Button codeButton;

    private InfoErrorTable infoTable;
    private Panel tablePanel;

    private TreeTableListener listener;

    public SecondView(SecondControllerI controller, SecondModelI model) {
        this.controller = controller;
        this.model = model;
        model.addObserver(this);
        init();
    }

    private void init() {
        initTitle();
        initDescriptionTable();
        initTablePanel();
    }

    private void initTitle() {
        title = new HorizontalLayout();
        title.setWidth("50%");
        addBackButton();
        addCode();
        addComponent(title);
    }

    private void addBackButton() {
        back = new Button(BACK);
        back.setStyleName(Reindeer.BUTTON_LINK);
        title.addComponent(back);
    }

    private void addCode() {
        HorizontalLayout codeLayout = new HorizontalLayout();

        codeLabel = new Label();
        codeLayout.addComponent(codeLabel);

        codeButton = new Button();
        codeButton.setStyleName(Reindeer.BUTTON_LINK);
        codeLayout.addComponent(codeButton);

        title.addComponent(codeLayout);
    }

    private void initDescriptionTable() {
        infoTable = new InfoErrorTable();
        infoTable.setVisible(false);
        addComponent(infoTable);
    }

    private void initTablePanel() {
        tablePanel = new Panel();
        addComponent(tablePanel);
    }

    public void setListener(TreeTableListener listener) {
        this.listener = listener;
    }

    @Override
    public void modified(SecondModelI obj) {
        infoTable.setErrorDetail(obj.getErrorDetail());
        infoTable.setVisible(obj.isDescriptionVisible());
        setCodeButtonText(obj.getCode());
        setList(obj.getList());
    }

    public void setList(List list) {
        Table table = createRecipientTable(new ArrayList<RecipientModel>());
        String code = CODE;
        if (!list.isEmpty()) {
            if (list.get(0) instanceof ErrorModel) {
                code = COMPANY;
                table = createErrorTable(list);
            }
            if (list.get(0) instanceof RecipientModel) {
                table = createRecipientTable(list);
            }
        }
        codeLabel.setValue(code);
        tablePanel.setContent(table);
    }

    private Table createRecipientTable(List<RecipientModel> list) {
        RecipientTreeTable table = new RecipientTreeTable();
        table.setListener(listener);
        table.setRecipients(list);
        return table;
    }

    private Table createErrorTable(List<ErrorModel> list) {
        ErrorTreeTable table = new ErrorTreeTable();
        table.setListener(listener);
        table.setErrors(list);
        return table;
    }

    public void setCodeButtonText(String text) {
        String code = text == null ? NULL_VALUE : text;
        codeButton.setCaption(code);
    }

    public void addBackClickListener(Button.ClickListener listener) {
        back.addClickListener(listener);
    }

    public void addCodeClickListener(Button.ClickListener listener) {
        codeButton.addClickListener(listener);
    }
}