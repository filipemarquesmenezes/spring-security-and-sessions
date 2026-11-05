-- User user/pass
INSERT INTO users (username, password, enabled)
  values ('user',
    '$2y$10$ywYZV4BajisPek24slhRIeAqWrz2uiHv4ppurCT0Cy6QGQ3WFRqbq',
    1);
INSERT INTO users (username, password, enabled)
values ('admin',
 '$2y$10$UvN57CBXKnLYAs.qhjYM/ODogROp4yks5KIt.PlyM.BmrLnfuUf7a',
 1);

INSERT INTO authorities (username, authority)
  values ('user', 'ROLE_USER');

INSERT INTO authorities (username, authority)
  values ('user', 'ROLE_ADMIN');