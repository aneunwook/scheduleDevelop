CREATE TABLE user (
      id BIGINT AUTO_INCREMENT PRIMARY KEY,
      email VARCHAR(255) NOT NULL UNIQUE,
      username VARCHAR(255) NOT NULL,
      password VARCHAR(255) NOT NULL,
      created_at DATETIME(6),
      updated_at DATETIME(6)
);


CREATE TABLE schedule (
      id BIGINT AUTO_INCREMENT PRIMARY KEY,
      title VARCHAR(255) NOT NULL,
      contents VARCHAR(255) NOT NULL,
      user_id BIGINT NOT NULL,
      created_at DATETIME(6),
      updated_at DATETIME(6),
      CONSTRAINT fk_schedule_user FOREIGN KEY (user_id) REFERENCES user(id)
);
