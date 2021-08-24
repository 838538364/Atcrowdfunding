<%--
  Created by IntelliJ IDEA.
  User: 83853
  Date: 2021/8/13
  Time: 16:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/doc.min.css">
    <style>
        .tree li {
            list-style-type: none;
            cursor: pointer;
        }
    </style>
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <div><a class="navbar-brand" style="font-size:32px;" href="user.html">众筹平台 - 用户维护</a></div>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <jsp:include page="/WEB-INF/jsp/common/top.jsp"/>
            </ul>
            <form class="navbar-form navbar-right">
                <input type="text" class="form-control" placeholder="Search...">
            </form>
        </div>
    </div>
</nav>

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <div class="tree">
                <jsp:include page="/WEB-INF/jsp/common/menu.jsp"/>
            </div>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="#">首页</a></li>
                <li><a href="#">数据列表</a></li>
                <li class="active">分配角色</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-body">
                    <form role="form" class="form-inline">
                        <div class="form-group">
                            <label for="leftRoleList">未分配角色列表</label><br>
                            <select class="form-control" id="leftRoleList" multiple size="10"
                                    style="width:250px;overflow-y:auto;">
                                <c:forEach items="${unassign }" var="role">
                                    <option value="${role.id}">${role.name}</option>
                                </c:forEach>


                            </select>
                        </div>
                        <div class="form-group">
                            <ul>
                                <li id="leftToRightBtn" class="btn btn-default glyphicon glyphicon-chevron-right"></li>
                                <br>
                                <li id="rightToLeftBtn" class="btn btn-default glyphicon glyphicon-chevron-left"
                                    style="margin-top:20px;"></li>
                            </ul>
                        </div>
                        <div class="form-group" style="margin-left:40px;">
                            <label for="rightRoleList">已分配角色列表</label><br>
                            <select class="form-control" id="rightRoleList" multiple size="10"
                                    style="width:250px;overflow-y:auto;">
                                <c:forEach items="${assign}" var="role">
                                    <option value="${role.id}">${role.name}</option>
                                </c:forEach>


                            </select>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">帮助</h4>
            </div>
            <div class="modal-body">
                <div class="bs-callout bs-callout-info">
                    <h4>测试标题1</h4>
                    <p>测试内容1，测试内容1，测试内容1，测试内容1，测试内容1，测试内容1</p>
                </div>
                <div class="bs-callout bs-callout-info">
                    <h4>测试标题2</h4>
                    <p>测试内容2，测试内容2，测试内容2，测试内容2，测试内容2，测试内容2</p>
                </div>
            </div>
            <!--
            <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
              <button type="button" class="btn btn-primary">Save changes</button>
            </div>
            -->
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/jquery/jquery-2.1.1.min.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/script/docs.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/layer/layer.js"></script>
<script type="text/javascript">
    $(function () {
        $(".list-group-item").click(function () {
            if ($(this).find("ul")) {
                $(this).toggleClass("tree-closed");
                if ($(this).hasClass("tree-closed")) {
                    $("ul", this).hide("fast");
                } else {
                    $("ul", this).show("fast");
                }
            }
        });
    });

    $("#leftToRightBtn").click(function () {

        var items = $("#leftRoleList option:selected");
        if (items.length == 0) {
            layer.msg("请选择要分配的角色", {time: 1000, icon: 6});
        } else {
            var data = {
                "userid": "${param.id}"
            };
            $.each(items, function (i, n) {
                data["ids[" + i + "]"] = this.value;
            });
            $.ajax({
                type:"POST",
                url:"${pageContext.request.contextPath}/user/assign.do",
                data:data,
                success:function (result) {
                    if (result.success){
                        layer.msg("分配的角色成功", {time:1000, icon:6});
                        $("#rightRoleList").append(items.clone());
                        items.remove();
                    }else{
                        layer.msg("分配的角色失败", {time:1000, icon:5});
                    }
                }
            });
        }
    });

    $("#rightToLeftBtn").click(function () {

        var items = $("#rightRoleList option:selected");
        if (items.length == 0) {
            layer.msg("请选择要取消分配的角色", {time: 1000, icon: 6});
        }  {
            var data = {
                "userid": "${param.id}"
            };
            $.each(items, function (i, n) {
                data["ids[" + i + "]"] = this.value;
            });
            $.ajax({
                type:"POST",
                url:"${pageContext.request.contextPath}/user/unassign.do",
                data:data,
                success:function (result) {
                    if (result.success){
                        layer.msg("分配的角色成功", {time:1000, icon:6});
                        $("#leftRoleList").append(items.clone());
                        items.remove();
                    }else{
                        layer.msg("分配的角色失败", {time:1000, icon:5});
                    }
                }
            });
        }
    });

</script>
</body>
</html>
