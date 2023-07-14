package thevelopers.devsoftware.controller.doctor;

import thevelopers.devsoftware.controller.Controller;
import thevelopers.devsoftware.model.SystemModel;
import thevelopers.devsoftware.model.user.types.Patient;
import thevelopers.devsoftware.view.doctor.DoctorPatientView;
import thevelopers.devsoftware.view.doctor.DoctorPatientsView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

public class DoctorPatientsController extends Controller {

    // Atributos da classe DoctorPatientsController
    private final SystemModel model;
    private final DoctorPatientsView view;

    // Construtor da classe DoctorPatientsController
    public DoctorPatientsController(SystemModel model, DoctorPatientsView view) {
        this.model = model;
        this.view = view;
    }

    // Métodos da classe DoctorPatientsController
    // Método que inicializa o controller
    @Override
    public void handleEvent(ActionEvent event) {
        if (!(event.getSource() instanceof JButton)) return;
        JButton button = (JButton) event.getSource();
        String buttonName = button.getText();

        switch (buttonName) {
            case "Editar Paciente":
                int selectedRow = this.view.getTable().getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(this.view, "Selecione um paciente para editar.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String login = (String) this.view.getTable().getValueAt(selectedRow, 1);
                Patient patient = this.model.getPatientByLogin(login);
                if (patient == null) return;

                this.view.dispose();

                DoctorPatientView doctorPatientView = new DoctorPatientView(this.model);
                doctorPatientView.setPreviousFrame(this.view);
                doctorPatientView.setPatient(patient);
                doctorPatientView.build();
                break;
            case "Voltar":
                if (this.view.getPreviousFrame() == null) return;

                this.view.dispose();
                this.view.getPreviousFrame().setVisible(true);
                break;
        }
    }

    // Métodos da classe Controller
    @Override
    public void handleEvent(MouseEvent event) {

    }

    // Método que inicializa o controller
    @Override
    public void update() {

    }
}