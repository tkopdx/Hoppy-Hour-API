package beer.hoppyhour.api.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import beer.hoppyhour.api.model.UserScheduleEvent;

@Entity
@Table(name = "scheduling")
public class Scheduling extends UserScheduleEvent {
    public Scheduling() {}
}
