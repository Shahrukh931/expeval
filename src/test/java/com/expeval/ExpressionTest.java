package com.expeval;

import com.expeval.tokenizer.Context;
import com.expeval.tokens.BinaryOperator;
import com.expeval.tokens.Function;
import com.expeval.tokens.UnaryOperator;
import com.expeval.tokens.Var;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by shahrukhimam on 15/05/16.
 */
public class ExpressionTest {


    @Test
    public void testSimpleNativeOperators() {
        assertEquals(-1d, Expression.parse("  -  1 ").eval());
        assertEquals(-.1d, Expression.parse(" - .1").eval());
        assertEquals(1d,Expression.parse("-+-+1").eval());
        assertEquals(2d,Expression.parse("1+-+-+1").eval());
        assertEquals(1d, Expression.parse("  +1  ").eval());
        assertEquals(0d, Expression.parse(" -1 + 1 ").eval());
        assertEquals(3.1666d, Expression.parse(" 2.0333 + 1.1333 ").eval());
        assertEquals(2d, Expression.parse("+1+1").eval());
        assertEquals(0d, Expression.parse("1+-1").eval());
        assertEquals(2d, Expression.parse("1--1").eval());
        assertEquals(1d, Expression.parse("2-1").eval());
        assertEquals(-6l, Expression.parse("~5").eval());
        assertEquals(-16d, Expression.parse("-10+~5").eval());
        assertEquals(false, Expression.parse("!true").eval());
        assertEquals(2d, Expression.parse("4/2").eval());
        assertEquals(0.5d, Expression.parse("2/4").eval());
        assertEquals(true, Expression.parse("false || !false").eval());
        assertEquals(false, Expression.parse("true && !true").eval());
        assertEquals(10l, Expression.parse("3+5^2").eval());
        assertEquals(7l, Expression.parse("3|5").eval());
        assertEquals("abc12",Expression.parse("\"abc\"+1+2").eval());
        assertEquals("3.0abc",Expression.parse("1+2+\"abc\"").eval());
        assertEquals(true, Expression.parse("\"ab\"==\"ab\"").eval());
        assertEquals(false, Expression.parse("\"ab\"==\"Ab\"").eval());
        assertEquals(false, Expression.parse("1==\"1\"").eval());
        assertEquals(true, Expression.parse("1==1.0").eval());
        assertEquals(true, Expression.parse("1!=2").eval());
        assertEquals(true, Expression.parse("  2>=2 && 2>=1 && 3<=3 && 3<=4 && 4<5 && 5<6 && 3.9999999<=4 && 5.0000000001>5  ").eval());
        assertEquals("1null", Expression.parse("\"1\"+null").eval());
        assertEquals("null1", Expression.parse("null+\"1\"").eval());
        assertEquals("12", Expression.parse("\"1\"+\"2\"").eval());
        assertEquals(-1d, Expression.parse("1*-1").eval());

    }

    @Test
    public void testWithParanthesis() {
        assertEquals(3d, Expression.parse(" (((1))) + ( ( ( 2 ) ) )  ").eval());
        assertEquals(-2.5d, Expression.parse("( (1   +    2) * -3.0000 / 2 + - (-1.0000 + -1 ) )").eval());
        assertEquals(-0.53d, Expression.parse(" (.1 + .2)*(.3 - .4) + - .5 ").eval());
    }


    @Test
    public void testForRegisteredCustomTokens() {
        Var x = new Var("x");
        x.setValue(10);
        Context.getDefault().registerVar(x);

        Var list = new Var("list");
        List<Object> l = new ArrayList<Object>();
        l.add(1);
        l.add(true);
        l.add("xyz");
        list.setValue(l);
        Context.getDefault().registerVar(list);

        final Map<String, Object> container = new HashMap<String, Object>();
        container.put("screenName", "MainScreen");
        container.put("screenTitle", "Title1");
        container.put("version", 1);
        container.put("discount", true);

        Context.getDefault().registerFunction(new Function("get") {
            @Override
            public Object onEvaluation(List<Object> arguments) {
                return container.get(arguments.get(0));
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

        Context.getDefault().registerBinaryOperator(new BinaryOperator("contains", BinaryOperator.EQ.getPrecedence()) {
            @Override
            public Object onEvaluation(Object leftOperand, Object rightOperand) {
                List<Object> list = (List<Object>) leftOperand;
                for (Object o : list) {
                    if ((Boolean) BinaryOperator.EQ.onEvaluation(o, rightOperand)) {
                        return true;
                    }
                }
                return false;

            }
        });


        Context.getDefault().registerUnaryOperator(new UnaryOperator("decBy2", UnaryOperator.NEGATE.getPrecedence()) {
            @Override
            public Object onEvaluation(Object rightOperand) {
                List<Number> list = (List<Number>) rightOperand;
                List<Double> result = new ArrayList<Double>();
                for (Number number : list) {
                    result.add(number.doubleValue() - 2);
                }
                return result;
            }
        });


        assertEquals(10, Expression.parse("x").eval());

        Context.getDefault().getVar("x").setValue(100);
        assertEquals(-101d, Expression.parse("-x+-1").eval());


        assertEquals(101d, Expression.parse("if(get(\"discount\"),x+1,x-2)").eval());
        assertEquals(true, Expression.parse("list contains 1 && asList(1,2,3,true) contains true").eval());
        assertEquals(3d, Expression.parse(" if(decBy2 asList(2,3,4,5,6)   contains   0 , get(\"version\") + 2,true  ) ").eval());
        assertEquals(true, Expression.parse("get(\"screenName\") == \"MainScreen\" && get(\"screenTitle\") == \"Title1\"").eval());


    }


}
