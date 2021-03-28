package bcd;

import com.mysql.cj.protocol.x.XProtocolDecoder;

import javax.management.timer.Timer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Principal {
    private final String[] ENTRADA = {
            "\n\n\n:::::::::::::::::::::::::\n",
            ":::::::::::::::::::::::::\n",
            "..:: SEJA BEM VINDO! ::..\n",
            "..::     Você é?     ::..\n",
            "1 - Vendedor\n",
            "2 - Cliente\n\n",
            "3 - Sair do programa\n\n",
            ">> "
    };
    private final String[] SAIDA = {
            "..::    ATÉ MAIS!    ::.."
    };
    private final String[] AUTENTICACAO = {
            "\n:::::::::::::::::::::::::\n",
            ":::::::::::::::::::::::::\n",
            "..::  DIGITE SUA ID  ::..\n",
            ">> "
    };
    private final String[] CLIENTE = {
            "\n:::::::::::::::::::::::::\n",
            ":::::::::::::::::::::::::\n",
            "..::   OLÁ CLIENTE   ::..\n",
            "..::   Gostaria de   ::..\n",
            "1 - Ver ofertas\n",
            "2 - Ver carrinho\n\n",
            "3 - Sair do menu Cliente\n\n",
            ">> "
    };
    private final String[] VENDEDOR = {
            "\n:::::::::::::::::::::::::\n",
            ":::::::::::::::::::::::::\n",
            "..::   OLÁ VENDEDOR  ::..\n",
            "..::   Gostaria de   ::..\n",
            "1 - Ver ofertas\n",
            "2 - Ver carrinho\n\n",
            "3 - Sair do menu Cliente\n\n",
            ">> "
    };
    private final String[] PRODUTOS = {
            ":::::::::::::::::::::::::\n",
            ":::::::::::::::::::::::::\n",
            "..::    PRODUTOS     ::..\n",
            "..::   DISPONIVEIS   ::..\n\n"
    };
    private final String[] PRODUTOS_OP = {
//            ":::::::::::::::::::::::::\n",
//            ":::::::::::::::::::::::::\n\n",
            "1 - Colocar produtos\n",
            "    no seu carrinho\n",
            "2 - Sair do menu Ofertas\n\n",
            ">> "
    };
    private final String[] CARREGAR_CARRINHO = {
            ":::::::::::::::::::::::::\n",
            ":::::::::::::::::::::::::\n",
            "..:: DIGITE O NÚMERO ::..\n",
            "..::  DOS PRODUTOS   ::..\n",
            "..:: QUE VOCÊ DESEJA ::..\n",
            "..::  COLOCAR NO SEU ::..\n",
            "..::     CARRINHO    ::..\n",
            " \n<<Separe por vírgulas>>\n",
            ">> "
    };
    private final String[] COMPRAR = {
            "1 - Confirmar sua compra\n",
            "2 - Esvaziar o carrinho\n",
            "3 - Sair\n",
            ">> "
    };
    private Scanner teclado;
    private Usuario usuario;
    private ArrayList<Produto> produtos;
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");



    public static void main(String[] args) {
        Principal p = new Principal();
        p.produtos = new ArrayList<>();
        p.teclado = new Scanner(System.in);
        p.menuPrincipal();
    }


    private void menuPrincipal(){
        int opcao = -1;
        do {
            for (String linha : this.ENTRADA) System.out.print(linha);
            opcao = this.teclado.nextInt();
            switch (opcao) {
                case 1:
                    if(this.autenticar(opcao)){
                        this.menuVendedor();
                    }
                    break;
                case 2:
                    if (this.autenticar(opcao)){
                        this.menuCliente();
                    }
                    break;
                case 3:
                    for (String linha : this.SAIDA) System.out.println(linha);
                    System.exit(0);
                    break;
            }
        } while (opcao != 3);
    }


    private void menuCliente(){
        int opcao = -1;
        do {
            for (String linha : this.CLIENTE) System.out.print(linha);
            opcao = this.teclado.nextInt();
            switch (opcao) {
                case 1:
//                    System.out.println("Opção 1: ver ofertas");
                    this.menuProdutos();
                    break;
                case 2:
//                    System.out.println("Opção 2: ver carrinho");
                    if (!this.produtos.isEmpty()){
                        this.verCarrinho();
                        this.menuCarrinho();
                    }else{
                        System.out.println("\nSEU CARRINHO ESTÁ VAZIO :(");
                        this.sleep();
                    }
                    break;
                case 3:
                    for (String linha : this.SAIDA) System.out.println(linha);
                    break;
            }
        } while (opcao != 3);
    }


    private void menuProdutos(){
        for (String linha : this.PRODUTOS) System.out.print(linha);
        System.out.println(Banco.listarProdutos());
        int opcao = -1;
        do {
            for (String linha : this.PRODUTOS_OP) System.out.print(linha);
            opcao = this.teclado.nextInt();
            switch (opcao) {
                case 1:
//                    System.out.println("Opção 1: escolha produtos");
                    this.menuCarregandoCarrinho();
                    return;
                case 2:
//                    System.out.println("Opção 2: Voltar");
                    break;
            }
        } while (opcao != 2);
    }


    private void menuCarregandoCarrinho(){
        for (String linha : this.CARREGAR_CARRINHO) System.out.print(linha);;
        String pd = this.teclado.next();
        String[] pdv = pd.split(",");
        for (int i = 0; i < pdv.length; i++) {
            //verificar se produto é valido
            Produto produto = Banco.procurarProduto(Integer.parseInt(pdv[i]));
            if (produto != null) {
                this.produtos.add(produto);
            } else {
                System.out.println("INVÁLIDO: " + pdv[i]);
            }
        }
    }

    private void menuCarrinho(){
        int opcao = -1;
        do {
            for (String linha : this.COMPRAR) System.out.print(linha);
            opcao = this.teclado.nextInt();
            switch (opcao) {
                case 1:
//                    System.out.println("Opção 1: confirmar compra");
                    LocalDateTime myDateObj = LocalDateTime.now();
                    String formattedData = myDateObj.format(myFormatObj);
                    int carrinho = Banco.adicionarCarrinho(this.usuario, formattedData);
                    produtos.forEach(produto -> {
                        Banco.adicionarCompras(this.usuario, produto, carrinho);
                    });

                    return;
                case 2:
                    System.out.println("Opção 2: cancelar carrinho");
                    break;
            }
        } while (opcao != 2);
    }



    private void menuVendedor(){
        System.out.println("Menu VENDEDOR");
        System.out.println("OPÇÃO 1: VER PEDIDOS");
        System.out.println("OPÇÃO 2: SAIR");
    }


    private boolean autenticar(int cat){
        for (String linha : this.AUTENTICACAO) System.out.print(linha);
        int id = this.teclado.nextInt();
        this.usuario = Banco.procurarUsuario(cat, id);
        return (this.usuario!=null);
    }

    private void verCarrinho(){
        StringBuilder sb = new StringBuilder();
        sb.append("------------------------------------------------------\n");
        sb.append(String.format("|%-5s|%-35s|%10s|\n", "ID", "Produto", "Valor"));
        sb.append("------------------------------------------------------\n");
        produtos.forEach(p ->{
            sb.append(String.format("|%-5d|%-35s|%10.2f|\n", p.getId(), p.getNome(), p.getValor()));
        });
        sb.append("------------------------------------------------------\n");
        System.out.println(sb.toString());

    }


    private int teste(){
        int resultado = -1;
        String query = "INSERT INTO Usuario (nome, Cat_Usuario_idCat_Usuario) VALUES ("
                + "'Deborah'," + 2 + ")";

        PreparedStatement stmt = null;
        try {
            stmt = ConnectionFactory.getDBConnection().prepareStatement(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        // Executando instrução para atualizar a tabela no banco de dados
        try {
            resultado = stmt.executeUpdate(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("\nResultado = "+resultado);
        return resultado;
    }

    private void sleep(){
        try { Thread.sleep (1000); } catch (InterruptedException ex) {}
    }
}
