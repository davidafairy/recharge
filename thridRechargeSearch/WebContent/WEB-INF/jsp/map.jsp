<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<meta name="keywords" content="百度地图,百度地图API，百度地图自定义工具，百度地图所见即所得工具" />
<meta name="description" content="百度地图API自定义地图，帮助用户在可视化操作下生成百度地图" />
<title>百度地图API自定义地图</title>
<%
	request.setAttribute("base", request.getContextPath());
	String regionId = (String)request.getAttribute("id");
	String[][] salerList = (String[][])request.getAttribute("longAndLatisList");
	String[][] regionLatis = (String[][])request.getAttribute("regionLatis");


%>
<!--引用百度地图API-->
<link rel="stylesheet" type="text/css" href="${base}/css/map.css">
<script src="http://api.map.baidu.com/api?v=2.0&ak=rTyjXLxN78lNTPNyRKgzA5x2"></script>
<script type="text/javascript" src="${base}/js/jQuery-main.js"></script>
</head>

<body>
  <!--百度地图容器-->
<div style="width:800px;height:495px;border:#ccc solid 1px;" id="dituContent"></div>
  
<div id="Layer1" style="display:none;">
<div id="Layer2"><a href="javascript:initMap(118.813633, 32.405139,11);">六合区</a></div>
<div id="Layer3"><a href="javascript:initMap(118.888372, 32.315837,13);">雄州街道</a></div>
<div id="Layer4"><a href="javascript:initMap(118.861926, 32.250878,13);">长芦街道</a></div>
<div id="Layer5"><a href="javascript:initMap(118.772239, 32.316325,13);">龙池街道</a></div>
<div id="Layer6"><a href="javascript:initMap(118.727396, 32.41977,13);">程桥街道</a></div>
<div id="Layer7"><a href="javascript:initMap(118.965411, 32.45585,13);">金牛湖街道</a></div>
<div id="Layer8"><a href="javascript:initMap(118.980359, 32.352447,13);">横梁街道</a></div>
<div id="Layer9"><a href="javascript:initMap(118.997606, 32.241106,13);">龙袍街道</a></div>
<div id="Layer10"><a href="javascript:initMap(118.818232, 32.473398,13);">马鞍街道</a></div>
<div id="Layer11"><a href="javascript:initMap(118.687152, 32.534786,13);">竹镇镇</a></div>
<div id="Layer12"><a href="javascript:initMap(118.906769, 32.52407,13);">冶山镇</a></div>
<div id="Layer13"><a href="javascript:initMap(118.726246, 32.283119,13);">葛塘街道</a></div>
</div>
<div id="r-result" style="display:none;"></div>


<div id="container" style="display:none;"></div>
</body>
<script>
var regionId = <%=regionId%>;
var longString = new Array();
var latiString = new Array();
var addString = new Array();
var regionLatis=new Array();         //先声明一维
for(var i=0;i<11;i++){                //一维长度为10
	regionLatis[i]=new Array();         //在声明二维
   for(var j=0;j<2;j++){             //二维长度为20
	   regionLatis[i][j]="";
 }
}
//获取街道及街道下属零售点总数
<%for(int i=0;i<regionLatis.length ;i++){%>
<%if(regionLatis[i][0] != null && regionLatis[i][1] != "0" ){%>
regionLatis[<%=i%>][0] = "<%=regionLatis[i][0]%>";     
regionLatis[<%=i%>][1] = <%=regionLatis[i][1]%>;
<%}%>
 <%}%> 
//获取经纬度及标注点
<%for(int i=0;i<salerList.length ;i++){%>
<%if(salerList[i][0] != null && salerList[i][1] != null && salerList[i][2] != null){%>
longString[<%=i%>] = "<%=salerList[i][0]%>";     
latiString[<%=i%>] = "<%=salerList[i][1]%>";
addString[<%=i%>] = "<%=salerList[i][2]%>"; 
<%}%>
 <%}%> 
 
 //定义初始中心点
var point = [[118.813633, 32.405139], [118.726246, 32.283119, 13], [118.726246, 32.283119, 13], [118.861926, 32.250878, 13], [118.888372, 32.315837, 13], [118.772239, 32.316325, 13], [118.727396, 32.41977, 13], [118.965411, 32.45585, 13], [118.980359, 32.352447, 13], [118.997606, 32.241106, 13], [118.906769, 32.52407, 13], [118.687152, 32.534786, 13]];


var mapPoint1 = point[regionId-1][0]; //初始化经度
var mapPoint2 = point[regionId-1][1];  //初始化纬度
if(regionId == 1){
	var level = 11;             //初始化缩放
	document.getElementById("Layer1").style.display='block';
	document.getElementById("container").style.display='block';
}else{
	var level = 13;             //初始化缩放 不同用户登录的缩放级别不一样
}
var markArrey = [[118.888372, 32.315837, "南京市六合区雄州街道"], [118.980359, 32.352447, "南京市六合区龙池街道"],[118.687152, 32.534786, "南京市六合区泰山路"]]; //初始化标注点数据
var map = new BMap.Map("dituContent"); //在百度地图容器中创建一个地图


//创建和初始化地图函数：
function initMap(mapPoint1, mapPoint2,level) {
    createMap(mapPoint1, mapPoint2,level);	//创建地图
	//getBoundary();
    setMapEvent();							//设置地图事件
    addMapControl();						//向地图添加控件
   	initMark();								//初始化标注
}



	//创建地图函数：
