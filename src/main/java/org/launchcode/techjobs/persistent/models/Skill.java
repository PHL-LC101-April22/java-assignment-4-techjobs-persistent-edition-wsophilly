package org.launchcode.techjobs.persistent.models;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Skill extends AbstractEntity {

        @ManyToMany(mappedBy = "skills")
        private List<Skill> jobs = new ArrayList<>();

        @Size(min=3, max=75)
        private String skills;

        @Size(min=3, max=75)
        private String description;

        public Skill() {
        }

        public List<Skill> getJobs() {
                return jobs;
        }

        public void setJobs(List<Skill> jobs) {
                this.jobs = jobs;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }
}