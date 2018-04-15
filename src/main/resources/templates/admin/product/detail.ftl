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
            data: {'orderId': $("#orderId").val()},
            success: function (data) {

                if (data.code == '200') {
                    $('#cancelModal').modal('hide');
                    location.href = "/sell/seller/order/list?page=1&size=5"
                } else {
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
            data: {'orderId': $("#orderId").val()},
            success: function (data) {
                if (data.code == '200') {
                    $('#cancelModal').modal('hide');
                    location.href = "/sell/seller/order/list?page=1&size=5"
                } else {
                    alert(data.msg);
                }
            }
        })
    }
</script>
<body>

<div id="wrapper" class="toggled">
<#include "../common/left.ftl">
    <div id="page-content-wrapper">
        <div class="container-fluid" style="margin-left:30px;">
            <div class="row clearfix">

                    <form class="form-horizontal">
                        <div class="form-group">
                            <label class="col-xs-1" for="productName">商品名称</label>
                            <div class="col-xs-6"><input type="text" class="form-control" id="productName" placeholder="商品名称"></div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-1"for="productPrice">商品单价</label>
                            <div class="col-xs-2"><input type="text" class="form-control" id="productPrice" placeholder="商品单价"></div>
                        </div>
                        <div class="form-group">
                            <label class="col-xs-1" for="productStock">商品库存</label>
                            <div class="col-xs-2"><input type="text" class="form-control" id="productStock" placeholder="商品库存"></div>
                        </div>
                    </form>
                </div>






        <#--<div class="modal fade" id="finishModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
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
        </div>-->

        </div>
    </div>


</div>
</body>
</html>