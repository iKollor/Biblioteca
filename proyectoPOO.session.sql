-- Creación de la tabla 'autores'
CREATE TABLE autores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    nacionalidad VARCHAR(255)
);

-- Creación de la tabla 'libros'
CREATE TABLE libros (
    ISBN INT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    autor_id INT,
    year YEAR,
    edition INT,
    pages INT,
    FOREIGN KEY (autor_id) REFERENCES autores(id)
);

-- Creación de la tabla 'copias_libro'
CREATE TABLE copias_libro (
    id INT AUTO_INCREMENT PRIMARY KEY,
    libro_ISBN INT,
    estado VARCHAR(50),
    FOREIGN KEY (libro_ISBN) REFERENCES libros(ISBN)
);

-- Creación de la tabla 'usuarios'
CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    password VARCHAR(255),
    dni VARCHAR(20)
);

-- Creación de la tabla 'prestamos'
CREATE TABLE prestamos (
    prestamo_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    libro_id INT,
    date_prestamo DATE,
    fecha_prestamo DATE,
    devuelto TINYINT,
    FOREIGN KEY (user_id) REFERENCES usuarios(id),
    FOREIGN KEY (libro_id) REFERENCES copias_libro(id)
);
