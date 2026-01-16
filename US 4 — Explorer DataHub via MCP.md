# US4 — Explorer DataHub via MCP

En tant que développeur,  
je souhaite utiliser un assistant IA pour explorer DataHub via MCP,  
afin de répondre à plusieurs questions métier sans connaître l’API.

---

## WHY

L’objectif est de valider que MCP permet une exploration réelle et efficace de l’API DataHub.

---

## WHAT

Accomplir 6 missions d'exploration via GitHub Copilot en utilisant uniquement le serveur MCP (sans interroger l'API directement).

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

---

## HOW

### Prérequis

1. **API DataHub lancée** :
   ```bash
   cd python/
   uv run fastapi dev datahub_api/main.py --port 8000
   ```

2. **Serveur MCP lancé** (au choix) :
   ```bash
   # Serveur de référence
   uv run fastmcp dev python/mcp/reference_server/server.py
   
   # OU vos implémentations (si exercices 2-3 complétés)
   uv run fastmcp dev python/mcp/exercises/exo2/server.py
   ```

3. **GitHub Copilot configuré** pour utiliser votre serveur MCP

### Exécution des missions

Pour chaque mission :
1. Formulez un prompt naturel à GitHub Copilot
2. Observez quels tools/resources sont utilisés
3. Vérifiez que le résultat est correct

**Exemples de prompts** :
- Mission 1 : "Trouve tous les documents DataHub qui parlent d'APIs"
- Mission 2 : "Cherche des documents sur GraphQL"
- Mission 3 : "Qui est le propriétaire du document sur les microservices patterns ?"
- Mission 4 : "Montre-moi le document sur Kubernetes et résume les concepts clés"
- Mission 5 : "Liste les snippets de configuration disponibles"
- Mission 6 : "Compare REST et GraphQL selon les docs DataHub"

### Observation

Pour chaque mission, notez :
- ✅ Le LLM a-t-il choisi le bon tool/resource ?
- ✅ Les paramètres étaient-ils corrects ?
- ✅ Le résultat était-il exploitable ?
- ⚠️ Difficultés rencontrées ?

Listez les problèmes d'utilisabilité pour l'exercice 5 :
- Descriptions de tools/resources ambiguës ?
- Paramètres mal documentés ?
- Confusion entre tools similaires ?

---

## RESSOURCES

- [README exercice 4](python/mcp/exercises/exo4/README.md) - Instructions détaillées
- [Serveur de référence](python/mcp/reference_server/server.py) - Tous les tools et resources
- [Données API](python/datahub_api/data/) - Documents et snippets disponibles

---

## VALIDATION CRITERIA

- Les 3 tâches sont résolues via MCP
- L’assistant utilise effectivement tools et resources
- Les réponses sont cohérentes avec les données DataHub