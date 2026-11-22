package model;

public class Sede {

    private int idSede;
    private int idFranquicia;
    private int capacidadTotalLockers;
    private String nombre;
    private String direccion;
    private String ciudad;
    private String fechaApertura;
    private String administradorSede;

    public Sede() {
    }

    public Sede(int id, int idFra, String nom,
                String dir, String ciu, int cap,
                String fecha, String admin) {
        idSede = id;
        idFranquicia = idFra;
        nombre = nom;
        direccion = dir;
        ciudad = ciu;
        capacidadTotalLockers = cap;
        fechaApertura = fecha;
        administradorSede = admin;
    }

    public int getIdSede() {
        return idSede;
    }

    public void setIdSede(int idSede) {
        this.idSede = idSede;
    }

    public int getIdFranquicia() {
        return idFranquicia;
    }

    public void setIdFranquicia(int idFranquicia) {
        this.idFranquicia = idFranquicia;
    }

    public int getCapacidadTotalLockers() {
        return capacidadTotalLockers;
    }

    public void setCapacidadTotalLockers(int capacidadTotalLockers) {
        this.capacidadTotalLockers = capacidadTotalLockers;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(String fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public String getAdministradorSede() {
        return administradorSede;
    }

    public void setAdministradorSede(String administradorSede) {
        this.administradorSede = administradorSede;
    }
}
