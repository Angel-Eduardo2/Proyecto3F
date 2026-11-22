package view;

import exception.DatabaseException;
import exception.ValidacionException;
import facade.LockSecureFacade;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.Locker;
import model.Sede;
import model.SesionDeUso;
import model.Usuario;

public class VentanaPrincipal extends JFrame {

    private final LockSecureFacade facade;

    private JTextField txtIdUsuario;
    private JTextField txtIdSedeUsuario;
    private JTextField txtNombreUsuario;
    private JTextField txtApellidoUsuario;
    private JComboBox<String> cmbTipoUsuario;
    private JTextField txtCorreoUsuario;
    private JTextField txtTelefonoUsuario;
    private JTextField txtFechaUsuario;
    private JComboBox<String> cmbEstadoUsuario;
    private JTable tblUsuarios;
    private DefaultTableModel dtmUsuarios;

    private JTextField txtCodigoLocker;
    private JTextField txtIdSedeLocker;
    private JComboBox<String> cmbTipoLocker;
    private JComboBox<String> cmbEstadoLocker;
    private JTextField txtFechaLocker;
    private JTextField txtUsosLocker;
    private JTable tblLockers;
    private DefaultTableModel dtmLockers;

    private JTextField txtIdUsuarioSesion;
    private JTextField txtCodigoLockerSesion;
    private JComboBox<String> cmbModeloAlquiler;
    private JTextField txtTarifaBase;
    private JComboBox<String> cmbMetodoApertura;
    private JCheckBox chkIncidencias;
    private JTable tblSesiones;
    private DefaultTableModel dtmSesiones;

    private JTextField txtIdSede;
    private JTextField txtIdFranquiciaSede;
    private JTextField txtNombreSede;
    private JTextField txtDireccionSede;
    private JTextField txtCiudadSede;
    private JTextField txtCapacidadSede;
    private JTextField txtFechaSede;
    private JTextField txtAdminSede;
    private JTable tblSedes;
    private DefaultTableModel dtmSedes;

    public VentanaPrincipal() {
        this.facade = new LockSecureFacade();
        setTitle("LOCKSECURE - Sistema de Gestion de Lockers Inteligentes");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
        cargarTodosLosDatos();
    }

