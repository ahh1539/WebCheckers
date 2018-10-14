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

<<<<<<< HEAD
=======

>>>>>>> 060c1c86a0128da08aec5e52e0312793eb34524e
      <#if !currentPlayer??>
        <p>There are currently ${numPlayers} players signed in.</p>
      <#else>
        <h3>Current Players</h3>
          <#if players??>
            <#list players as player>
              <#if player.username != currentPlayer.username>
                <p><a href="/game">${player.username}</a></p>
              <#else>
                <p>${player.username}</p>
              </#if>
            </#list>
          </#if>
      </#if>

    </div>
    
  </div>
</body>
</html>
