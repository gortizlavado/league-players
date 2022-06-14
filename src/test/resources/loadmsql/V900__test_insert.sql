INSERT INTO players (id, name, lastname, surname, date_birthday, player_position, team, status, created_at, updated_at, deleted_at)
VALUES ('00000001-f1b1-11ec-8ea0-0242ac120002', 'name1', 'lastname1', 'surname1', now(), 0, 15, 0, now(), null, null),
('00000002-f1b1-11ec-8ea0-0242ac120002', 'name2', 'lastname2', 'surname2', '1989-11-09', 3, 15, 0, now(), null, null);

INSERT INTO players (name, lastname, surname, date_birthday, player_position, team, status)
VALUES ('name3', 'lastname3', 'surname3', '1989-06-23', 0, 0, 0);

INSERT INTO stats (season_id, player_id, points, match_played, goals, goals_conceded, goals_penalty, yellow_cards, double_yellow_cards, red_cards)
VALUES ('2020/2021', '00000001-f1b1-11ec-8ea0-0242ac120002', 10, 4, 0, 2, 0, 1, 0, 0),
('2019/2020', '00000002-f1b1-11ec-8ea0-0242ac120002', -6, 22, 12, 0, 3, 5, 2, 1),
('2020/2021', '00000002-f1b1-11ec-8ea0-0242ac120002', 10, 4, 4, 0, 1, 1, 0, 0);