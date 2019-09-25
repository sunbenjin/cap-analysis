<#--Created by IntelliJ IDEA.
User: zxm
Date: 2017/12/20
Time: 10:00
To change this excel_template use File | Settings | File Templates.-->

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>检查详情</title>
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
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=YVgzgdpoyPLz2eoNQ1ilzgTNkICSKVGy"></script>
    <style>
       /* .layui-laydate{
            height: 50px;
        }*/
    </style>
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
       #allmap{height:500px;width:100%;}
    </style>

</head>

<body>
<div class="x-body">

    <div class="layui-form-item">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 10px;">

        </fieldset>
    </div>

    <form id="subform" class="layui-form layui-form-pane" name="itemForm"  method="post">
        <input type="hidden" id="id" name="id" value="${checkBook.id}"/>
        <div class="layui-form-item">

            <label for="checkDate" class="layui-form-label">
                <span class="x-red">*</span>
                检查时间：
            </label>
            <div class="layui-inline">
                <input id="checkDate" name="checkDate" style="height: 40px;;width:305%;" placeholder="yyyy-MM-dd" lay-verify="checkDate"
                       class="layui-input" value="${checkBook.checkDate?string('yyy-MM-dd')}"
                       autocomplete="off"></input>
            </div>
        </div>
        <#if '${checkBook.checkType}'=='1'>
            <div class="layui-form-item">

                <label for="checkProblemTypeText" class="layui-form-label">
                    <span class="x-red">*</span>
                    检查点位：
                </label>
                <div class="layui-inline">
                    <input id="checkProblemTypeText" name="checkProblemTypeText" style="height: 40px;width:305%;" lay-verify="checkProblemTypeText"
                           class="layui-input" style="resize: none" value="${checkBook.checkProblemTypeText}"></input>
                </div>
            </div>
            <div class="layui-form-item">

                <label for="checkProblemTypeText" class="layui-form-label">
                    <span class="x-red">*</span>
                    所在镇街：
                </label>
                <div class="layui-inline">
                    <input id="dutyAvenue" name="dutyAvenue" style="height: 40px;width:305%;" lay-verify="dutyAvenue"
                           class="layui-input" style="resize: none" value="${checkBook.dutyAvenue}"></input>
                </div>
            </div>
            <div class="layui-form-item">

            <label for="checkIndexText" class="layui-form-label">
                <span class="x-red">*</span>
                指标名称：
            </label>
            <div class="layui-inline">
                <input id="checkIndexText" name="checkIndexText" style="height: 40px;width:305%;" lay-verify="checkIndexText"
                       class="layui-input" style="resize: none" value="${checkBook.checkIndexText}"></input>
            </div>
        </div>
            <div class="layui-form-item">

                <label for="checkContentText" class="layui-form-label">
                    <span class="x-red">*</span>
                    考察内容：
                </label>
                <div class="layui-inline">
                    <input id="checkContentText" name="checkContentText" style="height: 40px;width:305%;" lay-verify="checkContentText"
                           class="layui-input" style="resize: none" value="${checkBook.checkContentText}"></input>
                </div>
            </div>
            <div class="layui-form-item">

                <label for="problemDescription" class="layui-form-label ">
                    <span class="x-red">*</span>
                   情况描述：
                </label>

                <div class="layui-inline">
                <textarea id="problemDescription" name="problemDescription" lay-verify="itemDescription"
                          class="layui-textarea" placeholder="20字以上，500字以内"
                          style="width:320%;">${checkBook.problemDescription}</textarea>
                </div>
            </div>
            <div class="layui-form-item">

                <label for="problemAddress" class="layui-form-label ">
                    <span class="x-red">*</span>
                    考察地点：
                </label>

                <div class="layui-inline">
                <textarea id="problemAddress" name="problemAddress" lay-verify="problemAddress"
                          class="layui-textarea" placeholder="20字以上，500字以内"
                          style="width:320%;">${checkBook.problemAddress}</textarea>
                </div>
            </div>
            <div class="layui-form-item">

                <label for="remark1" class="layui-form-label ">
                    <span class="x-red">*</span>
                    备注1：
                </label>

                <div class="layui-inline">
                <textarea id="remark1" name="remark1" lay-verify="remark1"
                          class="layui-textarea" placeholder="20字以上，500字以内"
                          style="width:320%;">${checkBook.remark1}</textarea>
                </div>
            </div>
            <div class="layui-form-item">

                <label for="remark2" class="layui-form-label ">
                    <span class="x-red">*</span>
                    备注2：
                </label>

                <div class="layui-inline">
                <textarea id="remark2" name="remark2" lay-verify="remark2"
                          class="layui-textarea" placeholder="20字以上，500字以内"
                          style="width:320%;">${checkBook.remark2}</textarea>
                </div>
            </div>
        </#if>

        <#if '${checkBook.checkType}'=='2'>
            <div class="layui-form-item">

                <label for="checkProblemTypeText" class="layui-form-label">
                    <span class="x-red">*</span>
                    问题类别：
                </label>
                <div class="layui-inline">
                    <input id="checkProblemTypeText" name="checkProblemTypeText" style="height: 40px;width:305%;" lay-verify="checkProblemTypeText"
                           class="layui-input" style="resize: none" value="${checkBook.checkProblemTypeText}"></input>
                </div>
            </div>
            <div class="layui-form-item">

                <label for="checkIndexText" class="layui-form-label">
                    <span class="x-red">*</span>
                    问题项：
                </label>
                <div class="layui-inline">
                    <input id="checkIndexText" name="checkIndexText" style="height: 40px;width:305%;" lay-verify="checkIndexText"
                           class="layui-input" style="resize: none" value="${checkBook.checkIndexText}"></input>
                </div>
            </div>
            <div class="layui-form-item">

                <label for="remarks" class="layui-form-label">
                    <span class="x-red">*</span>
                    问题说明：
                </label>
                <div class="layui-inline">
                    <input id="remarks" name="remarks" style="height: 40px;width:305%;" lay-verify="remarks"
                           class="layui-input" style="resize: none" value="${checkBook.remarks}"></input>
                </div>
            </div>
            <div class="layui-form-item">

                <label for="problemDescription" class="layui-form-label ">
                    <span class="x-red">*</span>
                    问题描述：
                </label>

                <div class="layui-inline">
                <textarea id="problemDescription" name="problemDescription" lay-verify="itemDescription"
                          class="layui-textarea" placeholder="20字以上，500字以内"
                          style="width:320%;">${checkBook.problemDescription}</textarea>
                </div>
            </div>
            <div class="layui-form-item">

                <label for="problemAddress" class="layui-form-label ">
                    <span class="x-red">*</span>
                    问题地点：
                </label>

                <div class="layui-inline">
                <textarea id="problemAddress" name="problemAddress" lay-verify="problemAddress"
                          class="layui-textarea" placeholder="20字以上，500字以内"
                          style="width:320%;">${checkBook.problemAddress}</textarea>
                </div>
            </div>
            <div class="layui-form-item">

                <label for="checkProblemTypeText" class="layui-form-label">
                    <span class="x-red">*</span>
                    所在镇街：
                </label>
                <div class="layui-inline">
                    <input id="dutyAvenue" name="dutyAvenue" style="height: 40px;width:305%;" lay-verify="dutyAvenue"
                           class="layui-input" style="resize: none" value="${checkBook.dutyAvenue}"></input>
                </div>
            </div>
              <div class="layui-form-item">

                <label for="remarks1" class="layui-form-label ">
                    <span class="x-red">*</span>
                    备注1：
                </label>

                <div class="layui-inline">
                <textarea id="remarks1" name="remarks1" lay-verify="remarks1"
                          class="layui-textarea" placeholder="20字以上，500字以内"
                          style="width:320%;">${checkBook.remarks1}</textarea>
                </div>
            </div>
            <div class="layui-form-item">

                <label for="remarks2" class="layui-form-label ">
                    <span class="x-red">*</span>
                    备注2：
                </label>

                <div class="layui-inline">
                <textarea id="remarks2" name="remarks2" lay-verify="remarks2"
                          class="layui-textarea" placeholder="20字以上，500字以内"
                          style="width:320%;">${checkBook.remarks2}</textarea>
                </div>
            </div>
        </#if>
        <#if '${checkBook.checkType}'=='3'>

            <div class="layui-form-item">

                <label for="problemDescription" class="layui-form-label ">
                    <span class="x-red">*</span>
                    问题描述：
                </label>

                <div class="layui-inline">
                <textarea id="problemDescription" name="problemDescription" lay-verify="itemDescription"
                          class="layui-textarea" placeholder="20字以上，500字以内"
                          style="width:320%;">${checkBook.problemDescription}</textarea>
                </div>
            </div>
            <div class="layui-form-item">

                <label for="problemAddress" class="layui-form-label ">
                    <span class="x-red">*</span>
                    问题地点：
                </label>

                <div class="layui-inline">
                <textarea id="problemAddress" name="problemAddress" lay-verify="problemAddress"
                          class="layui-textarea" placeholder="20字以上，500字以内"
                          style="width:320%;">${checkBook.problemAddress}</textarea>
                </div>
            </div>
            <div class="layui-form-item">

                <label for="checkProblemTypeText" class="layui-form-label">
                    <span class="x-red">*</span>
                    所在镇街：
                </label>
                <div class="layui-inline">
                    <input id="dutyAvenue" name="dutyAvenue" style="height: 40px;width:305%;" lay-verify="dutyAvenue"
                           class="layui-input" style="resize: none" value="${checkBook.dutyAvenue}"></input>
                </div>
            </div>

        </#if>
        <div class="layui-form-item">

            <label  class="layui-form-label">
                <span class="x-red">*</span>
                问题图片：
            </label>

        </div>
        <#--</div>-->
        <cloudFile projectName="check_book_pic" buisId="${checkBook.id}" fileUniqueCode="check_book_pic" filesDynCode="" tableName="check_book_pic" ></cloudFile>
        <div class="layui-form-item">

            <label  class="layui-form-label">
                <span class="x-red">*</span>
                地图坐标：
            </label>

        </div>
        <div id="allmap"></div>
        <input type="text" id="longitude" value="${checkBook.longitude}" style="display: none"/>
        <input type="text" id="latitude" value="${checkBook.latitude}" style="display: none"/>
        <#if '${detail}'=='false'>
            <#--<div style="width: 100%;height: 55px;background-color: white;border-top:1px solid #e6e6e6;
  position: fixed;bottom: 1px;margin-left:-20px;">
                <div class="layui-form-item" style="margin-right: 30px;margin-top: 8px;text-align: center">

                    <button class="layui-btn layui-btn-warm" lay-filter="user" lay-submit>
                        确认
                    </button>
                    <button class="layui-btn layui-btn-primary" id="close">
                        关闭
                    </button>
                </div>
            </div>-->
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
        </#if>
    </form>
