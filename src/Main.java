import java.io.FileReader;

public class Main {
    public static void main(String[] args) {
        try {
            MyLexer lexer = new MyLexer(new FileReader("D:\\MS timovi\\IV GODINA\\PeePee\\src\\testcode.txt"));
            PvToken token;
            while ((token = lexer.next_token()) != null) {
                if (token.m_index == sym.EOF) {
                    System.out.println("End of file reached.");
                    break;
                }
                System.out.println(token);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
