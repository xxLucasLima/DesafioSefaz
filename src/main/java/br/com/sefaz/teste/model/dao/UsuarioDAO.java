package br.com.sefaz.teste.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import br.com.sefaz.teste.model.domain.*;

public class UsuarioDAO {

    public PreparedStatement usuarioStatement = null;
    public PreparedStatement telefoneStatement = null;

    public boolean CreateUsuario(Usuario usuario) throws SQLException, ClassNotFoundException {
        int result = 0;
        int usuarioid = 0;
        Connection connection = JDBCFactory.getConnection();
        try {
            connection.setAutoCommit(false);
            usuarioStatement = connection.prepareStatement("INSERT INTO Public.USUARIO ( NOME, EMAIL, LOGIN, SENHA) VALUES (?,?,?,?);", usuarioStatement.RETURN_GENERATED_KEYS);
            usuarioStatement.setString(1, usuario.getNome());
            usuarioStatement.setString(2, usuario.getEmail());
            usuarioStatement.setString(3, usuario.getLogin());
            usuarioStatement.setString(4, usuario.getSenha());

            result = usuarioStatement.executeUpdate();

            try (ResultSet generatedKeys = usuarioStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    usuarioid = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Falha na criação do usuário. Não foi gerado ID");
                }
            }

            if (result == 0) {
                throw new SQLException("Houve um erro ao criar usuário.");
            }
            for (Telefone telefone : usuario.getTelefones()) {
                telefoneStatement = connection.prepareStatement("INSERT INTO Public.TELEFONE ( DDD, NUMERO, TIPO, USUARIOID) VALUES (?,?,?,?);");
                telefoneStatement.setInt(1, telefone.getDdd());
                telefoneStatement.setString(2, telefone.getNumero());
                telefoneStatement.setString(3, String.valueOf(telefone.getTipo()));
                telefoneStatement.setInt(4, usuarioid);
                result = telefoneStatement.executeUpdate();

                if (result == 0) {
                    throw new SQLException("Houve um erro ao informar dados do telefone.");
                }
            }

        } finally {
            connection.commit();
            JDBCFactory.close(telefoneStatement);
            JDBCFactory.close(usuarioStatement, connection);
        }

