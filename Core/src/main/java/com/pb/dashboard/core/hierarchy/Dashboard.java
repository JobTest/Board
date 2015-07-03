package com.pb.dashboard.core.hierarchy;

/**
 * Created by vlad
 * Date: 03.10.14_11:28
 */
public class Dashboard {
    public static final String PATH = "";

    public static class Biplane {
        public static final String PATH = "biplane";

        public static class Payments {
            public static final String PATH = Biplane.PATH + "/payments";

            public static class Pay {
                public static final String PATH = Payments.PATH + "/pay";
            }

            public static class Operday {
                public static final String PATH = Payments.PATH + "/operday";
            }

            public static class Selection {
                public static final String PATH = Payments.PATH + "/selection";
            }

            public static class Timeline {
                public static final String PATH = Payments.PATH + "/timeline";
            }

            public static class Trends {
                public static final String PATH = Payments.PATH + "/trends";
            }

            public static class Sla {
                public static final String PATH = Payments.PATH + "/page";
            }
        }

        public static class Predmine {
            public static final String PATH = Biplane.PATH + "/predmine";
        }

        public static class Tests {
            public static final String PATH = Biplane.PATH + "/tests";

            public static class AllTests {
                public static final String PATH = Tests.PATH + "/alltests";
            }

            public static class Middleware {
                public static final String PATH = Tests.PATH + "/middleware";
            }

            public static class Middleware2 {
                public static final String PATH = Tests.PATH + "/middleware2";
            }
        }

        public static class DebtLoad {
            public static final String PATH = Biplane.PATH + "/debt-load";

            public static class Queue {
                public static final String NAME = "queue";
                public static final String PATH = DebtLoad.PATH + "/" + NAME;
            }

            public static class Load {
                public static final String NAME = "load";
                public static final String PATH = DebtLoad.PATH + "/" + NAME;
            }

            public static class Charts {
                public static final String NAME = "charts";
                public static final String PATH = DebtLoad.PATH + "/" + NAME;
            }
        }

        public static class Monitoring {
            public static final String PATH = Biplane.PATH + "/monitoring";

            public static class Timings {
                public static final String PATH = Monitoring.PATH + "/timings";
            }

            public static class Sessions {
                public static final String PATH = Monitoring.PATH + "/sessions";
            }

            public static class Errors {
                public static final String PATH = Monitoring.PATH + "/errors";

                public static class All {
                    public static final String NAME = "all";
                    public static final String PATH = Errors.PATH + "/" + NAME;
                }

                public static class Outer {
                    public static final String NAME = "outer";
                    public static final String PATH = Errors.PATH + "/" + NAME;
                }
            }
        }

        public static class Support {
            public static final String PATH = Biplane.PATH + "/support";

            public static class Errors {
                public static final String NAME = "errors";
                public static final String PATH = Support.PATH + "/" + NAME;
            }

            public static class Queries {
                public static final String NAME = "queries";
                public static final String PATH = Support.PATH + "/" + NAME;
            }

            public static class Nagios {
                public static final String NAME = "nagios";
                public static final String PATH = Support.PATH + "/" + NAME;
            }

            public static class Sessions {
                public static final String NAME = "sessions";
                public static final String PATH = Support.PATH + "/" + NAME;
            }
        }
    }

    public static class Templates {
        public static final String PATH = "templates";

        public static class Sessions {
            public static final String PATH = Templates.PATH + "sessions";
        }
    }

    public static class Tickets {
        public static final String PATH = "tickets";
    }

    public static class Quality {
        public static final String PATH = "quality";
    }
}
