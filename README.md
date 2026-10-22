# PequeGestión · Servicio de autenticación

Servicio web (API REST) de registro e inicio de sesión para PequeGestión.

## Endpoints
URL base: `http://localhost:8080`

| Endpoint | Método | Recibe (JSON) | Si todo sale bien | Si algo falla |
|---|---|---|---|---|
| `/api/auth/registro` | POST | `usuario`, `contrasena` | **201** · Usuario registrado correctamente | **400** datos vacíos · **409** El usuario ya existe |
| `/api/auth/login` | POST | `usuario`, `contrasena` | **200** · Autenticación satisfactoria | **400** datos vacíos · **401** Error en la autenticación |