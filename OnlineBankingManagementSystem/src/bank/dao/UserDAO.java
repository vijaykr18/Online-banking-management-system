package bank.dao;

import bank.model.User;
import bank.dao.InMemoryStorage;

public class UserDAO {

    public User authenticateUser(String username, String password) {
        if (username == null || password == null) {
            return null;
        }

        // Use the InMemoryStorage to authenticate
        if (InMemoryStorage.authenticateUser(username, password)) {
            return InMemoryStorage.getUser(username);
        }
        return null;
    }
}