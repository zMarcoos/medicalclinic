package thevelopers.devsoftware.model.user.types;

import thevelopers.devsoftware.model.appointment.Consultation;
import thevelopers.devsoftware.model.appointment.MedicalRecord;
import thevelopers.devsoftware.model.user.User;

import java.util.ArrayList;
import java.util.List;

public class Patient extends User {
    //Inicializando atributos (Registro médico e consultas)
    private final MedicalRecord medicalRecord = new MedicalRecord();
    private final List<Consultation> consultations = new ArrayList<>();

    /**
     * Construtor da classe Paciente
     * @param name Nome do paciente
     * @param login Login do paciente
     * @param password Senha do paciente
     */
    public Patient(String name, String login, String password) {
        super(name, login, password);
    }

    /**
     * Método para pegar uma consulta pela data
     * Se não encontrar a consulta, procura no histórico
     * @param date Data da consulta
     * @return Consulta
     */
    public Consultation getConsultationByDate(String date) {
        return this.consultations.stream()
                .filter(consultation -> consultation.getDateAsString().equals(date))
                .findFirst()
                .orElse(
                        medicalRecord.getConsultations().stream()
                                .filter(consultation -> consultation.getDateAsString().equals(date))
                                .findFirst()
                                .orElse(null)
                );
    }

    /**
     * Método para adicionar uma consulta
     * @param consultation Consulta a ser adicionada
     */
    public void addConsultation(Consultation consultation) {
        this.consultations.add(consultation);
    }

    /**
     * Método para pegar uma consulta pelo médico
     * @param doctor Médico da consulta
     * @return Consulta
     */
    public Consultation getConsultationWithDoctor(Doctor doctor) {
        return this.consultations.stream()
                .filter(consultation -> consultation.getDoctor().equals(doctor))
                .findFirst()
                .orElse(null);
    }

    /**
     * Método para pegar as consultas do paciente
     * @return Lista de consultas ativas do paciente
     */
    public List<Consultation> getConsultations() {
        return consultations;
    }

    /**
     * Método para finalizar uma consulta
     * @param consultation Consulta a ser finalizada
     */
    public void finishConsultation(Consultation consultation) {
        for (int index = 0; index < this.consultations.size(); index++) {
            if (this.consultations.get(index).equals(consultation)) {
                consultation.setActive(false);
                consultation.getDoctor().getPatients().remove(this);

                this.medicalRecord.saveConsultation(consultation);
                this.consultations.remove(index);
                break;
            }
        }
    }

    /**
     * Método para pegar o registro médico do paciente
     * @return Registro médico do paciente
     */
    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }
}