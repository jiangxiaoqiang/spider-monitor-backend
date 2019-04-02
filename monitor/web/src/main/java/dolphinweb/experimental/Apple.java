package dolphinweb.experimental;

import lombok.Data;

@Data
public class Apple implements Fruit<Apple> {

    @Override
    public void compareTo(Apple o) {
    }
}
