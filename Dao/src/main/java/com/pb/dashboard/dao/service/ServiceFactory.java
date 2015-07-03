package com.pb.dashboard.dao.service;


/**
 * Created by vlad
 * Date: 02.04.15_10:57
 */
public class ServiceFactory {

    private static ServiceFactory factory = new ServiceFactory();
    private BiplaneServiceI biplane;
    private BusinessServiceI business;
    private ExternalServiceI external;
    private QualityServiceI quality;
    private TesterServiceI tester;
    private TicketsServiceI tickets;
    private MonitoringServiceI monitoring;

    public void setBiplane(BiplaneServiceI biplane) {
        factory.biplane = biplane;
    }

    public void setBusiness(BusinessServiceI business) {
        factory.business = business;
    }

    public void setExternal(ExternalServiceI external) {
        factory.external = external;
    }

    public void setQuality(QualityServiceI quality) {
        factory.quality = quality;
    }

    public void setTester(TesterServiceI tester) {
        factory.tester = tester;
    }

    public void setTickets(TicketsServiceI tickets) {
        factory.tickets = tickets;
    }

    public void setMonitoring(MonitoringServiceI timings) {
        factory.monitoring = timings;
    }

    public static BiplaneServiceI getBiplane() {
        return factory.biplane;
    }

    public static BusinessServiceI getBusiness() {
        return factory.business;
    }

    public static ExternalServiceI getExternal() {
        return factory.external;
    }

    public static QualityServiceI getQuality() {
        return factory.quality;
    }

    public static TesterServiceI getTester() {
        return factory.tester;
    }

    public static TicketsServiceI getTickets() {
        return factory.tickets;
    }

    public static MonitoringServiceI getMonitoring() {
        return factory.monitoring;
    }


}
