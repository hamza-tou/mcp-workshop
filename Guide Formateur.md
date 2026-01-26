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

## US - 2 : Exposer la recherche DataHub comme tool MCP
Encapsuler l'API DataHub dans un tool MCP pour rendre la recherche accessible via langage naturel, sans exposer la complexité de l'API REST.

**Concepts théoriques clés** :
- Tool MCP = façade conversationnelle sur une API existante
- Tool = Act => La base des agents
- Formatage de sortie pour LLM (texte lisible vs. JSON brut)
- Préférence des LLM : Natural language > XML > JSON

**Points d'attention/critères qualité** :
- Réutilisation du client API (`datahub_client.py` / `DataHubClient.java`)
- Description du tool explicite sur usage et paramètres

**Erreurs fréquentes** :
- API DataHub non démarrée (`http://localhost:8000`)
- Retour JSON brut (illisible pour LLM)

## US - 3 : Exposer un document comme resource MCP
Permettre à l'agent IA d'accéder à du contenu informatif via une resource MCP plutôt qu'un tool, pour une lecture directe et référençable.

**Concepts théoriques clés** :
- Distinction tool vs. resource : tool = action exécutable, resource = contenu lisible et référençable
- URI MCP : `datahub://documents/{doc_id}` comme point d'accès unique
- Resources = accès via `#` dans Copilot Chat

**Points d'attention/critères qualité** :
- Resource visible dans **MCP: Browse Resources**
- Contenu formaté en texte lisible (pas de JSON brut)

**Erreurs fréquentes** :
- Confusion tool/resource (créer un tool au lieu d'une resource)
- JSON brut non formaté pour lecture LLM

## US - 4 : Explorer DataHub via MCP
Valider l'utilisabilité réelle du serveur MCP en accomplissant des missions métier variées via langage naturel :
- Tester l'ergonomie utilisateur du serveur MCP en conditions réelles
- Identifier les confusions, ambiguïtés ou échecs d'un agent

**Concepts théoriques clés** :
- MCP = abstraction conversationnelle : le LLM choisit tools/resources selon le besoin
- Le LLM utilise la documentation exposée du MCP pour son choix de tools/ressources

**Points d'attention/critères qualité** :
- Lecture de la documentation des tools
- Vérification dans Chat Debug View

**Erreurs fréquentes** :
- [Java] Serveur MCP lancé sans mode complet `FULL_MCP=TRUE`

## US - 5

## US - 6
