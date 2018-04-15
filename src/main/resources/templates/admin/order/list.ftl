<html>

<#include "../common/header.ftl">

<script>

    function showCancelModal(orderId) {
        $('#cancelModal').modal('show');
        $("#orderId").val(orderId);
    }

    function cancelOrder() {
        console.log($("#orderId").val());
        $.ajax({
            url: '/sell/seller/order/cancel',
            type: 'POST',
            dataType: 'json',
            data:{'orderId':$("#orderId").val()},
            success: function (data) {

                if (data.code == '200') {
                    $('#cancelModal').modal('hide');
                    $("#resultModal").modal("show");
                }else{
                    alert(data.msg);
                }
            }
        })
    }
    
    function toOrderList() {
        location.href="/sell/seller/order/list?page=1&size=5"
    }
</script>
<body>
<div id="wrapper" class="toggled">
    <#include "../common/left.ftl">
    <div id="page-content-wrapper">

        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-hover table-bordered" style="font-size: 14px">
                        <thead>
                        <tr>
                            <th>订单编号</th>
                            <th>姓名</th>
                            <th>手机号</th>
                            <th> 地址</th>
                            <th>金额（元）</th>
                            <th>订单状态</th>
                            <th>支付状态</th>
                            <th> 创建时间</th>
                            <th colspan="2">操作</th>
                        </tr>
                        </thead>
                        <tbody>

                        <#list orderPageList.content as orderDTO>
                        <tr>
                            <td>${orderDTO.orderId}</td>
                            <td>${orderDTO.buyerName}</td>
                            <td>${orderDTO.buyerPhone}</td>
                            <td>${orderDTO.buyerAddress}</td>
                            <td>${orderDTO.orderAmount}</td>
                            <td>${orderDTO.orderStatusEnum.msg}</td>
                            <td>${orderDTO.payStatusEnum.msg}</td>
                            <td>${orderDTO.createTime}</td>
                            <td><a href="/sell/seller/order/detail/${orderDTO.orderId}">详情</a></td>
                                    <#if orderDTO.orderStatus lt 3>
                                        <td><a id="modal-972745" href="javascript:void(0)"
                                               onclick="showCancelModal('${orderDTO.orderId}')" role="button"
                                               class="btn" data-toggle="modal">取消</a></td>
                                    <#else>
                                        <td></td>
                                    </#if>
                        </tr>
                        </#list>

                        </tbody>

                    </table>

                    <#if orderPageList.getTotalPages() gt 0>
                        <ul class="pagination" style="float: right">
                            <li>
                                <a href="/sell/seller/order/list?page=1&size=5">首页</a>
                            </li>
                            <#if currentPage lte 1>
                                <li class="disabled">
                                    <a href="/sell/seller/order/list?page=${currentPage-1}&size=5">上一页</a>
                                </li>
                            <#else>
                                <li>
                                    <a href="/sell/seller/order/list?page=${currentPage-1}&size=5">上一页</a>
                                </li>
                            </#if>
                            <#list 1..orderPageList.getTotalPages() as index>
                                <#if currentPage == index>
                                    <li class="disabled">
                                        <a href="/sell/seller/order/list?page=${index}&size=5">${index}</a>
                                    </li>
                                <#else>
                                    <li>
                                        <a href="/sell/seller/order/list?page=${index}&size=5">${index}</a>
                                    </li>
                                </#if>
                            </#list>
                            <#if currentPage gte orderPageList.getTotalPages()>
                                <li class="disabled">
                                    <a href="/sell/seller/order/list?page=${currentPage+1}&size=5">下一页</a>
                                </li>
                            <#else>
                                <li>
                                    <a href="/sell/seller/order/list?page=${currentPage+1}&size=5">下一页</a>
                                </li>
                            </#if>
                            <li>
                                <a href="/sell/seller/order/list?page=${orderPageList.getTotalPages()}&size=5">末页</a>
                            </li>
                        </ul>
                    </#if>
                </div>
            </div>


            <div class="modal fade" id="cancelModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title" id="myModalLabel">
                                取消订单
                            </h4>
                        </div>
                        <div class="modal-body">
                            确认取消该订单？
                        </div>
                        <input type="hidden" id="orderId"/>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                            <button type="button" class="btn btn-primary" onclick="cancelOrder()">确认</button>
                        </div>
                    </div>

                </div>
            </div>


            <div class="modal fade" id="resultModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-body">
                           取消成功
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal" onclick="toOrderList()">确定</button>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>


</body>
</html>
