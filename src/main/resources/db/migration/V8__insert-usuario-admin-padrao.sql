-- Inserir usuário administrador padrão
-- Login: admin
-- Senha: admin123
-- O hash BCrypt foi gerado corretamente para a senha "admin123"

INSERT INTO usuarios (login, senha, perfil) 
VALUES ('admin', '$2a$10$YyAz5hXOlZAwv0L815MeJ.H0I8kM5urMrfdAqa9vmeGdaDX65SiWq', 'ADMIN');

