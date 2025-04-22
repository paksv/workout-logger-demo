-- Sample Users
INSERT INTO users (username, email, password_hash) VALUES
('john_doe', 'john@example.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG'), -- password: password
('jane_smith', 'jane@example.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG') -- password: password
ON CONFLICT (username) DO NOTHING;

-- Sample Athletes
INSERT INTO athletes (user_id, height, weight, fitness_goals) VALUES
(1, 180.5, 75.0, 'Build muscle and improve endurance'),
(2, 165.0, 60.0, 'Lose weight and tone muscles')
ON CONFLICT DO NOTHING;

-- Sample Exercises
INSERT INTO exercises (name, description, category, muscle_groups, is_custom, user_id) VALUES
('Push-up', 'A classic bodyweight exercise that targets the chest, shoulders, and triceps', 'STRENGTH', 'CHEST,SHOULDERS,TRICEPS', FALSE, NULL),
('Squat', 'A compound exercise that targets the legs and glutes', 'STRENGTH', 'LEGS', FALSE, NULL),
('Plank', 'An isometric core exercise that improves stability', 'STRENGTH', 'CORE', FALSE, NULL),
('Running', 'Cardiovascular exercise that improves endurance', 'CARDIO', 'FULL_BODY', FALSE, NULL),
('Custom Kettlebell Swing', 'A dynamic exercise using a kettlebell', 'STRENGTH', 'BACK,LEGS', TRUE, 1)
ON CONFLICT DO NOTHING;

-- Sample Workouts
INSERT INTO workouts (name, user_id, date, duration, notes) VALUES
('Morning Routine', 1, CURRENT_DATE - 7, 1800, 'Felt great, increased weight on squats'),
('Cardio Session', 1, CURRENT_DATE - 3, 2400, 'Focused on maintaining steady pace'),
('Full Body Workout', 2, CURRENT_DATE - 5, 3600, 'Increased reps on all exercises')
ON CONFLICT DO NOTHING;

-- Sample Workout Exercises
INSERT INTO workout_exercises (workout_id, exercise_id, "order") VALUES
(1, 1, 1), -- Push-up in Morning Routine
(1, 2, 2), -- Squat in Morning Routine
(1, 3, 3), -- Plank in Morning Routine
(2, 4, 1), -- Running in Cardio Session
(3, 1, 1), -- Push-up in Full Body Workout
(3, 2, 2), -- Squat in Full Body Workout
(3, 3, 3), -- Plank in Full Body Workout
(3, 4, 4)  -- Running in Full Body Workout
ON CONFLICT DO NOTHING;

-- Sample Exercise Sets
INSERT INTO exercise_sets (workout_exercise_id, reps, weight, duration, distance, completed, "order") VALUES
(1, 15, NULL, NULL, NULL, TRUE, 1),
(1, 12, NULL, NULL, NULL, TRUE, 2),
(1, 10, NULL, NULL, NULL, TRUE, 3),
(2, 12, 60.0, NULL, NULL, TRUE, 1),
(2, 10, 70.0, NULL, NULL, TRUE, 2),
(3, NULL, NULL, 60, NULL, TRUE, 1),
(4, NULL, NULL, 1200, 5.0, TRUE, 1),
(5, 12, NULL, NULL, NULL, TRUE, 1),
(5, 10, NULL, NULL, NULL, TRUE, 2),
(6, 15, 50.0, NULL, NULL, TRUE, 1),
(7, NULL, NULL, 45, NULL, TRUE, 1),
(8, NULL, NULL, 900, 3.0, TRUE, 1)
ON CONFLICT DO NOTHING;