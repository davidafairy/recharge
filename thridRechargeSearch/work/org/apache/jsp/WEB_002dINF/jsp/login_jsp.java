package org.apache.jsp.WEB_002dINF.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class login_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fs_005fproperty_0026_005fvalue_005fnobody;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fs_005fproperty_0026_005fvalue_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fs_005fproperty_0026_005fvalue_005fnobody.release();
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
      out.write("<html>\r\n");
      out.write("\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\r\n");
      out.write("<title>");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${projectName}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("</title>\r\n");

	request.setAttribute("base", request.getContextPath());

      out.write("\r\n");
      out.write("<LINK href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${base}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/css/login.css\" type=text/css rel=stylesheet>\r\n");
      out.write("<STYLE type=text/css>\r\n");
      out.write("    #errorMess {color:red; font-size:16px;} #Layer1 { position:absolute; width:36px;\r\n");
      out.write("    height:20px; z-index:1; left: 590px; top: 340px; background-color: #CCCCCC;\r\n");
      out.write("    } .STYLE1 { color: #FFFFFF; font-family: \"宋体\"; } #Layer2 { position:absolute;\r\n");
      out.write("    width:24px; height:20px; z-index:1; left: 596px; top: 367px; background-color:\r\n");
      out.write("    #CCCCCC; } .STYLE2 {font-size: 12px}\r\n");
      out.write("</STYLE>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("    function doLogin() {\r\n");
      out.write("        document.getElementById('loginForm').submit();\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("    function updateLoginName() {\r\n");
      out.write("        var t = document.getElementById(\"txtUserName\");\r\n");
      out.write("        t.value = \"\";\r\n");
      out.write("        t.style.color = \"black\";\r\n");
      out.write("        t.name = \"agent.loginName\";\r\n");
      out.write("        t.focus();\r\n");
      out.write("    }\r\n");
      out.write("    function updatePassword() {\r\n");
      out.write("        var t = document.getElementById(\"txtUserPassword1\");\r\n");
      out.write("        t.style.display = \"none\";\r\n");
      out.write("        t.blur();\r\n");
      out.write("        var t1 = document.getElementById(\"txtUserPassword\");\r\n");
      out.write("        t1.style.display = \"\";\r\n");
      out.write("        t1.focus();\r\n");
      out.write("    }\r\n");
      out.write("    function input() {\r\n");
      out.write("        var t1 = document.getElementById(\"txtUserPassword\");\r\n");
      out.write("        var r = t1.createTextRange();\r\n");
      out.write("        r.collapse(false);\r\n");
      out.write("        r.select();\r\n");
      out.write("    }\r\n");
      out.write("\r\n");
      out.write("   \tfunction onEnter(event) {\r\n");
      out.write("   \t\tif(event.keyCode==13){  \r\n");
      out.write("   \t\t\tdoLogin();\r\n");
      out.write("   \t\t}\r\n");
      out.write("   \t}\r\n");
      out.write("    \r\n");
      out.write("    \r\n");
      out.write("</script>\r\n");
      out.write("</HEAD>\r\n");
      out.write("\r\n");
      out.write("<div class=\"div\">\r\n");
      out.write("\t\t<form id=\"loginForm\" action=\"login.action\" method=\"post\">\r\n");
      out.write("\t\t  <table width=\"900\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t    <tr>\r\n");
      out.write("\t\t      <td height=\"83\" align=\"center\" valign=\"top\"><font size=\"9\"><B>订单查询系统</B></font></td>\r\n");
      out.write("\t\t    </tr>\r\n");
      out.write("\t\t    <tr>\r\n");
      out.write("\t\t      <td><table width=\"900\" border=\"0\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t        <tr>\r\n");
      out.write("\t\t          <td height=\"47\"><div class=\"bg1\">");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${roleName}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("登录</div></td>\r\n");
      out.write("\t\t          <td>&nbsp;</td>\r\n");
      out.write("\t\t        </tr>\r\n");
      out.write("\t\t      </table></td>\r\n");
      out.write("\t\t    </tr>\r\n");
      out.write("\t\t    <tr>\r\n");
      out.write("\t\t      <td  class=\"bg_blue\" align=\"center\"><div class=\" bg_center\">\r\n");
      out.write("\t\t        <table width=\"100%\" height=\"149\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t          <tr>\r\n");
      out.write("\t\t            <td width=\"54%\" valign=\"top\"><table width=\"130\" border=\"0\" align=\"right\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("\t\t              <tr>\r\n");
      out.write("\t\t                <td width=\"143\" height=\"37\">&nbsp;<input type=\"hidden\" name=\"loginRole\" value=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${loginRole }", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("\"></td>\r\n");
      out.write("\t\t              </tr>\r\n");
      out.write("\t\t              <tr>\r\n");
      out.write("\t\t                <td height=\"28\" align=\"right\">\r\n");
      out.write("\t                  <INPUT  class=\"textbox\" id=\"txtUserName\" tabindex=\"1\" type=\"text\" value=\"登录名\" style=\"color:#E0E0E0; width: 140px; height: 22px; \"name=\"loginName\" onClick=\"updateLoginName();\">\t\t              </tr>\r\n");
      out.write("\t\t              <tr>\r\n");
      out.write("\t\t                   <td height=\"28\" align=\"right\">\r\n");
      out.write("\t\t               <INPUT  class=\"textbox\" id=\"txtUserPassword1\" tabindex=\"2\" type=\"text\" name=\"password1\" value=\"密码\" style=\"color:#E0E0E0; width: 140px; height: 22px;\" onFocus=\"updatePassword();\">\r\n");
      out.write("\t\t               <INPUT  class=\"textbox\" id=\"txtUserPassword\" type=\"password\" name=\"agent.password\" value=\"\" style=\"color:black; width: 140px; height: 22px; display:none;\" onFocus=\"input();\" onKeyDown=\"onEnter(event);\">\r\n");
      out.write("\t\t               </TD>\r\n");
      out.write("\t\t             \r\n");
      out.write("\t\t              </tr>\r\n");
      out.write("\t\t              <tr>\r\n");
      out.write("\t\t                <td height=\"51\" valign=\"bottom\"><SPAN id=\"errorMess\">");
      if (_jspx_meth_s_005fproperty_005f0(_jspx_page_context))
        return;
      out.write("</SPAN></td>\r\n");
      out.write("\t\t              </tr>\r\n");
      out.write("\t\t            </table></td>\r\n");
      out.write("\t\t            <th width=\"46%\"><a href=\"#\" onClick=\"doLogin()\"><img src=\"images/login/dl_13.png\" width=\"156\" height=\"149\" /></a></th>\r\n");
      out.write("\t\t          </tr>\r\n");
      out.write("\t\t        </table>\r\n");
      out.write("\t\t     </div></td>\r\n");
      out.write("\t\t    </tr>\r\n");
      out.write("\t\t  </table>\r\n");
      out.write("\t\t</form>\r\n");
      out.write("\t\t<div class=\"txt1\"></div>\r\n");
      out.write("</div>\r\n");
      out.write("\r\n");
      out.write("<div class=\"STYLE1 STYLE2\" id=\"Layer1\" style=\"display:none;\" >用户名</div>\r\n");
      out.write("<div class=\"STYLE1 STYLE2\" id=\"Layer2\" style=\"display:none;\" >密码</div>\r\n");
      out.write("\r\n");
      out.write("</BODY>\r\n");
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

  private boolean _jspx_meth_s_005fproperty_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  s:property
    org.apache.struts2.views.jsp.PropertyTag _jspx_th_s_005fproperty_005f0 = (org.apache.struts2.views.jsp.PropertyTag) _005fjspx_005ftagPool_005fs_005fproperty_0026_005fvalue_005fnobody.get(org.apache.struts2.views.jsp.PropertyTag.class);
    _jspx_th_s_005fproperty_005f0.setPageContext(_jspx_page_context);
    _jspx_th_s_005fproperty_005f0.setParent(null);
    // /WEB-INF/jsp/login.jsp(90,71) name = value type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_s_005fproperty_005f0.setValue("errorMess");
    int _jspx_eval_s_005fproperty_005f0 = _jspx_th_s_005fproperty_005f0.doStartTag();
    if (_jspx_th_s_005fproperty_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fs_005fproperty_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005fproperty_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fs_005fproperty_0026_005fvalue_005fnobody.reuse(_jspx_th_s_005fproperty_005f0);
    return false;
  }
}
