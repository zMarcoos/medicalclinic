package thevelopers.devsoftware.model.user.types;

import thevelopers.devsoftware.model.user.User;

import java.util.ArrayList;
import java.util.List;

public class Doctor extends User {

    //Atributos da classe médico(Especialidade e lista de pacientes)
    private String speciality = "";
    private final List<Patient> patients = new ArrayList<>();

    /**
     * Construtor da classe médico
     * @param name Nome do médico
     * @param login Login do médico
     * @param password Senha do médico
     * @param speciality Especialidade do médico
     */
    public Doctor(String name, String login, String password, String speciality) {
        super(name, login, password);

        setSpeciality(speciality);
    }

    /**
     * Método para adicionar um paciente a lista de pacientes do médico
     * retorna os pacientes do médico
     */
    public List<Patient> getPatients() {
        return patients;
    }

    /** Método para pegar a especialidade do médico
     * @return Especialidade do médico
     */
    public String getSpeciality() {
        return speciality;
    }

    /**
     * Método para setar a especialidade do médico
     * Se a especialidade for nula ou vazia, não faz nada
     *
     * @param speciality Especialidade do médico
     */
    public void setSpeciality(String speciality) {
        if (speciality == null || speciality.isEmpty()) return;

        this.speciality = speciality;
    }
}
