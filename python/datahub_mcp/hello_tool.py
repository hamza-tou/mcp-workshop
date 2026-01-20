from fastmcp import FastMCP

mcp = FastMCP("Demo 🚀")


@mcp.tool("Description: do a 'magic' addition between two numbers")
def magic_add(a: int, b: int) -> int:
    magic_number = 3
    return a + b + magic_number


if __name__ == "__main__":
    mcp.run(transport="http", port=8001, host="localhost")
