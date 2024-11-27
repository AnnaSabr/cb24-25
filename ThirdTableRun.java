import java.util.List;
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
            return "int";
        }else if(ctx.getChild(1) != null && (ctx.getChild(1).getText().equals("<") || ctx.getChild(1).getText().equals(">") ||  ctx.getChild(1).getText().equals("==")||  ctx.getChild(1).getText().equals("!="))){
            return "bool";
        } else if (ctx.getChild(0).getText().equals("{")) {
            return "array";
        }
        return "unknown";
    }

    public String searchAllTables(String name){
        Stack<Integer> stackIDcopy=stackID;
        while(!stackIDcopy.isEmpty()){
            Map<String, Tabelle.Symbol> currentScope=symbolTable.allScopes.get(stackIDcopy.pop());
            if(currentScope.get(name) != null){
                return currentScope.get(name).getType();
            }
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
        String type = ctx.type().getText();
        if(ctx.expr()!=null) {
            String exprType = getExpressionType(ctx.expr());

            if (!type.equals(exprType)) {
                System.err.println("Typfehler bei Deklaration: Variable '" + ctx.ID().getText() +
                        "' hat Typ '" + type + "', Ausdruck hat Typ '" + exprType + "'.");
            }
        }

        super.enterVardecl(ctx);
    }

    @Override
    public void exitCond(miniCParser.CondContext ctx) {
        String expectedType="bool";
        String exprType = getExpressionType(ctx.expr());
        if (!expectedType.equals(exprType)){
            System.err.println("Typfehler bei if-Anweisung: " + exprType + " gefunden anstatt bool");
        }
        super.exitCond(ctx);
    }

    @Override
    public void exitWhile(miniCParser.WhileContext ctx) {
        String expectedType="bool";
        String exprType = getExpressionType(ctx.expr());
        if (!expectedType.equals(exprType)){
            System.err.println("Typfehler bei while-Anweisung: " + exprType + " gefunden anstatt bool");
        }
        super.exitWhile(ctx);
    }

    @Override
    public void enterProgram(miniCParser.ProgramContext ctx) {
        scopeID=1;
        scopeCounter=1;
        stackID.add(scopeID);
        super.enterProgram(ctx);
    }

    @Override
    public void enterBlock(miniCParser.BlockContext ctx) {
        stackID.add(scopeCounter++);
        super.enterBlock(ctx);
    }

    @Override
    public void exitBlock(miniCParser.BlockContext ctx) {
        super.exitBlock(ctx);
    }

    @Override
    public void exitFncall(miniCParser.FncallContext ctx) {
        String type = searchAllTables(ctx.ID().getText());
        if(type==null){
            System.err.println("Funktion nicht gefunden: " + ctx.ID().getText());
        }
        else if(!type.equals("function")){
            System.err.println("keine Funktion: " + ctx.ID().getText());
        }
        Stack<Integer> stackIDcopy=stackID;
        Tabelle.Symbol symbol;
        while(!stackIDcopy.isEmpty()){
            Map<String, Tabelle.Symbol> currentScope=symbolTable.allScopes.get(stackIDcopy.pop());
            if(currentScope.get(ctx.ID().getText()) != null){
                symbol=currentScope.get(ctx.ID().getText());
                List<String> paramsFromDef = symbol.getParameterTypes();
                String[] argumente=ctx.args().getText().split(",");
                if(!((paramsFromDef.size()/2)==argumente.length)){
                 System.err.println("Anzahl Argumente ungültig");
                 break;
                }
                for(int i=0;i<argumente.length;i++){
                    String paramType = paramsFromDef.get(i*2);
                    String matchingArgument=argumente[i];
                    boolean matching=true;
                    if (paramType.equals("int") && matchingArgument.equals("^\\d+$")){
                    }
                    else if (paramType.equals("string") && matchingArgument.equals("^\"([a-zA-Z]+)\"$")){}
                    else if (paramType.equals("bool") && matchingArgument.equals("^true$")){}
                    else if (paramType.equals("bool") && matchingArgument.equals("^false$")){}
                    else if (searchAllTables(matchingArgument)!=null){
                        String varType=searchAllTables(matchingArgument);
                        if(!varType.equals(type)){
                            matching=false;
                        }
                    }else {
                        matching=false;
                    }
                    if(!matching){
                        System.err.println("Argumente und Parameter stimmen nicht überein" + matchingArgument + paramType);
                    }

                }
            }
        }
        super.exitFncall(ctx);
    }
}
