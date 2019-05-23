package com.bk.sv.admin.dao;

import com.bk.sv.admin.core.model.Project;

import java.util.List;

public interface IProjectDao {

    int save(Project project);

    int update(Project project);

    int delete(int id);

    List<Project> loadAll();

    Project findByAppname(String appname);

    Project load(int id);

}
