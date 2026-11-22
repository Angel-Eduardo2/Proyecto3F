package model;

public abstract class Locker {

    protected String codigoIdentificador;
    protected int idSede;
    protected String tipo;
    protected String estado;
    protected String fechaUltimoMantenimiento;
    protected int cantidadVecesUsado;

    public Locker() {
    }

    public Locker(String cod, int id, String tip, String est,
                  String fecha, int usos) {
        codigoIdentificador = cod;
        idSede = id;
        tipo = tip;
        estado = est;
        fechaUltimoMantenimiento = fecha;
        cantidadVecesUsado = usos;
    }

    public abstract void verificarMantenimiento();

    public abstract String obtenerDiagnostico();

    public boolean estaDisponible() {
        return estado != null && estado.equalsIgnoreCase("disponible");
    }

    public void ocupar() {
        estado = "ocupado";
    }

    public void liberar() {
        estado = "disponible";
    }

    public void sumarUso() {
        cantidadVecesUsado++;
    }

    public String getCodigoIdentificador() {
        return codigoIdentificador;
    }

    public void setCodigoIdentificador(String codigoIdentificador) {
        this.codigoIdentificador = codigoIdentificador;
    }

    public int getIdSede() {
        return idSede;
    }

    public void setIdSede(int idSede) {
        this.idSede = idSede;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaUltimoMantenimiento() {
        return fechaUltimoMantenimiento;
    }

    public void setFechaUltimoMantenimiento(String fechaUltimoMantenimiento) {
        this.fechaUltimoMantenimiento = fechaUltimoMantenimiento;
    }

    public int getCantidadVecesUsado() {
        return cantidadVecesUsado;
    }

    public void setCantidadVecesUsado(int cantidadVecesUsado) {
        this.cantidadVecesUsado = cantidadVecesUsado;
    }
}
