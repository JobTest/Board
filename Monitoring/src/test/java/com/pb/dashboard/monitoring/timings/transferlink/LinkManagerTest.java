package com.pb.dashboard.monitoring.timings.transferlink;

import com.pb.dashboard.core.model.Complex;
import com.pb.dashboard.core.model.Country;
import com.pb.dashboard.dao.entity.vitrinametrics.DInterface;
import com.pb.dashboard.dao.entity.vitrinametrics.DInterfaceI;
import com.pb.dashboard.dao.entity.vitrinametrics.InterfaceMetric;
import com.pb.dashboard.monitoring.components.filter.FilterModelI;
import com.pb.dashboard.monitoring.components.filter.FilterRange;
import com.pb.dashboard.monitoring.timings.navigator.NavigatorModelAdapter;
import org.joda.time.LocalDateTime;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(Parameterized.class)
public class LinkManagerTest {

    private static LocalDateTime date, from, to;
    private String category;
    private String expS;
    private String expT;

    private FilterModelI filterModel;
    private NavigatorModelAdapter navigatorModel;
    private LinkManager manager;

    static {
        date = new LocalDateTime(2015, 1, 1, 0, 0);
        from = new LocalDateTime(2014, 5, 11, 0, 0);
        to = new LocalDateTime(2014, 7, 1, 0, 0);
    }

    public LinkManagerTest(FilterRange range, Country country, Complex complex, DInterfaceI bpInterface,
                           InterfaceMetric metric, boolean reglament, String category, String expS, String expT) {
        filterModel = mock(FilterModelI.class);
        when(filterModel.getFilterRange()).thenReturn(range);
        when(filterModel.getDate()).thenReturn(date.toLocalDate());
        when(filterModel.getDateFrom()).thenReturn(from.toLocalDate());
        when(filterModel.getDateTo()).thenReturn(to.toLocalDate());
        when(filterModel.isReglament()).thenReturn(reglament);
        navigatorModel = mock(NavigatorModelAdapter.class);
        when(navigatorModel.getCountry()).thenReturn(country);
        when(navigatorModel.getComplex()).thenReturn(complex);
        when(navigatorModel.getBpInterface()).thenReturn(bpInterface);
        when(navigatorModel.getMetric()).thenReturn(metric);

        manager = new LinkManager(filterModel, navigatorModel);
        this.category = category;
        this.expS = expS;
        this.expT = expT;
    }

    @Parameterized.Parameters
    public static Collection primeNumbers() {
        return Arrays.asList(new Object[][]{
                {FilterRange.R10MIN, Country.UKRAINE, Complex.BIPLANE_API2X, new DInterface(11), new InterfaceMetric(12), false, "Ошибки",
                        "?country=1&complex=1&interface=11&from=2014-05-11_00.00&to=2014-07-01_00.00&criterion=2",
                        "?country=1&complex=1&interface=11&metric=12&range=0&date=2015-01-01&reglament=false"},
                {FilterRange.HOUR, Country.RUSSIA, Complex.DEBT, new DInterface(21), new InterfaceMetric(15), true, "Мин.",
                        "?country=2&complex=2&interface=21&from=2014-05-11_00.00&to=2014-07-01_00.00&criterion=1",
                        "?country=2&complex=2&interface=21&metric=15&range=1&date=2015-01-01&reglament=true"},
                {FilterRange.DAY, Country.UKRAINE, Complex.BIPLANE_API2X, new DInterface(11), new InterfaceMetric(12), true, "Макс.",
                        "?country=1&complex=1&interface=11&from=2014-05-11_00.00&to=2014-07-01_00.00&criterion=0",
                        "?country=1&complex=1&interface=11&metric=12&range=2&from=2014-05-11&to=2014-07-01"}
        });
    }

    @After
    public void tearDown() throws Exception {
        filterModel = null;
        navigatorModel = null;
        manager = null;
    }

    @Test
    public void testUrlSessions() throws Exception {
        String actual = manager.urlParamsSessions(from, to, category);
        assertEquals(expS, actual);
    }

    @Test
    public void testUrlTimings() throws Exception {
        String actual = manager.urlParamsTimings();
        assertEquals(expT, actual);
    }
}