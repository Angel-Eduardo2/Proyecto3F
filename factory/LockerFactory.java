package factory;

import model.Locker;
import model.LockerEstandar;
import model.LockerGrande;
import model.LockerRefrigerado;

public class LockerFactory {

    public static Locker crearLocker(String tipo, String cod, int idSed,
                                     String est, String fecha, int usos) {
        if (tipo == null) {
            return new LockerEstandar(cod, idSed, est, fecha, usos);
        }

        String t = tipo.trim().toLowerCase();
        switch (t) {
            case "grande":
                return new LockerGrande(cod, idSed, est, fecha, usos);
            case "refrigerado":
                return new LockerRefrigerado(cod, idSed, est, fecha, usos);
            case "estandar":
            default:
                return new LockerEstandar(cod, idSed, est, fecha, usos);
        }
    }
}
