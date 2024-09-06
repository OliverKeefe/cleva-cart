CREATE DATABASE IF NOT EXISTS devappdb;
USE devappdb;

-- Allergen Table
CREATE TABLE Allergen (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(17) NOT NULL
);

CREATE TABLE Ingredient (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(50) NOT NULL,
                            allergen_id INT,  -- Foreign key to Allergen table
                            nutrient_id INT,  -- Foreign key to Nutrient table
                            FOREIGN KEY (allergen_id) REFERENCES Allergen(id),
                            FOREIGN KEY (nutrient_id) REFERENCES Nutrient(id)
);

CREATE TABLE Nutrient (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(50) NOT NULL
);

CREATE TABLE Recipe (
                        id INT AUTO_INCREMENT,
                        name VARCHAR(35) NOT NULL,
                        cooking_instructions TEXT,
                        PRIMARY KEY(id)
);

CREATE TABLE Ingredient_Nutrient (
                                     ingredient_id INT,
                                     nutrient_id INT,
                                     PRIMARY KEY (ingredient_id, nutrient_id),
                                     FOREIGN KEY (ingredient_id) REFERENCES Ingredient(id),
                                     FOREIGN KEY (nutrient_id) REFERENCES Nutrient(id)
);

CREATE TABLE Ingredient_Allergen (
                                     ingredient_id INT,
                                     allergen_id INT,
                                     PRIMARY KEY (ingredient_id, allergen_id),
                                     FOREIGN KEY (ingredient_id) REFERENCES Ingredient(id),
                                     FOREIGN KEY (allergen_id) REFERENCES Allergen(id)
);

CREATE TABLE Recipe_Ingredient (
                                   recipe_id INT,
                                   ingredient_id INT,
                                   PRIMARY KEY (recipe_id, ingredient_id),
                                   FOREIGN KEY (recipe_id) REFERENCES Recipe(id),
                                   FOREIGN KEY (ingredient_id) REFERENCES Ingredient(id)
);


-- Insert Allergen Data
INSERT INTO Allergen (name)
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
    ('No Major Allergen');  -- Add "No Major Allergen" to the Allergen table

INSERT INTO Nutrient (name)
VALUES
    ('Vitamin C'),
    ('Vitamin A'),
    ('Vitamin B'),
    ('Magnesium'),
    ('Iron');

-- For ingredients with allergens, reference the corresponding allergen_id
INSERT INTO Ingredient (name, allergen_id)
VALUES
    ('Wheat', (SELECT id FROM Allergen WHERE name = 'Gluten')),
    ('Cod', (SELECT id FROM Allergen WHERE name = 'Fish')),
    ('Carrot', (SELECT id FROM Allergen WHERE name = 'No Major Allergen'));






