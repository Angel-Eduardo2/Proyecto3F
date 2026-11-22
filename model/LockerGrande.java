package model;

public class LockerGrande extends Locker {

    public LockerGrande() {
        super();
        this.tipo = "grande";
    }

    public LockerGrande(String cod, int id, String est,
                        String fecha, int usos) {
        super(cod, id, "grande", est, fecha, usos);
    }

    @Override
    public void verificarMantenimiento() {
        if (estado != null && estado.equalsIgnoreCase("averiado")) {
            System.out.println("Locker grande: revisar estructura.");
        } else {
            System.out.println("Locker grande: condiciones normales.");
        }
    }

    @Override
    public String obtenerDiagnostico() {
        if (estado != null && estado.equalsIgnoreCase("averiado")) {
            return "Locker grande: revisar estructura.";
        }
        return "Locker grande: condiciones normales.";
    }
}
