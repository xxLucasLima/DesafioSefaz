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
        Connection connection = JDBCFactory.getConnection();

        connection.setAutoCommit(false);
        usuarioStatement = connection.prepareStatement("INSERT INTO Public.USUARIO ( NOME, EMAIL, SENHA) VALUES (?,?,?);", usuarioStatement.RETURN_GENERATED_KEYS);
        usuarioStatement.setString(1, usuario.getNome());
        usuarioStatement.setString(2, usuario.getEmail());
        usuarioStatement.setString(3, usuario.getSenha());
        result = usuarioStatement.executeUpdate();

        if (result == 0) {
            throw new SQLException("Houve um erro ao criar usuário.");
        }

        telefoneStatement = connection.prepareStatement("INSERT INTO Public.TELEFONE ( DDD, NUMERO, TIPO, USUARIOID) VALUES (?,?,?,?);");
        telefoneStatement.setInt(1, usuario.getTelefone().getDdd());
        telefoneStatement.setString(2, usuario.getTelefone().getNumero());
        telefoneStatement.setString(3, usuario.getTelefone().getTipo());
        try (ResultSet generatedKeys = usuarioStatement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                telefoneStatement.setInt(4, generatedKeys.getInt(1));
            } else {
                throw new SQLException("Falha na criação do usuário. Não foi gerado ID");
            }
        }

        result = telefoneStatement.executeUpdate();

        if (result == 0) {
            throw new SQLException("Houve um erro ao informar dados do telefone.");
        }
        connection.commit();
        JDBCFactory.close(telefoneStatement);
        JDBCFactory.close(usuarioStatement, connection);

        return true;
    }

    public ArrayList<Usuario> ListAllUsuarios() throws SQLException, ClassNotFoundException {

        ArrayList<Usuario> UsuariosList = new ArrayList<>();

        Connection connection = JDBCFactory.getConnection();
        Usuario usuario;

        usuarioStatement = connection.prepareStatement(
                "SELECT Usuario.*, Telefone.* FROM Usuario INNER JOIN Telefone ON Usuario.id=Telefone.usuarioId;");
        ResultSet result = usuarioStatement.executeQuery();

        while (result.next()) {
            usuario = new Usuario();
            usuario.Telefone = new Telefone();

            usuario.setNome(result.getString("NOME"));
            usuario.setEmail(result.getString("EMAIL"));
            usuario.setSenha(result.getString("SENHA"));
            usuario.setId(result.getInt("ID"));
            usuario.getTelefone().setDdd(result.getInt("DDD"));
            usuario.getTelefone().setNumero(result.getString("NUMERO"));
            usuario.getTelefone().setTipo(result.getString("TIPO"));

            UsuariosList.add(usuario);
        }

        JDBCFactory.close(result, usuarioStatement, connection);

        return UsuariosList;
    }

    public int UpdateUsuario(Usuario usuario) throws SQLException, ClassNotFoundException {

        int result = 0;
        Connection connection = JDBCFactory.getConnection();

        connection.setAutoCommit(false);
        usuarioStatement = connection.prepareStatement("UPDATE USUARIO SET NOME =?, EMAIL=?, SENHA=? WHERE ID=?;");

        usuarioStatement.setString(1, usuario.getNome());
        usuarioStatement.setString(2, usuario.getEmail());
        usuarioStatement.setString(3, usuario.getSenha());
        usuarioStatement.setInt(4, usuario.getId());
        result = usuarioStatement.executeUpdate();

        if (result == 0) {
            throw new SQLException("Houve um erro ao editar usuário.");
        }

        telefoneStatement = connection.prepareStatement("UPDATE TELEFONE SET DDD =?, NUMERO=?, TIPO=? WHERE USUARIOID=?");

        telefoneStatement.setInt(1, usuario.Telefone.getDdd());
        telefoneStatement.setString(2, usuario.Telefone.getNumero());
        telefoneStatement.setString(3, usuario.Telefone.getTipo());
        telefoneStatement.setInt(4, usuario.getId());

        result = telefoneStatement.executeUpdate();

        if (result == 0) {
            throw new SQLException("Houve um erro ao incluir dados de telefone.");
        }

        connection.commit();
        JDBCFactory.close(telefoneStatement);
        JDBCFactory.close(usuarioStatement, connection);

        return result;
    }

    public int DeleteUsuario(int id) throws SQLException, ClassNotFoundException {

        int result = 0;
        Connection connection = JDBCFactory.getConnection();

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

        connection.commit();
        JDBCFactory.close(telefoneStatement);
        JDBCFactory.close(usuarioStatement, connection);

        return result;
    }

    public boolean VerifyEmailAndPassword(Usuario usuario) throws SQLException, ClassNotFoundException {
        Connection connection = JDBCFactory.getConnection();

        usuarioStatement = connection.prepareStatement("SELECT * FROM USUARIO WHERE EMAIL=? AND SENHA=?");
        usuarioStatement.setString(1, usuario.getEmail());
        usuarioStatement.setString(2, usuario.getSenha());
        ResultSet result = usuarioStatement.executeQuery();

        if (!result.next()) {
            throw new SQLException("Dados de login incorretos");
        }

        JDBCFactory.close(result, usuarioStatement, connection);

        return true;
    }

    public Usuario GetUserById(int id) throws SQLException, ClassNotFoundException {

        Connection connection = JDBCFactory.getConnection();
        Usuario usuario;

        usuarioStatement = connection.prepareStatement("SELECT Usuario.*, Telefone.* FROM Usuario INNER JOIN Telefone ON Usuario.id=Telefone.usuarioId WHERE usuario.id = ?;");

        usuarioStatement.setInt(1, id);

        ResultSet result = usuarioStatement.executeQuery();

        if (!result.next()) {
            throw new SQLException("Houve um erro ao editar usuário.");
        } else {
            usuario = new Usuario();
            usuario.Telefone = new Telefone();

            usuario.setNome(result.getString("NOME"));
            usuario.setEmail(result.getString("EMAIL"));
            usuario.setSenha(result.getString("SENHA"));
            usuario.setId(result.getInt("ID"));
            usuario.getTelefone().setDdd(result.getInt("DDD"));
            usuario.getTelefone().setNumero(result.getString("NUMERO"));
            usuario.getTelefone().setTipo(result.getString("TIPO"));
        }

        JDBCFactory.close(result, usuarioStatement, connection);

        return usuario;
    }
}
