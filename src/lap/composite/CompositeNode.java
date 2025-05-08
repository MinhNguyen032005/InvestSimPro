package lap.composite;

import java.util.ArrayList;
import java.util.List;

public class CompositeNode extends XML {
    private List<XML> xmls = new ArrayList<>();

    @Override
    public void addNode(XML xml) {
        xmls.add(xml);
    }

    @Override
    public void remove(XML xml) {
        xmls.remove(xml);
    }

    @Override
    public String toString() {
       String re="";
       for (XML xml: xmls){
           re+="Book"+xml+"\n";
       }
       return re;
    }

    @Override
    public void print(String string) {

    }
}
