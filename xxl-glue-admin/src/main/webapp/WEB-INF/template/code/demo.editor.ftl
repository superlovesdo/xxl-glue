<#import "/common/common.macro.ftl" as netCommon>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Ace Kitchen Sink</title>
    <meta name="author" content="Fabian Jakobs">

    <link rel="stylesheet" href="${request.contextPath}/static/js/ace-demo/styles.css" type="text/css" media="screen" charset="utf-8">
</head>
<body>
<!-- 左侧Setting -->
<div id="optionsPanel" style="position:absolute;height:100%;width:260px">
  	<div style="position: absolute; overflow: hidden; top:0px; bottom:0">
  		<div style="width: 120%; height:100%; overflow-y: scroll">
			<table id="controls">
			    <tr>
			      	<td><label for="doc">Document</label></td>
			      	<td><select id="doc" size="1"></select></td>
			    </tr>
			    <tr>
			      	<td ><label for="mode">Mode</label></td>
			      	<td><select id="mode" size="1"></select></td>
			    </tr>
			    <tr>
			      	<td><label for="split">Split</label></td>
			      	<td>
						<select id="split" size="1">
				          	<option value="none">None</option>
				          	<option value="below">Below</option>
				          	<option value="beside">Beside</option>
			        	</select>
			      	</td>
			    </tr>
			    <tr>
			      	<td ><label for="theme">Theme</label></td>
			      	<td><select id="theme" size="1"></select></td>
			    </tr>
			    <tr>
			      	<td><label for="fontsize">Font Size</label></td>
			      	<td>
						<select id="fontsize" size="1">
							<option value="10px">10px</option>
				          	<option value="11px">11px</option>
				          	<option value="12px" selected="selected">12px</option>
				          	<option value="13px">13px</option>
				          	<option value="14px">14px</option>
				          	<option value="16px">16px</option>
				          	<option value="18px">18px</option>
				          	<option value="20px">20px</option>
				          	<option value="24px">24px</option>
				        </select>
			      	</td>
				</tr>
			    <tr>
			      	<td><label for="folding">Code Folding</label></td>
			      	<td>
						<select id="folding" size="1">
							<option value="manual">manual</option>
			          		<option value="markbegin" selected="selected">mark begin</option>
			          		<option value="markbeginend">mark begin and end</option>
		        		</select>
			      	</td>
			    </tr>
			    <tr>
			      	<td><label for="keybinding">Key Binding</label></td>
			      	<td>
			        	<select id="keybinding" size="1">
			          		<option value="ace">Ace</option>
			          		<option value="vim">Vim</option>
			          		<option value="emacs">Emacs</option>
			          		<option value="custom">Custom</option>
			        	</select>
			      	</td>
			    </tr>
				<tr>
			      	<td><label for="soft_wrap">Soft Wrap</label></td>
			      	<td>
				        <select id="soft_wrap" size="1">
							<option value="off">Off</option>
				          	<option value="40">40 Chars</option>
				          	<option value="80">80 Chars</option>
				          	<option value="free">Free</option>
				        </select>
					</td>
			    </tr>
			
			    <tr>
					<td colspan="2">
			    		<table id="more-controls">
						    <tr>
						      	<td><label for="select_style">Full Line Selection</label></td>
						      	<td><input type="checkbox" name="select_style" id="select_style" checked></td>
						    </tr>
						    <tr>
						      	<td><label for="highlight_active">Highlight Active Line</label></td>
						      	<td><input type="checkbox" name="highlight_active" id="highlight_active" checked></td>
						    </tr>
						    <tr>
						      	<td><label for="show_hidden">Show Invisibles</label></td>
						      	<td><input type="checkbox" name="show_hidden" id="show_hidden" checked></td>
						    </tr>
						    <tr>
						      	<td><label for="display_indent_guides">Show Indent Guides</label></td>
						      	<td><input type="checkbox" name="display_indent_guides" id="display_indent_guides" checked></td>
						    </tr>
						    <tr>
						      	<td><label for="show_hscroll">Persistent HScroll</label></td>
						      	<td>
									<input type="checkbox" name="show_hscroll" id="show_hscroll">
									<label for="show_hscroll">VScroll</label>
						        	<input type="checkbox" name="show_vscroll" id="show_vscroll">
						      	</td>
						    </tr>
						    <tr>
								<td><label for="animate_scroll">Animate scrolling</label></td>
						      	<td><input type="checkbox" name="animate_scroll" id="animate_scroll"></td>
						    </tr>
						    <tr>
						      	<td><label for="show_gutter">Show Gutter</label></td>
						      	<td><input type="checkbox" id="show_gutter" checked></td>
						    </tr>
						    <tr>
						      	<td><label for="show_print_margin">Show Print Margin</label></td>
						      	<td><input type="checkbox" id="show_print_margin" checked></td>
						    </tr>
						    <tr>
						      	<td><label for="soft_tab">Use Soft Tab</label></td>
						      	<td><input type="checkbox" id="soft_tab" checked></td>
						    </tr>
						    <tr>
						      	<td><label for="highlight_selected_word">Highlight selected word</label></td>
						      	<td><input type="checkbox" id="highlight_selected_word" checked></td>
						    </tr>
						    <tr>
						      	<td><label for="enable_behaviours">Enable Behaviours</label></td>
						      	<td><input type="checkbox" id="enable_behaviours"></td>
						    </tr>
						    <tr>
						      	<td><label for="fade_fold_widgets">Fade Fold Widgets</label></td>
						      	<td><input type="checkbox" id="fade_fold_widgets"></td>
						    </tr>
						    <tr>
						      	<td><label for="elastic_tabstops">Enable Elastic Tabstops</label></td>
						      	<td><input type="checkbox" id="elastic_tabstops"></td>
						    </tr>
						    <tr>
						      	<td><label for="isearch">Incremental Search</label></td>
						      	<td><input type="checkbox" id="isearch"></td>
						    </tr>
						    <tr>
						      	<td><label for="highlight_token">Show token info</label></td>
						      	<td><input type="checkbox" id="highlight_token"></td>
						    </tr>
						    <tr>
						      	<td><label for="read_only">Read-only</label></td>
						      	<td><input type="checkbox" id="read_only"></td>
						    </tr>
						    <tr>
						      	<td><label for="scrollPastEnd">Scroll Past End</label></td>
						      	<td><input type="checkbox" id="scrollPastEnd" checked></td>
						    </tr>
						    <tr>
						      	<td colspan="2"><input type="button" value="Edit Snippets" onclick="env.editSnippets()"></td>
						    </tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
	</div>
