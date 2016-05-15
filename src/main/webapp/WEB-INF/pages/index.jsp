<%--
  Created by IntelliJ IDEA.
  User: root
  Date: 2016/3/7
  Time: 21:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>monitor</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="<c:url value='/resources/site/css/site.css'/>" rel="stylesheet">
    <link href="<c:url value='/resources/bootstrap/css/bootstrap.min.css'/>" rel="stylesheet">
    <link href="<c:url value='/resources/bootstrap/css/bootstrap-theme.min.css'/>" rel="stylesheet">

    <script src="<c:url value='/resources/jquery/jquery-2.1.4.min.js'/>"></script>
    <script src="<c:url value='/resources/bootstrap/js/bootstrap.min.js'/> "></script>

</head>
<body>

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Project name</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">Dashboard</a></li>
                <li><a href="#">Settings</a></li>
                <li><a href="#">Profile</a></li>
                <li><a href="#">Help</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <ul class="nav nav-sidebar">
                <li class="active"><a href="#">Overview <span class="sr-only">(current)</span></a></li>
                <li><a href="#">Reports</a></li>
                <li><a href="#">Analytics</a></li>
                <li><a href="#">Export</a></li>
            </ul>
            <ul class="nav nav-sidebar">
                <li><a href="">Nav item</a></li>
                <li><a href="">Nav item again</a></li>
                <li><a href="">One more nav</a></li>
                <li><a href="">Another nav item</a></li>
                <li><a href="">More navigation</a></li>
            </ul>
            <ul class="nav nav-sidebar">
                <li><a href="">Nav item again</a></li>
                <li><a href="">One more nav</a></li>
                <li><a href="">Another nav item</a></li>
            </ul>
        </div>

        <div class="col-sm-9 col-md-10  main">
            <h1 class="page-header">Dashboard</h1>
            <div class="row placeholders">
                <!-- dashboard -->
            </div>

            <h2 class="sub-header">Normal Schedule Job</h2>
            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>任务名称</th>
                        <th>任务状态</th>
                        <th>锁定</th>
                        <th>并行任务数</th>
                        <th>任务控制</th>
                        <th>开始时间</th>
                        <th>结束时间</th>
                        <th>作业耗时</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody id="nm_job">

                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<script>
    /*
     Cache elements
     */
    var jobPanel;

    function fetchJobInfo(jobType, callback) {
        $.ajax({
            url: '<c:url value="/job"/>',
            type: 'post',
            data: {'jobType': jobType},
            success: function (data) {
                console.log(data);
                callback(data);
            },
            error: function (e) {
                console.log(e);
            }
        });
    }

    function layoutJobInfo(data) {
        jobPanel.empty();
        if (!data || data.length == 0) {
            jobPanel.html('暂无数据');
        } else {
            var tr = $('<tr></tr>');
            for (var i = 0; i < data.length; i++) {
                var entity = data[i];
                tr.append($('<td>').html(entity.jobName))
                        .append($('<td>').html(entity.jobStatus))
                        .append($('<td>').html(entity.jobLock))
                        .append($('<td>').html(entity.tasksNum))
                        .append($('<td>').html(entity.runFlag))
                        .append($('<td>').html(entity.lastStartTime))
                        .append($('<td>').html(entity.lastEndTime))
                        .append($('<td>').html(entity.cost))
                        .append($('<td>').append(buildOpDiv(entity)));
            }
            jobPanel.append(tr);
        }
    }

    function buildOpDiv(entity) {
        var div = $('<div>').attr({'class': "btn-group btn-group-xs", 'role': "group"});
        var lockBt = $('<button>').attr({
            'class': 'btn btn-primary',
            'onclick': 'releaseLock("' + entity.jobName + '")'
        }).text('解锁');
        var runBt = $('<button>').attr({
            'class': 'btn btn-default',
            'onclick': 'jobControl("' + entity.jobName + '", "' + entity.runFlag + '")'
        });
        if (entity.runFlag)
            runBt.text('恢复');
        else
            runBt.text('挂起');
        div.append(lockBt);
        div.append(runBt);
        return div;
    }

    function releaseLock(jobName) {
        $.ajax({
            type: 'post',
            url: '<c:url value="/release"/>',
            data: {'id': jobName},
            success: function (data) {
                console.log("release lock success.")
            },
            error: function (e) {
                console.log(e);
            }
        });
    }
    function jobControl(jobName, runFlag) {
        console.log(jobName, runFlag);
        console.log((!runFlag ? 1 : 0))
        $.ajax({
            type: 'post',
            url: '<c:url value="/hangup"/>',
            data: {'id': jobName, 'flag': (!runFlag ? 1 : 0)},
            success: function (data) {
                console.log(data);
            },
            error: function (e) {
                console.log(e);
            }
        });
    }

    $(function () {

        jobPanel = $('#nm_job');

        setInterval(function () {
            fetchJobInfo('NORMAL', layoutJobInfo);
        }, 2000);
    })
</script>
</body>
</html>
