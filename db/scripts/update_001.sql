CREATE TABLE IF NOT EXISTS post (
    id SERIAL PRIMARY KEY,
    name TEXT,
    description TEXT,
    created TEXT,
    city_id INTEGER,
    visible BOOLEAN
);