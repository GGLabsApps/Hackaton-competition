package com.gglabs.materna.Model;

/**
 * Created by GG on 21/02/2018.
 */

public class Task {

    protected String _id;
    protected String userCreatedId;
    protected boolean isComplete;

    public Task(String userCreatedId) {
        this.userCreatedId = userCreatedId;
    }

    @Override
    public String toString() {
        return _id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;

        if (obj instanceof Task)
            return _id.equals(((Task) obj).getId());

        if (obj instanceof String)
            return _id.equals(obj);

        return super.equals(obj);
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public String getUserCreatedId() {
        return userCreatedId;
    }

    public void setUserCreatedId(String userCreatedId) {
        this.userCreatedId = userCreatedId;
    }

}