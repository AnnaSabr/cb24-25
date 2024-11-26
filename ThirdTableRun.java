import java.util.Map;
import java.util.Stack;

public class ThirdTableRun extends ASTListener{

    int scopeCounter;
    int scopeID;
    Stack<Integer> stackID = new Stack<>();
    private Tabelle symbolTable;

    public ThirdTableRun(Tabelle symbolTable) {
        this.symbolTable = symbolTable;
    }

    private String getExpressionType(miniCParser.ExprContext ctx) {
        if (ctx.NUMBER() != null) {
            return "int";
        } else if (ctx.STRING() != null) {
            return "string";
        } else if (ctx.BOOL() != null) {
            return "bool";
        } else if (ctx.ID() != null) {
            return searchAllTables(ctx.ID().getText()); // Typ der Variable
        } else if (ctx.getChild(1) != null && (ctx.getChild(1).getText().equals("+") || ctx.getChild(1).getText().equals("-") || ctx.getChild(1).getText().equals("/") || ctx.getChild(1).getText().equals("*"))) {
            return "int"; //
        }else if(ctx.getChild(1) != null && (ctx.getChild(1).getText().equals("<") || ctx.getChild(1).getText().equals(">") ||  ctx.getChild(1).getText().equals("==")||  ctx.getChild(1).getText().equals("!="))){
            return "bool";
        }
        return "unknown";
    }

    public String searchAllTables(String name){
        Stack<Integer> stackIDcopy=stackID;
        for(int a=0; a<stackIDcopy.size(); a++){
            Map<String, Tabelle.Symbol> currentScope=symbolTable.allScopes.get(stackIDcopy.pop());
            return currentScope.get(name).getType();
        }
        return null;
    }

    @Override
    public void exitAssign(miniCParser.AssignContext ctx) {
        String varName = ctx.ID().getText();
        String varType = searchAllTables(varName);

        // Rechte Seite: Ausdruck
        String exprType = getExpressionType(ctx.expr());

        // Typprüfung
        if (!varType.equals(exprType)) {
            System.err.println("Typfehler bei Zuweisung: Variable '" + varName +
                "' hat Typ '" + varType + "', Ausdruck hat Typ '" + exprType + "'.");
        }
    }

    @Override
    public void exitVardecl(miniCParser.VardeclContext ctx) {
        String type = ctx.ID().getText();
        String exprType = getExpressionType(ctx.expr());

        if (!type.equals(exprType)) {
            System.err.println("Typfehler bei Zuweisung: Variable '" + ctx.ID().getText() +
                "' hat Typ '" + type + "', Ausdruck hat Typ '" + exprType + "'.");
        }

        super.enterVardecl(ctx);
    }

    @Override
    public void exitCond(miniCParser.CondContext ctx) {
        String expectedType="bool";
        String exprType = getExpressionType(ctx.expr());
        if (!expectedType.equals(exprType)){
            System.err.println("Typfehler bei if-Anweisung: " + exprType + "gefunden anstatt bool");
        }
        super.exitCond(ctx);
    }

    @Override
    public void exitWhile(miniCParser.WhileContext ctx) {
        String expectedType="bool";
        String exprType = getExpressionType(ctx.expr());
        if (!expectedType.equals(exprType)){
            System.err.println("Typfehler bei while-Anweisung: " + exprType + "gefunden anstatt bool");
        }
        super.exitWhile(ctx);
    }

    @Override
    public void enterProgram(miniCParser.ProgramContext ctx) {
        scopeID=0;
        scopeCounter=0;
        stackID.add(scopeID);
        super.enterProgram(ctx);
    }

    @Override
    public void enterBlock(miniCParser.BlockContext ctx) {
        scopeCounter++;
        scopeID=scopeCounter;
        stackID.add(scopeID);
        super.enterBlock(ctx);
    }

    @Override
    public void exitBlock(miniCParser.BlockContext ctx) {
        stackID.pop();
        super.exitBlock(ctx);
    }
}
