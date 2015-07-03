package com.pb.dashboard.monitoring.errors.all.db.pl;

/**
 * Created by vlad
 * Date: 05.11.14_14:45
 */
public final class PLQueryBuilder {

    private static final String SEPARATOR_CODE = "_";
    private static final int COUNT_CODE = 2;
    private static final int FIRST_ELEMENT = 0;
    private static final int SECOND_ELEMENT = 1;

    private PLQueryBuilder() {
    }

    public static String getDecodingErrorSelect(String codesStr) {
        String[] codes = splitCode(codesStr);
        String query = "select e.text_ru from _2pl.ERRORS as e JOIN _2pl.SYSTEMS as s on e.pkey_system=s.pkey where s.cod = '%s' and e.code = '%s' and last=1";
        return String.format(query, codes[FIRST_ELEMENT], codes[SECOND_ELEMENT]);
    }

    public static String getInfoErrorsSelect(String codesStr) {
        String[] codes = splitCode(codesStr);
        String query = "select e.text_ru, e.reason, e. recommend, \"group\" as gr, r.responsible, s.system, t.type from _2pl.ERRORS as e" +
                " left join _2pl.GROUPS as g on g.pkey = e.pkey_group" +
                " left join _2pl.RESPONSIBLE as r on r.pkey = e.pkey_responsible" +
                " left join _2pl.SYSTEMS as s on s.pkey = e.pkey_system" +
                " left join _2pl.TYPES as t on t.pkey = e.pkey_type" +
                " where  s.cod = '%s' and e.code = '%s' and last=1";
        return String.format(query, codes[FIRST_ELEMENT], codes[SECOND_ELEMENT]);
    }

    public static String getTypesSelect() {
        return "select t.pkey,t.type from _2pl._2pl.TYPES t";
    }

    public static String getGroupsSelect() {
        return "select * from _2pl._2pl.GROUPS";
    }

    public static String getResponsibilitySelect() {
        return "select * from _2pl._2pl.RESPONSIBLE";
    }

    public static String getConsumersSelect() {
        return "select c.pkey,c.name from _2pl.consumers c";
    }

    /**
     * Разделение codes на system_code и code
     *
     * @param codes code error. example: KC_Err1210
     * @return two parts. example: "KC_" and "Err1210"
     */
    private static String[] splitCode(String codes) {
        String[] res = new String[COUNT_CODE];
        String[] split = codes.split(SEPARATOR_CODE, COUNT_CODE);
        res[FIRST_ELEMENT] = split[FIRST_ELEMENT] + SEPARATOR_CODE;
        res[SECOND_ELEMENT] = split.length > 1 ? split[SECOND_ELEMENT] : "";
        return res;
    }
}
