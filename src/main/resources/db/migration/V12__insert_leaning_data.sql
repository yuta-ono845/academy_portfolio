-- test1@test.com に対する学習データを追加する
INSERT INTO learning_data (user_id, category_id, study_minutes, study_month, created_at, updated_at, item_name)
VALUES 
(
    (SELECT id FROM users WHERE email = 'test1@test.com'),
    1, 30, '2025-04-01', NOW(), NOW(), 'Spring'
),
(
    (SELECT id FROM users WHERE email = 'test1@test.com'),
    2, 40, '2025-04-01', NOW(), NOW(), 'HTML'
),
(
    (SELECT id FROM users WHERE email = 'test1@test.com'),
    3, 25, '2025-03-01', NOW(), NOW(), 'AWS'
),
(
    (SELECT id FROM users WHERE email = 'test1@test.com'),
    1, 30, '2025-04-01', NOW(), NOW(), 'Spring Framework'
),
(
    (SELECT id FROM users WHERE email = 'test1@test.com'),
    1, 45, '2025-04-01', NOW(), NOW(), 'Hibernate ORM'
);