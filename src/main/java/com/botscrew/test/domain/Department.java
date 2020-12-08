package com.botscrew.test.domain;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @OneToOne
    private Lector head;
    @ManyToMany
    @JoinTable(
            name = "departments_lectors",
            joinColumns = @JoinColumn(name = "department_id"),
            inverseJoinColumns = @JoinColumn(name = "lector_id"))
    private List<Lector> lectors;

    public Department() {
    }

    public Department(String name, Lector head, List<Lector> lectors) {
        this.name = name;
        this.head = head;
        this.lectors = lectors;
    }

    public Department(int id, String name, Lector head, List<Lector> lectors) {
        this(name, head, lectors);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Lector getHead() {
        return head;
    }

    public void setHead(Lector head) {
        this.head = head;
    }

    public List<Lector> getLectors() {
        return lectors;
    }

    public void setLectors(List<Lector> lectors) {
        this.lectors = lectors;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;
        Department that = (Department) o;
        return id == that.id &&
                name.equals(that.name) &&
                head.equals(that.head) &&
                lectors.equals(that.lectors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, head, lectors);
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", head=" + head +
                ", lectors=" + lectors +
                '}';
    }
}
