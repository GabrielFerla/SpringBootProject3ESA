-- ============================================
-- CRIAR USUÁRIO ADMIN MANUALMENTE
-- ============================================

USE projeto3esa;

-- 1. Limpar usuário admin existente (se houver)
DELETE FROM usuarios WHERE login = 'admin';

-- 2. Inserir usuário admin com hash BCrypt CORRETO
-- Login: admin
-- Senha: admin123
INSERT INTO usuarios (login, senha, perfil) 
VALUES ('admin', '$2a$10$N.zmdr9k7uOCQQVbvipoG.XHOtPnkAnxhvC3kDvZTBRRaW.cWlU62', 'ADMIN');

-- 3. Verificar se foi criado corretamente
SELECT id, login, perfil, 
       LEFT(senha, 20) as 'senha_inicio...',
       LENGTH(senha) as 'tamanho_senha'
FROM usuarios 
WHERE login = 'admin';

-- ============================================
-- INFORMAÇÕES IMPORTANTES:
-- ============================================
-- Hash BCrypt tem 60 caracteres
-- Formato: $2a$10$[22 chars salt][31 chars hash]
-- Este hash foi gerado para a senha: admin123
-- ============================================

