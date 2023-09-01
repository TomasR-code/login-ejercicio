INSTRUCCIONES PARA LA EJECUCION
- Se debe tener instalado JAVA 8 y maven
 En caso de usar intellij importarlo como proyecto spring para que pueda configurarse y darle RUN

- Te registras con la collection de postman que agregue al repositorio, y al hacerlo el response te va a dar un token,
- El cual vas a necesitar para logearte, tenes que copiarlo como param en el login y asi vas a poder ingresar.
- En caso de olvidarte o copiarlo mal, lo podes volver a generar con login-token que tambien se encuentra en las colleciones. 

ESTRUCTURA DEL PROYECTO:
El proyecto sigue una estructura organizada en diferentes directorios para modularizar y separar las responsabilidades:

- COMMON: Contiene clases como ErrorDetails y ErrorResponse utilizadas para mensajes de error.

- CONFIG: Almacena la configuración del token JWT y la configuración de seguridad.

- CONTROLLER: Aquí se encuentran AuthController (para autenticación) y UserController (para registro e inicio de sesión).

- DATA: Contiene clases como AuthRequest, ErrorInfo, ErrorResponse, RequestSignUp, Response, ResponseLogin que mapean las 
  solicitudes y respuestas.

- DTO: Contiene los Data Transfer Objects (PhoneDto y SignUpDto) para mapeos.

- ENTITY: Contiene entidades como PhoneEntity y UserEntity, con esta última conteniendo un array de PhoneEntity. Estas entidades 
  representan las tablas en la base de datos.

- MAPPER: Contiene UserEntityMapper, una herramienta para simplificar el mapeo entre entidades y DTOs.

- REPOSITORY: Aquí se encuentra UserRepository, que establece la conexión JPA a la base de datos.

- SERVICE: Contiene la lógica de negocio. UserService maneja el registro e inicio de sesión, mientras que AuthService se 
  encarga de la lógica del token JWT. JwtTokenService maneja la lógica de creación del token.


FLUJO DE EJECUCION:

El flujo típico de ejecución para los dos caminos principales, registro e inicio de sesión, se describe a continuación:

- Registro (Sign Up):
El cliente envía una solicitud al controlador AuthController.
El controlador pasa la solicitud al servicio UserService para validar y procesar.
El servicio realiza la lógica de registro, incluyendo la validación de datos y el almacenamiento en la base de datos a través 
del repositorio UserRepository.


- Inicio de Sesión (Log In):
El cliente envía una solicitud al controlador AuthController.
El controlador pasa la solicitud al servicio UserService para validar y procesar.
El servicio realiza la lógica de inicio de sesión, incluyendo la validación de credenciales y la generación del token JWT 
a través del servicio AuthService.

Tuve un pequeño problema que hasta el momento no pude solucionarlo, si ejecutas mvn clean install se rompe el mapper (UserEntityMapper). 
De todas formas el proyecto sigue funcionando.
En caso de ejecutar el comando y el proyecto no funcione, se debe borrar el archivo que esta dentro de 
target - generated-sources - annotations - com.example.login.mapper y el archivo que este dentro, eliminarlo.
Luego ejecutar el proyecto sin mvn clean install