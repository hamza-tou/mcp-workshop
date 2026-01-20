import httpx
from typing import Optional, List
from datetime import datetime
from pydantic import BaseModel


API_BASE_URL = "http://localhost:8000"


class Document(BaseModel):
    doc_id: str
    title: str
    content: str
    owner: str
    tags: List[str]
    created_at: datetime
    updated_at: datetime


class Snippet(BaseModel):
    snippet_id: str
    title: str
    language: str
    code: str
    type: str
    service: Optional[str] = None
    description: str


class Tag(BaseModel):
    tag: str
    description: str


class DocumentSearchResult(BaseModel):
    doc_id: str
    title: str
    content: str
    tags: List[str]
    owner: str


class SnippetSearchResult(BaseModel):
    snippet_id: str
    title: str
    language: str
    type: str
    description: str


class SearchResponse(BaseModel):
    total: int
    results: List[DocumentSearchResult | SnippetSearchResult]


async def search(query: str, scope: str = "docs", limit: int = 10) -> SearchResponse:
    """
    Recherche dans DataHub.

    Args:
        query: Texte à rechercher (ex: "graphql", "kubernetes")
        scope: Portée de la recherche - "docs" pour documents ou "snippets" pour code
        limit: Nombre maximum de résultats à retourner (1-100)

    Returns:
        SearchResponse contenant les résultats

    Raises:
        httpx.HTTPError: En cas d'erreur HTTP
    """
    async with httpx.AsyncClient() as client:
        response = await client.get(
            f"{API_BASE_URL}/search",
            params={"q": query, "scope": scope, "limit": limit},
        )
        response.raise_for_status()
        data = response.json()

        # Parse les résultats selon le scope
        if scope == "docs":
            results = [DocumentSearchResult(**item) for item in data["results"]]
        else:
            results = [SnippetSearchResult(**item) for item in data["results"]]

        return SearchResponse(total=data["total"], results=results)


async def list_documents(tag: Optional[str] = None) -> List[Document]:
    """
    Liste les documents DataHub.

    Args:
        tag: Tag optionnel pour filtrer les documents

    Returns:
        Liste de documents

    Raises:
        httpx.HTTPError: En cas d'erreur HTTP
    """
    async with httpx.AsyncClient() as client:
        params = {"tag": tag} if tag else {}
        response = await client.get(f"{API_BASE_URL}/documents", params=params)
        response.raise_for_status()
        data = response.json()
        return [Document(**doc) for doc in data]


async def get_document(doc_id: str) -> Document:
    """
    Récupère un document spécifique.

    Args:
        doc_id: Identifiant du document

    Returns:
        Document complet

    Raises:
        httpx.HTTPError: En cas d'erreur HTTP
    """
    async with httpx.AsyncClient() as client:
        response = await client.get(f"{API_BASE_URL}/documents/{doc_id}")
        response.raise_for_status()
        data = response.json()
        return Document(**data)


async def list_snippets(
    type: Optional[str] = None, service: Optional[str] = None
) -> List[Snippet]:
    """
    Liste les snippets de code.

    Args:
        type: Type de snippet (query, function, config, middleware, migration)
        service: Service concerné (postgresql, redis, kubernetes, etc.)

    Returns:
        Liste de snippets

    Raises:
        httpx.HTTPError: En cas d'erreur HTTP
    """
    async with httpx.AsyncClient() as client:
        params = {}
        if type:
            params["type"] = type
        if service:
            params["service"] = service

        response = await client.get(f"{API_BASE_URL}/snippets", params=params)
        response.raise_for_status()
        data = response.json()
        return [Snippet(**snippet) for snippet in data]


async def get_snippet(snippet_id: str) -> Snippet:
    """
    Récupère un snippet spécifique.

    Args:
        snippet_id: Identifiant du snippet

    Returns:
        Snippet complet avec son code

    Raises:
        httpx.HTTPError: En cas d'erreur HTTP
    """
    async with httpx.AsyncClient() as client:
        response = await client.get(f"{API_BASE_URL}/snippets/{snippet_id}")
        response.raise_for_status()
        data = response.json()
        return Snippet(**data)


async def get_tags() -> List[Tag]:
    """
    Liste tous les tags disponibles.

    Returns:
        Liste de tags avec leurs descriptions

    Raises:
        httpx.HTTPError: En cas d'erreur HTTP
    """
    async with httpx.AsyncClient() as client:
        response = await client.get(f"{API_BASE_URL}/tags")
        response.raise_for_status()
        data = response.json()
        return [Tag(**tag) for tag in data]
