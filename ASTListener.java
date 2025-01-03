import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.List;


public class ASTListener extends miniCBaseListener {

    //use to start traversing
    private ast firstNode;
    private List<ast> allParents = new ArrayList<>();
    private Tabelle symbolTable = new Tabelle();

    public Tabelle getSymbolTable() {
        return this.symbolTable;
    }

    public void addParent(ast parent) {
        this.allParents.add(parent);
    }

    public void removeParent() {
        this.allParents.removeLast();
    }

    public ast getCurrentParentNode() {
        return this.allParents.get(this.allParents.size() - 1);
    }

    public ast getFirstNode() {
        return firstNode;
    }

    //to be excluded: statement, array, params, args, type

    @Override
    public void enterProgram(miniCParser.ProgramContext ctx) {
        List<ast> children = new ArrayList<>();
        String[] text = null;
        this.firstNode = new programNode(text, children);
        this.firstNode.setParent(null);
        this.addParent(firstNode);
        symbolTable.openScope();
        super.enterProgram(ctx);
    }

    @Override
    public void exitProgram(miniCParser.ProgramContext ctx) {
        symbolTable.closeScope();
        super.exitProgram(ctx);
    }

    @Override
    public void enterVardecl(miniCParser.VardeclContext ctx) {
        List<ast> children = new ArrayList<>();

        //getting info from ctx
        String type = ctx.type().getText();
        String name = ctx.ID().getText();
        String arraySize = null;

        String[] text = {type, name, arraySize};

        if (symbolTable.containsInCurrentScope(name)) {
            System.out.println("Fehler: Variable '" + name + "' ist bereits im aktuellen Scope definiert.");
        } else if (symbolTable.lookup(name) != null) {
            System.out.println("Fehler: Variable '" + name + "' ist bereits in einem oberen Scope definiert.");
        } else {
            symbolTable.addSymbol(name, new Tabelle.Symbol(name, type));
        }

        vardeclNode vardeclNode = new vardeclNode(text, children);

        //set parent and child link in ast nodes
        vardeclNode.setParent(this.getCurrentParentNode());
        vardeclNode.getParent().addChild(vardeclNode);

        this.addParent(vardeclNode);
        super.enterVardecl(ctx);
    }

    @Override
    public void enterArray(miniCParser.ArrayContext ctx) {
        List<ast> children = new ArrayList<>();
        String[] text = new String[]{};
        if (ctx.NUMBER() != null) {
            String size = ctx.NUMBER().getText();
            text = new String[]{size};
        }

        arrayNode arrayNode = new arrayNode(text, children);

        arrayNode.setParent(this.getCurrentParentNode());
        arrayNode.getParent().addChild(arrayNode);

        this.addParent(arrayNode);
        super.enterArray(ctx);
    }

    @Override
    public void exitArray(miniCParser.ArrayContext ctx) {
        this.removeParent();
        super.exitArray(ctx);
    }

    @Override
    public void exitVardecl(miniCParser.VardeclContext ctx) {
        this.removeParent();
        super.exitVardecl(ctx);
    }

    @Override
    public void enterAssign(miniCParser.AssignContext ctx) {
        List<ast> children = new ArrayList<>();
        String variableName = ctx.ID().toString();
        String array = null;
        if (ctx.array() != null) {
            array = ctx.array().toString();
        }

        String[] text = {variableName, array};
        assignNode assignNode = new assignNode(text, children);

        assignNode.setParent(this.getCurrentParentNode());
        assignNode.getParent().addChild(assignNode);

        this.addParent(assignNode);
        super.enterAssign(ctx);
    }

    @Override
    public void exitAssign(miniCParser.AssignContext ctx) {
        this.removeParent();
        super.exitAssign(ctx);
    }

    @Override
    public void enterFndecl(miniCParser.FndeclContext ctx) {
        List<ast> children = new ArrayList<>();
        String funcReturn = ctx.type().getText();
        String functionName = ctx.ID().getText();


        ArrayList<String> parameters = new ArrayList<>();
        if (ctx.params() != null) {
            for (ParseTree a : ctx.params().children) {
                if (!a.getText().equals(",")) {
                    parameters.add(a.getText());
                }
            }
        }

        if (symbolTable.containsInCurrentScope(functionName)) {
            System.out.println("Fehler: Funktion '" + functionName + "' ist bereits im aktuellen Scope definiert.");
        } else if (symbolTable.lookup(functionName) != null) {
            System.out.println("Fehler: Funktion '" + functionName + "' ist bereits in einem oberen Scope definiert.");
        } else {
            symbolTable.addSymbol(functionName, new Tabelle.Symbol(functionName, funcReturn, parameters));
        }

        String[] text = new String[parameters.size() + 2];
        text[0] = funcReturn;
        text[1] = functionName;
        for (int a = 0; a < parameters.size(); a++) {
            text[a + 2] = parameters.get(a);
        }
        fndeclNode fncDeclNode = new fndeclNode(text, children);

        fncDeclNode.setParent(this.getCurrentParentNode());
        fncDeclNode.getParent().addChild(fncDeclNode);

        this.addParent(fncDeclNode);
        super.enterFndecl(ctx);
    }

    @Override
    public void exitFndecl(miniCParser.FndeclContext ctx) {
        this.removeParent();
        super.exitFndecl(ctx);
    }

