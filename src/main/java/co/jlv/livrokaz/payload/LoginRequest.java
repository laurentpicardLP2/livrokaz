package co.jlv.livrokaz.payload;


import javax.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank(message = "Login ne doit pas être vide")
    private String username;
    @NotBlank(message = "Mot de passe ne doit pas être vide")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
