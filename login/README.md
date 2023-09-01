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
  1. El cliente envía una solicitud de registro al controlador.
  2. El controlador crea una instancia de la clase RequestSignUp a partir de los datos de la solicitud del cliente.
  3. El controlador pasa la instancia de RequestSignUp al servicio de registro.
  4. El servicio de registro valida los datos de la instancia de RequestSignUp.
  5. Si los datos son válidos, el servicio de registro crea una nueva instancia de la clase UserEntity.
  6. El servicio de registro guarda la instancia de UserEntity en la base de datos.
  7. El servicio de registro devuelve una respuesta al cliente.


- Inicio de Sesión (Log In):
  1. El cliente envía una solicitud de inicio de sesión al controlador.
  2. El controlador crea una instancia de la clase AuthRequest a partir de los datos de la solicitud del cliente.
  3. El controlador pasa la instancia de AuthRequest al servicio de inicio de sesión.
  4. El servicio de inicio de sesión valida las credenciales del usuario.
  5. Si las credenciales son válidas, el servicio de inicio de sesión consulta la base de datos para obtener los datos del usuario.
  6. El servicio de inicio de sesión genera un token JWT para el usuario.
  7. El servicio de inicio de sesión devuelve el token JWT al cliente.

*Tuve un pequeño problema que hasta el momento no pude solucionarlo, si ejecutas mvn clean install se rompe el mapper (UserEntityMapper). 
De todas formas el proyecto sigue funcionando.
En caso de ejecutar el comando y el proyecto no funcione, se debe borrar el archivo que esta dentro de 
target - generated-sources - annotations - com.example.login.mapper y el archivo que este dentro, eliminarlo.
Luego ejecutar el proyecto sin mvn clean install*