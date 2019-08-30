package br.com.sefaz.teste.control.servlet;

import br.com.sefaz.teste.model.dao.*;
import br.com.sefaz.teste.model.domain.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "UsuariosList", urlPatterns = {"/logged/usuarios.jsp"})
public class UsuarioListServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UsuarioDAO userDAO = new UsuarioDAO();

        try {
            String action = request.getParameter("action");
            String id = request.getParameter("id");

            if (action != null && action.equals("delete")) {
                int idInt = Integer.parseInt(id);
                userDAO.DeleteUsuario(idInt);
                request.setAttribute("mensagem", "Usuário Excluído com sucesso");
            }
            request.setAttribute("usuarios", userDAO.ListAllUsuarios());
        } catch (SQLException e) {
            request.setAttribute("mensagem", "Erro na base de dados: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            request.setAttribute("mensagem", "Erro no conector JDBC: " + e.getMessage());
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/logged/usuarios.jsp");
        dispatcher.forward(request, response);
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
