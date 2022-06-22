package com.example.new_androidclient.work.bean;

public class WorkDTApplicationBean extends WorkApplicationBaseBean {
    /**
     *  "workingContent":null,
     *     "isUpgradeMgt":null,
     *     "workingPerson":null,
     *   "workingPlace":null,
     **/

    private String workingContent;
    private String isUpgradeMgt;
    private String workingPerson;
    private String workingPlace;

    public String getWorkingContent() {
        return workingContent;
    }

    public void setWorkingContent(String workingContent) {
        this.workingContent = workingContent;
    }

    public String getIsUpgradeMgt() {
        return isUpgradeMgt;
    }

    public void setIsUpgradeMgt(String isUpgradeMgt) {
        this.isUpgradeMgt = isUpgradeMgt;
    }

    public String getWorkingPerson() {
        return workingPerson;
    }

    public void setWorkingPerson(String workingPerson) {
        this.workingPerson = workingPerson;
    }

    public String getWorkingPlace() {
        return workingPlace;
    }

    public void setWorkingPlace(String workingPlace) {
        this.workingPlace = workingPlace;
    }
}
