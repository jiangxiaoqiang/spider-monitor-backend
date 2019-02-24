package model;

public class Sort {
    private String orderField;
    private Direction direction;

    public Sort(Direction direction, String orderField) {
        this.direction = direction;
        this.orderField = orderField;
    }

    public String getOrderField() {
        return orderField;
    }

    public Direction getDirection() {
        return direction;
    }

    public static enum Direction {
        ASC, DESC, NULLSLAST, RANDOM
    }
}
