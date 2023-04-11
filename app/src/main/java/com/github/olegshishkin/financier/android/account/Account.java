package com.github.olegshishkin.financier.android.account;

import androidx.annotation.NonNull;
import java.util.Objects;

public class Account {

    public Account(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return "Account{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account account = (Account) o;
        return name.equals(account.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
