# Serveur MCP DataHub

## Structure du projet

- `server.py` - Serveur MCP à compléter selon les exercices (fichier de travail)
- `reference_server/server.py` - Serveur MCP de référence avec toutes les fonctionnalités (pour continuer si bloqué)

## Lancer le serveur

Le serveur MCP doit être lancé sur `localhost:8001/mcp` :

```bash
# Avec votre serveur (à compléter)
 uv run python <chemin_du_fichier>

# Ou avec le serveur de référence (solution complète)
uv run python python/mcp/reference_server/server.py
```

## Tester avec GitHub Copilot

### 1. Ajouter le serveur MCP dans Copilot

1. Dans VS Code, ouvrir la palette de commandes (Cmd+Shift+P sur Mac)
2. Chercher et sélectionner : **MCP: Add Server**
3. Choisir le type : **HTTP**
4. Entrer l'adresse : `http://localhost:8001/mcp`

### 2. Utiliser les tools et resources MCP

Dans GitHub Copilot Chat, vous pouvez appeler les tools et resources avec le symbole `#` :

#### Exemples avec les tools :
```
# Rechercher des documents
#search_datahub query="kubernetes" scope="docs" limit=5

# Lister les documents par tag
#list_documents_by_tag tag="api"
```

#### Exemples avec les resources :
```
# Accéder à un document spécifique
#datahub://document/DOC001

# Accéder à un snippet
#datahub://snippet/SNIP001
```

#### Exemples avec les prompts :
```
# Utiliser un prompt pour analyser un document
#analyze_document doc_id="DOC001"

# Comparer des documents
#compare_documents doc_ids="DOC001,DOC002"
```

## Vérifier que le serveur fonctionne

Pour vérifier que votre serveur MCP est correctement lancé :

```bash
# Le serveur doit être accessible à cette adresse
curl http://localhost:8001/mcp

# Vous devriez voir une réponse JSON avec les informations du serveur
```

## Progression des exercices

Référez-vous aux User Stories (US 1 à US 5) pour implémenter progressivement :
- US 1 : Serveur MCP minimal
- US 2 : Tool de recherche (`search_datahub`)
- US 3 : Resources pour accéder aux documents
- US 4 : Exploration complète (tags, snippets)
- US 5 : Prompts et améliorations UX
