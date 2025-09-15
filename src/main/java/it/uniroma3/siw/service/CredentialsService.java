package it.uniroma3.siw.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.CredentialsRepository;

@Service
public class CredentialsService {

	@Autowired
    private CredentialsRepository credentialsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UtenteService userService;
    
    public Credentials getCredentials(String username) {
        return credentialsRepository.findByUsername(username).orElse(null);
    }
    
    public Credentials getCredentials(Long id) {
        return credentialsRepository.findById(id).orElse(null);
    }
    
    public Iterable<Credentials> getAllCredentials() {
        return credentialsRepository.findAll();
    }

    //utente default
    public Credentials saveCredentials(Credentials credentials) {
    	// Non impostare sempre DEFAULT_ROLE, mantieni il ruolo esistente
        if (credentials.getRole() == null) {
            credentials.setRole(Credentials.DEFAULT_ROLE);
        }
        
        // Codifica la password solo se non è già codificata
        if (credentials.getPassword() != null && !credentials.getPassword().startsWith("$2a$")) {
            credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
        }
        return credentialsRepository.save(credentials);
    }
    
    //metodo admin
    public void creaAdminCredentials() {
       //controllo se l'admin gia esiste
        Optional<Credentials> existingAdmin = credentialsRepository.findByUsername("admin");
        
        if (existingAdmin.isEmpty()) {
           //creo l'admin
            
            //prima salvo l'utente
            Utente adminUser = new Utente();
            adminUser.setNome("Admin");
            adminUser.setCognome("Administrator");
            adminUser.setEmail("admin@discoteca.it");
            Utente savedUser =userService.saveUser(adminUser);
            System.out.println("Utente admin salvato con ID: " + savedUser.getId());

            // poi creo le credenziali con l'utente salvato
            Credentials adminCredentials = new Credentials();
            adminCredentials.setUsername("admin");
            String rawPassword = "admin";
            String encodedPassword = passwordEncoder.encode(rawPassword);
            adminCredentials.setPassword(encodedPassword);
            adminCredentials.setRole(Credentials.ADMIN_ROLE);
            adminCredentials.setUtente(savedUser);
            
            credentialsRepository.save(adminCredentials);
            
        } 
    }

}
