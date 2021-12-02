package esport.but.feec.esport.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import esport.but.feec.esport.api.PersonAuthView;
import esport.but.feec.esport.data.PersonRepository;
import esport.but.feec.esport.exceptions.ResourceNotFoundException;

public class PersonAuthService {

    private PersonRepository personRepository;

    public PersonAuthService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    private PersonAuthView findPersonByEmail(String email) {
        return personRepository.findPersonByEmail(email);
    }

    public boolean authenticate(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return false;
        }
        PersonAuthView personAuthView = findPersonByEmail(username);
        if (personAuthView == null) {
            throw new ResourceNotFoundException("Provided username is not found.");
        }

        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), personAuthView.getPassword());
        return result.verified;
    }
}