        return true;
    }

    public ArrayList<Usuario> ListAllUsuarios() throws SQLException, ClassNotFoundException {

        ArrayList<Usuario> UsuariosList = new ArrayList<>();

        Connection connection = JDBCFactory.getConnection();
        Usuario usuario;
        ResultSet result = null;
        try {
            usuarioStatement = connection.prepareStatement(
                    "SELECT * FROM Usuario ORDER BY nome;");
            result = usuarioStatement.executeQuery();

            while (result.next()) {
                usuario = new Usuario();

                usuario.setId(result.getInt("ID"));
                usuario.setNome(result.getString("NOME"));
                usuario.setEmail(result.getString("EMAIL"));
                usuario.setLogin(result.getString("LOGIN"));

                UsuariosList.add(usuario);
            }

        } finally {

            JDBCFactory.close(result, usuarioStatement, connection);
        }

        return UsuariosList;

    }

    public int UpdateUsuario(Usuario usuario) throws SQLException, ClassNotFoundException {

        int result = 0;
        Connection connection = JDBCFactory.getConnection();
        try {

            connection.setAutoCommit(false);
            usuarioStatement = connection.prepareStatement("UPDATE USUARIO SET NOME =?, EMAIL=?, LOGIN =?, SENHA=? WHERE ID=?;");

            usuarioStatement.setString(1, usuario.getNome());
            usuarioStatement.setString(2, usuario.getEmail());
            usuarioStatement.setString(3, usuario.getLogin());
            usuarioStatement.setString(4, usuario.getSenha());
            usuarioStatement.setInt(5, usuario.getId());
            result = usuarioStatement.executeUpdate();

            if (result == 0) {
                throw new SQLException("Houve um erro ao editar usuário.");
            }

            for (Telefone telefone : usuario.getTelefones()) {

                if (telefone.getIdString() == "" || telefone.getIdString() == null) {
                    telefoneStatement = connection.prepareStatement("INSERT INTO Public.TELEFONE ( DDD, NUMERO, TIPO, USUARIOID) VALUES (?,?,?,?);");
                    telefoneStatement.setInt(1, telefone.getDdd());
                    telefoneStatement.setString(2, telefone.getNumero());
                    telefoneStatement.setString(3, String.valueOf(telefone.getTipo()));
                    telefoneStatement.setInt(4, usuario.getId());

                    result = telefoneStatement.executeUpdate();

                } else {
                    telefoneStatement = connection.prepareStatement("UPDATE TELEFONE SET DDD =?, NUMERO=?, TIPO=? WHERE USUARIOID=? and ID=?");
                    telefoneStatement.setInt(1, telefone.getDdd());
                    telefoneStatement.setString(2, telefone.getNumero());
                    telefoneStatement.setString(3, String.valueOf(telefone.getTipo()));
                    telefoneStatement.setInt(4, usuario.getId());
                    telefoneStatement.setInt(5, telefone.getId());

                    result = telefoneStatement.executeUpdate();
                }
            }

            if (result == 0) {
                throw new SQLException("Houve um erro ao incluir dados de telefone.");
            }
        } finally {
            connection.commit();
            JDBCFactory.close(telefoneStatement);
            JDBCFactory.close(usuarioStatement, connection);
        }
        return result;
    }

    public int DeleteUsuario(int id) throws SQLException, ClassNotFoundException {

        int result = 0;
        Connection connection = JDBCFactory.getConnection();
        try {
            connection.setAutoCommit(false);

            telefoneStatement = connection.prepareStatement("DELETE FROM TELEFONE WHERE USUARIOID = ?");

            telefoneStatement.setInt(1, id);

            result = telefoneStatement.executeUpdate();

            if (result == 0) {
                throw new SQLException("Houve um erro ao editar usuário.");
            }

            usuarioStatement = connection.prepareStatement("DELETE FROM USUARIO WHERE ID = ?");

            usuarioStatement.setInt(1, id);

            result = usuarioStatement.executeUpdate();

            if (result == 0) {
                throw new SQLException("Houve um erro ao editar usuário.");
            }

            result = usuarioStatement.executeUpdate();
        } finally {
            connection.commit();
            JDBCFactory.close(telefoneStatement);
            JDBCFactory.close(usuarioStatement, connection);
        }
        return result;
    }

    public boolean VerifyEmailAndPassword(Usuario usuario) throws SQLException, ClassNotFoundException {
        Connection connection = JDBCFactory.getConnection();
        ResultSet result = null;
        try {
            usuarioStatement = connection.prepareStatement("SELECT * FROM USUARIO WHERE LOGIN=? AND SENHA=?");
            usuarioStatement.setString(1, usuario.getLogin());
            usuarioStatement.setString(2, usuario.getSenha());
            result = usuarioStatement.executeQuery();

            if (!result.next()) {
                throw new SQLException("Dados de login incorretos");
            }
        } finally {
            JDBCFactory.close(result, usuarioStatement, connection);
        }
        return true;
    }

    public boolean VerifyLogin(String login, String id) throws SQLException, ClassNotFoundException {
        Connection connection = JDBCFactory.getConnection();
        ResultSet result = null;
        try {
            if (id == null || id.equals("")) {
                usuarioStatement = connection.prepareStatement("SELECT Login FROM USUARIO WHERE LOGIN=?");
                usuarioStatement.setString(1, login);
                result = usuarioStatement.executeQuery();
            } else {
                usuarioStatement = connection.prepareStatement("SELECT Login FROM USUARIO WHERE LOGIN=? AND ID <> ? ");
                usuarioStatement.setString(1, login);
                usuarioStatement.setInt(2, Integer.parseInt(id));
                result = usuarioStatement.executeQuery();
            }

            if (!result.next()) {
                return false;
            }
        } finally {
            JDBCFactory.close(result, usuarioStatement, connection);
        }
        return true;
    }

    public Usuario GetUserById(int id) throws SQLException, ClassNotFoundException {

        Connection connection = JDBCFactory.getConnection();
        Usuario usuario;
        ResultSet result = null;
        try {
            usuarioStatement = connection.prepareStatement("SELECT * FROM Usuario WHERE id = ?");

            usuarioStatement.setInt(1, id);

            result = usuarioStatement.executeQuery();

            if (!result.next()) {
                throw new SQLException("Houve um erro ao editar usuário.");
            } else {
                usuario = new Usuario();
                usuario.Telefones = new ArrayList<Telefone>();
                usuario.setNome(result.getString("NOME"));
                usuario.setEmail(result.getString("EMAIL"));
                usuario.setSenha(result.getString("SENHA"));
                usuario.setLogin(result.getString("LOGIN"));
                usuario.setId(result.getInt("ID"));
            }

            telefoneStatement = connection.prepareStatement("SELECT * FROM telefone WHERE usuarioid = ?");
            telefoneStatement.setInt(1, id);

            result = telefoneStatement.executeQuery();

            while (result.next()) {
                Telefone telefone = new Telefone();
                telefone.setNumero(result.getString("NUMERO"));
                telefone.setDdd(result.getInt("DDD"));
                telefone.setTipo(result.getString("TIPO").charAt(0));
                telefone.setId(result.getInt("ID"));
                telefone.setUsuarioId(result.getInt("USUARIOID"));
                usuario.Telefones.add(telefone);
            }
        } finally {

            JDBCFactory.close(telefoneStatement);
            JDBCFactory.close(result, usuarioStatement, connection);
        }
        return usuario;
    }
}
