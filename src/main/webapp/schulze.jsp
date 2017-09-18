<!doctype html>
<html>
    <head>
        <meta charset="UTF-8" />
        <meta name="keywords" content="Schulze,method,on-line,calculator,election,voting" />
        <meta name="gwt:property" content="locale=<%=request.getLocale()%>">

        <title>Schulze method</title>
        <script type="text/javascript" src="schulze/schulze.nocache.js"></script>

        <!-- Bootstrap CSS -->
        <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
        <!-- Application CSS -->
        <link type="text/css" rel="stylesheet" href="schulze.css" />
        <!-- Fonts -->
        <link href='https://fonts.googleapis.com/css?family=Titillium+Web:300,300italic,600&amp;subset=latin,latin-ext' rel='stylesheet' type='text/css' />

    </head>
    <body>

        <!-- RECOMMENDED if your web app will not function without JavaScript enabled -->
        <noscript>
        <div style="width: 22em; position: absolute; left: 50%; margin-left: -11em; color: red; background-color: white; border: 1px solid red; padding: 4px; font-family: sans-serif">
            Your web browser must have JavaScript enabled
            in order for this application to display correctly.
        </div>
        </noscript>

        <div style="width:500px; margin:0 auto; text-align:center;">
            <h1 id="title" class="text-info"></h1>
            <div id="mainTabContainer"></div>
            <p style="text-align: center;"><span id="author"></span>:
                Pavel Ponec,
                <%=java.time.LocalDate.now().getYear()%>,
                help.ujorm@gmail.com</p>
        </div>
    </body>
</html>
