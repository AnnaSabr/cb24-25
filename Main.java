import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import java.io.IOException;





public class Main {
    public static void main(String[] args) throws IOException {
        CharStream input=null;
        try{
            input = CharStreams.fromFileName("input.txt");
        }catch (Exception e){
            System.out.println("Input file not found");
        }
        miniCLexer lexer = new miniCLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        miniCParser parser = new miniCParser(tokens);

        ParseTree tree = parser.program();
        
        ASTListener listener = new ASTListener();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(listener, tree);

        ThirdTableRun thirdRun = new ThirdTableRun(listener.getSymbolTable());
        walker.walk(thirdRun, tree);

        ast root = listener.getFirstNode();
        root.print("");
    }
}
