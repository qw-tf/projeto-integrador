import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.*;

public class GerenciadorSenha {

    private static final String PASTA_DATA = "data";
    private static final String SENHAS_FILE = PASTA_DATA + "/senhas.txt";

    public void registrarSenha() {
        String senha = JOptionPane.showInputDialog("Digite a nova senha:");

        if (senha == null || senha.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Senha inválida. Não pode ser vazia.");
            return;
        }

        File pastaData = new File(PASTA_DATA);
        if (!pastaData.exists()) {
            pastaData.mkdir();  // Cria a pasta 'data' se não existir
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SENHAS_FILE, true))) {
            writer.write(senha + "\n");
            JOptionPane.showMessageDialog(null, "Senha registrada com sucesso!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro ao registrar senha: " + e.getMessage());
        }
    }

    public void listarSenhas() {
        File arquivoSenhas = new File(SENHAS_FILE);
        if (arquivoSenhas.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(arquivoSenhas))) {
                String linha;
                StringBuilder sb = new StringBuilder();
                sb.append("Lista de senhas registradas:\n\n");
                while ((linha = reader.readLine()) != null) {
                    sb.append(linha).append("\n");
                }
                JOptionPane.showMessageDialog(null, sb.toString(), "Senhas Registradas", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Erro ao listar senhas: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Nenhuma senha encontrada.");
        }
    }

    public void alterarSenha() {
        File arquivoSenhas = new File(SENHAS_FILE);
        if (arquivoSenhas.exists()) {
            List<String> senhas = new ArrayList<>();
            boolean senhaEncontrada = false;

            String senhaAtual = JOptionPane.showInputDialog("Digite a senha atual:");
            String novaSenha = JOptionPane.showInputDialog("Digite a nova senha:");
            String confirmarSenha = JOptionPane.showInputDialog("Confirme a nova senha:");

            if (novaSenha == null || novaSenha.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nova senha inválida. Não pode ser vazia.");
                return;
            }

            if (!novaSenha.equals(confirmarSenha)) {
                JOptionPane.showMessageDialog(null, "As senhas não coincidem. Alteração cancelada.");
                return;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(arquivoSenhas))) {
                String linha;
                while ((linha = reader.readLine()) != null) {
                    if (linha.equals(senhaAtual)) {
                        senhas.add(novaSenha);
                        senhaEncontrada = true;
                    } else {
                        senhas.add(linha);
                    }
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Erro ao ler senhas: " + e.getMessage());
                return;
            }

            if (senhaEncontrada) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoSenhas))) {
                    for (String s : senhas) {
                        writer.write(s + "\n");
                    }
                    JOptionPane.showMessageDialog(null, "Senha alterada com sucesso!");
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Erro ao salvar senhas: " + e.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Senha atual não encontrada.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Arquivo de senhas não encontrado.");
        }
    }

    public int validarEntradaNumero(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            JOptionPane.showMessageDialog(null, "Entrada inválida, digite um número.");
            scanner.next();
        }
        int numero = scanner.nextInt();
        scanner.nextLine(); // Limpa o buffer
        return Math.max(numero, 0);
    }
}