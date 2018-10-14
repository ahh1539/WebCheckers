<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <meta http-equiv="refresh" content="10">
    <title>${title} | Web Checkers</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
  <div class="page">
  
    <h1>Web Checkers</h1>
    
    <div class="navigation">
      <a href="/">my home</a>
      <a href="/signin">Sign in here!</a>
    </div>
    
    <div class="body">
      <p>Welcome to the world of online Checkers.</p>
       <#if players??>
          <h3>Current Players</h3>

          <#if thingy??>
            <p>${thingy}</p>
          </#if>
          <#list players as player>
            <p>${player.username}</p>
          </#list>
       </#if>


    </div>
    
  </div>
</body>
</html>
