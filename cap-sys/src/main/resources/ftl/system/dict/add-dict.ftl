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

<#--  <style>
    .layui-laydate{
      height: 50px;
    }
  </style>-->

  <style>

  /*  label {
      width: 200px !important;

    }*/
  .layui-laydate{
    height: 50px;
  }
  </style>

</header>

<body>

  <form id="subform" class="layui-form layui-form-pane" name="itemForm" action="addDict" method="post">
    <input type="hidden" id="id" name="id" />
    <div  class="layui-form-item" pane>

      <label for="value" class="layui-form-label"  style="text-align: center">

        键值：
      </label>
      <div class="layui-input-block">
        <input id="value" name="value"  lay-verify="value"       class="layui-input" ></input>
      </div>
    </div>
    <div class="layui-form-item" pane>
      <label for="label" class="layui-form-label" style="text-align: center">
        标签：
      </label>
      <div class="layui-input-block">
        <input id="label" name="label"  lay-verify="label"      class="layui-input"  ></input>
      </div>
    </div>
    <div class="layui-form-item" pane>

      <label for="type" class="layui-form-label" style="text-align: center">
        类型：
      </label>
      <div class="layui-input-block">
        <input id="type" name="type"  lay-verify="type"      class="layui-input" value="${itemDict.type}" ></input>
      </div>
    </div>

    <div class="layui-form-item" pane>
      <label for="parentId" class="layui-form-label">
        父级：
      </label>
      <div class="layui-input-block">
        <select name="parentId"  id="parentId" lay-search>

        </select>
      </div>

    </div>
    <div class="layui-form-item" pane>

      <label for="description" class="layui-form-label" style="text-align: center">
        描述：
      </label>
      <div class="layui-input-block">
        <input id="description" name="description"  lay-verify="description"  value="${itemDict.description}"     class="layui-input"  ></input>
      </div>
    </div>
    <div class="layui-form-item" pane>
      <label for="sort" class="layui-form-label" style="text-align: center">
        排序：
      </label>
      <div class="layui-input-block">
        <input id="sort" name="sort"  lay-verify="sort"     class="layui-input"   autocomplete="off"></input>
      </div>
    </div>
    <div class="layui-form-item layui-form-text" pane>

      <label for="remarks" class="layui-form-label "  style="text-align: center">
       备注：
      </label>

      <div class="layui-input-block">
                <textarea id="remarks" name="remarks" lay-verify="remarks"
                          class="layui-textarea" placeholder="20字以上，500字以内"></textarea>
      </div>

    </div>


    <#--</div>-->
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


  layui.use(['form', 'layer','laydate'], function (){
    $ = layui.jquery;
    var form = layui.form, layer = layui.layer;
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
    //自定义验证规则
    form.verify({
      value: function (value) {
        if (value.trim() == "") {
          return "键值不能为空";
        }
      },
      lable: function (value) {
        if (value.trim() == "") {
          return "标签不能为空";
        }
      },
      type: function (value) {
        if (value.trim() == "") {
          return "类型不能为空";
        }
      },
      description: function (value) {
        if (value.trim() == "") {
          return "描述不能为空";
        }
      }
    });

    $('#close').click(function () {
      var index = parent.layer.getFrameIndex(window.name);
      parent.layer.close(index);
      return false;
    });
    //监听提交
    form.on('submit(user)', function (data) {
      postAjaxre('addDict', data.field, 'dictList');
      return false;
    });
  });
</script>
<script type="text/html" id="parentIdTpl">
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
        "type":"item_index"
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
          console.log(data);
          var getTpl = document.getElementById('parentIdTpl').innerHTML;
          var view = document.getElementById('parentId');
          laytpl(getTpl).render(data,function(html){
            view.innerHTML = html;
          //  var selectArray = document.getElementById('parentId');
           // console.log(selectArray);
              var parentIdValue = '${itemDict.parentId}';
             var selectArray = document.getElementById('parentId');
             var options = selectArray.options;
       // alert(options);
          for(var i=0; i<options.length; i++){
               if(options[i].value==parentIdValue){
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
