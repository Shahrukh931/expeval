package com.expeval.tokens;

import com.expeval.tokenizer.InvalidDataTypeException;

/**
 * Created by shahrukhimam on 08/05/16.
 */
public abstract class UnaryOperator extends Operator {

    public UnaryOperator(String identifier, int precedence) {
        super(identifier, precedence, TYPE.UNARY_OPERATOR, false);
    }

    public abstract Object onEvaluation(Object rightOperand);


    public static final UnaryOperator NEGATE = new UnaryOperator("!", 51) {
        @Override
        public Object onEvaluation(Object rightOperand) {
            if(rightOperand instanceof Boolean) {
                return !((Boolean) rightOperand);
            } else {
                String rightOperandClass = rightOperand == null? "null": rightOperand.getClass().getName();
                throw new InvalidDataTypeException("Operator ! cannot be applied to '"+rightOperandClass+"'");
            }
        }
    };

    public static final UnaryOperator PLUS = new UnaryOperator("+", 51) {
        @Override
        public Object onEvaluation(Object rightOperand) {
            if(rightOperand instanceof Number) {
                if(rightOperand instanceof Double) {
                    return 0 + ((Number)rightOperand).doubleValue();
                } else if(rightOperand instanceof Float) {
                    return 0 + ((Number)rightOperand).floatValue();
                } else if(rightOperand instanceof Long) {
                    return ((Number)rightOperand).longValue();
                } else {
                    return ((Number)rightOperand).intValue();
                }
            } else {
                String rightOperandClass = rightOperand == null? "null": rightOperand.getClass().getName();
                throw new InvalidDataTypeException("Operator + cannot be applied to '"+rightOperandClass+"'");
            }
        }
    };

    public static final UnaryOperator MINUS = new UnaryOperator("-", 51) {
        @Override
        public Object onEvaluation(Object rightOperand) {
            if(rightOperand instanceof Number) {
                if(rightOperand instanceof Double) {
                    return 0 - ((Number)rightOperand).doubleValue();
                } else if(rightOperand instanceof Float) {
                    return 0 - ((Number)rightOperand).floatValue();
                } else if(rightOperand instanceof Long) {
                    return 0 -((Number)rightOperand).longValue();
                } else {
                    return 0 - ((Number)rightOperand).intValue();
                }
            } else {
                String rightOperandClass = rightOperand == null? "null": rightOperand.getClass().getName();
                throw new InvalidDataTypeException("Operator - cannot be applied to '"+rightOperandClass+"'");
            }
        }
    };

    public static final UnaryOperator BITWISE_COMPLEMENT = new UnaryOperator("~", 51) {
        @Override
        public Object onEvaluation(Object rightOperand) {
            if(rightOperand instanceof Long) {
                return (~((Number)rightOperand).longValue());
            } else  if(rightOperand instanceof Integer) {
                return (~((Number)rightOperand).intValue());
            } else {
                String rightOperandClass = rightOperand == null? "null": rightOperand.getClass().getName();
                throw new InvalidDataTypeException("Operator ~ cannot be applied to '"+rightOperandClass+"'");
            }
        }
    };

}
