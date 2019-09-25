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
  <link rel="stylesheet" href="${re.contextPath}/plugin/layui-v2.5.4/layui/css/layui.css">
  <link rel="stylesheet" href="${re.contextPath}/plugin/lenos/main.css">
  <script type="text/javascript" src="${re.contextPath}/plugin/jquery/jquery-3.2.1.min.js"></script>
  <script type="text/javascript" src="${re.contextPath}/plugin/layui/layui.all.js"
          charset="utf-8"></script>
  <script type="text/javascript" src="${re.contextPath}/plugin/tools/tool.js"></script>
  <script type="text/javascript" src="${re.contextPath}/plugin/tools/update-setting.js"></script>
<#--  <style>
    .layui-laydate{
      height: 50px;
    }
  </style>-->
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

  /*  label {
      width: 200px !important;

    }*/
  .layui-laydate{
    height: 50px;
  }
  </style>

</head>

<body>

  <form id="subform" class="layui-form " name="itemForm" action="updateItemDetail" method="post">
    <input type="hidden" id="id" name="id" />
    <div for="itemTitle" class="layui-form-item">

      <label for="itemTitle" class="layui-form-label">

        项目名称：
      </label>
      <div class="layui-input-block">
        <input id="itemTitle" name="itemTitle"  lay-verify="itemTitle"
               class="layui-input" type="text" ></input>
      </div>
    </div>
    <div class="layui-form-item">

      <label for="beginDate" class="layui-form-label">

        开始时间：
      </label>

      <div class="layui-input-block">
        <input id="beginDate" name="beginDate"  placeholder="yyyy-MM-dd" lay-verify="beginDate"
               class="layui-input"
               autocomplete="off"></input>
      </div>
    </div>
    <div class="layui-form-item">

      <label for="endDate" class="layui-form-label">
        结束时间：
      </label>
      <div class="layui-input-block">
        <input id="endDate" name="endDate"  placeholder="yyyy-MM-dd"
               class="layui-input"
               autocomplete="off"></input>
      </div>
    </div>
    <div class="layui-form-item layui-form-text">

      <label for="itemDescription" class="layui-form-label ">
       需求描述：
      </label>

      <div class="layui-input-block">
                <textarea id="itemDescription" name="itemDescription" lay-verify="itemDescription"
                          class="layui-textarea" placeholder="20字以上，500字以内">${itemRequirement.itemDescription}</textarea>
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
  layui.use(['form', 'layer','laydate'], function (){
    $ = layui.jquery;
    var form = layui.form, layer = layui.layer, layDate = layui.laydate;
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
    //监听提交
    form.on('submit(user)', function (data) {
      postAjaxre('addItem', data.field, 'itemRequirementList');
      return false;
    });
  });
</script>


</body>

</html>
