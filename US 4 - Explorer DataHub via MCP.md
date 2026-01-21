# US4 — Explorer DataHub via MCP

En tant que développeur,  
je souhaite utiliser un assistant IA pour explorer DataHub via MCP,  
afin de répondre à plusieurs questions métier sans connaître l’API.


## WHY
L'équipe a bien avancé sur le développement du serveur MCP, elle souhaite maintenant valider que cet outil permet une exploration réelle et efficace de l’API DataHub.

## WHAT

Accomplir 6 missions d'exploration via GitHub Copilot en utilisant uniquement le serveur MCP (sans interroger l'API directement).

## HOW
 

0. Assures toi que l'API de DataHub tourne sur ton poste (suit les instructions dans `datahub_api/README.md`)
1. Démarres le serveur MCP développé par l'API ( `FULL_MCP=TRUE mvn spring-boot:run` ou `uv run python datahub_mcp_server.py`)
2. Remplis les missions d'exploration :
   1. Formules un prompt naturel à GitHub Copilot
   2. Observes quels tools/resources sont utilisés
   3. Vérifies que le résultat est correct
   4. Notes les difficultés rencontrés, les confusions, ...


### Mission 1 : Découverte par tags
**Objectif** : Trouver tous les documents avec le tag `api`

**Résultat attendu** : Liste de 7 documents minimum incluant rest-api-design, api-authentication, api-rate-limiting, api-versioning, api-documentation, api-testing, api-pagination

### Mission 2 : Recherche par mot-clé
**Objectif** : Chercher des documents mentionnant "GraphQL"

**Résultat attendu** : Au moins le document `graphql-intro`

### Mission 3 : Extraction d'information spécifique
**Objectif** : Obtenir le propriétaire (owner) du document sur les microservices patterns

**Résultat attendu** : `alice.martin@company.com`

### Mission 4 : Consultation de contenu
**Objectif** : Lire et résumer le guide de déploiement Kubernetes

**Résultat attendu** : Résumé mentionnant Pod, Deployment, Service, ConfigMap, Secrets

### Mission 5 : Recherche de snippets par type
**Objectif** : Lister tous les snippets de type "config"

**Résultat attendu** : Au moins 3 snippets (kubernetes-health-checks, github-actions-ci, docker-compose-dev)

### Mission 6 : Analyse comparative
**Objectif** : Comparer REST et GraphQL selon les documents DataHub

**Résultat attendu** : Synthèse basée sur `rest-api-design` et `graphql-intro`


## VALIDATION CRITERIA

- Les 6 missions sont résolues via MCP
- L’assistant utilise effectivement tools et resources
- Les réponses sont cohérentes avec les données DataHub