function createMap(mapPoint1, mapPoint2,level) {
    var point = new BMap.Point(mapPoint1, mapPoint2);	//定义一个中心点坐标
	map.clearOverlays();							    //清除地图覆盖物    
    map.centerAndZoom(point,level);						//设定地图的中心点和坐标并将地图显示在地图容器中
    //添加多个不规则形区域
    var x = new BMap.Polygon([new BMap.Point(119.006535, 32.510805),new BMap.Point(118.997435, 32.509116),new BMap.Point(118.980851, 32.512362),new BMap.Point(118.975595, 32.533405),new BMap.Point(118.951424, 32.549602),new BMap.Point(118.942924, 32.564302),new BMap.Point(118.923048, 32.562725),new BMap.Point(118.920942, 32.55519),new BMap.Point(118.91284, 32.558531),new BMap.Point(118.912592, 32.564281),new BMap.Point(118.905799, 32.559185),new BMap.Point(118.894449, 32.560821),new BMap.Point(118.901024, 32.567777),new BMap.Point(118.905152, 32.566634),new BMap.Point(118.903639, 32.582235),new BMap.Point(118.915644, 32.592805),new BMap.Point(118.91427, 32.598765),new BMap.Point(118.902497, 32.592303),new BMap.Point(118.891642, 32.592275),new BMap.Point(118.887812, 32.584459),new BMap.Point(118.880008, 32.588524),new BMap.Point(118.880368, 32.582202),new BMap.Point(118.862592, 32.580035),new BMap.Point(118.850105, 32.57281),new BMap.Point(118.8512, 32.580629),new BMap.Point(118.842699, 32.576569),new BMap.Point(118.833568, 32.578746),new BMap.Point(118.825776, 32.590266),new BMap.Point(118.832033, 32.601471),new BMap.Point(118.828785, 32.61028),new BMap.Point(118.811757, 32.599779),new BMap.Point(118.808675, 32.590866),new BMap.Point(118.79123, 32.588705),new BMap.Point(118.780687, 32.593911),new BMap.Point(118.78203, 32.604131),new BMap.Point(118.769479, 32.609556),new BMap.Point(118.750952, 32.604526),new BMap.Point(118.748889, 32.596502),new BMap.Point(118.742117, 32.59446),new BMap.Point(118.732625, 32.602042),new BMap.Point(118.728696, 32.618627),new BMap.Point(118.70413, 32.612265),new BMap.Point(118.707333, 32.594705),new BMap.Point(118.702996, 32.594698),new BMap.Point(118.701117, 32.603267),new BMap.Point(118.693661, 32.611479),new BMap.Point(118.698287, 32.599614),new BMap.Point(118.695985, 32.594862),new BMap.Point(118.677834, 32.601049),new BMap.Point(118.668497, 32.599845),new BMap.Point(118.664353, 32.605161),new BMap.Point(118.639981, 32.583381),new BMap.Point(118.630451, 32.598083),new BMap.Point(118.608383, 32.603166),new BMap.Point(118.604648, 32.607207),new BMap.Point(118.575571, 32.5923),new BMap.Point(118.571351, 32.568456),new BMap.Point(118.59121, 32.555122),new BMap.Point(118.612451, 32.546025),new BMap.Point(118.615494, 32.542493),new BMap.Point(118.611104, 32.52862),new BMap.Point(118.621077, 32.526088),new BMap.Point(118.623429, 32.521393),new BMap.Point(118.621952, 32.514166),new BMap.Point(118.614603, 32.507474),new BMap.Point(118.601638, 32.508671),new BMap.Point(118.598991, 32.506163),new BMap.Point(118.60113, 32.486234),new BMap.Point(118.63472, 32.473338),new BMap.Point(118.640887, 32.477015),new BMap.Point(118.64999, 32.476365),new BMap.Point(118.654229, 32.482496),new BMap.Point(118.67897, 32.475751),new BMap.Point(118.697382, 32.478522),new BMap.Point(118.698104, 32.459713),new BMap.Point(118.692466, 32.434236),new BMap.Point(118.695103, 32.406439),new BMap.Point(118.684705, 32.398222),new BMap.Point(118.694444, 32.391076),new BMap.Point(118.697995, 32.383717),new BMap.Point(118.702381, 32.389332),new BMap.Point(118.708187, 32.386295),new BMap.Point(118.704741, 32.381237),new BMap.Point(118.710051, 32.375774),new BMap.Point(118.703131, 32.372719),new BMap.Point(118.70698, 32.364474),new BMap.Point(118.699891, 32.360493),new BMap.Point(118.705715, 32.355751),new BMap.Point(118.703331, 32.349347),new BMap.Point(118.71455, 32.342712),new BMap.Point(118.709624, 32.335619),new BMap.Point(118.690488, 32.333128),new BMap.Point(118.691495, 32.329916),new BMap.Point(118.70373, 32.325508),new BMap.Point(118.702387, 32.323215),new BMap.Point(118.685304, 32.322703),new BMap.Point(118.665752, 32.309986),new BMap.Point(118.665134, 32.304802),new BMap.Point(118.675321, 32.301884),new BMap.Point(118.67757, 32.292748),new BMap.Point(118.667641, 32.270882),new BMap.Point(118.674152, 32.264352),new BMap.Point(118.6825, 32.261471),new BMap.Point(118.683669, 32.256469),new BMap.Point(118.726454, 32.254908),new BMap.Point(118.730878, 32.221523),new BMap.Point(118.737648, 32.215992),new BMap.Point(118.740328, 32.203742),new BMap.Point(118.745706, 32.197228),new BMap.Point(118.742709, 32.194862),new BMap.Point(118.746162, 32.186735),new BMap.Point(118.742455, 32.180435),new BMap.Point(118.737139, 32.17993),new BMap.Point(118.736441, 32.172507),new BMap.Point(118.7406, 32.172988),new BMap.Point(118.754547, 32.189896),new BMap.Point(118.774637, 32.194454),new BMap.Point(118.77279, 32.211294),new BMap.Point(118.782841, 32.220278),new BMap.Point(118.814771, 32.23672),new BMap.Point(118.846341, 32.234056),new BMap.Point(118.859598, 32.226164),new BMap.Point(118.873143, 32.205506),new BMap.Point(118.879483, 32.187918),new BMap.Point(118.963099, 32.18344),new BMap.Point(119.018011, 32.188116),new BMap.Point(119.040469, 32.197355),new BMap.Point(119.069616, 32.228654),new BMap.Point(119.094119, 32.246408),new BMap.Point(119.082998, 32.253653),new BMap.Point(119.067036, 32.249722),new BMap.Point(119.043509, 32.266229),new BMap.Point(119.047491, 32.27807),new BMap.Point(119.042787, 32.300983),new BMap.Point(119.046449, 32.30975),new BMap.Point(119.04205, 32.315071),new BMap.Point(119.043613, 32.333236),new BMap.Point(119.048398, 32.33965),new BMap.Point(119.03488, 32.351378),new BMap.Point(119.033753, 32.357628),new BMap.Point(119.042474, 32.362194),new BMap.Point(119.050678, 32.372882),new BMap.Point(119.029504, 32.383556),new BMap.Point(119.031781, 32.386499),new BMap.Point(119.044828, 32.387936),new BMap.Point(119.045429, 32.391203),new BMap.Point(119.043582, 32.395495),new BMap.Point(119.02963, 32.397165),new BMap.Point(119.022968, 32.411641),new BMap.Point(119.02452, 32.42434),new BMap.Point(119.03072, 32.427123),new BMap.Point(119.03595, 32.435146),new BMap.Point(119.028549, 32.447643),new BMap.Point(119.030203, 32.456972),new BMap.Point(119.037218, 32.461503),new BMap.Point(119.042358, 32.458787),new BMap.Point(119.053185, 32.469647),new BMap.Point(119.073963, 32.468466),new BMap.Point(119.071053, 32.488153),new BMap.Point(119.052249, 32.504396),new BMap.Point(119.045519, 32.522393),new BMap.Point(119.0362, 32.52155),new BMap.Point(119.026271, 32.524651),new BMap.Point(119.014063, 32.520024),new BMap.Point(119.006535, 32.510805)], {
        strokeColor: "red",
        fillOpacity: 0.1,
        strokeWeight: 10,
        strokeOpacity: 0.2
    });
    map.addOverlay(x);
	
    var polygon = new BMap.Polygon([new BMap.Point(118.742119, 32.594458),new BMap.Point(118.73257, 32.601971),new BMap.Point(118.728402, 32.618881),new BMap.Point(118.703824, 32.61219),new BMap.Point(118.706986, 32.594914),new BMap.Point(118.702962, 32.594792),new BMap.Point(118.70095, 32.603188),new BMap.Point(118.693907, 32.610852),new BMap.Point(118.697931, 32.599659),new BMap.Point(118.695919, 32.595036),new BMap.Point(118.677378, 32.600876),new BMap.Point(118.668323, 32.600024),new BMap.Point(118.664299, 32.605013),new BMap.Point(118.639865, 32.583597),new BMap.Point(118.630523, 32.597956),new BMap.Point(118.608388, 32.603066),new BMap.Point(118.604364, 32.606959),new BMap.Point(118.575474, 32.592359),new BMap.Point(118.571306, 32.568628),new BMap.Point(118.590997, 32.555238),new BMap.Point(118.612269, 32.546108),new BMap.Point(118.615287, 32.542577),new BMap.Point(118.611407, 32.52894),new BMap.Point(118.620893, 32.525774),new BMap.Point(118.623336, 32.521269),new BMap.Point(118.621755, 32.514692),new BMap.Point(118.614425, 32.507507),new BMap.Point(118.601633, 32.508725),new BMap.Point(118.598902, 32.506045),new BMap.Point(118.600771, 32.486433),new BMap.Point(118.63426, 32.473641),new BMap.Point(118.640727, 32.477052),new BMap.Point(118.649782, 32.476443),new BMap.Point(118.654238, 32.482291),new BMap.Point(118.678672, 32.475956),new BMap.Point(118.697141, 32.478514),new BMap.Point(118.701615, 32.479717),new BMap.Point(118.713715, 32.478263),new BMap.Point(118.724063, 32.481187),new BMap.Point(118.74299, 32.481644),new BMap.Point(118.749925, 32.484088),new BMap.Point(118.757408, 32.473397),new BMap.Point(118.764271, 32.485588),new BMap.Point(118.761999, 32.495798),new BMap.Point(118.76534, 32.491306),new BMap.Point(118.771089, 32.503),new BMap.Point(118.769365, 32.507872),new BMap.Point(118.765915, 32.510795),new BMap.Point(118.761891, 32.509821),new BMap.Point(118.754992, 32.516154),new BMap.Point(118.754417, 32.522486),new BMap.Point(118.774539, 32.53515),new BMap.Point(118.771664, 32.540994),new BMap.Point(118.770515, 32.545864),new BMap.Point(118.763041, 32.545864),new BMap.Point(118.752692, 32.559986),new BMap.Point(118.768215, 32.570697),new BMap.Point(118.770515, 32.577999),new BMap.Point(118.765915, 32.581407),new BMap.Point(118.762466, 32.587248),new BMap.Point(118.756142, 32.588708),new BMap.Point(118.746368, 32.592115),new BMap.Point(118.745371, 32.595446)], {
        strokeColor: "black",
        fillColor: "green",
        fillOpacity: 0.2,
        strokeWeight: 3,
        strokeOpacity: 0.2
    });
    map.addOverlay(polygon);

    var polygon1 = new BMap.Polygon([new BMap.Point(118.697339, 32.478583),new BMap.Point(118.701632, 32.47971),new BMap.Point(118.713724, 32.478255),new BMap.Point(118.724054, 32.481187),new BMap.Point(118.743017, 32.481644),new BMap.Point(118.749916, 32.484096),new BMap.Point(118.757408, 32.473382),new BMap.Point(118.76428, 32.48558),new BMap.Point(118.769185, 32.486281),new BMap.Point(118.772922, 32.479276),new BMap.Point(118.775797, 32.478788),new BMap.Point(118.776946, 32.479946),new BMap.Point(118.775365, 32.482809),new BMap.Point(118.777593, 32.483966),new BMap.Point(118.777665, 32.483966),new BMap.Point(118.77939, 32.483844),new BMap.Point(118.782911, 32.485063),new BMap.Point(118.791966, 32.486707),new BMap.Point(118.778599, 32.448019),new BMap.Point(118.779174, 32.443144),new BMap.Point(118.77055, 32.430467),new BMap.Point(118.778599, 32.360712),new BMap.Point(118.784492, 32.36306),new BMap.Point(118.788912, 32.358058),new BMap.Point(118.762681, 32.347017),new BMap.Point(118.752548, 32.345491),new BMap.Point(118.736595, 32.351035),new BMap.Point(118.731672, 32.349754),new BMap.Point(118.715053, 32.360758),new BMap.Point(118.705441, 32.367772),new BMap.Point(118.703106, 32.372712),new BMap.Point(118.709987, 32.375792),new BMap.Point(118.704777, 32.381205),new BMap.Point(118.708154, 32.386343),new BMap.Point(118.702369, 32.389331),new BMap.Point(118.698003, 32.383751),new BMap.Point(118.694446, 32.391069),new BMap.Point(118.684708, 32.398218),new BMap.Point(118.695093, 32.406434),new BMap.Point(118.692452, 32.434238),new BMap.Point(118.698093, 32.459711)], {
        strokeColor: "black",
		fillColor: "blue",
        fillOpacity: 0.2,
        strokeWeight: 3,
        strokeOpacity: 0.2
    });
    map.addOverlay(polygon1);

    var polygon2 = new BMap.Polygon([new BMap.Point(118.762654, 32.347017),new BMap.Point(118.767954, 32.346673),new BMap.Point(118.780827, 32.337979),new BMap.Point(118.779228, 32.332541),new BMap.Point(118.779884, 32.328636),new BMap.Point(118.794652, 32.335333),new BMap.Point(118.80288, 32.334715),new BMap.Point(118.804731, 32.331588),new BMap.Point(118.809779, 32.326935),new BMap.Point(118.81154, 32.326706),new BMap.Point(118.813894, 32.329834),new BMap.Point(118.813822, 32.330932),new BMap.Point(118.810049, 32.334898),new BMap.Point(118.809097, 32.339947),new BMap.Point(118.810723, 32.342967),new BMap.Point(118.817595, 32.34543),new BMap.Point(118.829551, 32.350723),new BMap.Point(118.858944, 32.339405),new BMap.Point(118.858081, 32.336416),new BMap.Point(118.851901, 32.330314),new BMap.Point(118.855386, 32.323846),new BMap.Point(118.856788, 32.317057),new BMap.Point(118.8505, 32.311396),new BMap.Point(118.857111, 32.301264),new BMap.Point(118.856177, 32.301447),new BMap.Point(118.83318, 32.301371),new BMap.Point(118.818996, 32.295007),new BMap.Point(118.818224, 32.29445),new BMap.Point(118.816508, 32.292161),new BMap.Point(118.816768, 32.28875),new BMap.Point(118.816122, 32.288552),new BMap.Point(118.81507, 32.288491),new BMap.Point(118.806752, 32.292321),new BMap.Point(118.805387, 32.293542),new BMap.Point(118.795856, 32.292497),new BMap.Point(118.768475, 32.266816),new BMap.Point(118.755791, 32.265915),new BMap.Point(118.757372, 32.271045),new BMap.Point(118.750114, 32.282157),new BMap.Point(118.738903, 32.296076),new BMap.Point(118.725393, 32.317316),new BMap.Point(118.725159, 32.317285),new BMap.Point(118.724764, 32.317163),new BMap.Point(118.724458, 32.317087),new BMap.Point(118.703465, 32.302683),new BMap.Point(118.70289, 32.302714),new BMap.Point(118.701884, 32.302988),new BMap.Point(118.700914, 32.303568),new BMap.Point(118.69998, 32.303263),new BMap.Point(118.69768, 32.304057),new BMap.Point(118.69556, 32.30427),new BMap.Point(118.694123, 32.30424),new BMap.Point(118.693871, 32.304667),new BMap.Point(118.692542, 32.305003),new BMap.Point(118.69132, 32.304911),new BMap.Point(118.689523, 32.30543),new BMap.Point(118.688302, 32.305674),new BMap.Point(118.686397, 32.306284),new BMap.Point(118.682301, 32.307139),new BMap.Point(118.681331, 32.306895),new BMap.Point(118.6786, 32.307505),new BMap.Point(118.676264, 32.308207),new BMap.Point(118.673066, 32.308604),new BMap.Point(118.672887, 32.308207),new BMap.Point(118.671198, 32.308878),new BMap.Point(118.668323, 32.31016),new BMap.Point(118.666419, 32.310099),new BMap.Point(118.665413, 32.310343),new BMap.Point(118.685301, 32.322717),new BMap.Point(118.702387, 32.323266),new BMap.Point(118.703663, 32.325509),new BMap.Point(118.691464, 32.329933),new BMap.Point(118.690493, 32.33309),new BMap.Point(118.709573, 32.335653),new BMap.Point(118.714478, 32.342715),new BMap.Point(118.703357, 32.349411),new BMap.Point(118.705675, 32.355763),new BMap.Point(118.700087, 32.360453),new BMap.Point(118.706807, 32.36457),new BMap.Point(118.706879, 32.364509),new BMap.Point(118.705333, 32.367772),new BMap.Point(118.731636, 32.349777),new BMap.Point(118.736523, 32.351073),new BMap.Point(118.752477, 32.345522),new BMap.Point(118.752548, 32.345491)], {
        strokeColor: "black",
        fillColor: "red",
        fillOpacity: 0.2,
        strokeWeight: 3,
        strokeOpacity: 0.2
    });
    map.addOverlay(polygon2);


    var polygon3 = new BMap.Polygon([new BMap.Point(118.857111, 32.301264),new BMap.Point(118.856177, 32.301447),new BMap.Point(118.83318, 32.301371),new BMap.Point(118.818996, 32.295007),new BMap.Point(118.818224, 32.29445),new BMap.Point(118.816508, 32.292161),new BMap.Point(118.816768, 32.28875),new BMap.Point(118.816122, 32.288552),new BMap.Point(118.81507, 32.288491),new BMap.Point(118.806752, 32.292321),new BMap.Point(118.805387, 32.293542),new BMap.Point(118.795856, 32.292497),new BMap.Point(118.768475, 32.266816),new BMap.Point(118.768502, 32.2659),new BMap.Point(118.770946, 32.2659),new BMap.Point(118.774251, 32.2659),new BMap.Point(118.777845, 32.265045),new BMap.Point(118.781294, 32.263946),new BMap.Point(118.787043, 32.26187),new BMap.Point(118.789343, 32.260649),new BMap.Point(118.791068, 32.259549),new BMap.Point(118.793511, 32.256862),new BMap.Point(118.795523, 32.254908),new BMap.Point(118.797248, 32.252221),new BMap.Point(118.799835, 32.249778),new BMap.Point(118.800985, 32.248068),new BMap.Point(118.803572, 32.245258),new BMap.Point(118.806734, 32.242937),new BMap.Point(118.808746, 32.241227),new BMap.Point(118.814711, 32.236707),new BMap.Point(118.846188, 32.234203),new BMap.Point(118.859555, 32.2262),new BMap.Point(118.873137, 32.205366),new BMap.Point(118.879461, 32.18795),new BMap.Point(118.963039, 32.183366),new BMap.Point(118.973478, 32.184344),new BMap.Point(118.96365, 32.194367),new BMap.Point(118.96295, 32.19481),new BMap.Point(118.962626, 32.195176),new BMap.Point(118.95535, 32.200905),new BMap.Point(118.952475, 32.20335),new BMap.Point(118.940402, 32.210682),new BMap.Point(118.937527, 32.21166),new BMap.Point(118.937527, 32.214104),new BMap.Point(118.937527, 32.21557),new BMap.Point(118.932928, 32.223879),new BMap.Point(118.932641, 32.226078),new BMap.Point(118.932353, 32.229255),new BMap.Point(118.932928, 32.232187),new BMap.Point(118.933216, 32.23463),new BMap.Point(118.932066, 32.237074),new BMap.Point(118.929479, 32.240494),new BMap.Point(118.927466, 32.243426),new BMap.Point(118.926892, 32.246358),new BMap.Point(118.926029, 32.248801),new BMap.Point(118.925733, 32.251244),new BMap.Point(118.925571, 32.251923),new BMap.Point(118.92558, 32.252412),new BMap.Point(118.925454, 32.252732),new BMap.Point(118.925086, 32.252885),new BMap.Point(118.924601, 32.252824),new BMap.Point(118.92399, 32.252816),new BMap.Point(118.923433, 32.252763),new BMap.Point(118.92275, 32.252633),new BMap.Point(118.922005, 32.252465),new BMap.Point(118.917118, 32.252221),new BMap.Point(118.915393, 32.251732),new BMap.Point(118.915106, 32.246602),new BMap.Point(118.916543, 32.24196),new BMap.Point(118.914243, 32.235852),new BMap.Point(118.913094, 32.234142),new BMap.Point(118.908782, 32.234142),new BMap.Point(118.895559, 32.245625),new BMap.Point(118.891534, 32.250267),new BMap.Point(118.890959, 32.253198),new BMap.Point(118.890097, 32.258817),new BMap.Point(118.885785, 32.261015),new BMap.Point(118.882048, 32.261504),new BMap.Point(118.873712, 32.263702),new BMap.Point(118.86825, 32.266633),new BMap.Point(118.866238, 32.26932),new BMap.Point(118.867675, 32.272251),new BMap.Point(118.871125, 32.274449),new BMap.Point(118.872562, 32.277135),new BMap.Point(118.873712, 32.280066),new BMap.Point(118.869256, 32.280676),new BMap.Point(118.865663, 32.279455),new BMap.Point(118.861243, 32.276616),new BMap.Point(118.858836, 32.275822),new BMap.Point(118.856824, 32.276067),new BMap.Point(118.855925, 32.277257),new BMap.Point(118.855746, 32.278417),new BMap.Point(118.855889, 32.27976),new BMap.Point(118.856357, 32.281012),new BMap.Point(118.857039, 32.282172),new BMap.Point(118.857399, 32.283393),new BMap.Point(118.858081, 32.283912),new BMap.Point(118.861064, 32.287636),new BMap.Point(118.868969, 32.294229),new BMap.Point(118.869831, 32.296182),new BMap.Point(118.868825, 32.296915),new BMap.Point(118.86825, 32.29777),new BMap.Point(118.866094, 32.297647),new BMap.Point(118.863938, 32.296915),new BMap.Point(118.857327, 32.297892),new BMap.Point(118.857183, 32.299112),new BMap.Point(118.856464, 32.299967)], {
        strokeColor: "black",
        fillColor: "yellow",
        fillOpacity: 0.2,
        strokeWeight: 3,
        strokeOpacity: 0.2
    });
    map.addOverlay(polygon3);

    var polygon4 = new BMap.Polygon([new BMap.Point(118.964477, 32.193817),new BMap.Point(118.96365, 32.194367),new BMap.Point(118.96295, 32.19481),new BMap.Point(118.962626, 32.195176),new BMap.Point(118.95535, 32.200905),new BMap.Point(118.952475, 32.20335),new BMap.Point(118.940402, 32.210682),new BMap.Point(118.937527, 32.21166),new BMap.Point(118.937527, 32.214104),new BMap.Point(118.937527, 32.21557),new BMap.Point(118.932928, 32.223879),new BMap.Point(118.932641, 32.226078),new BMap.Point(118.932353, 32.229255),new BMap.Point(118.932928, 32.232187),new BMap.Point(118.933216, 32.23463),new BMap.Point(118.932066, 32.237074),new BMap.Point(118.929479, 32.240494),new BMap.Point(118.927466, 32.243426),new BMap.Point(118.926892, 32.246358),new BMap.Point(118.926029, 32.248801),new BMap.Point(118.925733, 32.251244),new BMap.Point(118.925571, 32.251923),new BMap.Point(118.92558, 32.252412),new BMap.Point(118.925454, 32.252732),new BMap.Point(118.92991, 32.252587),new BMap.Point(118.935084, 32.252832),new BMap.Point(118.939827, 32.25271),new BMap.Point(118.943564, 32.252832),new BMap.Point(118.947876, 32.25271),new BMap.Point(118.951325, 32.252343),new BMap.Point(118.954344, 32.252343),new BMap.Point(118.957075, 32.252343),new BMap.Point(118.959231, 32.252343),new BMap.Point(118.962968, 32.253076),new BMap.Point(118.965698, 32.254053),new BMap.Point(118.967711, 32.254908),new BMap.Point(118.969435, 32.255885),new BMap.Point(118.969866, 32.256374),new BMap.Point(118.96886, 32.259427),new BMap.Point(118.968142, 32.262969),new BMap.Point(118.967279, 32.265656),new BMap.Point(118.966704, 32.269075),new BMap.Point(118.965986, 32.271762),new BMap.Point(118.965267, 32.274449),new BMap.Point(118.96498, 32.276525),new BMap.Point(118.963974, 32.279699),new BMap.Point(118.963399, 32.281897),new BMap.Point(118.96268, 32.283485),new BMap.Point(118.96153, 32.285072),new BMap.Point(118.958943, 32.286415),new BMap.Point(118.956787, 32.288247),new BMap.Point(118.9542, 32.291421),new BMap.Point(118.959087, 32.293985),new BMap.Point(118.962393, 32.295938),new BMap.Point(118.965411, 32.296915),new BMap.Point(118.967711, 32.297892),new BMap.Point(118.970154, 32.298868),new BMap.Point(118.972454, 32.300089),new BMap.Point(118.976909, 32.299845),new BMap.Point(118.97964, 32.301066),new BMap.Point(118.979496, 32.302042),new BMap.Point(118.97849, 32.302164),new BMap.Point(118.980215, 32.301432),new BMap.Point(118.982946, 32.300455),new BMap.Point(118.985533, 32.299357),new BMap.Point(118.987833, 32.298136),new BMap.Point(118.989989, 32.297281),new BMap.Point(118.991713, 32.296427),new BMap.Point(118.993869, 32.297647),new BMap.Point(118.994588, 32.299235),new BMap.Point(118.996456, 32.300822),new BMap.Point(119.002205, 32.302897),new BMap.Point(119.006517, 32.304362),new BMap.Point(119.006661, 32.305583),new BMap.Point(119.009572, 32.305552),new BMap.Point(119.015321, 32.30659),new BMap.Point(119.021501, 32.306406),new BMap.Point(119.023729, 32.305064),new BMap.Point(119.026029, 32.305491),new BMap.Point(119.028041, 32.306345),new BMap.Point(119.028831, 32.307017),new BMap.Point(119.032353, 32.306529),new BMap.Point(119.035227, 32.305552),new BMap.Point(119.036736, 32.306895),new BMap.Point(119.043204, 32.30836),new BMap.Point(119.045504, 32.307688),new BMap.Point(119.042629, 32.300913),new BMap.Point(119.047444, 32.277959),new BMap.Point(119.04342, 32.266236),new BMap.Point(119.066919, 32.24987),new BMap.Point(119.082873, 32.253656),new BMap.Point(119.09394, 32.246511),new BMap.Point(119.069614, 32.228766),new BMap.Point(119.040294, 32.197361),new BMap.Point(119.018052, 32.188164),new BMap.Point(118.973478, 32.184344)], {
        strokeColor: "black",
        fillColor: "purple",
        fillOpacity: 0.2,
        strokeWeight: 3,
        strokeOpacity: 0.2
    });
    map.addOverlay(polygon4);

    var polygon5 = new BMap.Polygon([new BMap.Point(118.768475, 32.266816),new BMap.Point(118.755791, 32.265915),new BMap.Point(118.757372, 32.271045),new BMap.Point(118.750114, 32.282157),new BMap.Point(118.738903, 32.296076),new BMap.Point(118.725393, 32.317316),new BMap.Point(118.725159, 32.317285),new BMap.Point(118.724764, 32.317163),new BMap.Point(118.724458, 32.317087),new BMap.Point(118.703465, 32.302683),new BMap.Point(118.70289, 32.302714),new BMap.Point(118.701884, 32.302988),new BMap.Point(118.700914, 32.303568),new BMap.Point(118.69998, 32.303263),new BMap.Point(118.69768, 32.304057),new BMap.Point(118.69556, 32.30427),new BMap.Point(118.694123, 32.30424),new BMap.Point(118.693871, 32.304667),new BMap.Point(118.692542, 32.305003),new BMap.Point(118.69132, 32.304911),new BMap.Point(118.689523, 32.30543),new BMap.Point(118.688302, 32.305674),new BMap.Point(118.686397, 32.306284),new BMap.Point(118.682301, 32.307139),new BMap.Point(118.681331, 32.306895),new BMap.Point(118.6786, 32.307505),new BMap.Point(118.676264, 32.308207),new BMap.Point(118.673066, 32.308604),new BMap.Point(118.672887, 32.308207),new BMap.Point(118.671198, 32.308878),new BMap.Point(118.668323, 32.31016),new BMap.Point(118.666419, 32.310099),new BMap.Point(118.665413, 32.310343),new BMap.Point(118.664227, 32.30913),new BMap.Point(118.66394, 32.307055),new BMap.Point(118.664514, 32.304491),new BMap.Point(118.666096, 32.303149),new BMap.Point(118.667677, 32.302172),new BMap.Point(118.669545, 32.30205),new BMap.Point(118.671701, 32.302172),new BMap.Point(118.674001, 32.301928),new BMap.Point(118.675294, 32.299975),new BMap.Point(118.6763, 32.297411),new BMap.Point(118.676588, 32.293138),new BMap.Point(118.675869, 32.29033),new BMap.Point(118.673857, 32.287766),new BMap.Point(118.672132, 32.284469),new BMap.Point(118.670407, 32.282393),new BMap.Point(118.66782, 32.277509),new BMap.Point(118.665808, 32.27177),new BMap.Point(118.666383, 32.269694),new BMap.Point(118.674863, 32.26371),new BMap.Point(118.678744, 32.262488),new BMap.Point(118.681474, 32.261023),new BMap.Point(118.681187, 32.258824),new BMap.Point(118.683199, 32.257359),new BMap.Point(118.68593, 32.256992),new BMap.Point(118.690242, 32.256626),new BMap.Point(118.704327, 32.255404),new BMap.Point(118.710795, 32.255038),new BMap.Point(118.712951, 32.255038),new BMap.Point(118.71755, 32.255282),new BMap.Point(118.721287, 32.255526),new BMap.Point(118.724449, 32.255649),new BMap.Point(118.727468, 32.25516),new BMap.Point(118.727037, 32.252351),new BMap.Point(118.727324, 32.246488),new BMap.Point(118.726749, 32.241968),new BMap.Point(118.73027, 32.241105),new BMap.Point(118.73142, 32.236707),new BMap.Point(118.73372, 32.232798),new BMap.Point(118.735445, 32.230843),new BMap.Point(118.735445, 32.227422),new BMap.Point(118.737744, 32.225956),new BMap.Point(118.738894, 32.225956),new BMap.Point(118.741769, 32.224979),new BMap.Point(118.744068, 32.223512),new BMap.Point(118.749243, 32.222535),new BMap.Point(118.749818, 32.224979),new BMap.Point(118.750967, 32.226933),new BMap.Point(118.750967, 32.229866),new BMap.Point(118.749818, 32.232309),new BMap.Point(118.748093, 32.235241),new BMap.Point(118.746368, 32.239151),new BMap.Point(118.748668, 32.242571),new BMap.Point(118.751542, 32.24306),new BMap.Point(118.756142, 32.241594),new BMap.Point(118.763041, 32.240128),new BMap.Point(118.76534, 32.240128),new BMap.Point(118.772239, 32.241594),new BMap.Point(118.776839, 32.243548),new BMap.Point(118.781438, 32.245991),new BMap.Point(118.784887, 32.249412),new BMap.Point(118.787187, 32.251855),new BMap.Point(118.792361, 32.254297),new BMap.Point(118.794984, 32.255412),new BMap.Point(118.793511, 32.256862),new BMap.Point(118.791068, 32.259549),new BMap.Point(118.789343, 32.260649),new BMap.Point(118.787043, 32.26187),new BMap.Point(118.781294, 32.263946),new BMap.Point(118.777845, 32.265045),new BMap.Point(118.774251, 32.2659),new BMap.Point(118.770946, 32.2659),new BMap.Point(118.768502, 32.2659)], {
        strokeColor: "black",
        fillColor: "orange",
        fillOpacity: 0.2,
        strokeWeight: 3,
        strokeOpacity: 0.2
    });
    map.addOverlay(polygon6);


	    var polygon6 = new BMap.Polygon([new BMap.Point(118.829551, 32.350723),new BMap.Point(118.858944, 32.339405),new BMap.Point(118.858081, 32.336416),new BMap.Point(118.851901, 32.330314),new BMap.Point(118.855386, 32.323846),new BMap.Point(118.856788, 32.317057),new BMap.Point(118.8505, 32.311396),new BMap.Point(118.857111, 32.301264),new BMap.Point(118.856464, 32.299967),new BMap.Point(118.857183, 32.299112),new BMap.Point(118.857327, 32.297892),new BMap.Point(118.863938, 32.296915),new BMap.Point(118.866094, 32.297647),new BMap.Point(118.86825, 32.29777),new BMap.Point(118.868825, 32.296915),new BMap.Point(118.869831, 32.296182),new BMap.Point(118.868969, 32.294229),new BMap.Point(118.861064, 32.287636),new BMap.Point(118.858081, 32.283912),new BMap.Point(118.857399, 32.283393),new BMap.Point(118.857039, 32.282172),new BMap.Point(118.856357, 32.281012),new BMap.Point(118.855889, 32.27976),new BMap.Point(118.855746, 32.278417),new BMap.Point(118.855925, 32.277257),new BMap.Point(118.856824, 32.276067),new BMap.Point(118.858836, 32.275822),new BMap.Point(118.861243, 32.276616),new BMap.Point(118.865663, 32.279455),new BMap.Point(118.869256, 32.280676),new BMap.Point(118.873712, 32.280066),new BMap.Point(118.872562, 32.277135),new BMap.Point(118.871125, 32.274449),new BMap.Point(118.867675, 32.272251),new BMap.Point(118.866238, 32.26932),new BMap.Point(118.86825, 32.266633),new BMap.Point(118.873712, 32.263702),new BMap.Point(118.882048, 32.261504),new BMap.Point(118.885785, 32.261015),new BMap.Point(118.890097, 32.258817),new BMap.Point(118.890959, 32.253198),new BMap.Point(118.891534, 32.250267),new BMap.Point(118.895559, 32.245625),new BMap.Point(118.908782, 32.234142),new BMap.Point(118.913094, 32.234142),new BMap.Point(118.914243, 32.235852),new BMap.Point(118.916543, 32.24196),new BMap.Point(118.915106, 32.246602),new BMap.Point(118.915393, 32.251732),new BMap.Point(118.917118, 32.252221),new BMap.Point(118.922005, 32.252465),new BMap.Point(118.92275, 32.252633),new BMap.Point(118.923433, 32.252763),new BMap.Point(118.92399, 32.252816),new BMap.Point(118.924601, 32.252824),new BMap.Point(118.925086, 32.252885),new BMap.Point(118.925454, 32.252732),new BMap.Point(118.92991, 32.252587),new BMap.Point(118.935084, 32.252832),new BMap.Point(118.939827, 32.25271),new BMap.Point(118.943564, 32.252832),new BMap.Point(118.947876, 32.25271),new BMap.Point(118.951325, 32.252343),new BMap.Point(118.954344, 32.252343),new BMap.Point(118.957075, 32.252343),new BMap.Point(118.959231, 32.252343),new BMap.Point(118.962968, 32.253076),new BMap.Point(118.965698, 32.254053),new BMap.Point(118.967711, 32.254908),new BMap.Point(118.969435, 32.255885),new BMap.Point(118.969866, 32.256374),new BMap.Point(118.96886, 32.259427),new BMap.Point(118.968142, 32.262969),new BMap.Point(118.967279, 32.265656),new BMap.Point(118.966704, 32.269075),new BMap.Point(118.965986, 32.271762),new BMap.Point(118.965267, 32.274449),new BMap.Point(118.96498, 32.276525),new BMap.Point(118.963974, 32.279699),new BMap.Point(118.963399, 32.281897),new BMap.Point(118.96268, 32.283485),new BMap.Point(118.96153, 32.285072),new BMap.Point(118.956787, 32.288247),new BMap.Point(118.9542, 32.291421),new BMap.Point(118.936737, 32.294488),new BMap.Point(118.923801, 32.295465),new BMap.Point(118.919777, 32.293207),new BMap.Point(118.912231, 32.28814),new BMap.Point(118.909788, 32.30633),new BMap.Point(118.909069, 32.313837),new BMap.Point(118.916256, 32.315058),new BMap.Point(118.919274, 32.318537),new BMap.Point(118.915609, 32.334707),new BMap.Point(118.908854, 32.347764),new BMap.Point(118.915393, 32.348618),new BMap.Point(118.92567, 32.358318),new BMap.Point(118.929766, 32.361063),new BMap.Point(118.939683, 32.366552),new BMap.Point(118.944858, 32.367284),new BMap.Point(118.949673, 32.36948),new BMap.Point(118.946942, 32.37253),new BMap.Point(118.937743, 32.376555),new BMap.Point(118.931994, 32.379421),new BMap.Point(118.924233, 32.384483),new BMap.Point(118.923514, 32.384849),new BMap.Point(118.913884, 32.39052),new BMap.Point(118.904398, 32.395154),new BMap.Point(118.901523, 32.395276),new BMap.Point(118.89793, 32.392349),new BMap.Point(118.898074, 32.390398),new BMap.Point(118.895631, 32.389178),new BMap.Point(118.893331, 32.386129),new BMap.Point(118.887869, 32.381373),new BMap.Point(118.886432, 32.380153),new BMap.Point(118.885426, 32.376982),new BMap.Point(118.876227, 32.371737),new BMap.Point(118.869041, 32.369541),new BMap.Point(118.865735, 32.363319),new BMap.Point(118.84087, 32.367955),new BMap.Point(118.83742, 32.365881),new BMap.Point(118.83354, 32.356122)], {
        strokeColor: "black",
        fillColor: "green",
        fillOpacity: 0.2,
        strokeWeight: 3,
        strokeOpacity: 0.2
    });
    map.addOverlay(polygon6);

		var polygon7 = new BMap.Polygon([new BMap.Point(118.936737, 32.294488),new BMap.Point(118.923801, 32.295465),new BMap.Point(118.919777, 32.293207),new BMap.Point(118.912231, 32.28814),new BMap.Point(118.909788, 32.30633),new BMap.Point(118.909069, 32.313837),new BMap.Point(118.916256, 32.315058),new BMap.Point(118.919274, 32.318537),new BMap.Point(118.915609, 32.334707),new BMap.Point(118.908854, 32.347764),new BMap.Point(118.915393, 32.348618),new BMap.Point(118.92567, 32.358318),new BMap.Point(118.929766, 32.361063),new BMap.Point(118.939683, 32.366552),new BMap.Point(118.944858, 32.367284),new BMap.Point(118.949673, 32.36948),new BMap.Point(118.946942, 32.37253),new BMap.Point(118.937743, 32.376555),new BMap.Point(118.931994, 32.379421),new BMap.Point(118.924233, 32.384483),new BMap.Point(118.923514, 32.384849),new BMap.Point(118.923496, 32.384849),new BMap.Point(118.930305, 32.390779),new BMap.Point(118.932928, 32.389651),new BMap.Point(118.933252, 32.389651),new BMap.Point(118.934006, 32.389682),new BMap.Point(118.936234, 32.390108),new BMap.Point(118.938785, 32.390566),new BMap.Point(118.94166, 32.391114),new BMap.Point(118.953553, 32.393188),new BMap.Point(118.956895, 32.392669),new BMap.Point(118.961818, 32.392121),new BMap.Point(118.962069, 32.392029),new BMap.Point(118.965483, 32.392944),new BMap.Point(118.967315, 32.393066),new BMap.Point(118.969759, 32.393645),new BMap.Point(118.970837, 32.39456),new BMap.Point(118.972454, 32.394926),new BMap.Point(118.98079, 32.400139),new BMap.Point(118.982299, 32.402273),new BMap.Point(118.984671, 32.403797),new BMap.Point(118.984814, 32.40398),new BMap.Point(118.988695, 32.405138),new BMap.Point(118.992791, 32.405687),new BMap.Point(119.000481, 32.40587),new BMap.Point(119.003858, 32.407211),new BMap.Point(119.014207, 32.409528),new BMap.Point(119.022543, 32.411479),new BMap.Point(119.022543, 32.411479),new BMap.Point(119.023046, 32.41154),new BMap.Point(119.029631, 32.397174),new BMap.Point(119.043563, 32.395474),new BMap.Point(119.045396, 32.391206),new BMap.Point(119.044821, 32.387944),new BMap.Point(119.031778, 32.38648),new BMap.Point(119.029586, 32.383553),new BMap.Point(119.050534, 32.37288),new BMap.Point(119.042378, 32.362237),new BMap.Point(119.033682, 32.357723),new BMap.Point(119.034832, 32.351409),new BMap.Point(119.048378, 32.339604),new BMap.Point(119.043563, 32.333197),new BMap.Point(119.04209, 32.315073),new BMap.Point(119.04633, 32.309763),new BMap.Point(119.045504, 32.307688),new BMap.Point(119.043204, 32.30836),new BMap.Point(119.036736, 32.306895),new BMap.Point(119.035227, 32.305552),new BMap.Point(119.032353, 32.306529),new BMap.Point(119.028831, 32.307017),new BMap.Point(119.028041, 32.306345),new BMap.Point(119.026029, 32.305491),new BMap.Point(119.023729, 32.305064),new BMap.Point(119.021501, 32.306406),new BMap.Point(119.015321, 32.30659),new BMap.Point(119.009572, 32.305552),new BMap.Point(119.006661, 32.305583),new BMap.Point(119.006517, 32.304362),new BMap.Point(119.002205, 32.302897),new BMap.Point(118.996456, 32.300822),new BMap.Point(118.994588, 32.299235),new BMap.Point(118.993869, 32.297647),new BMap.Point(118.991713, 32.296427),new BMap.Point(118.989989, 32.297281),new BMap.Point(118.987833, 32.298136),new BMap.Point(118.985533, 32.299357),new BMap.Point(118.982946, 32.300455),new BMap.Point(118.980215, 32.301432),new BMap.Point(118.97849, 32.302164),new BMap.Point(118.979496, 32.302042),new BMap.Point(118.97964, 32.301066),new BMap.Point(118.976909, 32.299845),new BMap.Point(118.972454, 32.300089),new BMap.Point(118.970154, 32.298868),new BMap.Point(118.967711, 32.297892),new BMap.Point(118.965411, 32.296915),new BMap.Point(118.962393, 32.295938),new BMap.Point(118.959087, 32.293985),new BMap.Point(118.9542, 32.291421)], {
        strokeColor: "black",
        fillColor: "red",
        fillOpacity: 0.2,
        strokeWeight: 3,
        strokeOpacity: 0.2
    });
    map.addOverlay(polygon7);

		var polygon8 = new BMap.Polygon([new BMap.Point(118.768475, 32.266816),new BMap.Point(118.755791, 32.265915),new BMap.Point(118.757372, 32.271045),new BMap.Point(118.750114, 32.282157),new BMap.Point(118.738903, 32.296076),new BMap.Point(118.725393, 32.317316),new BMap.Point(118.725159, 32.317285),new BMap.Point(118.724764, 32.317163),new BMap.Point(118.724458, 32.317087),new BMap.Point(118.703465, 32.302683),new BMap.Point(118.70289, 32.302714),new BMap.Point(118.701884, 32.302988),new BMap.Point(118.700914, 32.303568),new BMap.Point(118.69998, 32.303263),new BMap.Point(118.69768, 32.304057),new BMap.Point(118.69556, 32.30427),new BMap.Point(118.694123, 32.30424),new BMap.Point(118.693871, 32.304667),new BMap.Point(118.692542, 32.305003),new BMap.Point(118.69132, 32.304911),new BMap.Point(118.689523, 32.30543),new BMap.Point(118.688302, 32.305674),new BMap.Point(118.686397, 32.306284),new BMap.Point(118.682301, 32.307139),new BMap.Point(118.681331, 32.306895),new BMap.Point(118.6786, 32.307505),new BMap.Point(118.676264, 32.308207),new BMap.Point(118.673066, 32.308604),new BMap.Point(118.672887, 32.308207),new BMap.Point(118.671198, 32.308878),new BMap.Point(118.668323, 32.31016),new BMap.Point(118.666419, 32.310099),new BMap.Point(118.665413, 32.310343),new BMap.Point(118.665233, 32.304789),new BMap.Point(118.675222, 32.301981),new BMap.Point(118.677378, 32.292825),new BMap.Point(118.667659, 32.270877),new BMap.Point(118.674162, 32.264343),new BMap.Point(118.682472, 32.261473),new BMap.Point(118.683675, 32.256481),new BMap.Point(118.683774, 32.256435),new BMap.Point(118.725312, 32.254969),new BMap.Point(118.730774, 32.221252),new BMap.Point(118.737888, 32.229988),new BMap.Point(118.74105, 32.234508),new BMap.Point(118.742056, 32.236952),new BMap.Point(118.741625, 32.239761),new BMap.Point(118.743494, 32.245258),new BMap.Point(118.746656, 32.245625),new BMap.Point(118.749099, 32.245503),new BMap.Point(118.751399, 32.245014),new BMap.Point(118.754848, 32.245869),new BMap.Point(118.755567, 32.248068),new BMap.Point(118.757723, 32.251855),new BMap.Point(118.762178, 32.256374),new BMap.Point(118.763616, 32.256862),new BMap.Point(118.767784, 32.256862),new BMap.Point(118.770227, 32.257595),new BMap.Point(118.773389, 32.260771),new BMap.Point(118.77382, 32.263091),new BMap.Point(118.773102, 32.264618),new BMap.Point(118.772239, 32.265228),new BMap.Point(118.771305, 32.265656),new BMap.Point(118.769724, 32.265656),new BMap.Point(118.768466, 32.2659),new BMap.Point(118.768484, 32.265908)], {
        strokeColor: "black",
        fillColor: "blue",
        fillOpacity: 0.2,
        strokeWeight: 3,
        strokeOpacity: 0.2
    });
    map.addOverlay(polygon8);

		var polygon9 = new BMap.Polygon([new BMap.Point(118.923487, 32.384864),new BMap.Point(118.904389, 32.395185),new BMap.Point(118.901461, 32.395291),new BMap.Point(118.896232, 32.41154),new BMap.Point(118.900257, 32.418855),new BMap.Point(118.886459, 32.427145),new BMap.Point(118.887034, 32.434947),new BMap.Point(118.899107, 32.44031),new BMap.Point(118.900832, 32.446648),new BMap.Point(118.899682, 32.452498),new BMap.Point(118.892208, 32.458835),new BMap.Point(118.893933, 32.466147),new BMap.Point(118.906006, 32.469559),new BMap.Point(118.91003, 32.473458),new BMap.Point(118.91233, 32.478819),new BMap.Point(118.918654, 32.485154),new BMap.Point(118.922679, 32.485154),new BMap.Point(118.931302, 32.483692),new BMap.Point(118.939926, 32.484667),new BMap.Point(118.94625, 32.488565),new BMap.Point(118.952574, 32.493925),new BMap.Point(118.956599, 32.499772),new BMap.Point(118.959473, 32.502695),new BMap.Point(118.974996, 32.507567),new BMap.Point(118.98017, 32.511465),new BMap.Point(118.980889, 32.5125),new BMap.Point(118.99713, 32.509212),new BMap.Point(119.006257, 32.510917),new BMap.Point(119.014018, 32.520112),new BMap.Point(119.026091, 32.524557),new BMap.Point(119.035865, 32.521756),new BMap.Point(119.045207, 32.522243),new BMap.Point(119.05225, 32.504279),new BMap.Point(119.070935, 32.488017),new BMap.Point(119.073594, 32.468645),new BMap.Point(119.053112, 32.469498),new BMap.Point(119.042261, 32.458835),new BMap.Point(119.037159, 32.461516),new BMap.Point(119.029972, 32.457068),new BMap.Point(119.028607, 32.447745),new BMap.Point(119.03591, 32.435137),new BMap.Point(119.030718, 32.427107),new BMap.Point(119.024367, 32.424402),new BMap.Point(119.022983, 32.411555),new BMap.Point(119.02257, 32.411494),new BMap.Point(119.000472, 32.40587),new BMap.Point(118.988686, 32.405138),new BMap.Point(118.984805, 32.403988),new BMap.Point(118.984662, 32.403797),new BMap.Point(118.98229, 32.40228),new BMap.Point(118.98079, 32.400146),new BMap.Point(118.972463, 32.394926),new BMap.Point(118.970837, 32.39456),new BMap.Point(118.969741, 32.393653),new BMap.Point(118.967324, 32.393073),new BMap.Point(118.965474, 32.392944),new BMap.Point(118.96206, 32.392029),new BMap.Point(118.9618, 32.392113),new BMap.Point(118.953535, 32.393188),new BMap.Point(118.933979, 32.389682),new BMap.Point(118.932901, 32.389651),new BMap.Point(118.930296, 32.390764)], {
        strokeColor: "black",
        fillColor: "blue",
        fillOpacity: 0.2,
        strokeWeight: 3,
        strokeOpacity: 0.2
    });

    map.addOverlay(polygon9);

		var polygon10 = new BMap.Polygon([new BMap.Point(118.892199, 32.458828),new BMap.Point(118.882623, 32.472666),new BMap.Point(118.880323, 32.480951),new BMap.Point(118.870837, 32.477783),new BMap.Point(118.857039, 32.474859),new BMap.Point(118.853302, 32.48022),new BMap.Point(118.85244, 32.488261),new BMap.Point(118.861064, 32.493133),new BMap.Point(118.870837, 32.499955),new BMap.Point(118.873137, 32.506289),new BMap.Point(118.866238, 32.512866),new BMap.Point(118.870262, 32.522852),new BMap.Point(118.867388, 32.525774),new BMap.Point(118.864513, 32.52894),new BMap.Point(118.862214, 32.534541),new BMap.Point(118.866525, 32.538681),new BMap.Point(118.870837, 32.54136),new BMap.Point(118.872275, 32.549882),new BMap.Point(118.868825, 32.552804),new BMap.Point(118.863938, 32.557429),new BMap.Point(118.863651, 32.562785),new BMap.Point(118.864226, 32.567654),new BMap.Point(118.8694, 32.576904),new BMap.Point(118.865376, 32.580068),new BMap.Point(118.879605, 32.582137),new BMap.Point(118.88018, 32.587856),new BMap.Point(118.887797, 32.585058),new BMap.Point(118.891391, 32.592237),new BMap.Point(118.902601, 32.592237),new BMap.Point(118.914252, 32.598747),new BMap.Point(118.915618, 32.592815),new BMap.Point(118.90332, 32.582137),new BMap.Point(118.904614, 32.567045),new BMap.Point(118.900589, 32.567654),new BMap.Point(118.894984, 32.561081),new BMap.Point(118.905189, 32.559499),new BMap.Point(118.911944, 32.563516),new BMap.Point(118.91295, 32.558647),new BMap.Point(118.920567, 32.555847),new BMap.Point(118.923011, 32.562785),new BMap.Point(118.942845, 32.564246),new BMap.Point(118.951469, 32.549517),new BMap.Point(118.975759, 32.533324),new BMap.Point(118.98088, 32.5125),new BMap.Point(118.980143, 32.51148),new BMap.Point(118.975005, 32.507567),new BMap.Point(118.959428, 32.502695),new BMap.Point(118.952565, 32.49394),new BMap.Point(118.946187, 32.48855),new BMap.Point(118.939935, 32.484667),new BMap.Point(118.931275, 32.483707),new BMap.Point(118.92267, 32.485154),new BMap.Point(118.918645, 32.485154),new BMap.Point(118.912312, 32.478804),new BMap.Point(118.910021, 32.473465),new BMap.Point(118.905988, 32.469551),new BMap.Point(118.893924, 32.466139)], {
        strokeColor: "black",
        fillColor: "yellow",
        fillOpacity: 0.2,
        strokeWeight: 3,
        strokeOpacity: 0.2
    });
    map.addOverlay(polygon10);

		var polygon11 = new BMap.Polygon([new BMap.Point(118.745353, 32.595446),new BMap.Point(118.74635, 32.592115),new BMap.Point(118.756133, 32.588701),new BMap.Point(118.762439, 32.587248),new BMap.Point(118.765888, 32.581407),new BMap.Point(118.770506, 32.577992),new BMap.Point(118.768197, 32.570704),new BMap.Point(118.752683, 32.559993),new BMap.Point(118.763032, 32.545857),new BMap.Point(118.770488, 32.545857),new BMap.Point(118.771637, 32.54101),new BMap.Point(118.774521, 32.53515),new BMap.Point(118.754399, 32.522479),new BMap.Point(118.754965, 32.516161),new BMap.Point(118.761891, 32.509828),new BMap.Point(118.765897, 32.510787),new BMap.Point(118.769356, 32.507864),new BMap.Point(118.771071, 32.502992),new BMap.Point(118.765322, 32.491314),new BMap.Point(118.76199, 32.495783),new BMap.Point(118.764262, 32.48558),new BMap.Point(118.769167, 32.486281),new BMap.Point(118.772904, 32.479276),new BMap.Point(118.775788, 32.478788),new BMap.Point(118.776928, 32.479946),new BMap.Point(118.777584, 32.483966),new BMap.Point(118.779372, 32.483844),new BMap.Point(118.782911, 32.485063),new BMap.Point(118.791948, 32.486707),new BMap.Point(118.778581, 32.448012),new BMap.Point(118.779165, 32.443136),new BMap.Point(118.770541, 32.43046),new BMap.Point(118.77859, 32.360712),new BMap.Point(118.784465, 32.36306),new BMap.Point(118.788894, 32.358058),new BMap.Point(118.762681, 32.347024),new BMap.Point(118.767936, 32.346673),new BMap.Point(118.7808, 32.337979),new BMap.Point(118.779201, 32.332534),new BMap.Point(118.779875, 32.328636),new BMap.Point(118.794643, 32.335333),new BMap.Point(118.802863, 32.334715),new BMap.Point(118.804713, 32.331588),new BMap.Point(118.80977, 32.326935),new BMap.Point(118.811522, 32.326706),new BMap.Point(118.813885, 32.329841),new BMap.Point(118.813813, 32.330932),new BMap.Point(118.810022, 32.334906),new BMap.Point(118.809088, 32.339955),new BMap.Point(118.810732, 32.342967),new BMap.Point(118.829551, 32.350723),new BMap.Point(118.833522, 32.356114),new BMap.Point(118.83742, 32.365881),new BMap.Point(118.84087, 32.367947),new BMap.Point(118.865726, 32.363327),new BMap.Point(118.869041, 32.369541),new BMap.Point(118.876218, 32.371729),new BMap.Point(118.885426, 32.376989),new BMap.Point(118.886414, 32.380145),new BMap.Point(118.89334, 32.386137),new BMap.Point(118.895622, 32.389178),new BMap.Point(118.898056, 32.390406),new BMap.Point(118.897921, 32.392349),new BMap.Point(118.901496, 32.395276),new BMap.Point(118.901452, 32.395291),new BMap.Point(118.896223, 32.411547),new BMap.Point(118.900239, 32.418855),new BMap.Point(118.886432, 32.427153),new BMap.Point(118.887025, 32.434955),new BMap.Point(118.899098, 32.440325),new BMap.Point(118.900814, 32.446648),new BMap.Point(118.899673, 32.452491),new BMap.Point(118.892181, 32.458835),new BMap.Point(118.892181, 32.458828),new BMap.Point(118.882605, 32.472673),new BMap.Point(118.880296, 32.480943),new BMap.Point(118.870828, 32.477783),new BMap.Point(118.85703, 32.474867),new BMap.Point(118.853293, 32.48022),new BMap.Point(118.852431, 32.488245),new BMap.Point(118.861082, 32.493156),new BMap.Point(118.870828, 32.499955),new BMap.Point(118.873119, 32.506289),new BMap.Point(118.866238, 32.512866),new BMap.Point(118.870235, 32.522859),new BMap.Point(118.864495, 32.528933),new BMap.Point(118.862196, 32.534549),new BMap.Point(118.866507, 32.538689),new BMap.Point(118.870828, 32.541367),new BMap.Point(118.872266, 32.549874),new BMap.Point(118.863929, 32.557422),new BMap.Point(118.863633, 32.562785),new BMap.Point(118.864217, 32.567654),new BMap.Point(118.869382, 32.576904),new BMap.Point(118.865376, 32.580068),new BMap.Point(118.865169, 32.580357),new BMap.Point(118.862591, 32.580038),new BMap.Point(118.850122, 32.572872),new BMap.Point(118.851164, 32.580593),new BMap.Point(118.842693, 32.576592),new BMap.Point(118.833567, 32.578737),new BMap.Point(118.825787, 32.590275),new BMap.Point(118.831932, 32.601469),new BMap.Point(118.828662, 32.610259),new BMap.Point(118.828662, 32.610259),new BMap.Point(118.811738, 32.599781),new BMap.Point(118.80863, 32.590898),new BMap.Point(118.791229, 32.588716),new BMap.Point(118.780674, 32.59391),new BMap.Point(118.781995, 32.604123),new BMap.Point(118.769481, 32.609567),new BMap.Point(118.750949, 32.604503),new BMap.Point(118.748856, 32.596496),new BMap.Point(118.745353, 32.595431)], {
        strokeColor: "black",
        fillColor: "black",
        fillOpacity: 0.2,
        strokeWeight: 3,
        strokeOpacity: 0.2
    });
    map.addOverlay(polygon11);

	//将map变量存储在全局
    window.map = map; 
}





	//添加标注点
