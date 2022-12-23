package uzum.trelloapp.entity;

import lombok.*;
import uzum.trelloapp.base.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "project_columns")
public class ProjectColumn extends BaseEntity {

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Project project;
    @Column(name = "project_id")
    private Integer projectId;

    @Column(name = "position")
    private Integer position;

}
