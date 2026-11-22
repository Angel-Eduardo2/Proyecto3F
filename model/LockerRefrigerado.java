package model;

public class LockerRefrigerado extends Locker {

    public LockerRefrigerado() {
        super();
        this.tipo = "refrigerado";
    }

    public LockerRefrigerado(String cod, int id, String est,
                             String fecha, int usos) {
        super(cod, id, "refrigerado", est, fecha, usos);
    }

    @Override
    public void verificarMantenimiento() {
        if (estado != null && estado.equalsIgnoreCase("averiado")) {
            System.out.println("Locker refrigerado: revisar refrigeracion.");
        } else {
            System.out.println("Locker refrigerado: refrigeracion normal.");
        }
    }

    @Override
    public String obtenerDiagnostico() {
        if (estado != null && estado.equalsIgnoreCase("averiado")) {
            return "Locker refrigerado: revisar refrigeracion.";
        }
        return "Locker refrigerado: refrigeracion normal.";
    }
}
