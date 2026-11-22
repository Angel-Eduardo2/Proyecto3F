package model;

public class ConfiguracionPorSede {

    private int idConfiguracion;
    private int idSede;
    private String clave;
    private String valor;
    private String descripcion;

    public ConfiguracionPorSede() {
    }

    public ConfiguracionPorSede(int id, int idSed,
                                String cla, String val,
                                String desc) {
        idConfiguracion = id;
        idSede = idSed;
        clave = cla;
        valor = val;
        descripcion = desc;
    }

    public int getIdConfiguracion() {
        return idConfiguracion;
    }

    public void setIdConfiguracion(int idConfiguracion) {
        this.idConfiguracion = idConfiguracion;
    }

    public int getIdSede() {
        return idSede;
    }

    public void setIdSede(int idSede) {
        this.idSede = idSede;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
