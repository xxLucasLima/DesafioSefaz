package br.com.sefaz.teste.control.servlet;

import br.com.sefaz.teste.model.dao.UsuarioDAO;
import br.com.sefaz.teste.model.domain.*;
import br.com.sefaz.teste.util.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "LoginServlet", urlPatterns = {"/index.jsp", "/logout.jsp"})
public class LoginServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Erro erros = new Erro();
        UsuarioDAO userDAO = new UsuarioDAO();

        try {
            if (request.getParameter("bOK") != null) {
                String login = request.getParameter("email");
                String senha = request.getParameter("senha");
                if (login == null || login.isEmpty()) {
                    erros.add("Login não informado!");
                }
                if (senha == null || senha.isEmpty()) {
                    erros.add("Senha não informada!");
                }
                if (!erros.isExisteErros()) {
                    Usuario user = new Usuario();
                    user.setEmail(login);
                    user.setSenha(senha);
                    boolean isUser = userDAO.VerifyEmailAndPassword(user);
                    if (isUser) {
                        request.getSession().setAttribute("usuarioLogado", user);
                        response.sendRedirect("logged/usuarios.jsp");
                        return;
                    }
                } else {
                    erros.add("Usuário não encontrado!");
                }
            }
        } catch (SQLException e) {
            erros.add("Erro na base de dados: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            erros.add("Erro com conector JDBC: " + e.getMessage());
        }

        request.getSession().invalidate();
        request.setAttribute("mensagens", erros);

        String URL = "/WEB-INF/index.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(URL);

        rd.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
