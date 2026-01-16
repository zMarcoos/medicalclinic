package com.thevelopers.model.user.role;

import com.thevelopers.model.appointment.MedicalRecord;
import lombok.Getter;

@Getter
public class Patient implements UserRole {

  private final MedicalRecord medicalRecord = new MedicalRecord(this);

  @Override
  public String getRoleName() {
    return "Paciente";
  }
}