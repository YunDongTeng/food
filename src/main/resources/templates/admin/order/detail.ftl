<html>

<#include "../common/header.ftl">

<body>

</body>
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

                        </tbody>

                    </table>
                </div>
            </div>





        </div>
    </div>
</div>

</html>