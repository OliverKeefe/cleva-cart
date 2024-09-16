CREATE DATABASE IF NOT EXISTS MealServiceDB;
USE MealServiceDB;

-- Allergy Table, includes the names of all known allergies and intolerances.
CREATE TABLE Allergen (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(25) NOT NULL
);

INSERT INTO Allergen (name)
VALUES
    ('Fish'),
    ('Gluten');

-- Major Allergen Table, includes the 18 major allergens.
CREATE TABLE MajorAllergen (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(17) NOT NULL
);

-- Insert Values into Major Allergen Table
INSERT INTO MajorAllergen (name)
VALUES
    ('Gluten'),
    ('Celery'),
    ('Crustaceans'),
    ('Eggs'),
    ('Fish'),
    ('Lupin'),
    ('Milk'),
    ('Molluscs'),
    ('Mustard'),
    ('Nuts'),
    ('Peanuts'),
    ('Sesame'),
    ('Soya'),
    ('Sulphites'),
    ('No Major Allergen');

-- Nutrient Table, includes Vitamins, Minerals, Water Content etc...
CREATE TABLE Nutrient (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    category VARCHAR(25) NOT NULL,
    measurement VARCHAR(15) NOT NULL
);

INSERT INTO Nutrient (name, category, measurement)
VALUES
    ('Alpha Carotene', 'Carotenoid', 'mcg'),
    ('Beta Carotene', 'Carotenoid', 'mcg'),
    ('Beta Cryptoxanthin', 'Carotenoid', 'mcg'),
    ('Carbohydrate', 'Macronutrient', 'g'),
    ('Cholesterol', 'Steroid', 'mmol/L'),
    ('Choline', 'Other Nutrient', 'mg'),
    ('Fiber', 'Macronutrient', 'g'),
    ('Lutein and Zeaxanthin', 'Carotenoid', 'mg'),
    ('Lycopene', 'Carotenoid', 'mg' ),
    ('Niacin (Vitamin B3)', 'Vitamin', 'mg'),
    ('Protein', 'Macronutrient', 'g'),
    ('Retinol', 'Retinoid', 'mcg'), -- µg is same as mcg
    ('Riboflavin (Vitamin B2)', 'Vitamin', 'mcg' ),
    ('Selenium', 'Mineral', 'mcg'),
    ('Sugar', 'Macronutrient', 'g'),
    ('Thiamin (Vitamin B1)', 'Thiamine', 'mg'),
    ('Water', 'Macronutrient', 'ml'),
    ('Monosaturated Fat', 'Fat', 'g'),
    ('Polysaturated Fat', 'Fat', 'g'),
    ('Saturated Fat', 'Fat', 'g'),
    ('Lipid Fat', 'Fat', 'g'),
    ('Calcium', 'Mineral', 'mg'),
    ('Copper', 'Mineral', 'mcg'),
    ('Iron', 'Mineral', 'mg'),
    ('Magnesium', 'Mineral', 'mg'),
    ('Phosphorus', 'Mineral', 'mg'),
    ('Potassium', 'Mineral', 'mg'),
    ('Sodium', 'Mineral', 'mg'),
    ('Zinc', 'Mineral', 'mg'),
    ('Vitamin A - RAE (Retinol Activity Equivalents)', 'Vitamin', 'mcg'),
    ('Vitamin K', 'Vitamin', 'mcg');

-- Ingredient Table
CREATE TABLE Ingredient (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL

);

CREATE TABLE IngredientAllergen (
    ingredient_id INT,
    allergen_id INT,
    PRIMARY KEY (ingredient_id, allergen_id),
    FOREIGN KEY (ingredient_id) REFERENCES Ingredient(id),
    FOREIGN KEY (allergen_id) REFERENCES Allergen(id)
);

CREATE TABLE IngredientNutrient (
    ingredient_id INT,
    nutrient_id INT,
    quantity DECIMAL(10, 2) NOT NULL,
    measurement VARCHAR(15) NOT NULL
);

CREATE TABLE Recipe (
    id INT AUTO_INCREMENT,
    name VARCHAR(35) NOT NULL,
    cooking_instructions TEXT,
    PRIMARY KEY(id)
);

INSERT INTO Allergen (name) VALUES ('Fish');
INSERT INTO Ingredient (name) VALUES ('Cod');

-- IngredientAllergy Table, link Cod to the Fish allergy
INSERT INTO IngredientAllergen (ingredient_id, allergen_id)
VALUES ((SELECT id FROM Ingredient WHERE name = 'Cod'), (SELECT id FROM Allergen WHERE name = 'Fish'));


-- IngredientNutrient Table, for ingredients with allergens, reference the corresponding allergen_id
INSERT INTO IngredientNutrient (ingredient_id, nutrient_id, quantity, measurement)
VALUES
    ((SELECT id FROM Ingredient WHERE name = 'Cod'), (SELECT id FROM Nutrient WHERE name = 'Protein'), 20, 'g'),
    ((SELECT id FROM Ingredient WHERE name = 'Cod'), (SELECT id FROM Nutrient WHERE name = 'Calcium'), 80, 'mg'),
    ((SELECT id FROM Ingredient WHERE name = 'Cod'), (SELECT id FROM Nutrient WHERE name = 'Sodium'), 200, 'mg');

CREATE TABLE RecipeIngredient (
    recipe_id INT,
    ingredient_id INT,
    PRIMARY KEY (recipe_id, ingredient_id),
    FOREIGN KEY (recipe_id) REFERENCES Recipe(id),
    FOREIGN KEY (ingredient_id) REFERENCES Ingredient(id)
);