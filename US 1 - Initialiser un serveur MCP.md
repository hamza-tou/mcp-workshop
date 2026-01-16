# US1 — Initialiser un serveur MCP

En tant que développeur,  
je souhaite initialiser et démarrer un serveur MCP local,  
afin de disposer d’une base technique pour exposer l’API DataHub à un assistant IA.

---

## WHY

Avant d’exposer des capacités métier, il est nécessaire de valider que l’environnement MCP est correctement installé et fonctionnel.  
Ce serveur MCP servira de fondation pour les expérimentations futures.

---

## WHAT

Créer un serveur MCP minimal capable de :
- démarrer localement
- afficher des logs de démarrage
- exposer un tool simple `hello_server` pour tester la communication


### Création du serveur

Travaille dans `python/mcp/server.py`.

**Structure minimale avec FastMCP** :
```python
from fastmcp import FastMCP

# Créer l'instance du serveur
mcp = FastMCP("DataHub MCP Server")

# Tool simple pour tester
@mcp.tool()
def hello_server() -> str:
    """Tool de test simple qui retourne un message de bienvenue."""
    return "Hello mcp server here !"

# Point d'entrée
if __name__ == "__main__":
    mcp.run(transport="sse", port=8001, path="/mcp")
```

### Démarrage

Depuis le répertoire `python/` :
```bash
uv run python -m mcp.server
```

### Logs attendus

Vous devriez voir quelque chose comme :
```
Starting MCP server 'DataHub MCP Server' with transport 'http' on ...
```

### Tester le tool

Une fois le serveur lancé et connecté à Copilot :
```
# Dans Copilot Chat
#hello_server
```

Vous devriez recevoir : `Hello mcp server here !`

---

## RESSOURCES

- [Guide Python complet](python/README.md) - Setup détaillé avec uv
- [Guide MCP](python/mcp/README.md) - Comment tester avec Copilot
- [Documentation FastMCP](https://github.com/jlowin/fastmcp)
- [Serveur de référence](python/mcp/reference_server/server.py) - Implémentation complète

---

## VALIDATION CRITERIA

- Le serveur MCP démarre sans erreur sur `http://localhost:8001/mcp`
- Les logs indiquent que le serveur est prêt avec 1 tool
- Le tool `hello_server` est accessible et retourne le message attendu
- Le serveur peut être connecté à GitHub Copilot via HTTP