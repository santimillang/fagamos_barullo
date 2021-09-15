package dev.santieinaki.fagamosbarullo.app.domain.user;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

    private String fullName;
    private String email;
    private String phoneNumber;
    private List<String> followers;
    private List<String> following;
    private List<String> instrumento;
    private List<String> fotos;
    private List<String> titulos;
    private List<String> maquetas;
    private List<String> titulosMaquetas;
    private List<String> caratulas;

    private String descripcion;
    private String id;

    public User(String fullName,
                String email,
                String phoneNumber,
                List<String> instrumento,
                String descripcion,
                String id) {

        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.instrumento = instrumento;
        this.descripcion = descripcion;
        this.id = id;
    }

    public List<String> getCaratulas() {

        return caratulas;
    }

    public void setCaratulas(List<String> caratulas) {

        this.caratulas = caratulas;
    }

    public List<String> getTitulos() {

        return titulos;
    }

    public void setTitulos(List<String> titulos) {

        this.titulos = titulos;
    }

    public String getFullName() {

        return fullName;
    }

    public void setFullName(String fullName) {

        this.fullName = fullName;
    }

    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    public String getPhoneNumber() {

        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {

        this.phoneNumber = phoneNumber;
    }

    public List<String> getFollowers() {

        return followers;
    }

    public void setFollowers(List<String> followers) {

        this.followers = followers;
    }

    public List<String> getFollowing() {

        return following;
    }

    public void setFollowing(List<String> following) {

        this.following = following;
    }

    public List<String> getInstrumento() {

        return instrumento;
    }

    public void setInstrumento(List<String> instrumento) {

        this.instrumento = instrumento;
    }

    public List<String> getFotos() {

        return fotos;
    }

    public void setFotos(List<String> fotos) {

        this.fotos = fotos;
    }

    public String getDescripcion() {

        return descripcion;
    }

    public void setDescripcion(String descripcion) {

        this.descripcion = descripcion;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

    public void addInstrumento(String instrumento) {

        this.instrumento.add(instrumento);
    }

    public void removeInstrumento(String instrumento) {

        this.instrumento.remove(instrumento);
    }

    public List<String> getMaquetas() {

        return maquetas;
    }

    public void setMaquetas(List<String> maquetas) {

        this.maquetas = maquetas;
    }

    public List<String> getTitulosMaquetas() {

        return titulosMaquetas;
    }

    public void setTitulosMaquetas(List<String> titulosMaquetas) {

        this.titulosMaquetas = titulosMaquetas;
    }
}
