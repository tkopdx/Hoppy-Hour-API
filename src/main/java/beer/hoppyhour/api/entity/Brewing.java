package beer.hoppyhour.api.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import beer.hoppyhour.api.model.UserScheduleEvent;

@Entity
@Table(name = "brewing")
public class Brewing extends UserScheduleEvent {
    public Brewing() {}
}
