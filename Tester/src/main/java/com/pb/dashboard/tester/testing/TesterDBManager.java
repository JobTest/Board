package com.pb.dashboard.tester.testing;

import com.pb.dashboard.core.db.DBManager;
import com.pb.dashboard.core.db.ResourceNames;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TesterDBManager extends DBManager {

    private static final Logger LOG = Logger.getLogger(TesterDBManager.class);
    private static final ResourceNames RESOURCE_NAMES = ResourceNames.TESTER;

    @Override
    public Logger getLog() {
        return LOG;
    }

    @Override
    public ResourceNames getResource() {
        return RESOURCE_NAMES;
    }

    public ArrayList<TestsTesters> getTestsTesterses() {
        ArrayList<TestsTesters> testsTesterses = new ArrayList<TestsTesters>();
        String query = "exec dbo.__2PL_GetDynamicsTestsTesters()";
        ResultSet rs = null;
        try {
            rs = getRSByStmt(query);
            while (rs.next()) {
                TestsTesters testsTesters = new TestsTesters();
                testsTesters.setName(rs.getString("name"));
                testsTesters.setCountAutoTestWeekNow(rs.getInt("cwk_c_count_test"));
                testsTesters.setCountAutoTestWeekPrev(rs.getInt("cwk_p_count_test"));
                testsTesters.setCountAutoTestMonthNow(rs.getInt("mm_c_count_test"));
                testsTesters.setCountAutoTestMonthPrev(rs.getInt("mm_p_count_test"));
                testsTesters.setCountAutoTestQuarterNow(rs.getInt("qq_c_count_test"));
                testsTesters.setCountAutoTestQuarterPrev(rs.getInt("qq_p_count_test"));

                testsTesters.setCountManualTestWeekNow(rs.getInt("cwk_c_m_count_test"));
                testsTesters.setCountManualTestWeekPrev(rs.getInt("cwk_p_m_count_test"));
                testsTesters.setCountManualTestMonthNow(rs.getInt("mm_c_m_count_test"));
                testsTesters.setCountManualTestMonthPrev(rs.getInt("mm_p_m_count_test"));
                testsTesters.setCountManualTestQuarterNow(rs.getInt("qq_c_m_count_test"));
                testsTesters.setCountManualTestQuarterPrev(rs.getInt("qq_p_m_count_test"));
                testsTesterses.add(testsTesters);
            }
        } catch (SQLException e) {
            LOG.error(e);
        }
        return testsTesterses;
    }
}