package com.thevelopers.model.appointment;

import com.thevelopers.model.user.User;
import com.thevelopers.model.user.role.Doctor;
import com.thevelopers.model.user.role.Patient;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class MedicalRecord {

  private final Map<UUID, Consultation> consultations = new HashMap<>();

  private final Patient patient;

  public void addConsultation(Consultation consultation) {
    this.consultations.put(consultation.getId(), consultation);
  }

  public Consultation getConsultationByDate(LocalDateTime localDateTime) {
    return this.consultations.values().stream()
      .filter(consultation -> consultation.getDateTime().equals(localDateTime))
      .findFirst()
      .orElse(null);
  }

  public Consultation getConsultationWithDoctor(User doctor) {
    return this.consultations.values().stream()
      .filter(consultation -> consultation.getDoctor().equals(doctor) && consultation.isActive())
      .findFirst()
      .orElse(null);
  }

  public void finishConsultation(Consultation consultation) {
    consultation.setActive(false);
  }
}