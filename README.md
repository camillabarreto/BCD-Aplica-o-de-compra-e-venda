# 2020-02-projeto-pratico-01-camillabarreto
![Diagrama ER](/diagramaER.png)


## Como executar o programa

Main.class = Principal.java

## Como usar a aplicação

Quando o programa iniciar o usuário deve selecionar o modo Cliente ou Vendedor e em seguida informar seu ID.
Veja alguns registros de ID para utilizar.

Modo   | ID
--------- | ------
Vendedor | 1
Cliente | 2

### Modo Vendedor

Entrando como Vendedor é possível visualizar todos os pedidos do vendedor registrados no banco (pendentes e não-pendentes). Para alterar o status do pedido basta informar a ID do pedido, assim mudando o status=0 para status=1. Para marcar como entregue mais que um pedido basta separar os IDs com vírgulas (ex: 1,2,3).


### Modo Usuario

Entrando como Usuario é possível visualizar todas as ofertas de produtos registrados no banco. Para colocar produtos no carrinho basta informar a ID do produto. Para selecionar um produtos mais que 1 vez basta informar o mesmo ID quantas vezes quiser. Sempre que for selecionar mais que um produto deve separar os IDs com vírgulas (ex: 1,1,1,2,3). No menu cliente tem a opção de ver o carrinho, que quando não estiver vazio mostra uma lista com os produtos selecionados e é solicitado a confirmação da compra. Após confirmar a compra o carrinho fica vazio.
