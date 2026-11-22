package model;

public class LockerEstandar extends Locker {

    public LockerEstandar() {
        super();
        this.tipo = "estandar";
    }

    public LockerEstandar(String cod, int id, String est,
                          String fecha, int usos) {
        super(cod, id, "estandar", est, fecha, usos);
    }

    @Override
    public void verificarMantenimiento() {
        if (estado != null && estado.equalsIgnoreCase("averiado")) {
            System.out.println("Locker estandar: necesita reparacion.");
        } else {
            System.out.println("Locker estandar: condiciones normales.");
        }
    }

    @Override
    public String obtenerDiagnostico() {
        if (estado != null && estado.equalsIgnoreCase("averiado")) {
            return "Locker estandar: necesita reparacion.";
        }
        return "Locker estandar: condiciones normales.";
    }
}
