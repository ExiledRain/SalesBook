<#import "parts/common.ftlh" as c>
<@c.page>
<#--    <div>-->
<#--        <img src="img/TallinnTrophy/22bbb.jpg"/>-->
<#--        <img src="img/TallinnTrophy/wallpapers_bmw_6-series_2008_3_b.jpg"-->
<#--             alt="img/TallinnTrophy/wallpapers_bmw_6-series_2008_3_b.jpg" class="card"/>-->
<#--    </div>-->
<#--    <div class="row row-cols-1 row-cols-md-2">-->
<#--    <div class="row row-cols-1 row-cols-md-3">-->
    <div class="row row-cols-1 row-cols-3">
        <#list alps as alp>
                <div class="col-4 mb-4">
                    <div class="card text-white bg-dark">
                        <img src="${alp.getPath()}" class="card-img-top" alt="${alp.getPath()}">
                        <div class="card-body">
                            <p class="card-text small">Short name: ${alp.getName()}</p>
                        </div>
                    </div>
                </div>
        </#list>
    </div>
</@c.page>