INSERT INTO roles(name, description, created_time, update_time) VALUES("ROLE_ADMIN","rol administrador", now(), now());
INSERT INTO roles(name, description, created_time, update_time) VALUES("ROLE_USER", "rol usurio", now(), now());

INSERT INTO users(first_name, last_name, email, password, photo, role_id, deleted) VALUES("Nombre1", "Apellido1", "correo1@emil.com", "contraseña", "default.jpg", 2, 0);

INSERT INTO news(content, name, image, deleted) VALUES("Contenido Notocia 1", "Noticia 1", "default.jpg", 0);

INSERT INTO comments(body, news_id, user_id, deleted) VALUES("Contenido body Comentario1", 1, 1, 0);

INSERT INTO categories(name, description, image, deleted) VALUES("Categoria 1", "descripcion categoria 1", "default.jpg", 0);

INSERT INTO news_to_category(news_id, category_id) VALUES(1,1);