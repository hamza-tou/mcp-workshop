"""Serveur MCP de référence - Implémentation complète."""

from fastmcp import FastMCP
import httpx
from typing import Optional

# Créer le serveur MCP
mcp = FastMCP("DataHub MCP Server")

# URL de l'API DataHub
API_BASE_URL = "http://localhost:8000"


# ===== TOOLS =====


@mcp.tool()
async def search_datahub(query: str, scope: str = "docs", limit: int = 10) -> str:
    """
    Recherche dans DataHub.

    Permet de rechercher des documents techniques ou des snippets de code.
    La recherche est full-text et porte sur le titre, contenu, tags, etc.

    Args:
        query: Texte à rechercher (ex: "graphql", "kubernetes")
        scope: Portée de la recherche - "docs" pour documents ou "snippets" pour code
        limit: Nombre maximum de résultats à retourner (1-100)

    Returns:
        Résultats de recherche formatés en texte lisible
    """
    async with httpx.AsyncClient() as client:
        try:
            response = await client.get(
                f"{API_BASE_URL}/search",
                params={"q": query, "scope": scope, "limit": limit},
                timeout=10.0,
            )
            response.raise_for_status()
            data = response.json()

            if data["total"] == 0:
                return f"Aucun résultat trouvé pour '{query}' dans {scope}"

            results = []
            results.append(
                f"Trouvé {data['total']} résultat(s) pour '{query}' dans {scope}:\n"
            )

            for item in data["results"]:
                if scope == "docs":
                    results.append(f"\n📄 {item['title']}")
                    results.append(f"   ID: {item['doc_id']}")
                    results.append(f"   Tags: {', '.join(item['tags'])}")
                    results.append(f"   Owner: {item['owner']}")
                    # Aperçu du contenu (premiers 200 caractères)
                    content_preview = item["content"][:200].replace("\n", " ")
                    results.append(f"   Aperçu: {content_preview}...")
                else:
                    results.append(f"\n💻 {item['title']}")
                    results.append(f"   ID: {item['snippet_id']}")
                    results.append(f"   Langage: {item['language']}")
                    results.append(f"   Type: {item['type']}")
                    results.append(f"   Description: {item['description']}")

            return "\n".join(results)

        except httpx.HTTPError as e:
            return f"Erreur lors de la recherche: {str(e)}"


@mcp.tool()
async def list_documents_by_tag(tag: str) -> str:
    """
    Liste les documents ayant un tag spécifique.

    Args:
        tag: Tag à filtrer (ex: "api", "kubernetes", "graphql")

    Returns:
        Liste des documents avec ce tag
    """
    async with httpx.AsyncClient() as client:
        try:
            response = await client.get(
                f"{API_BASE_URL}/docs", params={"tag": tag}, timeout=10.0
            )
            response.raise_for_status()
            docs = response.json()

            if not docs:
                return f"Aucun document trouvé avec le tag '{tag}'"

            results = [f"Documents avec le tag '{tag}' ({len(docs)} trouvé(s)):\n"]
            for doc in docs:
                results.append(f"\n📄 {doc['title']}")
                results.append(f"   ID: {doc['doc_id']}")
                results.append(f"   Tags: {', '.join(doc['tags'])}")
                results.append(f"   Owner: {doc['owner']}")

            return "\n".join(results)

        except httpx.HTTPError as e:
            return f"Erreur: {str(e)}"


