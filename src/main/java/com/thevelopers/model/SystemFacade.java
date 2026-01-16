package com.thevelopers.model;

import com.thevelopers.model.user.User;
import com.thevelopers.model.user.role.Doctor;
import com.thevelopers.observer.Observer;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
public class SystemFacade {

  private final Map<String, User> users = new ConcurrentHashMap<>();
  private final Map<String, List<User>> patientsPerDoctor = new ConcurrentHashMap<>();

  private final List<Observer> observers = new ArrayList<>();

  private User currentUser;

  public SystemFacade() {
    this.users.put("marcos", new User("Gregs", "marcos", "123"));
    this.users.put("isola", new User("Isis", "isola", "123"));
    this.users.put("isis", new User("Isis Nascimento de Lavor", "isis", "123", new Doctor("Neurologista")));
    this.users.put("gregs", new User("Marcos Gregory Rodrigues", "gregs", "123", new Doctor("Cardiologista")));
    this.users.put("everton", new User("Everton da Cunha", "everton", "123", new Doctor("Psiquiatra")));
    this.users.put("denilson", new User("Denilson Cordeiro", "denilson", "123", new Doctor("Clínico Geral")));
    this.users.put("anny", new User("Anny Karolyne", "anny", "123", new Doctor("Otorrinolaringologista")));
  }

  public Optional<User> getUser(String login) {
    User user = users.get(login);
    if (user != null) return Optional.of(user);

    return Optional.empty();
  }

  public boolean hasUser(String login) {
    return this.users.containsKey(login);
  }

  public boolean hasUser(User user) {
    return hasUser(user.getLogin());
  }

  public void addUser(User user) {
    if (hasUser(user.getLogin())) return;

    this.users.put(user.getLogin(), user);
    notifyObservers();
  }

  public void authenticateUser(User user) {
    setCurrentUser(user);
    notifyObservers();
  }

  public void deauthenticateUser() {
    setCurrentUser(null);
    notifyObservers();
  }

  public void assignPatient(User doctor, User patient) {
    this.patientsPerDoctor
      .computeIfAbsent(doctor.getLogin(), _ -> new ArrayList<>())
      .add(patient);
  }

  public List<User> getPatientsByDoctor(String doctorLogin) {
    return this.patientsPerDoctor.getOrDefault(doctorLogin, new ArrayList<>());
  }

  public void attachObserver(Observer observer) {
    if (observer == null) return;
    this.observers.add(observer);
  }

  public void detachObserver(Observer observer) {
    if (observer == null) return;
    this.observers.remove(observer);
  }

  public void notifyObservers() {
    observers.forEach(Observer::update);
  }
}