</div>

<div id="editor-container"></div>

<#--
1、避免阻塞，提高了 js 的加载性能；
2、通过编程使用 require 方法加载，而不是<script>硬写，更加灵活。
主数据:加载requirejs脚本的script标签加入了data-main属性，这个属性指定的js将在加载完reuqire.js后处理，我们把require.config的配置加入到data-main后，就可以使每一个页面都使用这个配置
data-main还有一个重要的功能，当script标签指定data-main属性时，require会默认的将data-main指定的js为根路径
require参数而为callback函数中发现有$参数，这个就是依赖的jquery模块的输出变量，如果你依赖多个模块，可以依次写入多个参数来使用
通过require加载的模块一般都需要符合AMD规范即使用define来申明模块，但是部分时候需要加载非AMD规范的js，这时候就需要用到另一个功能：shim
shim解释起来也比较难理解，shim直接翻译为"垫"，其实也是有这层意思的，目前我主要用在两个地方
1. 插件形式的非AMD模块，我们经常会用到jquery插件，而且这些插件基本都不符合AMD规范，比如jquery.form插件，这时候就需要将form插件"垫"到jquery中：
-->
<script src="${request.contextPath}/static/plugins/requirejs/requirejs.2.1.22.min.js" data-main="${request.contextPath}/static/js/requirejs.config" ></script>
<script src="${request.contextPath}/static/js/requirejs.config.js" ></script> <!-- 必须手动引用该main入口函数，否则10%几率应为引用地址错误导致部分引用404，费解 -->
<script> var base_url = '${request.contextPath}/'; </script>

</body>
</html>
