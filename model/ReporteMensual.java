package model;

public class ReporteMensual {

    private int idReporte;
    private int idSede;
    private int mes;
    private int anio;
    private int totalUsuarios;
    private int totalUsuariosActivos;
    private int totalSesiones;
    private double promedioDuracion;
    private int totalIncidencias;
    private boolean seGeneroFactura;

    public ReporteMensual() {
    }

    public ReporteMensual(int id, int idSed,
                          int mes, int anio,
                          int totUsu, int totAct,
                          int totSes, double prom,
                          int totInc, boolean fac) {
        idReporte = id;
        idSede = idSed;
        this.mes = mes;
        this.anio = anio;
        totalUsuarios = totUsu;
        totalUsuariosActivos = totAct;
        totalSesiones = totSes;
        promedioDuracion = prom;
        totalIncidencias = totInc;
        seGeneroFactura = fac;
    }

    public void mostrar() {
        System.out.println("Reporte " + mes + "/" + anio);
        System.out.println("Usuarios: " + totalUsuarios);
        System.out.println("Sesiones: " + totalSesiones);
        System.out.println("Incidencias: " + totalIncidencias);
    }

    public int getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(int idReporte) {
        this.idReporte = idReporte;
    }

    public int getIdSede() {
        return idSede;
    }

    public void setIdSede(int idSede) {
        this.idSede = idSede;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getTotalUsuarios() {
        return totalUsuarios;
    }

    public void setTotalUsuarios(int totalUsuarios) {
        this.totalUsuarios = totalUsuarios;
    }

    public int getTotalUsuariosActivos() {
        return totalUsuariosActivos;
    }

    public void setTotalUsuariosActivos(int totalUsuariosActivos) {
        this.totalUsuariosActivos = totalUsuariosActivos;
    }

    public int getTotalSesiones() {
        return totalSesiones;
    }

    public void setTotalSesiones(int totalSesiones) {
        this.totalSesiones = totalSesiones;
    }

    public double getPromedioDuracion() {
        return promedioDuracion;
    }

    public void setPromedioDuracion(double promedioDuracion) {
        this.promedioDuracion = promedioDuracion;
    }

    public int getTotalIncidencias() {
        return totalIncidencias;
    }

    public void setTotalIncidencias(int totalIncidencias) {
        this.totalIncidencias = totalIncidencias;
    }

    public boolean isSeGeneroFactura() {
        return seGeneroFactura;
    }

    public void setSeGeneroFactura(boolean seGeneroFactura) {
        this.seGeneroFactura = seGeneroFactura;
    }
}
