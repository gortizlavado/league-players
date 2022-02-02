INSERT INTO players (id, name, lastname, surname, date_birthday, player_position, team, status)
VALUES (0, 'name1', 'lastname1', 'surname1', now(), 'GOALKEEPER', 'MERIDA_AD', 'ACTIVE'),
(1, 'name2', 'lastname2', 'surname2', now(), 'FORWARD', 'MERIDA_AD', 'ACTIVE');


INSERT INTO stats (season_id, player_id, points, match_played, goals, goals_conceded, goals_penalty, yellow_cards, double_yellow_cards, red_cards)
VALUES ('2020/2021', 0, 10, 4, 0, 2, 0, 1, 0, 0),
('2019/2020', 1, 125, 22, 12, 0, 3, 5, 2, 1),
('2020/2021', 1, 30, 4, 4, 0, 1, 1, 0, 0);