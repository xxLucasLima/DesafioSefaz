<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<tags:layout>


    <font color="red"><h4> ${mensagem}</h4></font>

    <div class="table-usuarios ">

        <table class="table table-striped table-responsive">
            <br><h2> Bem vindo <font color="blue">${sessionScope.usuarioLogado.login}</font>!</h2><br>

            <thead class="thead-dark">
                <tr>                
                    <th scope="col">ID</th>
                    <th scope="col">Nome</th>
                    <th scope="col">Email</th>
                    <th scope="col">Login</th>
                    <th scope="col"></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="usuario" items="${usuarios}" >
                    <tr>      
                        <td>${usuario.id}</td>
                        <td>${usuario.nome}</td>
                        <td>${usuario.email}</td>
                        <td>${usuario.login}</td>

                        <td><a href="create.jsp?action=edit&id=${usuario.id}">Editar</a>
                            <a href="usuarios.jsp?action=delete&id=${usuario.id}">Excluir</a> 
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>


</tags:layout>
