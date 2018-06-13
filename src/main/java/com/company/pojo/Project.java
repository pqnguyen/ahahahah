package com.company.pojo;

import orm.lib.Annotation.Id;

public class Project {
    @Id
    private Integer projectId;
    private String title;

    @Override
    public String toString() {
        return "Project{" +
                "projectId=" + projectId +
                ", title='" + title + '\'' +
                '}';
    }
}
