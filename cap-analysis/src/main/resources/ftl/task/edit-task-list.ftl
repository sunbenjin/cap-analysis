<#--Created by IntelliJ IDEA.
User: zxm
Date: 2017/12/20
Time: 10:00
To change this excel_template use File | Settings | File Templates.-->

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>编辑项目需求</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8"/>
    <link rel="stylesheet" href="${re.contextPath}/plugin/layui/css/layui.css">
    <link rel="stylesheet" href="${re.contextPath}/plugin/lenos/main.css">
    <script type="text/javascript" src="${re.contextPath}/plugin/jquery/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${re.contextPath}/plugin/layui/layui.all.js"
            charset="utf-8"></script>
    <script type="text/javascript" src="${re.contextPath}/plugin/tools/tool.js"></script>
    <script type="text/javascript" src="${re.contextPath}/plugin/tools/update-setting.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            var flag = '${detail}';
            if (flag == 'true') {
                var input = document.getElementsByTagName("input");
                var textArea = document.getElementsByTagName('textarea');

                for (var i = 0; i < input.length; i++) {
                    input[i].setAttribute('disabled', true);
                }
                for (var i = 0; i < textArea.length; i++) {
                    textArea[i].setAttribute('disabled', true);
                }
            }

        });
    </script>
    <style>

        label {
            width: 200px !important;

        }

    </style>

</head>

<body>
<div class="x-body">

    <div class="layui-form-item">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">
            <legend style="font-size:16px;">项目需求信息</legend>
        </fieldset>
    </div>

        <input type="hidden" id="id" name="id" value="${itemRequirement.id}"/>
        <div class="layui-form-item">

            <label for="itemTitle" class="layui-form-label">
                <span class="x-red">*</span>
                项目需求名称：
            </label>
            <div class="layui-inline">
                <input id="itemTitle" name="itemTitle" style="height: 40px;width:270%;" lay-verify="itemTitle"
                       class="layui-input" style="resize: none" value="${itemRequirement.itemTitle}"></input>
            </div>
        </div>
        <div class="layui-form-item">

            <label for="beginDate" class="layui-form-label">
                <span class="x-red">*</span>
                开始时间：
            </label>

            <div class="layui-inline">
                <input id="beginDate" name="beginDate" style="height: 40px;;width:270%;" placeholder="yyyy-MM-dd" lay-verify="beginDate"
                       class="layui-input" value="${itemRequirement.beginDate?string('yyy-MM-dd')}"
                       autocomplete="off"></input>
            </div>
        </div>
        <div class="layui-form-item">

            <label for="endDate" class="layui-form-label">
                <span class="x-red">*</span>
                结束时间：
            </label>
            <div class="layui-inline">
                <input id="endDate" name="endDate" style="height: 40px;width:270%;" placeholder="yyyy-MM-dd"
                       class="layui-input" value="${itemRequirement.endDate?string('yyy-MM-dd')}"
                       autocomplete="off"></input>
            </div>
        </div>
        <div class="layui-form-item">

            <label for="itemDescription" class="layui-form-label ">
                <span class="x-red">*</span>
                项目需求描述：
            </label>

            <div class="layui-inline">
                <textarea id="itemDescription" name="itemDescription" lay-verify="itemDescription"
                          class="layui-textarea" placeholder="20字以上，500字以内"
                          style="width:270%;">${itemRequirement.itemDescription}</textarea>
            </div>

        </div>

        <div class="layui-form-item">
            <label for="itemTaskList" class="layui-form-label ">
                <span class="x-red">*</span>
                任务列表：
            </label>
            <button class="layui-btn layui-btn-normal" data-type="add" id="addTask">
                <i class="layui-icon">&#xe61f;</i>新增
            </button>
        </div>
        <#--</div>-->
    <div class="layui-form-item">
        <label for="itemTaskList" class="layui-form-label ">
            <span class="x-red"></span>

        </label>
        <table id="itemTaskList" class="layui-hide" lay-filter="user"></table>
        <script type="text/html" id="toolBar">
            <@shiro.hasPermission name="task:view">
                <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="task:edit">
                <a class="layui-btn layui-btn-xs  layui-btn-normal" lay-event="edit">编辑</a>
            </@shiro.hasPermission>
            <@shiro.hasPermission name="task:delete">
                <a class="layui-btn layui-btn-xs  layui-btn-normal" lay-event="del">删除</a>
            </@shiro.hasPermission>

        </script>
    </div>
