<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>项目任务列表</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8"/>
    <link rel="stylesheet" href="${re.contextPath}/plugin/layui/css/layui.css">
    <link rel="stylesheet" href="${re.contextPath}/plugin/lenos/main.css">
    <script type="text/javascript" src="${re.contextPath}/plugin/jquery/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${re.contextPath}/plugin/layui/layui.all.js"
            charset="utf-8"></script>
</head>

<body>
<div class="lenos-search">
    <div class="select">
        项目需求名称：
        <div class="layui-inline">
            <input class="layui-input" height="20px" id="itemTitle" autocomplete="off">
        </div>
        开始时间
        <div class="layui-inline">
            <input class="layui-input"  placeholder="yyyy-MM-dd" height="20px" id="beginDate" autocomplete="off">
        </div>
        结束时间：
        <div class="layui-inline">
            <input class="layui-input"  placeholder="yyyy-MM-dd" height="20px" id="endDate" autocomplete="off">
        </div>
        <button class="select-on layui-btn layui-btn-sm" data-type="select"><i class="layui-icon"></i>
        </button>
        <button class="layui-btn layui-btn-sm icon-position-button" id="refresh" style="float: right;"
                data-type="reload">
            <i class="layui-icon">ဂ</i>
        </button>
    </div>

</div>

<table id="itemTaskList" class="layui-hide" lay-filter="user"></table>
<script type="text/html" id="toolBar">
    <@shiro.hasPermission name="task:view">
        <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看任务</a>
    </@shiro.hasPermission>
    <@shiro.hasPermission name="check:view">
        <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detailCheckList">查看检查</a>
    </@shiro.hasPermission>
<@shiro.hasPermission name="task:edit">
  <a class="layui-btn layui-btn-xs  layui-btn-normal" lay-event="edit">编辑</a>
</@shiro.hasPermission>
  <@shiro.hasPermission name="task:delete">
      <a class="layui-btn layui-btn-xs  layui-btn-normal" lay-event="del">删除</a>
  </@shiro.hasPermission>

</script>


