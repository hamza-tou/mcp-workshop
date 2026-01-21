def sanitize_content(content: str) -> str:
    dangerous_patterns = [
        "IGNORE ALL PREVIOUS INSTRUCTIONS",
        "IGNORE PREVIOUS INSTRUCTIONS",
        "You are now",
        "Tu es maintenant",
        "Forget everything",
        "Oublie tout",
    ]

    for pattern in dangerous_patterns:
        if pattern.lower() in content.lower():
            print(f"⚠️  Prompt injection détecté: {pattern}")
            return "[CONTENU FILTRÉ: tentative d'injection détectée]"

    return content
