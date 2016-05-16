package com.expeval.tokenizer;

import com.expeval.Utils;
import com.expeval.tokens.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shahrukhimam on 06/05/16.
 */

//provide context inheritance
        /*
        E' -> E
        E -> E bop T
        E -> T
        T -> u T
        T -> Z
        Z -> ( E )
        Z -> id
        Z -> fid ( )
        Z -> fid ( F )
        F -> F , E
        F -> E
        */

public class Context {

    private Map<String, Token> binaryOperators = null;
    private Map<String, Token> unaryOperators = null;
    private Map<String, Token> functions = null;
    private Map<String, Token> vars = null;
    private static Map<String, Context> refs = new HashMap<String, Context>();
    private static final Object lock = new Object();

    private static Context self = new Context();
    private Context parentContext = null;

    static ParseTable parseTable = new ParseTable(20, 12);

    static {
        parseTable.map(ParseTable.COLUMN_BOP, 0);
        parseTable.map(ParseTable.COLUMN_U, 1);
        parseTable.map(ParseTable.COLUMN_LPARN, 2);
        parseTable.map(ParseTable.COLUMN_RPARN, 3);
        parseTable.map(ParseTable.COLUMN_ID, 4);
        parseTable.map(ParseTable.COLUMN_FID, 5);
        parseTable.map(ParseTable.COLUMN_COMMA, 6);
        parseTable.map(ParseTable.COLUMN_$, 7);
        parseTable.map(ParseTable.COLUMN_E, 8);
        parseTable.map(ParseTable.COLUMN_T, 9);
        parseTable.map(ParseTable.COLUMN_Z, 10);
        parseTable.map(ParseTable.COLUMN_F, 11);

        parseTable.setAction(new Shift(3), 0, 1);
        parseTable.setAction(new Shift(5), 0, 2);
        parseTable.setAction(new Shift(6), 0, 4);
        parseTable.setAction(new Shift(7), 0, 5);
        parseTable.setAction(new Goto(1, ParseTable.COLUMN_E), 0, 8);
        parseTable.setAction(new Goto(2, ParseTable.COLUMN_T), 0, 9);
        parseTable.setAction(new Goto(4, ParseTable.COLUMN_Z), 0, 10);

        parseTable.setAction(new Shift(8), 1, 0);
        parseTable.setAction(new Accept(), 1, 7);


        parseTable.setAction(new Reduce(1, ParseTable.COLUMN_E), 2, 0);
        parseTable.setAction(new Reduce(1, ParseTable.COLUMN_E), 2, 3);
        parseTable.setAction(new Reduce(1, ParseTable.COLUMN_E), 2, 6);
        parseTable.setAction(new Reduce(1, ParseTable.COLUMN_E), 2, 7);

        parseTable.setAction(new Shift(3), 3, 1);
        parseTable.setAction(new Shift(5), 3, 2);
        parseTable.setAction(new Shift(6), 3, 4);
        parseTable.setAction(new Shift(7), 3, 5);
        parseTable.setAction(new Goto(9, ParseTable.COLUMN_T), 3, 9);
        parseTable.setAction(new Goto(4, ParseTable.COLUMN_Z), 3, 10);

        parseTable.setAction(new Reduce(1, ParseTable.COLUMN_T), 4, 0);
        parseTable.setAction(new Reduce(1, ParseTable.COLUMN_T), 4, 3);
        parseTable.setAction(new Reduce(1, ParseTable.COLUMN_T), 4, 6);
        parseTable.setAction(new Reduce(1, ParseTable.COLUMN_T), 4, 7);

        parseTable.setAction(new Shift(3), 5, 1);
        parseTable.setAction(new Shift(5), 5, 2);
        parseTable.setAction(new Shift(6), 5, 4);
        parseTable.setAction(new Shift(7), 5, 5);
        parseTable.setAction(new Goto(10, ParseTable.COLUMN_E), 5, 8);
        parseTable.setAction(new Goto(2, ParseTable.COLUMN_T), 5, 9);
        parseTable.setAction(new Goto(4, ParseTable.COLUMN_Z), 5, 10);

        parseTable.setAction(new Reduce(1, ParseTable.COLUMN_Z), 6, 0);
        parseTable.setAction(new Reduce(1, ParseTable.COLUMN_Z), 6, 3);
        parseTable.setAction(new Reduce(1, ParseTable.COLUMN_Z), 6, 6);
        parseTable.setAction(new Reduce(1, ParseTable.COLUMN_Z), 6, 7);

        parseTable.setAction(new Shift(11), 7, 2);


        parseTable.setAction(new Shift(3), 8, 1);
        parseTable.setAction(new Shift(5), 8, 2);
        parseTable.setAction(new Shift(6), 8, 4);
        parseTable.setAction(new Shift(7), 8, 5);
        parseTable.setAction(new Goto(12, ParseTable.COLUMN_T), 8, 9);
        parseTable.setAction(new Goto(4, ParseTable.COLUMN_Z), 8, 10);

        parseTable.setAction(new Reduce(2, ParseTable.COLUMN_T), 9, 0);
        parseTable.setAction(new Reduce(2, ParseTable.COLUMN_T), 9, 3);
        parseTable.setAction(new Reduce(2, ParseTable.COLUMN_T), 9, 6);
        parseTable.setAction(new Reduce(2, ParseTable.COLUMN_T), 9, 7);

        parseTable.setAction(new Shift(8), 10, 0);
        parseTable.setAction(new Shift(13), 10, 3);


        parseTable.setAction(new Shift(3), 11, 1);
        parseTable.setAction(new Shift(5), 11, 2);
        parseTable.setAction(new Shift(14), 11, 3);
        parseTable.setAction(new Shift(6), 11, 4);
        parseTable.setAction(new Shift(7), 11, 5);
        parseTable.setAction(new Goto(16, ParseTable.COLUMN_E), 11, 8);
        parseTable.setAction(new Goto(2, ParseTable.COLUMN_T), 11, 9);
        parseTable.setAction(new Goto(4, ParseTable.COLUMN_Z), 11, 10);
        parseTable.setAction(new Goto(15, ParseTable.COLUMN_F), 11, 11);


        parseTable.setAction(new Reduce(3, ParseTable.COLUMN_E), 12, 0);
        parseTable.setAction(new Reduce(3, ParseTable.COLUMN_E), 12, 3);
        parseTable.setAction(new Reduce(3, ParseTable.COLUMN_E), 12, 6);
        parseTable.setAction(new Reduce(3, ParseTable.COLUMN_E), 12, 7);

        parseTable.setAction(new Reduce(3, ParseTable.COLUMN_Z), 13, 0);
        parseTable.setAction(new Reduce(3, ParseTable.COLUMN_Z), 13, 3);
        parseTable.setAction(new Reduce(3, ParseTable.COLUMN_Z), 13, 6);
        parseTable.setAction(new Reduce(3, ParseTable.COLUMN_Z), 13, 7);


        parseTable.setAction(new Reduce(3, ParseTable.COLUMN_Z), 14, 0);
        parseTable.setAction(new Reduce(3, ParseTable.COLUMN_Z), 14, 3);
        parseTable.setAction(new Reduce(3, ParseTable.COLUMN_Z), 14, 6);
        parseTable.setAction(new Reduce(3, ParseTable.COLUMN_Z), 14, 7);


        parseTable.setAction(new Shift(17), 15, 3);
        parseTable.setAction(new Shift(18), 15, 6);

        parseTable.setAction(new Shift(8), 16, 0);
        parseTable.setAction(new Reduce(1, ParseTable.COLUMN_F), 16, 3);
        parseTable.setAction(new Reduce(1, ParseTable.COLUMN_F), 16, 6);

        parseTable.setAction(new Reduce(4, ParseTable.COLUMN_Z), 17, 0);
        parseTable.setAction(new Reduce(4, ParseTable.COLUMN_Z), 17, 3);
        parseTable.setAction(new Reduce(4, ParseTable.COLUMN_Z), 17, 6);
        parseTable.setAction(new Reduce(4, ParseTable.COLUMN_Z), 17, 7);

        parseTable.setAction(new Shift(3), 18, 1);
        parseTable.setAction(new Shift(5), 18, 2);
        parseTable.setAction(new Shift(6), 18, 4);
        parseTable.setAction(new Shift(7), 18, 5);
        parseTable.setAction(new Goto(19, ParseTable.COLUMN_E), 18, 8);
        parseTable.setAction(new Goto(2, ParseTable.COLUMN_T), 18, 9);
        parseTable.setAction(new Goto(4, ParseTable.COLUMN_Z), 18, 10);

        parseTable.setAction(new Shift(8), 19, 0);
        parseTable.setAction(new Reduce(3, ParseTable.COLUMN_F), 19, 3);
        parseTable.setAction(new Reduce(3, ParseTable.COLUMN_F), 19, 6);


    }