    private void initUI() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Usuarios", crearPanelUsuarios());
        tabbedPane.addTab("Lockers", crearPanelLockers());
        tabbedPane.addTab("Sesiones de Uso (Alquiler)", crearPanelSesiones());
        tabbedPane.addTab("Sedes", crearPanelSedes());
        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel crearPanelUsuarios() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridLayout(5, 4, 8, 8));
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos del Usuario"));

        txtIdUsuario = new JTextField();
        txtIdSedeUsuario = new JTextField("1");
        txtNombreUsuario = new JTextField();
        txtApellidoUsuario = new JTextField();
        cmbTipoUsuario = new JComboBox<>(new String[]{"estudiante", "empleado", "visitante", "socio", "asistente"});
        txtCorreoUsuario = new JTextField();
        txtTelefonoUsuario = new JTextField();
        txtFechaUsuario = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        cmbEstadoUsuario = new JComboBox<>(new String[]{"activo", "inactivo"});

        formPanel.add(new JLabel("ID Usuario:"));
        formPanel.add(txtIdUsuario);
        formPanel.add(new JLabel("ID Sede:"));
        formPanel.add(txtIdSedeUsuario);

        formPanel.add(new JLabel("Nombre:"));
        formPanel.add(txtNombreUsuario);
        formPanel.add(new JLabel("Apellido:"));
        formPanel.add(txtApellidoUsuario);

        formPanel.add(new JLabel("Tipo:"));
        formPanel.add(cmbTipoUsuario);
        formPanel.add(new JLabel("Correo Electronico:"));
        formPanel.add(txtCorreoUsuario);

        formPanel.add(new JLabel("Telefono:"));
        formPanel.add(txtTelefonoUsuario);
        formPanel.add(new JLabel("Fecha Registro:"));
        formPanel.add(txtFechaUsuario);

        formPanel.add(new JLabel("Estado:"));
        formPanel.add(cmbEstadoUsuario);
        formPanel.add(new JLabel(""));
        formPanel.add(new JLabel(""));

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnRegistrar = new JButton("Registrar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");

        btnPanel.add(btnRegistrar);
        btnPanel.add(btnActualizar);
        btnPanel.add(btnEliminar);
        btnPanel.add(btnLimpiar);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(btnPanel, BorderLayout.SOUTH);

        dtmUsuarios = new DefaultTableModel(new String[]{"ID", "Sede", "Nombre", "Apellido", "Tipo", "Correo", "Telefono", "Fecha", "Estado"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblUsuarios = new JTable(dtmUsuarios);
        tblUsuarios.getSelectionModel().addListSelectionListener(e -> {
            int row = tblUsuarios.getSelectedRow();
            if (row >= 0) {
                txtIdUsuario.setText(dtmUsuarios.getValueAt(row, 0).toString());
                txtIdSedeUsuario.setText(dtmUsuarios.getValueAt(row, 1).toString());
                txtNombreUsuario.setText(dtmUsuarios.getValueAt(row, 2).toString());
                txtApellidoUsuario.setText(dtmUsuarios.getValueAt(row, 3).toString());
                cmbTipoUsuario.setSelectedItem(dtmUsuarios.getValueAt(row, 4).toString());
                txtCorreoUsuario.setText(dtmUsuarios.getValueAt(row, 5).toString());
                txtTelefonoUsuario.setText(dtmUsuarios.getValueAt(row, 6).toString());
                txtFechaUsuario.setText(dtmUsuarios.getValueAt(row, 7).toString());
                cmbEstadoUsuario.setSelectedItem(dtmUsuarios.getValueAt(row, 8).toString());
            }
        });

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(tblUsuarios), BorderLayout.CENTER);

        btnRegistrar.addActionListener(e -> accionRegistrarUsuario());
        btnActualizar.addActionListener(e -> accionActualizarUsuario());
        btnEliminar.addActionListener(e -> accionEliminarUsuario());
        btnLimpiar.addActionListener(e -> limpiarFormularioUsuario());

        return panel;
    }

    private JPanel crearPanelLockers() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridLayout(3, 4, 8, 8));
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos del Locker"));

        txtCodigoLocker = new JTextField();
        txtIdSedeLocker = new JTextField("1");
        cmbTipoLocker = new JComboBox<>(new String[]{"estandar", "grande", "refrigerado"});
        cmbEstadoLocker = new JComboBox<>(new String[]{"disponible", "ocupado", "averiado"});
        txtFechaLocker = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        txtUsosLocker = new JTextField("0");

        formPanel.add(new JLabel("Codigo:"));
        formPanel.add(txtCodigoLocker);
        formPanel.add(new JLabel("ID Sede:"));
        formPanel.add(txtIdSedeLocker);

        formPanel.add(new JLabel("Tipo:"));
        formPanel.add(cmbTipoLocker);
        formPanel.add(new JLabel("Estado:"));
        formPanel.add(cmbEstadoLocker);

        formPanel.add(new JLabel("Ultimo Mantenimiento:"));
        formPanel.add(txtFechaLocker);
        formPanel.add(new JLabel("Cantidad Usos:"));
        formPanel.add(txtUsosLocker);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnRegistrar = new JButton("Registrar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnDiagnostico = new JButton("Verificar Mantenimiento");
        JButton btnLimpiar = new JButton("Limpiar");

        btnPanel.add(btnRegistrar);
        btnPanel.add(btnActualizar);
        btnPanel.add(btnEliminar);
        btnPanel.add(btnDiagnostico);
        btnPanel.add(btnLimpiar);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(btnPanel, BorderLayout.SOUTH);

        dtmLockers = new DefaultTableModel(new String[]{"Codigo", "Sede", "Tipo", "Estado", "Ult. Mantenimiento", "Usos"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblLockers = new JTable(dtmLockers);
        tblLockers.getSelectionModel().addListSelectionListener(e -> {
            int row = tblLockers.getSelectedRow();
            if (row >= 0) {
                txtCodigoLocker.setText(dtmLockers.getValueAt(row, 0).toString());
                txtIdSedeLocker.setText(dtmLockers.getValueAt(row, 1).toString());
                cmbTipoLocker.setSelectedItem(dtmLockers.getValueAt(row, 2).toString());
                cmbEstadoLocker.setSelectedItem(dtmLockers.getValueAt(row, 3).toString());
                txtFechaLocker.setText(dtmLockers.getValueAt(row, 4).toString());
                txtUsosLocker.setText(dtmLockers.getValueAt(row, 5).toString());
            }
        });

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(tblLockers), BorderLayout.CENTER);

        btnRegistrar.addActionListener(e -> accionRegistrarLocker());
        btnActualizar.addActionListener(e -> accionActualizarLocker());
        btnEliminar.addActionListener(e -> accionEliminarLocker());
        btnDiagnostico.addActionListener(e -> accionDiagnosticarLocker());
        btnLimpiar.addActionListener(e -> limpiarFormularioLocker());

        return panel;
    }

    private JPanel crearPanelSesiones() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridLayout(3, 4, 8, 8));
        formPanel.setBorder(BorderFactory.createTitledBorder("Iniciar o Gestionar Alquiler (Estrategia)"));

        txtIdUsuarioSesion = new JTextField();
        txtCodigoLockerSesion = new JTextField();
        cmbModeloAlquiler = new JComboBox<>(new String[]{"tiempo", "uso", "mensual", "evento"});
        txtTarifaBase = new JTextField("10000");
        cmbMetodoApertura = new JComboBox<>(new String[]{"PIN", "Huella", "Tarjeta", "App"});
        chkIncidencias = new JCheckBox("Hubo incidencias al entregar");

        formPanel.add(new JLabel("ID Usuario:"));
        formPanel.add(txtIdUsuarioSesion);
        formPanel.add(new JLabel("Codigo Locker:"));
        formPanel.add(txtCodigoLockerSesion);

        formPanel.add(new JLabel("Modelo de Alquiler (Strategy):"));
        formPanel.add(cmbModeloAlquiler);
        formPanel.add(new JLabel("Tarifa Base ($):"));
        formPanel.add(txtTarifaBase);

        formPanel.add(new JLabel("Metodo de Apertura:"));
        formPanel.add(cmbMetodoApertura);
        formPanel.add(chkIncidencias);
        formPanel.add(new JLabel(""));

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnIniciar = new JButton("Iniciar Sesion");
        JButton btnTerminar = new JButton("Terminar Sesion");
        JButton btnCalcular = new JButton("Calcular Costo Estimado");
        JButton btnEliminar = new JButton("Eliminar Registro");

        btnPanel.add(btnIniciar);
        btnPanel.add(btnTerminar);
        btnPanel.add(btnCalcular);
        btnPanel.add(btnEliminar);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(btnPanel, BorderLayout.SOUTH);

        dtmSesiones = new DefaultTableModel(new String[]{"ID Sesion", "ID Usuario", "Codigo Locker", "Inicio", "Fin", "Metodo", "Incidencias", "Costo Total ($)"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblSesiones = new JTable(dtmSesiones);
        tblSesiones.getSelectionModel().addListSelectionListener(e -> {
            int row = tblSesiones.getSelectedRow();
            if (row >= 0) {
                txtIdUsuarioSesion.setText(dtmSesiones.getValueAt(row, 1).toString());
                txtCodigoLockerSesion.setText(dtmSesiones.getValueAt(row, 2).toString());
            }
        });

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(tblSesiones), BorderLayout.CENTER);

        btnIniciar.addActionListener(e -> accionIniciarSesion());
        btnTerminar.addActionListener(e -> accionTerminarSesion());
        btnCalcular.addActionListener(e -> accionCalcularEstimado());
        btnEliminar.addActionListener(e -> accionEliminarSesion());

        return panel;
    }

    private JPanel crearPanelSedes() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridLayout(4, 4, 8, 8));
        formPanel.setBorder(BorderFactory.createTitledBorder("Datos de la Sede"));

        txtIdSede = new JTextField();
        txtIdFranquiciaSede = new JTextField("1");
        txtNombreSede = new JTextField();
        txtDireccionSede = new JTextField();
        txtCiudadSede = new JTextField();
        txtCapacidadSede = new JTextField();
        txtFechaSede = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        txtAdminSede = new JTextField();

        formPanel.add(new JLabel("ID Sede:"));
        formPanel.add(txtIdSede);
        formPanel.add(new JLabel("ID Franquicia:"));
        formPanel.add(txtIdFranquiciaSede);

        formPanel.add(new JLabel("Nombre:"));
        formPanel.add(txtNombreSede);
        formPanel.add(new JLabel("Direccion:"));
        formPanel.add(txtDireccionSede);

        formPanel.add(new JLabel("Ciudad:"));
        formPanel.add(txtCiudadSede);
        formPanel.add(new JLabel("Capacidad Lockers:"));
        formPanel.add(txtCapacidadSede);

        formPanel.add(new JLabel("Fecha Apertura:"));
        formPanel.add(txtFechaSede);
        formPanel.add(new JLabel("Administrador Sede:"));
        formPanel.add(txtAdminSede);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnRegistrar = new JButton("Registrar Sede");
        JButton btnActualizar = new JButton("Actualizar Sede");
        JButton btnEliminar = new JButton("Eliminar Sede");
        JButton btnLimpiar = new JButton("Limpiar");

        btnPanel.add(btnRegistrar);
        btnPanel.add(btnActualizar);
        btnPanel.add(btnEliminar);
        btnPanel.add(btnLimpiar);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(btnPanel, BorderLayout.SOUTH);

        dtmSedes = new DefaultTableModel(new String[]{"ID Sede", "ID Franquicia", "Nombre", "Direccion", "Ciudad", "Capacidad", "Apertura", "Administrador"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblSedes = new JTable(dtmSedes);
        tblSedes.getSelectionModel().addListSelectionListener(e -> {
            int row = tblSedes.getSelectedRow();
            if (row >= 0) {
                txtIdSede.setText(dtmSedes.getValueAt(row, 0).toString());
                txtIdFranquiciaSede.setText(dtmSedes.getValueAt(row, 1).toString());
                txtNombreSede.setText(dtmSedes.getValueAt(row, 2).toString());
                txtDireccionSede.setText(dtmSedes.getValueAt(row, 3).toString());
                txtCiudadSede.setText(dtmSedes.getValueAt(row, 4).toString());
                txtCapacidadSede.setText(dtmSedes.getValueAt(row, 5).toString());
                txtFechaSede.setText(dtmSedes.getValueAt(row, 6).toString());
                txtAdminSede.setText(dtmSedes.getValueAt(row, 7).toString());
            }
        });

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(tblSedes), BorderLayout.CENTER);

        btnRegistrar.addActionListener(e -> accionRegistrarSede());
        btnActualizar.addActionListener(e -> accionActualizarSede());
        btnEliminar.addActionListener(e -> accionEliminarSede());
        btnLimpiar.addActionListener(e -> limpiarFormularioSede());

        return panel;
    }

    private void accionRegistrarUsuario() {
        try {
            int id = Integer.parseInt(txtIdUsuario.getText().trim());
            int idSede = Integer.parseInt(txtIdSedeUsuario.getText().trim());
            String nombre = txtNombreUsuario.getText();
            String apellido = txtApellidoUsuario.getText();
            String tipo = (String) cmbTipoUsuario.getSelectedItem();
            String correo = txtCorreoUsuario.getText();
            String telefono = txtTelefonoUsuario.getText();
            String fecha = txtFechaUsuario.getText();
            String estado = (String) cmbEstadoUsuario.getSelectedItem();

            facade.registrarUsuario(id, idSede, nombre, apellido, tipo, correo, telefono, fecha, estado);
            JOptionPane.showMessageDialog(this, "Usuario registrado exitosamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormularioUsuario();
            cargarUsuarios();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Los campos ID e ID Sede deben ser numeros validos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (ValidacionException | DatabaseException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionActualizarUsuario() {
        try {
            int id = Integer.parseInt(txtIdUsuario.getText().trim());
            int idSede = Integer.parseInt(txtIdSedeUsuario.getText().trim());
            String nombre = txtNombreUsuario.getText();
            String apellido = txtApellidoUsuario.getText();
            String tipo = (String) cmbTipoUsuario.getSelectedItem();
            String correo = txtCorreoUsuario.getText();
            String telefono = txtTelefonoUsuario.getText();
            String fecha = txtFechaUsuario.getText();
            String estado = (String) cmbEstadoUsuario.getSelectedItem();

            facade.actualizarUsuario(id, idSede, nombre, apellido, tipo, correo, telefono, fecha, estado);
            JOptionPane.showMessageDialog(this, "Usuario actualizado exitosamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormularioUsuario();
            cargarUsuarios();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Los campos ID e ID Sede deben ser numeros validos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (ValidacionException | DatabaseException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionEliminarUsuario() {
        try {
            int id = Integer.parseInt(txtIdUsuario.getText().trim());
            int confirm = JOptionPane.showConfirmDialog(this, "¿Esta seguro de eliminar el usuario con ID " + id + "?", "Confirmar Eliminacion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                facade.eliminarUsuario(id);
                JOptionPane.showMessageDialog(this, "Usuario eliminado correctamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormularioUsuario();
                cargarUsuarios();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Seleccione o ingrese un ID de usuario valido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (ValidacionException | DatabaseException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormularioUsuario() {
        txtIdUsuario.setText("");
        txtIdSedeUsuario.setText("1");
        txtNombreUsuario.setText("");
        txtApellidoUsuario.setText("");
        txtCorreoUsuario.setText("");
        txtTelefonoUsuario.setText("");
        txtFechaUsuario.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        cmbTipoUsuario.setSelectedIndex(0);
        cmbEstadoUsuario.setSelectedIndex(0);
        tblUsuarios.clearSelection();
    }

    private void accionRegistrarLocker() {
        try {
            String codigo = txtCodigoLocker.getText().trim();
            int idSede = Integer.parseInt(txtIdSedeLocker.getText().trim());
            String tipo = (String) cmbTipoLocker.getSelectedItem();
            String estado = (String) cmbEstadoLocker.getSelectedItem();
            String fecha = txtFechaLocker.getText();
            int usos = Integer.parseInt(txtUsosLocker.getText().trim());

            facade.registrarLocker(codigo, idSede, tipo, estado, fecha, usos);
            JOptionPane.showMessageDialog(this, "Locker registrado exitosamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormularioLocker();
            cargarLockers();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Los campos ID Sede y Usos deben ser numeros validos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (ValidacionException | DatabaseException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionActualizarLocker() {
        try {
            String codigo = txtCodigoLocker.getText().trim();
            int idSede = Integer.parseInt(txtIdSedeLocker.getText().trim());
            String tipo = (String) cmbTipoLocker.getSelectedItem();
            String estado = (String) cmbEstadoLocker.getSelectedItem();
            String fecha = txtFechaLocker.getText();
            int usos = Integer.parseInt(txtUsosLocker.getText().trim());

            facade.actualizarLocker(codigo, idSede, tipo, estado, fecha, usos);
            JOptionPane.showMessageDialog(this, "Locker actualizado exitosamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormularioLocker();
            cargarLockers();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Los campos ID Sede y Usos deben ser numeros validos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (ValidacionException | DatabaseException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionEliminarLocker() {
        try {
            String codigo = txtCodigoLocker.getText().trim();
            int confirm = JOptionPane.showConfirmDialog(this, "¿Esta seguro de eliminar el locker con codigo " + codigo + "?", "Confirmar Eliminacion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                facade.eliminarLocker(codigo);
                JOptionPane.showMessageDialog(this, "Locker eliminado correctamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormularioLocker();
                cargarLockers();
            }
        } catch (ValidacionException | DatabaseException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionDiagnosticarLocker() {
        try {
            String codigo = txtCodigoLocker.getText().trim();
            String diag = facade.diagnosticarLocker(codigo);
            JOptionPane.showMessageDialog(this, diag, "Diagnostico de Mantenimiento", JOptionPane.INFORMATION_MESSAGE);
        } catch (ValidacionException | DatabaseException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormularioLocker() {
        txtCodigoLocker.setText("");
        txtIdSedeLocker.setText("1");
        txtFechaLocker.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        txtUsosLocker.setText("0");
        cmbTipoLocker.setSelectedIndex(0);
        cmbEstadoLocker.setSelectedIndex(0);
        tblLockers.clearSelection();
    }

    private void accionIniciarSesion() {
        try {
            int idUsuario = Integer.parseInt(txtIdUsuarioSesion.getText().trim());
            String codigoLocker = txtCodigoLockerSesion.getText().trim();
            String modelo = (String) cmbModeloAlquiler.getSelectedItem();
            String metodo = (String) cmbMetodoApertura.getSelectedItem();
            double tarifaBase = Double.parseDouble(txtTarifaBase.getText().trim());

            SesionDeUso sesion = facade.iniciarSesion(idUsuario, codigoLocker, modelo, metodo, tarifaBase);
            JOptionPane.showMessageDialog(this, "Sesion iniciada con exito.\nID Sesion: " + sesion.getIdSesion() + "\nCosto Calculado: $" + sesion.getCostoTotal(), "Alquiler Iniciado", JOptionPane.INFORMATION_MESSAGE);
            cargarSesiones();
            cargarLockers();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID de usuario y tarifa base deben ser numericos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (ValidacionException | DatabaseException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionTerminarSesion() {
        int row = tblSesiones.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una sesion de la tabla para terminarla.", "Atencion", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int idSesion = Integer.parseInt(dtmSesiones.getValueAt(row, 0).toString());
            boolean inc = chkIncidencias.isSelected();
            facade.terminarSesion(idSesion, inc);
            JOptionPane.showMessageDialog(this, "Sesion terminada exitosamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            cargarSesiones();
            cargarLockers();
        } catch (ValidacionException | DatabaseException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionCalcularEstimado() {
        try {
            int idUsuario = Integer.parseInt(txtIdUsuarioSesion.getText().trim());
            String modelo = (String) cmbModeloAlquiler.getSelectedItem();
            double tarifaBase = Double.parseDouble(txtTarifaBase.getText().trim());

            double costo = facade.calcularCostoEstimado(idUsuario, modelo, tarifaBase);
            JOptionPane.showMessageDialog(this, "Costo estimado para el usuario con estrategia '" + modelo + "': $" + costo, "Calculo Estimado", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un ID de usuario y tarifa base validos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (ValidacionException | DatabaseException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionEliminarSesion() {
        int row = tblSesiones.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una sesion de la tabla para eliminarla.", "Atencion", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int idSesion = Integer.parseInt(dtmSesiones.getValueAt(row, 0).toString());
            int confirm = JOptionPane.showConfirmDialog(this, "¿Desea eliminar el registro de la sesion " + idSesion + "?", "Confirmar Eliminacion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                facade.eliminarSesion(idSesion);
                cargarSesiones();
            }
        } catch (DatabaseException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionRegistrarSede() {
        try {
            int id = Integer.parseInt(txtIdSede.getText().trim());
            int idFra = Integer.parseInt(txtIdFranquiciaSede.getText().trim());
            String nombre = txtNombreSede.getText();
            String dir = txtDireccionSede.getText();
            String ciudad = txtCiudadSede.getText();
            int cap = Integer.parseInt(txtCapacidadSede.getText().trim());
            String fecha = txtFechaSede.getText();
            String admin = txtAdminSede.getText();

            facade.registrarSede(id, idFra, nombre, dir, ciudad, cap, fecha, admin);
            JOptionPane.showMessageDialog(this, "Sede registrada con exito.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormularioSede();
            cargarSedes();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID Sede, ID Franquicia y Capacidad deben ser numeros enteros.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (ValidacionException | DatabaseException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionActualizarSede() {
        try {
            int id = Integer.parseInt(txtIdSede.getText().trim());
            int idFra = Integer.parseInt(txtIdFranquiciaSede.getText().trim());
            String nombre = txtNombreSede.getText();
            String dir = txtDireccionSede.getText();
            String ciudad = txtCiudadSede.getText();
            int cap = Integer.parseInt(txtCapacidadSede.getText().trim());
            String fecha = txtFechaSede.getText();
            String admin = txtAdminSede.getText();

            facade.actualizarSede(id, idFra, nombre, dir, ciudad, cap, fecha, admin);
            JOptionPane.showMessageDialog(this, "Sede actualizada con exito.", "Exito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormularioSede();
            cargarSedes();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID Sede, ID Franquicia y Capacidad deben ser numeros enteros.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (ValidacionException | DatabaseException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void accionEliminarSede() {
        try {
            int id = Integer.parseInt(txtIdSede.getText().trim());
            int confirm = JOptionPane.showConfirmDialog(this, "¿Esta seguro de eliminar la sede con ID " + id + "?", "Confirmar Eliminacion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                facade.eliminarSede(id);
                JOptionPane.showMessageDialog(this, "Sede eliminada correctamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormularioSede();
                cargarSedes();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un ID de sede valido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (ValidacionException | DatabaseException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormularioSede() {
        txtIdSede.setText("");
        txtIdFranquiciaSede.setText("1");
        txtNombreSede.setText("");
        txtDireccionSede.setText("");
        txtCiudadSede.setText("");
        txtCapacidadSede.setText("");
        txtFechaSede.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        txtAdminSede.setText("");
        tblSedes.clearSelection();
    }

    private void cargarTodosLosDatos() {
        cargarUsuarios();
        cargarLockers();
        cargarSesiones();
        cargarSedes();
    }

    private void cargarUsuarios() {
        dtmUsuarios.setRowCount(0);
        try {
            List<Usuario> lista = facade.listarUsuarios();
            for (Usuario u : lista) {
                dtmUsuarios.addRow(new Object[]{
                        u.getIdUsuario(),
                        u.getIdSede(),
                        u.getNombre(),
                        u.getApellido(),
                        u.getTipo(),
                        u.getCorreoElectronico(),
                        u.getTelefono(),
                        u.getFechaRegistro(),
                        u.getEstado()
                });
            }
        } catch (DatabaseException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar usuarios: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarLockers() {
        dtmLockers.setRowCount(0);
        try {
            List<Locker> lista = facade.listarLockers();
            for (Locker l : lista) {
                dtmLockers.addRow(new Object[]{
                        l.getCodigoIdentificador(),
                        l.getIdSede(),
                        l.getTipo(),
                        l.getEstado(),
                        l.getFechaUltimoMantenimiento(),
                        l.getCantidadVecesUsado()
                });
            }
        } catch (DatabaseException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar lockers: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarSesiones() {
        dtmSesiones.setRowCount(0);
        try {
            List<SesionDeUso> lista = facade.listarSesiones();
            for (SesionDeUso s : lista) {
                dtmSesiones.addRow(new Object[]{
                        s.getIdSesion(),
                        s.getIdUsuario(),
                        s.getCodigoIdentificador(),
                        s.getFechaInicio(),
                        s.getFechaFin() == null ? "En curso" : s.getFechaFin(),
                        s.getMetodoApertura(),
                        s.isHuboIncidencias() ? "Si" : "No",
                        s.getCostoTotal()
                });
            }
        } catch (DatabaseException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar sesiones: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarSedes() {
        dtmSedes.setRowCount(0);
        try {
            List<Sede> lista = facade.listarSedes();
            for (Sede s : lista) {
                dtmSedes.addRow(new Object[]{
                        s.getIdSede(),
                        s.getIdFranquicia(),
                        s.getNombre(),
                        s.getDireccion(),
                        s.getCiudad(),
                        s.getCapacidadTotalLockers(),
                        s.getFechaApertura(),
                        s.getAdministradorSede()
                });
            }
        } catch (DatabaseException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar sedes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
