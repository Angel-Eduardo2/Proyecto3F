package facade;

import dao.FranquiciaDAO;
import dao.LockerDAO;
import dao.SedeDAO;
import dao.SesionDeUsoDAO;
import dao.UsuarioDAO;
import exception.DatabaseException;
import exception.ValidacionException;
import factory.FranquiciaFactory;
import factory.LockerFactory;
import factory.ModeloAlquilerFactory;
import factory.UsuarioFactory;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import model.Franquicia;
import model.Locker;
import model.Sede;
import model.SesionDeUso;
import model.Usuario;
import strategy.ModeloAlquiler;

public class LockSecureFacade {

    private final UsuarioDAO usuarioDAO;
    private final LockerDAO lockerDAO;
    private final SedeDAO sedeDAO;
    private final SesionDeUsoDAO sesionDAO;
    private final FranquiciaDAO franquiciaDAO;

    public LockSecureFacade() {
        this.usuarioDAO = new UsuarioDAO();
        this.lockerDAO = new LockerDAO();
        this.sedeDAO = new SedeDAO();
        this.sesionDAO = new SesionDeUsoDAO();
        this.franquiciaDAO = new FranquiciaDAO();
    }

    public void registrarUsuario(int id, int idSede, String nombre, String apellido,
                                 String tipo, String correo, String telefono,
                                 String fecha, String estado) throws ValidacionException, DatabaseException {
        validarUsuario(id, idSede, nombre, apellido, tipo, correo, telefono, fecha, estado);
        if (usuarioDAO.buscarPorId(id) != null) {
            throw new ValidacionException("Ya existe un usuario con el ID " + id);
        }
        Usuario u = UsuarioFactory.crearUsuario(tipo, id, idSede, nombre, apellido, correo, telefono, fecha, estado);
        usuarioDAO.insertar(u);
    }

    public void actualizarUsuario(int id, int idSede, String nombre, String apellido,
                                  String tipo, String correo, String telefono,
                                  String fecha, String estado) throws ValidacionException, DatabaseException {
        validarUsuario(id, idSede, nombre, apellido, tipo, correo, telefono, fecha, estado);
        if (usuarioDAO.buscarPorId(id) == null) {
            throw new ValidacionException("No existe el usuario con el ID " + id);
        }
        Usuario u = UsuarioFactory.crearUsuario(tipo, id, idSede, nombre, apellido, correo, telefono, fecha, estado);
        usuarioDAO.actualizar(u);
    }

    public void eliminarUsuario(int id) throws ValidacionException, DatabaseException {
        if (id <= 0) {
            throw new ValidacionException("El ID debe ser mayor a cero.");
        }
        if (usuarioDAO.buscarPorId(id) == null) {
            throw new ValidacionException("No se encontro el usuario con ID " + id);
        }
        usuarioDAO.eliminar(id);
    }

    public Usuario buscarUsuario(int id) throws DatabaseException {
        return usuarioDAO.buscarPorId(id);
    }

    public List<Usuario> listarUsuarios() throws DatabaseException {
        return usuarioDAO.listarTodos();
    }

    public double calcularTarifaUsuario(int idUsuario, double base) throws ValidacionException, DatabaseException {
        Usuario u = usuarioDAO.buscarPorId(idUsuario);
        if (u == null) {
            throw new ValidacionException("Usuario no encontrado.");
        }
        return u.calcularTarifa(base);
    }

    public void registrarLocker(String codigo, int idSede, String tipo, String estado,
                                String fecha, int usos) throws ValidacionException, DatabaseException {
        validarLocker(codigo, idSede, tipo, estado, fecha, usos);
        if (lockerDAO.buscarPorId(codigo) != null) {
            throw new ValidacionException("Ya existe un locker con el codigo " + codigo);
        }
        Locker l = LockerFactory.crearLocker(tipo, codigo, idSede, estado, fecha, usos);
        lockerDAO.insertar(l);
    }

    public void actualizarLocker(String codigo, int idSede, String tipo, String estado,
                                 String fecha, int usos) throws ValidacionException, DatabaseException {
        validarLocker(codigo, idSede, tipo, estado, fecha, usos);
        if (lockerDAO.buscarPorId(codigo) == null) {
            throw new ValidacionException("No existe un locker con el codigo " + codigo);
        }
        Locker l = LockerFactory.crearLocker(tipo, codigo, idSede, estado, fecha, usos);
        lockerDAO.actualizar(l);
    }

