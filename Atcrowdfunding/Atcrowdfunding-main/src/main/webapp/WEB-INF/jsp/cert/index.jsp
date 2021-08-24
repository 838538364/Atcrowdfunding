<%--
  Created by IntelliJ IDEA.
  User: 83853
  Date: 2021/8/16
  Time: 19:57
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
            <div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 资质管理</a></div>
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
                                <input id="queryText" class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button type="button" id="queryBtn" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <button type="button" id="deleteBatchBtn" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <button type="button" class="btn btn-primary" style="float:right;" onclick="window.location.href='${pageContext.request.contextPath}/cert/toAdd.htm'"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr >
                                <th width="30">#</th>
                                <th width="30"><input id="allCheckbox" type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>


                            </tbody>
                            <tfoot>
                            <tr>
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
<script src="${pageContext.request.contextPath}/script/menu.js"></script>
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

    var loadingIndex = -1;
    var jsonObj = {
        "pageno": 1,
        "pagesize": 10
    };

    function queryPageUser(pageno) {
        jsonObj.pageno = pageno;
        $.ajax({
            type: "POST",
            data: jsonObj,
            url: "${pageContext.request.contextPath}/cert/toIndex.do",
            beforeSend: function () {
                loadingIndex = layer.load(2, {time: 10 * 1000});
                return true;
            },
            success: function (result) {
                layer.close(loadingIndex);
                if (result.success) {
                    var page = result.page;
                    var data = page.certList;
                    var content = '';
                    $.each(data, function (index, cert) {

                        content += '<tr>';

                        content += '  <td>' + (index + 1) + '</td>';

                        content += '  <td><input type="checkbox" id="' + cert.id + '" name="' + cert.name + '"></td>';

                        content += '  <td>' + cert.name + '</td>';

                        content += '  <td>';

                        content += '          <button type="button"  onclick="window.location.href=\'${pageContext.request.contextPath}/cert/toUpdate.htm?id=' + cert.id + '\'" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>';

                        content += '          <button type="button" class="btn btn-danger btn-xs" onclick="deleteCert(' + cert.id + ',\'' + cert.name + '\')"><i class=" glyphicon glyphicon-remove"></i></button>';

                        content += '  </td>';

                        content += '</tr>';

                    });
                    $("tbody").html(content);

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
                    layer.msg(result.message, {time: 1000, icon: 5, shift: 6});
                }

            },
            error: function () {
                layer.msg("加载数据失败", {time: 1000, icon: 5, shift: 6});
            }
        });
    }

    $("#queryBtn").click(function () {
        var queryText = $("#queryText").val();
        jsonObj.queryText = queryText;
        queryPageUser(1);
    });

    function deleteCert(id, name) {

        layer.confirm("是否删除[" + name + "]用户？", {icon: 3, title: '提示'}, function (cindex) {
            layer.close(cindex);
            $.ajax({
                type: "POST",
                data: {
                    "id": id
                },
                url: "${pageContext.request.contextPath}/cert/doDelete.do",
                beforeSend: function () {
                    return true;
                },
                success: function (result) {
                    if (result.success) {
                        window.location.href = "${pageContext.request.contextPath}/cert/index.htm";
                    } else {
                        layer.msg("删除用户失败", {time: 1000, icon: 5, shift: 6});
                    }
                },
                error: function () {
                    layer.msg("删除失败", {time: 1000, icon: 5, shift: 6});
                }
            });

        }, function (cindex) {
            layer.close(cindex);
        });
    }


    $("#allCheckbox").click(function () {
        var checkedStatus = this.checked;
        $("tbody tr td input[type='checkbox']").prop("checked", checkedStatus);
    });

    $("#deleteBatchBtn").click(function () {

        var selectCheckbox = $("tbody tr td input:checked");

        if (selectCheckbox.length==0){
            layer.msg("至少选择一个用户进行删除！", {time: 1000, icon: 5, shift: 6});
            return false;
        }


        var dataObj={};
        $.each(selectCheckbox, function (i, n) {
            dataObj["certList["+i+"].id"]=n.id;
            dataObj["certList["+i+"].name"]=n.name;
        });




        layer.confirm("是否删除这些用户？", {icon: 3, title: '提示'}, function (cindex) {
            layer.close(cindex);
            $.ajax({
                type: "POST",
                data: dataObj,
                url: "${pageContext.request.contextPath}/cert/doDeleteCertBatch.do",
                beforeSend: function () {
                    return true;
                },
                success: function (result) {
                    if (result.success) {
                        window.location.href = "${pageContext.request.contextPath}/cert/index.htm";
                    } else {
                        layer.msg("删除用户失败", {time: 1000, icon: 5, shift: 6});
                    }
                },
                error: function () {
                    layer.msg("删除失败", {time: 1000, icon: 5, shift: 6});
                }
            });

        }, function (cindex) {
            layer.close(cindex);
        });


    });
</script>
</body>
</html>
