<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<!DOCTYPE html>
<tags:layout>  

    <ul>

        <c:if test="${mensagens.existeErros}">
            <div id="erro" >
                <c:forEach var="erro" items="${mensagens.erros}">
                    <li style="color: red"> ${erro} </li>
                    </c:forEach>
            </div>
        </c:if>    
    </ul>
    <font color="red"><h2> ${mensagem}</h2></font>
    <div class="form-create">
        <form method="post" action="create.jsp">
            <h2> Novo Usuário</h2><br><br>
            <div class="container">
                <h4>Dados Pessoais: <h4>
                        <input type="hidden"  name="id" value="${usuario.id}">
                        <div class="form-group">
                            <input type="text" class="form-control" name="nome" placeholder="Nome" value="${usuario.nome}">
                        </div>
                        <div class="form-group">
                            <input type="text" class="form-control" name="email" placeholder="Email" value="${usuario.email}">
                        </div>
                        <div class="form-group">
                            <input type="text" class="form-control" name="login" placeholder="Login" value="${usuario.login}">
                        </div>
                        <div class="form-group">
                            <input type="password" class="form-control" name="senha" placeholder="Senha" value="${usuario.senha}">
                        </div>       
                        <h4> Telefone para contato (Obrigatório):</h4>
                        <div class="form-group">
                            <input type="hidden" name="id1" value="${telefone1.id}">
                            <input type="text" class="form-control" name="ddd1" placeholder="DDD" value="${telefone1.ddd}">
                        </div>
                        <div class="form-group">
                            <input type="text" class="form-control" name="numero1" placeholder="Numero de Contato" value="${telefone1.numero}">
                        </div>

                        <select class="form-control" name="tipo1">
                            <option value= " " >Selecione Tipo de Contato</option>
                            <option value="C" ${tipo1 == 'C' ? 'selected' : ''} >Comercial</option>
                            <option value="P" ${tipo1 == 'P' ? 'selected' : ''}>Pessoal</option>
                            <option value="R" ${tipo1 == 'R' ? 'selected' : ''}>Residencial</option>
                            <option value="T" ${tipo1 == 'T' ? 'selected' : ''}>Contato</option>   
                            <option value="O" ${tipo1 == 'O' ? 'selected' : ''}>Outro</option>
                        </select><br>
                        <h4> Telefone para contato (Opcional):</h4>
                        <div class="form-group">
                            <input type="hidden" name="id2" value="${telefone2.id}">
                            <input type="text" class="form-control" name="ddd2" placeholder="DDD" value="${telefone2.ddd}">
                        </div>
                        <div class="form-group">
                            <input type="text" class="form-control" name="numero2" placeholder="Numero de Contato" value="${telefone2.numero}">
                        </div>

                        <select class="form-control" name="tipo2" >
                            <option value= " ">Selecione Tipo de Contato</option>
                            <option value="C" value="C" ${tipo2 == 'C' ? 'selected' : ''}>Comercial</option>
                            <option value="P" value="P" ${tipo2 == 'P' ? 'selected' : ''}>Pessoal</option>
                            <option value="R" value="R" ${tipo2 == 'R' ? 'selected' : ''}>Residencial</option>
                            <option value="T" value="T" ${tipo2 == 'T' ? 'selected' : ''}>Contato</option>   
                            <option value="O" value="O" ${tipo2 == 'O' ? 'selected' : ''}>Outro</option>
                        </select><br>

                        <div class="form-group">
                            <input type="submit" class="btn btn-primary" value="Enviar">
                            <input type="button" class="btn btn-secondary " value="Voltar" onclick="window.location = '${pageContext.request.contextPath}/logged/usuarios.jsp;'" />    
                        </div>
                        </form>
                        </div>
                        </div>
                    </tags:layout>
