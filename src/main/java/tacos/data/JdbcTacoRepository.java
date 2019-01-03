package tacos.data;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import tacos.Ingredient;
import tacos.Taco;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

public class JdbcTacoRepository implements TacoRepository {

    private JdbcTemplate jdbc;

    public JdbcTacoRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Taco save(Taco taco) {
        long tacoId = saveTacoInfo(taco);
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

    private long saveTacoInfo(Taco taco) {
        taco.setCreatedAt(new Date());
        PreparedStatementCreator psc =
                new PreparedStatementCreatorFactory(
                        "insert into Taco(name, createdAt) Values (?, ?)",
                        Types.VARCHAR, Types.TIMESTAMP)
                    .newPreparedStatementCreator(
                            Arrays.asList(taco.getName(),
                            new Timestamp(taco.getCreatedAt().getTime())));

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(psc, keyHolder);

        return keyHolder.getKey().longValue();
    }

    private void saveIngredientToTaco(String ingredient, long tacoId) {
        jdbc.update("insert into Taco_Ingredients (taco, ingredient) " +
                "values (?, ?)", tacoId, ingredient);
    }
}
