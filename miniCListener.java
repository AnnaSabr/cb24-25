// Generated from C:/Users/Annas/Documents/private/uni/s9/cb/blatt4.2/miniC.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link miniCParser}.
 */
public interface miniCListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link miniCParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(miniCParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link miniCParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(miniCParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link miniCParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterStmt(miniCParser.StmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link miniCParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitStmt(miniCParser.StmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link miniCParser#vardecl}.
	 * @param ctx the parse tree
	 */
	void enterVardecl(miniCParser.VardeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link miniCParser#vardecl}.
	 * @param ctx the parse tree
	 */
	void exitVardecl(miniCParser.VardeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link miniCParser#array}.
	 * @param ctx the parse tree
	 */
	void enterArray(miniCParser.ArrayContext ctx);
	/**
	 * Exit a parse tree produced by {@link miniCParser#array}.
	 * @param ctx the parse tree
	 */
	void exitArray(miniCParser.ArrayContext ctx);
	/**
	 * Enter a parse tree produced by {@link miniCParser#assign}.
	 * @param ctx the parse tree
	 */
	void enterAssign(miniCParser.AssignContext ctx);
	/**
	 * Exit a parse tree produced by {@link miniCParser#assign}.
	 * @param ctx the parse tree
	 */
	void exitAssign(miniCParser.AssignContext ctx);
	/**
	 * Enter a parse tree produced by {@link miniCParser#fndecl}.
	 * @param ctx the parse tree
	 */
	void enterFndecl(miniCParser.FndeclContext ctx);
	/**
	 * Exit a parse tree produced by {@link miniCParser#fndecl}.
	 * @param ctx the parse tree
	 */
	void exitFndecl(miniCParser.FndeclContext ctx);
	/**
	 * Enter a parse tree produced by {@link miniCParser#params}.
	 * @param ctx the parse tree
	 */
	void enterParams(miniCParser.ParamsContext ctx);
	/**
	 * Exit a parse tree produced by {@link miniCParser#params}.
	 * @param ctx the parse tree
	 */
	void exitParams(miniCParser.ParamsContext ctx);
	/**
	 * Enter a parse tree produced by {@link miniCParser#return}.
	 * @param ctx the parse tree
	 */
	void enterReturn(miniCParser.ReturnContext ctx);
	/**
	 * Exit a parse tree produced by {@link miniCParser#return}.
	 * @param ctx the parse tree
	 */
	void exitReturn(miniCParser.ReturnContext ctx);
	/**
	 * Enter a parse tree produced by {@link miniCParser#fncall}.
	 * @param ctx the parse tree
	 */
	void enterFncall(miniCParser.FncallContext ctx);
	/**
	 * Exit a parse tree produced by {@link miniCParser#fncall}.
	 * @param ctx the parse tree
	 */
	void exitFncall(miniCParser.FncallContext ctx);
	/**
	 * Enter a parse tree produced by {@link miniCParser#args}.
	 * @param ctx the parse tree
	 */
	void enterArgs(miniCParser.ArgsContext ctx);
	/**
	 * Exit a parse tree produced by {@link miniCParser#args}.
	 * @param ctx the parse tree
	 */
	void exitArgs(miniCParser.ArgsContext ctx);
	/**
	 * Enter a parse tree produced by {@link miniCParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(miniCParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link miniCParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(miniCParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link miniCParser#while}.
	 * @param ctx the parse tree
	 */
	void enterWhile(miniCParser.WhileContext ctx);
	/**
	 * Exit a parse tree produced by {@link miniCParser#while}.
	 * @param ctx the parse tree
	 */
	void exitWhile(miniCParser.WhileContext ctx);
	/**
	 * Enter a parse tree produced by {@link miniCParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterCond(miniCParser.CondContext ctx);
	/**
	 * Exit a parse tree produced by {@link miniCParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitCond(miniCParser.CondContext ctx);
	/**
	 * Enter a parse tree produced by {@link miniCParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(miniCParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link miniCParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(miniCParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link miniCParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(miniCParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link miniCParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(miniCParser.TypeContext ctx);
}