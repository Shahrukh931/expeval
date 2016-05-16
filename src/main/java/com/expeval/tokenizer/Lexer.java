package com.expeval.tokenizer;

import com.expeval.tokens.Token;

import java.util.Iterator;

/**
 * Created by shahrukhimam on 02/05/16.
 */
abstract class Lexer implements Iterator<String> {
     String input = null;
    Context context = null;
    int i = 0;
    private StringBuilder sb = new StringBuilder();
    private boolean escaped = false;
    private boolean isInsideQuotes = false;
    private char ch = 0;

    public Lexer(String input, Context context) {
        this.input = input;
        this.context = context;
    }

    public Context getContext() {
        return this.context;
    }

    @Override
    public boolean hasNext() {
        return i < input.length();
    }

    @Override
    public String next() {
        sb.setLength(0);
        escaped = false;
        isInsideQuotes = false;
        while (i < input.length()) {
            ch = input.charAt(i);
            if (isInsideQuotes) {
                switch (ch) {
                    case 't':
                        if (escaped) {
                            sb.append('\t');
                            escaped = false;
                        } else {
                            sb.append(ch);
                        }
                        break;
                    case 'b':
                        if (escaped) {
                            sb.append('\b');
                            escaped = false;
                        } else {
                            sb.append(ch);
                        }
                        break;
                    case 'n':
                        if (escaped) {
                            sb.append('\n');
                            escaped = false;
                        } else {
                            sb.append(ch);
                        }
                        break;
                    case 'r':
                        if (escaped) {
                            sb.append('\r');
                            escaped = false;
                        } else {
                            sb.append(ch);
                        }
                        break;
                    case 'f':
                        if (escaped) {
                            sb.append('\f');
                            escaped = false;
                        } else {
                            sb.append(ch);
                        }
                        break;
                    case '\'':
                        if (escaped) {
                            sb.append('\'');
                            escaped = false;
                        } else {
                            sb.append(ch);
                        }
                        break;
                    case '"':
                        if (escaped) {
                            sb.append(ch);
                            escaped = false;
                        } else {
                            sb.append(ch);
                            isInsideQuotes = false;
                            i++;
                            return sb.toString();
                        }
                        break;
                    case '\\':
                        escaped = !escaped;
                        if (!escaped) {
                            sb.append(ch);
                        }
                        break;
                    default:
                        if (escaped) {
                            throw new UnexpectedIdentifierException(input, ch + "", i, "'t','b','n','r','f',''','\"','\\',");
                        }
                        sb.append(ch);
                        break;

                }
            } else {
                if (!Character.isJavaIdentifierStart(ch)) {
                    switch (ch) {
                        case '\"':
                            isInsideQuotes = true;
                            sb.append(ch);
                            break;
                        case ' ':
                            break;
                        case '|':
                            if (ch == justNextChar(i)) {
                                i = i + 2;
                                return "||";
                            } else {
                                i = i + 1;
                                return "|";
                            }
                        case '&':
                            if (ch == justNextChar(i)) {
                                i = i + 2;
                                return "&&";
                            } else {
                                i = i + 1;
                                return "&";
                            }
                        case '^':
                            i += 1;
                            return "^";
                        case '=':
                            if (ch == justNextChar(i)) {
                                i += 2;
                                return "==";
                            } else {
                                i += 1;
                                return "=";
                            }
                        case '!':
                            if ('=' == justNextChar(i)) {
                                i += 2;
                                return "!=";
                            } else {
                                i += 1;
                                return "!";
                            }
                        case '<':
                            if ('=' == justNextChar(i)) {
                                i += 2;
                                return "<=";
                            } else if ('<' == justNextChar(i)) {
                                i += 2;
                                return "<<";
                            } else {
                                i += 1;
                                return "<";
                            }
                        case '>':
                            if ('=' == justNextChar(i)) {
                                i += 2;
                                return ">=";
                            } else if ('>' == justNextChar(i)) {
                                i += 2;
                                return ">>";
                            } else {
                                i += 1;
                                return ">";
                            }
                        case '+':
                        case '-':
                        case '~':
                        case '*':
                        case '%':
                        case '/':
                        case '(':
                        case ')':
                        case ',':
                            i += 1;
                            return ch + "";
                        default:
                            if ((Character.isDigit(ch) || (ch == '.' && Character.isDigit(nextChar(i))))) {
                                boolean isDotFound = false;
                                while (i < input.length()) {
                                    ch = input.charAt(i);
                                    if (Character.isDigit(ch)) {
                                        sb.append(ch);
                                    } else if (ch == '.') {
                                        if (!isDotFound) {
                                            sb.append(ch);
                                            isDotFound = true;
                                        } else {
                                            break;
                                        }
                                    } else {
                                        break;
                                    }
                                    i++;
                                }
                                return sb.toString();
                            } else {
                                i += 1;
                                return ch + "";
                            }
                    }
                } else {
                    while (i < input.length()) {
                        ch = input.charAt(i);
                        if (!Character.isJavaIdentifierPart(ch)) {
                            break;
                        } else {
                            sb.append(ch);
                        }
                        i++;
                    }
                    return sb.toString();
                }
            }
            i++;
        }
        return sb.toString();
    }

    int nextChar(int pos) {
        int j = pos + 1;
        while (j < input.length()) {
            if (input.charAt(j) != ' ') {
                return input.charAt(j);
            }
            j++;
        }
        return -1;
    }

    private int justNextChar(int pos) {
        return pos + 1 < input.length() ? input.charAt(pos + 1) : -1;
    }

    abstract Token nextToken();

    abstract boolean hasNextToken();


}
