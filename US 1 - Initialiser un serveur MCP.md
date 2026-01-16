# US1 — Initialiser un serveur MCP

En tant que développeur,  
je souhaite initialiser et démarrer un serveur MCP local,  
afin de disposer d’une base technique pour exposer l’API DataHub à un assistant IA.

---

## WHY

Avant d’exposer des capacités métier, il est nécessaire de valider que l’environnement MCP est correctement installé et fonctionnel.  
Ce serveur MCP servira de fondation pour les expérimentations futures.

---

## WHAT

Créer un serveur MCP minimal capable de :
- démarrer localement
- afficher des logs de démarrage
- ne pas exposer de tool ni de resource

---

## HOW

- Travaille dans le dossier `mcp/exercises/exo1/`
- Crée un serveur MCP minimal (point d’entrée, configuration)
- Démarre le serveur localement
- Vérifie les logs de démarrage

---

## RESSOURCES

- Documentation MCP fournie
- Code du serveur MCP de référence (`mcp/reference_server/`)

---

## VALIDATION CRITERIA

- Le serveur MCP démarre sans erreur
- Les logs indiquent que le serveur est prêt
- Aucun tool ni resource n’est exposé