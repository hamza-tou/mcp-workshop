# US3 — Exposer un document comme resource MCP

En tant que développeur,  
je souhaite exposer un document interne comme resource MCP,  
afin qu’un assistant IA puisse le consulter directement.

---

## WHY

Certains contenus sont purement informatifs et ne nécessitent pas d’appel d’action.  
Les exposer comme resource MCP est plus simple et plus adapté qu’un tool.

---

## WHAT

Créer une resource MCP exposant :
- un document interne accessible via `GET /docs/{doc_id}`

---

## HOW

### Prérequis

L'API DataHub doit être lancée :
```bash
cd python/
uv run fastapi dev datahub_api/main.py --port 8000
```

### Création de la resource

Travaille dans `python/mcp/exercises/exo3/server.py`.

**Structure d'une resource avec FastMCP** :
```python
from fastmcp import FastMCP
import httpx

mcp = FastMCP("DataHub MCP")
API_BASE_URL = "http://localhost:8000"

@mcp.resource("datahub://docs/{doc_id}")
async def get_document(doc_id: str) -> str:
    """
    Récupère le contenu complet d'un document DataHub.
    
    Args:
        doc_id: Identifiant du document (ex: "rest-api-design")
    
    Returns:
        Contenu du document formaté
    """
    async with httpx.AsyncClient() as client:
        try:
            response = await client.get(f"{API_BASE_URL}/docs/{doc_id}")
            response.raise_for_status()
            doc = response.json()
            
            # Formater le document
            result = f"# {doc['title']}\n\n"
            result += f"**Owner**: {doc['owner']}\n"
            result += f"**Tags**: {', '.join(doc['tags'])}\n\n"
            result += doc['content']
            
            return result
            
        except httpx.HTTPStatusError as e:
            if e.response.status_code == 404:
                return f"Document '{doc_id}' non trouvé"
            return f"Erreur: {str(e)}"

if __name__ == "__main__":
    mcp.run()
```

### Documents disponibles

Quelques `doc_id` à tester :
- `rest-api-design`
- `graphql-intro`
- `microservices-patterns`
- `kubernetes-deployment`
- `api-authentication`

Liste complète : `curl http://localhost:8000/docs`

### Lancement

```bash
uv run fastmcp dev python/mcp/exercises/exo3/server.py
```

Vérifiez que les logs affichent **"Resources: 1"**.

### Test avec GitHub Copilot

Testez :
- #get_document
- "Montre-moi le document rest-api-design"
- "Lis le guide sur Kubernetes (doc ID: kubernetes-deployment)"

---

## RESSOURCES

- [API DataHub](python/datahub_api/README.md) - Liste des documents disponibles
- [Serveur de référence](python/mcp/reference_server/server.py) - Implémentation de la resource get_document
- [README exercice 3](python/mcp/exercises/exo3/README.md) - Instructions détaillées
- [Documentation FastMCP](https://github.com/jlowin/fastmcp)

---

## VALIDATION CRITERIA

- La resource est visible côté serveur MCP
- Le contenu du document est lisible par le client MCP