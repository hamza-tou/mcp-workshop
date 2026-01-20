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

**Use-case** : Un développeur demande à l'agent IA :  
_"Donne-moi les bonnes pratiques pour concevoir une API REST"_

L'agent doit pouvoir :
1. **Chercher** des documents pertinents avec le tool `search_datahub` (US2)
2. **Accéder au contenu complet** du document trouvé via une **resource MCP**
3. **Synthétiser** les bonnes pratiques pour répondre à la question

**Ce que tu dois créer** :

Une **resource MCP** nommée `datahub://docs/{doc_id}` qui :
- Récupère le contenu d'un document depuis l'API DataHub (`GET /docs/{doc_id}`)
- **Formate le résultat en texte lisible** pour l'agent IA (titre, métadonnées, contenu)
- Permet à l'agent d'accéder directement au document en tapant `#` dans Copilot Chat puis en sélectionnant la resource

**Différence avec un tool** :
- Un **tool** = action que l'agent peut **exécuter** (rechercher, créer, modifier)
- Une **resource** = contenu que l'agent peut **lire** et **référencer** (document, fichier, page)

---

## HOW

### Prérequis

L'API DataHub doit être lancée :
```bash
cd python/
uv run fastapi dev datahub_api/main.py --port 8000
```

### Création de la resource

Travaille dans `python/datahub_mcp/server.py`.

<details>
<summary>💡 Voir la solution</summary>

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

</details>

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
uv run python python/datahub_mcp/server.py
```


### Test de la resource MCP

**Vérifier que la resource est exposée :**

1. Dans VSCode, ouvrez la vue **MCP**
2. Cliquez sur **List servers**
3. Sélectionnez votre serveur (`test-mcp` ou le nom de votre serveur)
4. Cliquez sur **Browse Resources**
5. Cherchez la ressource `get_document` et cliquz dessus
5. Cherchez l'id `rest-api-design`

---

## RESSOURCES

- [API DataHub](python/datahub_api/README.md) - Liste des documents disponibles
- [Serveur de référence](python/datahub_mcp/reference_server/server.py) - Implémentation de la resource get_document
- [Documentation FastMCP](https://github.com/jlowin/fastmcp)

---

## VALIDATION CRITERIA

- La resource est visible côté serveur MCP
- Le contenu du document est lisible par le client MCP