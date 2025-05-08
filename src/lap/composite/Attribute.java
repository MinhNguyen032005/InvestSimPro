package lap.composite;

import java.util.UUID;

public class Attribute {
    private String name;
    private String value;
    private String status;

    @Override
    public String toString() {
        return "Book" + "(id" + UUID.randomUUID() + ")" + "status=" + status + "\n" +
                "\t" + "Name:" + name + "\n" ;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
