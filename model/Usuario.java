package model;

public abstract class Usuario {

    protected int idUsuario;
    protected int idSede;
    protected String nombre;
    protected String apellido;
    protected String tipo;
    protected String correoElectronico;
    protected String telefono;
    protected String fechaRegistro;
    protected String estado;

    public Usuario() {
    }

    public Usuario(int id, int idSed, String nom, String ape, String tip,
                   String correo, String tel, String fecha, String est) {
        idUsuario = id;
        idSede = idSed;
        nombre = nom;
        apellido = ape;
        tipo = tip;
        correoElectronico = correo;
        telefono = tel;
        fechaRegistro = fecha;
        estado = est;
    }

    public abstract double calcularTarifa(double base);

    public boolean estaActivo() {
        return estado != null && estado.equalsIgnoreCase("activo");
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdSede() {
        return idSede;
    }

    public void setIdSede(int idSede) {
        this.idSede = idSede;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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
