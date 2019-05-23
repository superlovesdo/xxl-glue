package com.bk.sv.admin.dao;

import com.bk.sv.admin.core.model.ExecutionUnitLog;

import java.util.List;

public interface IExecutionUnitLogDao {

    int save(ExecutionUnitLog executionUnitLog);

    List<ExecutionUnitLog> findByGlueId(int glueId);

    int removeOldLogs(int glueId);

    int delete(int glueId);

}
