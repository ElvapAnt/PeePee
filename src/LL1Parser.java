import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;
public class LL1Parser {
    private HashMap<String, Integer> rowMap = new HashMap<>();
    private HashMap<String, Integer> columnMap = new HashMap<>();
    private String[][] syntaxTable = new String[16][10];
    private String[] rules = new String[6];
    public LL1Parser() {
        // Initialize rowMap with non-terminals
        rowMap.put("AE", 0);
        rowMap.put("NL", 1);
        rowMap.put("NL'", 2);
        rowMap.put("E", 3);
        rowMap.put("E'", 4);
        rowMap.put("T", 5);
        rowMap.put("for", 6);
        rowMap.put("ID", 7);
        rowMap.put("in", 8);
        rowMap.put("[", 9);
        rowMap.put("]", 10);
        rowMap.put("apply", 11);
        rowMap.put("+", 12);
        rowMap.put("CONST", 13);
        rowMap.put(",", 14);
        rowMap.put("#",15);


        columnMap.put("for", 0);
        columnMap.put("ID", 1);
        columnMap.put("in", 2);
        columnMap.put("[", 3);
        columnMap.put("]", 4);
        columnMap.put("apply", 5);
        columnMap.put("+", 6);
        columnMap.put("CONST", 7);
        columnMap.put(",", 8);
        columnMap.put("#",9);

        rules[0] = "for ID in [ NL ] apply E";
        rules[1] = "ID NL'";
        rules[2] = ", ID NL'";
        rules[3] = "";
        rules[4] = "T E'";
        rules[5] = "+ T E'";

        for (String[] strings : syntaxTable) {
            Arrays.fill(strings, "");
        }

        for(int i = 6; i < 16; ++i) {
            this.syntaxTable[i][i - 6] = "pop";
        }

        this.syntaxTable[15][9] = "acc";
        this.syntaxTable[0][0] = "0";
        this.syntaxTable[1][1] = "1";
        this.syntaxTable[2][4] = "2";
        this.syntaxTable[2][9] = "3";
        this.syntaxTable[3][1] = "4";
        this.syntaxTable[3][7] = "5";
        this.syntaxTable[4][6] = "6";
        this.syntaxTable[4][9] = "7";
        this.syntaxTable[5][1] = "8";
        this.syntaxTable[5][7]="9";

        for(int i = 0; i < 16; ++i) {
            for(int j = 0; j < 10; ++j) {
                if (this.syntaxTable[i][j] == "") {
                    this.syntaxTable[i][j] = "err";
                }
            }
        }

    }
    public void reverse(String[] a) {
        for (int i = 0; i < a.length / 2; i++) {
            String temp = a[i];
            a[i] = a[a.length - 1 - i];
            a[a.length - 1 - i] = temp;
        }
    }

    public String M(String top, PvToken next) {
        // Assuming top is a non-terminal and next.m_text contains the terminal symbol
        if (next.m_index == sym.ID) { // sym.ID should be the integer value representing ID tokens in your lexer
            return top.equals("ID") ? "pop" : syntaxTable[rowMap.get(top)][columnMap.get("ID")];
        } else if (next.m_index == sym.CONST) { // sym.CONST should be the integer value representing CONST tokens in your lexer
            return top.equals("CONST") ? "pop" : syntaxTable[rowMap.get(top)][columnMap.get("CONST")];
        } else if (top.equals("#") && next.m_text.equals("#")) {
            return "acc";
        } else if (top.equals(next.m_text)) {
            return "pop";
        } else {
            return columnMap.containsKey(next.m_text) ? syntaxTable[rowMap.get(top)][columnMap.get(next.m_text)] : "err";
        }
    }

    public boolean SA_LL1(MyLexer lexer) {
        Stack<String> stack = new Stack<>();
        stack.push("#");
        stack.push("AE"); // Start symbol of your grammar
        boolean recognized = false;
        boolean error = false;

        try {
            PvToken next = lexer.next_token();

            while (!recognized && !error) {
                String top = stack.peek();
                String action = M(top, next);

                switch (action) {
                    case "acc":
                        recognized = true;
                        break;
                    case "err":
                        error = true;
                        break;
                    case "pop":
                        stack.pop();
                        if (!stack.isEmpty()) {
                            next = lexer.next_token();
                        }
                        break;
                    default:
                        stack.pop();
                        String[] ruleToApply = rules[Integer.parseInt(action)].split(" ");
                        reverse(ruleToApply);
                        for (String symbol : ruleToApply) {
                            if (!symbol.equals("")) {
                                stack.push(symbol);
                            }
                        }
                        break;
                }
            }

            return recognized;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
