# Utiliser une image Java avec Maven pour compiler l'application
FROM maven:3.9.1-eclipse-temurin-17 AS builder

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copier les fichiers pom.xml et le code source du projet dans le conteneur
COPY pom.xml ./
RUN mvn dependency:go-offline -B

COPY src ./src

# Compiler le projet avec Maven
RUN mvn clean package -DskipTests

# Utiliser une image Java pour exécuter l'application
FROM eclipse-temurin:17-jdk

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copier le fichier JAR généré à partir de l'étape précédente
COPY --from=builder /app/target/*.jar app.jar

# Exposer le port par défaut utilisé par Spring Boot
EXPOSE 8080

# Commande pour exécuter l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
