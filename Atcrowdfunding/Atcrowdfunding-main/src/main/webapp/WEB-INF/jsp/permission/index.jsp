<%--
  Created by IntelliJ IDEA.
  User: 83853
  Date: 2021/8/14
  Time: 14:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/ztree/zTreeStyle.css">
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
            <div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 许可维护</a></div>
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

            <div class="panel panel-default">
                <div class="panel-heading"><i class="glyphicon glyphicon-th-list"></i> 权限菜单列表
                    <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i
                            class="glyphicon glyphicon-question-sign"></i>
                    </div>
                </div>
                <div class="panel-body">
                    <ul id="treeDemo" class="ztree"></ul>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/jquery/jquery-2.1.1.min.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/script/docs.min.js"></script>
<script src="${pageContext.request.contextPath}/ztree/jquery.ztree.all-3.5.min.js"></script>
<script src="${pageContext.request.contextPath}/script/menu.js"></script>
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


        showMenu();
        loadData();
    });




        var setting = {
            view: {

                addDiyDom: function (treeId, treeNode) {
                    var icoObj = $("#" + treeNode.tId + "_ico"); // tId = permissionTree_1, $("#permissionTree_1_ico")
                    if (treeNode.icon) {
                        icoObj.removeClass("button ico_docu ico_open").addClass(treeNode.icon).css("background", "");
                    }
                },

                addHoverDom: function (treeId, treeNode) {
                    var aObj = $("#" + treeNode.tId + "_a"); // tId = permissionTree_1, ==> $("#permissionTree_1_a")
                    aObj.attr("href", "#").attr("target", "_parent");
                    if (treeNode.editNameFlag || $("#btnGroup" + treeNode.tId).length > 0) return;
                    var s = '<span id="btnGroup' + treeNode.tId + '">';
                    if (treeNode.level == 0) {
                        s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" href="#" onclick="window.location.href=\'${pageContext.request.contextPath}/permission/toAdd.htm?id=' + treeNode.id + '\'" >&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
                    } else if (treeNode.level == 1) {
                        s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;"  href="#" onclick="window.location.href=\'${pageContext.request.contextPath}/permission/toUpdate.htm?id=' + treeNode.id + '\'" title="修改权限信息">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
                        if (treeNode.children.length == 0) {
                            s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" href="#" onclick="deletePermission(' + treeNode.id + ',\'' + treeNode.name + '\')" >&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
                        }
                        s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" href="#" onclick="window.location.href=\'${pageContext.request.contextPath}/permission/toAdd.htm?id=' + treeNode.id + '\'">&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
                    } else if (treeNode.level == 2) {
                        s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;"  href="#" onclick="window.location.href=\'${pageContext.request.contextPath}/permission/toUpdate.htm?id=' + treeNode.id + '\'" title="修改权限信息">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
                        s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" href="#" onclick="deletePermission(' + treeNode.id + ',\'' + treeNode.name + '\')" >&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
                    }

                    s += '</span>';
                    aObj.after(s);
                },
                removeHoverDom: function (treeId, treeNode) {
                    $("#btnGroup" + treeNode.tId).remove();
                }
            }
        }




    function deletePermission(id, name) {

        layer.confirm("确定要删除[" + name + "]吗?", {icon: 3, title: '提示'}, function (cindex) {
            var datas = {
                id: id
            };
            $.ajax({
                url: "${pageContext.request.contextPath}/permission/deletePermission.do",
                type: "POST",
                data: datas,
                success: function (result) {
                    if (result.success) {
                        layer.msg("删除许可成功!", {time: 1000, icon: 6}, function () {
                           loadData();
                        });
                    } else {
                        layer.msg("删除许可失败!", {time: 1000, icon: 5});
                    }
                }
            });
            layer.close(cindex);
        }, function (cindex) {
            layer.close(cindex);
        });

    }


    function loadData() {
        $.ajax({
            type: "post",
            url: "${pageContext.request.contextPath}/permission/loadData.do",
            success: function (result) {

                if (result.success) {

                    var zNodes = result.data;

                    $.fn.zTree.init($("#treeDemo"), setting, zNodes);

                } else {
                    alert("加载数据失败");
                }
            }
        });
    }

</script>
</body>
</html>

