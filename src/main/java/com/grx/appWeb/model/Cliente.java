package com.grx.appWeb.model;

import jakarta.persistence.*;

/*
Aquí ocurre el verdadero mapeo ORM. Usando anotaciones (esas palabras que empiezan con @), le enseñas a Hibernate cómo traducir los conceptos de Java a conceptos de base de datos relacional.@Entity y @Table:

Con @Entity marcas esta clase común y corriente para que Hibernate la gestione.

Con @Table(name = "clientes"), le dices explícitamente que los objetos de esta clase se guardarán como filas en una tabla llamada "clientes" en MariaDB.

@Id y @GeneratedValue: La base de datos necesita una clave primaria (Primary Key). Al poner @Id sobre Long id, designas esa propiedad como el identificador. El @GeneratedValue(strategy = GenerationType.IDENTITY) delega la responsabilidad a MariaDB de usar el autoincremento (el ID será 1, 2, 3... de forma automática sin que tú lo envíes).

@Column: Agrega restricciones SQL a los atributos. Por ejemplo, @Column(nullable = false, unique = true) sobre el email se traduce en la base de datos como las restricciones NOT NULL y UNIQUE. Esto asegura a nivel de base de datos que no existan clientes sin correo o con correos repetidos.
*/

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String email;

    public Cliente() {
    }

    public Cliente(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
