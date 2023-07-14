package thevelopers.devsoftware.model.user;

public class User {
    //Inicializando os atributos da classe(Nome, Login e Senha)
    private String name, login, password;

    /**
     * Construtor da classe User
     * @param name - Nome do usuário
     * @param login - Login do usuário
     * @param password - Senha do usuário
     */
    public User(String name, String login, String password) {
        setName(name);
        setLogin(login);
        setPassword(password);
    }
    /**
     * Método para pegar o nome do usuário
     * @return - Nome do usuário
     */
    public String getName() {
        return name;
    }

    /**
     * Método para setar o nome do usuário
     * Ele verifica se o nome é nulo ou vazio, se for, ele não seta o nome
     * @param name - Nome do usuário
     * @return - Nome do usuário
     */
    public void setName(String name) {
        if (name == null || name.isEmpty()) return;

        this.name = name;
    }

    /**
     * Método para pegar o login do usuário
     * @return - Login do usuário
     */
    public String getLogin() {
        return login;
    }

    /**
     * Método para setar o login do usuário
     * Ele verifica se o login é nulo ou vazio, se for, ele não seta o login
     * @param login - Login do usuário
     * @return - Login do usuário
     */
    public void setLogin(String login) {
        if (login == null || login.isEmpty()) return;

        this.login = login;
    }

    /**
     * Método para pegar a senha do usuário
     * @return - Senha do usuário
     */
    public String getPassword() {
        return password;
    }

    /**
     * Método para setar a senha do usuário
     * Ele verifica se a senha é nula ou vazia, se for, ele não seta a senha
     * @param password - Senha do usuário
     * @return - Senha do usuário
     */
    public void setPassword(String password) {
        if (password == null || password.isEmpty()) return;

        this.password = password;
    }
}