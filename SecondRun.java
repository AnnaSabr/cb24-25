import java.util.Stack;

public class SecondRun extends ASTListener {

    int scopeCounter;
    int scopeID;
    Stack<Integer> stackID = new Stack<>();
    private Tabelle symbolTable;

    public SecondRun(Tabelle symbolTable) {
        this.symbolTable = symbolTable;
    }

    @Override
    public void enterAssign(miniCParser.AssignContext ctx) {
        String varName = ctx.ID().getText();
        Tabelle.Symbol varSymbol = symbolTable.lookup(varName);


        super.enterAssign(ctx);
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
