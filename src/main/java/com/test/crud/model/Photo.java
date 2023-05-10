package com.test.crud.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "photos")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "il titolo non può essere vuoto o contenere solo spazi")
    @Size(min = 3,  message = "il titolo deve avere almeno 3 caratteri")
    @Size(max = 50, message = "il titolo non può superare i 50 caratteri")
    @Column(nullable = false, unique = true)
    private String title;
    @Lob
    private String description;
    @NotBlank(message = "url non può essere vuoto o contenere solo spazi")
    @Column(nullable = false)
    private String url;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
