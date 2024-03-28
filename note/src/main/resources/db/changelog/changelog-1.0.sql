-- Create table for Label entity
CREATE TABLE IF NOT EXISTS label (
                                     id SERIAL PRIMARY KEY NOT NULL ,  -- Use SERIAL here
                                     title VARCHAR(255) NOT NULL,
                                    git description VARCHAR(255)
    );

-- Create table for Note entity
CREATE TABLE IF NOT EXISTS note (
                                    id SERIAL PRIMARY KEY NOT NULL ,  -- Use SERIAL here
                                    label VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    note VARCHAR(255) NOT NULL,
    date_created TIMESTAMP,
    last_updated TIMESTAMP
    );

-- Create join table for many-to-many relationship between Label and Note
CREATE TABLE IF NOT EXISTS note_label (
                                          note_id BIGINT NOT NULL,
                                          label_id BIGINT NOT NULL,
                                          PRIMARY KEY (note_id, label_id),
    CONSTRAINT fk_note_label_note FOREIGN KEY (note_id) REFERENCES note (id),
    CONSTRAINT fk_note_label_label FOREIGN KEY (label_id) REFERENCES label (id)
    );

-- Create join table for many-to-many relationship between Note and Note (self-referencing)
CREATE TABLE IF NOT EXISTS note_note (
                                         note_id BIGINT NOT NULL,
                                         related_note_id BIGINT NOT NULL,
                                         PRIMARY KEY (note_id, related_note_id),
    CONSTRAINT fk_note_note_note1 FOREIGN KEY (note_id) REFERENCES note (id),
    CONSTRAINT fk_note_note_note2 FOREIGN KEY (related_note_id) REFERENCES note (id)
    );