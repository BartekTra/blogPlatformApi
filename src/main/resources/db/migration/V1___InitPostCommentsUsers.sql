CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       display_name VARCHAR(100),
                       age INT,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL
);

CREATE TABLE posts (
                       id BIGSERIAL PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       content TEXT NOT NULL,
                       category VARCHAR(100),
                       tags VARCHAR(255)
);

CREATE TABLE comments (
                          id BIGSERIAL PRIMARY KEY,
                          content TEXT NOT NULL,
                          post_id BIGINT NOT NULL,
                          user_id BIGINT NOT NULL,
                          CONSTRAINT fk_comment_post
                              FOREIGN KEY (post_id)
                                  REFERENCES posts(id)
                                  ON DELETE CASCADE,
                          CONSTRAINT fk_comment_user
                              FOREIGN KEY (user_id)
                                  REFERENCES users(id)
                                  ON DELETE CASCADE
);

CREATE TABLE post_likes (
                            post_id BIGINT NOT NULL,
                            user_id BIGINT NOT NULL,
                            PRIMARY KEY (post_id, user_id),
                            CONSTRAINT fk_post_likes_post
                                FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
                            CONSTRAINT fk_post_likes_user
                                FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE comment_likes (
                               comment_id BIGINT NOT NULL,
                               user_id BIGINT NOT NULL,
                               PRIMARY KEY (comment_id, user_id),
                               CONSTRAINT fk_comment_likes_comment
                                   FOREIGN KEY (comment_id) REFERENCES comments(id) ON DELETE CASCADE,
                               CONSTRAINT fk_comment_likes_user
                                   FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);