<#import "parts/common.ftlh" as p>

<@p.page>
    <div>
        <form method="post" action="search">
            <input type="text" name="ename" placeholder="Name you looking for?"/>
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
            <button type="submit" class="btn btn-outline-info btn-sm center">Add</button>
        </form>
    </div>
    <div>
        <div class="table-responsive-sm">
            <table class="table table-bordered table-hover table-striped">
                    <tr class="thead-light">
                        <th class="col-sm">Id</th>
                        <th class="col">Name</th>
                        <th class="col">Group</th>
                        <th class="col">Email</th>
                        <th class="col">Total cost</th>
                        <th class="col-bg-1">Description</th>
                        <th class="col">Status</th>
                    </tr>
                <#list sales as sale>
                        <tr>
                            <th class="col">${sale.id}</th>
                            <th class="col">${sale.name}</th>
                            <th class="col">${sale.cat}</th>
                            <th class="col">${sale.email}</th>
                            <th class="col">${sale.totalCost} Eur</th>
                            <th class="col-sm-1">${sale.description}</th>
                            <th class="col">${sale.isDone()?c}</th>
                        </tr>
                </#list>
            </table>
        </div>
    </div>
</@p.page>