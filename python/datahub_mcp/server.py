"""Serveur MCP pour DataHub - À compléter selon les exercices."""

from fastmcp import FastMCP

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
#     """Recherche dans DataHub."""
#     pass


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
