INSERT INTO Filme (titulo, diretor, estoque) VALUES
    ('Godzilla 200', 'Takao Okawara', 1),
    ('Godzilla Contra Ataca', 'Motoyoshi Oda', 5);

INSERT INTO Usuario (nome, email, senha) VALUES
    ('Cesar Augusto', 'cliente@gmail.com', '$2a$10$AfL27fLE26vSQvn2pJnVzeiNo.4lqlagqEAhpx6l0sHSrIuoxWpEW'),
    ('Pedro Pedigru','pedro.pedigru@gmail.com', '$2a$10$Q8UizEq/Q56s3aDlG2bkFuCcphH.C9rBe2moRkUeCXwd//BYZbWyG');

    -- SENHAS => CESAR:cesaraugusto123 PEDRO:1234567@

INSERT INTO Alugador (usuario_id, filme_id, data_aluguel) VALUES
    (1, 1, '2022-03-12'),
    (2,2, '2022-03-11');