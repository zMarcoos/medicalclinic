package thevelopers.devsoftware.model.appointment;

import thevelopers.devsoftware.model.user.types.Doctor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Consultation {

    // Atributos da classe Consultas (Data, Médico, Ativo, Descrição, Exames)
    private Date date;
    private Doctor doctor;
    private boolean active = true;
    private String description = "Não adicionado pelo médico!";
    private final List<Exam> exams = new ArrayList<>();

    /**
     * Construtor da classe Consulta
     * @param date
     * @param doctor
     */
    public Consultation(Date date, Doctor doctor) {
        this.date = date;
        this.doctor = doctor;
    }

    /**
     * Método para remover um exame da lista de exames
     * @param exam
     */
    public void removeExam(Exam exam) {
        this.exams.remove(exam);
    }

    /**
     * Método para adicionar um exame à lista de exames
     * @param exam
     */
    public void addExam(Exam exam) {
        this.exams.add(exam);
    }

    /**
     * Método para pegar a lista de exames
     * @return lista de exames
     */
    public List<Exam> getExams() {
        return exams;
    }

    /**
     * Método para pegar a descrição da consulta
     * @return descrição da consulta
     */
    public String getDescription() {
        return description;
    }

    /**
     * Método para definir a descrição da consulta
     * @param description
     */
    public void setDescription(String description) {
        if (description == null || description.isEmpty()) return;
        this.description = description;
    }

    /**
     * Método para pegar o status da consulta
     * @return status da consulta
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Método para definir o status da consulta
     * @param active
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Método para pegar a data da consulta como String
     * @return data da consulta como String
     */
    public String getDateAsString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return dateFormat.format(this.date);
    }

    /**
     * Método para pegar a data da consulta
     * @return data da consulta
     */
    public Date getDate() {
        return date;
    }

    /**
     * Método para definir a data da consulta
     * @param date
     */
    public void setDate(Date date) {
        if (date == null) return;
        this.date = date;
    }

    /**
     * Método para pegar o médico da consulta
     * @return médico da consulta
     */
    public Doctor getDoctor() {
        return doctor;
    }

    /**
     * Método para definir o médico da consulta
     * @param doctor
     */
    public void setDoctor(Doctor doctor) {
        if (doctor == null) return;
        this.doctor = doctor;
    }
}