@mcp.tool()
async def list_snippets(
    type: Optional[str] = None, service: Optional[str] = None
) -> str:
    """
    Liste les snippets de code disponibles.

    Args:
        type: Type de snippet (query, function, config, middleware, migration)
        service: Service concerné (postgresql, redis, kubernetes, etc.)

    Returns:
        Liste des snippets correspondants
    """
    async with httpx.AsyncClient() as client:
        try:
            params = {}
            if type:
                params["type"] = type
            if service:
                params["service"] = service

            response = await client.get(
                f"{API_BASE_URL}/snippets", params=params, timeout=10.0
            )
            response.raise_for_status()
            snippets = response.json()

            if not snippets:
                filters = []
                if type:
                    filters.append(f"type='{type}'")
                if service:
                    filters.append(f"service='{service}'")
                filter_str = " et ".join(filters) if filters else ""
                return (
                    f"Aucun snippet trouvé{' avec ' + filter_str if filter_str else ''}"
                )

            results = [f"Snippets trouvés ({len(snippets)}):\n"]
            for snippet in snippets:
                results.append(f"\n💻 {snippet['title']}")
                results.append(f"   ID: {snippet['snippet_id']}")
                results.append(f"   Langage: {snippet['language']}")
                results.append(f"   Type: {snippet['type']}")
                if snippet["service"]:
                    results.append(f"   Service: {snippet['service']}")

            return "\n".join(results)

        except httpx.HTTPError as e:
            return f"Erreur: {str(e)}"


@mcp.tool()
async def get_available_tags() -> str:
    """
    Liste tous les tags disponibles dans DataHub.

    Returns:
        Liste des tags avec leurs descriptions
    """
    async with httpx.AsyncClient() as client:
        try:
            response = await client.get(f"{API_BASE_URL}/tags", timeout=10.0)
            response.raise_for_status()
            tags = response.json()

            results = [f"Tags disponibles ({len(tags)}):\n"]
            for tag_info in tags:
                results.append(f"  • {tag_info['tag']}: {tag_info['description']}")

            return "\n".join(results)

        except httpx.HTTPError as e:
            return f"Erreur: {str(e)}"


# ===== RESOURCES =====


@mcp.resource("datahub://docs/{doc_id}")
async def get_document(doc_id: str) -> str:
    """
    Récupère le contenu complet d'un document DataHub.

    Args:
        doc_id: Identifiant du document (ex: "rest-api-design", "graphql-intro")

    Returns:
        Contenu complet du document
    """
    async with httpx.AsyncClient() as client:
        try:
            response = await client.get(f"{API_BASE_URL}/docs/{doc_id}", timeout=10.0)
            response.raise_for_status()
            doc = response.json()

            result = []
            result.append(f"# {doc['title']}\n")
            result.append(f"**ID**: {doc['doc_id']}")
            result.append(f"**Owner**: {doc['owner']}")
            result.append(f"**Tags**: {', '.join(doc['tags'])}")
            result.append(f"**Créé**: {doc['created_at']}")
            result.append(f"**Mis à jour**: {doc['updated_at']}\n")
            result.append("---\n")
            result.append(doc["content"])

            return "\n".join(result)

        except httpx.HTTPStatusError as e:
            if e.response.status_code == 404:
                return f"Document '{doc_id}' non trouvé"
            return f"Erreur: {str(e)}"
        except httpx.HTTPError as e:
            return f"Erreur de connexion: {str(e)}"


@mcp.resource("datahub://snippets/{snippet_id}")
async def get_snippet(snippet_id: str) -> str:
    """
    Récupère un snippet de code complet.

    Args:
        snippet_id: Identifiant du snippet

    Returns:
        Snippet avec son code et métadonnées
    """
    async with httpx.AsyncClient() as client:
        try:
            response = await client.get(
                f"{API_BASE_URL}/snippets/{snippet_id}", timeout=10.0
            )
            response.raise_for_status()
            snippet = response.json()

            result = []
            result.append(f"# {snippet['title']}\n")
            result.append(f"**ID**: {snippet['snippet_id']}")
            result.append(f"**Langage**: {snippet['language']}")
            result.append(f"**Type**: {snippet['type']}")
            if snippet["service"]:
                result.append(f"**Service**: {snippet['service']}")
            result.append(f"**Description**: {snippet['description']}\n")
            result.append("## Code\n")
            result.append(f"```{snippet['language']}")
            result.append(snippet["code"])
            result.append("```")

            return "\n".join(result)

        except httpx.HTTPStatusError as e:
            if e.response.status_code == 404:
                return f"Snippet '{snippet_id}' non trouvé"
            return f"Erreur: {str(e)}"
        except httpx.HTTPError as e:
            return f"Erreur de connexion: {str(e)}"


if __name__ == "__main__":
    mcp.run(transport="http", port=8001, host="localhost")
