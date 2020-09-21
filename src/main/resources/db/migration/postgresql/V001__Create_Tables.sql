CREATE TABLE IF NOT EXISTS players(
    id serial PRIMARY KEY NOT NULL,
    name VARCHAR(15) NOT NULL,
    lastname VARCHAR(15),
    surname VARCHAR(15),
    date_birthday DATE NOT NULL,
    player_position VARCHAR(10) NOT NULL,
    team VARCHAR(25) NOT NULL,
    status VARCHAR(27) NOT NULL
);

CREATE TABLE IF NOT EXISTS stats(
    season_id CHARACTER(9) NOT NULL,
    player_id INTEGER NOT NULL,
    points SMALLINT DEFAULT NULL,
    match_played SMALLINT DEFAULT NULL,
    goals SMALLINT DEFAULT NULL,
    goals_conceded SMALLINT DEFAULT NULL,
    goals_penalty SMALLINT DEFAULT NULL,
    yellow_cards SMALLINT DEFAULT NULL,
    double_yellow_cards SMALLINT DEFAULT NULL,
    red_cards SMALLINT DEFAULT NULL,

    CONSTRAINT pk_stat PRIMARY KEY (season_id, player_id),
    CONSTRAINT fk_player FOREIGN KEY (player_id) REFERENCES players (id)
);
