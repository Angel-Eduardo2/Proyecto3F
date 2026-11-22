package factory;

import strategy.AlquilerEvento;
import strategy.AlquilerMensual;
import strategy.AlquilerPorTiempo;
import strategy.AlquilerPorUso;
import strategy.ModeloAlquiler;

public class ModeloAlquilerFactory {

    public static ModeloAlquiler crearModelo(String tipo) {
        if (tipo == null) {
            return new AlquilerPorTiempo();
        }

        String t = tipo.trim().toLowerCase();
        switch (t) {
            case "uso":
                return new AlquilerPorUso();
            case "mensual":
                return new AlquilerMensual();
            case "evento":
                return new AlquilerEvento();
            case "tiempo":
            default:
                return new AlquilerPorTiempo();
        }
    }
}
