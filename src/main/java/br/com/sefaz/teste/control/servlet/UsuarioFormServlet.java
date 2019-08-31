package br.com.sefaz.teste.control.servlet;

import br.com.sefaz.teste.model.dao.UsuarioDAO;
import br.com.sefaz.teste.util.*;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.sefaz.teste.model.domain.*;
import java.sql.SQLException;
import java.util.ArrayList;

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
                request.setAttribute("telefone1", user.Telefones.get(0));
                request.setAttribute("tipo1", String.valueOf(user.Telefones.get(0).getTipo()));

                if (user.Telefones.size() > 1) {
                    request.setAttribute("telefone2", user.Telefones.get(1));
                    request.setAttribute("tipo2", String.valueOf(user.Telefones.get(1).getTipo()));

                }
            }
        } catch (SQLException e) {
            request.setAttribute("mensagem", "Erro na base de dados: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            request.setAttribute("mensagem", "Erro no conector JDBC: " + e.getMessage());
        }
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/logged/create.jsp");
        rd.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Erro erros = new Erro();

        try {
            UsuarioDAO userDao = new UsuarioDAO();

            String nome = request.getParameter("nome");
            String email = request.getParameter("email");
            String login = request.getParameter("login");
            String senha = request.getParameter("senha");
            String id = request.getParameter("id");

            String ddd1 = request.getParameter("ddd1");
            String numero1 = request.getParameter("numero1");
            String tipo1 = request.getParameter("tipo1");
            String id1 = request.getParameter("id1");
            String idStr1 = request.getParameter("id1");

            String ddd2 = request.getParameter("ddd2");
            String numero2 = request.getParameter("numero2");
            String tipo2 = request.getParameter("tipo2");
            String id2 = request.getParameter("id2");
            String idStr2 = request.getParameter("id2");

            Usuario user = new Usuario();
            user.setNome(nome);
            user.setEmail(email);
            user.setLogin(login);
            user.setSenha(senha);

            Telefone telefone1 = new Telefone();
            if (ddd1 != null && !ddd1.equals("")) {
                telefone1.setDdd(Integer.parseInt(ddd1));
            }

            telefone1.setNumero(numero1);
            telefone1.setTipo(tipo1.charAt(0));
            if (id1 != null && !id1.equals("")) {
                telefone1.setId(Integer.parseInt(id1));
            }

            Telefone telefone2 = new Telefone();
            if (ddd2 != null && !ddd2.equals("")) {
                telefone2.setDdd(Integer.parseInt(ddd2));
            }

            telefone2.setTipo(tipo2.charAt(0));
            telefone2.setNumero(numero2);

            if (!id2.equals("") && id2 != null) {
                telefone2.setId(Integer.parseInt(id2));
            }

            user.Telefones = new ArrayList<Telefone>();
            telefone1.setIdString(idStr1);
            telefone2.setIdString(idStr2);
            user.Telefones.add(telefone1);
            user.Telefones.add(telefone2);

            erros = ValidateForm(user, userDao, id);

            if (id != null && !id.equals("")) {
                user.setId(Integer.parseInt(id));
                if (!erros.isExisteErros()) {
                    userDao.UpdateUsuario(user);
                    request.setAttribute("mensagem", "Usuário Atualizado com sucesso");
                } else {
                    request.setAttribute("usuario", user);
                    request.setAttribute("telefone1", telefone1);
                    request.setAttribute("telefone2", telefone2);
                    request.setAttribute("tipo1", String.valueOf(user.Telefones.get(0).getTipo()));
                    request.setAttribute("tipo2", String.valueOf(user.Telefones.get(1).getTipo()));

                }
            } else {
                if (!erros.isExisteErros()) {
                    userDao.CreateUsuario(user);
                    request.setAttribute("mensagem", "Usuário Criado com sucesso");
                } else {
                    request.setAttribute("usuario", user);
                    request.setAttribute("telefone1", telefone1);
                    request.setAttribute("telefone2", telefone2);
                    request.setAttribute("tipo1", String.valueOf(user.Telefones.get(0).getTipo()));
                    request.setAttribute("tipo2", String.valueOf(user.Telefones.get(1).getTipo()));
                }
            }

        } catch (SQLException e) {
            request.setAttribute("mensagem", "Erro na base de dados: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            request.setAttribute("mensagem", "Erro no conector JDBC: " + e.getMessage());
        }

        request.setAttribute("mensagens", erros);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/logged/create.jsp");

        dispatcher.forward(request, response);
    }

    private Erro ValidateForm(Usuario user, UsuarioDAO userDAO, String id) throws SQLException, ClassNotFoundException {
        Erro erro = new Erro();

        if (user.getEmail().length() > 100) {
            erro.add("Email não pode ter mais que 100 caracteres");
        }
        if (user.getEmail() == null || user.getEmail().equals("")) {
            erro.add("Campo Email deve ser preenchido");
        }
        if (user.getSenha().length() > 100) {
            erro.add("Senha não pode ter mais que 100 caracteres");
        }
        if (user.getSenha().length() < 6) {
            erro.add("Senha não pode ter menos que 6 caracteres");
        }
        if (user.getSenha() == null || user.getSenha().equals("")) {
            erro.add("Campo Senha deve ser preenchido");
        }
        if (!user.getSenha().matches(".*\\d.*") || !user.getSenha().matches(".*[a-zA-Z].*")) {
            erro.add("Senha muito fraca. Campo Senha deve possuir pelo menos um número e uma letra");
        }
        if (user.getNome().length() > 100) {
            erro.add("Nome não pode ter mais que 100 caracteres");
        }
        if (user.getNome() == null || user.getNome().equals("")) {
            erro.add("Campo Nome deve ser preenchido");
        }
        if (user.getLogin().length() > 100) {
            erro.add("Login não pode ter mais que 100 caracteres");
        }

        if (user.getLogin() == null || user.getLogin().equals("")) {
            erro.add("Campo Login deve ser preenchido");
        }

        if (userDAO.VerifyLogin(user.getLogin(), id)) {
            erro.add("Login ja existe.");
        }

        if (user.Telefones.get(1).getDdd() == 0 && (user.Telefones.get(1).getNumero() == null || user.Telefones.get(1).getNumero().equals(""))
                && (user.Telefones.get(1).getTipo() == ' ' || user.Telefones.get(1).getTipo() == ' ')) {
            user.Telefones.remove(1);
        }

        ValidateTelefone(user.Telefones, erro);

        return erro;
    }

    private Erro ValidateTelefone(ArrayList<Telefone> telefones, Erro erro) {

        for (Telefone telefone : telefones) {

            if (telefone.getDdd() == 0) {
                erro.add("DDD não pode ser 0");
            }
            if (telefone.getDdd() > 999) {
                erro.add("DDD não pode ser maior que 999");
            }
            if (telefone.getNumero() == null || telefone.getNumero().equals("")) {
                erro.add("Campo Número deve ser preenchido");
            }
            if (telefone.getNumero().length() < 8 || telefone.getNumero().matches("[a-zA-Z]+")) {
                erro.add("Numero Inválido");
            }
            if (telefone.getTipo() == ' ') {
                erro.add("Campo Tipo de contato deve ser preenchido");
            }
        }

        return erro;
    }
}
