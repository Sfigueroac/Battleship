package model;

public class Ship {
    private ShipType type;
    private Coords coords;
    private int state;

    public Ship(Coords coords, int state, ShipType type) {
        this.coords = coords;
        this.state = state;
        this.type = type;
    }

    public ShipType getType() {
        return type;
    }

    public void setType(ShipType type) {
        this.type = type;
    }

    public Coords getCoords() {
        return coords;
    }

    public void setCoords(Coords coords) {
        this.coords = coords;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

}

