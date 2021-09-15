package dev.santieinaki.fagamosbarullo.app.module.main.search.viewmodel;

public class UserModel {

    private String fName;
    private String email;
    private String id;

    private UserModel() {

    }

    public UserModel(String fName,
                     String email,
                     String id) {

        this.fName = fName;
        this.email = email;
        this.id = id;
    }

    public String getfName() {

        return fName;
    }

    public String getEmail() {

        return email;
    }

    public void setfName(String fName) {

        this.fName = fName;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = id;
    }
}
