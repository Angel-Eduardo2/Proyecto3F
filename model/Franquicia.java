package model;

public abstract class Franquicia {

    protected int idFranquicia;
    protected String nombre;
    protected String tipo;
    protected String direccion;
    protected String telefono;
    protected String correoContacto;
    protected String modoOperacion;
    protected String fechaAlta;
    protected String estado;
    protected double cuotaMensual;

    public Franquicia() {
    }

    public Franquicia(int id, String nom, String tip,
                      String dir, String tel, String cor,
                      String modo, String fecha, String est,
                      double cuota) {
        idFranquicia = id;
        nombre = nom;
        tipo = tip;
        direccion = dir;
        telefono = tel;
        correoContacto = cor;
        modoOperacion = modo;
        fechaAlta = fecha;
        estado = est;
        cuotaMensual = cuota;
    }

    public abstract double calcularCuotaMensual();

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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoContacto() {
        return correoContacto;
    }

    public void setCorreoContacto(String correoContacto) {
        this.correoContacto = correoContacto;
    }

    public String getModoOperacion() {
        return modoOperacion;
    }

    public void setModoOperacion(String modoOperacion) {
        this.modoOperacion = modoOperacion;
    }

    public String getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(String fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getCuotaMensual() {
        return cuotaMensual;
    }

    public void setCuotaMensual(double cuotaMensual) {
        this.cuotaMensual = cuotaMensual;
    }
}
