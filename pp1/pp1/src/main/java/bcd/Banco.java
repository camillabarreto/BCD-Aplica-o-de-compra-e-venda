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
                System.out.println("\nVocê é "+usuario.getNome());
            }else {
                System.out.println("Não existe esse id");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return usuario;
    }

    public static String listarProdutos(){
        StringBuilder sb = new StringBuilder();
        String query = "SELECT * FROM Produto";
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
        int resultado = -1;
//        String query = "INSERT INTO Usuario (nome, Cat_Usuario_idCat_Usuario) VALUES ("
//                + "'Deborah'," + 2 + ")";
        String query = "INSERT INTO Carrinho (data, idCliente) "+
                "VALUES ('"+data+"', "+usuario.getId()+")";
        PreparedStatement stmt = null;
        try {
            stmt = ConnectionFactory.getDBConnection().prepareStatement(query);
            resultado = stmt.executeUpdate(query);
            System.out.println("RESULTADO: "+resultado);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultado;
    }

    public static boolean adicionarCompras(Usuario u, Produto p, int carrinho){
        boolean resultado = false;
        String query = "INSERT INTO Compra(Produto_idProduto, Produto_idVendedor, "+
                "Carrinho_idCarrinho, Carrinho_idCliente, unidades, entrega) "+
                "VALUES ("+p.getId()+", "+p.getIdVendedor()+", "+carrinho+", "+
                u.getId()+", 1, 0)";
        PreparedStatement stmt = null;
        try {
            stmt = ConnectionFactory.getDBConnection().prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                System.out.println("\nCarrinho "+rs.getInt("idCarrinho"));
                resultado=true;
            }else {
                System.out.println("Carrinho vazio");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultado;
    }

    public static boolean procurarCarrinho(int id){
        boolean resultado = false;
        String query = "SELECT * FROM Carrinho NATURAL JOIN Usuario WHERE idCarrinho="+id+"";
        PreparedStatement stmt = null;
        try {
            stmt = ConnectionFactory.getDBConnection().prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                System.out.println("\nCarrinho "+rs.getInt("idCarrinho"));
                resultado=true;
            }else {
                System.out.println("Carrinho vazio");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultado;
    }



    public static boolean mostrarCarrinho(int carrinho){
        boolean resultado = false;
        String query = "SELECT * FROM Carrinho WHERE idCarrinho="+carrinho+"";
        PreparedStatement stmt = null;
        try {
            stmt = ConnectionFactory.getDBConnection().prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                System.out.println("\nCarrinho "+rs.getInt("idCarrinho"));
                resultado=true;
            }else {
                System.out.println("Carrinho vazio");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultado;
    }
}
