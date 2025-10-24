package br.com.fiap3esa.spring_boot_project.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GerarHashSenha {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        System.out.println("=== Gerando Hashes de Senha ===");
        System.out.println();
        
        // Hash para senha "admin123"
        String hashAdmin123 = encoder.encode("admin123");
        System.out.println("Senha: admin123");
        System.out.println("Hash: " + hashAdmin123);
        System.out.println();
        
        // Hash para senha "admin"
        String hashAdmin = encoder.encode("admin");
        System.out.println("Senha: admin");
        System.out.println("Hash: " + hashAdmin);
        System.out.println();
        
        // Verificar se o hash existente funciona
        String hashExistente = "$2a$10$Y50UaMFOxteibQEYLrwuAu.zk3xTEb2nL7QiTiMCnvz3hFN18XQFC";
        boolean matchesAdmin123 = encoder.matches("admin123", hashExistente);
        boolean matchesAdmin = encoder.matches("admin", hashExistente);
        
        System.out.println("=== Testando Hash Existente ===");
        System.out.println("Hash: " + hashExistente);
        System.out.println("Corresponde a 'admin123'? " + matchesAdmin123);
        System.out.println("Corresponde a 'admin'? " + matchesAdmin);
    }
}