</div>

<script>
    layui.laytpl.toDateString = function (d, format) {
        var date = new Date(d || new Date())
            , ymd = [
            this.digit(date.getFullYear(), 4)
            , this.digit(date.getMonth() + 1)
            , this.digit(date.getDate())
        ]
            , hms = [
            this.digit(date.getHours())
            , this.digit(date.getMinutes())
            , this.digit(date.getSeconds())
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
    //数字前置补零
    layui.laytpl.changeValueToText= function(value, dictType){
        if(value=='1'){
            return '创城检查';
        }else if(value=='2'){
            return '环境检查';
        }else if(value=='3'){
            return '自由检查';
        }
    };
    layui.use(['form', 'layer','laydate','laytpl','table'], function (){
        $ = layui.jquery;
        var form = layui.form, layer = layui.layer, layDate = layui.laydate,table = layui.table;
        var a = layDate.render({
            elem: '#beginDate',
            done: function (value, date, endDate) {
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

   table.render({
       elem:'#itemTaskList'
       ,height:200
       ,url:'taskList?itemRequirementId=${itemRequirement.id}'
       ,page:false
        ,cols:[[
          {field: 'taskName', title: '任务名称' , width: '10%', sort: true}

            ,{field: 'taskType', title: '任务类型',width: '15%',templet: '<div>{{ layui.laytpl.changeValueToText(d.taskType,"task_type") }}</div>', sort: true}
            ,{field: 'taskStartTime', title: '开始时间', width: '15%',templet: '<div>{{ layui.laytpl.toDateString(d.taskStartTime,"yyyy-MM-dd") }}</div>', sort: true}
            ,{field: 'taskEndTime', title: '结束时间', width: '15%',templet: '<div>{{ layui.laytpl.toDateString(d.taskEndTime,"yyyy-MM-dd") }}</div>', sort: true}
            ,{field: 'remarks', title: '备注', width: '10%'}
           , {field: 'remark', title: '操作', width: '20%', toolbar: "#toolBar"}

        ]]
            ,  height: 'full-83'
   });
       var $ = layui.$, active = {

                add: function () {
                    add('添加任务', 'addTask?itemRequirementId=${itemRequirement.id}', 800, 600);
                }
            };
        var b = layDate.render({
            elem: '#endDate'
        });
        //自定义验证规则
        form.verify({
            itemDescription: function (value) {
                if (value.trim() == "") {
                    return "项目描述不能为空";
                }
            },
        beginDate: function (value) {
            if (value.trim() == "") {
                return "开始时间不能为空";
            }
        },
            itemTitle: function (value) {
                if (value.trim() == "") {
                    return "项目标题不能为空";
                }
            },
            endDate: function (value) {
            if (value.trim() == "") {
                return "结束日期不能为空";
            }
        }
        });

        $('#close').click(function () {
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);

        });
        $('#addTask').on('click', function () {
            var type = $(this).data('type');

            active[type] ? active[type].call(this) : '';
        });
        $('.select .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
        //监听工具条
        table.on('tool(user)', function (obj) {

            var data = obj.data;
            if (obj.event === 'detail') {

                detail('查看任务', 'editTaskDetail?id=' + data.id, 800, 600);
            } else if (obj.event === 'del') {
                layer.confirm('确定删除?', function(){
                    del(data.id);
                });
            } else if (obj.event === 'edit') {

                update('编辑任务', 'editTaskDetail?id=' + data.id, 800, 600);
            }
        });
    });
    function del(id) {
        $.ajax({
            url: "deleteTask",
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
        layer.open({
            id: 'item-update',
            type: 2,
            area: [w + 'px', h + 'px'],
            fix: false,
            maxmin: true,
            shadeClose: false,
            shade: 0.4,
            title: title,
            content: url + '&detail=false'
        });

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
      //  alert(url);
        layer.open({
            id: 'task-add',
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
</script>


</body>

</html>
