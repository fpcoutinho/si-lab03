package models;

import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.util.List;
import javax.persistence.*;
import javax.persistence.GeneratedValue;

@Entity
public abstract class Recommend {
        @Id
        @GeneratedValue
        private Long id;

        public abstract Episodio rec(List<Temporada> temporadas);

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        }