package thevelopers.devsoftware;

import thevelopers.devsoftware.model.SystemModel;
import thevelopers.devsoftware.view.login.LoginView;

import javax.swing.*;

public class MedicalClinic {

    //Iniciando a primeira tela do sistema
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginView(new SystemModel()).build());
    }
}
