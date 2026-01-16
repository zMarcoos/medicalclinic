package com.thevelopers.model.appointment;

import com.github.f4b6a3.uuid.UuidCreator;
import com.thevelopers.model.user.User;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
public class Consultation {

  private final UUID id = UuidCreator.getTimeOrdered();
  private final List<Exam> exams = new ArrayList<>();

  @NotNull(message = "A data não pode ser nula.")
  @Future(message = "A consulta não pode ser agendada para datas anteriores.")
  private final LocalDateTime dateTime;

  @NotNull(message = "O médico não pode ser nulo.")
  private final User doctor;

  private boolean active = true;

  private String description = "Não adicionado pelo médico!";

  public void removeExam(Exam exam) {
    this.exams.remove(exam);
  }

  public void addExam(Exam exam) {
    this.exams.add(exam);
  }
}
