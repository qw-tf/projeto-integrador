// Backend/GerenciadorSenha.java
package Backend;

import java.io.*;

public class GerenciadorSenha {

    private static final String ARQUIVO_SENHA = "senha.txt";
    private static final String ARQUIVO_DICA = "dica.txt";
    private static final String ARQUIVO_TOKEN = "token.txt";

    public static String carregarSenha() {
        return lerArquivo(ARQUIVO_SENHA, "0000");
    }

    public static void salvarSenha(String novaSenha) {
        escreverArquivo(ARQUIVO_SENHA, novaSenha);
    }

    public static void salvarDica(String dica) {
        escreverArquivo(ARQUIVO_DICA, dica);
    }

    public static void salvarToken(String token) {
        escreverArquivo(ARQUIVO_TOKEN, token);
    }

    public static String carregarDica() {
        return lerArquivo(ARQUIVO_DICA, "");
    }

    public static String carregarToken() {
        return lerArquivo(ARQUIVO_TOKEN, "");
    }

    public static boolean validarRecuperacao(String dica, String token) {
        String dicaSalva = lerArquivo(ARQUIVO_DICA, "");
        String tokenSalvo = lerArquivo(ARQUIVO_TOKEN, "");

        return (dica != null && dica.equals(dicaSalva)) ||
                (token != null && token.equals(tokenSalvo));
    }

    private static String lerArquivo(String caminho, String padrao) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            return br.readLine();
        } catch (IOException e) {
            return padrao;
        }
    }

    private static void escreverArquivo(String caminho, String conteudo) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminho))) {
            bw.write(conteudo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
