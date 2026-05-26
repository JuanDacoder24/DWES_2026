✅ Así lo puedes hacerlo
Ya tienes la funcionalidad hecha en un único controlador: el controlador de partidos.

Ruta que debes usar
POST http://localhost:8080/api/partidos/crear
Qué recibe
El body debe tener estos 4 valores:

{
  "equipo1": "ID_DEL_EQUIPO_1",
  "equipo2": "ID_DEL_EQUIPO_2",
  "arbitro1": "ID_DEL_ARBITRO_1",
  "arbitro2": "ID_DEL_ARBITRO_2"
}

Qué devuelve
La respuesta contiene:

los árbitros participantes
los jugadores del equipo 1
los jugadores del equipo 2
Ejemplo de respuesta:

{
  "id": "uuid-del-partido",
  "equipo1": {
    "id": "id-equipo-1",
    "nombreEquipo": "Equipo A",
    "sede": "Sede A"
  },
  "equipo2": {
    "id": "id-equipo-2",
    "nombreEquipo": "Equipo B",
    "sede": "Sede B"
  },
  "arbitros": [
    {
      "id": "id-arbitro-1",
      "nombre": "Luis",
      "apellido1": "García",
      "apellido2": "Pérez",
      "rol": "PRINCIPAL"
    },
    {
      "id": "id-arbitro-2",
      "nombre": "Ana",
      "apellido1": "Martínez",
      "apellido2": "López",
      "rol": "ASISTENTE"
    }
  ],
  "jugadoresEquipo1": [
    {
      "id": "id-jugador-1",
      "dorsal": 1,
      "nombre": "Carlos",
      "apellido1": "Ruiz",
      "apellido2": "Sánchez",
      "posicion": "DEFENSA"
    }
  ],
  "jugadoresEquipo2": [
    {
      "id": "id-jugador-2",
      "dorsal": 9,
      "nombre": "Iván",
      "apellido1": "Linares",
      "apellido2": "Ramos",
      "posicion": "DELANTERO"
    }
  ]
}

🔧 Cómo se implementa internamente
La lógica está en el controlador de partidos y en el servicio:

el controlador recibe el request con los 4 IDs
el servicio valida que existan
guarda el partido
devuelve los datos formateados
🧪 Cómo probarlo en Postman
Abre Postman
Crea una petición POST
URL: http://localhost:8080/api/partidos/crear
Header: Content-Type: application/json
Body: JSON con los 4 IDs
Ejemplo real:

{
  "equipo1": "id-del-equipo-1",
  "equipo2": "id-del-equipo-2",
  "arbitro1": "id-del-arbitro-1",
  "arbitro2": "id-del-arbitro-2"
}

⚠️ Importante
Para que funcione, esos IDs deben existir previamente en la base de datos:

equipos
árbitros
Si quieres, te puedo dejar un ejemplo paso a paso para crear primero un equipo, luego un árbitro, luego un partido y probarlo completo en Postman.