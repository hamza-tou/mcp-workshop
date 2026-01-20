# DataHub API

**Cette API sert de backend de test pour le workshop MCP.** Elle simule une API interne existante que vous allez exposer à un LLM via un serveur MCP. Consultez le [README principal](../README.md) pour le contexte complet du workshop.

## Dev Guide

This project uses **astral uv** as a dependency manager. You need to install it:

```bash
curl -LsSf https://astral.sh/uv/install.sh | sh
```

### Set up the project

From the `datahub_api/` directory, launch the API:
```bash
uv run fastapi run main.py --port 8000
```

This command starts the server, you can check it with:
```bash
# Expected response: {"status":"healthy","version":"1.0.0"}
curl http://localhost:8000/health
```

**Important** : L'API doit tourner en permanence pendant que vous travaillez sur les exercices MCP.

## Interactive Documentation

Once the server is running, visit:

- **Swagger UI**: http://localhost:8000/docs
- **ReDoc**: http://localhost:8000/redoc