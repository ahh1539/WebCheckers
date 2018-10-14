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
      <a href="/">Home</a>
      <a href="/signin">Sign In</a>
      <a href="/signout">Sign Out</a>
    </div>
    
    <div class="body">
      <p>Welcome to the world of online Checkers.</p>

      <#if errorMsg??>
        <p>${errorMsg}</p>
      </#if>

      <#if !currentPlayer??>
        <p>There are currently ${(players?size)!0} players signed in.</p>
      <#else>
        <h3>Current Players</h3>
          <#list players as player>
            <#if player.username != currentPlayer.username>
              <p><a href="/game">${player.username}</a></p>
            </#if>
          </#list>
      </#if>

    </div>
    
  </div>
</body>
</html>
