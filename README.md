# Architecture Micro Services modèle - Java (Spring Boot) + Docker (PoC)

## Avertissement

🚨🚨🚨 Ce projet est une __Preuve de concept__ fourni à des fins pédagogiques. Le code doit être optimisé avant d'être employé en production. 🚨🚨🚨

TODO:

- le service auth n'est pas implémenté (signin, signup, signout, verify token...).
- le service gateway est simpliste. Il peut être amélioré.

## Instructions pour l'installation (avant le lancement des services Docker)

- créer les fichiers de variables d'environnement en se basant sur les fichiers modèles nommés .env.example :

- ./secrets/tasks-db-password
- ./gateway-service/.env
- ./tasks-service/.env
- ./tasks-db/.env
- ./rabbitmq/.env

## Génération des services Java avec Spring Initializr

Les services Java ont été initialisés à l'aide de Spring Initializr <https://start.spring.io/> avec la configuration suivante :

- Project : Maven
- Language : Java
- Spring Boot : 3.4.0
- Project Meatadat :
  - Group : dev.shrp
  - Artifact : nom-du-service
  - Name : nom-du-service
  - Description : Micro Service nom-du-service
  - Package name : dev.shrp.nom-du-service
  - Packaging : Jar
  - Java : 17
  - Dependencies :
    - Spring Web
    - Spring Data JPA (si le service est connecté à une base de données)
    - PostgreSQL Driver (si la base de données est de type PostgreSQL)

- Pour chaque service Java :
  - ajouter un fichier Dockerfile et les variables d'environnement utiles avec un fichier .env à la racine du dossier du service, à référencer dans le fichier compose.yaml
  - personnaliser le fichier ./src/main/resources/application.properties pour accéder aux variables d'environnement  

  ```properties
    spring.application.name=gateway

    tasks_service=${TASKS_SERVICE}
  ```

- [Configuration Spring Initializr pour le service Gateway](https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.4.0&packaging=jar&jvmVersion=17&groupId=dev.shrp&artifactId=gateway&name=gateway&description=Service%20gateway&packageName=dev.shrp.gateway&dependencies=cloud-gateway-reactive,cloud-resilience4j,cloud-contract-stub-runner)

- Tutoriel pour le service Gateway : [Building a Gateway](https://spring.io/guides/gs/gateway)

## Lancement des services Docker

### Sans reconstruction des images Docker

```sh
docker compose up
```

### Sans reconstruction des images Docker + activation du mode watch (permet de reconstruire en live les containers en cas de modification du code source)

```sh
docker compose up --watch
```

### Avec reconstruction des images Docker au lancement (si modification du code source associé)

```sh
docker compose up --build
```

### Avec reconstruction des images Docker au lancement (si modification du code source associé) + activation du mode watch (permet de reconstruire en live les containers en cas de modification du code source)

```sh
docker compose up --build --watch
```

### Suppression des containers

```sh
docker compose down
```

## Test de l'API avec Curl

- curl --request GET \
  --url <http://localhost:8888/tasks>
- curl --request GET \
  --url <http://localhost:8888/tasks/1>
- curl --request POST \
  --url <http://localhost:8888/tasks> \
  --header 'content-type: application/json' \
  --data '{
  "user_id":"f928c455-d2f3-4e30-bf58-178ae041e8c2",
  "content":"Faire du sport"
}'

--

!["Logotype Shrp"](https://sherpa.one/images/sherpa-logotype.png)

__Alexandre Leroux__  
_Enseignant / Formateur_  
_Développeur logiciel web & mobile_

Nancy (Grand Est, France)

<https://shrp.dev>
