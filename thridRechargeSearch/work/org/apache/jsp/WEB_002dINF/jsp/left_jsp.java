package org.apache.jsp.WEB_002dINF.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class left_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/html; charset=UTF-8");
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
      out.write("<HTML>\r\n");
      out.write("\r\n");
      out.write("<BODY>\r\n");
      out.write("\t\r\n");
      out.write("\t<div style=\"margin:10px 0;\">\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<ul id=\"tt\" class=\"easyui-tree\" data-options=\"url:'mainMenu.action',method:'get',animate:true,lines:true,onlyLeafCheck:true\"></ul>\r\n");
      out.write("\t<script type=\"text/javascript\">\r\n");
      out.write("\t$('#tt').tree({\r\n");
      out.write("\t    onClick: function(node){\r\n");
      out.write("\t    \tif (node.attributes.url) {\r\n");
      out.write("\t    \t\taddTab(node.text,node.attributes.url,node.iconCls);  // alert node text property when clicked\r\n");
      out.write("\t    \t}\r\n");
      out.write("\t    \t\t\r\n");
      out.write("\t    }\r\n");
      out.write("\t});\r\n");
      out.write("\t\r\n");
      out.write("\tfunction addTab(title, href,icon) {\r\n");
      out.write("\t\tvar tt = $('#main-center');\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tif (tt.tabs('exists', title)){//如果tab已经存在,则选中并刷新该tab          \r\n");
      out.write("\t        tt.tabs('select', title);  \r\n");
      out.write("\t        refreshTab({tabTitle:title,url:href});  \r\n");
      out.write("\t    } else {  \r\n");
      out.write("\t        if (href){  \r\n");
      out.write("\t            var content = '<iframe scrolling=\"no\" frameborder=\"0\"  src=\"'+href+'\" style=\"width:'+tt.width()+';height:100%;\"></iframe>';  \r\n");
      out.write("\t        } else {  \r\n");
      out.write("\t            var content = '未实现';  \r\n");
      out.write("\t        }  \r\n");
      out.write("\t        tt.tabs('add',{  \r\n");
      out.write("\t            title:title,  \r\n");
      out.write("\t            closable:true,  \r\n");
      out.write("\t            content:content\r\n");
      out.write("\t        });  \r\n");
      out.write("\t    }  \r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t/**     \r\n");
      out.write("\t * 刷新tab \r\n");
      out.write("\t * @cfg  \r\n");
      out.write("\t *example: {tabTitle:'tabTitle',url:'refreshUrl'} \r\n");
      out.write("\t *如果tabTitle为空，则默认刷新当前选中的tab \r\n");
      out.write("\t *如果url为空，则默认以原来的url进行reload \r\n");
      out.write("\t */  \r\n");
      out.write("\tfunction refreshTab(cfg){  \r\n");
      out.write("\t    var refresh_tab = cfg.tabTitle?$('#main-center').tabs('getTab',cfg.tabTitle):$('#tabs').tabs('getSelected');  \r\n");
      out.write("\t    if(refresh_tab && refresh_tab.find('iframe').length > 0){  \r\n");
      out.write("\t    var _refresh_ifram = refresh_tab.find('iframe')[0];  \r\n");
      out.write("\t    var refresh_url = cfg.url?cfg.url:_refresh_ifram.src;  \r\n");
      out.write("\t    //_refresh_ifram.src = refresh_url;  \r\n");
      out.write("\t    _refresh_ifram.contentWindow.location.href=refresh_url;  \r\n");
      out.write("\t    }  \r\n");
      out.write("\t}  \r\n");
      out.write("\t</script>\r\n");
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
}
