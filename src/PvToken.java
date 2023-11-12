public class PvToken {
    public int m_index;
    public String m_text;
    public int m_line;
    public int m_charBegin;

    PvToken(int index, String text, int line, int charBegin) {
        m_index = index;
        m_text = text;
        m_line = line;
        m_charBegin = charBegin;
    }

    public String toString() {
        return "Text: " + m_text
                + "\nIndex: " + m_index
                + "\nLine: " + m_line
                + "\nChar Begin: " + m_charBegin;
    }
}
