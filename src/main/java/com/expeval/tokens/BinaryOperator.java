package com.expeval.tokens;

import com.expeval.Utils;
import com.expeval.tokenizer.InvalidDataTypeException;

/**
 * Created by shahrukhimam on 09/05/16.
 */
public abstract class BinaryOperator extends Operator {

    private static double EPSILON = 0.0000000001;

    public BinaryOperator(String identifier, int precedence) {
        super(identifier, precedence, TYPE.BINARY_OPERATOR, true);
    }

    public BinaryOperator(String identifier, int precedence, boolean leftToRightAssociative) {
        super(identifier, precedence, TYPE.BINARY_OPERATOR, leftToRightAssociative);
    }

    public abstract Object onEvaluation(Object leftOperand, Object rightOperand);


    public static final BinaryOperator OR = new BinaryOperator("||", 41) {
        @Override
        public Object onEvaluation(Object leftValue, Object rightValue) {
            if(leftValue instanceof Boolean && rightValue instanceof Boolean) {
                return (Boolean)leftValue || (Boolean) rightValue;
            } else {
                Utils.throwInvalidDataTypeExceptionForBinaryOperator("||", leftValue, rightValue);
                return null;
            }
        }
    };


    public static final BinaryOperator AND = new BinaryOperator("&&", 42) {
        @Override
        public Object onEvaluation(Object leftValue, Object rightValue) {
            if(leftValue instanceof Boolean && rightValue instanceof Boolean) {
                return (Boolean)leftValue && (Boolean) rightValue;
            } else {
                Utils.throwInvalidDataTypeExceptionForBinaryOperator("&&", leftValue, rightValue);
                return null;
            }
        }
    };

    public static final BinaryOperator BITWISE_OR = new BinaryOperator("|", 43) {
        @Override
        public Object onEvaluation(Object leftValue, Object rightValue) {
            if((leftValue instanceof Long || leftValue instanceof Integer) && (rightValue instanceof Long || rightValue instanceof Integer)) {
                if(leftValue instanceof Long || rightValue instanceof Long) {
                    return ((Number) leftValue).longValue() | ((Number) rightValue).longValue();
                } else {
                    return ((Number) leftValue).intValue() | ((Number) rightValue).intValue();
                }
            } else {
                Utils.throwInvalidDataTypeExceptionForBinaryOperator("|", leftValue, rightValue);
                return null;
            }
        }
    };

    public static final BinaryOperator BITWISE_XOR = new BinaryOperator("^", 44) {
        @Override
        public Object onEvaluation(Object leftValue, Object rightValue) {
            if((leftValue instanceof Long || leftValue instanceof Integer) && (rightValue instanceof Long || rightValue instanceof Integer)) {
                if(leftValue instanceof Long || rightValue instanceof Long) {
                    return ((Number) leftValue).longValue() ^ ((Number) rightValue).longValue();
                } else {
                    return ((Number) leftValue).intValue() ^ ((Number) rightValue).intValue();
                }
            } else {
                Utils.throwInvalidDataTypeExceptionForBinaryOperator("^", leftValue, rightValue);
                return null;
            }
        }
    };
    public static final BinaryOperator BITWISE_AND = new BinaryOperator("&", 45) {
        @Override
        public Object onEvaluation(Object leftValue, Object rightValue) {
            if((leftValue instanceof Long || leftValue instanceof Integer) && (rightValue instanceof Long || rightValue instanceof Integer)) {
                if(leftValue instanceof Long || rightValue instanceof Long) {
                    return ((Number) leftValue).longValue() & ((Number) rightValue).longValue();
                } else {
                    return ((Number) leftValue).intValue() & ((Number) rightValue).intValue();
                }
            } else {
                Utils.throwInvalidDataTypeExceptionForBinaryOperator("&", leftValue, rightValue);
                return null;
            }
        }
    };

    public static final BinaryOperator EQ = new BinaryOperator("==", 46) {
        @Override
        public Object onEvaluation(Object leftValue, Object rightValue) {
            if (leftValue == null && rightValue == null) {
                return true;
            } else if (leftValue == null || rightValue == null) {
                return false;
            } else if (leftValue instanceof Number && rightValue instanceof Number) {
                return Math.abs(((Number) leftValue).doubleValue() - ((Number) rightValue).doubleValue()) < EPSILON;
            } else if (!leftValue.getClass().getSimpleName().equals(rightValue.getClass().getSimpleName())) {
                return false;
            }
            return leftValue.toString().equals(rightValue.toString());

        }
    };

