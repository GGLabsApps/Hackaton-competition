package com.gglabs.materna.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GG on 15/02/2018.
 */

public class Department {

    public static final int NO_DEPARTMENT = 0;
    public static final int RECEPTION = 1;
    public static final int HOUSE_HOLD = 2;
    public static final int ROOM_SERVICE = 3;
    public static final int SECURITY = 4;

    private int department;

    public Department(int department) {
        this.department = department;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public static List<Department> getAllDepartments() {
        List<Department> departments = new ArrayList<>();
        for (int i = 0; i <= 4; i++) departments.add(new Department(i));

        return departments;
    }

    @Override
    public String toString() {
        return toString(department);
    }

    public static String toString(int department) {
        switch (department) {
            case NO_DEPARTMENT:
                return "No Department";

            case RECEPTION:
                return "Reception";

            case HOUSE_HOLD:
                return "Household";

            case ROOM_SERVICE:
                return "Room Service";

            case SECURITY:
                return "Security";
        }

        return "Error, no such department";
    }

}
