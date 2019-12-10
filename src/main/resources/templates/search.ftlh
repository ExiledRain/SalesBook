<#import "parts/common.ftlh" as p>

<@p.page>
    <div>
        <div class="table-responsive-sm">
            <table class="table table-bordered table-hover table-striped">
                <tr class="thead-light">
                    <th>Id</th>
                    <th>Name</th>
                    <th>Group</th>
                    <th>Email</th>
                    <th>Total cost</th>
                    <th  style="width: 20px">Description</th>
                    <th>Status</th>
                </tr>
                <#list saleList as sale>
                    <tr>
                        <th>${sale.id}</th>
                        <th>${sale.name}</th>
                        <th>${sale.cat}</th>
                        <th>${sale.email}</th>
                        <th>${sale.totalCost} Eur</th>
                        <th style="width: 20px">${sale.description}</th>
                    </tr>
                </#list>
            </table>
        </div>
    </div>
</@p.page>