Guide formateur
==

Ce guide permet au formateur de comprendre l’intention pédagogique, structurer la séance de travaux dirigé et faciliter l’apprentissage actif.

# Cadre général

Cette formation se déroule sous forme de mini-projet dans lequel les apprenants réalisent une suite d’exercices fortement guidés pour développer leurs compétences de développement de serveur MCP.

**Guidelines** :
- **Installation en amont** : les prérequis techniques sont clairs et réalisables avant la session.
- **Fortement guidé** : chaque exercice fournit un cadre, des consignes et des attentes explicites.
- **Exercices indépendants** : chaque étape peut être réalisée sans bloquer l’ensemble du TP.
- **Alignement pédagogique** : chaque exercice sert un objectif d’apprentissage identifié.
- **Mini-projet proche du réel** : contexte, contraintes et livrables reflètent des situations professionnelles.

# Déroulement 

## En amont

Les apprenants installent et initialisent le projet (environnement, dépendances, repo) à partir des consignes fournies.

## Pendant la session

Le formateur :

- introduit le contexte et le déroulé global du TP
- reste disponible pour débloquer les apprenants,
- assure le **cadencement** de la session.
- le contexte narratif est là pour rapprocher du réel et sa compréhensio ne doit en aucun cas ralentir les apprenants

## Cadence par exercice

Pour chaque exercice, le formateur :

- laisse du temps à l’exploration et à la tentative autonome,
- restitue et explique la solution par une démonstration en **live coding**
- transmet les élements théoriques et concepts mentaux à l'oral

## Pédagogie

Fortement guidé par le formateur, cette formation verticale est **100 % orientée pratique**. La théorie est transmise à l’oral, au fil de l’exécution et des questions, jamais comme un prérequis formel.


# Pré-requis et préparation formateur
- Installation et initialisation du projet (environnement, dépendances, repo) à partir des consignes fournies.
- Vérification de la connection de l'IDE à Github Copilot
- Vérification de l'adéquation des exercices avec la version actuelle de Github Copilot

# Objectifs pédagogiques
|                     Objectif principal                    |                              Objectifs secondaires                             |                                                          Savoirs et compétences                                                          |
|:---------------------------------------------------------:|:------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------------------------------------------------------:|
| Je mets en place un serveur MCP exploitable par un client | J’initialise un serveur MCP fonctionnel en Python / Java et démarre le serveur | Création d’un projet MCP, initialisation du serveur, dépendances MCP, structure minimale d’un serveur MCP, Lancement d’un serveur MCP,   |
|                                                           | Je déclare et expose un tool MCP                                               | Définition d’un tool MCP, schéma d’entrée et de sortie, implémentation d’une logique métier simple, gestion basique des erreurs.         |
|                                                           | Je déclare et expose une resource MCP                                          | Définition d’une resource MCP, exposition de données (fichier, données simulées), accès contrôlé à une ressource.                        |
|                                                           | Je teste et valide mon serveur MCP avec un client MCP                          | Connexion d’un client MCP (Copilot VS Code), détection des tools et resources, appel d’un tool depuis le client, validation du résultat. |
|                                                           | J’améliore la compréhension et l’usage des capacités MCP par un LLM            | Analyse du comportement LLM, Amélioration des descriptions MCP, Validation par test avec un client MCP                                   |


# Détails des exercices

## US - 1 : Initialiser un serveur MCP
Valider l'environnement technique, comprendre le pattern de base d'un tool MCP et tester la communication client-serveur pour créer une base réutilisable.

**Concepts théoriques clés** :
- Structure d'un serveur MCP : décorateur `@mcp.tool` (Python) ou `@McpTool` (Java)
- Modèle requête/réponse : un tool = fonction métier exposée au LLM
- Transport HTTP vs. SSE vs. STDIO
- Workflow MCP : LLM choisit le tool pertinent selon sa description, envoie les paramètres au serveur, reçoit la réponse et l'intègre dans son contexte

**Points d'attention/critères qualité** :
- Le serveur démarre sans erreur sur `localhost:8001`
- La description du tool `hello_world` est claire et explicite

**Erreurs fréquentes** :
- Port déjà utilisé (autre serveur actif)
- Test via curl au lieu du client Copilot (ne valide pas l'intégration MCP)

## US - 2
## US - 3
## US - 4
## US - 5
## US - 6