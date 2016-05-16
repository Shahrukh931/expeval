package com.expeval.tokenizer;

import com.expeval.Expression;
import com.expeval.tokens.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by shahrukhimam on 10/05/16.
 */
public class ParserTest {

    @Before
    public void setUp() {
        Context.resetDefault();
    }

    @Test
    public void testParans() {
        Parser parser = Parser.newParser("((1+2)+(3*4)+(((1))))", Context.getDefault());
        List<Token> tokens = parser.parse();// ( ( ( 1 2 + ) ( 3 4 *) + ( ( ( 1 ) ) ) + ) )
        assertEquals(23, tokens.size());
        assertEquals(Token.TYPE.LPARN, tokens.get(0).getType());
        assertEquals(Token.TYPE.LPARN, tokens.get(1).getType());
        assertEquals(Token.TYPE.LPARN, tokens.get(2).getType());
        assertEquals(Token.TYPE.RPARN, tokens.get(6).getType());
        assertEquals(Token.TYPE.LPARN, tokens.get(7).getType());
        assertEquals(Token.TYPE.RPARN, tokens.get(11).getType());
        assertEquals(Token.TYPE.LPARN, tokens.get(13).getType());
        assertEquals(Token.TYPE.LPARN, tokens.get(14).getType());
        assertEquals(Token.TYPE.LPARN, tokens.get(15).getType());
        assertEquals(Token.TYPE.RPARN, tokens.get(17).getType());
        assertEquals(Token.TYPE.RPARN, tokens.get(18).getType());
        assertEquals(Token.TYPE.RPARN, tokens.get(19).getType());
        assertEquals(Token.TYPE.RPARN, tokens.get(21).getType());
        assertEquals(Token.TYPE.RPARN, tokens.get(22).getType());
    }

    @Test
    public void testNull() {
        Parser parser = Parser.newParser("null", Context.getDefault());
        List<Token> tokens = parser.parse();
        assertEquals(Token.TYPE.NULL, tokens.get(1).getType());
        assertEquals("null", tokens.get(1).getLexeme());
    }

    @Test
    public void testNumber() {
        Parser parser = Parser.newParser("12 + 1", Context.getDefault());
        List<Token> tokens = parser.parse();
        assertEquals(5, tokens.size());
        assertEquals(Token.TYPE.NUMBER, tokens.get(1).getType());
        assertEquals("12", tokens.get(1).getLexeme());

        assertEquals(Token.TYPE.NUMBER, tokens.get(1).getType());
        assertEquals("1", tokens.get(2).getLexeme());
    }

    @Test
    public void testDecimalNumber() {
        Parser parser = Parser.newParser(".1+12+0.6", Context.getDefault());
        List<Token> tokens = parser.parse(); // ( .1 12 + 0.6 + )
        assertEquals(7, tokens.size());
        assertEquals(Token.TYPE.NUMBER, tokens.get(1).getType());
        assertEquals(".1", tokens.get(1).getLexeme());

        assertEquals(Token.TYPE.NUMBER, tokens.get(2).getType());
        assertEquals("12", tokens.get(2).getLexeme());

        assertEquals(Token.TYPE.NUMBER, tokens.get(4).getType());
        assertEquals("0.6", tokens.get(4).getLexeme());
    }

    @Test
    public void testString() {
        Parser parser = Parser.newParser("\"a\"", Context.getDefault());
        List<Token> tokens = parser.parse();
        assertEquals(3, tokens.size());
        assertEquals(Token.TYPE.STRING, tokens.get(1).getType());
        assertEquals("a", tokens.get(1).getLexeme());
    }

    @Test
    public void testBoolean() {
        Parser parser = Parser.newParser("true || false", Context.getDefault());
        List<Token> tokens = parser.parse();
        assertEquals(5, tokens.size());
        assertEquals(Token.TYPE.BOOLEAN, tokens.get(1).getType());
        assertEquals("true", tokens.get(1).getLexeme());

        assertEquals(Token.TYPE.BOOLEAN, tokens.get(2).getType());
        assertEquals("false", tokens.get(2).getLexeme());
    }

