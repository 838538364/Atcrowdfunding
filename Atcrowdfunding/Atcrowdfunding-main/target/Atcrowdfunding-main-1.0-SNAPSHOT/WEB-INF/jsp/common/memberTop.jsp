<%--
  Created by IntelliJ IDEA.
  User: 83853
  Date: 2021/8/20
  Time: 20:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<div id="navbar" class="navbar-collapse collapse" style="float:right;">
    <ul class="nav navbar-nav">
        <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="glyphicon glyphicon-user"></i>${member.loginacct}<span class="caret"></span></a>
            <ul class="dropdown-menu" role="menu">
                <li><a href="member.html"><i class="glyphicon glyphicon-scale"></i> 会员中心</a></li>
                <li><a href="#"><i class="glyphicon glyphicon-comment"></i> 消息</a></li>
                <li class="divider"></li>
                <li><a href="${pageContext.request.contextPath}/logout.do"><i class="glyphicon glyphicon-off"></i> 退出系统</a></li>
            </ul>
        </li>
    </ul>
</div>