    private Context() {
        binaryOperators = Collections.synchronizedMap(new HashMap<String, Token>());
        unaryOperators = Collections.synchronizedMap(new HashMap<String, Token>());
        functions = Collections.synchronizedMap(new HashMap<String, Token>());
        vars = Collections.synchronizedMap(new HashMap<String, Token>());
        registerNativeTokens();
    }

    public static Context newContext(String tag) {
        synchronized (lock) {
            Context context = new Context();
            refs.put(tag, context);
            return context;
        }
    }

    public static void removeContext(String tag) {
        synchronized (lock) {
            if (refs.containsKey(tag)) {
                refs.put(tag, null);
            }
        }
    }

    public static Context get(String tag) {
        synchronized (lock) {
            return refs.get(tag);
        }
    }

    public static Context getDefault() {
        synchronized (lock) {
            return self;
        }
    }

    public static Context resetDefault() {
        synchronized (lock) {
            self = new Context();
            return self;
        }
    }

    public ParseTable getParseTable() {
        return parseTable;
    }

    public void registerBinaryOperator(BinaryOperator operator) {
        if (!Utils.isValidJavaIdentifier(operator.getLexeme())) {
            throw new IllegalArgumentException("Unsupported operator identifier : " + operator.getLexeme());
        }
        synchronized (lock) {
            this.binaryOperators.put(operator.getLexeme(), operator);
        }
    }

