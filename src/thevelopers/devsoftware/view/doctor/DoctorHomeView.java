package thevelopers.devsoftware.view.doctor;

import thevelopers.devsoftware.controller.doctor.DoctorHomeController;
import thevelopers.devsoftware.model.SystemModel;
import thevelopers.devsoftware.utility.ClassUtility;
import thevelopers.devsoftware.utility.SwingUtility;
import thevelopers.devsoftware.view.View;

import java.awt.*;

public class DoctorHomeView extends View {
    //Atributos da classe DoctorHomeView (classe que representa a tela inicial do médico)
    private final SystemModel model;
    private final DoctorHomeController controller;

    //Construtor da classe DoctorHomeView
    public DoctorHomeView(SystemModel model) {
        this.model = model;
        this.controller = new DoctorHomeController(this.model, this);
        this.model.attachObserver(this);
    }

    //Método que constrói a tela inicial do médico
    //Esse método é chamado no construtor da classe DoctorHomeView
    //Esse método é chamado no método update da classe SystemModel
    @Override
    public void build() {
        getContentPane().setBackground(ClassUtility.BACKGROUND_BLUE_COLOR);

        SwingUtility.createImage(this, "src/" + ClassUtility.MAIN_PATH + "/assets/healthcare-social.png", 250, 130, 600, 400);
        SwingUtility.createLabel(this, "\"Conectando as pessoas à saúde,com cuidado inovação e confiança.\"", 330, 500, 500, 100, 13, null)
                .setForeground(ClassUtility.BACKGROUND_BLACK_COLOR);

        SwingUtility.createPanel(this, 200, 100, 800, 500, ClassUtility.BACKGROUND_ALMOST_WHITE_COLOR);

        SwingUtility.createImage(this, "src/" + ClassUtility.MAIN_PATH + "/assets/medical_icon_branco.png", 20, 25, 50, 50);
        SwingUtility.createLabel(this, "Medical", 80, 15, 100, 100, 25, null).setForeground(Color.white);
        SwingUtility.createButton(this, "Pacientes", 20, 150, this.controller::handleEvent);
        SwingUtility.createButton(this, "Sair", 20, 200, this.controller::handleEvent);
    }

    //Método que atualiza a tela inicial do médico
    //Esse método é chamado no método update da classe SystemModel
    //Esse método é chamado no método handleEvent da classe DoctorHomeController
    //Esse método é chamado no método handleEvent da classe DoctorPatientController
    @Override
    public void update() {

    }
}