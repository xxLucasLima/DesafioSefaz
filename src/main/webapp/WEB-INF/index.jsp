<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Login</title>
        <script src="/index.js"></script>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    </head>
    <body>
        <div id="form-group">
            <form action="index.jsp" method="post">
                <h2 >Login</h2>
                <input type="text" name="login" placeholder="Login" class="form-control"></input>
                <input type="password" name="senha" placeholder="Senha" class="form-control"><hr>
                <button type="submit" name="bOK" class="btn btn-primary form-control">Enviar</button>
        </div>
    </form>
    
        <c:if test="${mensagens.existeErros}">
            <div id="erro" >
                <ul>
                    <c:forEach var="erro" items="${mensagens.erros}">
                        <h4> ${erro} <h4>
                        </c:forEach>
                </ul>
            </div>
        </c:if>        
            
</body>
</html>

<style>
    body, html {   
        width: 100%;
        height: 100%;
        margin: 0;
        padding: 0;
        display:table;
    }
    h2{
        text-align: center;
    }
    body {
        display:table-cell;
        vertical-align:middle;
    }
    form {
        display:table;/* shrinks to fit content */
        margin:auto;
    }
    
    #erro{
        color: red;
        text-align: center;
        padding-top: 50px;
    }
</style>