import java.util.*;


public class Tabelle {

    // Stack von Scopes (jeder Scope ist eine Map)
    private final Stack<Map<String, Symbol>> scopes;
    HashMap<Integer, Map<String, Symbol>> allScopes = new HashMap<>();
    int scopeCounter;
    int scopeID;
    Stack<Integer> stackID = new Stack<>();

    public Tabelle() {
        scopes = new Stack<>();
        openScope(); // Initialer globaler Scope
        scopeCounter=0;
    }

    // Scope-Management
    public void openScope() {
        scopeCounter++;
        scopeID=scopeCounter;
        stackID.add(scopeID);
        scopes.push(new HashMap<>());
    }

    public void closeScope() {
        if (!scopes.isEmpty()) {
            allScopes.put(stackID.peek(), this.scopes.getLast());
            stackID.pop();
            scopes.pop();
        } else {
            System.out.println("Fehler: Kein Scope zum Schließen vorhanden.");
        }

    }

    // Symbol hinzufügen
    public void addSymbol(String name, Symbol symbol) {
        if (scopes.isEmpty()) {
            throw new IllegalStateException("Kein Scope vorhanden.");
        }

        Map<String, Symbol> currentScope = scopes.peek();
        if (currentScope.containsKey(name)) {
            System.out.println("Fehler: Symbol '" + name + "' ist bereits im aktuellen Scope definiert.");
        } else {
            currentScope.put(name, symbol);
        }
    }

    // Symbol nachschlagen (Scope-Kette durchlaufen)
    public Symbol lookup(String name) {
        for (int i = scopes.size() - 1; i >= 0; i--) {
            Map<String, Symbol> scope = scopes.get(i);
            if (scope.containsKey(name)) {
                return scope.get(name);
            }
        }
        return null; // Nicht gefunden
    }

    // Prüfen, ob ein Symbol nur im aktuellen Scope existiert
    public boolean containsInCurrentScope(String name) {
        if (scopes.isEmpty()) return false;
        return scopes.peek().containsKey(name);
    }

    // Für spätere Läufe: Zugriff auf die Tabelle
    public List<Map<String, Symbol>> getAllScopes() {
        return Collections.unmodifiableList(scopes);
    }

    // Symbol-Klasse
    public static class Symbol {
        private final String name;  // Name des Symbols
        private final String type;  // Typ der Variable oder Funktion
        private final boolean isFunction; // Ist es eine Funktion?
        private final List<String> parameterTypes; // Parameter (nur für Funktionen)
        private final String returnType; // Rückgabetyp (nur für Funktionen)

        // Konstruktor für Variablen
        public Symbol(String name, String type) {
            this.name = name;
            this.type = type;
            this.isFunction = false;
            this.parameterTypes = null;
            this.returnType = null;
        }

        // Konstruktor für Funktionen
        public Symbol(String name, String returnType, List<String> parameterTypes) {
            this.name = name;
            this.type = "function"; // Funktion wird als eigener Typ gespeichert
            this.isFunction = true;
            this.parameterTypes = parameterTypes;
            this.returnType = returnType;
        }

        // Getter
        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public boolean isFunction() {
            return isFunction;
        }

        public List<String> getParameterTypes() {
            return parameterTypes;
        }

        public String getReturnType() {
            return returnType;
        }

        @Override
        public String toString() {
            if (isFunction) {
                return "Funktion " + name + "(" + String.join(", ", parameterTypes) + "): " + returnType;
            } else {
                return "Variable " + name + ": " + type;
            }
        }
    }
}


