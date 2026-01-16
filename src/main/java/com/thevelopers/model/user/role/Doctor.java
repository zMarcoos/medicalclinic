package com.thevelopers.model.user.role;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Doctor implements UserRole {

  @NotBlank(message = "A especialidade é obrigatória!")
  private String speciality;

  public Doctor(String speciality) {
    setSpeciality(speciality);
  }

  @Override
  public String getRoleName() {
    return "Médico(a)";
  }
}
