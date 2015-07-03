package com.pb.dashboard.itdashbord.errors.errorSession.errorDownloader;

import com.pb.dashboard.itdashbord.errors.db.container.ErrByCompany;
import com.pb.dashboard.itdashbord.errors.errorSession.tableComponent.ErrorsContentControler;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import java.io.*;
import java.util.List;

public class ErrorDownloaderView extends HorizontalLayout {

    private Button xlsButton;

    public ErrorDownloaderView(ErrorsContentControler errorsContentControler) {
        setWidth("200px");
        xlsButton = new Button();
        xlsButton.setImmediate(true);
        xlsButton.setIcon(new ThemeResource("icons/xls.jpeg"));
        final File file;
        addComponent(xlsButton);
        setComponentAlignment(xlsButton, Alignment.MIDDLE_CENTER);
        try {
            final FileInputStream[] fis = {null};
            file = new File("/home/petrik/XLSfolder/121.xls");
            if (file.exists()){
                file.createNewFile();
            }
            WritableWorkbook workbook = Workbook.createWorkbook(file);
            WritableSheet sheet = workbook.createSheet("First Sheet", 0);
            int count = 1;
            List <ErrByCompany> data = errorsContentControler.getData();
            for (ErrByCompany d: data){
                for (int i = 0; i < d.errByCompanyAsStringArr().length; i++) {
                    Label label = new Label(i, count, d.errByCompanyAsStringArr()[i]);
                    sheet.addCell(label);
                }
                count++;
            }
            workbook.write();
            workbook.close();
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
            fileDownloader.extend(this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RowsExceededException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }

}
