<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
    <base href="<%=basePath%>" />
    <link type="text/css" href="res/css/style.css" rel="stylesheet" />
    <script type="text/javascript" src="res/js/jquery-3.2.1.js"></script>
    <script type="text/javascript" src="res/js/common.js"></script>
    <script type="text/javascript" src="res/js/my-ui.js"></script>
