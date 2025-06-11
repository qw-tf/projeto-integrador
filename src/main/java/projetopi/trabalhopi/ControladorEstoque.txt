//importando bibliotecas para usar listas e scanner
import java.util.InputMismatchException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import excessoes.*;
@SuppressWarnings("resource")
//supress warning usado para calar a boca do ide sobre scanner nao fechados;
//senao ele da o erro java.util.NoSuchElementException
public class ControladorDeEstoque {
    // classe verificador para verificar respostas
    Verificador verificador = new Verificador();

    // cria uma instancia de controlador para dar ao construtor do manipulador de arquivo
    private ControladorDeEstoque controlador;
    private GerarLogs rlog = new GerarLogs();
    ManipularArquivo arquivo = new ManipularArquivo("produtos.csv", controlador);
    // cria uma nova lista para guardar os produtos e salvar corretamente
    private List<Produto> produtos = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);

    // get da lista ja que ela é privada
    public List<Produto> getProdutos() {
        return produtos;
    }

    // para adicionar um produto
    public void adicionarProduto() {

        // esse while é usado para repetir essa sessao do zero
        //toda vez que o usuario digitar algo invalido
        while (true) {
            try {
                String nome, descricao = "", dataDeValidade;
                int quantidade, limiteEstoque;
                double preco;
                boolean descricaoAdd; // booleano para checar se a descricao foi adicionada
                descricaoAdd = false; // setta ela como falsa a cada repeticao para funcionar corretamente
                rlog.logAutomatic("Adicionando Produto");
                System.out.println("Qual o nome do produto? (0 para sair)");
                //checa primeiro se o usuario quer sair do loop
                nome = scanner.nextLine();
                if (nome.equals("0")) {
                    System.out.println("Saindo...");
                    rlog.logAutomatic("Operacao Cancelada pelo usuario");
                    return; // se sim, sai do metodo imetiadamente.
                }
                    verificador.verificarNome(nome); // chama classe verificador para validar resposta e entra nos catchs se for invalida
                    System.out.println("Qual o limite de estoque desse produto?");
                    limiteEstoque = scanner.nextInt();
                    verificador.verificarLimiteDeEstoque(limiteEstoque, 0); //usa classe verificador para checar resposta
                    System.out.println("Quanto tem desse produto?");
                    quantidade = scanner.nextInt();
                    verificador.verificarQuantidade(quantidade, limiteEstoque);//usa classe verificador para checar resposta
                    scanner.nextLine(); // consumir quebra de linha
                    System.out.println("Qual o preco desse produto?");
                    preco = scanner.nextDouble();
                    scanner.nextLine(); // consumir quebra de linha
                    verificador.verificarPreco(preco);//usa classe verificador para checar resposta

                    System.out.println("Quer adicionar uma descricao ao produto? s/n");
                    String opcao = scanner.next();
                    verificador.verificarResposta(opcao);//usa classe verificador para checar resposta
                    if (opcao.charAt(0) == 's') {
                        System.out.println("Pode digitar a descricao do produto: ");
                        scanner.nextLine(); // consumir quebra de linha
                        descricao = scanner.nextLine();
                        descricaoAdd = true;
                        verificador.verificarNome(descricao);//usa classe verificador para checar resposta
                    }

                    // aqui ele diverge entre criar um produto perecivel ou nao
                    System.out.println("O produto é perecivel ou nao? s/n");
                    opcao = scanner.next();
                    verificador.verificarResposta(opcao); // valida se a resposta é "s" ou "n"
                    scanner.nextLine(); // consumir quebra de linha
                    if (opcao.charAt(0) == 's') {
                        System.out.println("Escreva a data de validade do produto (dd/mm/yyyy):");
                        dataDeValidade = scanner.nextLine();
                        verificador.verificarDataValidade(dataDeValidade);
                        // cria a instancia do produto perecivel
                        Produto prod1 = new ProdutoPerecivel(nome, quantidade, preco, dataDeValidade, limiteEstoque);
                        if (descricaoAdd) { // adiciona a descricao previamente colocada pelo usuario somente se
                                            // "descricaoAdd" for verdadeira
                            prod1.setDescricao(descricao);
                        }
                        produtos.add(prod1);
                        rlog.logAutomatic("Produto cadastrado com sucesso!");
                        return;
                    }
                    Produto prod1 = new Produto(nome, quantidade, preco, limiteEstoque); // cria a instancia do produto
                    // comum
                    if (descricaoAdd) {
                        prod1.setDescricao(descricao);
                    }
                    produtos.add(prod1);
                    rlog.logAutomatic("Produto cadastrado ");
                    return;
                
                // catchs usados para lançar a mensagem de erro e reiniciar o while do começo
                //caso o usuario quiser cancelar o adicionar produto
            } catch (InvalidNameException e) {
                System.out.println(e.getMessage());
                continue;
            } catch (InvalidPrecoException e) {
                System.out.println(e.getMessage());
                continue;
            } catch(DateTimeParseException e){
                System.out.println("Data invalida!");
                continue;
            } catch (InvalidQuantidadeException e) {
                System.out.println(e.getMessage());
                scanner.nextLine(); // consumir quebra de linha
                continue;
            } catch (LimiteEstoqueException e) {
                System.out.println(e.getMessage());
                scanner.nextLine(); // consumir quebra de linha
                continue;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                scanner.nextLine(); // consumir quebra de linha
                continue;
            } catch (ProdutoVencidoException e) {
                System.out.println(e.getMessage());
                continue;
            } catch (InputMismatchException e) {
                System.out.println("Erro, escreva o numero corretamente!");
                scanner.nextLine(); // consumir quebra de linha
                continue;
            } catch (Exception e) {
                System.out.println("Erro.");
                scanner.nextLine(); // consumir quebra de linha
                continue;
            }
        }
    }

    //metodo para excluir produtos da lista
    public void excluirProduto() {
        int codigo; // para identificar o codigo do produto que ira ser excluido
		Scanner scanner = new Scanner(System.in);
        while(true){ //while para repetir esse segmneto caso o usuario der um valor invalido
            try {
                rlog.logAutomatic("Lendo Arquivo");
                verificador.verificarLista(produtos);//checa se a lista de produtos esta vazia, se sim
                                                    //joga uma excessao que fecha o metodo
                rlog.logAutomatic("Removendo produto");
                for(Produto p : produtos){//imprime os produtos cadastrados para o usuario escolher um pelo codigo
                    System.out.println("Codigo: " + p.getCodigo() + ", Nome: " + p.getNome());
                } 
                System.out.println("Digite o codigo do produto que quer excluir ou '0' para sair: ");
                codigo = scanner.nextInt();
                scanner.nextLine();
                if (codigo == 0) {
                    rlog.logAutomatic("Operacao Cancelada pelo usuario");
                    System.out.println("Saindo...");
                    return;
                } else {
                    verificador.verificarCodigo(codigo); //verifica se o codigo dado esta entre 1000 e 9999
    
                    Iterator<Produto> iterator = produtos.iterator(); // iterator para percorrer pela lista e remover os
                                                                      // itens corretamente
                    while (iterator.hasNext()) {
                        Produto produto = iterator.next();
                        if (produto.getCodigo() == codigo) {
                            System.out.println("Tem certeza que quer excluir (" + produto.getNome() +") (s/n)?");
                            String opcao = scanner.nextLine();
                            verificador.verificarResposta(opcao); //um ultimo check para ter certeza que o
                                                                //usuario realmente quer remover o item
                            if(opcao.equals("n")){
                                System.out.println("Saindo...");
                                rlog.logAutomatic("Remocao Cancelada!");
                                return;
                            }
                            iterator.remove(); // Remove o produto da lista
                            System.out.println("Produto removido com sucesso!");
                            rlog.logAutomatic("Produto removido");
                            return;
                        }
                    }
                }
                System.out.println("Produto com esse codigo nao existe!"); //caso ele de um codigo que nao esta atrelaçado com um produto ex:(9999)
                rlog.logAutomatic("Produto nao encontrado");        
    
                //catchs para mandar a mensagem de erro e recomeçar o while
            } catch(InvalidListaException e){
                System.out.println(e.getMessage());
                return;
            } catch (InvalidCodigoException e) {
                System.out.println(e.getMessage());
                continue;
            } catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
                continue;
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
                continue;
            }
        }
        }

    public void aumentarLista(Produto produto) {
        produtos.add(produto); // metodo para aumentar a lista privada
    }

    public void imprimirProdutos(){
        rlog.logAutomatic("Lendo Arquivo");
        try{
            verificador.verificarLista(produtos); //primeiro verifica se a lista esta vazia
                                                //se sim, sai do metodo imediatamente
        }catch(InvalidListaException e){
            System.out.println(e.getMessage());
            rlog.logAutomatic("Erro");
            return;
        }
        rlog.logAutomatic("Imprimindo o Estoque");
        for(Produto p : produtos){
            if(p instanceof ProdutoPerecivel){ // checa se faz parte da subclasse de produtos pereciveis
                                            //se sim, imprime uma mensagem levemente diferente (data de validade adicionada)
                ProdutoPerecivel perecivel = (ProdutoPerecivel) p;
                System.out.print("Codigo: " + perecivel.getCodigo() + ", Nome: " + perecivel.getNome()
                    + ", Limite de Estoque: " + perecivel.getLimiteDeEstoque()
                    + ", Quantidade: " + perecivel.getQuantidade()
                    + ", Preco: " + perecivel.getPreco()
                    + ", Data de Validade: " + perecivel.getDataDeValidade()); //imprime a data de validade caso perecivel
                    if(perecivel.getDescricao() != null && !perecivel.getDescricao().isBlank()){ // se descricao nao for vazia, imprime 
                        System.out.print(",Descricao: " + perecivel.getDescricao());
                    }
                    System.out.println(); // para ter certeza que pula linha no final
            }
            else{ //caso for um produto normal (nao perecivel)
                System.out.print("Codigo: " + p.getCodigo() + ", Nome: " + p.getNome()
                    + ", Limite de Estoque: " + p.getLimiteDeEstoque()
                    + ", Quantidade: " + p.getQuantidade()
                    + ", Preco: " + p.getPreco());
                    if(p.getDescricao() != null && !p.getDescricao().isBlank()){// se descricao nao for vazia, imprime 
                        System.out.print(", Descricao: " + p.getDescricao());
                    }
                    System.out.println();// para ter certeza que pula linha no final
            }
        }
        rlog.logAutomatic("Estoque impresso");
    }
    //cheque automatico de estoque baixo toda vez que o sistema é iniciado
    public void checarEstoqueBaixo(Produto p){
        int estoqueBaixo = (int) (p.getLimiteDeEstoque() * 0.2);
        if(p.getQuantidade() == 0){ //checa se o produto acabou
            System.out.println("Aviso! Produto (" + p.getNome() + ") acabou!");
        } else if(p.getQuantidade() <= estoqueBaixo){ //dps checa se esta abaixo de 20% do limite
            System.out.println("Aviso! Produto (" + p.getNome() + ") esta quase acabando!");
        }

    }
    
    public boolean verificarDisponibilidade(Produto produto, int quantidade) {
        if (produto.getQuantidade() >= quantidade) {
            return true;
        } else {
            return false;
        }
    }

    public void removerProduto(String nomeProduto, int quantidade) throws InvalidQuantidadeException {
        Produto produto = buscarProduto(nomeProduto); // Supondo que exista um método para buscar o produto pelo nome
        if (produto != null) {
            int novaQuantidade = produto.getQuantidade() - quantidade;
            produto.setQuantidade(novaQuantidade);
            System.out.println("Produto " + nomeProduto + " atualizado no estoque. Nova quantidade: " + novaQuantidade);
        }
    }

    public Produto buscarProduto(String nomeProduto) {
        for (Produto produto : produtos) { // temos lista percorrer
            if (produto.getNome().equalsIgnoreCase(nomeProduto)) { // Compara o nome do produto
                return produto;
            }
        }
        return null; // Se não encontrar o produto, retorna null
    }
    
    public void atualizarNome() { // metodo para mudar o nome de um produto
        try{
            rlog.logAutomatic("Lendo Arquivo");
            verificador.verificarLista(produtos); //primeiro verifica se a lista esta vazia, se sim, sai do metodo imediatamente
        }
        catch(InvalidListaException e){
            System.out.println(e.getMessage());
            rlog.logAutomatic("Erro");
            return;
        }
       
        System.out.println("Digite o codigo do produto que quer renomear ou '0' para sair: ");
        for(Produto p : produtos){
                System.out.println("Codigo: " + p.getCodigo() + ", Nome: " + p.getNome());
            }
        Scanner scanner = new Scanner(System.in);
        boolean produtoEncontrado = false;//booleano para confirmar se codigo dado realmente é de um produto cadastrado
        rlog.logAutomatic("Renomeando produto");
        int codigo = scanner.nextInt();
        scanner.nextLine(); //consome quebra de linha
        try {
            if (codigo == 0) { // checa se o usuario quer sair
                System.out.println("Saindo...");
                rlog.logAutomatic("Operacao Cancelada pelo usuario");
            } else {
                verificador.verificarCodigo(codigo); // verifica se o codigo esta entre 1000 e 9999
                for (Produto produto : produtos) {
                    if (produto.getCodigo() == codigo) {
                        System.out.println("Tem certeza que quer renomear (" + produto.getNome() +") (s/n)?");
                        //ultimo check caso o usuario mude de ideia
                        String opcao = scanner.nextLine();
                        verificador.verificarResposta(opcao); // verifica se a resposta eh "s" ou "n"
                        if(opcao.equals("n")){
                            System.out.println("Saindo...");
                            rlog.logAutomatic("Atualizacao Cancelada!");
                            return;
                        }
                        produtoEncontrado = true; // muda o booleano caso encontrado
                        rlog.logAutomatic("Atualizando nome");
                        System.out.println("Digite um novo nome para o(a) (" + produto.getNome() +")");
                        String nome = scanner.nextLine();
                        verificador.verificarNome(nome); // verifica se o nome dado eh nulo ou apenas espacos
                        produto.setNome(nome); // atualiza o nome do produto
                        System.out.println("Nome atualizado com sucesso!");
                        rlog.logAutomatic("Nome atualizado");
                        break; // produto encontrado, sai do loop
                    }
                }
                if (!produtoEncontrado) { // caso nao encontrado, o codigo nao esta atrelacado a nenhum produto cadastrado
                    System.out.println("Produto com esse código nao existe!");
                    rlog.logAutomatic("Produto nao encontrado");
                }
            }
        } catch (InvalidCodigoException e) {
            System.out.println(e.getMessage());
        } catch (InvalidNameException e) {
            System.out.println(e.getMessage());
        }
    }

    public void atualizarQuantidade() { // metdo para reabastecer ou mudar a quantiade de um produto.
        try{
            rlog.logAutomatic("Lendo Arquivo");
            verificador.verificarLista(produtos); // verifica se a lista de produtos esta vazia
                                                // se sim, sai do metodo
        }
        catch(InvalidListaException e){
            System.out.println(e.getMessage());
            rlog.logAutomatic("Erro");
            return;
        }
        for(Produto p : produtos){ //imprime os produtos existentes para o usuario saber oq esta cadastrado
            System.out.println("Codigo: " + p.getCodigo() + ", Nome: " + p.getNome() + ", Quantidade: " + p.getQuantidade());
        }
        System.out.println("Digite o codigo do produto que quer atualizar quantiade ou '0' para sair: "); 
        Scanner scanner = new Scanner(System.in); 
        boolean produtoEncontrado = false; // para verificar se o usuario deu um codigo de produto nao existente
        int codigo = scanner.nextInt();
        rlog.logAutomatic("Atualizando Quantidade no estoque");
        scanner.nextLine(); //consome quebra de linha
        try {
            if (codigo == 0) { //checa se o usuario quis cancelar a operacao
                System.out.println("Saindo...");
                rlog.logAutomatic("Operacao cancelada pelo usuario");
            } else {
                verificador.verificarCodigo(codigo); // verifica se o codigo esta entre 1000 e 9999
                for (Produto produto : produtos) { //percorre a lista de produtos
                    if (produto.getCodigo() == codigo) {
                        System.out.println("Tem certeza que quer mudar quantidade de (" + produto.getNome() +") (s/n)?");
                        //ultima chance de cancelar a operacao
                        String opcao = scanner.nextLine();
                        verificador.verificarResposta(opcao);
                        if(opcao.equals("n")){
                            System.out.println("Saindo...");
                            rlog.logAutomatic("Atualizacao Cancelada!");
                            return;
                        }
                        produtoEncontrado = true; //produto encontrado, muda o booleano
                        System.out.println("Digite a nova quantidade: ");
                        int quantidade = scanner.nextInt(); //recebe nova quantidade
                        verificador.verificarQuantidade(quantidade, produto.getLimiteEstoque()); //checa se a quantidade passada é valida
                        produto.setQuantidade(quantidade); // atualiza a quantidade do produto
                        System.out.println("Estoque atualizado com sucesso!");
                        break; // quantidade atualizada, sai do loop
                    }
                }
                if (!produtoEncontrado) { // caso produto nao tenha sido encontrado, o codigo passado nao estava atrelaçado
                                        // a  nenhum produto ex: (9999)
                    System.out.println("Produto com esse código nao existe!");
                    rlog.logAutomatic("Produto nao encontrado");
                }
            }
        } catch (InvalidCodigoException e) {
            System.out.println(e.getMessage());
        } catch (InvalidQuantidadeException e) {
            System.out.println(e.getMessage());
        }
    }

    public void atualizarPreco() { // metdo para mudar o preco de um produto.
        try{
            rlog.logAutomatic("Lendo Arquivo");
            verificador.verificarLista(produtos); // verifica se existem produtos cadastrados
        }
        catch(InvalidListaException e){
            System.out.println(e.getMessage()); // se não, ele sai do metodo
            rlog.logAutomatic("Erro");
            return;
        }
        for(Produto p : produtos){//imprime os produtos cadastrados para o usuario escolher um pelo codigo
            System.out.println("Codigo: " + p.getCodigo() + ", Nome: " + p.getNome() + ", Preco: " + p.getPreco());
        } 
        System.out.println("Digite o codigo do produto que quer mudar o preco ou '0' para sair: ");
        Scanner scanner = new Scanner(System.in);
        boolean produtoEncontrado = false; // booleano para checar se o produto foi encontrado
        rlog.logAutomatic("Atualizando Preco");
        int codigo = scanner.nextInt();
        scanner.nextLine(); //consome quebra de linha
        try {
            if (codigo == 0) { //checa se o usuario quer sair
                System.out.println("Saindo...");
                rlog.logAutomatic("Operacao cancelada pelo usuario");
            } else {
                verificador.verificarCodigo(codigo); //checa se o codigo esta entre 1000 e 9999
                for (Produto produto : produtos) {
                    if (produto.getCodigo() == codigo) {
                        System.out.println("Tem certeza que quer mudar o preco de (" + produto.getNome() +") (s/n)?");
                        String opcao = scanner.nextLine();
                        verificador.verificarResposta(opcao);// verifica se a resposta foi "s" ou "n"
                        if(opcao.equals("n")){
                            System.out.println("Saindo...");
                            rlog.logAutomatic("Atualizacao Cancelada!");
                            return;
                        }
                        produtoEncontrado = true; //produto encontrado, atualizado booleano
                        System.out.println("Digite um novo preço para este produto: ");
                        double preco = scanner.nextDouble();
                        verificador.verificarPreco(preco);// verifica se o valor dado é valido
                        produto.setPreco(preco); // atualiza o preço do produto
                        System.out.println("Preco atualizado com sucesso!");
                        break; // preço atualizado,  sai do loop
                    }
                }
                if (!produtoEncontrado) { // se produto nao for encontrado, codigo dado foi de um produto que nao esta cadastrado
                    System.out.println("Produto com esse código nao existe!");
                    rlog.logAutomatic("Produto nao encontrado");
                }
            }
        } catch (InvalidCodigoException e) {
            System.out.println(e.getMessage());
        } catch (InvalidPrecoException e) {
            System.out.println(e.getMessage());
        }
    }

    public void atualizarLimite() { // metdo para mudar o limite de um produto.
        try{
            rlog.logAutomatic("Lendo Arquivo");
            verificador.verificarLista(produtos); // verifica se existem produtos cadastrados
        }
        catch(InvalidListaException e){
            System.out.println(e.getMessage()); // se não, ele sai do metodo
            rlog.logAutomatic("Erro");
            return;
        }
        rlog.logAutomatic("Atualizando limite de estoque");
        for(Produto p : produtos){//imprime os produtos cadastrados para o usuario escolher um pelo codigo
            System.out.println("Codigo: " + p.getCodigo() + ", Nome: " + p.getNome()
            + ", Limite de Estoque: " + p.getLimiteDeEstoque());
        }
        System.out.println("Digite o codigo do produto que quer mudar o limite de estoque ou '0' para sair: ");
        Scanner scanner = new Scanner(System.in);
        boolean produtoEncontrado = false;// booleano para checar se o produto foi encontrado
        int codigo = scanner.nextInt();
        scanner.nextLine();//consome quebra de linha
        try {
            if (codigo == 0) {//checa se o usuario quer sair
                System.out.println("Saindo...");
                rlog.logAutomatic("Operacao cancelada pelo usuario");
            } else {
                verificador.verificarCodigo(codigo);//checa se o codigo esta entre 1000 e 9999
                for (Produto produto : produtos) {
                    if (produto.getCodigo() == codigo) {
                        System.out.println("Tem certeza que quer mudar limite de (" + produto.getNome() +") (s/n)?");
                        String opcao = scanner.nextLine();
                        verificador.verificarResposta(opcao);// verifica se a resposta foi "s" ou "n"
                        if(opcao.equals("n")){
                            rlog.logAutomatic("Atualizacao Cancelada!");
                            System.out.println("Saindo...");
                            return;
                        }
                        produtoEncontrado = true; //produto encontrado, atualizado booleano
                        System.out.println("Digite um novo limite de estoque para este produto: ");
                        int limiteEstoque = scanner.nextInt();
                        verificador.verificarLimiteDeEstoque(limiteEstoque, produto.getQuantidade()); // verifica se o valor dado é valido
                        produto.setLimiteDeEstoque(limiteEstoque);// atualiza o limite de estoque do produto
                        System.out.println("Limite de estoque atualizado com sucesso!");
                        rlog.logAutomatic("Limite de estoque atualizado");
                        break; // limite de estoque atualizado, sai do loop
                    }
                }
                if (!produtoEncontrado) {
                    System.out.println("Produto com esse código nao existe!");
                    rlog.logAutomatic("Produto nao encontrado!");
                }
            }
        } catch (InvalidCodigoException e) {
            System.out.println(e.getMessage());
        } catch (LimiteEstoqueException e) {
            System.out.println(e.getMessage());
        }
    }
     public void atualizarDescricao() { // metdo para mudar a descricao de um produto.
        try{
            rlog.logAutomatic("Lendo o Arquivo");
            verificador.verificarLista(produtos);// verifica se existem produtos cadastrados
        }
        catch(InvalidListaException e){
            System.out.println(e.getMessage());// se não, ele sai do metodo
            rlog.logAutomatic("Erro");
            return;
        }
        for(Produto p : produtos){//imprime os produtos cadastrados para o usuario escolher um pelo codigo
            System.out.println("Codigo: " + p.getCodigo() + ", Nome: " + p.getNome() + "Descricao: " + p.getDescricao());
        }
        System.out.println("Digite o codigo do produto que quer mudar a descricao ou '0' para sair: ");
        Scanner scanner = new Scanner(System.in);
        boolean produtoEncontrado = false;// booleano para checar se o produto foi encontrado
        int codigo = scanner.nextInt();
        scanner.nextLine(); //consome quebra de linha
        try {
            if (codigo == 0) {//checa se o codigo esta entre 1000 e 9999
                System.out.println("Saindo...");
                rlog.logAutomatic("Operacao cancelada pelo usuario");
            } else {
                verificador.verificarCodigo(codigo);
                for (Produto produto : produtos) {
                    if (produto.getCodigo() == codigo) {
                        System.out.println("Tem certeza que quer mudar descricao de (" + produto.getNome() +") (s/n)?");
                        String opcao = scanner.nextLine();
                        verificador.verificarResposta(opcao);// verifica se a resposta foi "s" ou "n"
                        if(opcao.equals("n")){
                            System.out.println("Saindo...");
                            rlog.logAutomatic("Atualizacao Cancelada!");
                            return;
                        }
                        produtoEncontrado = true;//produto encontrado, atualizado booleano
                        System.out.println("Digite uma nova descricao para este produto: ");
                        String descricao = scanner.nextLine();
                        verificador.verificarNome(descricao);// verifica se o valor dado é valido
                        produto.setDescricao(descricao); // atualiza a descrição do produto
                        System.out.println("Descricao atualizada com sucesso!");
                        rlog.logAutomatic("Descricao atualizada!");
                        break; // descriçao atualizada, sai do loop
                    }
                }
                if (!produtoEncontrado) {// se produto nao for encontrado, codigo dado foi de um produto que nao esta cadastrado
                    System.out.println("Produto com esse código nao existe!");
                    rlog.logAutomatic("Produto nao encontrado");
                }
            }
        } catch (InvalidCodigoException e) {
            System.out.println(e.getMessage());
        } catch (InvalidNameException e) {
            System.out.println(e.getMessage());
        }
    }
    public void atualizarDataDeValidade() {// metdo para mudar o preco de um produto.
        try{
            rlog.logAutomatic("Lendo Arquivo");
            verificador.verificarLista(produtos);// verifica se existem produtos cadastrados
        }
        catch(InvalidListaException e){
            System.out.println(e.getMessage());// se não, ele sai do metodo
            rlog.logAutomatic("Erro");
            return;
        }
        boolean produtoEncontrado = false; // booleano para checar se o produto foi encontrado
        for(Produto p : produtos){//imprime os produtos cadastrados para o usuario escolher um pelo codigo
            if(p instanceof ProdutoPerecivel){ // mas ele so imprime se o produto for uma instancia dos produtos pereciveis
                ProdutoPerecivel perecivel = (ProdutoPerecivel) p;
                System.out.println("Codigo: " + perecivel.getCodigo() + ", Nome: " + perecivel.getNome()
                    + ", Data de Validade: " + perecivel.getDataDeValidade());
                    produtoEncontrado = true; // muda o booleano caso for encontrado
            }
        }
        if(!produtoEncontrado){ // checa se foi encontrado algum produto perecivel
            System.out.println("Nao tem nenhum produto perecivel para mudar!");
            rlog.logAutomatic("Nao tem produtos pereciveis");
            return; // se nao, sai do metodo
        }

        // se foi encontrado, ele roda a parte do codigo que muda a data de validade
        System.out.println("Digite o codigo do produto que quer mudar a data de validade ou '0' para sair: ");
        Scanner scanner = new Scanner(System.in);
        produtoEncontrado = false; // muda o booleano para falso de novo para checks futuros
                                //basicamente reutilizando a mesma variavel 
        int codigo = scanner.nextInt();
        scanner.nextLine();
        try {
            if (codigo == 0) { //checa se o usuario quer sair do metodo
                System.out.println("Saindo...");
                rlog.logAutomatic("Operacao cancelada pelo usuario");
            } else {
                verificador.verificarCodigo(codigo);//verifica se o codigo esta entre 1000 e 9999
                for (Produto produto : produtos) {
                    if (produto instanceof ProdutoPerecivel) { // verifica se é perecivel
                        ProdutoPerecivel produtoPerecivel = (ProdutoPerecivel) produto; //ja que obrigatoriamente é, faz o cast para mudar
                                                                                        // a instancia "produto" para "produtoPerecivel"
                        if (produtoPerecivel.getCodigo() == codigo) { //verifica se o codigo existe na lista de produtos
                            System.out.println("Tem certeza que quer mudar data de validade de (" + produto.getNome() +") (s/n)?");
                            String opcao = scanner.nextLine(); 
                            verificador.verificarResposta(opcao);// verifica se a resposta foi "s" ou "n"

                            if(opcao.equals("n")){ //verifica se quer sair
                                System.out.println("Saindo...");
                                rlog.logAutomatic("Atualizacao Cancelada!");
                                return;
                            }
                            produtoEncontrado = true;//produto encontrado, atualizado booleano
                            System.out.println("Digite uma nova data de validade para este produto:(dd/mm/yyyy)");
                            String dataDeValidade = scanner.nextLine(); //recebe do usuario
                            verificador.verificarDataValidade(dataDeValidade);// verifica se o valor dado é valido
                            produtoPerecivel.setDataDeValidade(dataDeValidade); // atualiza a data de validade  do produto
                            System.out.println("Data de validade atualizado com sucesso!");
                            rlog.logAutomatic("Data de validade atualizada");
                            break; // data atualizada,  sai do loop
                        }
                    }
                }
                if (!produtoEncontrado) {// se produto nao for encontrado, codigo dado foi de um produto que nao esta cadastrado
                    System.out.println("Produto com esse código nao existe!");
                    rlog.logAutomatic("Produto nao encontrado");
                }
            }
        } catch (InvalidCodigoException e) {
            System.out.println(e.getMessage());
        } catch (ProdutoVencidoException e) {
            System.out.println(e.getMessage());
        }
    }

    public void exibirProdutosDisponiveis() {
        System.out.println("\n--- Produtos Disponíveis ---");
        if (produtos.isEmpty()) {
            System.out.println("Não há produtos disponíveis.");
        } else {
            for (Produto produto : produtos) {
                if(!(produto.getQuantidade() <= 0)){
                    System.out.println(produto.getNome() + " - Preço: " + produto.getPreco() + " | Quantidade: " + produto.getQuantidade());
                }
            }
        }
    }

}