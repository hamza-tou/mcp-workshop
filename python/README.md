# Workshop MCP - Guide Python

Ce guide vous accompagne dans la mise en place et l'utilisation de l'environnement Python pour le workshop MCP.

## Prérequis

- Python 3.11 ou supérieur
- uv (gestionnaire de dépendances)

## Installation

### Installer uv

Si vous n'avez pas encore installé uv :

```bash
curl -LsSf https://astral.sh/uv/install.sh | sh
```

Redémarrez votre terminal après l'installation.

### Installer les dépendances

Depuis le répertoire `python/` :

```bash
uv sync
```

Cette commande va créer un environnement virtuel et installer toutes les dépendances (FastAPI, uvicorn, fastmcp, httpx, etc.).

## Structure du projet

```
python/
├── pyproject.toml              # Configuration uv et dépendances
├── README.md                   # Ce fichier
├── datahub_api/               # API DataHub (FastAPI)
│   ├── main.py                # Point d'entrée de l'API
│   ├── models.py              # Modèles Pydantic
│   ├── README.md              # Documentation API
│   └── data/                  # Données de test
│       ├── documents.json
│       ├── snippets.json
│       └── tags.json
└── mcp/                       # Serveurs MCP
    ├── README.md              # Guide pour tester avec Copilot
    ├── server.py              # Serveur MCP à compléter (exercices)
    └── reference_server/      # Implémentation de référence complète
        └── server.py          # Solution complète (si bloqué)
```

## Démarrage rapide

### 1. Lancer l'API DataHub

L'API DataHub doit tourner en permanence pour que les serveurs MCP puissent l'interroger :

```bash
cd python/
uv run uvicorn datahub_api.main:app --reload --port 8000
```

L'API sera disponible sur `http://localhost:8000`

**Vérification** :
```bash
# Expected response: {"status":"healthy"}
curl http://localhost:8000/health
```

**Documentation interactive** : http://localhost:8000/docs

## Ressources utiles

- [Documentation FastAPI](https://fastapi.tiangolo.com/)
- [Documentation FastMCP](https://github.com/jlowin/fastmcp)
- [Documentation MCP](https://modelcontextprotocol.io/)
- [Documentation uv](https://docs.astral.sh/uv/)
