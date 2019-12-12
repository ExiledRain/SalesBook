<#import "parts/common.ftlh" as p>
<#import "parts/login.ftlh" as l>

<@p.page>
    Add new user
    <#if message??>
        ${message}
    </#if>
    <@l.login "/registration"/>
</@p.page>