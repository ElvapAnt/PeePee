import java.util.Hashtable;

public class KWTable {
    private Hashtable<String, Integer> mTable;

    public KWTable() {
        mTable = new Hashtable<>();
        mTable.put("main", sym.MAIN);
        mTable.put("exit", sym.EXIT);
        mTable.put("int", sym.INT);
        mTable.put("float", sym.FLOAT);
        mTable.put("bool", sym.BOOL);
        mTable.put("for", sym.FOR);
        mTable.put("in", sym.IN);
        mTable.put("apply", sym.APPLY);
    }

    public int find(String keyword) {
        Object symbol = mTable.get(keyword);
        if (symbol != null)
            return (Integer) symbol;
        return sym.ID;
    }
}
