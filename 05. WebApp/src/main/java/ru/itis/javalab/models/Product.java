package ru.itis.javalab.models;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Product {
    private Long id;
    private String title;
    private Double price;
    private User owner;
}
