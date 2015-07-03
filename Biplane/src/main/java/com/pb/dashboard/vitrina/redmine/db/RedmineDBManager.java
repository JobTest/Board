package com.pb.dashboard.vitrina.redmine.db;

import com.pb.dashboard.core.db.DBManager;
import com.pb.dashboard.core.db.ResourceNames;
import com.pb.dashboard.vitrina.redmine.RedmineTask;
import com.pb.dashboard.vitrina.redmine.version.Version;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Link;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RedmineDBManager extends DBManager implements Serializable {

    private static final Logger LOG = Logger.getLogger(RedmineDBManager.class);
    private static final ResourceNames DATABASE = ResourceNames.REDMINE;

    @Override
    public Logger getLog() {
        return LOG;
    }

    @Override
    public ResourceNames getResource() {
        return DATABASE;
    }

    public ArrayList<RedmineTask> getTasks(String target, ArrayList<String> outVersion) {
        ArrayList<RedmineTask> redmineTasks = new ArrayList<RedmineTask>();
        String query = getTasksQueryOutVersion(target, outVersion);
        ResultSet rs = null;
        LOG.debug("Query: " + query);
        try {
            rs = getRSByStmt(query);
            while (rs.next()) {
                RedmineTask task = new RedmineTask();
                task.setId(rs.getString("id"));
                task.setSubject(rs.getString("subject"));
                task.setStatus(rs.getString("status"));
                task.setType(rs.getString("tracker"));
                task.setData(rs.getString("due_date"));
                String url = "https://itsm.privatbank.ua/predmine/" + target + "/issue/" + task.getId() + ".htm";
                Link link = new Link("ссылка", new ExternalResource(url));
                link.setTargetName("_blank");
                task.setUrl(link);
                redmineTasks.add(task);
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        return redmineTasks;
    }

    private String getTasksQueryOutVersion(String target, ArrayList<String> outVersion) {
        return "select i.id, i.subject, i.due_date, v.name version, s.name status, t.name tracker " +
                "from Redmine.issue i " +
                "left join Redmine.project p on i.project_id=p.id " +
                "LEFT join Redmine.version v on i.fixed_version_id=v.id " +
                "LEFT JOIN Redmine.status s on i.status_id=s.id " +
                "left JOIN Redmine.tracker t on t.id=i.tracker_id " +
                "where p.target = '" + target + '\'' + ((outVersion.isEmpty()) ? "" : (" and v.name NOT IN(" + getList(outVersion) + ") ")) +
                "and i.due_date!='' " +
                "and i.status_id NOT IN(5,6,9)" +
                "and i.due_date<CURRENT_DATE";
    }

    private String getList(ArrayList<String> list) {
        if (list.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append('\'' + s + "\',");
        }
        sb.delete(sb.length() - 1, sb.length());
        return sb.toString();
    }

    public ArrayList<RedmineTask> getTasks(String target, String version) {
        ArrayList<RedmineTask> tasks = new ArrayList<RedmineTask>();
        Statement stmt = null;
        try {
            String query = getTasksQueryInVersion(target, version);
            LOG.debug("Query: " + query);

            ResultSet rs = getRSByStmt(query);
            final String name = Version.Status.DELETED.getName();
            while (rs.next()) {
                String status = rs.getString("status");
                if (!name.equalsIgnoreCase(status)) {
                    RedmineTask task = new RedmineTask();
                    task.setId(rs.getString("id"));
                    task.setType(rs.getString("type"));
                    task.setSubject(rs.getString("subject"));
                    task.setData(rs.getString("due_date"));
                    task.setStatus(status);
                    String url = "https://itsm.privatbank.ua/predmine/" + target + "/issue/" + task.getId() + ".htm";
                    Link link = new Link("ссылка", new ExternalResource(url));
                    link.setTargetName("_blank");
                    task.setUrl(link);
                    tasks.add(task);
                }
            }
        } catch (SQLException e) {
            LOG.error(e);
        }
        return tasks;
    }

    private String getTasksQueryInVersion(String target, String version) {
        StringBuilder sb = new StringBuilder("SELECT ");
        sb.append("i.id,i.subject 'subject',t.name 'type',s.name 'status',i.start_date,i.due_date ");
        sb.append("FROM Redmine.issue i ");
        sb.append("LEFT JOIN Redmine.project p ");
        sb.append("ON i.project_id=p.id ");
        sb.append("LEFT JOIN Redmine.tracker t ");
        sb.append("ON i.tracker_id=t.id ");
        sb.append("LEFT JOIN Redmine.version v ");
        sb.append("ON i.fixed_version_id=v.id ");
        sb.append("LEFT JOIN Redmine.status s ");
        sb.append("ON i.status_id=s.id ");
        sb.append("WHERE p.target=").append(getSQLString(target) + ' ');
        if (version != null && !version.isEmpty())
            sb.append("AND v.name=").append(getSQLString(version));
        return sb.toString();
    }

    public ArrayList<String> getVersion(String target) {
        ArrayList<String> res = new ArrayList<String>(2);
        ResultSet rs = null;
        try {
            String query = getVersionSQL(target);
            LOG.debug("Query: " + query);
            rs = getRSByStmt(query);
            while (rs.next()) {
                res.add(rs.getString("version"));
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        if (res.isEmpty()) {
            res.add(null);
        }
        return res;
    }

    private String getVersionSQL(String target) {
        return "SELECT v.name 'version' FROM Redmine.version v LEFT JOIN Redmine.project p ON v.project_id = p.id WHERE p.target = " + getSQLString(target) + " AND v.name LIKE '%.%' ORDER BY v.name DESC LIMIT 3";
    }

    private String getSQLString(String str) {
        StringBuilder sb = new StringBuilder("'");
        sb.append(str).append("'");
        return sb.toString();
    }

    public String getTargetToName(String target) {
        ResultSet rs = null;
        try {
            String query = getTargetQuery(target);
            LOG.debug("Query: " + query);
            rs = getRSByStmt(query);
            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (SQLException e) {
            LOG.error(e);
        }
        return target;
    }

    private String getTargetQuery(String target) {
        return "SELECT p.name FROM Redmine.project p WHERE p.target = " + getSQLString(target);
    }
}