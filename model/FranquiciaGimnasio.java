package model;

public class FranquiciaGimnasio extends Franquicia {

    public FranquiciaGimnasio() {
        super();
        this.tipo = "gimnasio";
    }

    public FranquiciaGimnasio(int id, String nom, String tip,
                              String dir, String tel, String cor,
                              String modo, String fecha,
                              String est, double cuota) {
        super(id, nom, "gimnasio", dir, tel, cor, modo, fecha, est, cuota);
    }

    @Override
    public double calcularCuotaMensual() {
        return cuotaMensual * 0.95;
    }
}
