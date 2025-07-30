Create TABLE nachricht (
    id uuid primary key,
    text varchar(255),
    created_at timestamp with time zone default now() not null
);



DROP TABLE IF EXISTS nachricht;
CREATE TABLE nachricht (
                           id UUID PRIMARY KEY,
                           text TEXT,
                           created_at TIMESTAMPTZ DEFAULT now() NOT NULL
);
