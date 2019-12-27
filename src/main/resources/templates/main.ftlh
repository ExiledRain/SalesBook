<#import "parts/common.ftlh" as p>
<#import "parts/login.ftlh" as l>
<@p.page>
<#--    <@l.logout />-->
    <div>
        <form method="post" action="search">
            <input type="text" name="ename" placeholder="Name you looking for?" value="${ename!}"/>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit" class="btn btn-outline-info btn-sm center">Look</button>
        </form>
    </div>
    <div>
        <form method="post">
            <input type="text" name="name" placeholder="Name:"/>
            <input type="email" name="email" placeholder="Email:"/>
            <input type="text" name="cat" placeholder="Category?"/>
            <input type="text" name="totalCost" placeholder="Total cost:"/>
            <input type="text" name="description" placeholder="Description:"/>
            <input type="checkbox" name="isDone" placeholder="Ready?"/>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit" class="btn btn-outline-info btn-sm center">Add</button>
        </form>
    </div>
    <div class="container row col-md-6">
        <div class="table-responsive-sm">
            <table class="table table-bordered table-hover table-striped">
                <colgroup>
                    <col class="col-sm-1">
                    <col class="col-sm-2">
                    <col class="col-sm-2">
                    <col class="col-sm-2">
                    <col class="col-sm-1">
                    <col class="col-sm-3">
                    <col class="col-sm-1">
                </colgroup>
                <tr class="thead-light">
                        <th>Id</th>
                        <th>Name</th>
                        <th>Group</th>
                        <th>Email</th>
                        <th>Total cost</th>
                        <th>Description</th>
                        <th>Status</th>
                    </tr>
                <#list sales as sale>
                        <tr>
                            <th>${sale.id}</th>
                            <th>${sale.name}</th>
                            <th>${sale.cat}</th>
                            <th>${sale.email}</th>
                            <th>${sale.totalCost} Eur</th>
                            <th>${sale.description}</th>
                            <th>${sale.isDone()?c}</th>
                        </tr>
                </#list>
            </table>
        </div>
    </div>
</@p.page>