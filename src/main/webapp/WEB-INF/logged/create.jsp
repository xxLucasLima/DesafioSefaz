<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<!DOCTYPE html>
<tags:layout>

    <font color="red"><h2> ${mensagem}</h2></font>
    <div class="form-create">
        <form method="post" action="create.jsp">
            <h2> Novo Usuário</h2>

            <input type="hidden"  name="id" value="${usuario.id}">
            <div class="form-group">
                <input type="text" class="form-control" name="nome" placeholder="Nome" value="${usuario.nome}">
            </div>
            <div class="form-group">
                <input type="text" class="form-control" name="email" placeholder="Email" value="${usuario.email}">
            </div>
            <div class="form-group">
                <input type="password" class="form-control" name="senha" placeholder="Senha" value="${usuario.senha}">
            </div>
            <div class="form-group">
                <input type="text" class="form-control" name="ddd" placeholder="DDD" value="${usuario.telefone.ddd}">
            </div>
            <div class="form-group">
                <input type="text" class="form-control" name="numero" placeholder="Numero de Contato" value="${usuario.telefone.numero}">
            </div>

            <select class="form-control" name="tipo" value="${usuario.telefone.tipo}">
                <option hidden >Selecione Tipo de Contato</option>
                <option>Comercial</option>
                <option>Pessoal</option>
            </select><br>

            <div class="form-group">
                <input type="submit" class="btn btn-primary" value="Enviar">
                <input type="submit" class="btn btn-primary" value="Enviar">

            </div>
        </form>
    </div>
</tags:layout>
