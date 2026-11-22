package strategy;

public class AlquilerEvento extends ModeloAlquiler {

    public AlquilerEvento() {
        super();
    }

    @Override
    public double calcularCosto(double base) {
        return base * 1.20;
    }

    @Override
    public String getNombreEstrategia() {
        return "evento";
    }
}
