# US2 — Exposer la recherche DataHub comme tool MCP

En tant que développeur,  
je souhaite exposer la recherche DataHub comme tool MCP,  
afin qu’un assistant IA puisse rechercher des contenus internes sans connaître l’API.

---

## WHY

La route `GET /search` est puissante mais difficile à utiliser sans documentation.  
L’exposer comme tool MCP permet un usage guidé et fiable par un LLM.

---

## WHAT

Créer un tool MCP qui encapsule :
- l’endpoint `GET /search`
- les paramètres `query`, `scope`, `limit`

---

## HOW

### Prérequis

L'API DataHub doit être lancée :
```bash
cd python/
uv run uvicorn datahub_api.main:app --reload --port 8000
```

### Création du tool

Travaille dans `python/mcp/server.py`.

<details>
<summary>💡 Voir la solution</summary>

**Structure d'un tool avec FastMCP** :
```python
from fastmcp import FastMCP
import httpx

mcp = FastMCP("DataHub MCP")
API_BASE_URL = "http://localhost:8000"

@mcp.tool()
async def search_datahub(
    query: str,
    scope: str = "docs",
    limit: int = 10
) -> str:
    """
    Recherche dans DataHub (documents ou snippets).
    
    Args:
        query: Texte à rechercher (ex: "graphql", "kubernetes")
        scope: "docs" pour documents, "snippets" pour code
        limit: Nombre maximum de résultats (1-100)
    
    Returns:
        Résultats formatés en texte lisible
    """
    async with httpx.AsyncClient() as client:
        try:
            response = await client.get(
                f"{API_BASE_URL}/search",
                params={"q": query, "scope": scope, "limit": limit}
            )
            response.raise_for_status()
            data = response.json()
            
            # Formater et retourner les résultats
            # ...
            
        except httpx.HTTPError as e:
            return f"Erreur: {str(e)}"

if __name__ == "__main__":
    mcp.run()
```

</details>

### Lancement

```bash
uv run python python/mcp/server.py
```

Vérifiez que les logs affichent **"Tools: 1"**.

### Test avec GitHub Copilot

Configurez ce serveur MCP dans VS Code et testez :
- #search_datahub
- "Cherche des documents sur GraphQL"
- "Trouve des snippets Redis"

---

## RESSOURCES

- [API DataHub](python/datahub_api/README.md) - Tous les endpoints documentés
- [Serveur de référence](python/mcp/reference_server/server.py) - Implémentation du tool search_datahub
- [Documentation FastMCP](https://github.com/jlowin/fastmcp)

---

## VALIDATION CRITERIA

- Le tool est déclaré et visible côté serveur MCP
- L’appel du tool déclenche bien `GET /search`
- Les résultats sont retournés dans un format exploitable