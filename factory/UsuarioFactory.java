package factory;

import model.Asistente;
import model.Empleado;
import model.Estudiante;
import model.Socio;
import model.Usuario;
import model.Visitante;

public class UsuarioFactory {

    public static Usuario crearUsuario(String tipo, int id, int idSed, String nom,
                                       String ape, String correo, String tel,
                                       String fecha, String est) {
        if (tipo == null) {
            return new Visitante(id, idSed, nom, ape, "visitante", correo, tel, fecha, est);
        }

        String t = tipo.trim().toLowerCase();
        switch (t) {
            case "estudiante":
                return new Estudiante(id, idSed, nom, ape, "estudiante", correo, tel, fecha, est);
            case "empleado":
                return new Empleado(id, idSed, nom, ape, "empleado", correo, tel, fecha, est);
            case "socio":
                return new Socio(id, idSed, nom, ape, "socio", correo, tel, fecha, est);
            case "asistente":
                return new Asistente(id, idSed, nom, ape, "asistente", correo, tel, fecha, est);
            case "visitante":
            default:
                return new Visitante(id, idSed, nom, ape, "visitante", correo, tel, fecha, est);
        }
    }
}
