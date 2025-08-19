package com.Forumhub.ApiRest.Controller;

import com.Forumhub.ApiRest.Dto.DadosAutenticacao;
import com.Forumhub.ApiRest.Service.TokenService;
import com.Forumhub.ApiRest.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<String> autenticar(@RequestBody DadosAutenticacao dados) {
        var usuarioOptional = usuarioRepository.findByLogin(dados.login());
        if (usuarioOptional.isEmpty() || !passwordEncoder.matches(dados.senha(), usuarioOptional.get().getSenha())) {
            return ResponseEntity.status(401).body("Login ou senha inválidos");
        }

        String token = tokenService.gerarToken(usuarioOptional.get());
        return ResponseEntity.ok(token);
    }
}
