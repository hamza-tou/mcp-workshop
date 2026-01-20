# Workshop MCP - Guide Python

Ce guide vous accompagne dans la mise en place et l'utilisation de l'environnement Python pour le workshop MCP.


## Structure du projet

```
python/
├── pyproject.toml         # Configuration uv et dépendances
├── README.md              # Ce fichier
├── hello_tool.py          # Fake MCP server
├── server.py              # Serveur MCP à compléter (exercices)
└── reference_server/      # Implémentation de référence complète
    └── server.py          # Solution complète (si bloqué)
```

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

### Lancer le serveur MCP

Tester votre installation avec : 
```bash
uv run python hello_tool.py
```

Cette commande doit démarrer un serveur MCP (en mode HTTP) sur http://localhost:8001/mcp. Logs attendus:
```
[01/20/26 17:15:12] INFO     Starting MCP server 'Demo 🚀' with transport 'http' on
 http://localhost:8001/mcp                                                          
INFO:     Started server process [293122]
INFO:     Waiting for application startup.
INFO:     Application startup complete.
INFO:     Uvicorn running on http://localhost:8001 (Press CTRL+C to quit)
```


### Configurer votre IDE
Ajouter le serveur MCP dans votre IDE pour pouvoir le tester directment avec Copilot
- Mode : HTTP
- Nom : datahub-mcp
- Url : http://localhost:8001/mcp

Vérifie que le serveur est bien actif avec Copilot en lui demandant : "#magic-add 3 + 4". Le résultat devrait être 10 !