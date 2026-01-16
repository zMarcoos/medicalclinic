package com.thevelopers.util;

import jakarta.validation.Validation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.Optional;
import java.util.stream.Collectors;

@UtilityClass
public class ValidatorUtil {

  private static final ValidatorFactory factory;
  private static final Validator validator;

  static {
    try {
      factory = Validation.buildDefaultValidatorFactory();
      validator = factory.getValidator();

      Runtime.getRuntime().addShutdownHook(new Thread(factory::close));
    } catch (Exception exception) {
      throw new RuntimeException("Erro ao iniciar validação: " + exception.getMessage());
    }
  }

  public static <T> Optional<String> validate(@NonNull T object) {
    var violations = validator.validate(object);
    if (violations.isEmpty()) return Optional.empty();

    String errorMessage = violations.stream()
      .map(violation -> "• " + violation.getMessage())
      .sorted()
      .collect(Collectors.joining("\n"));

    return Optional.of(errorMessage);
  }
}
