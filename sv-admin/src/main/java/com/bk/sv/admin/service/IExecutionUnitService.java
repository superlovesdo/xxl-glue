package com.bk.sv.admin.service;

import com.bk.sv.admin.core.model.ExecutionUnitLog;
import com.bk.sv.admin.core.model.ExecutionUnit;
import com.bk.sv.admin.core.result.ReturnT;

import java.util.List;
import java.util.Map;

public interface IExecutionUnitService {

    public Map<String, Object> pageList(int offset, int pagesize, int projectId, String name);

    public ReturnT<String> delete(int id);

    public ReturnT<String> add(ExecutionUnit codeInfo);

    ReturnT<String> update(ExecutionUnit codeInfo);

    public ExecutionUnit load(int id);

    public ReturnT<String> clearCache(int id, String appNames);

    public ReturnT<String> updateCodeSource(ExecutionUnitLog executionUnitLog);

    public List<ExecutionUnitLog> loadLogs(int glueId);

}
