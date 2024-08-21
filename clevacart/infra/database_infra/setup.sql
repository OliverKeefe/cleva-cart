CREATE DATABASE IF NOT EXISTS devappdb;
USE devappdb;

-- Allergen Table
CREATE TABLE Allergen (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(17) NOT NULL
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

-- Ingredient Table
CREATE TABLE Ingredient (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(50) NOT NULL,
                            allergen_id INT,  -- Foreign key to Allergen table
                            FOREIGN KEY (allergen_id) REFERENCES Allergen(id)
);

-- Insert Ingredients
-- For ingredients with allergens, reference the corresponding allergen_id
INSERT INTO Ingredient (name, allergen_id)
VALUES
    ('Wheat', (SELECT id FROM Allergen WHERE name = 'Gluten')),
    ('Cod', (SELECT id FROM Allergen WHERE name = 'Fish')),
    ('Carrot', (SELECT id FROM Allergen WHERE name = 'No Major Allergen'));