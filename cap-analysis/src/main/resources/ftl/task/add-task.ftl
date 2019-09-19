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


<style>
  .layui-laydate{
     height: 50px;
  }
</style>



</header>

<body>


  <form id="subform" class="layui-form layui-form-pane" name="itemForm" action="" method="post">
    <input type="hidden" id="id" name="id" />
    <input type="hidden" id="itemRequirementId" name="itemRequirementId" value="${itemRequirementId}" />
    <div class="layui-form-item" pane>

      <label for="itemTitle" class="layui-form-label" style="text-align: center">

         任务名称：
      </label>
      <div class="layui-input-block">
        <input id="taskName" name="taskName"  lay-verify="taskName" placeholder="任务名称"
               class="layui-input"></input>
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
      <label for="taskPersons" class="layui-form-label">
        执行人员：
      </label>
      <div class="layui-input-block" id="taskPersons" >

      </div>
    </div>
    <div class="layui-form-item" pane>
      <label for="taskStartTime" class="layui-form-label">
        开始时间：
      </label>
      <div class="layui-input-block">
        <input id="taskStartTime" name="taskStartTime"  placeholder="yyyy-MM-dd" lay-verify="taskStartTime"
               class="layui-input"
               autocomplete="off"></input>
      </div>
    </div>
    <div class="layui-form-item" pane>
      <label for="taskEndTime" class="layui-form-label">
        结束时间：
      </label>
      <div class="layui-input-block">
        <input id="taskEndTime" name="taskEndTime"  placeholder="yyyy-MM-dd"
               class="layui-input"
               autocomplete="off"></input>
      </div>
    </div>
    <div class="layui-form-item" pane>
      <label for="remarks" class="layui-form-label ">
        任务备注：
      </label>
      <div class="layui-input-block">
                <textarea id="remarks" name="remarks"
                          class="layui-textarea" placeholder="20字以上,500字以内"
                          ></textarea>
      </div>
    </div>
        <div class="layui-form-item" style="text-align: center" >
          <div class="layui-input-block">
          <button class="layui-btn" lay-filter="user" lay-submit>
            确认
          </button>
          <button class="layui-btn" id="close">
            关闭
          </button>
        </div>
        </div>

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
      postAjaxre('saveTask', data.field, 'itemTaskList');
      return false;
    });
    $("#addInputIndex").click(function () {
      layer.open({
        title: '个性指标'
        ,content: '<input type="text" name="手动输入" placeholder="手动填写" class="layui-input"/>' //这里content是一个普通的String
        ,btn:['确认','取消']

      });
      return false;
    });
    $("#addSelectIndex").click(function () {
      var html = "<select class='layui-select' style='width:100%'><option value=''>请选择</option><option value='1'>个性指标1</option><option value='2'>个性指标2</option><option value='3'>个性指标3</option><option value='4'>个性指标4</option><option value='5'>个性指标5</option><option value='6'>个性指标6</option></select>"
      layer.open({
        title: '个性指标'
        ,content: html//这里content是一个普通的String
        ,btn:['确认','取消']
      });
      return false;
    });
    $('#close').click(function () {
      var index = parent.layer.getFrameIndex(window.name);
      parent.layer.close(index);
      return false;
    });
  });

</script>
<script type="text/html" id="taskPersonsTpl">
  {{#layui.each(d.list,function(index,item){ }}

  <input type="checkbox" name="taskPersons" title="{{item.username}}" value="{{item.id}}">

  {{# }); }}
  {{# if(d.list.length==0){ }}
  无数据
  {{# }}}
</script>
<script>
  layui.use('laytpl',function(){
    var laytpl = layui.laytpl;
    getTaskPerson(laytpl);

  });
  function getTaskPerson(laytpl){
    $.ajax({
      url:"/user/listUsers",
      dataType:"json",
      async: false,
      success:function(res){
        //console.log(res);
        var data = {};
        var list = [];
        data.msg = res.msg;
        if(res.object.length>0){
          for(var i=0; i<res.object.length; i++){
            var array = res.object[i];
            list.push(array);
          }
        }
        data.list = list;
        // var data = {'title':'获取消息成功',"list":[{'id':'1','name':'张三'},{'id':'2','name':'李四'},{'id':'3','name':'王五'},{'id':'4','name':'张三4'},{'id':'5','name':'张三5'},{'id':'6','name':'张三6'},{'id':'7','name':'张三7'},{'id':'8','name':'张三8'}]};
        var getTpl = document.getElementById('taskPersonsTpl').innerHTML;
        var view = document.getElementById('taskPersons');
        laytpl(getTpl).render(data,function(html){
          view.innerHTML = html;
          var taskPersons = '${itemTask.taskPersons}';
          console.log(taskPersons);
          var checkArray = document.getElementsByName('taskPersons');
          console.log(checkArray);
          checkArray.forEach(function(item){
            if(taskPersons.indexOf(item.defaultValue)>=0){
              item.checked=true;
            }
          })
        });
      },
      error:function(e){
        layer.msg("服务器异常！");
      }
    })
  }
</script>
<script type="text/html" id="taskTypeTpl">
  <option value=""></option>
  {{#layui.each(d.list,function(index,item){ }}


  <option  value="{{item.value}}"  >{{item.label}}</option>

  {{# }); }}
  {{# if(d.list.length==0){ }}
  无数据
  {{# }}}
</script>
<script>
  layui.use('laytpl',function(){
    var laytpl = layui.laytpl;
    getTaskDict(laytpl);
    //console.log(data);
    //var data = {'title':'获取消息成功',"list":[{'value':'1','name':'创城检查'},{'value':'2','name':'环境检查'},{'value':'3','name':'自由检查'}]};

  });
  function getTaskDict(laytpl){

    $.ajax({
      url:"/dict/getDict",
      data:{
        "type":"task_type"
      },
      dataType:"json",
      type:"get",
      async:false,
      success:function(res){
        // console.log(res);
        if(res.code=='1'){
          //let  array = res.object;
          // var data =  getTaskDict();
          var data = {};
          var list = [];
          data.msg = res.msg;
          if(res.object.length>0){
            for(var i=0; i<res.object.length; i++){
              var array = res.object[i];
              list.push(array);
            }
          }
          data.list = list;
          //console.log(data);
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
        }
      },
      error:function (e) {
        layer.msg("服务器异常！");
      }

    });
    // return list;
  }
</script>
</html>
