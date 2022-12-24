package uzum.trelloapp.entity;

import lombok.*;
import org.hibernate.annotations.Type;
import uzum.trelloapp.base.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "task_check_list_item")
public class TaskCheckListItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_check_list_id", referencedColumnName = "id", insertable = false, updatable = false)
    private TaskCheckList taskCheckList;
    @Column(name = "task_check_list_id")
    private Long taskCheckListId;

    @Column(name = "is_finished", columnDefinition = "NUMERIC default 0")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    protected boolean isFinished = false;

}
