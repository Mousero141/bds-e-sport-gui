package esport.but.feec.esport.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import esport.but.feec.esport.data.PersonRepository;

public class PersonAuthService {

    private PersonRepository personRepository;

    public PersonAuthService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    /**public boolean authenticate(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return false;
        }
    }**/
}
