package model;

public class CredencialBiometrica {

    private int idCredencial;
    private int idUsuario;
    private String pinUnicoApp;
    private String fechaRegistro;
    private String estado;

    public CredencialBiometrica() {
    }

    public CredencialBiometrica(int id, int idUsu,
                                String pin, String fecha,
                                String est) {
        idCredencial = id;
        idUsuario = idUsu;
        pinUnicoApp = pin;
        fechaRegistro = fecha;
        estado = est;
    }

    public boolean esValida() {
        return estado != null && estado.equalsIgnoreCase("activo");
    }

    public int getIdCredencial() {
        return idCredencial;
    }

    public void setIdCredencial(int idCredencial) {
        this.idCredencial = idCredencial;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getPinUnicoApp() {
        return pinUnicoApp;
    }

    public void setPinUnicoApp(String pinUnicoApp) {
        this.pinUnicoApp = pinUnicoApp;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
