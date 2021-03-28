package bcd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Banco {
    public static Usuario procurarUsuario(int cat, int id){
        Usuario usuario = null;
        String query = "SELECT * FROM Usuario WHERE Cat_Usuario_idCat_Usuario="+cat+" and idUsuario="+id+"";
        PreparedStatement stmt = null;
        try {
            stmt = ConnectionFactory.getDBConnection().prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                usuario = new Usuario(id, rs.getString("nome"), cat);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return usuario;
    }

    public static String listarProdutos(){
        String query = "SELECT * FROM Produto";
        StringBuilder sb = new StringBuilder();
        PreparedStatement stmt = null;
        try {
            stmt = ConnectionFactory.getDBConnection().prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                sb.append("------------------------------------------------------\n");
                sb.append(String.format("|%-5s|%-35s|%10s|\n", "ID", "Produto", "Valor"));
                sb.append("------------------------------------------------------\n");
                do {
                    int idProduto = rs.getInt("idProduto");
                    String nome = rs.getString("nome");
                    Float valor = rs.getFloat("valor");

                    sb.append(String.format("|%-5d|%-35s|%10.2f|\n", idProduto, nome, valor));

                } while (rs.next());
                sb.append("------------------------------------------------------\n");
            }else {
                sb.append("Não há registros no banco de dados\n");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return sb.toString();
    }

    public static Produto procurarProduto(int id){
        Produto produto = null;
        String query = "SELECT * FROM Produto WHERE idProduto="+id+"";
        PreparedStatement stmt = null;
        try {
            stmt = ConnectionFactory.getDBConnection().prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                int idProduto = rs.getInt("idProduto");
                String nome = rs.getString("nome");
                float valor = rs.getFloat("valor");
                int categoria = rs.getInt("Cat_Produto_idCat_Produto");
                int idVendedor = rs.getInt("idVendedor");
                produto = new Produto(id, nome, valor, categoria, idVendedor);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return produto;
    }

    public static int adicionarCarrinho(Usuario usuario, String data){
        String query = "INSERT INTO Carrinho (data, idCliente) "+
                "VALUES ('"+data+"', "+usuario.getId()+")";
        PreparedStatement stmt = null;
        int resultado = -1;
        try {
            stmt = ConnectionFactory.getDBConnection().prepareStatement(query);
            stmt.executeUpdate(query);
            query = "SELECT max(LAST_INSERT_ID(idCarrinho)) id FROM Carrinho";
            stmt = ConnectionFactory.getDBConnection().prepareStatement(query);
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            resultado = rs.getInt("id");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultado;
    }

    public static int adicionarCompras(Usuario u, Produto p, int carrinho){
        int resultado = -1;
        String query = "INSERT INTO Compra(Produto_idProduto, Produto_idVendedor, "+
                "Carrinho_idCarrinho, Carrinho_idCliente, unidades, entrega) "+
                "VALUES ("+p.getId()+", "+p.getIdVendedor()+", "+carrinho+", "+
                u.getId()+", 1, 0)";
        PreparedStatement stmt = null;
        try {
            stmt = ConnectionFactory.getDBConnection().prepareStatement(query);
            resultado = stmt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultado;
    }

    public static StringBuilder listarPedidos(int id){
//        String query = "SELECT * FROM Compra WHERE Produto_idVendedor="+id+""; //AND entrega=0
        String query = "SELECT C.idCompra, P.nome, C.entrega FROM Compra C"+
                " NATURAL JOIN Produto P WHERE Produto_idVendedor="+id+
                " AND C.Produto_idProduto=P.idProduto";
        StringBuilder sb = null;
        PreparedStatement stmt = null;
        try {
            stmt = ConnectionFactory.getDBConnection().prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                sb = new StringBuilder();
                sb.append("------------------------------------------------------\n");
                sb.append(String.format("|%-5s|%-35s|%10s|\n", "ID", "Produto", "Status"));
                sb.append("------------------------------------------------------\n");
                do {
                    int idCompra = rs.getInt("idCompra");
                    String nome = rs.getString("nome");
                    int entrega = rs.getInt("entrega");

                    sb.append(String.format("|%-5d|%-35s|%10.2s|\n", idCompra, nome, entrega));

                } while (rs.next());
                sb.append("------------------------------------------------------\n");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return sb;
    }

    public static boolean marcarEntrega(int idCompra, int idVendedor){
        boolean resultado=false;
        String query = "SELECT * FROM Compra WHERE idCompra="+idCompra+" AND Produto_idVendedor="+idVendedor+"";
        PreparedStatement stmt = null;
        try {
            stmt = ConnectionFactory.getDBConnection().prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                query = " UPDATE Compra SET entrega = 1 WHERE idCompra = "+idCompra+"";
                stmt = ConnectionFactory.getDBConnection().prepareStatement(query);
                resultado = stmt.executeUpdate()==1;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultado;
    }

}
