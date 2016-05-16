package com.expeval.tokenizer;

import com.expeval.tokens.BinaryOperator;
import com.expeval.tokens.Function;
import com.expeval.tokens.UnaryOperator;
import com.expeval.tokens.Var;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by shahrukhimam on 10/05/16.
 */
public class ContextTest {

    @Before
    public void setUp() {
        Context.resetDefault();
    }

    @Test
    public void testForInvalidBinaryOperators() {

        try {
            Context.getDefault().registerBinaryOperator(new BinaryOperator("+", BinaryOperator.ADD.getPrecedence()) {
                @Override
                public Object onEvaluation(Object leftOperand, Object rightOperand) {
                    return null;
                }
            });
            fail();
        } catch (IllegalArgumentException e) {

        }

        try {
            Context.getDefault().registerBinaryOperator(new BinaryOperator("@abc", BinaryOperator.ADD.getPrecedence()) {
                @Override
                public Object onEvaluation(Object leftOperand, Object rightOperand) {
                    return null;
                }
            });
            fail();
        } catch (IllegalArgumentException e) {

        }

        try {
            Context.getDefault().registerBinaryOperator(new BinaryOperator("a+b", BinaryOperator.ADD.getPrecedence()) {
                @Override
                public Object onEvaluation(Object leftOperand, Object rightOperand) {
                    return null;
                }
            });
            fail();
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testForInvalidUnaryOperator() {
        Var var = new Var("a");
        Context.getDefault().registerVar(var);
        try {
            Context.getDefault().registerUnaryOperator(new UnaryOperator("a", UnaryOperator.NEGATE.getPrecedence()) {
                @Override
                public Object onEvaluation(Object rightOperand) {
                    return null;
                }
            });
            fail();
        } catch (IllegalArgumentException e) {

        }

        try {
            Context.getDefault().registerUnaryOperator(new UnaryOperator("-", UnaryOperator.NEGATE.getPrecedence()) {
                @Override
                public Object onEvaluation(Object rightOperand) {
                    return null;
                }
            });
            fail();
        } catch (IllegalArgumentException e) {

        }

        try {
            Context.getDefault().registerUnaryOperator(new UnaryOperator("*ab", UnaryOperator.NEGATE.getPrecedence()) {
                @Override
                public Object onEvaluation(Object rightOperand) {
                    return null;
                }
            });
            fail();
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testForInvalidFunction() {
        Context.getDefault().registerUnaryOperator(new UnaryOperator("dec", UnaryOperator.NEGATE.getPrecedence()) {
            @Override
            public Object onEvaluation(Object rightOperand) {
                return null;
            }
        });

        try {
            Context.getDefault().registerFunction(new Function("dec") {
                @Override
                public Object onEvaluation(List<Object> arguments) {
                    return null;
                }
            });
            fail();
        } catch (IllegalArgumentException e) {

        }

        try {
            Context.getDefault().registerFunction(new Function("+ab") {
                @Override
                public Object onEvaluation(List<Object> arguments) {
                    return null;
                }
            });
            fail();
        } catch (IllegalArgumentException e) {

        }

        try {
            Context.getDefault().registerFunction(new Function("a b") {
                @Override
                public Object onEvaluation(List<Object> arguments) {
                    return null;
                }
            });
            fail();
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testForInvalidVar() {
        Context.getDefault().registerUnaryOperator(new UnaryOperator("dec", UnaryOperator.NEGATE.getPrecedence()) {
            @Override
            public Object onEvaluation(Object rightOperand) {
                return null;
            }
        });

        try {
            Var var = new Var("dec");
            Context.getDefault().registerVar(var);
            fail();
        } catch (IllegalArgumentException e) {

        }

        try {
            Var var = new Var("a%b");
            Context.getDefault().registerVar(var);
            fail();
        } catch (IllegalArgumentException e) {

        }
        try {
            Var var = new Var(" a b ");
            Context.getDefault().registerVar(var);
            fail();
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void testForScopes() {
        Context.getDefault().registerFunction(new Function("sin") {
            @Override
            public Object onEvaluation(List<Object> arguments) {
                return null;
            }
        });


        Context context1 = Context.newContext("scope1");
        context1.registerFunction(new Function("cos") {
            @Override
            public Object onEvaluation(List<Object> arguments) {
                return null;
            }
        });
        context1.registerBinaryOperator(new BinaryOperator("add", BinaryOperator.ADD.getPrecedence()) {
            @Override
            public Object onEvaluation(Object leftOperand, Object rightOperand) {
                return null;
            }
        });

        Context context2 = Context.newContext("scope2");
        context2.registerFunction(new Function("tan") {
            @Override
            public Object onEvaluation(List<Object> arguments) {
                return null;
            }
        });

        context2.registerUnaryOperator(new UnaryOperator("u", UnaryOperator.NEGATE.getPrecedence()) {
            @Override
            public Object onEvaluation(Object rightOperand) {
                return null;
            }
        });
        context2.registerVar(new Var("ab"));


        assertNotNull(Context.getDefault().getFunction("sin"));
        assertNull(Context.getDefault().getFunction("cos"));
        assertNull(Context.getDefault().getFunction("tan"));
        assertNull(Context.getDefault().getBinaryOperator("add"));
        assertNull(Context.getDefault().getUnaryOperator("u"));
        assertNull(Context.getDefault().getVar("ab"));


        assertNotNull(Context.get("scope1").getFunction("cos"));
        assertNotNull(Context.get("scope1").getBinaryOperator("add"));
        assertNull(Context.get("scope1").getFunction("sin"));
        assertNull(Context.get("scope1").getFunction("tan"));
        assertNull(Context.get("scope1").getUnaryOperator("u"));
        assertNull(Context.get("scope1").getVar("ab"));


        assertNotNull(Context.get("scope2").getFunction("tan"));
        assertNotNull(Context.get("scope2").getUnaryOperator("u"));
        assertNotNull(Context.get("scope2").getVar("ab"));
        assertNull(Context.get("scope2").getFunction("cos"));
        assertNull(Context.get("scope2").getBinaryOperator("add"));
        assertNull(Context.get("scope2").getFunction("sin"));


        Context.resetDefault();

        assertNull(Context.getDefault().getFunction("sin"));
        assertNull(Context.getDefault().getFunction("cos"));
        assertNull(Context.getDefault().getFunction("tan"));
        assertNull(Context.getDefault().getBinaryOperator("add"));
        assertNull(Context.getDefault().getUnaryOperator("u"));
        assertNull(Context.getDefault().getVar("ab"));

        assertNotNull(Context.get("scope1").getFunction("cos"));
        assertNotNull(Context.get("scope1").getBinaryOperator("add"));

        assertNotNull(Context.get("scope2").getFunction("tan"));
        assertNotNull(Context.get("scope2").getUnaryOperator("u"));
        assertNotNull(Context.get("scope2").getVar("ab"));

        Context.removeContext("scope1");
        assertNull(Context.get("scope1"));
        assertNotNull(Context.get("scope2"));


        Context.removeContext("scope2");
        assertNull(Context.get("scope2"));
        assertNull(Context.get("scope1"));



    }
}
