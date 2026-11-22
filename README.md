# LockSecure - Sistema de Gestion de Casilleros Inteligentes

LockSecure es una aplicacion desarrollada en Java para coordinar la administracion y el alquiler de casilleros inteligentes en diferentes sedes y franquicias.


## 1. Funcionamiento 

* Registro y gestion: Permite dar de alta, modificar y consultar franquicias, sedes fisicas, casilleros y usuarios.
* Ciclo de un alquiler:
  - Para iniciar una sesion, el usuario debe estar en estado activo y el casillero en estado disponible.
  - Al iniciar el uso, el casillero cambia su estado a ocupado y suma a su historial de usos.
  - Al concluir la sesion, el casillero se libera volviendo a disponible, se calcula el tiempo transcurrido en minutos
* Persistencia tolerante a fallos: El sistema se comunica con una base de datos relacional, si el motor de base de datos no se encuentra activo o configurado, la aplicacion recurre de manera automatica a un almacenamiento en memoria para que el usuario pueda continuar trabajando sin que el programa se cierre.
* Dos formas de uso: Incluye una interfaz grafica con ventanas para el usuario final y un modo de consola pensado para pruebas rapidas.


## 2. Reglas y tarifas


### Tarifas segun el perfil de usuario (Polimorfismo)
El cobro base se ajusta de acuerdo con el tipo de usuario registrado:
* Estudiante: 20% de descuento sobre la tarifa base.
* Empleado: 10% de descuento corporativo.
* Socio: 25% de descuento por membresia.
* Asistente: 15% de descuento para eventos especiales.
* Visitante: sin descuento.

### Modalidades de alquiler
El costo final tambien depende de como se contrata el servicio:
* Por Tiempo: Cobro proporcional al tiempo de ocupacion (tarifa estandar).
* Por Uso: Cobro fijo por apertura con un ajuste operativo (+5%).
* Mensual: Modalidad de suscripcion fija con descuento continuo (10%).
* Evento: Modalidad temporal para convenciones con recargo de operacion (+20%).

### Validaciones obligatorias
* Los nombres, apellidos y correos electronicos no pueden estar vacios.
* El correo electronico debe incluir un formato valido (contener @ y punto).
* El numero de telefono debe contar con al menos 7 digitos.
* Los numeros deben ser mayores que cero.
* No se permite duplicar codigos de casilleros ni identificadores de usuario ya existentes.


## 3. Organizacion del proyecto

El codigo fuente esta distribuido en paquetes 

Main.java                           Entrada de la aplicacion
script_base_datos.sql               Script SQL con la estructura relacional y datos de prueba
model/                              Clases del negocio
factory/                            Fabricas para la creacion de objetos
strategy/                           Algoritmos de calculo de cobro
dao/                                Acceso a datos y persistencia SQL y memoria
database/                           Gestion de la conexion 
facade/                             Fachada unificada del sistema
view/                               Interfaz grafica de usuario en Java
exception/                          Manejo centralizado de excepciones


### Descripcion de cada paquete:

* model:
  conceptos del negocio con sus atributos y que hacen.
  - Usuario: clase base abstracta de la que derivan Estudiante, Empleado, Visitante, Socio y Asistente.
  - Locker: clase para los casilleros, con especializaciones en LockerEstandar, LockerGrande y LockerRefrigerado con diagnostico y    mantenimiento particular.
  - Franquicia: modela la entidad corporativa (FranquiciaAcademica, FranquiciaEmpresa, FranquiciaGimnasio, FranquiciaEvento).
  - Sede: define la ubicacion geografica, ciudad y capacidad fisica de casilleros.
  - SesionDeUso: controla el registro puntual de un alquiler (fechas, duracion, metodo de apertura, incidencias y costo).
  - Administrador: roles de gestion como SuperAdministrador, AdministradorCentral, AdministradorLocal y TecnicoSoporte.

* factory:
  Aplica el patron creacional Factory Method. Centraliza la creacion de instancias (UsuarioFactory, LockerFactory, FranquiciaFactory, ModeloAlquilerFactory) a partir de textos o selecciones del usuario, evitando crear objetos directamente en la interfaz.

* strategy:
  Aplica el patron de comportamiento Strategy. Define la interfaz ModeloAlquiler y sus implementaciones (AlquilerPorTiempo, AlquilerPorUso, AlquilerMensual, AlquilerEvento), permitiendo incorporar nuevas formas de cobro sin alterar el resto del codigo.

* dao:
  aplica el patron data access object a traves de una interfaz generica DAO<T, K>. Clases como UsuarioDAO, LockerDAO, SedeDAO, SesionDeUsoDAO y FranquiciaDAO contienen las sentencias preparadas (PreparedStatement) para interactuar con la base de datos y mantienen a su vez una lista en memoria como respaldo.

* database:
  ConexionBD.java se encarga de abrir y cerrar las conexiones, creando automaticamente las tablas al iniciar si aun no existen.

* facade:
  LockSecureFacade.java implementa el patron estructural Facade. Actua como punto unico de entrada para la vista, reuniendo las validaciones de negocio, la llamada a las fabricas, la seleccion de estrategias y el guardado en los DAO.

* view:
  VentanaPrincipal.java ofrece una interfaz grafica construida con Java Swing dividida en cuatro pestanas principales:
  - Usuarios: Formulario para registrar, actualizar, dar de baja y listar usuarios en una tabla.
  - Lockers: Panel para dar de alta casilleros, cambiar su disponibilidad y revisar alertas de mantenimiento.
  - Sesiones de Uso: seccion para iniciar alquileres (seleccionando metodo de apertura: PIN, Tarjeta RFID, Huella o QR) y concluir sesiones abiertas con calculo automatico de costos.
  - Sedes: Panel informativo de sedes y capacidades.

* exception:
  - ValidacionException: Se lanza cuando un dato no cumple las reglas del negocio (por ejemplo, intentar alquilar un casillero que ya esta ocupado).
  - DatabaseException: Envuelve los errores derivados de la capa de persistencia SQL.


## 4. Base de datos relacional

El script script_base_datos.sql define la estructura normalizada en seis tablas vinculadas por claves primarias y foraneas:

1. franquicia: Datos de la empresa asociada y cuota mensual.
2. sede: Sucursales fisicas vinculadas a una franquicia.
3. usuario: Clientes registrados asociados a una sede.
4. administrador: Personal con credenciales y roles de acceso.
5. locker: Inventario de casilleros fisicos asignados a cada sede.
6. sesion_de_uso: Transacciones de alquiler que conectan a un usuario con un casillero.


## 5. compilacion y ejecucion

### Requisitos previos
* Java Development Kit 8 o superior instalado 

### Opcion 1: Ejecucion con interfaz grafica (Recomendada)
Para abrir la ventana interactiva del sistema:

```bash
javac -encoding UTF-8 Main.java */*.java
java Main
```

### Opcion 2: Ejecucion en modo consola
Para ejecutar una prueba de demostracion directa en la terminal sin cargar la interfaz grafica:

```bash
java Main --console
```

