package ru.job4j.cars.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "auto_user")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    private String login;
    private String password;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "participates",
            joinColumns = { @JoinColumn (name = "user_id") },
            inverseJoinColumns = { @JoinColumn (name = "post_id") }
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Post> participates;

    public User() {
    }
}
