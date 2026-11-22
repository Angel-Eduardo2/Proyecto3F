package model;

public abstract class Administrador {

    protected int idAdmin;
    protected int idFranquicia;
    protected String nombre;
    protected String apellido;
    protected String correo;
    protected String contrasena;
    protected String rol;
    protected String fechaUltimoAcceso;

    public Administrador() {
    }

    public Administrador(int id, int idFra, String nom, String ape,
                         String cor, String pass, String rol,
                         String fecha) {
        idAdmin = id;
        idFranquicia = idFra;
        nombre = nom;
        apellido = ape;
        correo = cor;
        contrasena = pass;
        this.rol = rol;
        fechaUltimoAcceso = fecha;
    }

    public abstract void accederPanelDeControl();

    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }

    public int getIdFranquicia() {
        return idFranquicia;
    }

    public void setIdFranquicia(int idFranquicia) {
        this.idFranquicia = idFranquicia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getFechaUltimoAcceso() {
        return fechaUltimoAcceso;
    }

    public void setFechaUltimoAcceso(String fechaUltimoAcceso) {
        this.fechaUltimoAcceso = fechaUltimoAcceso;
    }
}
