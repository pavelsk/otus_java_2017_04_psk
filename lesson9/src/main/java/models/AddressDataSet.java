package models;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
public class AddressDataSet extends DataSet {

    @Column(name = "`index`")
    private int index;

    @Column(name = "street")
    private String street;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserDataSet user;

    public AddressDataSet() {
    }

    public AddressDataSet(int index, String street) {
        this.index = index;
        this.street = street;
    }

    public AddressDataSet(int index, String street, UserDataSet user) {
        this.index = index;
        this.street = street;
        this.user = user;
    }

    public int getIndex() {
        return index;
    }

    public String getStreet() {
        return street;
    }

    public UserDataSet getUser() {
        return user;
    }

    public void setUser(UserDataSet user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "AddressDataSet{" +
                "index=" + index +
                ", street='" + street + '\'' +
                '}';
    }
}
