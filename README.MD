Simple web application for managing the program list used by the spring festival gala

# Web interface
http://127.0.0.1:8077/SP-IMS.html

# API
## Read
### Get program count
Method & URL: `GET /api/programCount`

Example response:
```json
{
  "code": "0",
  "message": "OK",
  "data": 2
}
```

### Get paged program list (max 5 per page)
Method & URL: `GET /api/program`

Query parameters:
- pageNum: Integer (required)

Example response: (`GET /api/program?pageNum=1`)
```json
{
  "code": "0",
  "message": "OK",
  "data": [
    {
      "id": 1,
      "actorCount": 1,
      "name": "Prog0",
      "typeName": "戏曲",
      "view": "description",
      "actorList": "actor1"
    },
    {
      "id": 2,
      "actorCount": 2,
      "name": "Prog1",
      "typeName": "相声",
      "view": "description",
      "actorList": "actor1,actor2"
    }
  ]
}
```

### Get single program data
Method & URL: `GET /api/program/{id}`

URL parameters:
- id: Integer (required)

Example response: (`GET /api/program/2`)
```json
{
    "code": "0",
    "message": "OK",
    "data": {
      "id": 2,
      "actorCount": 2,
      "name": "Prog1",
      "typeName": "相声",
      "view": "description",
      "actorList": "actor1,actor2"
    }
}
```

### Search
Method & URL: `GET /api/search`

Query parameters: (provide at least one)
- name: String, optional*
- typeName: String, optional*
- actorCount: Integer, optional*

Example response: (`GET /api/search?name=Pro&actorCount=2`)
```json
{
  "code": "0",
  "message": "OK",
  "data": [
    {
      "id": 2,
      "actorCount": 2,
      "name": "Prog1",
      "typeName": "相声",
      "view": "description",
      "actorList": "actor1,actor2"
    }
  ]
}
```

## Write
### Create program
Method & URL: `POST /add/program`

JSON parameters:
- name: String (required)
- typeName: String (required)
- actorList: String (required)
- view: String (required)

Example response:
```json
{
  "code": "0",
  "message": "Program created",
  "data": null
}
```

### Update program
Method & URL: `PUT /api/program/{id}`

URL parameters:
- id: Integer (required)

JSON parameters:
- name: String (required)
- typeName: String (required)
- actorList: String (required)
- view: String (required)

Example response:
```json
{
  "code": "0",
  "message": "Program updated",
  "data": null
}
```

### Delete program
Method & URL: `DELETE /api/program/{id}`

URL parameters:
- id: Integer (required)

Example response: (`DELETE /api/program/1`)
```json
{
  "code": "0",
  "message": "Program deleted",
  "data": null
}
```
