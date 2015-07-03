package com.pb.dashboard.dao.entity.biplanesupport.db;

import com.pb.dashboard.core.util.IntegerUtil;

/**
 * Created by vlad
 * Date: 22.12.14_11:14
 */
public enum SessionType {

    MAX_OK(0, "max", "MAX (OK)"),
    MIN_OK(1, "min", "MIN (OK)"),
    MAX_ERRORS(2, "errors", "MAX (EXCEPTION)");

    private final int id;
    private final String name;
    private final String fullName;

    SessionType(int id, String name, String fullName) {
        this.id = id;
        this.name = name;
        this.fullName = fullName;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    public static SessionType idToType(String idStr) {
        if (IntegerUtil.checkInt(idStr)) {
            int id = Integer.parseInt(idStr);
            return idToType(id);
        }
        throw new IllegalArgumentException("Sessions category with id [" + idStr + "] not exists.");
    }

    public static SessionType idToType(int id) {
        for (SessionType category : values()) {
            if (category.getId() == id) {
                return category;
            }
        }
        throw new IllegalArgumentException("Sessions category with id [" + id + "] not exists.");
    }

    @Override
    public String toString() {
        return name;
    }
}
