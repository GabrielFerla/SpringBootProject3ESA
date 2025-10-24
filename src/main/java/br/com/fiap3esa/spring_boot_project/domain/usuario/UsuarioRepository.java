package br.com.fiap3esa.spring_boot_project.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    UserDetails findByLogin(String login);
    
    boolean existsByLogin(String login);
    
    Usuario getReferenceByLogin(String login);
}