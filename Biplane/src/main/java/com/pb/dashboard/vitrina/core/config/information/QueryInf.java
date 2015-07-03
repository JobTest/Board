package com.pb.dashboard.vitrina.core.config.information;

public interface QueryInf {
    //Кол-во  платежей

    public final String KASSA_M_All = "select mt.pkey, sum(m.value) as value, m.day from  dbo.metrics m join dbo.metrics_type mt on mt.pkey = m.pkey_type_metrics where mt.hour_bind=1 and mt.pkey=1 or mt.pkey=2 or mt.pkey=3 group by mt.pkey,mt.name,m.day";
    public final String BASS_M_All = "select mt.pkey, sum(m.value) as value, m.day from  dbo.metrics m join dbo.metrics_type mt on mt.pkey = m.pkey_type_metrics where mt.hour_bind=1 and mt.pkey=10 or mt.pkey=11 or mt.pkey=12 group by mt.pkey,mt.name,m.day";
    public final String P24_M_All = "select mt.pkey, sum(m.value) as value, m.day from  dbo.metrics m join dbo.metrics_type mt on mt.pkey = m.pkey_type_metrics where mt.hour_bind=1 and mt.pkey=17 or mt.pkey=18 group by mt.pkey,mt.name,m.day";
    public final String M_ALL = "select mt.pkey, sum(m.value) as value, m.day from  dbo.metrics m join dbo.metrics_type mt on mt.pkey = m.pkey_type_metrics where mt.hour_bind=1 and mt.pkey=1 or mt.pkey=2 or mt.pkey=3 or mt.pkey=10 or mt.pkey=11 or mt.pkey=12 or mt.pkey=17 or mt.pkey=18 and m.day >=? and m.day <=? group by mt.pkey,mt.name,m.day";

    //Все метрики за текущий час
    public final String ALL_METRICS = "select mt.pkey,mt.name, m.value from dbo.metrics m join dbo.metrics_type mt on mt.pkey = m.pkey_type_metrics where  day = convert(int,convert(char(8),getdate(),112))  and hour = datepart(hh,getdate()) and mt.hour_bind<>0";
    public final String ALL_METRICS_BYDAY = "select mt.pkey,mt.name, m.value from dbo.metrics m join dbo.metrics_type mt on mt.pkey = m.pkey_type_metrics where  day = ?  and hour = datepart(hh,getdate()) and mt.hour_bind<>0";
    //Все метрики за прошлый час
    public final String ALL_METRICS_Pre = "select mt.pkey,mt.name, m.value from dbo.metrics m join dbo.metrics_type mt on mt.pkey = m.pkey_type_metrics where  day = convert(int,convert(char(8),getdate(),112))  and hour = datepart(hh,getdate())-1 and mt.hour_bind<>0";
    public final String ALL_METRICS_Pre_BYDAY = "select mt.pkey,mt.name, m.value from dbo.metrics m join dbo.metrics_type mt on mt.pkey = m.pkey_type_metrics where  day = ?  and hour = datepart(hh,getdate())-1 and mt.hour_bind<>0";
    //Все метрики два часа назад
    public final String ALL_METRICS_2Pre = "select mt.pkey,mt.name, m.value from dbo.metrics m join dbo.metrics_type mt on mt.pkey = m.pkey_type_metrics where  day = convert(int,convert(char(8),getdate(),112))  and hour = datepart(hh,getdate())-2 and mt.hour_bind<>0";
    public final String ALL_METRICS_2Pre_BYDAY = "select mt.pkey,mt.name, m.value from dbo.metrics m join dbo.metrics_type mt on mt.pkey = m.pkey_type_metrics where  day = ?  and hour = datepart(hh,getdate())-2 and mt.hour_bind<>0";
    //Все метрики три часа назад
    public final String ALL_METRICS_3Pre = "select mt.pkey,mt.name, m.value from dbo.metrics m join dbo.metrics_type mt on mt.pkey = m.pkey_type_metrics where  day = convert(int,convert(char(8),getdate(),112))  and hour = datepart(hh,getdate())-3 and mt.hour_bind<>0";
    public final String ALL_METRICS_3Pre_BYDAY = "select mt.pkey,mt.name, m.value from dbo.metrics m join dbo.metrics_type mt on mt.pkey = m.pkey_type_metrics where  day = ?  and hour = datepart(hh,getdate())-3 and mt.hour_bind<>0";

    //Сумма метрик за весь день
    public final String ALL_METRICS_SUMM = "select mt.pkey,mt.name, sum(m.value) as value from  dbo.metrics m join dbo.metrics_type mt on mt.pkey = m.pkey_type_metrics where  day = convert(int,convert(char(8),getdate(),112)) and mt.hour_bind=1 group by mt.pkey,mt.name";
    public final String ALL_METRICS_SUMM_BY_DAY = "select mt.pkey,mt.name, sum(m.value) as value from  dbo.metrics m join dbo.metrics_type mt on mt.pkey = m.pkey_type_metrics where  day = ? and mt.hour_bind=1 group by mt.pkey,mt.name";
    //Все метрики что независят от часа
    public final String ALL_NONE_HOUR_METRICS = "select mt.pkey,mt.name, m.value from  dbo.metrics m join dbo.metrics_type mt on mt.pkey = m.pkey_type_metrics where  day = convert(int,convert(char(8),getdate(),112))   and mt.hour_bind=0";
    //Статистика
    public final String KK_DAY_STATS = "select mt.pkey,mt.name, m.value, m.hour from  dbo.metrics m join dbo.metrics_type mt on mt.pkey = m.pkey_type_metrics where  day = 20120725  and mt.hour_bind=1 and mt.pkey=44 or mt.pkey=45 or mt.pkey=46 or mt.pkey=47 group by mt.pkey,mt.name, m.value, m.hour";
    public final String KBP_DAY_STATS2 = "select mt.pkey,mt.name, m.value, m.hour from  dbo.metrics m join dbo.metrics_type mt on mt.pkey = m.pkey_type_metrics where  day = convert(int,convert(char(8),getdate(),112))  and mt.hour_bind=1";
    //Получить статистику за определеную дату
    public final String CURRENT_DAY_METRICS = "select mt.pkey, sum(m.value) as value, m.day, m.hour from  dbo.metrics m join dbo.metrics_type mt on mt.pkey = m.pkey_type_metrics where  day = ? and hour_bind=1 group by mt.pkey,mt.name,m.day,m.hour ";

    //Сумма метрик за определенный период
//    public final String METRIC_SUM_BY_PERIOD = "select day,mt.pkey,sum(m.value) as value from  dbo.metrics m join dbo.metrics_type mt on mt.pkey = m.pkey_type_metrics where  day between ? and ? and mt.hour_bind=1 group by mt.pkey,mt.name,day";
    public final String METRIC_SUM_BY_PERIOD = "set forceplan on select day,mt.pkey,sum(m.value) as value from  dbo.metrics m join dbo.metrics_type mt on mt.pkey = m.pkey_type_metrics where day between ? and ? and mt.hour_bind=1 group by mt.pkey,mt.name,day set forceplan off";
}
