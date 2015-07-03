package com.pb.dashboard.itdashbord.errors.error.title.downloader;

import com.pb.dashboard.itdashbord.errors.db.container.ErrorsData;
import com.pb.dashboard.itdashbord.errors.error.content.ContentController;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class WrightToXLS {

    public WrightToXLS(ContentController contentController) {
        try {
            File file = new File("/home/petrik/XLSfolder/111.xls");
            if (file.exists()){
                file.createNewFile();
            }
            WritableWorkbook workbook = Workbook.createWorkbook(file);
            WritableSheet sheet = workbook.createSheet("First Sheet", 0);
            int count = 1;
            List<ErrorsData> datas = contentController.getSettedData();
            for (ErrorsData data: datas){
                for (int i = 0; i < data.getAsArray().length; i++) {
                    Label label = new Label(i, count, data.getAsArray()[i]);
                    sheet.addCell(label);
                }
                count++;
            }
            workbook.write();
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RowsExceededException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }
}
