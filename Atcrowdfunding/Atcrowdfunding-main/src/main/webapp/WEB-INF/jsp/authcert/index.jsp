<%--
  Created by IntelliJ IDEA.
  User: 83853
  Date: 2021/8/22
  Time: 18:35
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
    <style>
        .tree li {
            list-style-type: none;
            cursor:pointer;
        }
        table tbody tr:nth-child(odd){background:#F4F4F4;}
        table tbody td:nth-child(even){color:#C00;}
    </style>
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 实名认证审核</a></div>
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
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr >
                                <th width="30">#</th>
                                <th>流程名称</th>
                                <th>流程版本</th>
                                <th>任务名称</th>
                                <th>申请会员</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>

                            </tbody>
                            <tfoot>
                            <tr >
                                <td colspan="6" align="center">
                                    <ul class="pagination">

                                    </ul>
                                </td>
                            </tr>

                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/jquery/jquery-2.1.1.min.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/script/docs.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/layer/layer.js"></script>
<script type="text/javascript">
    $(function () {
        $(".list-group-item").click(function(){
            if ( $(this).find("ul") ) {
                $(this).toggleClass("tree-closed");
                if ( $(this).hasClass("tree-closed") ) {
                    $("ul", this).hide("fast");
                } else {
                    $("ul", this).show("fast");
                }
            }
        });
        queryPageUser(1);
    });

    function pageChange(pageno) {
        queryPageUser(pageno);
    }





    function queryPageUser(pageindex) {

        var loadingIndex = -1;

        var obj = {
            "pageno" : pageindex,
            "pagesize" : 10
        };
        $.ajax({

            url : "${pageContext.request.contextPath}/auth_cert/pageQuery.do",
            type : "POST",
            data : obj,
            beforeSend : function() {
                loadingIndex = layer.msg('数据查询中', {icon: 16});
                return true;
            },

            success : function( result ) {
                layer.close(loadingIndex);
                if ( result.success ) {
                    // 将查询结果循环遍历，将数据展现出来
                    var page = result.page;
                    var taskList = page.data;
                    var content = "";
                    $.each(taskList, function(i, task){

                        content += '<tr>';

                        content += '  <td>'+(i+1)+'</td>';

                        content += '  <td>'+task.procDefName+'</td>';

                        content += '  <td>'+task.procDefVersion+'</td>';

                        content += '  <td>'+task.name+'</td>';

                        content += '  <td>'+task.memberUsername+'</td>';

                        content += '  <td>';

                        content += '      <button type="button" onclick="window.location.href=\'${pageContext.request.contextPath}/auth_cert/show.htm?taskid='+task.id+'&memberid='+task.memberid+'\'" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>';

                        content += '  </td>';

                        content += '</tr>';

                    });
                    $("tbody").html(content);
                    // 创建分页
                    var contentNavigater = '';
                    if (page.pageno == 1) {
                        contentNavigater += '<li class="disabled"><a href="#">上一页</a></li>';
                    } else {
                        contentNavigater += '<li><a href="#" onclick="pageChange(' + (page.pageno - 1) + ')">上一页</a></li>';
                    }
                    for (var i = 1; i <= page.totalno; i++) {
                        if (page.pageno == i) {
                            contentNavigater += '<li class="active"><a href="#" onclick="pageChange(' + i + ')">' + i + '</a></li>';
                        } else {
                            contentNavigater += '<li><a href="#" onclick="pageChange(' + i + ')">' + i + '</a></li>';
                        }
                    }
                    if (page.pageno == page.totalno) {
                        contentNavigater += '<li class="disabled"><a href="#">下一页</a></li>';
                    } else {
                        contentNavigater += '<li><a href="#" onclick="pageChange(' + (page.pageno + 1) + ')">下一页</a></li>';
                    }
                    $(".pagination").html(contentNavigater);
                } else {
                    layer.msg("审核任务分页查询失败", {time:1000, icon:5, shift:6});
                }
            }
        });
    }



</script>
</body>
</html>
