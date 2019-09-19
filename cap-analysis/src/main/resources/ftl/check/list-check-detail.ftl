<#--Created by IntelliJ IDEA.
User: zxm
Date: 2017/12/20
Time: 10:00
To change this excel_template use File | Settings | File Templates.-->

<!DOCTYPE html>
<html>

<header>
    <meta charset="UTF-8">
    <title>编辑项目需求</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8"/>
    <link rel="stylesheet" href="${re.contextPath}/plugin/layui-v2.5.4/layui/css/layui.css">

</header>

<body>


<form id="subform" class="layui-form layui-form-pane" name="itemForm" action="" method="post">
    <input type="hidden" id="id" name="id" value="${itemTask.id}">
    <input type="hidden" id="itemRequirementId" name="itemRequirementId" value="${itemTask.itemRequirementId}" />
    <div class="layui-form-item" pane>

        <label for="itemTitle" class="layui-form-label" style="text-align: center">

            任务名称：
        </label>
        <div class="layui-input-block">
            <input id="taskName" name="taskName"  lay-verify="taskName" placeholder="任务名称"
                   class="layui-input" value="${itemTask.taskName}"></input>
        </div>
    </div>
    <div class="layui-form-item" pane>
        <label for="taskType" class="layui-form-label">
            任务类型：
        </label>
        <div class="layui-input-block">
            <select name="taskType" lay-verify="required" id="taskType" lay-search>

            </select>
        </div>

    </div>

    <div class="layui-form-item" pane>
        <label for="taskStartTime" class="layui-form-label">
            开始时间：
        </label>
        <div class="layui-input-block">
            <input id="taskStartTime" name="taskStartTime"  placeholder="yyyy-MM-dd" lay-verify="taskStartTime"
                   class="layui-input"
                   autocomplete="off" value="${itemTask.taskStartTime?string('yyy-MM-dd')}"></input>
        </div>
    </div>
    <div class="layui-form-item" pane>
        <label for="taskEndTime" class="layui-form-label">
            结束时间：
        </label>
        <div class="layui-input-block">
            <input id="taskEndTime" name="taskEndTime"  placeholder="yyyy-MM-dd"
                   class="layui-input"
                   autocomplete="off" value="${itemTask.taskEndTime?string('yyy-MM-dd')}"></input>
        </div>
    </div>
    <div class="layui-form-item" pane>
        <label for="remarks" class="layui-form-label ">
            任务备注：
        </label>
        <div class="layui-input-block">
                <textarea id="remarks" name="remarks"
                          class="layui-textarea" placeholder="20字以上,500字以内"
                >${itemTask.remarks}</textarea>
        </div>

    </div>
    <#if '${detail}'=='false'>
        <div class="layui-form-item" style="text-align: center">
            <div class="layui-input-block">
                <button class="layui-btn" lay-filter="user" lay-submit>
                    确认
                </button>
                <button class="layui-btn" id="close">
                    关闭
                </button>
            </div>
        </div>
    </#if>
</form>



</body>
<script type="text/javascript" src="${re.contextPath}/plugin/jquery/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="${re.contextPath}/plugin/layui-v2.5.4/layui/layui.js"></script>
<script type="text/javascript" src="${re.contextPath}/plugin/tools/tool.js"></script>
<script type="text/javascript" src="${re.contextPath}/plugin/tools/update-setting.js"></script>

<script>

    layui.use(['form', 'layer','laydate','laytpl','table'], function(){
        var form = layui.form;

        var laydate = layui.laydate;

        var a = laydate.render({
            elem: '#taskStartTime',
            trigger:'click',
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
        var b = laydate.render({
            elem: '#taskEndTime',
            trigger:'click'
        });
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
        $('#close').click(function () {
            var index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
            return false;
        });

        //监听提交
        form.on('submit(user)', function(data){
            var taskPersonsChecked = [];

            $("input:checkbox[name=taskPersons]:checked").each(
                function (index) {
                    taskPersonsChecked.push($(this).val());
                }
            );
            data.field.taskPersons=taskPersonsChecked.toString();
            //console.log(data.field);
            postAjaxre('editTaskDetail', data.field, 'itemTaskList');

            return false;
        });
    });

</script>

<script type="text/html" id="taskTypeTpl">
    <option value=""></option>
    {{#layui.each(d.list,function(index,item){ }}


    <option  value="{{item.value}}"  >{{item.name}}</option>

    {{# }); }}
    {{# if(d.list.length==0){ }}
    无数据
    {{# }}}
</script>
<script>
    layui.use('laytpl',function(){
        var laytpl = layui.laytpl;
        var data = {'title':'获取消息成功',"list":[{'value':'1','name':'创城检查'},{'value':'2','name':'环境检查'},{'value':'3','name':'自由检查'}]};
        var getTpl = document.getElementById('taskTypeTpl').innerHTML;
        var view = document.getElementById('taskType');
        laytpl(getTpl).render(data,function(html){
            view.innerHTML = html;
            var taskTypeValue = '${itemTask.taskType}';
            var selectArray = document.getElementById('taskType');
            var options = selectArray.options;
            /*console.log(selectArray);*/
            for(var i=0; i<options.length; i++){
                if(options[i].value==taskTypeValue){
                    options[i].selected=true;
                }
            }

        });
    });
</script>
</html>
