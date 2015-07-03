package com.pb.dashboard.vitrina.redmine;

import com.pb.dashboard.vitrina.redmine.db.RedmineDBManager;
import com.pb.dashboard.vitrina.redmine.project.Project;
import com.pb.dashboard.vitrina.redmine.project.ProjectController;
import com.pb.dashboard.vitrina.redmine.version.Version;
import com.pb.dashboard.vitrina.redmine.version.VersionController;
import com.vaadin.addon.charts.PointClickEvent;
import com.vaadin.addon.charts.PointClickListener;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.event.LayoutEvents;
import com.vaadin.ui.Table;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Redmine implements Serializable {

    public static final String[][] PROJECTS_TARGET = {
            {"biplane-web", "2.9 Доработки API", "2.10 Доработки API", "2.11 Доработки АПИ"},
            {"bpln-1", "2.9", "2.10", "2.11"},
            {"irbis", "2.2.8", "2.2.9", "2.2.10"},
            {"apiticket", "1.11", "1.12", "1.13"},
            {"debt", "2.7", "2.8", "2.11"},
            {"2pl-ekspl", ""},
            {"bpln-templ", "2.5", "2.7", "2.9"},
            {"bpln-2", ""},
            {"bpln-3", ""},
            {"bpln-4", ""},
            {"debt-onnline-standart", ""},
            {"2pl-report", ""},
            {"2pl-report-ru", ""},
            {"2pl-report-ua", ""},
            {"2pl-report-geo", ""},
            {"2pl-debt-off-line", ""},
            {"2pl-script", ""},
            {"2pl-auto-test", ""},
            {"linuksforpb", ""},
            {"komiss", ""},
            {"2pl-utils", ""},
            {"ru", ""},
            {"ap", ""},
            {"report", ""},
            {"svn", ""},
            {"skd", ""},
            {"mticket", ""},
            {"module-purse", ""},
            {"build", ""},
            {"pens-kart", ""},
            {"golomako1", ""},
            {"golomako2", ""},
            {"golomako3", ""},
            {"golomako5", ""},
            {"callbackgold", ""},
            {"migration-datacenter-georgia-project", ""},
            {"prepare-stage-migration-datacenter-georgia-project", ""},
            {"TG030990GTK", ""},
            {"iss", ""},
            {"ediniysprav", ""},
            {"obslklientoch", ""},
            {"podkltspkoch", ""},
            {"ohcforever", ""},
            {"dashboard", ""}
    };
    private RedmineDBManager dbManager;
    private ArrayList<ProjectController> projectControllers;

    public Redmine() {
        initCellControllers();
        initDBManager();
        initProjects();
    }

    private void initCellControllers() {
        projectControllers = new ArrayList<ProjectController>();
    }

    private void initDBManager() {
        dbManager = new RedmineDBManager();
    }

    private void initProjects() {
        for (int i = 0; i < PROJECTS_TARGET.length; i++) {
            Project project = new Project();

            String target = PROJECTS_TARGET[i][0];
            project.setTarget(target);

            String name = dbManager.getTargetToName(target);
            project.setName(name);

            ArrayList<String> versions = getVersions(i);

            ArrayList<RedmineTask> tasksOutVersions = dbManager.getTasks(target, versions);
            project.setTaskOutVersion(tasksOutVersions);

            for (int j = 0; j < versions.size(); j++) {
                ArrayList<RedmineTask> tasks = dbManager.getTasks(project.getTarget(), versions.get(j));
                project.addVersion(new Version(versions.get(j), tasks));
            }

            if (project.getAllTask().isEmpty()) {
                continue;
            }

            ProjectController controller = new ProjectController(project);
            projectControllers.add(controller);

            ArrayList<VersionController> versionControllers = controller.getVersionControllers();
            for (int j = 0; j < versionControllers.size(); j++) {
                VersionController versController = versionControllers.get(j);
                versController.getView().getChart().addPointClickListener(getClickListenerChart(versController, controller));
                versController.getView().getClickLabel().addLayoutClickListener(getClickListenerLabel(versController.getVersion().getPastTasks(), controller));
            }
            controller.getView().getTitle().addLayoutClickListener(getClickListenerLabel(tasksOutVersions, controller));
        }
    }

    private ArrayList<String> getVersions(int numberProject) {
        ArrayList<String> versions = new ArrayList<String>();
        for (int j = 0; j < PROJECTS_TARGET[numberProject].length - 1; j++) {
            versions.add(PROJECTS_TARGET[numberProject][j + 1]);
        }
        return versions;
    }

    public ArrayList<ProjectController> getProjects() {
        return projectControllers;
    }

    private PointClickListener getClickListenerChart(final VersionController c, final ProjectController pc) {
        return new PointClickListener() {
            @Override
            public void onClick(PointClickEvent event) {
                Table table = pc.getView().getTable();
                table.removeAllItems();

                ArrayList<RedmineTask> tasks;
                if (event.getSeries().getName().equalsIgnoreCase("Тип")) {
                    DataSeries dataSeries = c.getRedmineChart().getOutSeries();

                    String name = dataSeries.get(event.getPointIndex()).getName();
                    Version.Tracker tracker = Version.Tracker.getTracker(name);

                    String nameStasus = c.getRedmineChart().getInSeries().get(event.getPointIndex() / 2).getName();
                    Version.Status status = Version.Status.getStatus(nameStasus);

                    tasks = c.getVersion().getTasks(status, tracker);
                } else {
                    String nameStasus = c.getRedmineChart().getInSeries().get(event.getPointIndex() % 2).getName();
                    Version.Status status = Version.Status.getStatus(nameStasus);

                    tasks = c.getVersion().getTasks(status);
                }
                int count = tasks.size();
                addDataInTable(table, tasks);

                count = (((count > 8) ? 8 : count) + 1) * 30;
                updataTablePanel(pc, count);

//                Notification.show("Click: " + dataSeries.getName() + " " + event.getPointIndex());
            }
        };
    }

    private void addDataInTable(Table table, ArrayList<RedmineTask> tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            RedmineTask task = tasks.get(i);
            table.addItem(new Object[]{task.getId(), task.getSubject(), task.getType(), task.getUrl()}, i);
        }
    }

    private LayoutEvents.LayoutClickListener getClickListenerLabel(final ArrayList<RedmineTask> tasks, final ProjectController pc) {
        return new LayoutEvents.LayoutClickListener() {

            @Override
            public void layoutClick(LayoutEvents.LayoutClickEvent event) {
                Table table = pc.getView().getTable();
                table.removeAllItems();
//                ArrayList<RedmineTask> pastTasks = c.getVersion().getPastTasks();

                addDataInTable(table, tasks);

                int count = tasks.size();
                updataTablePanel(pc, count);
            }
        };
    }

    private void updataTablePanel(ProjectController pc, int count) {
        for (int i = 0; i < projectControllers.size(); i++) {
            if (pc == projectControllers.get(i) && count != 0) {
                pc.getView().open(count);
                pc.getView().getTablePanel().setVisible(true);
            } else {
                projectControllers.get(i).getView().open(-1);
                projectControllers.get(i).getView().getTablePanel().setVisible(false);
            }
        }
    }
}