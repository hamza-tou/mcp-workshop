# Workshop MCP - Guide Java

Ce guide vous accompagne dans la mise en place et l'utilisation de l'environnement Java pour le workshop MCP.

## Structure du projet

Le projet est organisé selon les répertoires suivants :
```
src/main/java/com/datahub/
└── DataHubMCPApplication.java    # Point d'entrée de l'application Spring Boot

src/main/resources/
├── application.properties      # Configuration de l'application
└── application.yml             # Configuration YAML alternative

pom.xml                         # Dépendances Maven et configuration du projet
```


## Prérequis

Avant d'exécuter ce projet, assurez-vous d'avoir installé les éléments suivants :

- **JDK 17** : [Télécharger JDK 17](https://www.oracle.com/java/technologies/downloads/#java17) ou utiliser [OpenJDK 17](https://jdk.java.net/17/)
- **Maven 3.6+** : [Télécharger Maven](https://maven.apache.org/download.cgi) et suivre le [guide d'installation](https://maven.apache.org/install.html)

Vérifiez vos installations :
```bash
java -version  # Devrait afficher Java 17
mvn -version   # Devrait afficher Maven 3.6 ou supérieur
```


## Guide de développement

Pour installer le projet, vous devez suivre ces étapes :
```bash
mvn clean install
```

Tester votre installation avec : 
```bash
mvn spring-boot:run
```

Cette commande doit démarrer un serveur MCP (en mode HTTP) sur http://localhost:8001/mcp. Logs attendus:
```logs
[...]
Registered tools: 1
[...]
Tomcat started on port 8001 (http) with context path '/'
[...]
Started McpServerApplication
```

### Configurer votre IDE
Ajouter le serveur MCP dans votre IDE pour pouvoir le tester directment avec Copilot avec la command palette: `>MCP: Add Server...`
- Mode : HTTP
- Nom : datahub-mcp
- Url : http://localhost:8001/mcp

Vérifie que le serveur est bien actif avec Copilot en lui demandant : "#magic-add 3 + 4
". Le résultat devrait être 10 !