    public void registerUnaryOperator(UnaryOperator operator) {
        if (!Utils.isValidJavaIdentifier(operator.getLexeme())) {
            throw new IllegalArgumentException("Unsupported operator identifier : " + operator.getLexeme());
        }
        if (isRegisteredVar(operator.getLexeme())) {
            throw new IllegalArgumentException("Unary Operator identifier : " + operator.getLexeme() + " will create ambiguity during parsing");
        }
        synchronized (lock) {
            this.unaryOperators.put(operator.getLexeme(), operator);
        }
    }

    public void registerFunction(Function function) {
        if (!Utils.isValidJavaIdentifier(function.getLexeme())) {
            throw new IllegalArgumentException("Unsupported function identifier : " + function.getLexeme());
        }
        if (isRegisteredUnaryOperator(function.getLexeme())) {
            throw new IllegalArgumentException("Function identifier : " + function.getLexeme() + " will create ambiguity during parsing");
        }
        synchronized (lock) {
            this.functions.put(function.getLexeme(), function);
        }
    }

    public void registerVar(Var var) {
        if (!Utils.isValidJavaIdentifier(var.getLexeme())) {
            throw new IllegalArgumentException("Unsupported Var identifier : " + var.getLexeme());
        }
        if (isRegisteredUnaryOperator(var.getLexeme())) {
            throw new IllegalArgumentException("Var identifier : " + var.getLexeme() + " will create ambiguity during parsing");
        }
        synchronized (lock) {
            this.vars.put(var.getLexeme(), var);
        }
    }

    protected void registerNativeBinaryOperator(BinaryOperator operator) {
        this.binaryOperators.put(operator.getLexeme(), operator);
    }

