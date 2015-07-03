package com.pb.dashboard.monitoring.errors.all.strategy;

import com.pb.dashboard.core.model.Complex;
import com.pb.dashboard.core.util.StringUtil;
import com.pb.dashboard.dao.entity.biplanesupport.db.ErrorsType;
import com.pb.dashboard.dao.entity.biplanesupport.db.ErrorsTypeI;
import com.pb.dashboard.monitoring.components.navigator.ContentItem;
import com.pb.dashboard.monitoring.components.navigator.NavigatorControllerI;
import com.pb.dashboard.monitoring.components.navigator.NavigatorModelI;
import com.pb.dashboard.monitoring.components.navigator.NavigatorObserver;
import com.pb.dashboard.monitoring.components.observers.Observer;
import com.pb.dashboard.monitoring.errors.all.filter.FilterControllerI;
import com.pb.dashboard.monitoring.errors.all.filter.FilterModelI;
import com.pb.dashboard.monitoring.errors.all.first.FirstController;
import com.pb.dashboard.monitoring.errors.all.first.FirstControllerI;
import com.pb.dashboard.monitoring.errors.all.navigator.NavigatorItem;
import com.pb.dashboard.monitoring.errors.all.navigator.NavigatorModelAdapter;
import com.pb.dashboard.monitoring.errors.all.search.SearchListener;
import com.pb.dashboard.monitoring.errors.all.second.SecondController;
import com.pb.dashboard.monitoring.errors.all.second.SecondControllerI;
import com.pb.dashboard.monitoring.errors.all.table.listener.ClickListener;
import com.pb.dashboard.monitoring.errors.all.table.listener.TreeTableListener;
import com.pb.dashboard.monitoring.errors.all.window.SessionController;
import com.vaadin.ui.*;

import java.io.Serializable;
import java.util.List;

public class PanelController implements NavigatorObserver, Observer<FilterModelI>, PanelControllerI, Serializable {

    private static final long serialVersionUID = -7638159890490812521L;
    private PanelView view;
    private PanelModelI model;

    private NavigatorControllerI navigatorController;
    private FilterControllerI filterController;
    private FirstControllerI firstController;
    private SecondControllerI secondController;
    private LoadData loadData;

    public PanelController(PanelModelI model) {
        this.model = model;
    }

    public void init() {
        initControllers();
        initView();
        resetPage();
        update();
    }

    private void initControllers() {
        initLoadData();
        initFirstController();
        initSecondController();
    }

    private void initLoadData() {
        loadData = new LoadData(model.getFilterModel(), model.getNavigatorModel());
    }

    private void initFirstController() {
        firstController = new FirstController();
        firstController.setListener(new ClickListener() {
            @Override
            public void clickCode(String code) {
                String id = LoadData.NULL_VALUE.endsWith(code) ? null : code;
                model.setCodeIdFirstPage(id);
                changePage();
            }
        });
        firstController.setSearchListener(getSearchListener());
    }

    private void initSecondController() {
        secondController = new SecondController();
        secondController.setTreeTableListener(createListener());
        secondController.addBackClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                changePage();
            }
        });
    }

    private TreeTableListener createListener() {
        return new TreeTableListener() {
            @Override
            public List<String> clickItem(String itemId, PageType type) {
                String id = LoadData.NULL_VALUE.equals(itemId) ? null : itemId;
                return loadData.getSessions(id, type, model.getCodeIdFirstPage());
            }

            @Override
            public void clickSession(String session) {
                openSession(session);
            }
        };
    }

    private void initView() {
        view = new PanelView(this, model);
        view.setNavigator(navigatorController.getView());
        view.setFilter(filterController.getView());
        view.init();
    }

    private SearchListener getSearchListener() {
        return new SearchListener() {
            @Override
            public void clickSearch(String searchText) {
                ErrorsTypeI metric = model.getNavigatorModel().getErrorType();
                if (!ErrorsType.RECIPIENT.equals(metric)) {
                    openSession(searchText);
                } else {
                    model.setCodeIdFirstPage(searchText);
                    changePage();
                }
            }
        };
    }

    public void changePage() {
        Strategy.transitionOnPage(model);
        update();
    }

    private void resetPage() {
        ErrorsTypeI metric = model.getNavigatorModel().getErrorType();
        if (ErrorsType.RECIPIENT.equals(metric)) {
            movePage(PageNumber.FIRST, PageType.RECIPIENT);
        } else {
            movePage(PageNumber.FIRST, PageType.ERROR);
        }
    }

    public void movePage(PageNumber number, PageType type) {
        model.setType(type);
        model.setNumber(number);
        update();
    }

    private void update() {
        if (model.getNumber() == PageNumber.FIRST) {
            model.setCodeIdFirstPage(null);
            List list = getListData(model.getType());
            firstController.setList(list);
            view.setPanelComponent(firstController.getView());
            boolean recipientType = model.getNavigatorModel().getErrorType() == ErrorsType.RECIPIENT;
            navigatorController.setEnabled(NavigatorItem.GROUP.getPkey(), !recipientType);
            navigatorController.setEnabled(NavigatorItem.RESPONSIBLE.getPkey(), !recipientType);
        } else {
            secondController.setCode(model.getCodeIdFirstPage());
            List list = getListData(model.getType());
            secondController.setList(list, model.getType());
            secondController.setDescriptionVisible(false);
            view.setPanelComponent(secondController.getView());
        }
    }

    public PanelModelI getModel() {
        return model;
    }

    @Override
    public PanelView getView() {
        return view;
    }

    public List getListData(PageType type) {
        String codeId = model.getCodeIdFirstPage();
        return type == PageType.ERROR ? loadData.getErrors(model.getNumber(), codeId) : loadData.getRecipients(codeId);
    }

    private void openSession(String sessionName) {
        if (!StringUtil.isEmptyOrNull(sessionName) && !model.isOpenSession()) {
            Complex complex = model.getNavigatorModel().getComplex();
            model.setOpenSession(true);
            SessionController session = new SessionController(complex, sessionName);
            session.addCloseListener(new Window.CloseListener() {
                private static final long serialVersionUID = 8985091380311510167L;

                @Override
                public void windowClose(Window.CloseEvent e) {
                    model.setOpenSession(false);
                }
            });
        }
    }

    @Override
    public void setItem(int navigatorId, ContentItem item) {
        NavigatorModelAdapter navigatorModel = model.getNavigatorModel();
        navigatorModel.setItem(navigatorId, item.getPkey());
        resetPage();
        model.getNavigatorModel().changeUrlParams();
    }

    @Override
    public void modified(FilterModelI obj) {
        update();
    }

    public void setNavigatorController(NavigatorControllerI navigatorController) {
        this.navigatorController = navigatorController;
        NavigatorModelI navigatorModel = navigatorController.getModel();
        navigatorModel.addObserver(this);
        model.setNavigatorModel((NavigatorModelAdapter) navigatorModel);
    }

    public void setFilterController(FilterControllerI filterController) {
        this.filterController = filterController;
        FilterModelI filterModel = filterController.getModel();
        filterModel.addObserver(this);
        model.setFilterModel(filterModel);
    }
}