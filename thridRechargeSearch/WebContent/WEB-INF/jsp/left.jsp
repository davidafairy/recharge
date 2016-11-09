<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<HTML>

<BODY>
	
	<div style="margin:10px 0;">
	</div>
	<ul id="tt" class="easyui-tree" data-options="url:'mainMenu.action',method:'get',animate:true,lines:true,onlyLeafCheck:true"></ul>
	<script type="text/javascript">
	$('#tt').tree({
	    onClick: function(node){
	    	if (node.attributes.url) {
	    		addTab(node.text,node.attributes.url,node.iconCls);  // alert node text property when clicked
	    	}
	    		
	    }
	});
	
	function addTab(title, href,icon) {
		var tt = $('#main-center');
		
		if (tt.tabs('exists', title)){//如果tab已经存在,则选中并刷新该tab          
	        tt.tabs('select', title);  
	        refreshTab({tabTitle:title,url:href});  
	    } else {  
	        if (href){  
	            var content = '<iframe scrolling="no" frameborder="0"  src="'+href+'" style="width:'+tt.width()+';height:100%;"></iframe>';  
	        } else {  
	            var content = '未实现';  
	        }  
	        tt.tabs('add',{  
	            title:title,  
	            closable:true,  
	            content:content
	        });  
	    }  
	}
	
	/**     
	 * 刷新tab 
	 * @cfg  
	 *example: {tabTitle:'tabTitle',url:'refreshUrl'} 
	 *如果tabTitle为空，则默认刷新当前选中的tab 
	 *如果url为空，则默认以原来的url进行reload 
	 */  
	function refreshTab(cfg){  
	    var refresh_tab = cfg.tabTitle?$('#main-center').tabs('getTab',cfg.tabTitle):$('#tabs').tabs('getSelected');  
	    if(refresh_tab && refresh_tab.find('iframe').length > 0){  
	    var _refresh_ifram = refresh_tab.find('iframe')[0];  
	    var refresh_url = cfg.url?cfg.url:_refresh_ifram.src;  
	    //_refresh_ifram.src = refresh_url;  
	    _refresh_ifram.contentWindow.location.href=refresh_url;  
	    }  
	}  
	</script>
</BODY>
</HTML>
