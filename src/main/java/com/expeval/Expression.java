package com.expeval;

import com.expeval.eval.Evaluator;
import com.expeval.tokenizer.Context;
import com.expeval.tokenizer.Parser;
import com.expeval.tokens.Token;

import java.util.List;

/**
 * Created by shahrukhimam on 02/05/16.
 */
public class Expression {
    private List<Token> tokens;
    private String inputExpression;
    private Context context;

    Expression(List<Token> tokens, String inputExpression, Context context) {
        this.tokens = tokens;
        this.inputExpression = inputExpression;
        this.context = context;
    }

    public static Expression parse(String inputExpression) {
        return parse(inputExpression, Context.getDefault());
    }

    public static Expression parse(String inputExpression, Context context) {
        Parser parser = Parser.newParser(inputExpression.trim(), context);
        List<Token> tokens = parser.parse();
        return new Expression(tokens, inputExpression, context);
    }


    public Object eval() {
        Evaluator evaluator = Evaluator.newEvaluator(this.tokens, this.context);
        return evaluator.eval();
    }

    public String getInputExpression() {
        return this.inputExpression;
    }

    public String getParsedExpression() {
        if (tokens == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tokens.size(); i++) {
            sb.append(tokens.get(i).getLexeme());
            if (i < tokens.size() - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Expression)) {
            return false;
        }
        Expression expression = (Expression) obj;
        return this.inputExpression.equals(expression.inputExpression);
    }

    @Override
    public int hashCode() {
        return this.inputExpression.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String NEW_LINE = System.getProperty("line.separator");
        sb.append(this.getClass().getName() + " Object { " + NEW_LINE);
        sb.append("Input Expression : " + this.inputExpression + NEW_LINE);
        sb.append("Parsed Expression : " + this.getParsedExpression() + NEW_LINE);
        sb.append("}");
        return sb.toString();
    }

}
