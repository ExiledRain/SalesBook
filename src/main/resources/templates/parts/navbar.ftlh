<#include "security.ftlh">
<nav class="navbar navbar-expand-lg navbar-light bg-light mt-2">
    <#--    <a class="navbar-brand" href="/">SaleBook</a>-->
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-targer="#navbarSupportedContent">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="/">Home<span class="sr-only"></span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/main">Sales<span class="sr-only"></span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/images">Images<span class="sr-only"></span></a>
            </li>
            <#if isAdmin>
                <li class="nav-item">
                    <a class="nav-link" href="/user">UserList<span class="sr-only"></span></a>
                </li>>
            </#if>
        </ul>
        <div class="navbar-text">${name}</div>
    </div>
</nav>