<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>字典列表</title>
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
        类型：
        <div class="layui-inline">
            <input class="layui-input" height="20px" id="type" autocomplete="off">
        </div>
        <button class="select-on layui-btn layui-btn-sm" data-type="select"><i class="layui-icon"></i>
        </button>
          <button class="layui-btn layui-btn-sm icon-position-button" id="refresh" style="float: right;"
                data-type="reload">
            <i class="layui-icon">ဂ</i>
        </button>
    </div>

</div>
<div class="layui-col-md12" style="height:40px;margin-top:3px;">
    <div class="layui-btn-group">
    <@shiro.hasPermission name="dict:add">
    <button class="layui-btn layui-btn-normal" data-type="add">
        <i class="layui-icon">&#xe608;</i>新增
    </button>
    </@shiro.hasPermission>
    </div>
</div>
<table id="dictList" class="layui-hide" lay-filter="user"></table>
<script type="text/html" id="toolBar">
    <@shiro.hasPermission name="dict:view">
  <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
    </@shiro.hasPermission>
    <@shiro.hasPermission name="dict:add">
        <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="addDict">添加</a>
    </@shiro.hasPermission>
<@shiro.hasPermission name="dict:edit">
  <a class="layui-btn layui-btn-xs  layui-btn-normal" lay-event="edit">编辑</a>
</@shiro.hasPermission>
  <@shiro.hasPermission name="dict:delete">
      <a class="layui-btn layui-btn-xs  layui-btn-normal" lay-event="del">删除</a>
  </@shiro.hasPermission>
</script>
<script>



    document.onkeydown = function (e) { // 回车提交表单
        var theEvent = window.event || e;
        var code = theEvent.keyCode || theEvent.which;
        if (code == 13) {
            $(".select .select-on").click();
        }
    }
    layui.use('table', function () {
        var table = layui.table;

        //方法级渲染

            table.render({
                id: 'dictList',
                elem: '#dictList'
                , url: 'dictList'
                , cols: [[
                    {checkbox: true, fixed: true, width: '5%'}
                    , {field: 'value', title: '键值', width: '15%', sort: true}
                    , {field: 'label', title: '标签', width: '15%',sort: true}
                    , {field: 'type', title: '类型', width: '15%',sort: true}
                    , {field: 'description', title: '描述', width: '15%'}
                    , {field: 'sort', title: '排序', width: '15%'}
                    , {field: 'remarks', title: '操作', width: '20%', toolbar: "#toolBar"}
                ]]
                , page: true


                
                ,  height: 'full-83'
            });




        var $ = layui.$, active = {
            select: function () {
                var type = $('#type').val();
               
                table.reload('dictList', {
                    where: {
                        type: type
                    }
                });
            },
            reload:function(){
                $('#type').val('');

                table.reload('dictList', {
                    where: {
                        type: null
                    }
                });
            },
            add: function () {
                add('添加字典', 'addDict', 800, 600);
            },
            update: function () {
                var checkStatus = table.checkStatus('dictList')
                        , data = checkStatus.data;
                if (data.length != 1) {
                    layer.msg('请选择一行编辑', {icon: 5});
                    return false;
                }
                update('编辑字典', 'editDict?id=' + data[0].id, 800, 600);
            },
            detail: function () {
                var checkStatus = table.checkStatus('dictList')
                        , data = checkStatus.data;

                if (data.length != 1) {
                    layer.msg('请选择一行查看', {icon: 5});
                    return false;
                }
                detail('查看字典', 'updateDict?id=' + data[0].id, 800, 600);
            }
        };

        //监听表格复选框选择
        table.on('checkbox(dictList)', function (obj) {
            //console.log(obj)
            console.log(obj);
        });
        //监听工具条
        table.on('tool(user)', function (obj) {

            var data = obj.data;
          //  alert(obj.event);
            if (obj.event === 'detail') {

                detail('查看字典', 'updateDict?id=' + data.id, 800, 600);
            } else if (obj.event === 'del') {
                layer.confirm('确定删除?', function(){
                    del(data.id);
                });
            } else if (obj.event === 'edit') {
                update('编辑字典', 'updateDict?id=' + data.id, 800, 600);
            }else if(obj.event==='addDict'){
                addDict("添加字典","addDict?type="+data.type+"&description="+data.description+"&remarks="+data.remarks,800,600);
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
            url: "deleteDict",
            type: "post",
            data: {id: id},
            success: function (d) {
                if(d.msg){
                    layer.msg(d.msg,{icon:6,offset: 'rb',area:['120px','80px'],anim:2});
                    layui.table.reload('dictList');
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
        layer.open({
            id: 'dict-add',
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
    function addDict(title, url, w, h) {
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
            id: 'dict-add',
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
    function  detailTaskList(url) {
        window.location.href=url;
    }
</script>
</body>

</html>
