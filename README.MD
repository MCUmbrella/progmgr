Simple web application for managing the program list used by the spring festival gala

# Web interface
http://127.0.0.1:8077/SP-IMS.html

# Known bugs
- Fail to update programs due to unknown reasons (in the web interface, javascript related issues)

# API
## Read
### Program count
Method & URL: `GET /get/programCount`

Example response:
```json
{
  "code": "0",
  "message": "OK",
  "data": 2
}
```

### Program list (max 20 per page)
Method & URL: `GET /get/programList`

Parameters:
- pageNum: Integer (required)

Example response:
```json
{
  "code": "0",
  "message": "OK",
  "data": {
    "programResults": [
      {
        "id": 1,
        "actorCount": 1,
        "name": "Prog0",
        "typeName": "Dance",
        "view": "description",
        "actorList": "actor1"
      },
      {
        "id": 2,
        "actorCount": 2,
        "name": "Prog1",
        "typeName": "Other",
        "view": "description",
        "actorList": "actor1,actor2"
      }
    ]
  }
}
```

### Program search
Method & URL: `GET /get/search`

Parameters: (provide at least one)
- type: String
- num: Integer
- name: String

Example response:
```json
{
  "code": "0",
  "message": "OK",
  "data": {
    "programSearchResults": [
      {
        "id": 2,
        "actorCount": 2,
        "name": "Prog1",
        "typeName": "Other",
        "view": "description",
        "actorList": "actor1,actor2"
      }
    ]
  }
}
```

## Write
### Create program
Method & URL: `POST /add/program`

JSON parameters:
- name: String (required)
- type: String (required)
- actors: String (required)
- point: String (required)

Example response:
```json
{
  "code": "0",
  "message": "OK",
  "data": null
}
```

### Update program
Method & URL: `POST /update/program`

JSON parameters:
- id: Integer (required)
- name: String (required)
- type: String (required)
- actors: String (required)
- point: String (required)

Example response:
```json
{
  "code": "0",
  "message": "OK",
  "data": null
}
```

### Delete program
Method & URL: `POST /delete/program`

JSON parameters:
- id: Integer (required)

Example response:
```json
{
  "code": "0",
  "message": "OK",
  "data": null
}
```
