package com.bk.sv.admin.dao;

import com.bk.sv.admin.core.model.ExecutionUnit;

import java.util.List;

public interface IExecutionUnitDao {

    List<ExecutionUnit> pageList(int offset, int pagesize, int projectId, String name);

    int pageListCount(int offset, int pagesize, int projectId, String name);

    int delete(int id);

    int save(ExecutionUnit codeInfo);

    int update(ExecutionUnit codeInfo);

    ExecutionUnit load(int id);

    ExecutionUnit loadCodeByName(String name);

}
