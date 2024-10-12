package bebra.rzhork_ua_rest.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Entity
@Getter @Setter @NoArgsConstructor
@NamedQuery(
        name = "Company.findFilteredCompanies",
        query = "SELECT c FROM Company c WHERE c.title LIKE :search OR c.location LIKE :search"
)
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, unique = true)
    private String title;
    private String location;
    private String description;
    private int joinYear;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Vacancy> vacancies;
}
