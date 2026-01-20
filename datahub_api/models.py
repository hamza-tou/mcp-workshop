"""Modèles Pydantic pour l'API DataHub."""

from datetime import datetime
from typing import Optional
from pydantic import BaseModel, Field


class Document(BaseModel):
    """Document technique."""

    doc_id: str = Field(..., description="Identifiant unique du document")
    title: str = Field(..., description="Titre du document")
    content: str = Field(..., description="Contenu du document")
    tags: list[str] = Field(default_factory=list, description="Tags associés")
    owner: str = Field(..., description="Propriétaire du document")
    created_at: datetime = Field(..., description="Date de création")
    updated_at: datetime = Field(..., description="Date de dernière modification")


class Snippet(BaseModel):
    """Snippet de code."""

    snippet_id: str = Field(..., description="Identifiant unique du snippet")
    title: str = Field(..., description="Titre du snippet")
    code: str = Field(..., description="Code du snippet")
    language: str = Field(..., description="Langage de programmation")
    type: str = Field(
        ..., description="Type de snippet (query, function, config, etc.)"
    )
    service: Optional[str] = Field(None, description="Service concerné")
    description: str = Field(..., description="Description du snippet")
    created_at: datetime = Field(..., description="Date de création")


class SearchResult(BaseModel):
    """Résultat de recherche."""

    results: list[Document | Snippet] = Field(..., description="Liste des résultats")
    total: int = Field(..., description="Nombre total de résultats")
    query: str = Field(..., description="Requête de recherche")


class HealthResponse(BaseModel):
    """Réponse du endpoint health."""

    status: str = Field(..., description="Statut de l'API")
    version: str = Field(default="1.0.0", description="Version de l'API")
