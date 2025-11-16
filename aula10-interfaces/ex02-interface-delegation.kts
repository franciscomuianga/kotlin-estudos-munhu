println("═══════════════════════════════════════")
println("    MUNHU - INTERFACE DELEGATION")
println("═══════════════════════════════════════")

// ========== DELEGATION BÁSICO ==========

interface Logger {
    fun log(mensagem: String)
    fun erro(mensagem: String)
    fun info(mensagem: String)
}

class ConsoleLogger : Logger {
    override fun log(mensagem: String) {
        println("[LOG] $mensagem")
    }
    
    override fun erro(mensagem: String) {
        println("[ERRO] ❌ $mensagem")
    }
    
    override fun info(mensagem: String) {
        println("[INFO] ℹ️ $mensagem")
    }
}

// Delegation: "by logger" delega TODAS as chamadas para logger
class SistemaAutenticacao(logger: Logger) : Logger by logger {
    private val usuarios = mutableMapOf<String, String>()
    
    fun cadastrar(username: String, senha: String) {
        log("Tentando cadastrar: $username")
        
        if (username in usuarios) {
            erro("Username já existe: $username")
            return
        }
        
        usuarios[username] = senha
        info("Usuário cadastrado com sucesso: $username")
    }
    
    fun login(username: String, senha: String): Boolean {
        log("Tentativa de login: $username")
        
        if (username !in usuarios) {
            erro("Usuário não encontrado: $username")
            return false
        }
        
        if (usuarios[username] != senha) {
            erro("Senha incorreta para: $username")
            return false
        }
        
        info("Login bem-sucedido: $username")
        return true
    }
}

println("\n=== DELEGATION BÁSICO ===")

val logger = ConsoleLogger()
val auth = SistemaAutenticacao(logger)

auth.cadastrar("francisco_raul", "senha123")
auth.cadastrar("ana_silva", "senha456")
auth.cadastrar("francisco_raul", "outrasenha")  // Falha

println()
auth.login("francisco_raul", "senha_errada")  // Falha
auth.login("francisco_raul", "senha123")  // Sucesso
auth.login("carlos", "senha")  // Falha

// ========== DELEGATION COM OVERRIDE ==========

interface Notificador {
    fun enviar(destinatario: String, mensagem: String)
    fun enviarEmMassa(destinatarios: List<String>, mensagem: String)
}

class NotificadorEmail : Notificador {
    override fun enviar(destinatario: String, mensagem: String) {
        println("📧 Email para $destinatario: $mensagem")
    }
    
    override fun enviarEmMassa(destinatarios: List<String>, mensagem: String) {
        println("📧 Enviando email em massa para ${destinatarios.size} pessoas...")
        destinatarios.forEach { enviar(it, mensagem) }
    }
}

class SistemaMunhu(notificador: Notificador) : Notificador by notificador {
    private var notificacoesEnviadas = 0
    
    // Sobrescreve método delegado para adicionar funcionalidade
    override fun enviar(destinatario: String, mensagem: String) {
        // Chama implementação original via notificador
        notificador.enviar(destinatario, mensagem)
        notificacoesEnviadas++
    }
    
    fun estatisticas() {
        println("\n📊 Total de notificações enviadas: $notificacoesEnviadas")
    }
    
    fun notificarNovoCurtida(usuario: String, autor: String) {
        enviar(usuario, "@$autor curtiu seu post")
    }
    
    fun notificarNovoSeguidor(usuario: String, seguidor: String) {
        enviar(usuario, "@$seguidor começou a te seguir")
    }
}

println("\n=== DELEGATION COM OVERRIDE ===")

val emailNotif = NotificadorEmail()
val munhu = SistemaMunhu(emailNotif)

munhu.notificarNovoCurtida("francisco_raul", "ana_silva")
munhu.notificarNovoSeguidor("francisco_raul", "carlos_dev")
munhu.notificarNovoCurtida("francisco_raul", "beatriz")

