package com.expeval.tokenizer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shahrukhimam on 13/05/16.
 */
class ParseTable {

    public static String COLUMN_BOP = "bop";
    public static String COLUMN_U = "u";
    public static String COLUMN_LPARN = "(";
    public static String COLUMN_RPARN = ")";
    public static String COLUMN_ID = "id";
    public static String COLUMN_FID = "fid";
    public static String COLUMN_COMMA = ",";
    public static String COLUMN_$ = "$";
    public static String COLUMN_E = "E";
    public static String COLUMN_T = "T";
    public static String COLUMN_Z = "Z";
    public static String COLUMN_F = "F";

    private static Action[][] table = null;
    private Map<String, Integer> rowsMappings = null;

    ParseTable(int rows, int column) {
        table = new Action[rows][column];
        rowsMappings = new HashMap<String, Integer>();
    }

    public static ParseTable init(int rows, int column) {
        return new ParseTable(rows, column);
    }

    public void map(String token, int column) {
        rowsMappings.put(token, column);
    }

    public Action get(int row, String token) {
        return get(row, rowsMappings.get(token));
    }

    public Action get(int row, int column) {
        return table[row][column];
    }

    public void setAction(Action action, int row, int column) {
        table[row][column] = action;
    }

    public boolean hasMapping(int row, String token) {
        return get(row, token) != null;
    }

    public String getExpectedIdentifiers(int row, Context context) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Integer> entry : rowsMappings.entrySet()) {
            if (table[row][entry.getValue()] != null) {
                if (ParseTable.COLUMN_BOP.equals(entry.getKey())) {
                    sb.append(context.getAllBinaryOperators());
                    sb.append(",");
                } else if (ParseTable.COLUMN_U.equals(entry.getKey())) {
                    sb.append(context.getAllUnaryOperators());
                    sb.append(",");
                } else if (ParseTable.COLUMN_FID.equals(entry.getKey())) {
                    sb.append(context.getAllFunctions());
                    sb.append(",");
                } else if (ParseTable.COLUMN_ID.equals(entry.getKey())) {
                    sb.append("expression");
                    sb.append(",");
                } else if (ParseTable.COLUMN_COMMA.equals(entry.getKey()) || ParseTable.COLUMN_LPARN.equals(entry.getKey()) || ParseTable.COLUMN_RPARN.equals(entry.getKey())) {
                    sb.append("'");
                    sb.append(entry.getKey());
                    sb.append("'");
                    sb.append(",");
                }

            }
        }

        return sb.length() > 0 ? sb.toString().substring(0, sb.length() - 1) : sb.toString();
    }


}
