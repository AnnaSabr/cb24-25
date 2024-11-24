import java.util.ArrayList;
import java.util.List;

// Abstrakte Basis-Node für alle AST-Knoten
public abstract class ast {
    String[] text; // Informationen, die den AST-Knoten beschreiben
    List<ast> children = new ArrayList<>(); // Kindknoten

    // Konstruktor
    ast(String[] text, List<ast> children) {
        this.text = text;
        this.children = children;
    }

    // Elternknoten
    private ast parent;

    public void addChild(ast newChild) {
        this.children.add(newChild);
    }

    public ast getParent() {
        return this.parent;
    }

    public void setParent(ast parent) {
        this.parent = parent;
    }

    // Abstrakte Methode zum Drucken des AST (jede abgeleitete Klasse implementiert dies)
    abstract void print(String indent);
}

// Program-Knoten
class programNode extends ast {
    programNode(String[] text, List<ast> children) {
        super(text, children);
    }

    public void print(String indent) {
        System.out.println(indent + "Program:");
        for (ast child : children) {
            child.print(indent + "  "); // Erhöht die Einrückung für Kinder
        }
    }
}

// Variablen-Deklarationsknoten
class vardeclNode extends ast {
    vardeclNode(String[] text, List<ast> children) {
        super(text, children);
    }

    public void print(String indent) {
        System.out.println(indent + "Variable Declaration:");
        System.out.println(indent + "  Type: " + text[0] + ", Name: " + text[1] +
                (text[2] != null ? ", Array Size: " + text[2] : ""));
    }
}

// Zuweisungsknoten
class assignNode extends ast {
    assignNode(String[] text, List<ast> children) {
        super(text, children);
    }

    public void print(String indent) {
        System.out.println(indent + "Assignment:");
        System.out.println(indent + "  Variable: " + text[0] +
                (text[1] != null ? ", Array Index: " + text[1] : ""));
        for (ast child : children) {
            child.print(indent + "  "); // Erhöht die Einrückung für Kinder
        }
    }
}

// Funktionsdeklarationsknoten
class fndeclNode extends ast {
    fndeclNode(String[] text, List<ast> children) {
        super(text, children);
    }

    public void print(String indent) {
        System.out.println(indent + "Function Declaration:");
        System.out.println(indent + "  Return Type: " + text[0] + ", Name: " + text[1]);
        if (text.length>2) {
            System.out.print(indent + "  Parameters: ");
            for (int h =0; h<text.length-1; h+=2) {
                System.out.print(text[h]+" "+text[h+1]+", ");
            }
            System.out.println("");

        }
        for (ast child : children) {
            child.print(indent + "  "); // Erhöht die Einrückung für Kinder
        }
    }
}

// Return-Knoten
class returnNode extends ast {
    returnNode(String[] text, List<ast> children) {
        super(text, children);
    }

    public void print(String indent) {
        System.out.print(indent + "Return Statement: ");
        for (int c =0; c<text.length; c++) {
            System.out.print(text[c]);
        }
        System.out.println();
    }
}

// Ausdrucksknoten
class exprNode extends ast {
    exprNode(String[] text, List<ast> children) {
        super(text, children);
    }

    public void print(String indent) {
        System.out.println(indent + "Expression:");
        if (text.length == 3) {
            System.out.println(indent + "  Left: " + text[0] + ", Operator: " + text[1] + ", Right: " + text[2]);
        } else if (text.length == 1) {
            System.out.println(indent + "  Value: " + text[0]);
        }
        for (ast child : children) {
            child.print(indent + "  "); // Erhöht die Einrückung für Kinder
        }
    }
}

// If-Knoten
class ifNode extends ast {
    ifNode(String[] text, List<ast> children) {
        super(text, children);
    }

    public void print(String indent) {
        System.out.println(indent + "If Statement:");
        if (text.length > 1 && text[1] != null) {
            System.out.println(indent + "  contains Else Block");
        }
        System.out.println(indent + "  Condition: ");
        for (ast child : children) {
            child.print(indent + "  "); // Erhöht die Einrückung für Kinder
        }
    }
}

// While-Knoten
class whileNode extends ast {
    whileNode(String[] text, List<ast> children) {
        super(text, children);
    }

    public void print(String indent) {
        System.out.println(indent + "While Loop:");
        System.out.println(indent + "  Condition: " + text[0]);
        for (ast child : children) {
            child.print(indent + "  "); // Erhöht die Einrückung für Kinder
        }
    }
}

// Block-Knoten
class blockNode extends ast {
    blockNode(String[] text, List<ast> children) {
        super(text, children);
    }

    public void print(String indent) {
        System.out.println(indent + "Block:");
        for (ast child : children) {
            child.print(indent + "  "); // Erhöht die Einrückung für Kinder
        }
    }
}

// Funktionsaufrufknoten
class fncallNode extends ast {
    fncallNode(String[] text, List<ast> children) {
        super(text, children);
    }

    public void print(String indent) {
        System.out.println(indent + "Function Call:");
        System.out.println(indent + "  Function: " + text[0]);
        if (text.length > 1 && text[1] != null) {
            System.out.println(indent + "  Arguments: " + text[1]);
        }
        for (ast child : children) {
            child.print(indent + "  "); // Erhöht die Einrückung für Kinder
        }
    }
}

// Argument-Knoten
class argsNode extends ast {
    argsNode(String[] text, List<ast> children) {
        super(text, children);
    }

    public void print(String indent) {
        System.out.println(indent + "Arguments:");
        for (ast child : children) {
            child.print(indent + "  "); // Erhöht die Einrückung für Kinder
        }
    }
}
