package tacos.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import tacos.Taco;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Repository
public class JdbcTacoRepository implements TacoRepository {

    private SimpleJdbcInsert tacoInserter;
    private SimpleJdbcInsert tacoIngredientInserter;
    private ObjectMapper objectMapper;

    @Autowired
    public JdbcTacoRepository(JdbcTemplate jdbc) {
        this.tacoInserter = new SimpleJdbcInsert(jdbc).withTableName("Taco").usingGeneratedKeyColumns("id");
        this.tacoIngredientInserter = new SimpleJdbcInsert(jdbc).withTableName("Taco_Ingredients");
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Taco save(Taco taco) {
        if (taco.getCreatedAt() == null) {
            taco.setCreatedAt(new Date());
        }
        long tacoId = saveTacoDetails(taco);
        taco.setId(tacoId);
        // This forEach isn't like the book, the book wanted to use the object Ingredient,
        // which doesn't match what the current code base is, the "taco.getIngredients()"
        // returns a List<String> - so the type needs to be String instead of Ingredient,
        // which this also affects the private method saveIngredientToTaco as that was
        // also wanting an Ingredient. It needs to be a String instead.
        for (String ingredient : taco.getIngredients()) {
            saveIngredientToTaco(ingredient, tacoId);
        }
        return taco;
    }

    private long saveTacoDetails(Taco taco) {
        Map<String, Object> values = objectMapper.convertValue(taco, Map.class);
        values.put("createdAt", taco.getCreatedAt());
        long tacoId = tacoInserter.executeAndReturnKey(values).longValue();

        return tacoId;
    }

    private void saveIngredientToTaco(String ingredient, long tacoId) {
        Map<String, Object> values = new HashMap<>();
        values.put("taco", tacoId);
        values.put("ingredient", ingredient);
        tacoIngredientInserter.execute(values);
    }
}
