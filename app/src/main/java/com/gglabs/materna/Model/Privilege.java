package com.gglabs.materna.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GG on 15/02/2018.
 */

public class Privilege {

    public static final int ADMIN = 0;
    public static final int STORAGE = 1;
    public static final int DELIVERY = 2;
    public static final int DONATE = 100;

    private int privilege;

    public Privilege(int privilege) {
        this.privilege = privilege;
    }

    public int getPrivilege() {
        return privilege;
    }

    public void setPrivilege(int privilege) {
        this.privilege = privilege;
    }

    public static List<Privilege> getAllPrivileges() {
        List<Privilege> privileges = new ArrayList<>();
        for (int i = 1; i <= 4; i++) privileges.add(new Privilege(i));

        return privileges;
    }

    @Override
    public String toString() {
        return toString(privilege);
    }

    public static String toString(int privilege) {
        switch (privilege) {
            case ADMIN:
                return "Admin";

            case STORAGE:
                return "Department Manager";

            case DELIVERY:
                return "Team Head";

            case DONATE:
                return "Worker";

            default:
                return null;
        }
    }

}
