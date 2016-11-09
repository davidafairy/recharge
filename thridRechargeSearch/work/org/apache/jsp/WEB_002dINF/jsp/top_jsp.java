package org.apache.jsp.WEB_002dINF.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class top_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("    \r\n");
      out.write("<HTML>\r\n");
      out.write("<HEAD>\r\n");
      out.write("\r\n");

	request.setAttribute("base", request.getContextPath());

      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
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
      out.write("<LINK href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${base}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/css/login.css\" type=text/css rel=stylesheet>\r\n");
      out.write("\r\n");
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
      out.write("        #fmPassword{  \r\n");
      out.write("            margin:0;  \r\n");
      out.write("            width:330px;\r\n");
      out.write("            heigth:90px;\r\n");
      out.write("            padding:5px 5px;  \r\n");
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
      out.write("            margin-bottom:10px;  \r\n");
      out.write("        }  \r\n");
      out.write("        .fitem label{  \r\n");
      out.write("            display:inline-block;  \r\n");
      out.write("            width:80px;  \r\n");
      out.write("            text-align:right;\r\n");
      out.write("            padding:5px;\r\n");
      out.write("        }  \r\n");
      out.write("    </style>  \r\n");
      out.write("</HEAD>\r\n");
      out.write("<body>\r\n");
      out.write("\t<div class=\"div_top\" style=\"height:51px\">\r\n");
      out.write("\t  <table width=\"100%\" height=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" background=\"images/login_new/top_03.png\">\r\n");
      out.write("\t    <tr>\r\n");
      out.write("\t      <td width=\"50%\" align=\"left\" valign=\"middle\" style=\"font-size:18px\"><font size=\"6\" color=\"white\"><B>联通订单查询系统</B></font></td>\r\n");
      out.write("\t      <td width=\"43%\" align=\"right\" valign=\"middle\"><a href=\"#\"  id=\"btnEditPassword\"   style=\"color:white; \"  onclick=\"editUserPassword()\">修改密码</a></td>\r\n");
      out.write("\t     <td width=\"7%\" align=\"center\" valign=\"middle\"><a href=\"#\" id=\"btnExit\" style=\"color:white;\"  onclick=\"doLogout()\" >安全退出</a></td>\r\n");
      out.write("\t    </tr>\r\n");
      out.write("\t  </table>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t\r\n");
      out.write("\t   <div id=\"dlgPassword\" class=\"easyui-dialog\" style=\"width:380px;height:300px;\" closed=\"true\" buttons=\"#dlg-buttons-password\">\r\n");
      out.write("\t\t\t<form id=\"fmPassword\" style=\" width:300px;heigth:90px; padding:10px 10px; \"  method=\"post\">\r\n");
      out.write("\t\t\t\t<div class=\"fitem\">\r\n");
      out.write("\t\t\t\t  <label style=\"width:70px; display:inline-block;\">原密码: </label>\r\n");
      out.write("\t\t\t\t\t<input id=\"oldPassword\" type=\"password\" class=\"easyui-validatebox\" required=\"true\" missingMessage=\"原密码不能为空\">\r\n");
      out.write("\t\t \t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"fitem\" style=\"height:5px;\" ></div>\r\n");
      out.write("\t\t\t\t<div class=\"fitem\">\r\n");
      out.write("\t\t\t\t   <label style=\"width:70px; display:inline-block;\">新密码: </label>\r\n");
      out.write("\t\t\t\t   <input id=\"newPassword\" type=\"password\" class=\"easyui-validatebox\"\t required=\"true\" missingMessage=\"新密码不能为空\">\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class=\"fitem\" style=\"height:5px;\" ></div>\r\n");
      out.write("\t\t\t\t<div class=\"fitem\">\r\n");
      out.write("\t\t\t\t   <label style=\"width:70px; display:inline-block;\">确认密码:</label>\r\n");
      out.write("\t\t\t\t   <input id=\"confirmPassWord\" type=\"password\" class=\"easyui-validatebox\"\tvalidType=\"equalTo['#newPassword']\" required=\"true\" missingMessage=\"确认新密码不能为空\">\r\n");
      out.write("\t\t\t   </div>\r\n");
      out.write("\t\t\t</form>\r\n");
      out.write("\t   </div>\r\n");
      out.write("\t\r\n");
      out.write("\t\t<div id=\"dlg-buttons-password\">\r\n");
      out.write("\t\t  <a href=\"#\" id=\"btnPasswordSave\" class=\"easyui-linkbutton\" iconCls=\"icon-ok\" onclick=\"saveUserPassword()\">保存</a> \r\n");
      out.write("\t\t  <a href=\"#\" id=\"btnPasswordCanel\" class=\"easyui-linkbutton\" iconCls=\"icon-cancel\" onclick=\"javascript:$('#dlgPassword').dialog('close')\">取 消</a>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t<script type=\"text/javascript\">\r\n");
      out.write("\r\n");
      out.write("\tfunction editUserPassword(){  //修改用戶密码窗口  \r\n");
      out.write("\t    \t$(\"#dlgPassword\").dialog('open').dialog('setTitle','修改自己的密码');\r\n");
      out.write("\t    \t$(\"#fmPassword\").form('clear');\r\n");
      out.write("\t   \r\n");
      out.write("\t}  \r\n");
      out.write("\r\n");
      out.write("\t  function saveUserPassword(){ //保存用户增加或修改的信息\r\n");
      out.write("\t\t  progress();\r\n");
      out.write("      \t $('#btnPasswordSave').linkbutton({disabled:true});\r\n");
      out.write("     \t $('#btnPasswordCancel').linkbutton({disabled:true});\r\n");
      out.write("      \t  var v1 =  $(\"#fmPassword\").form('validate');\r\n");
      out.write("            if(v1){\r\n");
      out.write("\t\t           \t   $.ajax({  \r\n");
      out.write("\t\t      \t            url:'validateEnableEditUserSelfPassword.action?oldPassword='+$('#oldPassword').val(), \r\n");
      out.write("\t\t      \t             cache:false,  \r\n");
      out.write("\t\t      \t             dataType:'text',  \r\n");
      out.write("\t\t      \t             success:function(data){ \r\n");
      out.write("\t\t\t      \t        \t   eval('data='+data);\r\n");
      out.write("\t\t\t      \t        \t   var str = data.success;\r\n");
      out.write("\t\t\t      \t        \t   if(str){\r\n");
      out.write("\t\t\t      \t        \t\t  $(\"#fmPassword\").form('submit', {  \r\n");
      out.write("\t\t\t\t\t          \t                 url: 'user/updateUserSelfPassword.action?newPassword='+$('#newPassword').val(), \r\n");
      out.write("\t\t\t\t\t          \t                 success:function(data){ \r\n");
      out.write("\t\t\t\t\t          \t                 eval('data='+data); \r\n");
      out.write("\t\t\t\t\t          \t                 $.messager.progress('close');\r\n");
      out.write("\r\n");
      out.write("\t\t\t\t\t          \t                 $('#btnPasswordSave').linkbutton({disabled:false});\r\n");
      out.write("\t\t\t\t\t             \t             $('#btnPasswordCancel').linkbutton({disabled:false}); \r\n");
      out.write("\t\t\t\t\t\t\t          \t               if (data.success){  \r\n");
      out.write("\t\t\t\t\t\t\t                            \t$.messager.alert('操作提示',data.msg,'ok'); \r\n");
      out.write("\t\t\t\t\t\t\t                            \t$('#dlgPassword').window('close'); \r\n");
      out.write("\t\t\t\t\t\t\t                            } else {  \r\n");
      out.write("\t\t\t\t\t\t\t                                $.messager.alert('操作提示',data.msg,'error');\r\n");
      out.write("\t\t\t\t\t\t\t                                $('#dlgPassword').window('close');\r\n");
      out.write("\t\t\t\t\t\t\t                               \r\n");
      out.write("\t\t\t\t\t\t\t                            }   \r\n");
      out.write("\t\t\t\t\t      \t                   }\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t      \t        \t\t});\r\n");
      out.write("\t\t\t                           }else{\r\n");
      out.write("\t\t\t                        \t        $.messager.progress('close');\r\n");
      out.write("\t\t\t\t\t                          \t$.messager.alert('操作提示',\"您输入的旧密码错误，请重新输入\",'error'); \r\n");
      out.write("\t\t\t\t\t                          \t$('#oldPassword').val(\"\");\r\n");
      out.write("\t\t\t\t\t                          \t $('#btnPasswordSave').linkbutton({disabled:false});\r\n");
      out.write("\t\t\t\t\t               \t            $('#btnPasswordCancel').linkbutton({disabled:false});\r\n");
      out.write("\t\t\t                               }\r\n");
      out.write("\t\t                           }\r\n");
      out.write("\t\t                   });\r\n");
      out.write("                 }else{\r\n");
      out.write("              \t            $.messager.progress('close');\r\n");
      out.write("\t\t               \t   $.messager.alert('操作提示',\"表单字段验证没通过，不能提交\",'error'); \r\n");
      out.write("\t\t               \t   $('#btnPasswordSave').linkbutton({disabled:false});\r\n");
      out.write("\t          \t           $('#btnPasswordCancel').linkbutton({disabled:false});\r\n");
      out.write("                    }\r\n");
      out.write("      \t}\r\n");
      out.write("\r\n");
      out.write("\t  /*登出绑定*/\r\n");
      out.write("\t\t//$(\"#logout\").bind(\"click\",doLogout);\r\n");
      out.write("\r\n");
      out.write("\t  \r\n");
      out.write("\t\tfunction doLogout(){\r\n");
      out.write("\t\t\turl = 'logout.action';\r\n");
      out.write("\t\t\tlocation.href = url;\r\n");
      out.write("\t\t}\r\n");
      out.write("\r\n");
      out.write("\t  function progress(){//进度条\r\n");
      out.write("  \t      var win = $.messager.progress({\r\n");
      out.write("  \t          title:'请稍等',\r\n");
      out.write("  \t          msg:'正在提交数据...',\r\n");
      out.write("  \t          text:\"\"\r\n");
      out.write("  \t      });\r\n");
      out.write("  \t  }\r\n");
      out.write("\r\n");
      out.write("\t  $.extend($.fn.validatebox.defaults.rules, {\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t    safepass: {\r\n");
      out.write("\t\t        validator: function (value, param) {\r\n");
      out.write("\t\t            return safePassword(value);\r\n");
      out.write("\t\t        },\r\n");
      out.write("\t\t        message: '密码由字母、数字或字符组成，至少6位'\r\n");
      out.write("\t\t    },\r\n");
      out.write("\t\t    equalTo: {\r\n");
      out.write("\t\t        validator: function (value, param) {\r\n");
      out.write("\t\t            return value == $(param[0]).val();\r\n");
      out.write("\t\t        },\r\n");
      out.write("\t\t        message: '两次输入的字符不一至'\r\n");
      out.write("\t\t    }\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\t\r\n");
      out.write("\t  var safePassword = function (value) {\r\n");
      out.write("\t\t    return !(/^(([A-Z]*|[a-z]*|\\d*|[-_\\~!@#$%\\^&\\*\\.\\(\\)\\[\\]\\{\\}<>\\?\\\\\\/\\'\\\"]*)|.{0,5})$|\\s/.test(value));\r\n");
      out.write("\t\t};\r\n");
      out.write("   </script>\r\n");
      out.write("  </body>\r\n");
      out.write("</HTML>\r\n");
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
