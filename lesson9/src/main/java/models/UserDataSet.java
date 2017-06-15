package models;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class UserDataSet extends DataSet {

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @Fetch(FetchMode.JOIN)
    private Set<PhoneDataSet> phone = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    @Fetch(FetchMode.JOIN)
    private AddressDataSet address;

    //Important for Hibernate
    public UserDataSet() {
    }

    public UserDataSet(long id, String name) {
        this.setId(id);
        this.setName(name);
    }

    public UserDataSet(String name) {
        this.setId(-1);
        this.setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addPhone(PhoneDataSet phone) {
        this.phone.add(phone);
    }

    public void setPhone(Set<PhoneDataSet> phone) {
        this.phone = phone;
    }

    public Set<PhoneDataSet> getPhone() {
        return phone;
    }

    public AddressDataSet getAddress() {
        return address;
    }

    public void setAddress(AddressDataSet address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "UserDataSet{" +
                "name='" + name + '\'' +
                ", phone=" + phone +
                ", address=" + address +
                '}';
    }
}

