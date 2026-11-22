package strategy;

public class AlquilerPorTiempo extends ModeloAlquiler {

    public AlquilerPorTiempo() {
        super();
    }

    @Override
    public double calcularCosto(double base) {
        return base;
    }

    @Override
    public String getNombreEstrategia() {
        return "tiempo";
    }
}
