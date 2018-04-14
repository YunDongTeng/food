<html>

<#include "../common/header.ftl">
<script>

    function showCancelModal(orderId) {
        $('#cancelModal').modal('show');
        $("#orderId").val(orderId);
    }

    function showFinishModal(orderId) {
        $('#finishModal').modal('show');
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
                    location.href="/sell/seller/order/list?page=1&size=5"
                }else{
                    alert(data.msg);
                }
            }
        })
    }
    function finishOrder() {
        console.log($("#orderId").val());
        $.ajax({
            url: '/sell/seller/order/finish',
            type: 'POST',
            dataType: 'json',
            data:{'orderId':$("#orderId").val()},
            success: function (data) {
                if (data.code == '200') {
                    $('#cancelModal').modal('hide');
                    location.href="/sell/seller/order/list?page=1&size=5"
                }else{
                    alert(data.msg);
                }
            }
        })
    }
</script>
<body>

</body>
<div id="wrapper" class="toggled">
    <#include "../common/left.ftl">
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-6 column">
                    <table class="table table-hover table-bordered" style="font-size: 14px">
                        <thead>
                            <tr>
                                <th>订单编号</th>
                                <th>订单金额</th>
                                <th>姓名</th>
                                <th>手机号</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>${order.orderId}</td>
                                <td>${order.orderAmount}</td>
                                <td>${order.buyerName}</td>
                                <td>${order.buyerPhone}</td>
                            </tr>
                            <#--<input type="hidden" id="detailOrderId" value="${order.orderId}"/>-->
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="col-md-12 column">
                <table class="table table-hover table-bordered" style="font-size: 14px">
                    <thead>
                    <tr>
                        <th>商品编号</th>
                        <th>商品名称</th>
                        <th>单价</th>
                        <th>购买数量</th>
                    </tr>
                    </thead>
                    <tbody>

                    <#list order.detailList as detail>
                    <tr>
                        <td>${detail.productId}</td>
                        <td>${detail.productName}</td>
                        <td>${detail.productPrice}</td>
                        <td>${detail.productAmount}</td>
                    </tr>
                    </#list>

                    </tbody>
                </table>
            </div>

            <button class="btn btn-primary" onclick="showFinishModal('${order.orderId}')">完结订单</button> &nbsp;&nbsp;   <button class="btn btn-danger" onclick="showCancelModal('${order.orderId}')">取消订单</button>




            <div class="modal fade" id="finishModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title" id="myModalLabel">
                                完结订单
                            </h4>
                        </div>
                        <div class="modal-body">
                            确认完结该订单？
                        </div>
                        <input type="hidden" id="orderId"/>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                            <button type="button" class="btn btn-primary" onclick="finishOrder()">确认</button>
                        </div>
                    </div>

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

        </div>
    </div>



</div>

</html>