    @Test
    public void testNativeBinaryOperators() {
        //test or
        Parser parser = Parser.newParser("true||false&&true|5^6&7==8!=9>=0<=1>2<3>>4<<5+6-7*8%9/0", Context.getDefault());
        List<Token> tokens = parser.parse();// [(, true, false, true, 5, 6, 7, 8, ==, 9, 0, >=, 1, <=, 2, >, 3, 4, >>, 5, 6, +, 7, 8, *, 9, %, 0, /, -, <<, <, !=, &, ^, |, &&, ||, )]
        assertEquals(39, tokens.size());
        assertEquals(Token.TYPE.BINARY_OPERATOR, tokens.get(8).getType());
        assertEquals("==", tokens.get(8).getLexeme());

        assertEquals(Token.TYPE.BINARY_OPERATOR, tokens.get(11).getType());
        assertEquals(">=", tokens.get(11).getLexeme());

        assertEquals(Token.TYPE.BINARY_OPERATOR, tokens.get(13).getType());
        assertEquals("<=", tokens.get(13).getLexeme());

        assertEquals(Token.TYPE.BINARY_OPERATOR, tokens.get(15).getType());
        assertEquals(">", tokens.get(15).getLexeme());

        assertEquals(Token.TYPE.BINARY_OPERATOR, tokens.get(18).getType());
        assertEquals(">>", tokens.get(18).getLexeme());


        assertEquals(Token.TYPE.BINARY_OPERATOR, tokens.get(21).getType());
        assertEquals("+", tokens.get(21).getLexeme());

        assertEquals(Token.TYPE.BINARY_OPERATOR, tokens.get(24).getType());
        assertEquals("*", tokens.get(24).getLexeme());


        assertEquals(Token.TYPE.BINARY_OPERATOR, tokens.get(26).getType());
        assertEquals("%", tokens.get(26).getLexeme());

        assertEquals(Token.TYPE.BINARY_OPERATOR, tokens.get(28).getType());
        assertEquals("/", tokens.get(28).getLexeme());

        assertEquals(Token.TYPE.BINARY_OPERATOR, tokens.get(29).getType());
        assertEquals("-", tokens.get(29).getLexeme());


        assertEquals(Token.TYPE.BINARY_OPERATOR, tokens.get(30).getType());
        assertEquals("<<", tokens.get(30).getLexeme());


        assertEquals(Token.TYPE.BINARY_OPERATOR, tokens.get(31).getType());
        assertEquals("<", tokens.get(31).getLexeme());

        assertEquals(Token.TYPE.BINARY_OPERATOR, tokens.get(32).getType());
        assertEquals("!=", tokens.get(32).getLexeme());

        assertEquals(Token.TYPE.BINARY_OPERATOR, tokens.get(33).getType());
        assertEquals("&", tokens.get(33).getLexeme());

        assertEquals(Token.TYPE.BINARY_OPERATOR, tokens.get(34).getType());
        assertEquals("^", tokens.get(34).getLexeme());

        assertEquals(Token.TYPE.BINARY_OPERATOR, tokens.get(35).getType());
        assertEquals("|", tokens.get(35).getLexeme());

        assertEquals(Token.TYPE.BINARY_OPERATOR, tokens.get(36).getType());
        assertEquals("&&", tokens.get(36).getLexeme());

        assertEquals(Token.TYPE.BINARY_OPERATOR, tokens.get(37).getType());
        assertEquals("||", tokens.get(37).getLexeme());

    }

    @Test
    public void testNativeUnaryOperator() {
        Parser parser = Parser.newParser("1--3+!true+~5", Context.getDefault());
        List<Token> tokens = parser.parse();//(1 3 - - true ! + 5 ~ +)

        assertEquals(12, tokens.size());
        assertEquals(Token.TYPE.UNARY_OPERATOR, tokens.get(3).getType());
        assertEquals("-", tokens.get(3).getLexeme());

        assertEquals(Token.TYPE.BINARY_OPERATOR, tokens.get(4).getType());
        assertEquals("-", tokens.get(4).getLexeme());

        assertEquals(Token.TYPE.UNARY_OPERATOR, tokens.get(6).getType());
        assertEquals("!", tokens.get(6).getLexeme());

        assertEquals(Token.TYPE.BINARY_OPERATOR, tokens.get(7).getType());
        assertEquals("+", tokens.get(7).getLexeme());

        assertEquals(Token.TYPE.UNARY_OPERATOR, tokens.get(9).getType());
        assertEquals("~", tokens.get(9).getLexeme());

        assertEquals(Token.TYPE.BINARY_OPERATOR, tokens.get(10).getType());
        assertEquals("+", tokens.get(10).getLexeme());
    }

