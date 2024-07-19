INSERT INTO finance_management.customer (name, phone, address, adhar_card_number) VALUES ('John Doe', '1234567890', '123 Main St', '123412341234');
INSERT INTO finance_management.customer (name, phone, address, adhar_card_number) VALUES ('Jane Doe', '0987654321', '456 Side St', '432143214321');

INSERT INTO finance_management.vehicle (vehicle_number, vehicle_model, insurance_validity, customer_id) VALUES ('XYZ123', 'Toyota Camry', '2023-12-31', 1);
INSERT INTO finance_management.vehicle (vehicle_number, vehicle_model, insurance_validity, customer_id) VALUES ('ABC456', 'Honda Accord', '2024-06-30', 2);

INSERT INTO finance_management.loan (loan_amount, emi_tenure, monthly_emi, emi_date, vehicle_id) VALUES (50000.00, 24, 2083.33, '2024-07-15', 1);
INSERT INTO finance_management.loan (loan_amount, emi_tenure, monthly_emi, emi_date, vehicle_id) VALUES (30000.00, 12, 2500.00, '2024-07-15', 2);
