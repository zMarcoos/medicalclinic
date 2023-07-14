package thevelopers.devsoftware.model;

import thevelopers.devsoftware.model.user.User;
import thevelopers.devsoftware.model.user.types.Doctor;
import thevelopers.devsoftware.model.user.types.Patient;
import thevelopers.devsoftware.observer.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SystemModel {

    //Inicializando os atributos (usuários e observadores)
    private final Map<String, User> users = new HashMap<>();
    private final List<Observer> observers = new ArrayList<>();

    private User currentUser; //Atual usuário logado

    public SystemModel() {
        this.users.put("marcos", new Patient("Gregs", "marcos", "123"));
        this.users.put("isola", new Patient("Isis", "isola", "123"));
        this.users.put("isis", new Doctor("Isis Nascimento de Lavor", "isis", "123", "Neurologia"));
        this.users.put("gregs", new Doctor("Marcos Gregory Rodrigues", "gregs", "123", "Cardiologia"));
        this.users.put("everton", new Doctor("Everton da Cunha", "everton", "123", "Psiquiatria"));
        this.users.put("denilson", new Doctor("Denilson Cordeiro", "denilson", "123", "Clínico Geral"));
        this.users.put("anny", new Doctor("Anny Karolyne", "anny", "123", "Otorrinolaringologia"));
    }

    /**
     * Método para pegar usuário caso ele exista
     * @param login Login do usuário que deseja pegar os dados
     * @param password Senha do usuário que deseja pegar os dados
     *
     * @return Retorna o usuário caso ele exista, caso contrário retorna null
     */
    public User getUser(String login, String password) {
        if (hasUser(login, password)) return users.get(login);

        return null;
    }

    /**
     * Método para verificar se o usuário existe
     * @param login Login do usuário que deseja verificar
     * @param password Senha do usuário que deseja verificar
     *
     * @return Retorna true caso o usuário exista, caso contrário retorna false
     */
    public boolean hasUser(String login, String password) {
        User user = this.users.get(login);
        return user != null && user.getPassword().equals(password);
    }

    /**
     * Método para verificar se o usuário existe
     * @param user Usuário que deseja verificar
     *
     * @return Retorna true caso o usuário exista, caso contrário retorna false
     */
    public boolean hasUser(User user) {
        return hasUser(user.getLogin(), user.getPassword());
    }

    /**
     * Método para adicionar usuário no sistema
     * @param user Usuário que deseja adicionar
     *
     */
    public void addUser(User user) {
        if (hasUser(user.getLogin(), user.getPassword())) return;

        this.users.put(user.getLogin(), user);
        notifyObservers();//Notifica os observadores
    }

    /**
     * Método para autenticar usuário no sistema
     * @param user Usuário que deseja autenticar
     *
     */
    public void authenticateUser(User user) {
        this.currentUser = user;

        notifyObservers();
    }

    /**
     * Método para desautenticar usuário no sistema
     * @param user Usuário que deseja desautenticar
     *
     */
    public void deauthenticateUser(User user) {
        this.currentUser = null;

        notifyObservers();
    }

    /**
     * Método para pegar usuário do tipo paciente pelo login
     * @param login Login do usuário que deseja pegar
     *
     * @return Retorna o usuário caso ele exista, caso contrário retorna null
     */
    public Patient getPatientByLogin(String login) {
        return (Patient) this.users
                .values()
                .stream()
                .filter(user -> user instanceof Patient && user.getLogin().equals(login))
                .findFirst()
                .orElse(null);
    }

    /**
     * Método para pegar usuário do tipo doutor pelo nome
     * @param name Nome do usuário que deseja pegar
     *
     * @return Retorna o usuário caso ele exista, caso contrário retorna null
     */
    public Doctor getDoctorByName(String name) {
        return (Doctor) this.users
                .values()
                .stream()
                .filter(user -> user instanceof Doctor && user.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    /**
     * Método para pegar todos os usuários do tipo doutor
     *
     * @return Retorna uma lista com todos os usuários do tipo doutor
     */
    public List<Doctor> getAllDoctors() {
        return this.users
                .values()
                .stream()
                .filter(user -> user instanceof Doctor)
                .map(user -> (Doctor) user)
                .collect(Collectors.toList());
    }

    /**
     * Método para adicionar observador
     * @param observer Observador que deseja adicionar
     */
    public void attachObserver(Observer observer) {
        if (observer == null) return;
        this.observers.add(observer);
    }

    /**
     * Método para remover observador
     * @param observer Observador que deseja remover
     */
    public void detachObserver(Observer observer) {
        if (observer == null) return;
        this.observers.remove(observer);
    }

    /**
     * Método para notificar os observadores
     */
    public void notifyObservers() {
        observers.forEach(Observer::update);
    }

    /**
     * Método para pegar o usuário atual logado
     *
     * @return Retorna o usuário atual logado
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Método para pegar todos os usuários registrados
     *
     * @return Retorna um mapa com todos os usuários registrados
     */
    public Map<String, User> getUsers() {
        return users;
    }
}