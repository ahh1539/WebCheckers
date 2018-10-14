<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <meta http-equiv="refresh" content="10">
    <link rel="stylesheet" type="text/css" href="/css/style.css">

    <title>${title} | Web Checkers</title>
</head>
<body>
  <div class="page">

    <h1>Web Checkers Sign In</h1>

    <div class="navigation">
      <a href="/">Back home</a>
    </div>


    <div class="body">
      <p>Welcome to the sign in page</p>
      <form action="./signin" method="POST">
        <label for="uname">Username</label>
        <input required type="text" id="uname" name="username" placeholder="Username...">
        <input type="submit" value="Sign In">
        <#if message??>
            <p style="color:darkred">${message}</p>
        </#if>
      </form>
    </div>

  </div>
</body>
</html>