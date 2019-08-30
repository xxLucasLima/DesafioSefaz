
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Insert title here</title>
        <link href="../../css/layout.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    </head>
    <body>

        <nav class="navbar navbar-expand-lg navbar-light bg-light navbar-fixed-top">
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
                <div class="navbar-nav">
                    <a class="nav-item nav-link active brand" href="${pageContext.request.contextPath}/logged/usuarios.jsp">HOME <span class="sr-only">(current)</span></a>
                    <a class="nav-item nav-link" href="${pageContext.request.contextPath}/logged/usuarios.jsp">LISTA DE USUARIOS</a>
                    <a class="nav-item nav-link" href="${pageContext.request.contextPath}/logged/create.jsp">CRIAR NOVO USUARIO</a>
                    <a class="nav-item nav-link" href="${pageContext.request.contextPath}/logout.jsp">SAIR</a>
                </div>
            </div>
        </nav>
        <jsp:doBody/>
        <!-- rodape que você quer -->
    </body>
</html>


<style>
    .form-create, .table-usuarios{
        padding-left: 250px;
        padding-right : 250px;
        width: 100%;
        height: 100%;
        text-align: center;
    }
    .table-usuarios{
        padding-left:350px;
        padding-right : 350px;
        text-align: center;
    }
</style>
