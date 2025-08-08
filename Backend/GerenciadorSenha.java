
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

    /**
     * Valida o token informado para recuperação de senha.
     * Retorna true apenas se o token informado for igual ao token salvo.
     */
    public static boolean validarTokenRecuperacao(String tokenInformado) {
        String tokenSalvo = carregarToken();
        return tokenSalvo != null && tokenSalvo.equals(tokenInformado);
    }

    /**
     * Método mantido para compatibilidade. Agora delega à validação de token.
     * O parâmetro dica é ignorado nesta versão.
     */
    public static boolean validarRecuperacao(String dica, String token) {
        return validarTokenRecuperacao(token);
    }

    private static String lerArquivo(String caminho, String padrao) {
        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha = br.readLine();
            return linha != null ? linha : padrao;
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