    public static final BinaryOperator NOT_EQ = new BinaryOperator("!=", 46) {
        @Override
        public Object onEvaluation(Object leftValue, Object rightValue) {
            return !(Boolean) EQ.onEvaluation(leftValue, rightValue);
        }
    };

    public static final BinaryOperator GT_EQ = new BinaryOperator(">=", 47) {
        @Override
        public Object onEvaluation(Object leftValue, Object rightValue) {
            return (((Number) leftValue).doubleValue() > ((Number) rightValue).doubleValue()) || Math.abs(((Number) leftValue).doubleValue() - ((Number) rightValue).doubleValue()) < EPSILON;
        }
    };

    public static final BinaryOperator LT_EQ = new BinaryOperator("<=", 47) {
        @Override
        public Object onEvaluation(Object leftValue, Object rightValue) {
            return (((Number) leftValue).doubleValue() < ((Number) rightValue).doubleValue()) || Math.abs(((Number) leftValue).doubleValue() - ((Number) rightValue).doubleValue()) < EPSILON;
        }
    };

    public static final BinaryOperator GT = new BinaryOperator(">", 47) {
        @Override
        public Object onEvaluation(Object leftValue, Object rightValue) {
            return ((Number) leftValue).doubleValue() > ((Number) rightValue).doubleValue();
        }
    };

    public static final BinaryOperator LT = new BinaryOperator("<", 47) {
        @Override
        public Object onEvaluation(Object leftValue, Object rightValue) {
            return ((Number) leftValue).doubleValue() < ((Number) rightValue).doubleValue();
        }
    };

    public static final BinaryOperator RIGHT_SHIFT = new BinaryOperator(">>", 48) {
        @Override
        public Object onEvaluation(Object leftValue, Object rightValue) {
            if((leftValue instanceof Long || leftValue instanceof Integer) && (rightValue instanceof Long || rightValue instanceof Integer)) {
                if(leftValue instanceof Long || rightValue instanceof Long) {
                    return ((Number) leftValue).longValue() >> ((Number) rightValue).longValue();
                } else {
                    return ((Number) leftValue).intValue() >> ((Number) rightValue).intValue();
                }
            } else {
                Utils.throwInvalidDataTypeExceptionForBinaryOperator(">>", leftValue, rightValue);
                return null;
            }
        }
    };

    public static final BinaryOperator LEFT_SHIFT = new BinaryOperator("<<", 48) {
        @Override
        public Object onEvaluation(Object leftValue, Object rightValue) {
            if((leftValue instanceof Long || leftValue instanceof Integer) && (rightValue instanceof Long || rightValue instanceof Integer)) {
                if(leftValue instanceof Long || rightValue instanceof Long) {
                    return ((Number) leftValue).longValue() << ((Number) rightValue).longValue();
                } else {
                    return ((Number) leftValue).intValue() << ((Number) rightValue).intValue();
                }
            } else {
                Utils.throwInvalidDataTypeExceptionForBinaryOperator("<<", leftValue, rightValue);
                return null;
            }
        }
    };

    public static final BinaryOperator ADD = new BinaryOperator("+", 49) {
        @Override
        public Object onEvaluation(Object leftValue, Object rightValue) {
            if (leftValue instanceof String) {
                return leftValue.toString() + rightValue;
            } else if (rightValue instanceof String) {
                return leftValue + rightValue.toString();
            } else {
                if(leftValue instanceof Number && rightValue instanceof Number) {
                    if(leftValue instanceof Double || rightValue instanceof Double) {
                        return ((Number)leftValue).doubleValue() + ((Number)rightValue).doubleValue();
                    } else if(leftValue instanceof Float || rightValue instanceof Float) {
                        return ((Number)leftValue).floatValue() + ((Number)rightValue).floatValue();
                    } else if(leftValue instanceof Long || rightValue instanceof Long) {
                        return ((Number)leftValue).longValue() + ((Number)rightValue).longValue();
                    } else {
                        return ((Number)leftValue).intValue() + ((Number)rightValue).intValue();
                    }
                } else {
                    String leftValueClass = leftValue == null ? "null": leftValue.getClass().getName();
                    String rightValueClass = rightValue == null? "null": rightValue.getClass().getName();
                    throw new InvalidDataTypeException("Operator + cannot be applied to '"+leftValueClass+"' and '"+rightValueClass+"'");
                }
            }
        }
    };

