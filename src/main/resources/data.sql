delete from Taco_Order_Tacos;
delete from Taco_Ingredients;
delete from Taco;
delete from Taco_Order;

delete from Ingredient;

insert into ingredient (id, name, type)
    values ('FLTO', 'Flour Tortilla', 'WRAP');
insert into ingredient (id, name, type)
    values ('COTO', 'Corn Tortilla', 'WRAP');
insert into ingredient (id, name, type)
    values ('GRBF', 'Ground Beef', 'PROTEIN');
insert into ingredient (id, name, type)
    values ('CARN', 'Carnitas', 'PROTEIN');
insert into ingredient (id, name, type)
    values ('TMTO', 'Diced Tomatoes', 'VEGGIES');
insert into ingredient (id, name, type)
    values ('LETC', 'Lettuce', 'VEGGIES');
insert into ingredient (id, name, type)
    values ('CHED', 'Cheddar', 'CHEESE');
insert into ingredient (id, name, type)
    values ('JACK', 'Monterrey Jack', 'CHEESE');
insert into ingredient (id, name, type)
    values ('SLSA', 'Salsa', 'SAUCE');
insert into ingredient (id, name, type)
    values ('SOUR', 'Sour Cream', 'SAUCE');

