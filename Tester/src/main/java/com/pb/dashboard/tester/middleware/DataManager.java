package com.pb.dashboard.tester.middleware;

import com.pb.dashboard.tester.middleware.holder.EASInfoHolder;
import com.pb.dashboard.tester.middleware.holder.GFInfoHolder;
import com.pb.dashboard.tester.middleware.holder.ServerInfoHolder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataManager implements Serializable {

    private DataReader reader = new DataReader();
    private String easServerUser = "jenkins";
    private String easServerPassword = "jenkins";

    private String gfServerUser = "glassfish";
    private String gfServerPassword = "glassfish";

    private ServerInfoHolder serv8 = new ServerInfoHolder("10.13.71.8", gfServerUser, gfServerPassword);
    private ServerInfoHolder serv11 = new ServerInfoHolder("10.13.71.11", gfServerUser, gfServerPassword);
    private ServerInfoHolder serv24 = new ServerInfoHolder("10.13.71.24", easServerUser, easServerPassword);
    private ServerInfoHolder serv71 = new ServerInfoHolder("10.13.71.71", easServerUser, easServerPassword);
    private ServerInfoHolder serv167 = new ServerInfoHolder("10.1.96.167", easServerUser, easServerPassword);

    /* 10.13.71.24 */
    private EASInfoHolder[] serv24EasHolders = {
        new EASInfoHolder("/opt/sybaseEAS7300/"),
        new EASInfoHolder("/opt/sybaseEAS7400/"),
        new EASInfoHolder("/opt/sybaseEAS7500/"),
        new EASInfoHolder("/opt/sybaseEAS7600/"),
        new EASInfoHolder("/opt/sybaseEAS7700/"),
        new EASInfoHolder("/opt/sybaseEAS7800/"),
        new EASInfoHolder("/opt/sybaseEAS8100/"),
        new EASInfoHolder("/opt/sybaseEAS8200/"),
        new EASInfoHolder("/opt/sybaseEAS8400/")
    };

    /* 10.13.71.71 */
    private EASInfoHolder[] serv71EasHolders = {
        new EASInfoHolder("/opt/sybaseEAS7000/"),
        new EASInfoHolder("/opt/sybaseEAS7100/"),
        new EASInfoHolder("/opt/sybaseEAS7200/"),
        new EASInfoHolder("/opt/sybaseEAS7800/"),
        new EASInfoHolder("/opt/sybaseEAS7900/")
    };

    /* 10.1.96.167 */
    private EASInfoHolder[] serv167EasHolders = {
        new EASInfoHolder("/opt/sybaseEAS6200/"),
        new EASInfoHolder("/opt/sybaseEAS6800")
    };

                                             /* GF */
    /* 10.13.71.8 */
    private GFInfoHolder[] serv8GfHolders = {
        new GFInfoHolder("PL-1", "GF-2", "4949", "domain7"),
        new GFInfoHolder("PL-1", "GF-2", "4850", "domain2"),
        new GFInfoHolder("PL-1", "GF-2", "4852", "domain9"),
        new GFInfoHolder("PL-2", "GF-2", "4846", "domain2"),
        new GFInfoHolder("PL-2", "GF-2", "4858", "domain15"),
        new GFInfoHolder("PL-6", "GF-2", "4847", "domain4"),
        new GFInfoHolder("PL-6", "GF-2", "4854", "domain11"),
        new GFInfoHolder("PL-16", "GF-3", "4957", "domain9"),
    };

    /* 10.13.71.71 */
    private GFInfoHolder[] serv71GfHolders = {
        new GFInfoHolder("PL-1", "GF-2", "4843", "domain6"),
        new GFInfoHolder("PL-1", "GF-2", "4845", "domain5"),
        new GFInfoHolder("PL-1", "GF-2", "4949", "domain7"),
        new GFInfoHolder("PL-1", "GF-2", "4841", "domain12"),
        new GFInfoHolder("PL-1", "GF-2", "4842", "domain9"),
        new GFInfoHolder("PL-1", "GF-2", "4852", "domain14"),
        new GFInfoHolder("PL-1", "GF-2", "4853", "domain10"),
    };

    /* 10.13.71.11 */
    private GFInfoHolder[] serv11GfHolders = {
        new GFInfoHolder("PL-1", "GF-2", "5050", "domain2"),
    };

    public List<Object[]> getEasTableData() {
        List<Object[]> tableData = new ArrayList<Object[]>();
        serv24.setEasInfoHolders(serv24EasHolders);
        serv71.setEasInfoHolders(serv71EasHolders);
        serv167.setEasInfoHolders(serv167EasHolders);
        reader.loadServerInfoHolder(serv24);
        reader.loadServerInfoHolder(serv71);
        reader.loadServerInfoHolder(serv167);
        List<ServerInfoHolder> serverHolders = new ArrayList<ServerInfoHolder>();
        serverHolders.add(serv24);
        serverHolders.add(serv71);
        serverHolders.add(serv167);
        int id = 1;
        for (ServerInfoHolder serv : serverHolders) {
            EASInfoHolder[] easHolders = serv.getEasInfoHolders();
            for (EASInfoHolder eas : easHolders) {
                Object[] row = new Object[8]; //table columns
                row[0] = id + "";
                row[1] = "*";
                row[2] = serv.getServerIP();
                row[3] = eas.getPort();
                row[4] = eas.getDbIP();
                row[5] = eas.getDbName();
                String[] apps = eas.getApps();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < apps.length; i++) {
                    sb.append(apps[i]);
                    if (i != apps.length - 1) sb.append(", ");
                }
                row[6] = sb.toString();
                row[7] = eas.getBaseURL();
                if (!"biplane_dev".equalsIgnoreCase((String) row[5])) {
                    tableData.add(row);
                    id++;
                }
            }
        }
        return tableData;
    }

    public List<Object[]> getGfTableData() {
        List<Object[]> tableData = new ArrayList<Object[]>();
        serv8.setGfInfoHolders(serv8GfHolders);
        serv11.setGfInfoHolders(serv11GfHolders);
        serv71.setGfInfoHolders(serv71GfHolders);

        return tableData;
    }
}
