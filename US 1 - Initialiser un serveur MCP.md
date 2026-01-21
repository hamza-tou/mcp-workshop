# US1 -  Initialiser un serveur MCP

En tant que développeur,  
je souhaite initialiser et démarrer un serveur MCP local,  
afin de disposer d’une base technique pour exposer l’API DataHub à un assistant IA.

## WHY

Avant d’exposer des capacités métier, il est nécessaire de valider que l’environnement MCP est correctement installé et fonctionnel.  
Ce serveur MCP servira de fondation pour les expérimentations futures.
Notre tech lead a déjà commencé le travail et souhaiterait que tu développes un nouveau tool qui nous servirait d'exemple.

## WHAT

On veut mettre en place un Tool MCP qui dit "Hello mcp server here !"

Créer un serveur MCP minimal capable de :
- démarrer localement
- exposer un tool simple `hello_world` pour tester la communication

## HOW

1. Reprend le travail du tech lead (`hello_tool.py` ou `HelloTool.java`)
2. Inspire toi de l'exemple du tool d'addition pour exposer un nouveau tool `hello_world` qui retourne "Hello mcp server here !"
3. (Re)Lance le serveur MCP en suivant les instructions du README.md (`python/README.md` ou `java/README.md`)
4. Vérifie que le tool est bien actif avec Copilot en lui demandant : "Que dit #hello_world ?"


## VALIDATION CRITERIA

- Le serveur MCP redémarre sans erreur sur `http://localhost:8001`
- Le tool `hello_world` est accessible et retourne le message attendu