    @Test
    public void testCustomOperatorsVarsAndFunction() {
        Context.getDefault().registerBinaryOperator(new BinaryOperator("pow", BinaryOperator.ADD.getPrecedence()) {
            @Override
            public Object onEvaluation(Object leftOperand, Object rightOperand) {
                return Math.pow(((Number) leftOperand).longValue(), ((Number) rightOperand).longValue());
            }
        });

        Context.getDefault().registerUnaryOperator(new UnaryOperator("u", UnaryOperator.NEGATE.getPrecedence()) {
            @Override
            public Object onEvaluation(Object rightOperand) {
                if (rightOperand == null) {
                    return null;
                }
                return ((Number) rightOperand).doubleValue() - 1;
            }
        });

        Context.getDefault().registerFunction(new Function("min") {
            @Override
            public Object onEvaluation(List<Object> arguments) {
                if (arguments == null) {
                    return null;
                }
                double min = Double.MAX_VALUE;
                for (Object o : arguments) {
                    min = Math.min(min, ((Number) o).doubleValue());
                }
                return min;
            }
        });

        Var var = new Var("x");
        var.setValue(2);
        Context.getDefault().registerVar(var);

        Parser parser = Parser.newParser("min(2 pow -1 ,min(1,u 1),10) + u x", Context.getDefault());
        List<Token> tokens = parser.parse();//( ( 2 1 - pow (1 1 u min ) 10 min ) x u + )
        assertEquals(19, tokens.size());

        assertEquals(Token.TYPE.NUMBER, tokens.get(2).getType());
        assertEquals("2", tokens.get(2).getLexeme());

        assertEquals(Token.TYPE.NUMBER, tokens.get(3).getType());
        assertEquals("1", tokens.get(3).getLexeme());

        assertEquals(Token.TYPE.UNARY_OPERATOR, tokens.get(4).getType());
        assertEquals("-", tokens.get(4).getLexeme());

        assertEquals(Token.TYPE.BINARY_OPERATOR, tokens.get(5).getType());
        assertEquals("pow", tokens.get(5).getLexeme());

        assertEquals(Token.TYPE.NUMBER, tokens.get(7).getType());
        assertEquals("1", tokens.get(7).getLexeme());

        assertEquals(Token.TYPE.NUMBER, tokens.get(8).getType());
        assertEquals("1", tokens.get(8).getLexeme());

        assertEquals(Token.TYPE.UNARY_OPERATOR, tokens.get(9).getType());
        assertEquals("u", tokens.get(9).getLexeme());

        assertEquals(Token.TYPE.FUNCTION, tokens.get(10).getType());
        assertEquals("min", tokens.get(10).getLexeme());

        assertEquals(Token.TYPE.NUMBER, tokens.get(12).getType());
        assertEquals("10", tokens.get(12).getLexeme());

        assertEquals(Token.TYPE.FUNCTION, tokens.get(13).getType());
        assertEquals("min", tokens.get(13).getLexeme());

        assertEquals(Token.TYPE.VAR, tokens.get(15).getType());
        assertEquals("x", tokens.get(15).getLexeme());

        assertEquals(Token.TYPE.UNARY_OPERATOR, tokens.get(16).getType());
        assertEquals("u", tokens.get(16).getLexeme());

        assertEquals(Token.TYPE.BINARY_OPERATOR, tokens.get(17).getType());
        assertEquals("+", tokens.get(17).getLexeme());

    }

