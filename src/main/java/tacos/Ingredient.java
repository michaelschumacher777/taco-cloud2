package tacos;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Ingredient {

    private final String id;
    private final String name;

    private final Type type;

    public static enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

}
