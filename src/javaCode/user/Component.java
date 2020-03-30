package javaCode.user;

public class Component {
    private int ID;
    private String type;
    private int price;

    public Component(int ID, String type, int price) {
        this.ID = ID;
        this.type = type;
        this.price = price;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

class Motor extends Component{
    private int hk;

    public Motor(int ID, String type, int price, int hk) {
        super(ID, type, price);
        this.hk = hk;
    }

    public int getHk() {
        return hk;
    }

    public void setHk(int hk) {
        this.hk = hk;
    }
}

class Wheel extends Component{
    public Wheel(int ID, String type, int price) {
        super(ID, type, price);
    }
}

class Rims extends Component{
    public Rims(int ID, String type, int price) {
        super(ID, type, price);
    }
}

class Setetrekk extends Component{
    public Setetrekk(int ID, String type, int price) {
        super(ID, type, price);
    }
}