    protected void registerNativeFunction(Function function) {
        this.functions.put(function.getLexeme(), function);
    }

    protected void registerNativeUnaryOperator(UnaryOperator operator) {
        this.unaryOperators.put(operator.getLexeme(), operator);
    }

    protected void registerNativeVar(Var var) {
        this.vars.put(var.getLexeme(), var);
    }


    public BinaryOperator getBinaryOperator(String identifier) {
        synchronized (lock) {
            return (BinaryOperator) this.binaryOperators.get(identifier);
        }
    }


    public UnaryOperator getUnaryOperator(String identifier) {
        synchronized (lock) {
            return (UnaryOperator) this.unaryOperators.get(identifier);
        }
    }


    public Function getFunction(String identifier) {
        synchronized (lock) {
            return (Function) this.functions.get(identifier);
        }
    }

    public Var getVar(String identifier) {
        synchronized (lock) {
            return (Var) this.vars.get(identifier);
        }
    }


    protected boolean isRegistered(String identifier) {
        synchronized (lock) {
            return this.binaryOperators.containsKey(identifier) || unaryOperators.containsKey(identifier) || functions.containsKey(identifier) || vars.containsKey(identifier);
        }
    }

    protected boolean isRegisteredBinaryOperator(String identifier) {
        synchronized (lock) {
            return this.binaryOperators.containsKey(identifier);
        }
    }

    protected boolean isRegisteredUnaryOperator(String identifier) {
        synchronized (lock) {
            return this.unaryOperators.containsKey(identifier);
        }
    }

    protected boolean isRegisteredFunction(String identifier) {
        synchronized (lock) {
            return this.functions.containsKey(identifier);
        }
    }

    protected boolean isRegisteredVar(String identifier) {
        synchronized (lock) {
            return this.vars.containsKey(identifier);
        }
    }

    protected String getAllBinaryOperators() {
        synchronized (lock) {
            return Utils.setToString(this.binaryOperators.keySet());
        }
    }

    protected String getAllUnaryOperators() {
        synchronized (lock) {
            return Utils.setToString(this.unaryOperators.keySet());
        }
    }

    protected String getAllVars() {
        synchronized (lock) {
            return Utils.setToString(this.vars.keySet());
        }
    }

    protected String getAllFunctions() {
        synchronized (lock) {
            return Utils.setToString(this.functions.keySet());
        }
    }

    private void registerNativeTokens() {

        registerNativeBinaryOperator(BinaryOperator.OR);
        registerNativeBinaryOperator(BinaryOperator.AND);
        registerNativeBinaryOperator(BinaryOperator.BITWISE_OR);
        registerNativeBinaryOperator(BinaryOperator.BITWISE_XOR);
        registerNativeBinaryOperator(BinaryOperator.BITWISE_AND);
        registerNativeBinaryOperator(BinaryOperator.EQ);
        registerNativeBinaryOperator(BinaryOperator.NOT_EQ);
        registerNativeBinaryOperator(BinaryOperator.GT_EQ);
        registerNativeBinaryOperator(BinaryOperator.LT_EQ);
        registerNativeBinaryOperator(BinaryOperator.GT);
        registerNativeBinaryOperator(BinaryOperator.LT);
        registerNativeBinaryOperator(BinaryOperator.RIGHT_SHIFT);
        registerNativeBinaryOperator(BinaryOperator.LEFT_SHIFT);
        registerNativeBinaryOperator(BinaryOperator.ADD);
        registerNativeBinaryOperator(BinaryOperator.SUBTRACT);
        registerNativeBinaryOperator(BinaryOperator.MUL);
        registerNativeBinaryOperator(BinaryOperator.MOD);
        registerNativeBinaryOperator(BinaryOperator.DIV);

        registerNativeUnaryOperator(UnaryOperator.NEGATE);
        registerNativeUnaryOperator(UnaryOperator.PLUS);
        registerNativeUnaryOperator(UnaryOperator.MINUS);
        registerNativeUnaryOperator(UnaryOperator.BITWISE_COMPLEMENT);


    }


}
