CREATE TABLE IF NOT EXISTS finance_management.customer (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(10) NOT NULL,
    address VARCHAR(255) NOT NULL,
    adhar_card_number VARCHAR(12) NOT NULL
);

CREATE TABLE IF NOT EXISTS finance_management.vehicle (
    id SERIAL PRIMARY KEY,
    vehicle_number VARCHAR(255) NOT NULL,
    vehicle_model VARCHAR(255) NOT NULL,
    insurance_validity DATE NOT NULL,
    customer_id BIGINT NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES finance_management.customer(id)
);

CREATE TABLE IF NOT EXISTS finance_management.loan (
    id SERIAL PRIMARY KEY,
    loan_amount DECIMAL(15, 2) NOT NULL,
    emi_tenure INT NOT NULL,
    monthly_emi DECIMAL(15, 2) NOT NULL,
    emi_date DATE NOT NULL,
    vehicle_id BIGINT NOT NULL,
    FOREIGN KEY (vehicle_id) REFERENCES finance_management.vehicle(id)
);
