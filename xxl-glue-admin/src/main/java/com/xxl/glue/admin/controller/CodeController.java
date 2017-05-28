package com.xxl.glue.admin.controller;

import com.xxl.glue.admin.controller.annotation.PermessionLimit;
import com.xxl.glue.admin.core.model.CodeInfo;
import com.xxl.glue.admin.core.model.CodeLog;
import com.xxl.glue.admin.core.result.ReturnT;
import com.xxl.glue.admin.service.ICodeService;
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
@RequestMapping("/code")
public class CodeController {
	
	@Resource
	private ICodeService codeService;
	
	@RequestMapping
	@PermessionLimit
	public String index(){
		return "code/index";
	}
	
	@RequestMapping("/pageList")
	@ResponseBody
	@PermessionLimit
	public Map<String, Object> pageList(@RequestParam(required = false, defaultValue = "0") int start,  
			@RequestParam(required = false, defaultValue = "10") int length, String name){
		return codeService.pageList(start, length, name);
	}
	
	@RequestMapping("/delCode")
	@ResponseBody
	@PermessionLimit
	public ReturnT<String> delCode(int id){
		return codeService.delete(id);
	}
	
	@PermessionLimit
	@RequestMapping("/addCode")
	@ResponseBody
	public ReturnT<String> saveCode(CodeInfo codeInfo){
		return codeService.saveCodeInfo(codeInfo);
	}
	
	@RequestMapping("/codeSourceEditor")
	@PermessionLimit
	public String codeSourceEditor(Model model, int id){
		CodeInfo codeInfo = codeService.loadCode(id);
		model.addAttribute("codeInfo", codeInfo);
		
		if (codeInfo!=null) {
			List<CodeLog> codeLogs = codeService.loadLogs(codeInfo.getName());
			model.addAttribute("codeLogs", codeLogs);
		}
		
		return "code/code.editor";
	}
	
	@PermessionLimit
	@RequestMapping("/updateCodeSource")
	@ResponseBody
	public ReturnT<String> updateCodeSource(HttpServletRequest request, CodeInfo codeInfo){
		return codeService.updateCodeSource(codeInfo);
	}
	
	@RequestMapping("/clearCache")
	@ResponseBody
	@PermessionLimit
	public ReturnT<String> clearCache(int id, String appNames){
		return codeService.clearCache(id, appNames);
	}
	
	/*@RequestMapping("/demoEditor")
	@PermessionLimit
	public String demoEditor(HttpServletRequest request){
		return "code/demo.editor";
	}*/
}
