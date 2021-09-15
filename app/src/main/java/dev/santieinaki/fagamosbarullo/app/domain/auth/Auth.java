package dev.santieinaki.fagamosbarullo.app.domain.auth;

public class Auth {

    private String id;
    private String email;

    public Auth(String id) {

        this.id = id;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }
}
