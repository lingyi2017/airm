/**
 * 查询相关
 *
 * Created by lxy on 18-07-02.
 */

$(function () {

    //随着输入条件加载添加
    var searchText = "";

    function getResult() {
        var select_char = $.trim($('#select_by').val());
        searchText = select_char;
        if (select_char === "") {
            $('.input_clear').css("display", "none");
            $('#select_result').children().remove();
            $('#select_content_result').children().remove();
            $('#select_result').removeClass('add_select_result');
            return;
        }
        $('.input_clear').css("display", "block");
        $('#select_content_result').children().remove();
        $('#select_result').children().remove();   //清空select_result里面的查询所有内容
        var s = searchText.toLowerCase();
        $.ajax({
            type: "GET",
            url: restUrl + "device/list",
            data: {
                keyword: s
            },
            dataType: "json",
            success: function (datas) {
                $('#select_result').children().remove();   //清空select_result里面的查询所有内容
                var data = $.grep(datas, function (item) {
                    for (var key in item) {
                        if ((typeof item[key] === 'string' ||
                            typeof item[key] === 'number')) {
                            return true;
                        }
                    }
                    return false;
                });
                var html = '';
                var data = datas.content;
                var a = data.length;
                if (a == 0) {
                    html += '<li class="select_result_li"><span>没有搜索到相关内容</span></li>';
                    $('#select_result').append(html);
                    $('#select_result').addClass('add_select_result');
                    return;
                }
                //对模糊查询进行限制
                if (a <= 10) {
                    $.each(data, function (commentIndex, comment) {
                        html += '<li class="select_result_li select_action select_result_li_hover"><span name=' + comment['deviceId'] + '>' + comment['name'] + '</span></li>';
                    });
                } else {
                    var newdata = [];
                    for (var i = 0; i < 10; i++) {
                        newdata.push(data[i]);
                    }
                    $.each(newdata, function (commentIndex, comment) {
                        html += '<li class="select_result_li select_action select_result_li_hover"><span name=' + comment['deviceId'] + '>' + comment['name'] + '</span></li>';
                    });
                }
                $('#select_result').append(html);
                $('#select_result').addClass('add_select_result');
            }
        });
    }

    var timeoutId = 0;
    $('#select_by').off('keyup').on('keyup', function (event) {
        if (!$(this).hasClass("focus")) {
            $(this).addClass("focus");
        }
        clearTimeout(timeoutId); // doesn't matter if it's 0
        timeoutId = setTimeout($.proxy(getResult), 100, event); // 100ms
    });
    $('#select_by').off('keydown').on('keydown', function (event) {
        if (!$(this).hasClass("focus")) {
            $(this).addClass("focus");
        }
    });
    //清除input框内容
    $(".input_clear").mouseover(function () {
        $(".alert_info").animate({
            "left": "323.5px",
            "top": "95px"
        }, 0.0001);
        $(".alert_info").css("display", "block");
    }).mouseleave(function () {
        $(".alert_info").css("display", "none");
    });
    $(".input_clear").click(function () {
        $(".content_result").css("display", "none");
        $('#select_by').val('');
        $('.input_clear').css("display", "none");
        $('#select_result').children().remove();   //清空select_result里面的查询所有内容
        $('#select_result').removeClass('add_select_result');

        map.clearOverlays();  // 删除地图覆盖物
    });
    //获取相关设备或区域函数
    function get_relevantResult(id) {
        ws.onclose();  // 关闭WEB SOCKET
        map.clearOverlays();  // 删除地图覆盖物
        $.ajax({
            type: "GET",
            url: restUrl + "device?deviceId=" + id,
            dataType: "json",
            success: function (datas) {
                if (datas) {
                    map.setZoom(14);  // 先修改地图缩放等级
                    var data = datas.content;
                    setTimeout(function () {
                        if (undefined == data.lon || undefined == data.lat) {
                            return;
                        }
                        addDeviceMarker(data);  // 添加设备标注
                        map.panTo(new BMap.Point(data.lon, data.lat));  // 定位
                    }, 10);
                }
            }
        });
    }

    //动态绑定
    $('#select_result').on('click', '.select_action', function () {
        var char = $(this).text();
        var id = $(this).children().attr("name");
        $('#select_by').val(char);
        $('#select_result').children().remove();
        $('#select_result').removeClass('add_select_result');
        get_relevantResult(id);
    });

    //键盘事件
    $(document).keydown(function (event) {
        var e = event || window.event || arguments.callee.caller.arguments[0];
        var limit_i = $('.select_action').length;
        if (e.keyCode != 40 && e.keyCode != 38 && e.keyCode != 13) {
            $('#select_by').off('keyup').on('keyup', function (event) {
                clearTimeout(timeoutId); // doesn't matter if it's 0
                timeoutId = setTimeout($.proxy(getResult), 100, event); // 100ms
                i_count = 0;
            });
        } else {
            if (0 <= i_count <= limit_i) {
                function key_action() {
                    if (e.keyCode == 40) { // 按 向下箭头
                        $('#select_by').removeClass("focus");
                        $('#select_by').off('keyup');
                        $('.select_action').removeClass("hover");
                        if (i_count < limit_i) {
                            $('.select_result').find("li").eq(i_count).addClass("hover");
                            var char = $('.select_result').find("li").eq(i_count).text();
                            $('#select_by').val(char);
                            i_count++;
                        } else {
                            i_count = 0;
                            $('.select_result').find("li").eq(i_count).addClass("hover");
                            var char = $('.select_result').find("li").eq(i_count).text();
                            $('#select_by').val(char);
                            i_count++;
                        }
                    }
                    if (e.keyCode == 38) { // 按  向上箭头
                        $('#select_by').removeClass("focus");
                        $('#select_by').off('keyup');
                        if (i_count >= 1) {
                            i_count--;
                            $('.select_action').removeClass("hover");
                            $('.select_result').find("li").eq(i_count - 1).addClass("hover");
                            var char = $('.select_result').find("li").eq(i_count - 1).text();
                            $('#select_by').val(char);
                        }
                        if (i_count == 0) {
                            i_count = limit_i;
                            $('.select_action').removeClass("hover");
                            $('.select_result').find("li").eq(i_count - 1).addClass("hover");
                            var char = $('.select_result').find("li").eq(i_count - 1).text();
                            $('#select_by').val(char);
                        }
                    }
                    if (e.keyCode == 13) { // enter 键
                        if ($('#select_by').hasClass("focus") && i_count == 0) {
                            $('#select_by').off('keyup');
                            clearTimeout(timeoutId); // doesn't matter if it's 0
                            timeoutId = setTimeout($.proxy(getResult), 100, event); // 100ms
                            var len = $('#select_result').find('.select_action').length;
                            if (len > 0) {
                                clearTimeout(timeoutId);
                                var id = $('#select_result').find('.select_action').eq(0).find("span").attr("name");
                                $('#select_result').html("");
                                setTimeout($.proxy(get_relevantResult(id)), 100, event);
                            }
                        } else {
                            $('#select_by').off('keyup');
                            if (1 <= i_count && i_count <= limit_i) {
                                var num = i_count - 1;
                                var name = $('.select_result').find("li").eq(num).text();
                                var id = $('.select_result').find("li").eq(num).find('span').attr('name');
                                $('#select_by').val(name);
                                $('#select_result').children().remove();
                                $('#select_result').removeClass('add_select_result');
                                get_relevantResult(id);
                                if (i_count == limit_i) {
                                    i_count++;
                                }
                            }
                        }
                    }
                }

                key_action();
            }
            if (i_count > limit_i) {
                i_count = 0;
            }
        }
    });

});