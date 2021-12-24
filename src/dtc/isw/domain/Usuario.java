package dtc.isw.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class Usuario implements Serializable {
    String username;
    String password;
    String correo;
    int puntos;

    public Usuario(String username, String password, String correo, int puntos) {
        this.username = username;
        this.password = password;
        this.correo = correo;
        this.puntos = puntos;
    }

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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
}
