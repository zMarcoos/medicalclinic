package com.thevelopers.model.user;

import com.github.f4b6a3.uuid.UuidCreator;
import com.thevelopers.model.user.role.Patient;
import com.thevelopers.model.user.role.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class User {

  @NotBlank(message = "O nome é obrigatório.")
  private String name;

  @NotBlank(message = "O login é obrigatório.")
  private String login;

  @NotBlank(message = "A senha é obrigatória.")
  private String password;

  @NotNull(message = "O cargo não pode ser nulo.")
  private UserRole role;

  public User(String name, String login, String password) {
    this.name = name;
    this.login = login;
    this.password = password;
    this.role = new Patient();
  }
}