<script>
    layui.laytpl.toDateString = function(d, format){
        var date = new Date(d || new Date())
                ,ymd = [
            this.digit(date.getFullYear(), 4)
            ,this.digit(date.getMonth() + 1)
            ,this.digit(date.getDate())
        ]
                ,hms = [
            this.digit(date.getHours())
            ,this.digit(date.getMinutes())
            ,this.digit(date.getSeconds())
        ];

        format = format || 'yyyy-MM-dd HH:mm:ss';

        return format.replace(/yyyy/g, ymd[0])
                .replace(/MM/g, ymd[1])
                .replace(/dd/g, ymd[2])
                .replace(/HH/g, hms[0])
                .replace(/mm/g, hms[1])
                .replace(/ss/g, hms[2]);
    };

    //数字前置补零
    layui.laytpl.digit = function(num, length, end){
        var str = '';
        num = String(num);
        length = length || 2;
        for(var i = num.length; i < length; i++){
            str += '0';
        }
        return num < Math.pow(10, length) ? str + (num|0) : num;
    };
    layui.laytpl.changeValueToText= function(value, dictType){
        let text = "";
        $.ajax({
            url:"/dict/getDict",
            dataType:'json',
            data:{
                "type":dictType,
               "value":value
            },
            async:false,
            type:'get',
            success:function (res) {
               if(res.code=='1'){
                   var obj = res.object;
                   //console.log(obj);
                   if(obj.length>0){
                       text = obj[0].label;

                   }else{
                       console.log(obj[0]);
                   }

               }else{
                   layer.msg("获取字典值失败");
               }
            },
            error:function (e) {
                layer.msg("服务器异常！");
            }
        });
      //  alert(text);
        return text;

    };
    document.onkeydown = function (e) { // 回车提交表单
        var theEvent = window.event || e;
        var code = theEvent.keyCode || theEvent.which;
        if (code == 13) {
            $(".select .select-on").click();
        }
    }
    layui.use('table', function () {
        var table = layui.table,laydate = layui.laydate;
        var a = laydate.render({
            elem: '#beginDate',
            done: function(value, date, endDate) {
                b.config.min = {
                    year: date.year,
                    month: date.month - 1,
                    date: date.date,
                    hours: date.hours,
                    minutes: date.minutes,
                    seconds: date.seconds
                }
            }
        });
        var b = laydate.render({
            elem: '#endDate'
        });
        //方法级渲染

            table.render({
                id: 'itemTaskList',
                elem: '#itemTaskList'
                , url: '/capItemTask/taskList?itemRequirementId=${itemRequirementId}'
                , cols: [[
                    {checkbox: true, fixed: true, width: '5%'}
                    , {field: 'taskName', title: '任务名称', width: '20%', sort: true}
                    , {field: 'taskStartTime', title: '开始时间', width: '15%',templet: '<div>{{ layui.laytpl.toDateString(d.taskStartTime,"yyyy-MM-dd") }}</div>',sort: true}
                    , {field: 'taskEndTime', title: '结束时间', width: '15%',templet: '<div>{{ layui.laytpl.toDateString(d.taskEndTime,"yyyy-MM-dd") }}</div>',sort: true}
                    , {field: 'taskType', title: '任务类型',templet:'<div>{{layui.laytpl.changeValueToText(d.taskType,"task_type")}}</div>', width: '25%',sort: true}
                    , {field: 'remark', title: '操作', width: '20%', toolbar: "#toolBar"}
                ]]
                , page: true
                ,  height: 'full-83'
            });




        var $ = layui.$, active = {
            select: function () {
                var itemTitle = $('#itemTitle').val();
                var beginDate = $("#beginDate").val();
                var endDate = $("#endDate").val();
                table.reload('itemTaskList', {
                    where: {
                        itemTitle: itemTitle,
                        beginDate: beginDate,
                        endDate: endDate
                    }
                });
            },
            reload:function(){
                $('#itemTitle').val('');
                $('#beginDate').val('');
                $("#endDate").val('');
                table.reload('itemTaskList', {
                    where: {
                        itemTitle: null,
                        beginDate: null,
                        endDate:null
                    }
                });
            },
           /* add: function () {
                add('添加项目需求', 'add', 800, 600);
            },*/
            update: function () {
                var checkStatus = table.checkStatus('itemTaskList')
                        , data = checkStatus.data;
                if (data.length != 1) {
                    layer.msg('请选择一行编辑', {icon: 5});
                    return false;
                }
                update('编辑角色', 'editItemTask?id=' + data[0].id, 800, 600);
            },
            detail: function () {
                var checkStatus = table.checkStatus('itemTaskList')
                        , data = checkStatus.data;

                if (data.length != 1) {
                    layer.msg('请选择一行查看', {icon: 5});
                    return false;
                }
                detail('查看需求信息', 'updateTaskDetail?id=' + data[0].id, 800, 600);
            }
        };

        //监听表格复选框选择
        table.on('checkbox(itemTaskList)', function (obj) {
            //console.log(obj)
            console.log(obj);
        });
        //监听工具条
        table.on('tool(user)', function (obj) {

            var data = obj.data;
            if (obj.event === 'detail') {

                detail('查看项目需求', 'updateTaskDetail?id=' + data.id, 800, 600);
            } else if (obj.event === 'del') {
                layer.confirm('确定删除?', function(){
                    del(data.id);
                });
            } else if (obj.event === 'edit') {
                update('编辑项目需求', 'updateTaskDetail?id=' + data.id, 800, 600);
            }else if(obj.event === 'detailCheckList'){
                detailCheckList("/check/showCheckList?itemTaskId="+data.id+"&taskType="+data.taskType);
            }
        });

        $('.layui-col-md12 .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
        $('.select .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

    });
    function del(id) {
        $.ajax({
            url: "deleteItemTask",
            type: "post",
            data: {id: id},
            success: function (d) {
                if(d.msg){
                    layer.msg(d.msg,{icon:6,offset: 'rb',area:['120px','80px'],anim:2});
                    layui.table.reload('itemTaskList');
                }else{
                    layer.msg(d.msg,{icon:5,offset: 'rb',area:['120px','80px'],anim:2});
                }
            }
        });
    }
    function detail(title, url, w, h) {

        var number = 1;
        if (title == null || title == '') {
            title = false;
        }
        ;
        if (url == null || url == '') {
            url = "/error/404";
        }
        ;
        if (w == null || w == '') {
            w = ($(window).width() * 0.9);
        }
        ;
        if (h == null || h == '') {
            h = ($(window).height() - 50);
        }
        ;
       layer.open({
            id: 'item-detail',
            type: 2,
            area: [w + 'px', h + 'px'],
            fix: false,
            maxmin: true,
            shadeClose: true,
            shade: 0.4,
            title: title,
            content: url + '&detail=true',
            // btn:['关闭']
        });
      // window.location.href=url+"&detail=true";

    }
    /**
     * 更新用户
     */
    function update(title, url, w, h) {
        if (title == null || title == '') {
            title = false;
        }
        if (url == null || url == '') {
            url = "/error/404";
        }
        if (w == null || w == '') {
            w = ($(window).width() * 0.9);
        }
        if (h == null || h == '') {
            h = ($(window).height() - 50);
        }
     /*   layer.open({
            id: 'item-update',
            type: 2,
            area: [w + 'px', h + 'px'],
            fix: false,
            maxmin: true,
            shadeClose: false,
            shade: 0.4,
            title: title,
            content: url + '&detail=false'
        });*/
        window.location.href=url+"&detail=false";

    }

    /*弹出层*/
    /*
     参数解释：
     title   标题
     url     请求的url
     id      需要操作的数据id
     w       弹出层宽度（缺省调默认值）
     h       弹出层高度（缺省调默认值）
     */
    function add(title, url, w, h) {
        if (title == null || title == '') {
            title = false;
        }
        ;
        if (url == null || url == '') {
            url = "/error/404";
        }
        ;
        if (w == null || w == '') {
            w = ($(window).width() * 0.9);
        }
        ;
        if (h == null || h == '') {
            h = ($(window).height() - 50);
        }
        ;
        layer.open({
            id: 'advice-add',
            type: 2,
            area: [w + 'px', h + 'px'],
            fix: false,
            maxmin: true,
            shadeClose: false,
            shade: 0.4,
            title: title,
            content: url
        });
    }
    function  detailCheckList(url) {
     //   alert(url);
        window.location.href = url;
    }
</script>
</body>

</html>
