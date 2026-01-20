# DataHub API

API REST pour centraliser la documentation technique, guides et snippets de code.

## Project Structure

```
datahub_api/
├── main.py         # Point d'entrée FastAPI avec tous les endpoints
├── models.py       # Modèles Pydantic pour la validation
├── __init__.py     # Module Python
├── README.md       # Ce fichier
└── data/           # Données de test (JSON)
    ├── documents.json
    ├── snippets.json
    └── tags.json
```

## Dev Guide

This project uses **astral uv** as a dependency manager. You need to install it:

```bash
curl -LsSf https://astral.sh/uv/install.sh | sh
```

### Set up the project

From the `python/` directory:

```bash
uv sync
```

### Launch the FastAPI application

```bash
uv run fastapi dev datahub_api/main.py --port 8000
```

This command starts the server with auto-reload enabled, allowing you to see changes without restarting the server.

### Test the API

```bash
# Expected response: {"status":"healthy","version":"1.0.0"}
curl http://localhost:8000/health
```

## API Endpoints

### Health

- **GET /health** - Vérifier l'état de l'API
  ```bash
  curl http://localhost:8000/health
  ```

### Documents

- **GET /documents** - Lister les documents (avec filtres optionnels)
  ```bash
  curl "http://localhost:8000/documents"
  curl "http://localhost:8000/documents?tag=api"
  curl "http://localhost:8000/documents?owner=alice.martin@company.com"
  ```

- **GET /documents/{doc_id}** - Récupérer un document spécifique
  ```bash
  curl "http://localhost:8000/documents/rest-api-design"
  ```

### Search

- **GET /search** - Rechercher dans les documents ou snippets
  ```bash
  curl "http://localhost:8000/search?q=graphql&scope=docs&limit=5"
  curl "http://localhost:8000/search?q=redis&scope=snippets"
  ```

### Snippets

- **GET /snippets** - Lister les snippets (avec filtres optionnels)
  ```bash
  curl "http://localhost:8000/snippets"
  curl "http://localhost:8000/snippets?type=query"
  curl "http://localhost:8000/snippets?service=kubernetes"
  ```

- **GET /snippets/{snippet_id}** - Récupérer un snippet spécifique
  ```bash
  curl "http://localhost:8000/snippets/graphql-query-example"
  ```

### Tags & Owners

- **GET /tags** - Lister tous les tags disponibles
  ```bash
  curl "http://localhost:8000/tags"
  ```

- **GET /owners** - Lister tous les propriétaires de documents
  ```bash
  curl "http://localhost:8000/owners"
  ```

## Interactive Documentation

Once the server is running, visit:

- **Swagger UI**: http://localhost:8000/docs
- **ReDoc**: http://localhost:8000/redoc

## Data

L'API utilise des données JSON statiques pour ce workshop :

- **15 documents** sur l'architecture API (REST, GraphQL, microservices, etc.)
- **10 snippets** de code (Python, SQL, YAML, etc.)
- **35 tags** pour catégoriser le contenu

Les données sont chargées au démarrage de l'API depuis les fichiers JSON dans `data/`.

## Notes pour le Workshop MCP

Cette API sert de backend pour les exercices MCP. Les serveurs MCP que vous allez créer interrogeront cette API pour exposer ses fonctionnalités aux LLMs de manière plus utilisable.

**Important** : L'API doit tourner en permanence pendant que vous travaillez sur les exercices MCP.
