package lap.composite;

import java.util.ArrayList;
import java.util.List;

public abstract class XML {
    protected String name;
    protected List<Attribute> ans = new ArrayList<>();

    public abstract void addNode(XML xml);

    public abstract void remove(XML xml);
    public abstract void print(String string);
}
