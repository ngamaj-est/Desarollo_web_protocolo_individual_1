# Gestión de Enfermedades

Protocolo individual unidad 1 en donde se realizo el ejercicio asignado numero 9 en donde se construyo una Aplicación web Java para administrar usuarios y un catálogo de enfermedades. La interfaz está construida con JSP, los controladores usan Jakarta Servlets, la persistencia se realiza con JDBC y MySQL, y el despliegue está preparado para Tomcat 10.1.

URL DEL PROYECRTO DESPLEGADO: desarollowebprotocoloindividual1-production.up.railway.app

## Requisitos

- JDK 21.
- Maven 3.9 o superior.
- MySQL 8.0 o superior.
- Tomcat 10.1 para desplegar el WAR manualmente, o Docker para usar el `dockerfile` incluido.

## Instalación

Ejecuta los comandos desde la carpeta raíz del proyecto.

### 1. Crear la base de datos

Importa el script incluido:

```powershell
cmd /c "mysql -u root -p < src\db\GestionEnfermedades_db.sql"
```

El script crea la base `GestionEnfermedades_db`, las tablas `Usuarios` y `Enfermedades`, y registros de ejemplo.

> **Importante:** el script contiene `DROP TABLE IF EXISTS` para ambas tablas. Si lo ejecutas sobre una base existente, eliminará sus datos y volverá a crear las tablas. Haz una copia de seguridad antes de ejecutarlo nuevamente.

### 2. Configurar la conexión MySQL

La conexión está implementada en `DbConnection.java`. Si no defines variables de entorno, usa estos valores:

| Variable | Valor predeterminado |
| --- | --- |
| `MYSQLHOST` | `localhost` |
| `MYSQLPORT` | `3306` |
| `MYSQLUSER` | `root` |
| `MYSQLPASSWORD` | vacío |
| `MYSQLDATABASE` | `GestionEnfermedades_db` |

En PowerShell puedes definirlas para la sesión actual antes de iniciar Tomcat:

```powershell
$env:MYSQLHOST = "localhost"
$env:MYSQLPORT = "3306"
$env:MYSQLUSER = "root"
$env:MYSQLPASSWORD = "clavedelusuariodeelsistema"
$env:MYSQLDATABASE = "GestionEnfermedades_db"
```
*CONFIGURAR LA CONTRASEÑA DEL USUARIO O DEL SISTEMA DONDE SE ESTA UTILIZANDO LA BASE DE DATOS DE FORMA MANUAL PARA EVITAR BRECHAS DE SEGURIDAD

### 3. Compilar y probar

```powershell
mvn clean test
mvn clean package
```

Actualmente `src/test/java` está vacío, por lo que `mvn test` no ejecuta pruebas automatizadas del proyecto.

El WAR resultante queda en:

```text
target/protocolo_1_nathan_gama-1.0-SNAPSHOT.war
```

### 4. Ejecutar con Tomcat

Copia el WAR a la carpeta `webapps` de Tomcat 10.1 y arranca el servidor. Por ejemplo:

```powershell
Copy-Item .\target\protocolo_1_nathan_gama-1.0-SNAPSHOT.war C:\tomcat\webapps\
```

Abre la aplicación usando el contexto derivado del nombre del WAR:

```text
http://localhost:8080/protocolo_1_nathan_gama-1.0-SNAPSHOT/
```

Para abrirla directamente en la raíz de Tomcat, renombra el WAR a `ROOT.war` antes de copiarlo.

### Alternativa: Docker

El `dockerfile` compila la aplicación con JDK 21 y la ejecuta en Tomcat 10.1. Construye la imagen:

```powershell
docker build -f dockerfile -t gestion-enfermedades .
```

Iníciala pasando la configuración de MySQL. En Docker Desktop, `host.docker.internal` permite que el contenedor alcance un MySQL que corre en Windows:

```powershell
docker run --rm -p 8080:8080 `
  -e MYSQLHOST=host.docker.internal `
  -e MYSQLPORT=3306 `
  -e MYSQLUSER=root `
  -e MYSQLPASSWORD=tu-clave `
  -e MYSQLDATABASE=GestionEnfermedades_db `
  gestion-enfermedades
```

La aplicación queda disponible en `http://localhost:8080/`. MySQL debe estar iniciado y aceptar conexiones desde el contenedor. El proyecto no incluye un archivo Docker Compose.

### Cuentas de demostración

El script SQL carga estas cuentas iniciales:

| Correo | Contraseña | Rol |
| --- | --- | --- |
| `nathan.gama@admin.com` | `admin` | Administrador |
| `antonio.lopez@medico.com` | `medico123` | Medico |

Estas credenciales son únicamente para desarrollo local. Cambia o elimina las cuentas de ejemplo en cualquier entorno compartido.



## Endpoints principales

| Ruta | Función |
| --- | --- |
| `/` | Entrada de la aplicación |
| `/auth` | Inicio de sesión, registro y cierre de sesión mediante `accion` |
| `/dashboard?modulo=usuarios` | Dashboard y CRUD de usuarios |
| `/dashboard?modulo=enfermedades` | Dashboard y CRUD de enfermedades |
| `/usuarios` | Acciones `agregar`, `buscar`, `actualizar` y `eliminar` |
| `/enfermedades` | Acciones `agregar`, `listartodo`, `buscar`, `actualizar` y `eliminar` |