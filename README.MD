# AmaZombie - (Plants vs Zombies)

 ![Menu de App](https://github.com/iAngelGuzman/AmaZombie/blob/master/app.png)

## Gestión de paqueteria

Nuestro programa es una gestion de paquetes con la tematica del videojuego Plants vs Zombies.

## Base de datos - MySQL

Modificar la conexion en el archivo "/amazombie/dao/ConexionDB.java" con sus datos de MySQL.

```
private static final String URL = "jdbc:mysql://localhost:3306/amazombie
private static final String USUARIO = "angel";
private static final String PASSWORD = "root";
```

Para este programa se utilizo la base de datos MySQL, es importante ejecutar el siguiente query.

```
CREATE DATABASE amazombie;

USE amazombie;

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    rol ENUM('usuario', 'admin') NOT NULL
);

CREATE TABLE paquetes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10,2),
    estado ENUM('espera', 'enviado', 'procesado', 'enterrado') DEFAULT 'espera',
    ruta ENUM('preparando', 'listo', 'camino', 'llegando', 'entregado') DEFAULT 'preparando',
    guia VARCHAR(50) UNIQUE,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    origen VARCHAR(250) NOT NULL,
    destino VARCHAR(250) NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

CREATE TABLE reportes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    usuario_id INT NOT NULL,
    asunto VARCHAR(255) NOT NULL,
    descripcion TEXT,
    respuesta TEXT,
    estado ENUM('pendiente', 'resuelto') DEFAULT 'pendiente',
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

CREATE TABLE solicitudes (
    id INT PRIMARY KEY AUTO_INCREMENT,
    usuario_id INT NOT NULL,
    razon TEXT,
    estado ENUM('pendiente', 'aceptada', 'rechazada') DEFAULT 'pendiente',
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

CREATE TABLE faq (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pregunta VARCHAR(500) NOT NULL,
    respuesta TEXT
);

INSERT INTO usuarios (nombre, contrasena, rol) VALUES
('admin', 'root', 'admin'),
('raul', '123', 'usuario');

INSERT INTO paquetes (
    usuario_id,
    nombre,
    descripcion,
    precio,
    estado,
    ruta,
    guia,
    origen,
    destino
) VALUES (
    1, -- ID del usuario que envía el paquete
    'Libros Varios',
    'Caja con 10 libros de literatura y ensayos.',
    50.75,
    'espera',
    'preparando',
    'PQT7890123456789',
    'Calle Falsa 123, Ciudad de México',
    'Avenida Siempre Viva 742, Guadalajara'
);

INSERT INTO solicitudes (
    usuario_id,
    razon,
    estado
) VALUES (
    1,
    'Solicitud para cambiar dirección de entrega del paquete PQT789012345.',
    'pendiente'
);

INSERT INTO faq (pregunta, respuesta) VALUES
('¿Cuáles son los límites de peso y tamaño para envíos nacionales e internacionales?','Los envíos nacionales permiten paquetes de hasta 30 kg, con dimensiones máximas de 150 cm en cualquier lado. Para envíos internacionales, el límite varía según el destino, pero suele ser de 70 kg, con restricciones adicionales según las aduanas locales.'),
('¿Qué tipo de embalaje es recomendable para mi envío?','Para proteger tu envío:
- Objetos frágiles → Usa cajas reforzadas y relleno interno (burbuja, espuma, papel).
- Documentos → Sobres acolchonados o plásticos.
- Ropa y textiles → Bolsas resistentes y selladas.
- Electrónicos → Empaque original con protecciones adicionales.'),
('¿Cuánto cuesta enviar un paquete?','El precio depende del peso, tamaño y distancia. Para estimaciones, se usa la siguiente tabla:
- Local: Desde $50 MXN.
- Nacional: Desde $150 MXN.
- Internacional: Desde $500 MXN, sujeto a impuestos y regulaciones.'),
('¿En dónde puedo buscar las sucursales en mi localidad?','Puedes buscar sucursales cercanas en el apartado "FAQ" e interacutuando con el mapa.'),
('¿Cómo puedo rastrear mi paquete?','Cada envío tiene un número de rastreo. Ingrésalo en nuestra plataforma en línea para ver su ubicación y fecha estimada de entrega.'),
('¿Por qué no hay información de mi envío?','Puede deberse a:
- Retrasos en el sistema de actualización.
- Que el paquete aún no haya sido escaneado en la sucursal.
- Un error en el número de rastreo ingresado.'),
('¿Cuáles son los artículos prohibidos en envíos?','No aceptamos envíos de:
- Sustancias peligrosas o inflamables.
- Armas o explosivos.
- Animales vivos.
- Dinero en efectivo.
- Documentos oficiales como pasaportes o identificaciones'),
('¿Qué sucede si mi paquete se pierde?','Si tu paquete no llega en el tiempo estimado:
- Realizamos una investigación interna.
- Si no se encuentra, aplicamos una compensación según el valor asegurado del envío.'),
('¿Puedo enviar paquetes los fines de semana o días festivos?','Sí, pero solo bajo ciertas condiciones:
- Entregas express pueden programarse en algunos servicios.
- Las oficinas principales trabajan los sábados, aunque con horarios reducidos.'),
('¿Existen descuentos para envíos frecuentes o empresariales?','Si realizas envíos regularmente, ofrecemos tarifas especiales y suscripciones con precios preferenciales para empresas o comerciantes.'),
('¿Cuáles son las políticas de reembolso en caso de pérdida o daño?','Los paquetes con seguro de envío pueden solicitar reembolso en caso de:
- Daños durante el transporte.
- Pérdida confirmada por nuestro equipo.
- Mal manejo por parte de nuestras sucursales.'),
('¿Cómo funciona el servicio de reenvío si el paquete no pudo ser entregado?','Si el paquete no pudo ser entregado:
- Se intentan hasta 3 entregas.
- Si sigue sin éxito, puedes solicitar una reprogramación gratuita.
- Tras cierto tiempo, el paquete se devuelve al remitente.');
```
