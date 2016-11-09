package org.apache.jsp.WEB_002dINF.jsp.biz;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import com.redstoneinfo.platform.entity.Agent;

public final class orderList_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=utf-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<HTML>\r\n");
      out.write("\r\n");
      out.write("<HEAD>\r\n");
      out.write("<title>");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${projectName}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("</title>\r\n");

	request.setAttribute("base", request.getContextPath());

      out.write("\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${base}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/easyui/themes/default/easyui.css\">\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${base}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/easyui/themes/icon.css\">\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${base}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/css/pf.css\">\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${base}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/easyui/demo.css\">\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${base}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/easyui/jquery-1.8.0.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${base}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/easyui/jquery.easyui.min.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${base}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/easyui/easyui-lang-zh_CN.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<META http-equiv=Content-Type content=\"text/html; charset=utf-8\">\r\n");
      out.write("<style type=\"text/css\">  \r\n");
      out.write("        #fm{  \r\n");
      out.write("            margin:0;  \r\n");
      out.write("            padding:10px 30px;  \r\n");
      out.write("        }  \r\n");
      out.write("        .ftitle{  \r\n");
      out.write("            font-size:14px;  \r\n");
      out.write("            font-weight:bold;  \r\n");
      out.write("            color:#666;  \r\n");
      out.write("            padding:5px 0;  \r\n");
      out.write("            margin-bottom:10px;  \r\n");
      out.write("            border-bottom:1px solid #ccc;  \r\n");
      out.write("        }  \r\n");
      out.write("        .fitem{  \r\n");
      out.write("            margin-bottom:5px;  \r\n");
      out.write("        }  \r\n");
      out.write("        .fitem label{  \r\n");
      out.write("            display:inline-block;  \r\n");
      out.write("            width:80px;  \r\n");
      out.write("        }  \r\n");
      out.write("         .fitem2{  \r\n");
      out.write("            margin-bottom:25px;  \r\n");
      out.write("            padding:25px 0;\r\n");
      out.write("        }  \r\n");
      out.write("        .fitem2 label{  \r\n");
      out.write("            display:inline-block;  \r\n");
      out.write("            width:50px; \r\n");
      out.write("            padding:5px; \r\n");
      out.write("        } \r\n");
      out.write("    </style>  \r\n");
      out.write("    \r\n");
      out.write("    <script type=\"text/javascript\">\r\n");
      out.write("\r\n");
      out.write("\t    var form =$('#ff');\r\n");
      out.write("\t    var win = $('#dlg');\r\n");
      out.write("\t\r\n");
      out.write("\t    var formPassword = $('#fmPassword'); \r\n");
      out.write("\r\n");
      out.write("        function changeP(){//翻页按钮\r\n");
      out.write("            var dg = $('#dg');\r\n");
      out.write("            dg.datagrid('loadData',[]);\r\n");
      out.write("            dg.datagrid({pagePosition:$('#p-pos').val()});\r\n");
      out.write("            dg.datagrid('getPager').pagination({\r\n");
      out.write("                layout:['list','sep','first','prev','sep',$('#p-style').val(),'sep','next','last','sep','refresh']\r\n");
      out.write("            });\r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("       \r\n");
      out.write("        \r\n");
      out.write("        \r\n");
      out.write("        function closeWindow(){  \r\n");
      out.write("            win.window('close');  \r\n");
      out.write("        }  \r\n");
      out.write("\r\n");
      out.write("        function progress(){//进度条\r\n");
      out.write("  \t      var win = $.messager.progress({\r\n");
      out.write("  \t          title:'请稍等',\r\n");
      out.write("  \t          msg:'正在提交数据...',\r\n");
      out.write("  \t          text:\"\"\r\n");
      out.write("  \t      });\r\n");
      out.write("  \t  }\r\n");
      out.write("      \r\n");
      out.write("      \r\n");
      out.write("        function refresh(){//刷新列表\r\n");
      out.write("        \t$('#dg').datagrid('reload');  \r\n");
      out.write("        }\r\n");
      out.write("\r\n");
      out.write("        function doSearch(){\r\n");
      out.write("              $('#dg').datagrid('load',{  \r\n");
      out.write("            \t  \"condition.lk.flowNo\": $('#flowNo').val(),\r\n");
      out.write("            \t  \"condition.lk.mobile\": $('#mobile').val(),\r\n");
      out.write("            \t  \"condition.eq.agentId\":($('#agentId').combobox('getValue')),\r\n");
      out.write("            \t  \"condition.ge.createTime\":$(\"input[name='startCreateTime']\").val(),\r\n");
      out.write("            \t  \"condition.le.createTime\":$(\"input[name='endCreateTime']\").val()\r\n");
      out.write("              });  \r\n");
      out.write("        } \r\n");
      out.write("\r\n");
      out.write("        function doClear(){//清空查询条件\r\n");
      out.write("        \t $('#flowNo').val(\"\");\r\n");
      out.write("        \t  $('#mobile').val(\"\");\r\n");
      out.write("        \t   $('#startCreateTime').datebox('setValue',\"\");\r\n");
      out.write("\t\t      $('#endCreateTime').datebox('setValue',\"\");\r\n");
      out.write("       }\r\n");
      out.write("        \r\n");
      out.write("        function initPage(){//分页的初始化\r\n");
      out.write("        \t\r\n");
      out.write("        \tvar p = $('#dg').datagrid().datagrid('getPager'); \r\n");
      out.write(" \t         p.pagination({        \r\n");
      out.write(" \t         pageSize: 20,//每页显示的记录条数 \r\n");
      out.write(" \t         pageList: [10,20,30,40],//可以设置每页记录条数的列表 \r\n");
      out.write(" \t         beforePageText: '第',//页数文本框前显示的汉字 \r\n");
      out.write(" \t         afterPageText: '页    共 {pages} 页', \r\n");
      out.write(" \t         displayMsg: '当前显示{from}条到{to}条记录   共{total}条记录' \r\n");
      out.write(" \t        }); \r\n");
      out.write("             \r\n");
      out.write("            }\r\n");
      out.write("        \r\n");
      out.write("        $(function(){\r\n");
      out.write("            initPage();\r\n");
      out.write("            ");

				Agent currentAgent = (Agent)request.getSession().getAttribute("currentAgent");
				if (!"admin".equals(currentAgent.getLoginName())) {
					
      out.write("\r\n");
      out.write("\t\t\t\t\t\t$(\"#agentcondition\").hide(); \r\n");
      out.write("\t\t\t\t\t");

				}
			
      out.write("\r\n");
      out.write("        });\r\n");
      out.write("\r\n");
      out.write("    </script>\r\n");
      out.write("</HEAD>\r\n");
      out.write("<body style=\"margin:0\" class=\"easyui-layout\">\r\n");
      out.write("<div region=\"center\" style=\"padding:5px;\" border=\"false\">\r\n");
      out.write("<table id=\"dg\" title='订单列表(此功能只能查询充值中订单，充值成功或充值失败订单请去\"历史订单查询\"功能中查询)' class=\"easyui-datagrid\" \r\n");
      out.write("        url=\"user/getOrderList.action\" \r\n");
      out.write("        fit=\"true\"\r\n");
      out.write("        toolbar=\"#searchtool\"\r\n");
      out.write("        pagination=\"true\"\r\n");
      out.write("        striped=\"true\"\r\n");
      out.write("         loadMsg=\"正在加载数据，请稍等……\"\r\n");
      out.write("        rownumbers=\"true\" fitColumns=\"true\" singleSelect=\"true\" pageSize=20>  \r\n");
      out.write("    <thead>  \r\n");
      out.write("        <tr>  \r\n");
      out.write("            <th field=\"flowNo\" width=\"100\">订单号</th>  \r\n");
      out.write("            <th field=\"mobile\"  width=\"100\">手机号</th>  \r\n");
      out.write("            <th field=\"money\" width=\"100\">充值金额</th>\r\n");
      out.write("            <th field=\"createTime\" width=\"100\">订单时间</th>\r\n");
      out.write("            <th field=\"status\" value=\"充值中\" width=\"100\">状态</th>   \r\n");
      out.write("        </tr>  \r\n");
      out.write("    </thead>  \r\n");
      out.write("</table>  \r\n");
      out.write("</div>\r\n");
      out.write("<div id=\"searchtool\">  \r\n");
      out.write("\t\t<span id=\"agentcondition\">\r\n");
      out.write("\t\t &nbsp;&nbsp;&nbsp; <span>代理商:</span>\r\n");
      out.write("\t\t <select id =\"agentId\" class =\"easyui-combobox\" name =\"agentId\" data-options =\"panelHeight:'auto'\" style =\" padding:2px;width:141px;\">\r\n");
      out.write("\t     \t<option value =\"\" selected =\"selected\">所有代理商</option>\r\n");
      out.write("\t     \t<option value =\"721\">社会渠道1</option>\r\n");
      out.write("\t     \t<option value =\"723\">社会渠道2</option>\r\n");
      out.write("\t     \t<option value =\"724\">社会渠道3</option>\r\n");
      out.write("\t     \t<option value =\"732\">社会渠道4</option>\r\n");
      out.write("\t     \t<option value =\"731\">社会渠道CH</option>\r\n");
      out.write("\t     </select>\r\n");
      out.write("     </span>\r\n");
      out.write("       &nbsp;&nbsp;&nbsp; <span>订单号:</span><input type=\"text\" id=\"flowNo\" class=\"easyui-validatebox\" value=\"\" size=20 />&nbsp;&nbsp;&nbsp; \r\n");
      out.write("      <span>手机号:</span><input type=\"text\" id=\"mobile\" class=\"easyui-validatebox\" value=\"\" size=20 />&nbsp;&nbsp;&nbsp; \r\n");
      out.write("      &nbsp;<span>订单时间:</span><input id=\"startCreateTime\" name=\"startCreateTime\" class=\"easyui-datebox\" size=12 editable =\"false\"  readonly=\"readonly\" />--<input id=\"endCreateTime\" name=\"endCreateTime\" class=\"easyui-datebox\"  size=12 editable =\"false\"  readonly=\"readonly\"/>\r\n");
      out.write("      <a href=\"#\" class=\"easyui-linkbutton\" iconCls=\"icon-search\" plain=\"true\" onclick=\"javascript:doSearch();\">查询</a>&nbsp;&nbsp;\r\n");
      out.write("      <a href=\"#\" class=\"easyui-linkbutton\" iconCls=\"icon-cancel\" plain=\"true\" onclick=\"doClear()\">清空</a>&nbsp;&nbsp;\r\n");
      out.write("      <a href=\"#\" class=\"easyui-linkbutton\" iconCls=\"icon-reload\" plain=\"true\" onclick=\"refresh()\">刷新</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;    \r\n");
      out.write("</div>  \r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else log(t.getMessage(), t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
