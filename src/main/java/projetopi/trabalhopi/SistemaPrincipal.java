import javax.swing.*;

public class SistemaPrincipal {

    public static void main(String[] args) {

        GerenciadorSenha gerenciadorSenha = new GerenciadorSenha();

        while (true) {
            JOptionPane.showMessageDialog(null, "Bem-vindo ao Sistema de Gerenciamento de Senhas!");

            String[] options = {"Registrar Senha", "Listar Senhas", "Alterar Senha", "Sair"};
            int escolha = JOptionPane.showOptionDialog(
                    null,
                    "Escolha uma opção:",
                    "Menu Principal",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]);
 
            switch (escolha) {
                case 0 -> gerenciadorSenha.registrarSenha();
                case 1 -> gerenciadorSenha.listarSenhas();
                case 2 -> gerenciadorSenha.alterarSenha();
                case 3 -> {
                    JOptionPane.showMessageDialog(null, "Saindo do sistema...");
                    return;
                }
                default -> {
                    JOptionPane.showMessageDialog(null, "Opção inválida ou cancelada. Encerrando.");
                    return;
                }
            }
        }
    }
}