"""API DataHub - Point d'entrée FastAPI."""

import json
from pathlib import Path
from datetime import datetime
from typing import Optional
from fastapi import FastAPI, HTTPException, Query
from fastapi.responses import JSONResponse

from .models import Document, Snippet, SearchResult, HealthResponse

app = FastAPI(
    title="DataHub API",
    description="API REST pour centraliser la documentation technique, guides et snippets de code",
    version="1.0.0",
)

# Charger les données
DATA_DIR = Path(__file__).parent / "data"

with open(DATA_DIR / "documents.json", "r", encoding="utf-8") as f:
    DOCUMENTS = [Document(**doc) for doc in json.load(f)]

with open(DATA_DIR / "snippets.json", "r", encoding="utf-8") as f:
    SNIPPETS = [Snippet(**snippet) for snippet in json.load(f)]

with open(DATA_DIR / "tags.json", "r", encoding="utf-8") as f:
    TAGS = json.load(f)

# Extraire les owners uniques
OWNERS = list(set(doc.owner for doc in DOCUMENTS))


@app.get("/health", response_model=HealthResponse)
async def health():
    """Vérifier l'état de santé de l'API."""
    return HealthResponse(status="healthy", version="1.0.0")


@app.get("/docs", response_model=list[Document])
async def list_documents(
    tag: Optional[str] = Query(None, description="Filtrer par tag"),
    owner: Optional[str] = Query(None, description="Filtrer par propriétaire"),
    updated_after: Optional[str] = Query(
        None, description="Documents mis à jour après cette date (ISO 8601)"
    ),
):
    """
    Lister les documents techniques disponibles.

    Filtres disponibles :
    - tag : filtrer par tag
    - owner : filtrer par propriétaire
    - updated_after : documents mis à jour après une date donnée
    """
    results = DOCUMENTS.copy()

    if tag:
        results = [doc for doc in results if tag in doc.tags]

    if owner:
        results = [doc for doc in results if doc.owner == owner]

    if updated_after:
        try:
            date_filter = datetime.fromisoformat(updated_after.replace("Z", "+00:00"))
            results = [doc for doc in results if doc.updated_at >= date_filter]
        except ValueError:
            raise HTTPException(
                status_code=400,
                detail="Format de date invalide. Utilisez le format ISO 8601.",
            )

    return results


@app.get("/docs/{doc_id}", response_model=Document)
async def get_document(doc_id: str):
    """Récupérer un document spécifique par son ID."""
    for doc in DOCUMENTS:
        if doc.doc_id == doc_id:
            return doc

    raise HTTPException(status_code=404, detail=f"Document '{doc_id}' non trouvé")


@app.get("/search", response_model=SearchResult)
async def search(
    q: str = Query(..., description="Requête de recherche"),
    scope: str = Query(
        "docs", description="Portée de la recherche : 'docs' ou 'snippets'"
    ),
    limit: int = Query(10, ge=1, le=100, description="Nombre maximum de résultats"),
):
    """
    Rechercher dans les documents ou snippets.

    La recherche porte sur :
    - Pour les docs : titre, contenu, tags
    - Pour les snippets : titre, description, code, langage
    """
    q_lower = q.lower()
    results = []

    if scope == "docs":
        for doc in DOCUMENTS:
            if (
                q_lower in doc.title.lower()
                or q_lower in doc.content.lower()
                or any(q_lower in tag.lower() for tag in doc.tags)
            ):
                results.append(doc)
    elif scope == "snippets":
        for snippet in SNIPPETS:
            if (
                q_lower in snippet.title.lower()
                or q_lower in snippet.description.lower()
                or q_lower in snippet.code.lower()
                or q_lower in snippet.language.lower()
            ):
                results.append(snippet)
    else:
        raise HTTPException(
            status_code=400,
            detail="Le paramètre 'scope' doit être 'docs' ou 'snippets'",
        )

    # Limiter les résultats
    results = results[:limit]

    return SearchResult(results=results, total=len(results), query=q)


@app.get("/snippets", response_model=list[Snippet])
async def list_snippets(
    type: Optional[str] = Query(
        None, description="Filtrer par type (query, function, config, etc.)"
    ),
    service: Optional[str] = Query(None, description="Filtrer par service"),
):
    """
    Lister les snippets de code disponibles.

    Filtres disponibles :
    - type : type de snippet (query, function, config, middleware, migration)
    - service : service concerné (postgresql, redis, kubernetes, etc.)
    """
    results = SNIPPETS.copy()

    if type:
        results = [s for s in results if s.type == type]

    if service:
        results = [s for s in results if s.service == service]

    return results


@app.get("/snippets/{snippet_id}", response_model=Snippet)
async def get_snippet(snippet_id: str):
    """Récupérer un snippet spécifique par son ID."""
    for snippet in SNIPPETS:
        if snippet.snippet_id == snippet_id:
            return snippet

    raise HTTPException(status_code=404, detail=f"Snippet '{snippet_id}' non trouvé")


@app.get("/tags", response_model=list[dict])
async def list_tags():
    """Lister tous les tags disponibles avec leurs descriptions."""
    return TAGS


@app.get("/owners", response_model=list[str])
async def list_owners():
    """Lister tous les propriétaires de documents."""
    return sorted(OWNERS)
