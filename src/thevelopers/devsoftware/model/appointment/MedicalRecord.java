package thevelopers.devsoftware.model.appointment;

import java.util.ArrayList;
import java.util.List;

public class MedicalRecord {

    //Atributos da classe Registro Médico (Lista de Consultas)
    private final List<Consultation> consultations = new ArrayList<>();

    /**
     * Método para salvar uma consulta no registro médico
     *
     * @param consultation Consulta a ser salva
     */
    public void saveConsultation(Consultation consultation) {
        this.consultations.add(consultation);
    }

    /**
     * Método para obter a lista de consultas do registro médico
     *
     * @return Lista de consultas do registro médico
     */
    public List<Consultation> getConsultations() {
        return consultations;
    }
}