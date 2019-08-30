package br.com.sefaz.teste.control.servlet;

import br.com.sefaz.teste.model.dao.UsuarioDAO;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.sefaz.teste.model.domain.*;
import java.sql.SQLException;

@WebServlet(name = "CreateUsuarioServlet", urlPatterns = {"/logged/create.jsp"})
public class UsuarioFormServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            String id = request.getParameter("id");
            if (action != null && action.equals("edit")) {
                int idInt = Integer.parseInt(id);
                Usuario user = new Usuario();
                UsuarioDAO userDAO = new UsuarioDAO();
                user = userDAO.GetUserById(idInt);
                request.setAttribute("usuario", user);
            }
        } catch (SQLException e) {
            request.setAttribute("mensagem", "Erro na base de dados: "+ e.getMessage());
        } catch (ClassNotFoundException e) {
            request.setAttribute("mensagem", "Erro no conector JDBC: " + e.getMessage());
        }
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/logged/create.jsp");
        rd.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            UsuarioDAO userDao = new UsuarioDAO();

            String nome = request.getParameter("nome");
            String email = request.getParameter("email");
            String senha = request.getParameter("senha");
            String ddd = request.getParameter("ddd");
            String numero = request.getParameter("numero");
            String tipo = request.getParameter("tipo");
            String id = request.getParameter("id");

            Usuario user = new Usuario();
            user.setNome(nome);
            user.setEmail(email);
            user.setSenha(senha);
            user.Telefone = new Telefone();
            user.getTelefone().setDdd(Integer.parseInt(ddd));
            user.getTelefone().setNumero(numero);
            user.getTelefone().setTipo(tipo);

            if (id != null && !id.equals("")) {
                user.setId(Integer.parseInt(id));
                userDao.UpdateUsuario(user);
                request.setAttribute("mensagem", "Usuário Atualizado com sucesso");
            } else {
                userDao.CreateUsuario(user);
                request.setAttribute("mensagem", "Usuário Criado com sucesso");
            }
        } catch (SQLException e) {
            request.setAttribute("mensagem", "Erro na base de dados: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            request.setAttribute("mensagem", "Erro no conector JDBC: " + e.getMessage());
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/logged/create.jsp");

        dispatcher.forward(request, response);
    }
}
