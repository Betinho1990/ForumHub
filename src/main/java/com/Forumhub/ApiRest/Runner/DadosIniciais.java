package com.Forumhub.ApiRest.Runner;

import com.Forumhub.ApiRest.model.Usuario;
import com.Forumhub.ApiRest.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DadosIniciais implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Value("${APP_ADMIN_LOGIN}")
    private String adminLogin;

    @Value("${APP_ADMIN_PASSWORD}")
    private String adminPassword;

    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.findByLogin(adminLogin).isEmpty()) {
            Usuario admin = new Usuario();
            admin.setLogin(adminLogin);
            admin.setSenha(passwordEncoder.encode(adminPassword));

            usuarioRepository.save(admin);
            System.out.println("Usuário '" + adminLogin + "' criado com senha criptografada!");
        } else {
            System.out.println("Usuário '" + adminLogin + "' já existe.");
        }
    }
}
