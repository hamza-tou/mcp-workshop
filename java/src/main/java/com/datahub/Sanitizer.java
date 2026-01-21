package com.datahub;

public class Sanitizer {
    
    public static String sanitizeContent(String content) {
        String[] dangerousPatterns = {
            "IGNORE ALL PREVIOUS INSTRUCTIONS",
            "IGNORE PREVIOUS INSTRUCTIONS",
            "You are now",
            "Tu es maintenant",
            "Forget everything",
            "Oublie tout"
        };

        String contentLower = content.toLowerCase();
        for (String pattern : dangerousPatterns) {
            if (contentLower.contains(pattern.toLowerCase())) {
                System.out.println("⚠️  Prompt injection détecté: " + pattern);
                return "[CONTENU FILTRÉ: tentative d'injection détectée]";
            }
        }

        return content;
    }
}
