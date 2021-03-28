package bcd;

public class Produto {
    private int id;
    private String nome;
    private float valor;
    private int categoria;
    private int idVendedor;

    public Produto(int id, String nome, float valor, int categoria, int idVendedor) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.categoria = categoria;
        this.idVendedor = idVendedor;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public float getValor() {
        return valor;
    }

    public int getCategoria() {
        return categoria;
    }

    public int getIdVendedor() {
        return idVendedor;
    }
}
