CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS players(
    id UUID NOT NULL DEFAULT uuid_generate_v1mc (),
    name VARCHAR(15) NOT NULL,
    lastname VARCHAR(15),
    surname VARCHAR(15),
    date_birthday DATE NOT NULL,
    player_position SMALLINT NOT NULL,
    team SMALLINT NOT NULL,
    status SMALLINT NOT NULL,

    created_at DATE NOT NULL DEFAULT CURRENT_DATE,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP,

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS stats(
    season_id CHARACTER(9) NOT NULL,
    player_id UUID NOT NULL,
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

CREATE TRIGGER t_player
  BEFORE UPDATE
  ON players
  FOR EACH ROW
  EXECUTE PROCEDURE upd_timestamp();