function initMark() {
	var marker;
	var infoWindow;
    for (i = 0; i < longString.length; i++) {
    	var point = new BMap.Point(longString[i],latiString[i]);
    	var hotSpot = new BMap.Hotspot(point, {text: addString[i]});					//创建文字标注语
    	map.addHotspot(hotSpot);                                                        //文字标注语添加到底图中
        marker = new BMap.Marker(new BMap.Point(longString[i],latiString[i]));		// 创建标注点
        map.addOverlay(marker);															// 将标注添加到地图中
        //创建信息窗口
		//alert(markArrey[i][2]);
        //infoWindow = new BMap.InfoWindow(markArrey[i][2]);
        //marker.addEventListener("click",
        //function() {
        //    this.openInfoWindow(infoWindow);
        //});
    }

}

//地图事件设置函数：
function setMapEvent() {
    map.enableDragging();			//启用地图拖拽事件，默认启用(可不写)
    map.enableScrollWheelZoom();	//启用地图滚轮放大缩小
    map.enableDoubleClickZoom();	//启用鼠标双击放大，默认启用(可不写)
    map.disableKeyboard();			//禁用键盘上下左右键移动地图，默认禁用(可不写)
}

//地图控件添加函数：
function addMapControl() {
    //向地图中添加缩放控件
    var ctrl_nav = new BMap.NavigationControl({
        anchor: BMAP_ANCHOR_TOP_LEFT,
        type: BMAP_NAVIGATION_CONTROL_LARGE
    });
    map.addControl(ctrl_nav);
    //向地图中添加缩略图控件
    var ctrl_ove = new BMap.OverviewMapControl({
        anchor: BMAP_ANCHOR_BOTTOM_RIGHT,
        isOpen: 1
    });
    map.addControl(ctrl_ove);
    //向地图中添加比例尺控件
    var ctrl_sca = new BMap.ScaleControl({
        anchor: BMAP_ANCHOR_BOTTOM_LEFT
    });
    map.addControl(ctrl_sca);
}

initMap(mapPoint1, mapPoint2,level);	//创建和初始化地图


map.addEventListener("click", function(e){
  document.getElementById("r-result").innerHTML =  e.point.lng + ", " + e.point.lat ;
});

$(function () {
    var chart;
    
    $(document).ready(function () {
    	
    	// Build the chart
        $('#container').highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false
            },
            title: {
                text: ''
            },
            tooltip: {
                formatter: function() {
                    return '<b>'+ this.point.name +'</b>: '+ Highcharts.numberFormat(this.percentage, 1) +'% ('+
                                 Highcharts.numberFormat(this.y, 0, ',') +' 个)';
                 }
              },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: true,
                        distance: -50,
                        style: {
                            fontWeight: 'bold',
                            color: 'white',
                            textShadow: '0px 1px 2px black'
                        }
                    },
                    showInLegend: true
                }
            },
            series: [{
                type: 'pie',
                name: '比重',
                data: regionLatis
            }]
        });
    });
    
});
</script>
<script src="${base}/js/modules/exporting.js"></script>
<script src="${base}/js/highcharts.js"></script>
</html>