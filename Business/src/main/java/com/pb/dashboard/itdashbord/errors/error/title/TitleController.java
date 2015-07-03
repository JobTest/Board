package com.pb.dashboard.itdashbord.errors.error.title;

import com.pb.dashboard.itdashbord.errors.ErrorsFiltersListener;
import com.pb.dashboard.itdashbord.errors.error.content.ContentController;
import com.pb.dashboard.itdashbord.errors.error.title.dater.DaterController;
import com.pb.dashboard.itdashbord.errors.error.title.downloader.DownloaderView;
import com.pb.dashboard.itdashbord.errors.error.title.downloader.WrightToXLS;
import com.pb.dashboard.itdashbord.errors.error.title.filter.FilterController;
import com.pb.dashboard.itdashbord.errors.error.title.search.SearchController;
import com.vaadin.data.Property;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;

public class TitleController extends VerticalLayout{

    private TitleView view;
    private SearchController searchController;
    private DaterController daterController;
    private FilterController filterController;
    private ContentController contentController;
    private ErrorsFiltersListener filter;

    public TitleController(ErrorsFiltersListener filter ) {
        this.filter = filter;

        init();
    }

    private void init() {

        searchController = new SearchController(filter);

        daterController = new DaterController(filter);

        filterController = new FilterController(filter);

        contentController = new ContentController(filter);
        addListeners();

        view = new TitleView();
        view.setWidth("100%");
        view.setHeight("150px");

        view.addComponent(searchController.getView(), 0, 0);
        view.setComponentAlignment(searchController.getView(), Alignment.MIDDLE_CENTER);

        view.addComponent(daterController.getView(), 1, 0);
        view.setComponentAlignment(daterController.getView(), Alignment.MIDDLE_CENTER);

        view.addComponent(filterController.getView(), 1, 1);
        view.setComponentAlignment(filterController.getView(), Alignment.MIDDLE_CENTER);

        addComponent(view);
        setExpandRatio(view, 1f);

        tableRepaint();
    }

    private void addListeners() {
        filterController.getView().getBranchButton().addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                removeComponent(contentController.getView());
                tableRepaint();
            }
        });
        filterController.getView().getGroupButton().addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                removeComponent(contentController.getView());
                tableRepaint();
            }
        });
        filterController.getView().getTypeDebtButton().addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                removeComponent(contentController.getView());
                tableRepaint();
            }
        });
        daterController.getView().getFrom().addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                final SimpleDateFormat sdf = new SimpleDateFormat("yyyy M d");
                filter.setDateFrom((sdf.format(daterController.getView().getFrom().getValue())));
                removeComponent(contentController.getView());
                tableRepaint();
            }
        });
        daterController.getView().getTo().addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                final SimpleDateFormat sdf = new SimpleDateFormat("yyyy M d");
                filter.setDateTo((sdf.format(daterController.getView().getTo().getValue())));
                removeComponent(contentController.getView());
                tableRepaint();
            }
        });
        searchController.getView().getSearchApply().addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                removeComponent(contentController.getView());
                tableRepaint();
            }
        });
    }

    private void tableRepaint() {
        contentController.tableRepaint();
        addComponent(contentController.getView());
        setExpandRatio(contentController.getView(), 2f);
        changeDownloadListener();
    }

    private void changeDownloadListener() {
        Button downloadBut = new DownloaderView().getXlsButton();
        final FileInputStream[] fis = {null};
        new WrightToXLS(contentController);
        final File file = new File("/home/petrik/XLSfolder/111.xls");
        StreamResource.StreamSource source = new StreamResource.StreamSource() {

            public InputStream getStream() {
                try {
                    fis[0] = new FileInputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                return fis[0];

            }
        };

        StreamResource resource = new StreamResource(source, file.getAbsolutePath());
        FileDownloader fileDownloader = new FileDownloader(resource);
        fileDownloader.extend(downloadBut);
        view.removeComponent(0,1);
        view.addComponent(downloadBut, 0, 1);
        view.setComponentAlignment(downloadBut, Alignment.MIDDLE_CENTER);
    }
}
