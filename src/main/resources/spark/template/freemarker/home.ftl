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
            <#if !currentPlayer??>
                <a href="/signin">Sign In</a>
            <#else>
            <a href="/signout">Sign Out</a>
            </#if>

    </div>
    
    <div class="body">
      <p>Welcome to the world of online Checkers.</p>
      <#if !currentPlayer??>
        <p>There are currently ${numPlayers!0} players signed in.</p>
      <#else>
        <h3>Current Players</h3>
          <#if errorMsg??>
            <p>${errorMsg}</p>
          </#if>
          <#if players??>
            <#list players as player>
              <#if player.name != currentPlayer.name>
                <p><a href="/game?opponent=${player.name}">${player.name}</a></p>
              <#else>
                <p>${player.name}</p>
              </#if>
            </#list>
          </#if>
      </#if>

    </div>
    
  </div>
</body>
</html>
