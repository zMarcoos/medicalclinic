package thevelopers.devsoftware.controller.patient;

import thevelopers.devsoftware.controller.Controller;
import thevelopers.devsoftware.model.SystemModel;
import thevelopers.devsoftware.model.appointment.Consultation;
import thevelopers.devsoftware.model.user.types.Doctor;
import thevelopers.devsoftware.model.user.types.Patient;
import thevelopers.devsoftware.utility.DateUtility;
import thevelopers.devsoftware.view.patient.PatientAppointmentView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PatientAppointmentController extends Controller {

    //Atributos da classe (model e view)
    private final SystemModel model;
    private final PatientAppointmentView view;

    /**
     * Construtor da classe
     * @param model - modelo do sistema
     * @param view - view de registro
     */
    public PatientAppointmentController(SystemModel model, PatientAppointmentView view) {
        this.model = model;
        this.view = view;
    }

    /**
     * Método que  trata os eventos da view
     * @param event - evento
     */
    @Override
    public void handleEvent(ActionEvent event) {
        if (!(event.getSource() instanceof JButton)) return;
        JButton button = (JButton) event.getSource();
        String buttonName = button.getText();

        switch (buttonName) {
            case "Agendar":
                String doctorNameWithSpeciality = (String) this.view.getDoctorsSelection().getSelectedItem();
                if (doctorNameWithSpeciality == null) {
                    JOptionPane.showMessageDialog(this.view, "Selecione um médico!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String doctorName = doctorNameWithSpeciality.substring(0, doctorNameWithSpeciality.lastIndexOf(" "));
                Doctor doctor = this.model.getDoctorByName(doctorName);

                Patient patient = (Patient) this.model.getCurrentUser();
                if (patient.getConsultationByDate(this.view.getDateField().getText()) != null) {
                    JOptionPane.showMessageDialog(this.view, "Você já possui uma consulta agendada para esta data!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (patient.getConsultationWithDoctor(doctor) != null) {
                    JOptionPane.showMessageDialog(this.view, "Você já possui uma consulta agendada com este médico!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!DateUtility.isDateValid(this.view.getDateField().getText())) {
                    JOptionPane.showMessageDialog(this.view, "Data inválida!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                try {
                    patient.addConsultation(new Consultation(
                            dateFormat.parse(this.view.getDateField().getText()),
                            doctor
                    ));

                    doctor.getPatients().add(patient);

                    JOptionPane.showMessageDialog(this.view, "Consulta agendada com Dr(a). " + this.view.getDoctorsSelection().getSelectedItem(), "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } catch (ParseException exception) {
                    JOptionPane.showMessageDialog(this.view, "Ocorreu um erro não esperado. Consulte algum administrador para ver este erro!", "Erro", JOptionPane.ERROR_MESSAGE);
                    System.out.println("Ocorreu um erro ao adicionar a consulta do paciente " + patient.getName());
                }

                this.view.dispose();
                this.view.getPreviousFrame().setVisible(true);
                break;
            case "Voltar":
                if (this.view.getPreviousFrame() == null) return;

                this.view.dispose();
                this.view.getPreviousFrame().setVisible(true);
                break;
        }
    }

    //Método que trata os eventos do mouse
    @Override
    public void handleEvent(MouseEvent event) {

    }

    //Método que atualiza a view
    @Override
    public void update() {

    }
}
