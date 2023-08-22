package fhellipe.github.com.crudbyloianeback.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SQLDelete(sql = "UPDATE Course SET status = 'Inativo' WHERE id = ?" )
@Where(clause = "status = 'Ativo'")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("_id")
    private Long id;

    @NotNull
    @NotBlank
    @Length(min = 5, max = 20)
    @Column(length = 10, nullable = false)
    private String name;

    @NotNull
    @Length(max = 20)
    @Column(length = 10, nullable = false)
    @Pattern(regexp = "Back-End|Front-End")
    private String category;

    @NotNull
    @Length(max = 10)
    @Column(length = 10, nullable = false)
    @Pattern(regexp = "Ativo|Inativo")
    private String status = "Ativo";
}
