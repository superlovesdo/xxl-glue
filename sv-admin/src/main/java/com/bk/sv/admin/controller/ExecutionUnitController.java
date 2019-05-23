package com.bk.sv.admin.controller;

import com.bk.sv.admin.core.model.ExecutionUnitLog;
import com.bk.sv.admin.core.model.ExecutionUnit;
import com.bk.sv.admin.core.model.Project;
import com.bk.sv.admin.core.result.ReturnT;
import com.bk.sv.admin.dao.IProjectDao;
import com.bk.sv.admin.service.IExecutionUnitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/glueinfo")
public class ExecutionUnitController {

    @Resource
    private IExecutionUnitService glueInfoService;
    @Resource
    private IProjectDao projectDao;

    @RequestMapping
    public String index(Model model) {

        List<Project> projectList = projectDao.loadAll();
        model.addAttribute("projectList", projectList);

        return "glueinfo/glueinfo.list";
    }

    @RequestMapping("/pageList")
    @ResponseBody
    public Map<String, Object> pageList(@RequestParam(required = false, defaultValue = "0") int start, @RequestParam(required = false, defaultValue = "10") int length, int projectId, String name) {
        return glueInfoService.pageList(start, length, projectId, name);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ReturnT<String> delete(int id) {
        return glueInfoService.delete(id);
    }

    @RequestMapping("/add")
    @ResponseBody
    public ReturnT<String> add(ExecutionUnit codeInfo) {
        return glueInfoService.add(codeInfo);
    }

    @RequestMapping("/update")
    @ResponseBody
    public ReturnT<String> update(ExecutionUnit codeInfo) {
        return glueInfoService.update(codeInfo);
    }

    @RequestMapping("/clearCache")
    @ResponseBody
    public ReturnT<String> clearCache(int id, String appNames) {
        return glueInfoService.clearCache(id, appNames);
    }

    @RequestMapping("/glueWebIde")
    public String codeSourceEditor(Model model, int id) {
        ExecutionUnit codeInfo = glueInfoService.load(id);
        model.addAttribute("codeInfo", codeInfo);

        if (codeInfo != null) {
            List<ExecutionUnitLog> executionUnitLogList = glueInfoService.loadLogs(id);
            model.addAttribute("codeLogList", executionUnitLogList);
        }

        return "glueinfo/glue.webide";
    }

    @RequestMapping("/updateCodeSource")
    @ResponseBody
    public ReturnT<String> updateCodeSource(HttpServletRequest request, ExecutionUnitLog executionUnitLog) {
        return glueInfoService.updateCodeSource(executionUnitLog);
    }

}
