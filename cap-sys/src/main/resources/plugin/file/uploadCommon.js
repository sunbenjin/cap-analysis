window.UploadCommon = (function () {

    var filesDynCode = uuid(32,16);

    function UploadCommon() {

    }

    UploadCommon.prototype.loadFile = (url, eleArr, type, ele) => {
        const token = "";
        $.each(eleArr, function (index, value) {
            var params = $("cloudFile[projectName="+value+"]");
            CloudFileInput(value, token, type, ele, url,params);
            //alert($("#MessengerDemo").attr("src"));
            var frm = document.getElementById(value);
            $(frm).ready(function () {
                var messenger = new Messenger('parent', params.attr("projectName"));
                var childFrame = document.getElementById(value);
                if (messenger != undefined) {
                    messenger.addTarget(childFrame.contentWindow, value);
                    var sendMessage = function(v){
                        messenger.targets[value].send(v);
                    };
                    var listen = function(){
                        messenger.listen(function (msg) {
                            //debugger;
                            var cf = document.getElementById(value);
                            var heightMs =parseInt(msg)+20;
                            //alert(heightMs);
                            //alert(heightMs+"px");
                            cf.height = heightMs+"px";
                        });
                    };
                    listen();
                }

            })
        })
    };

    /**
     * 请求获取token并设置显示上传组件的iframe
     * @param url
     * @param code
     * @param eleArr
     * @param type   调用方式。1.从页面元素中获取对应属性值    2.传入对应属性值
     */
    UploadCommon.prototype.setToken = function (url, code, eleArr, type, ele) {
        console.log(url);
        $.ajax({
            url:url,
            data:{userId:code},
            type:'post',
            success:function(data){
                //console.log(data);
                var token = data.data;
                //callback(data);
                $.each(eleArr, function (index, value) {
                    var params = $("cloudFile[projectName="+value+"]");
                    CloudFileInput(value, token, type, ele, url,params);
                    //alert($("#MessengerDemo").attr("src"));
                    var frm = document.getElementById(value);
                    $(frm).ready(function () {
                        var messenger = new Messenger('parent', params.attr("projectName"));
                        var childFrame = document.getElementById(value);
                        if (messenger != undefined) {
                            messenger.addTarget(childFrame.contentWindow, value);
                            var sendMessage = function(v){
                                messenger.targets[value].send(v);
                            }
                            var listen = function(){
                                messenger.listen(function (msg) {
                                    //debugger;
                                    var cf = document.getElementById(value);
                                    var heightMs =parseInt(msg)+20;
                                    //alert(heightMs);
                                    //alert(heightMs+"px");
                                    cf.height = heightMs+"px";
                                });
                            }
                            listen();
                        }

                    })

                })

            }
        });
    }


    UploadCommon.prototype.getDynCode = function () {
        //return uuid(32,16);
        return filesDynCode;
    }

    UploadCommon.prototype.getUUID = function () {
        return uuid(32,16);
    }


    UploadCommon.prototype.saveFile = function (url, param) {
        $.ajax({
            url: url,
            data: param,
            dataType: "json",
            type: 'post',
            success: function (data) {
                //alert("保存成功");
            }
        });
    }



    function CloudFileInput (id, token, type, ele, url,parameters) {
        let address = url.substring(0, url.indexOf("/token"));
        console.log(address);
        //let serverAddress = "http://localhost/fileInput/index?";
        let serverAddress = `${address}/fileInput/index?`;
        let parameter = parameters;
        if(parameter==undefined){
            alert("没有该对象");
        }
        let projectName = "";
        let filesId = "";
        let buisId = "";
        let fileUniqueCode = "";
        let tableName = "";
        let fileReadOnly = "";
        if (type == "1") {
            projectName = parameter.attr("projectName");
            filesId = parameter.attr("projectName");
            buisId = parameter.attr("buisId");
            fileUniqueCode = parameter.attr("fileUniqueCode");
            tableName = parameter.attr("tableName");
            fileReadOnly = parameter.attr("fileReadOnly");
        } else {
            projectName = id;
            filesId = id;
            buisId = ele.buisId;
            fileUniqueCode = ele.fileUniqueCode;
            tableName = ele.tableName;
            fileReadOnly = ele.fileReadOnly;
        }

        function getSrcPath(){
            let queryString = [];
            queryString = queryString.concat(getParameter("projectName",projectName))
                .concat(getParameter("filesId",projectName))
                .concat(getParameter("buisId",buisId))
                .concat(getParameter("fileUniqueCode",fileUniqueCode))
                .concat(getParameter("tableName",tableName))
                .concat(getParameter("fileReadOnly",fileReadOnly))
                .concat(getParameter("filesDynCode",filesDynCode))
                .concat(getParameter("accessToken", token))
                .join("&");
            return serverAddress+queryString;
        }

        var addDom = function(id) {
            var html ="<iframe id=\""+filesId+"\" src="+getSrcPath()+" scrolling=\"no\" frameborder=\"0\"  width=\"100%\"></iframe>"
            //$("cloudFile[projectName=" + id + "]").html("sssss");
            $("cloudFile[projectName="+id+"]").append(html);
            /*$.ajax({
                type: "GET",
                url: getSrcPath(),
                contentType: "application/json",
                beforeSend: function(xhr, settings){
                    xhr.setRequestHeader("accessToken", token);
                },
                success: function(data){
                    $("#"+id).attr('src',"data:text/html;charset=utf-8," + escape(data))
                }
            });*/
        }
        return addDom(id);
    }


    function getParameter(param,value){
        if (value==''||value==null||value==undefined){
            return '';
        }
        return param+"="+value;
    }
    
    function uuid(len, radix) {
        var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
        var uuid = [],
            i;
        radix = radix || chars.length;

        if (len) {
            // Compact form
            for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random() * radix];
        } else {
            // rfc4122, version 4 form
            var r;
            // rfc4122 requires these characters
            uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
            uuid[14] = '4';
            // Fill in random data.  At i==19 set the high bits of clock sequence as
            // per rfc4122, sec. 4.1.5
            for (i = 0; i < 36; i++) {
                if (!uuid[i]) {
                    r = 0 | Math.random() * 16;
                    uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
                }
            }
        }
        return uuid.join('');
    }
    
    

    return UploadCommon;
})();