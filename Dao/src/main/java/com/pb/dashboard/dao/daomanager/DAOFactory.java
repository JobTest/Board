package com.pb.dashboard.dao.daomanager;


import com.pb.dashboard.dao.dbmanager.AseDbManager;
import com.pb.dashboard.dao.dbmanager.IqDbManager;

/**
 * Created by vlad
 * Date: 01.04.15_12:03
 */
public class DAOFactory {

    private VitrinaMetricsDAOI vitrinaMetrics;
    private DashboardDAOI dashboard;
    private BiplaneDescriptionDAOI biplaneDescription;
    private BiplanePl2DAOI biplanePl2;
    private BiplaneSupportDAOI biplaneSupport;
    private IqDbManager iqdbManager = IqDbManager.getInstance();
    private AseDbManager aseDbManager = AseDbManager.getInstance();

    public void setVitrinaMetrics(VitrinaMetricsDAOI vitrinaMetrics) {
        this.vitrinaMetrics = vitrinaMetrics;
    }

    public void setDashboard(DashboardDAOI dashboard) {
        this.dashboard = dashboard;
    }

    public void setBiplaneDescription(BiplaneDescriptionDAOI biplaneDescription) {
        this.biplaneDescription = biplaneDescription;
    }

    public void setBiplanePl2(BiplanePl2DAOI biplanePl2) {
        this.biplanePl2 = biplanePl2;
    }

    public void setBiplaneSupport(BiplaneSupportDAOI biplaneSupport) {
        this.biplaneSupport = biplaneSupport;
    }

    public VitrinaMetricsDAOI getVitrinaMetrics() {
        return vitrinaMetrics;
    }

    public DashboardDAOI getDashboard() {
        return dashboard;
    }

    public BiplaneDescriptionDAOI getBiplaneDescription() {
        return biplaneDescription;
    }

    public BiplanePl2DAOI getBiplanePl2() {
        return biplanePl2;
    }

    public BiplaneSupportDAOI getBiplaneSupport() {
        return biplaneSupport;
    }

    public IqDbManager getIqdbManager() {
        return iqdbManager;
    }

    public AseDbManager getAseDbManager() {
        return aseDbManager;
    }
}
