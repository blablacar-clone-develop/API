-- Create cars_brands table
CREATE TABLE cars_brands (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             name VARCHAR(255) NOT NULL
);

-- Create cars_models table
CREATE TABLE cars_models (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             name VARCHAR(255) NOT NULL,
                             brand_id BIGINT,
                             FOREIGN KEY (brand_id) REFERENCES cars_brands(id)
);

CREATE TABLE colors
(
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            hex  VARCHAR(255) NOT NULL,
                            name VARCHAR(255) NOT NULL
);