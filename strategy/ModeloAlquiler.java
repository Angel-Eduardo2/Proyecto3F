package strategy;

public abstract class ModeloAlquiler {

    public ModeloAlquiler() {
    }

    public abstract double calcularCosto(double base);

    public abstract String getNombreEstrategia();
}
