package co.com.poli.cloud.project.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Date;


@Entity
@Table(name = "tbl_projects")
@Getter
@Setter
@AllArgsConstructor
public class Project {

    public Project() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El nombre del proyecto no puede ser vacio")
    @Column(name = "project_name", nullable = false)
    private String projectName;
    @NotEmpty(message = "El identificador del proyecto no puede ser vacio")
    @Column(name = "project_identifier", nullable = false)
    private String projectIdentifier;
    @NotEmpty(message = "La descripción no puede ser vacio")
    @Column(name = "description", nullable = false)
    private String description;
    private Date startDate;
    private Date endDate;
    private String state;
    // private any backlog;

}
