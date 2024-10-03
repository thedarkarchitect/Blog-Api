package com.example.blogApi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

//@Data
@Getter
@Setter
@Entity
@Builder // this is used to create a builder class for the entity
@NoArgsConstructor
@AllArgsConstructor
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;

    @Lob
    private byte[] imageData;
    private String downloadUrl;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "post_id")
    private Post post;
}
