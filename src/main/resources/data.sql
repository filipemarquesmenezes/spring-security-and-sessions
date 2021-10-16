-- User user/pass
INSERT INTO users (username, password, enabled)
  values ('user',
    '$2a$10$jurk50S8K5heCZe1ZU.GB.mU1MdknUB18nMgifyXk8O2eaZ1HL7VG',
    1);

INSERT INTO authorities (username, authority)
  values ('user', 'ROLE_USER');


INSERT INTO users (username, password, enabled)
values ('admin',
  '$2a$10$jurk50S8K5heCZe1ZU.GB.mU1MdknUB18nMgifyXk8O2eaZ1HL7VG',
  1);

INSERT INTO authorities (username, authority)
 values ('admin', 'ROLE_ADMIN');
 INSERT INTO authorities (username, authority)
  values ('admin', 'ROLE_USER');