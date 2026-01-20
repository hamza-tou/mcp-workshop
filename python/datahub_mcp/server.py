"""Serveur MCP pour DataHub - À compléter selon les exercices."""

from fastmcp import FastMCP
# import httpx  # Décommenter pour US2

# TODO: Créer une instance FastMCP avec un nom descriptif
# mcp = FastMCP("DataHub MCP Server")

# URL de l'API DataHub
API_BASE_URL = "http://localhost:8000"


# ===== TOOLS =====

# US1 - Tool simple pour tester le serveur
# TODO: Décommenter
# @mcp.tool()
# def hello_server() -> str:
#     """Tool de test simple qui retourne un message de bienvenue."""
#     return "Hello mcp server here !"


# US2 - Tool de recherche DataHub
# TODO: Implémenter après US1
# @mcp.tool()
# async def search_datahub(query: str, scope: str = "docs", limit: int = 10) -> str:
#     """
#     Recherche dans DataHub (documents ou snippets).
#
#     Args:
#         query: Texte à rechercher (ex: "graphql", "kubernetes")
#         scope: "docs" pour documents, "snippets" pour code
#         limit: Nombre maximum de résultats (1-100)
#
#     Returns:
#         Résultats formatés en texte lisible
#     """
#     # Client HTTP pour appeler l'API DataHub
#     async with httpx.AsyncClient() as client:
#         try:
#             response = await client.get(
#                 f"{API_BASE_URL}/search",
#                 params={"q": query, "scope": scope, "limit": limit}
#             )
#             response.raise_for_status()
#             data = response.json()
#
#             # TODO: Formater et retourner les résultats
#
#         except httpx.HTTPError as e:
#             return f"Erreur: {str(e)}"


# ===== RESOURCES =====
# TODO: Implémenter les resources MCP selon les exercices
# Exemple :
# @mcp.resource("datahub://document/{doc_id}")
# async def get_document(doc_id: str) -> str:
#     """Récupère un document DataHub."""
#     pass


if __name__ == "__main__":
    # TODO: Décommenter pour lancer le serveur MCP
    # mcp.run(transport="http",port=8001,host="localhost")
    pass
