package thevelopers.devsoftware.view.login;

import thevelopers.devsoftware.controller.login.LoginController;
import thevelopers.devsoftware.model.SystemModel;
import thevelopers.devsoftware.utility.ClassUtility;
import thevelopers.devsoftware.utility.SwingUtility;
import thevelopers.devsoftware.view.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginView extends View {

    //Iniciando todos os atributos (classe de modelo principal, controlador e os campos)
    private final SystemModel model;
    private final LoginController controller;

    private JTextField loginField;
    private JPasswordField passwordField;

    public LoginView(SystemModel model) {
        this.model = model;
        this.controller = new LoginController(model, this);
        this.model.attachObserver(this);
    }

    //Criando a tela de login com todos os seus componentes (imagem, campos, botões e labels)
    @Override
    public void build() {
        SwingUtility.createImage(this, "src/" + ClassUtility.MAIN_PATH + "/assets/medical_icon.png", (this.getWidth() / 2) - 160, 80, 95, 95);
        SwingUtility.createLabel(this, "Medical", (this.getWidth() / 2) - 50, 130, 250, 50, 50, null);
        SwingUtility.createLabel(this, "Usuário", (this.getWidth() / 2) - 160, (this.getHeight() * 2 / 6) + 5, 300, 30, 12, null);
        SwingUtility.createLabel(this, "Senha", (this.getWidth() / 2) - 160, (this.getHeight() * 2 / 6) + 65, 300, 30, 12, null);

        this.loginField = SwingUtility.createTextField(this, "", (this.getWidth() / 2) - 160, (this.getHeight() * 2 / 6) + 30, 300, 30);
        this.passwordField = SwingUtility.createPasswordField(this, "", (this.getWidth() / 2) - 160, (this.getHeight() * 2 / 6) + 90, 300, 30);

        SwingUtility.createButton(this, "Entrar", (this.getWidth() / 2) - 160, (this.getHeight() * 3 / 6) + 50, controller::handleEvent);
        JLabel registerLabel = SwingUtility.createLabel(
                this, "<html><u>Crie uma conta</u></html>", (this.getWidth() / 2) + 40, (this.getHeight() * 3 / 6) + 50, 300, 30, 12, new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent event) {
                        controller.handleEvent(event);
                    }
                });
        registerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    //Getters e Setters (Acessores e Modificadores)
    public JTextField getLoginField() {
        return loginField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    @Override
    public void update() {
        this.loginField.setText("");
        this.passwordField.setText("");
    }
}