<#macro login classpath>
    <div>
        <form class="form-group" action="${classpath}" method="post">
            <div><label> User Name: <input type="text" name="username"> </label></div>
            <div><label> Password <input type="password" name="password"> </label></div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
        <a href="/registration">Add new r</a>
    </div>
</#macro>

<#macro logout>
    <form method="post" action="/logout">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit">Sign out</button>
    </form>
</#macro>