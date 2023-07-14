package thevelopers.devsoftware.view.patient;

import thevelopers.devsoftware.controller.patient.PatientHomeController;
import thevelopers.devsoftware.model.SystemModel;
import thevelopers.devsoftware.utility.ClassUtility;
import thevelopers.devsoftware.utility.SwingUtility;
import thevelopers.devsoftware.view.View;

import java.awt.*;

public class PatientHomeView extends View {

    // Atributos da classe PatientHomeView
    private final SystemModel model;
    private final PatientHomeController controller;

    // Construtor da classe PatientHomeView
    public PatientHomeView(SystemModel model) {
        this.model = model;
        this.controller = new PatientHomeController(this.model, this);
        this.model.attachObserver(this);
    }

    // Métodos da classe PatientHomeView
    @Override
    public void build() {
        getContentPane().setBackground(ClassUtility.BACKGROUND_BLUE_COLOR);

        SwingUtility.createImage(this, "src/" + ClassUtility.MAIN_PATH + "/assets/schedule.jpg", 250, 130, 600, 400);
        SwingUtility.createLabel(this, "\"Conectando as pessoas à saúde,com cuidado inovação e confiança.\"", 330, 500, 500, 100, 13, null)
                .setForeground(ClassUtility.BACKGROUND_BLACK_COLOR);

        SwingUtility.createPanel(this, 200, 100, 800, 500, ClassUtility.BACKGROUND_ALMOST_WHITE_COLOR);

        SwingUtility.createImage(this, "src/" + ClassUtility.MAIN_PATH + "/assets/medical_icon_branco.png", 20, 25, 50, 50);
        SwingUtility.createLabel(this, "Medical", 80, 15, 100, 100, 25, null).setForeground(Color.white);
        SwingUtility.createButton(this, "Agendar", 20, 150, this.controller::handleEvent);
        SwingUtility.createButton(this, "Consultas", 20, 200, this.controller::handleEvent);
        SwingUtility.createButton(this, "Sair", 20, 250, this.controller::handleEvent);
    }

    // Métodos da classe View
    // Método update
    @Override
    public void update() {

    }
}