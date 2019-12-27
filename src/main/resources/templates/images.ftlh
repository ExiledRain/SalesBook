<#import "parts/common.ftlh" as c>
<@c.page>
<#--    <#assign alps = "http://localhost:8080/alps/all">-->
    <div class="row row-cols-1 row-cols-3">
        <#list alps as alp>
            <div class="col-4 mb-4">
                <div class="card text-white bg-dark">
                    <img src="img${alp.getPath()}" class="card-img-top" alt="${alp.getPath()}">
                    <div class="card-body">
                        <p class="card-text small">Short name: ${alp.getName()}</p>
                    </div>
                </div>
            </div>
        </#list>
    </div>
</@c.page>