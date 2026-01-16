# Serveur MCP de Référence

Ce serveur MCP contient l'implémentation complète de tous les tools et resources demandés dans les User Stories.

## Utilisation

Le serveur de référence sert deux objectifs :

1. **Exemple** : comprendre comment implémenter des tools et resources MCP
2. **Dépendance** : les exercices peuvent importer et réutiliser des parties de ce serveur

## Lancement

Depuis la racine du projet :

```bash
uv run fastmcp dev python/mcp/reference_server/server.py
```

Vous devriez voir des logs indiquant que le serveur MCP est démarré.

## Contenu

### Tools

1. **search_datahub** - Recherche full-text dans documents ou snippets
2. **list_documents_by_tag** - Liste documents filtrés par tag
3. **list_snippets** - Liste snippets avec filtres optionnels
4. **get_available_tags** - Liste tous les tags disponibles

### Resources

1. **datahub://docs/{doc_id}** - Récupère le contenu complet d'un document
2. **datahub://snippets/{snippet_id}** - Récupère un snippet de code

## Architecture

Le serveur utilise :
- **FastMCP** : framework pour créer des serveurs MCP en Python
- **httpx** : client HTTP async pour appeler l'API DataHub
- **Décorateurs** : `@mcp.tool()` et `@mcp.resource()` pour exposer les fonctionnalités

## Points d'attention

### Gestion des erreurs
Le serveur gère proprement les erreurs HTTP et retourne des messages clairs.

### Descriptions
Les descriptions des tools et resources sont **intentionnellement basiques** dans cette version de référence. L'exercice 5 (US5) consiste à les améliorer.

### Format de retour
Tous les tools retournent du texte formaté lisible par un humain et un LLM.

## Prérequis

L'API DataHub doit être lancée sur `http://localhost:8000` :

```bash
uv run uvicorn datahub_api.main:app --reload --port 8000
```
