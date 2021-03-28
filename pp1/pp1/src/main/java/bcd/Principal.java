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
            "1 - Ver pedidos\n\n",
            "2 - Sair do menu Vendedor\n\n",
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
            "3 - Sair do menu Carrinho\n",
            ">> "
    };
    private final String[] PEDIDOS = {
            ":::::::::::::::::::::::::\n",
            ":::::::::::::::::::::::::\n",
            "..::   SEUS PEDIDOS  ::..\n",
            "1 - Entregar pedidos\n",
            "2 - Sair do menu Pedidos\n",
            ">> "
    };
    private final String[] ENTREGA = {
            "\n:::::::::::::::::::::::::\n",
            ":::::::::::::::::::::::::\n",
            "..:: DIGITE O NÚMERO ::..\n",
            "..::   DOS PEDIDOS   ::..\n",
            "..:: QUE VOCÊ DESEJA ::..\n",
            "..::     ENTREGAR    ::..\n",
            " \n<<Separe por vírgulas>>\n",
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
                    }else System.out.println("ID INVÁLIDO"); this.sleep();
                    break;
                case 2:
                    if (this.autenticar(opcao)){
                        this.menuCliente();
                    }else System.out.println("ID INVÁLIDO"); this.sleep();
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
                    this.menuProdutos();
                    break;
                case 2:
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
                    this.menuCarregandoCarrinho();
                    return;
                case 2:
                    break;
            }
        } while (opcao != 2);
    }


    private void menuCarregandoCarrinho(){
        for (String linha : this.CARREGAR_CARRINHO) System.out.print(linha);
        String pd = this.teclado.next();
        StringBuilder sb = new StringBuilder();
        String[] pdv = pd.split(",");
        for (int i = 0; i < pdv.length; i++) {
            Produto produto = Banco.procurarProduto(Integer.parseInt(pdv[i]));
            if (produto != null) {
                this.produtos.add(produto);
            } else {
                sb.append("\nINVÁLIDO: " + pdv[i]);
            }
        }
        if(sb.length()>0) System.out.println(sb.toString()); this.sleep();
    }

    private void menuCarrinho(){
        int opcao = -1;
        do {
            for (String linha : this.COMPRAR) System.out.print(linha);
            opcao = this.teclado.nextInt();
            switch (opcao) {
                case 1:
                    LocalDateTime myDateObj = LocalDateTime.now();
                    String formattedData = myDateObj.format(myFormatObj);
                    int carrinho = Banco.adicionarCarrinho(this.usuario, formattedData);
                    produtos.forEach(produto -> {
                        Banco.adicionarCompras(this.usuario, produto, carrinho);
                    });
                    this.produtos.clear();
                    return;
                case 2:
                    this.produtos.clear();
                    return;
                case 3:
                    break;
            }
        } while (opcao != 3);
    }


    private void menuVendedor(){
        int opcao = -1;
        do {
            for (String linha : this.VENDEDOR) System.out.print(linha);
            opcao = this.teclado.nextInt();
            switch (opcao) {
                case 1:
                    StringBuilder s = Banco.listarPedidos(usuario.getId());
                    if(s!=null){
                        System.out.println(s.toString());
                        this.menuPedidos();
                    }else{
                        System.out.println("VOCÊ NÃO TEM PEDIDOS");
                        this.sleep();
                    }
                    break;
                case 2:
                    for (String linha : this.SAIDA) System.out.print(linha);
                    break;
            }
        } while (opcao != 2);
    }

    private void menuPedidos(){
        int opcao = -1;
        do {
            for (String linha : this.PEDIDOS) System.out.print(linha);
            opcao = this.teclado.nextInt();
            switch (opcao) {
                case 1:
                    this.menuEntregas();
                    break;
                case 2:
                    break;
            }
        } while (opcao != 2);
    }

    private void menuEntregas(){
        for (String linha : this.ENTREGA) System.out.print(linha);
        String pd = this.teclado.next();
        StringBuilder sb = new StringBuilder();
        String[] pdv = pd.split(",");
        for (int i = 0; i < pdv.length; i++) {
            boolean resultado = Banco.marcarEntrega(Integer.parseInt(pdv[i]),usuario.getId());
            if(!resultado) sb.append("\nINVÁLIDO: " + pdv[i]);
        }
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

    private void sleep(){
        try { Thread.sleep (1500); } catch (InterruptedException ex) {}
    }
}
