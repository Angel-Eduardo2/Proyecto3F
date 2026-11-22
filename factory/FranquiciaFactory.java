package factory;

import model.Franquicia;
import model.FranquiciaAcademica;
import model.FranquiciaEmpresa;
import model.FranquiciaEvento;
import model.FranquiciaGimnasio;

public class FranquiciaFactory {

    public static Franquicia crearFranquicia(String tipo, int id, String nom,
                                             String dir, String tel, String cor,
                                             String modo, String fecha,
                                             String est, double cuota) {
        if (tipo == null) {
            return new FranquiciaEmpresa(id, nom, "empresa", dir, tel, cor, modo, fecha, est, cuota);
        }

        String t = tipo.trim().toLowerCase();
        switch (t) {
            case "academica":
                return new FranquiciaAcademica(id, nom, "academica", dir, tel, cor, modo, fecha, est, cuota);
            case "evento":
                return new FranquiciaEvento(id, nom, "evento", dir, tel, cor, modo, fecha, est, cuota);
            case "gimnasio":
                return new FranquiciaGimnasio(id, nom, "gimnasio", dir, tel, cor, modo, fecha, est, cuota);
            case "empresa":
            default:
                return new FranquiciaEmpresa(id, nom, "empresa", dir, tel, cor, modo, fecha, est, cuota);
        }
    }
}