</div>
<script type="text/javascript" src="${re.contextPath}/plugin/file/uploadCommon.js"></script>
<script type="text/javascript" src="${re.contextPath}/plugin/file/mess.js"></script>
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
        var map = new BMap.Map("allmap");
        map.centerAndZoom(new BMap.Point(116.331398,39.897445),18);
        map.enableScrollWheelZoom(false);
        map.disableDragging();     //禁止拖拽
        map.disableScrollWheelZoom();//禁止缩放
        theLocation();
        // 用经纬度设置地图中心点
        function theLocation(){
            var longitude = document.getElementById("longitude").value;
            var latitude = document.getElementById("latitude").value;
           // alert(longitude,latitude)
            if(longitude != "" && latitude != ""){
                map.clearOverlays();
                var new_point = new BMap.Point(longitude,latitude);
                var marker = new BMap.Marker(new_point);  // 创建标注
                map.addOverlay(marker);              // 将标注添加到地图中
                map.panTo(new_point);
            }
        }
        var point = new BMap.Point('${checkBook.longitude}','${checkBook.latitude}');
        map.centerAndZoom(point,18);
        var uploadCommon = new UploadCommon();
        //$("#dynCode").val(uploadCommon.getDynCode());
        var arr = ['check_book_pic'];
        uploadCommon.setToken("${fileServer}/token/getToken", "123456", arr, "1");
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
            postAjaxre('updateItemDetail', data.field, 'itemRequirementList');
            return false;
        });
    });
</script>


</body>

</html>