    @Test
    public void testForSameIdentifiers() {
        Context.getDefault().registerBinaryOperator(new BinaryOperator("b", BinaryOperator.ADD.getPrecedence()) {
            @Override
            public Object onEvaluation(Object leftOperand, Object rightOperand) {
                return ((Number) leftOperand).longValue() + ((Number) rightOperand).longValue();
            }
        });

        Context.getDefault().registerFunction(new Function("b") {
            @Override
            public Object onEvaluation(List<Object> arguments) {
                return ((Number) arguments.get(0)).doubleValue() - ((Number) arguments.get(1)).doubleValue();
            }
        });

        Var var = new Var("b");
        var.setValue(10);
        Context.getDefault().registerVar(var);

        Parser parser = Parser.newParser("b b b b b(b,b) b b", Context.getDefault());
        List<Token> tokens = parser.parse();//(b b b (b b b) b b b)

        assertEquals(13, tokens.size());

        assertEquals(Token.TYPE.VAR, tokens.get(1).getType());
        assertEquals("b", tokens.get(1).getLexeme());

        assertEquals(Token.TYPE.VAR, tokens.get(2).getType());
        assertEquals("b", tokens.get(2).getLexeme());

        assertEquals(Token.TYPE.BINARY_OPERATOR, tokens.get(3).getType());
        assertEquals("b", tokens.get(3).getLexeme());

        assertEquals(Token.TYPE.VAR, tokens.get(5).getType());
        assertEquals("b", tokens.get(5).getLexeme());

        assertEquals(Token.TYPE.VAR, tokens.get(6).getType());
        assertEquals("b", tokens.get(6).getLexeme());

        assertEquals(Token.TYPE.FUNCTION, tokens.get(7).getType());
        assertEquals("b", tokens.get(7).getLexeme());

        assertEquals(Token.TYPE.BINARY_OPERATOR, tokens.get(9).getType());
        assertEquals("b", tokens.get(9).getLexeme());

        assertEquals(Token.TYPE.VAR, tokens.get(10).getType());
        assertEquals("b", tokens.get(10).getLexeme());

        assertEquals(Token.TYPE.BINARY_OPERATOR, tokens.get(11).getType());
        assertEquals("b", tokens.get(11).getLexeme());


    }


    @Test
    public void testErrors() {
        Context.getDefault().registerBinaryOperator(new BinaryOperator("add", BinaryOperator.ADD.getPrecedence()) {
            @Override
            public Object onEvaluation(Object leftOperand, Object rightOperand) {
                return ((Number) leftOperand).longValue() + ((Number) rightOperand).longValue();
            }
        });

        Context.getDefault().registerFunction(new Function("get") {
            @Override
            public Object onEvaluation(List<Object> arguments) {
                int[] ar = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
                return Arrays.asList(ar);
            }
        });

        Context.getDefault().registerFunction(new Function("if") {
            @Override
            public Object onEvaluation(List<Object> arguments) {
                if (arguments.size() == 1) {
                    return arguments.get(0);
                } else {
                    Boolean result = (Boolean) arguments.get(0);
                    return result ? arguments.get(1) : arguments.get(2);
                }
            }
        });

        Context.getDefault().registerFunction(new Function("asList") {
            @Override
            public Object onEvaluation(List<Object> arguments) {
                return arguments;
            }
        });

        Context.getDefault().registerUnaryOperator(new UnaryOperator("dec2", UnaryOperator.NEGATE.getPrecedence()) {
            @Override
            public Object onEvaluation(Object rightOperand) {
                List<Integer> list = (List<Integer>) rightOperand;
                List<Integer> result = new ArrayList<Integer>();
                for (Integer integer : list) {
                    result.add(integer - 2);
                }
                return result;
            }
        });


        try {
            Parser parser = Parser.newParser("a+1", Context.getDefault());
            parser.parse();
            fail();
        } catch (Exception e) {

        }


        try {
            Parser parser = Parser.newParser(")1", Context.getDefault());
            parser.parse();
            fail();
        } catch (Exception e) {

        }

        try {
            Parser parser = Parser.newParser("sin()", Context.getDefault());
            parser.parse();
            fail();
        } catch (Exception e) {

        }

        try {
            Parser parser = Parser.newParser("if(true,false,true", Context.getDefault());
            parser.parse();
            fail();
        } catch (Exception e) {

        }

        try {
            Parser parser = Parser.newParser("()", Context.getDefault());
            parser.parse();
            fail();
        } catch (Exception e) {

        }

        try {
            Parser parser = Parser.newParser("1+get() add1", Context.getDefault());
            parser.parse();
            fail();
        } catch (Exception e) {

        }

        try {
            Parser parser = Parser.newParser("1+*8", Context.getDefault());
            parser.parse();
            fail();
        } catch (Exception e) {

        }
    }


}
