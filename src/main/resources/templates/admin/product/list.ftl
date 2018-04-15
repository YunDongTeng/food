<html>

<#include "../common/header.ftl">

<script>

    function showUpdateState(state,productId) {

        if(state==0){
            $("#myModalLabel").text("下架商品");
            $("#modal-body").text("确定下架该商品？");
        }else{
            $("#myModalLabel").text("上架商品");
            $("#modal-body").text("确定上架该商品？")
        }

        $("#updateState").modal('show');

        $("#productId").val(productId);
        $("#productState").val(state);
    }

    function updateState() {

        var productId = $("#productId").val();
        var state = $("#productState").val();

        $.ajax({
            url:'/sell/seller/product/updateState',
            data:{'state':state,'id':productId},
            type:'post',
            dataType:'json',
            success:function (data) {

                console.log(data);

                if(data.code==200){

                    $("#resultModal").modal('show');
                    $("#updateState").modal('hide');

                    $("#result-body").text(state==0?'下架成功':'上架成功');
                }
            }
        })
    }

    function toProductList() {
        location.href="/sell/seller/product/list";
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
                            <th>商品id</th>
                            <th>名称</th>
                            <th>图片</th>
                            <th>单价</th>
                            <th>库存</th>
                            <th>介绍</th>
                            <th>分类</th>
                            <th>创建时间</th>
                            <th>修改时间</th>
                            <th colspan="2">操作</th>
                        </tr>
                        </thead>
                        <tbody>

                        <#list productList.content as product>
                        <tr>
                            <td>${product.productId}</td>
                            <td>${product.productName}</td>
                            <td><img src="${product.productImg}" width="50px;" height="50px;"/> </td>
                            <td>${product.productPrice}</td>
                            <td>${product.productStock}</td>
                            <td>${product.productDesc}</td>
                            <td>${product.categoryName}</td>
                            <td width=100px">${product.createTime}</td>
                            <td width="100px">${product.updateTime}</td>
                            <td><a href="/sell/seller/product/detail/${product.productId}">修改</a></td>
                            <td>
                                <#if product.productStatus == 0>
                                    <a href="javascript:void(0)" onclick="showUpdateState('${product.productStatus}','${product.productId}')">下架</a>
                                    <#else>
                                     <a href="javascript:void(0)" onclick="showUpdateState('${product.productStatus}','${product.productId}')">上架</a>
                                </#if>
                            </td>
                        </tr>
                        </#list>

                        </tbody>

                    </table>

                <#if productList.getTotalPages() gt 0>
                    <ul class="pagination" style="float: right">
                        <li>
                            <a href="/sell/seller/product/list?page=1&size=5">首页</a>
                        </li>
                        <#if currentPage lte 1>
                            <li class="disabled">
                                <a href="/sell/seller/product/list?page=${currentPage-1}&size=5">上一页</a>
                            </li>
                        <#else>
                            <li>
                                <a href="/sell/seller/product/list?page=${currentPage-1}&size=5">上一页</a>
                            </li>
                        </#if>
                        <#list 1..productList.getTotalPages() as index>
                            <#if currentPage == index>
                                <li class="disabled">
                                    <a href="/sell/seller/product/list?page=${index}&size=5">${index}</a>
                                </li>
                            <#else>
                                <li>
                                    <a href="/sell/seller/product/list?page=${index}&size=5">${index}</a>
                                </li>
                            </#if>
                        </#list>
                        <#if currentPage gte productList.getTotalPages()>
                            <li class="disabled">
                                <a href="/sell/seller/product/list?page=${currentPage+1}&size=5">下一页</a>
                            </li>
                        <#else>
                            <li>
                                <a href="/sell/seller/product/list?page=${currentPage+1}&size=5">下一页</a>
                            </li>
                        </#if>
                        <li>
                            <a href="/sell/seller/product/list?page=${productList.getTotalPages()}&size=5">末页</a>
                        </li>
                    </ul>
                </#if>
                </div>


                <div class="modal fade" id="updateState" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                <h4 class="modal-title" id="myModalLabel">

                                </h4>
                            </div>
                            <div class="modal-body" id="modal-body">
                            </div>
                            <input type="hidden" id="productId"/>
                            <input type="hidden" id="productState"/>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                                <button type="button" class="btn btn-primary" onclick="updateState()">确认</button>
                            </div>
                        </div>

                    </div>
                </div>
            </div>

            <div class="modal fade" id="resultModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-body" id="result-body">
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal" onclick="toProductList()">确定</button>
                        </div>
                    </div>
                </div>
            </div>




        </div>
    </div>
</div>


</body>
</html>