    public static final BinaryOperator SUBTRACT = new BinaryOperator("-", 49) {
        @Override
        public Object onEvaluation(Object leftValue, Object rightValue) {
            if(leftValue instanceof Number && rightValue instanceof Number) {
                if(leftValue instanceof Double || rightValue instanceof Double) {
                    return ((Number)leftValue).doubleValue() - ((Number)rightValue).doubleValue();
                } else if(leftValue instanceof Float || rightValue instanceof Float) {
                    return ((Number)leftValue).floatValue() - ((Number)rightValue).floatValue();
                } else if(leftValue instanceof Long || rightValue instanceof Long) {
                    return ((Number)leftValue).longValue() - ((Number)rightValue).longValue();
                } else {
                    return ((Number)leftValue).intValue() - ((Number)rightValue).intValue();
                }
            } else {
                String leftValueClass = leftValue == null ? "null": leftValue.getClass().getName();
                String rightValueClass = rightValue == null? "null": rightValue.getClass().getName();
                throw new InvalidDataTypeException("Operator - cannot be applied to '"+leftValueClass+"' and '"+rightValueClass+"'");
            }
        }
    };


    public static final BinaryOperator MUL = new BinaryOperator("*", 50) {
        @Override
        public Object onEvaluation(Object leftValue, Object rightValue) {
            if(leftValue instanceof Number && rightValue instanceof Number) {
                if(leftValue instanceof Double || rightValue instanceof Double) {
                    return ((Number)leftValue).doubleValue() * ((Number)rightValue).doubleValue();
                } else if(leftValue instanceof Float || rightValue instanceof Float) {
                    return ((Number)leftValue).floatValue() * ((Number)rightValue).floatValue();
                } else if(leftValue instanceof Long || rightValue instanceof Long) {
                    return ((Number)leftValue).longValue() * ((Number)rightValue).longValue();
                } else {
                    return ((Number)leftValue).intValue() * ((Number)rightValue).intValue();
                }
            } else {
                String leftValueClass = leftValue == null ? "null": leftValue.getClass().getName();
                String rightValueClass = rightValue == null? "null": rightValue.getClass().getName();
                throw new InvalidDataTypeException("Operator * cannot be applied to '"+leftValueClass+"' and '"+rightValueClass+"'");
            }
        }
    };

    public static final BinaryOperator MOD = new BinaryOperator("%", 50) {
        @Override
        public Object onEvaluation(Object leftValue, Object rightValue) {
            if(leftValue instanceof Number && rightValue instanceof Number) {
                if(leftValue instanceof Double || rightValue instanceof Double) {
                    return ((Number)leftValue).doubleValue() % ((Number)rightValue).doubleValue();
                } else if(leftValue instanceof Float || rightValue instanceof Float) {
                    return ((Number)leftValue).floatValue() % ((Number)rightValue).floatValue();
                } else if(leftValue instanceof Long || rightValue instanceof Long) {
                    return ((Number)leftValue).longValue() % ((Number)rightValue).longValue();
                } else {
                    return ((Number)leftValue).intValue() % ((Number)rightValue).intValue();
                }
            } else {
                String leftValueClass = leftValue == null ? "null": leftValue.getClass().getName();
                String rightValueClass = rightValue == null? "null": rightValue.getClass().getName();
                throw new InvalidDataTypeException("Operator % cannot be applied to '"+leftValueClass+"' and '"+rightValueClass+"'");
            }
        }
    };

    public static final BinaryOperator DIV = new BinaryOperator("/", 50) {
        @Override
        public Object onEvaluation(Object leftValue, Object rightValue) {
            if(leftValue instanceof Number && rightValue instanceof Number) {
                if(leftValue instanceof Double || rightValue instanceof Double) {
                    return ((Number)leftValue).doubleValue() / ((Number)rightValue).doubleValue();
                } else if(leftValue instanceof Float || rightValue instanceof Float) {
                    return ((Number)leftValue).floatValue() / ((Number)rightValue).floatValue();
                } else if(leftValue instanceof Long || rightValue instanceof Long) {
                    return ((Number)leftValue).longValue() / ((Number)rightValue).longValue();
                } else {
                    return ((Number)leftValue).intValue() / ((Number)rightValue).intValue();
                }
            } else {
                String leftValueClass = leftValue == null ? "null": leftValue.getClass().getName();
                String rightValueClass = rightValue == null? "null": rightValue.getClass().getName();
                throw new InvalidDataTypeException("Operator / cannot be applied to '"+leftValueClass+"' and '"+rightValueClass+"'");
            }
        }
    };

}
