package com.bk.sv.admin.dao.impl;

import com.bk.sv.admin.core.model.ExecutionUnitLog;
import com.bk.sv.admin.dao.IExecutionUnitLogDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class ExecutionUnitLogDaoImpl implements IExecutionUnitLogDao {

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    public int save(ExecutionUnitLog executionUnitLog) {
        return sqlSessionTemplate.insert("ExecutionUnitLogMapper.save", executionUnitLog);
    }

    @Override
    public List<ExecutionUnitLog> findByGlueId(int glueId) {
        return sqlSessionTemplate.selectList("ExecutionUnitLogMapper.findByGlueId", glueId);
    }

    @Override
    public int removeOldLogs(int glueId) {
        return sqlSessionTemplate.delete("ExecutionUnitLogMapper.removeOldLogs", glueId);
    }

    @Override
    public int delete(int glueId) {
        return sqlSessionTemplate.delete("ExecutionUnitLogMapper.delete", glueId);
    }

}
