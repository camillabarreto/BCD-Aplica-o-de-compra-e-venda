package bcd;

public class Usuario {
    private String nome;
    private int id;
    private int cat;
    private int carrinho;

    public Usuario( int id, String nome, int cat) {
        this.nome = nome;
        this.id = id;
        this.cat = cat;
    }

    public String getNome() {
        return nome;
    }

    public int getId() {
        return id;
    }

    public int getCat() {
        return cat;
    }

    public int getCarrinho() {
        return carrinho;
    }
}
