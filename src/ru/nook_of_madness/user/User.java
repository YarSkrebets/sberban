package ru.nook_of_madness.user;

public class User {
    private String name;
    private String secondName;

    private String passportData;
    private String address;

    public User(String name, String secondName) {
        this.name = name;
        this.secondName = secondName;
        if ((name == null) || (secondName == null)) {
            throw new IllegalArgumentException("Имя или фамилия не указаны.");
        }
    }

    public User(String name, String secondName, String passportData, String address) {
        this(name, secondName);
        this.passportData = passportData;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getPassportData() {
        return passportData;
    }

    public String getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setPassportData(String passportData) {
        this.passportData = passportData;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isTrusted() {
        return passportData != null && address != null;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", secondName='" + secondName + '\'' +
                ", passportData='" + passportData + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public static Builder builder(String name, String secondName) {
        return new Builder(name, secondName);
    }

    public static class Builder {
        private String name;
        private String secondName;
        private String passportData;
        private String address;

        public Builder(String name, String secondName) {
            this.name = name;
            this.secondName = secondName;
        }

        public Builder passportData(String passportData) {
            this.passportData = passportData;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public User build() {
            return new User(name, secondName, passportData, address);
        }
    }
}