val usuarios = listOf("user1", "user2", "user3")
munhu.enviarEmMassa(usuarios, "Nova atualização disponível!")

munhu.estatisticas()

// ========== DELEGATION MÚLTIPLO ==========

interface Armazenavel {
    fun salvar()
    fun carregar()
}

interface Sincronizavel {
    fun sincronizar()
    fun ultimaSinc(): Long
}

class ArmazenamentoLocal : Armazenavel {
    override fun salvar() {
        println("💾 Salvando localmente...")
    }
    
    override fun carregar() {
        println("📂 Carregando do armazenamento local...")
    }
}

class SincronizacaoNuvem : Sincronizavel {
    private var ultimaSincronizacao = 0L
    
    override fun sincronizar() {
        println("☁️ Sincronizando com nuvem...")
        ultimaSincronizacao = System.currentTimeMillis()
    }
    
    override fun ultimaSinc() = ultimaSincronizacao
}

class GerenciadorDados(
    armazenamento: Armazenavel,
    sincronizacao: Sincronizavel
) : Armazenavel by armazenamento, Sincronizavel by sincronizacao {
    
    fun backupCompleto() {
        println("\n🔄 Iniciando backup completo...")
        salvar()
        sincronizar()
        println("✅ Backup concluído!")
        println("   Última sincronização: ${ultimaSinc()}")
    }
}

println("\n=== DELEGATION MÚLTIPLO ===")

val storage = ArmazenamentoLocal()
val sync = SincronizacaoNuvem()
val gerenciador = GerenciadorDados(storage, sync)

gerenciador.carregar()
gerenciador.salvar()
gerenciador.sincronizar()
Thread.sleep(100)
gerenciador.backupCompleto()

// ========== EXEMPLO PRÁTICO: CACHE ==========

interface Cache<T> {
    fun colocar(chave: String, valor: T)
    fun obter(chave: String): T?
    fun limpar()
    fun tamanho(): Int
}

class CacheMemoria<T> : Cache<T> {
    private val dados = mutableMapOf<String, T>()
    
    override fun colocar(chave: String, valor: T) {
        dados[chave] = valor
        println("💾 Cache: '$chave' armazenado")
    }
    
    override fun obter(chave: String): T? {
        val valor = dados[chave]
        if (valor != null) {
            println("✅ Cache HIT: '$chave'")
        } else {
            println("❌ Cache MISS: '$chave'")
        }
        return valor
    }
    
    override fun limpar() {
        dados.clear()
        println("🗑️ Cache limpo")
    }
    
    override fun tamanho() = dados.size
}

class RepositorioUsuarios(
    cache: Cache<String>
) : Cache<String> by cache {
    
    fun buscarUsuario(id: String): String {
        // Tenta cache primeiro
        val cached = obter("user_$id")
        if (cached != null) {
            return cached
        }
        
        // Simula busca no banco
        println("🔍 Buscando usuário $id no banco de dados...")
        Thread.sleep(50)
        val usuario = "Usuario_$id"
        
        // Armazena no cache
        colocar("user_$id", usuario)
        
        return usuario
    }
    
    fun estatisticas() {
        println("\n📊 Cache stats:")
        println("   Itens no cache: ${tamanho()}")
    }
}

println("\n=== DELEGATION PRÁTICO: CACHE ===")

val cache = CacheMemoria<String>()
val repo = RepositorioUsuarios(cache)

println("\nPrimeira busca (deve ir ao banco):")
repo.buscarUsuario("123")

println("\nSegunda busca (deve usar cache):")
repo.buscarUsuario("123")

println("\nOutros usuários:")
repo.buscarUsuario("456")
repo.buscarUsuario("789")

repo.estatisticas()

println("\nLimpando cache:")
repo.limpar()

println("\nBusca após limpar:")
repo.buscarUsuario("123")
