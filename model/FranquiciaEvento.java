package model;

public class FranquiciaEvento extends Franquicia {

    public FranquiciaEvento() {
        super();
        this.tipo = "evento";
    }

    public FranquiciaEvento(int id, String nom, String tip,
                            String dir, String tel, String cor,
                            String modo, String fecha,
                            String est, double cuota) {
        super(id, nom, "evento", dir, tel, cor, modo, fecha, est, cuota);
    }

    @Override
    public double calcularCuotaMensual() {
        return cuotaMensual * 1.10;
    }
}