    @Override
    public void enterExpr(miniCParser.ExprContext ctx) {
        List<ast> children = new ArrayList<>();
        String[] text = null;
        //expr with 3 parts
        if (ctx.getChildCount() == 3) {
            String left = ctx.getChild(0).getText();
            String operator = ctx.getChild(1).getText();
            String right = ctx.getChild(2).getText();
            text = new String[]{left, operator, right};
        }
        //number
        else if (ctx.NUMBER() != null) {
            String number = ctx.NUMBER().getText();
            text = new String[]{number};
        }
        // var
        else if (ctx.ID() != null) {
            String varName = ctx.ID().getText();
            text = new String[]{varName};
        }
        // func call
        else if (ctx.fncall() != null) {
            String fncall = ctx.fncall().getText();
            text = new String[]{fncall};
        }
        // string
        else if (ctx.STRING() != null) {
            String txt = ctx.STRING().getText();
            text = new String[]{txt};
        } else {
            String txt = ctx.getText();
            text = new String[]{txt};
        }
        exprNode expNode = new exprNode(text, children);

        expNode.setParent(this.getCurrentParentNode());
        expNode.getParent().addChild(expNode);

        this.addParent(expNode);
        super.enterExpr(ctx);
    }

    @Override
    public void exitExpr(miniCParser.ExprContext ctx) {
        this.removeParent();
        super.exitExpr(ctx);
    }

    @Override
    public void enterBlock(miniCParser.BlockContext ctx) {
        symbolTable.openScope();
        List<ast> children = new ArrayList<>();
        blockNode blockNode = new blockNode(null, children);
        blockNode.setParent(this.getCurrentParentNode());
        blockNode.getParent().addChild(blockNode);

        this.addParent(blockNode);
        super.enterBlock(ctx);
    }

    @Override
    public void exitBlock(miniCParser.BlockContext ctx) {
        symbolTable.closeScope();
        this.removeParent();
        super.exitBlock(ctx);
    }

    @Override
    public void enterWhile(miniCParser.WhileContext ctx) {
        List<ast> children = new ArrayList<>();

        String condition = ctx.expr().getText();
        String[] text = {condition};
        whileNode whileNode = new whileNode(text, children);
        whileNode.setParent(this.getCurrentParentNode());
        whileNode.getParent().addChild(whileNode);

        this.addParent(whileNode);
        super.enterWhile(ctx);
    }

    @Override
    public void exitWhile(miniCParser.WhileContext ctx) {

        this.removeParent();
        super.exitWhile(ctx);
    }

    @Override
    public void enterCond(miniCParser.CondContext ctx) {
        List<ast> children = new ArrayList<>();
        String condition = ctx.expr().getText();
        String[] text = {condition};

        String elseCondition = null;
        //check if there's an else statement
        if (ctx.block(1) != null) {
            elseCondition = ctx.block(1).getText();
            text = new String[]{condition, elseCondition};
        }

        ifNode ifNode = new ifNode(text, children);
        ifNode.setParent(this.getCurrentParentNode());
        ifNode.getParent().addChild(ifNode);

        this.addParent(ifNode);
        super.enterCond(ctx);
    }

    @Override
    public void exitCond(miniCParser.CondContext ctx) {
        this.removeParent();
        super.exitCond(ctx);
    }

    @Override
    public void enterReturn(miniCParser.ReturnContext ctx) {
        List<ast> children = new ArrayList<>();
        ArrayList<String> returns = new ArrayList<>();
        for (ParseTree a : ctx.expr().children) {
            if (!a.getText().equals("return")) {
                returns.add(a.getText());
            }
        }
        String[] text = new String[returns.size()];
        for (int i = 0; i < returns.size(); i++) {
            text[i] = returns.get(i);
        }

        returnNode returnNode = new returnNode(text, children);
        returnNode.setParent(this.getCurrentParentNode());
        returnNode.getParent().addChild(returnNode);

        this.addParent(returnNode);
        super.enterReturn(ctx);
    }

    @Override
    public void exitReturn(miniCParser.ReturnContext ctx) {
        this.removeParent();
        super.exitReturn(ctx);
    }

    @Override
    public void enterFncall(miniCParser.FncallContext ctx) {
        List<ast> children = new ArrayList<>();
        String functionName = ctx.ID().getText();
        String[] text = {functionName};
        String args = null;

        if (ctx.args() != null) {
            args = ctx.args().getText();
            text = new String[]{functionName, args};
        }

        if (!symbolTable.containsInCurrentScope(functionName) && symbolTable.lookup(functionName) == null) {
            System.out.println("Fehler: Funktion'" + functionName + "' ist in keinem Scope definiert.");
        }

        fncallNode fncall = new fncallNode(text, children);
        fncall.setParent(this.getCurrentParentNode());
        fncall.getParent().addChild(fncall);

        this.addParent(fncall);
        super.enterFncall(ctx);
    }

    @Override
    public void exitFncall(miniCParser.FncallContext ctx) {
        this.removeParent();
        super.exitFncall(ctx);
    }

    @Override
    public void enterParams(miniCParser.ParamsContext ctx) {

        int b = 0;
        boolean first = true;
        String name = "";
        String type = "";
        for (int a = 0; a < ctx.getChildCount(); a++) {
            if (ctx.getChild(a).getText().equals(ctx.ID(b).getText())) {
                name = ctx.ID(b).getText();
                type = ctx.type(b).getText();

                if (symbolTable.containsInCurrentScope(name)) {
                    System.out.println("Fehler: Parameter '" + name + "' ist bereits im aktuellen Scope definiert.");
                } else if (symbolTable.lookup(name) != null) {
                    System.out.println("Fehler: Parameter '" + name + "' ist bereits in einem oberen Scope definiert.");
                } else {
                    symbolTable.addSymbol(name, new Tabelle.Symbol(name, type));
                }
                b++;
            }
        }

    }


}
