import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Tabelle {

    // Symboltabelle für einen Scope: Speichert Variablen und Funktionen
    private Stack<Map<String, Symbol>> scopes;

    public Tabelle() {
        // Initialisieren des Stack der Scopes
        scopes = new Stack<>();
        // Erster Scope (globaler Scope)
        openScope();
    }

    // Öffnet einen neuen Scope (für Funktionen, Blöcke, etc.)
    public void openScope() {
        scopes.push(new HashMap<>());
    }

    // Schließt den aktuellen Scope
    public void closeScope() {
        if (!scopes.isEmpty()) {
            scopes.pop();
        }
    }

    // Fügt eine neue Variable oder Funktion zur Tabelle hinzu
    public void addSymbol(String name, Symbol symbol) {
        Map<String, Symbol> currentScope = scopes.peek();
        if (currentScope.containsKey(name)) {
            // Fehler: Symbol bereits im aktuellen Scope definiert
            System.out.println("Fehler: Symbol '" + name + "' ist bereits im aktuellen Scope definiert.");
        } else {
            currentScope.put(name, symbol);
        }
    }

    // Sucht ein Symbol im aktuellen Scope und allen übergeordneten Scopes
    public Symbol lookup(String name) {
        for (int i = scopes.size() - 1; i >= 0; i--) {
            Map<String, Symbol> scope = scopes.get(i);
            if (scope.containsKey(name)) {
                return scope.get(name);
            }
        }
        return null;
    }

    // Prüft, ob ein Symbol im aktuellen Scope existiert
    public boolean contains(String name) {
        return scopes.contains(name);
    }

    // Symboldaten für Variablen und Funktionen
    public static class Symbol {
        String name;  // Name des Symbols
        String type;  // Datentyp der Variable/Funktion
        boolean isFunction; // Kennzeichnet, ob es sich um eine Funktion handelt

        public Symbol(String name, String type, boolean isFunction) {
            this.name = name;
            this.type = type;
            this.isFunction = isFunction;
        }

        @Override
        public String toString() {
            return (isFunction ? "Funktion " : "Variable ") + name + ": " + type;
        }
    }
}