    public void eliminarLocker(String codigo) throws ValidacionException, DatabaseException {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new ValidacionException("El codigo del locker no puede estar vacio.");
        }
        if (lockerDAO.buscarPorId(codigo) == null) {
            throw new ValidacionException("No se encontro el locker con codigo " + codigo);
        }
        lockerDAO.eliminar(codigo);
    }

    public Locker buscarLocker(String codigo) throws DatabaseException {
        return lockerDAO.buscarPorId(codigo);
    }

    public List<Locker> listarLockers() throws DatabaseException {
        return lockerDAO.listarTodos();
    }

    public String diagnosticarLocker(String codigo) throws ValidacionException, DatabaseException {
        Locker l = lockerDAO.buscarPorId(codigo);
        if (l == null) {
            throw new ValidacionException("Locker no encontrado.");
        }
        l.verificarMantenimiento();
        return l.obtenerDiagnostico();
    }

    public void registrarSede(int id, int idFranquicia, String nombre, String direccion,
                              String ciudad, int capacidad, String fecha,
                              String admin) throws ValidacionException, DatabaseException {
        if (id <= 0) {
            throw new ValidacionException("El ID de sede debe ser mayor a cero.");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ValidacionException("El nombre de la sede es obligatorio.");
        }
        if (capacidad <= 0) {
            throw new ValidacionException("La capacidad debe ser mayor a cero.");
        }
        if (sedeDAO.buscarPorId(id) != null) {
            throw new ValidacionException("Ya existe la sede con ID " + id);
        }
        Sede s = new Sede(id, idFranquicia, nombre, direccion, ciudad, capacidad, fecha, admin);
        sedeDAO.insertar(s);
    }

    public void actualizarSede(int id, int idFranquicia, String nombre, String direccion,
                               String ciudad, int capacidad, String fecha,
                               String admin) throws ValidacionException, DatabaseException {
        if (id <= 0) {
            throw new ValidacionException("El ID de sede debe ser mayor a cero.");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ValidacionException("El nombre de la sede es obligatorio.");
        }
        if (capacidad <= 0) {
            throw new ValidacionException("La capacidad debe ser mayor a cero.");
        }
        if (sedeDAO.buscarPorId(id) == null) {
            throw new ValidacionException("No existe la sede con ID " + id);
        }
        Sede s = new Sede(id, idFranquicia, nombre, direccion, ciudad, capacidad, fecha, admin);
        sedeDAO.actualizar(s);
    }

    public void eliminarSede(int id) throws ValidacionException, DatabaseException {
        if (sedeDAO.buscarPorId(id) == null) {
            throw new ValidacionException("No se encontro la sede con ID " + id);
        }
        sedeDAO.eliminar(id);
    }

    public List<Sede> listarSedes() throws DatabaseException {
        return sedeDAO.listarTodos();
    }

    public SesionDeUso iniciarSesion(int idUsuario, String codigoLocker, String tipoModeloAlquiler,
                                     String metodoApertura, double tarifaBase) throws ValidacionException, DatabaseException {
        if (tarifaBase <= 0) {
            throw new ValidacionException("La tarifa base debe ser mayor a cero.");
        }

        Usuario usuario = usuarioDAO.buscarPorId(idUsuario);
        if (usuario == null) {
            throw new ValidacionException("El usuario con ID " + idUsuario + " no existe.");
        }
        if (!usuario.estaActivo()) {
            throw new ValidacionException("El usuario no se encuentra activo.");
        }

        Locker locker = lockerDAO.buscarPorId(codigoLocker);
        if (locker == null) {
            throw new ValidacionException("El locker " + codigoLocker + " no existe.");
        }
        if (!locker.estaDisponible()) {
            throw new ValidacionException("El locker " + codigoLocker + " no esta disponible (Estado actual: " + locker.getEstado() + ").");
        }

        ModeloAlquiler estrategia = ModeloAlquilerFactory.crearModelo(tipoModeloAlquiler);
        double tarifaUsuario = usuario.calcularTarifa(tarifaBase);
        double costoTotal = estrategia.calcularCosto(tarifaUsuario);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechaInicio = sdf.format(new Date());

        SesionDeUso sesion = new SesionDeUso(0, idUsuario, codigoLocker, fechaInicio, null, 0, metodoApertura, false, costoTotal);
        sesion.iniciar(usuario, locker);

        lockerDAO.actualizar(locker);
        sesionDAO.insertar(sesion);
        return sesion;
    }

    public void terminarSesion(int idSesion, boolean huboIncidencias) throws ValidacionException, DatabaseException {
        SesionDeUso sesion = sesionDAO.buscarPorId(idSesion);
        if (sesion == null) {
            throw new ValidacionException("No existe la sesion con ID " + idSesion);
        }

        Locker locker = lockerDAO.buscarPorId(sesion.getCodigoIdentificador());
        if (locker != null) {
            sesion.terminar(locker, huboIncidencias);
            lockerDAO.actualizar(locker);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sesion.setFechaFin(sdf.format(new Date()));
        sesion.setHuboIncidencias(huboIncidencias);
        sesionDAO.actualizar(sesion);
    }

    public double calcularCostoEstimado(int idUsuario, String tipoModeloAlquiler, double tarifaBase) throws ValidacionException, DatabaseException {
        if (tarifaBase <= 0) {
            throw new ValidacionException("La tarifa base debe ser mayor a cero.");
        }
        Usuario usuario = usuarioDAO.buscarPorId(idUsuario);
        if (usuario == null) {
            throw new ValidacionException("Usuario no encontrado.");
        }
        ModeloAlquiler estrategia = ModeloAlquilerFactory.crearModelo(tipoModeloAlquiler);
        double tarifaUsuario = usuario.calcularTarifa(tarifaBase);
        return estrategia.calcularCosto(tarifaUsuario);
    }

    public List<SesionDeUso> listarSesiones() throws DatabaseException {
        return sesionDAO.listarTodos();
    }

    public void eliminarSesion(int id) throws DatabaseException {
        sesionDAO.eliminar(id);
    }

    public List<Franquicia> listarFranquicias() throws DatabaseException {
        return franquiciaDAO.listarTodos();
    }

    public double calcularCuotaFranquicia(int idFranquicia) throws ValidacionException, DatabaseException {
        Franquicia f = franquiciaDAO.buscarPorId(idFranquicia);
        if (f == null) {
            throw new ValidacionException("Franquicia no encontrada.");
        }
        return f.calcularCuotaMensual();
    }

    private void validarUsuario(int id, int idSede, String nombre, String apellido,
                                String tipo, String correo, String telefono,
                                String fecha, String estado) throws ValidacionException {
        if (id <= 0) {
            throw new ValidacionException("El ID de usuario debe ser un numero positivo mayor a cero.");
        }
        if (idSede <= 0) {
            throw new ValidacionException("El ID de sede debe ser un numero positivo mayor a cero.");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ValidacionException("El nombre es un campo obligatorio.");
        }
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new ValidacionException("El apellido es un campo obligatorio.");
        }
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new ValidacionException("El tipo de usuario es obligatorio.");
        }
        if (correo == null || !correo.contains("@") || !correo.contains(".")) {
            throw new ValidacionException("El correo electronico ingresado no tiene un formato valido.");
        }
        if (telefono == null || telefono.trim().length() < 7) {
            throw new ValidacionException("El telefono debe tener al menos 7 digitos.");
        }
        if (fecha == null || fecha.trim().isEmpty()) {
            throw new ValidacionException("La fecha de registro es obligatoria.");
        }
        if (estado == null || estado.trim().isEmpty()) {
            throw new ValidacionException("El estado es obligatorio.");
        }
    }

    private void validarLocker(String codigo, int idSede, String tipo, String estado,
                               String fecha, int usos) throws ValidacionException {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new ValidacionException("El codigo identificador del locker es obligatorio.");
        }
        if (idSede <= 0) {
            throw new ValidacionException("El ID de sede debe ser mayor a cero.");
        }
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new ValidacionException("El tipo de locker es obligatorio.");
        }
        if (estado == null || estado.trim().isEmpty()) {
            throw new ValidacionException("El estado del locker es obligatorio.");
        }
        if (fecha == null || fecha.trim().isEmpty()) {
            throw new ValidacionException("La fecha del ultimo mantenimiento es obligatoria.");
        }
        if (usos < 0) {
            throw new ValidacionException("La cantidad de usos no puede ser negativa.");
        }
    }
}
