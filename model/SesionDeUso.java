package model;

public class SesionDeUso {

    private int idSesion;
    private int idUsuario;
    private int duracionMinutos;
    private String codigoIdentificador;
    private String fechaInicio;
    private String fechaFin;
    private String metodoApertura;
    private boolean huboIncidencias;
    private double costoTotal;

    public SesionDeUso() {
    }

    public SesionDeUso(int id, int idUsu, String cod,
                       String inicio, String fin,
                       int dur, String metodo,
                       boolean inc) {
        idSesion = id;
        idUsuario = idUsu;
        codigoIdentificador = cod;
        fechaInicio = inicio;
        fechaFin = fin;
        duracionMinutos = dur;
        metodoApertura = metodo;
        huboIncidencias = inc;
        costoTotal = 0.0;
    }

    public SesionDeUso(int id, int idUsu, String cod,
                       String inicio, String fin,
                       int dur, String metodo,
                       boolean inc, double costo) {
        idSesion = id;
        idUsuario = idUsu;
        codigoIdentificador = cod;
        fechaInicio = inicio;
        fechaFin = fin;
        duracionMinutos = dur;
        metodoApertura = metodo;
        huboIncidencias = inc;
        costoTotal = costo;
    }

    public void iniciar(Usuario usu, Locker locker) {
        if (!usu.estaActivo()) {
            System.out.println("El usuario no esta activo.");
            return;
        }

        if (!locker.estaDisponible()) {
            System.out.println("El locker no esta disponible.");
            return;
        }

        locker.ocupar();
        locker.sumarUso();
        System.out.println("Sesion iniciada.");
    }

    public void terminar(Locker locker, boolean inc) {
        huboIncidencias = inc;
        if (!inc) {
            locker.liberar();
        }
        System.out.println("Sesion terminada.");
    }

    public int getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(int idSesion) {
        this.idSesion = idSesion;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(int duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public String getCodigoIdentificador() {
        return codigoIdentificador;
    }

    public void setCodigoIdentificador(String codigoIdentificador) {
        this.codigoIdentificador = codigoIdentificador;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getMetodoApertura() {
        return metodoApertura;
    }

    public void setMetodoApertura(String metodoApertura) {
        this.metodoApertura = metodoApertura;
    }

    public boolean isHuboIncidencias() {
        return huboIncidencias;
    }

    public void setHuboIncidencias(boolean huboIncidencias) {
        this.huboIncidencias = huboIncidencias;
    }

    public double getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(double costoTotal) {
        this.costoTotal = costoTotal;
    }
}
