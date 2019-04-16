package dolphinweb.experimental;

import lombok.Data;

@Data
public class Peach implements Fruit<Peach> {
    @Override
    public void compareTo(Peach peach) {

    }
}
