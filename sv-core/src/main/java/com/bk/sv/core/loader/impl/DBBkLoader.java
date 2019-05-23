package com.bk.sv.core.loader.impl;

import com.bk.sv.core.loader.BkLoader;
import com.bk.sv.core.loader.util.DBUtil;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * Created by BK on 17/5/29.
 */
public class DBBkLoader implements BkLoader {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public String load(String name) {
        String sql = "SELECT source FROM sv_execution_unit WHERE name = ?";
        List<Map<String, Object>> result = DBUtil.query(dataSource, sql, new Object[]{name});
        if (result != null && result.size() == 1 && result.get(0) != null && result.get(0).get("source") != null) {
            return (String) result.get(0).get("source");
        }
        return null;
    }
}
