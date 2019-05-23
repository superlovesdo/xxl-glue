package com.bk.sv.admin.dao.impl;

import com.bk.sv.admin.core.model.ExecutionUnit;
import com.bk.sv.admin.dao.IExecutionUnitDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ExecutionUnitDaoImpl implements IExecutionUnitDao {

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<ExecutionUnit> pageList(int offset, int pagesize, int projectId, String name) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("offset", offset);
        params.put("pagesize", pagesize);
        params.put("projectId", projectId);
        params.put("name", name);

        return sqlSessionTemplate.selectList("ExecutionUnitMapper.pageList", params);
    }

    @Override
    public int pageListCount(int offset, int pagesize, int projectId, String name) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("offset", offset);
        params.put("pagesize", pagesize);
        params.put("projectId", projectId);
        params.put("name", name);

        return sqlSessionTemplate.selectOne("ExecutionUnitMapper.pageListCount", params);
    }

    @Override
    public int delete(int id) {
        return sqlSessionTemplate.delete("ExecutionUnitMapper.delete", id);
    }

    @Override
    public int save(ExecutionUnit codeInfo) {
        return sqlSessionTemplate.insert("ExecutionUnitMapper.save", codeInfo);
    }

    @Override
    public int update(ExecutionUnit codeInfo) {
        return sqlSessionTemplate.update("ExecutionUnitMapper.update", codeInfo);
    }

    @Override
    public ExecutionUnit load(int id) {
        return sqlSessionTemplate.selectOne("ExecutionUnitMapper.load", id);
    }

    @Override
    public ExecutionUnit loadCodeByName(String name) {
        return sqlSessionTemplate.selectOne("ExecutionUnitMapper.loadByName", name);
    }

}
