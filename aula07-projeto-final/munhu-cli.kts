println("╔════════════════════════════════════════╗")
println("║                                        ║")
println("║          🇲🇿 MUNHU V1.0 🇲🇿            ║")
println("║    Rede Social Moçambicana - CLI       ║")
println("║                                        ║")
println("║      Desenvolvido por Francisco        ║")
println("║           THE FRA LABS                 ║")
println("║                                        ║")
println("╚════════════════════════════════════════╝")

// ═══════════════════════════════════════════════════════
//  DATA CLASSES - ESTRUTURAS DE DADOS
// ═══════════════════════════════════════════════════════

data class Usuario(
    val id: String,
    var username: String,
    var nome: String,
    var email: String,
    var senha: String,
    var bio: String = "",
    var seguidores: Int = 0,
    var seguindo: Int = 0,
    var verificado: Boolean = false,
    val dataCriacao: Long = System.currentTimeMillis()
)

data class Post(
    val id: String,
    val autorId: String,
    var conteudo: String,
    var likes: Int = 0,
    var comentarios: MutableList<Comentario> = mutableListOf(),
    var compartilhamentos: Int = 0,
    val timestamp: Long = System.currentTimeMillis()
)

data class Comentario(
    val autorId: String,
    val conteudo: String,
    val timestamp: Long = System.currentTimeMillis()
)

// ═══════════════════════════════════════════════════════
//  BANCO DE DADOS (em memória)
// ═══════════════════════════════════════════════════════

val usuarios = mutableMapOf<String, Usuario>()
val posts = mutableListOf<Post>()
val seguindo = mutableMapOf<String, MutableSet<String>>() // userId -> Set de IDs seguidos
var usuarioLogado: Usuario? = null
var proximoUserId = 1
var proximoPostId = 1

// ═══════════════════════════════════════════════════════
//  FUNÇÕES DE VALIDAÇÃO
// ═══════════════════════════════════════════════════════

fun validarEmail(email: String): Boolean {
    return email.contains("@") && 
           email.contains(".") && 
           email.length >= 5
}

fun validarUsername(username: String): Boolean {
    return username.length in 3..15 &&
           username.all { it.isLetterOrDigit() || it == '_' } &&
           username[0].isLetter() &&
           usuarios.values.none { it.username.equals(username, ignoreCase = true) }
}

fun validarSenha(senha: String): Boolean {
    return senha.length >= 6
}

// ═══════════════════════════════════════════════════════
//  FUNÇÕES DE FORMATAÇÃO
// ═══════════════════════════════════════════════════════

fun formatarNumero(numero: Int): String {
    return when {
        numero >= 1_000_000 -> "${numero / 1_000_000}M"
        numero >= 1_000 -> "${numero / 1_000}K"
        else -> numero.toString()
    }
}

fun formatarTempo(timestamp: Long): String {
    val minutos = (System.currentTimeMillis() - timestamp) / 60000
    return when {
        minutos < 1 -> "agora"
        minutos < 60 -> "${minutos}min"
        minutos < 1440 -> "${minutos / 60}h"
        else -> "${minutos / 1440}d"
    }
}

// ═══════════════════════════════════════════════════════
//  FUNÇÕES DE AUTENTICAÇÃO
// ═══════════════════════════════════════════════════════

fun cadastrar() {
    println("\n╔════════════════════════════════════════╗")
    println("║            CADASTRO MUNHU              ║")
    println("╚════════════════════════════════════════╝")
    
    print("\n👤 Nome completo: ")
    val nome = readln().trim()
    
    if (nome.length < 3) {
        println("❌ Nome muito curto!")
        return
    }
    
    print("📧 Email: ")
    val email = readln().trim()
    
    if (!validarEmail(email)) {
        println("❌ Email inválido!")
        return
    }
    
    if (usuarios.values.any { it.email.equals(email, ignoreCase = true) }) {
        println("❌ Email já cadastrado!")
        return
    }
    
    print("🔑 Senha (mín 6 caracteres): ")
    val senha = readln()
    
    if (!validarSenha(senha)) {
        println("❌ Senha muito curta!")
        return
    }
    
    print("📱 Username: @")
    val username = readln().trim().lowercase()
    
    if (!validarUsername(username)) {
        println("❌ Username inválido ou já existe!")
        return
    }
    
    // Criar usuário
    val userId = "USER_${proximoUserId++}"
    val novoUsuario = Usuario(
        id = userId,
        username = username,
        nome = nome,
        email = email,
        senha = senha
    )
    
    usuarios[userId] = novoUsuario
    seguindo[userId] = mutableSetOf()
    
    println("\n✅ Cadastro realizado com sucesso!")
    println("🎉 Bem-vindo ao Munhu, @$username!")
}

fun login(): Boolean {
    println("\n╔════════════════════════════════════════╗")
    println("║              LOGIN MUNHU               ║")
    println("╚════════════════════════════════════════╝")
    
    print("\n📧 Email ou Username: ")
    val identificador = readln().trim().lowercase()
    
    print("🔑 Senha: ")
    val senha = readln()
    
    // Buscar usuário
    val usuario = usuarios.values.find { 
        (it.email.equals(identificador, ignoreCase = true) || 
         it.username.equals(identificador, ignoreCase = true)) &&
        it.senha == senha
    }
    
    if (usuario != null) {
        usuarioLogado = usuario
        println("\n✅ Login realizado!")
        println("👋 Bem-vindo de volta, @${usuario.username}!")
        return true
    } else {
        println("\n❌ Credenciais inválidas!")
        return false
    }
}

fun logout() {
    usuarioLogado = null
    println("\n👋 Logout realizado com sucesso!")
}

// ═══════════════════════════════════════════════════════
//  FUNÇÕES DE POSTS
// ═══════════════════════════════════════════════════════

fun criarPost() {
    val usuario = usuarioLogado ?: return
    
    println("\n╔════════════════════════════════════════╗")
    println("║             CRIAR POST                 ║")
    println("╚════════════════════════════════════════╝")
    
    print("\n✍️ Escreva seu post (máx 280 caracteres):\n> ")
    val conteudo = readln().trim()
    
    if (conteudo.isEmpty()) {
        println("❌ Post não pode estar vazio!")
        return
    }
    
    if (conteudo.length > 280) {
        println("❌ Post muito longo! (${conteudo.length}/280)")
        return
    }
    
    val postId = "POST_${proximoPostId++}"
    val novoPost = Post(
        id = postId,
        autorId = usuario.id,
        conteudo = conteudo
    )
    
    posts.add(0, novoPost) // Adiciona no início
    
    println("\n✅ Post publicado com sucesso!")
}

fun exibirPost(post: Post, mostrarOpcoes: Boolean = false) {
    val autor = usuarios[post.autorId] ?: return
    
    println("\n┌────────────────────────────────────────")
    println("│ @${autor.username} ${if (autor.verificado) "✓" else ""} · ${formatarTempo(post.timestamp)}")
    println("│")
    println("│ ${post.conteudo}")
    println("│")
    println("│ ❤️ ${formatarNumero(post.likes)}  💬 ${post.comentarios.size}  🔄 ${formatarNumero(post.compartilhamentos)}")
    
    if (mostrarOpcoes && usuarioLogado != null) {
        println("│")
        println("│ [1] Curtir  [2] Comentar  [3] Compartilhar  [0] Voltar")
    }
    
    println("└────────────────────────────────────────")
}

fun verFeed() {
    println("\n╔════════════════════════════════════════╗")
    println("║                 FEED                   ║")
    println("╚════════════════════════════════════════╝")
    
    if (posts.isEmpty()) {
        println("\n📭 Nenhum post ainda!")
        println("💡 Seja o primeiro a postar!")
        return
    }
    
    val postsExibir = posts.take(10)
    
    for ((index, post) in postsExibir.withIndex()) {
        exibirPost(post)
        
        if (index < postsExibir.size - 1) {
            println()
        }
    }
    
    if (posts.size > 10) {
        println("\n... e mais ${posts.size - 10} posts")
    }
}

fun interagirComPost() {
    println("\n╔════════════════════════════════════════╗")
    println("║          INTERAGIR COM POST            ║")
    println("╚════════════════════════════════════════╝")
    
    if (posts.isEmpty()) {
        println("\n📭 Nenhum post disponível!")
        return
    }
    
    println("\nPosts recentes:")
    posts.take(5).forEachIndexed { index, post ->
        val autor = usuarios[post.autorId]!!
        println("${index + 1}. @${autor.username}: ${post.conteudo.take(40)}...")
    }
    
    print("\nEscolha um post (1-${minOf(5, posts.size)}): ")
    val escolha = readln().toIntOrNull()
    
    if (escolha == null || escolha !in 1..minOf(5, posts.size)) {
        println("❌ Opção inválida!")
        return
    }
    
    val post = posts[escolha - 1]
    exibirPost(post, mostrarOpcoes = true)
    
    print("\nEscolha uma ação: ")
    when (readln()) {
        "1" -> {
            post.likes++
            println("❤️ Post curtido!")
        }
        "2" -> {
            print("💬 Seu comentário: ")
            val comentarioTexto = readln().trim()
            if (comentarioTexto.isNotEmpty()) {
                val comentario = Comentario(
                    autorId = usuarioLogado!!.id,
                    conteudo = comentarioTexto
                )
                post.comentarios.add(comentario)
                println("✅ Comentário adicionado!")
            }
        }
        "3" -> {
            post.compartilhamentos++
            println("🔄 Post compartilhado!")
        }
    }
}

// ═══════════════════════════════════════════════════════
//  FUNÇÕES DE PERFIL
// ═══════════════════════════════════════════════════════

fun verPerfil(usuario: Usuario) {
    println("\n╔════════════════════════════════════════╗")
    println("║                PERFIL                  ║")
    println("╚════════════════════════════════════════╝")
    
    val meuPerfil = usuario.id == usuarioLogado?.id
    
    println("\n@${usuario.username} ${if (usuario.verificado) "✓" else ""}")
    println("${usuario.nome}")
    if (usuario.bio.isNotEmpty()) {
        println("\"${usuario.bio}\"")
    }
    println()
    println("👥 ${formatarNumero(usuario.seguidores)} seguidores · ${formatarNumero(usuario.seguindo)} seguindo")
    
    // Posts do usuário
    val postsdoUsuario = posts.filter { it.autorId == usuario.id }
    println("\n📝 ${postsdoUsuario.size} posts")
    
    if (postsdoUsuario.isNotEmpty()) {
        println("\nPosts recentes:")
        postsdoUsuario.take(3).forEach { post ->
            println("\n  \"${post.conteudo}\"")
            println("  ❤️ ${post.likes} 💬 ${post.comentarios.size}")
        }
    }
    
    // Opções
    if (!meuPerfil && usuarioLogado != null) {
        val estaSeguindo = seguindo[usuarioLogado!!.id]?.contains(usuario.id) == true
        println("\n${if (estaSeguindo) "[1] Deixar de seguir" else "[1] Seguir"}")
    }
    
    if (meuPerfil) {
        println("\n[1] Editar perfil")
    }
}

fun editarPerfil() {
    val usuario = usuarioLogado ?: return
    
    println("\n╔════════════════════════════════════════╗")
    println("║            EDITAR PERFIL               ║")
    println("╚════════════════════════════════════════╝")
    
    println("\nDeixe em branco para manter o valor atual")
    
    print("\n👤 Nome (${usuario.nome}): ")
    val novoNome = readln().trim()
    if (novoNome.isNotEmpty()) {
        usuario.nome = novoNome
    }
    
    print("📝 Bio (${if (usuario.bio.isEmpty()) "vazio" else usuario.bio}): ")
    val novaBio = readln().trim()
    if (novaBio.isNotEmpty() || readln() == "") {
        usuario.bio = novaBio
    }
    
    println("\n✅ Perfil atualizado!")
}

fun buscarUsuarios() {
    println("\n╔════════════════════════════════════════╗")
    println("║            BUSCAR USUÁRIOS             ║")
    println("╚════════════════════════════════════════╝")
    
    print("\n🔍 Buscar por: ")
    val query = readln().trim().lowercase()
    
    if (query.isEmpty()) {
        println("❌ Digite algo para buscar!")
        return
    }
    
    val resultados = usuarios.values.filter { usuario ->
        usuario.username.contains(query, ignoreCase = true) ||
        usuario.nome.contains(query, ignoreCase = true)
    }
    
    if (resultados.isEmpty()) {
        println("\n📭 Nenhum usuário encontrado!")
        return
    }
    
    println("\n📊 ${resultados.size} usuário(s) encontrado(s):")
    resultados.forEachIndexed { index, usuario ->
        println("${index + 1}. @${usuario.username} ${if (usuario.verificado) "✓" else ""}")
        println("   ${usuario.nome} · ${formatarNumero(usuario.seguidores)} seguidores")
    }
    
    print("\nVer perfil de (1-${resultados.size}) ou 0 para voltar: ")
    val escolha = readln().toIntOrNull()
    
    if (escolha != null && escolha in 1..resultados.size) {
        verPerfil(resultados[escolha - 1])
    }
}

fun seguirUsuario(alvo: Usuario) {
    val usuario = usuarioLogado ?: return
    
    if (usuario.id == alvo.id) {
        println("❌ Você não pode seguir a si mesmo!")
        return
    }
    
    val meusSeguidores = seguindo[usuario.id]!!
    
    if (meusSeguidores.contains(alvo.id)) {
        // Deixar de seguir
        meusSeguidores.remove(alvo.id)
        usuario.seguindo--
        alvo.seguidores--
        println("✅ Você deixou de seguir @${alvo.username}")
    } else {
        // Seguir
        meusSeguidores.add(alvo.id)
        usuario.seguindo++
        alvo.seguidores++
        println("✅ Você agora segue @${alvo.username}")
    }
}

// ═══════════════════════════════════════════════════════
//  FUNÇÕES DE ESTATÍSTICAS
// ═══════════════════════════════════════════════════════

fun exibirEstatisticas() {
    println("\n╔════════════════════════════════════════╗")
    println("║            ESTATÍSTICAS                ║")
    println("╚════════════════════════════════════════╝")
    
    println("\n📊 GERAL:")
    println("   Usuários cadastrados: ${usuarios.size}")
    println("   Total de posts: ${posts.size}")
    println("   Total de likes: ${posts.sumOf { it.likes }}")
    println("   Total de comentários: ${posts.sumOf { it.comentarios.size }}")
    
    if (posts.isNotEmpty()) {
        val postMaisCurtido = posts.maxByOrNull { it.likes }!!
        val autor = usuarios[postMaisCurtido.autorId]!!
        
        println("\n🔥 POST MAIS CURTIDO:")
        println("   @${autor.username}: \"${postMaisCurtido.conteudo.take(50)}...\"")
        println("   ❤️ ${postMaisCurtido.likes} likes")
    }
    
    if (usuarios.isNotEmpty()) {
        val usuarioMaisSeguidores = usuarios.values.maxByOrNull { it.seguidores }!!
        
        println("\n👑 USUÁRIO MAIS SEGUIDO:")
        println("   @${usuarioMaisSeguidores.username}")
        println("   👥 ${formatarNumero(usuarioMaisSeguidores.seguidores)} seguidores")
    }
}

// ═══════════════════════════════════════════════════════
//  DADOS INICIAIS (DEMO)
// ═══════════════════════════════════════════════════════

fun carregarDadosDemo() {
    // Criar usuários demo
    val demo1 = Usuario(
        id = "USER_DEMO1",
        username = "munhu_oficial",
        nome = "Munhu Oficial",
        email = "oficial@munhu.co.mz",
        senha = "demo123",
        bio = "Rede Social Moçambicana 🇲🇿",
        seguidores = 5000,
        verificado = true
    )
    
    val demo2 = Usuario(
        id = "USER_DEMO2",
        username = "ana_maputo",
        nome = "Ana Silva",
        email = "ana@munhu.co.mz",
        senha = "demo123",
        bio = "Desenvolvedora | Maputo",
        seguidores = 850
    )
    
    usuarios[demo1.id] = demo1
    usuarios[demo2.id] = demo2
    seguindo[demo1.id] = mutableSetOf()
    seguindo[demo2.id] = mutableSetOf(demo1.id)
    
    // Criar posts demo
    posts.add(Post(
        id = "POST_DEMO1",
        autorId = demo1.id,
        conteudo = "Bem-vindos ao Munhu! A primeira rede social moçambicana! 🇲🇿🎉",
        likes = 342,
        compartilhamentos = 89
    ))
    
    posts.add(Post(
        id = "POST_DEMO2",
        autorId = demo2.id,
        conteudo = "Adorando o Munhu! Finalmente uma rede social nossa! #MozTech",
        likes = 156
    ))
    
    proximoUserId = 3
    proximoPostId = 3
}

// ═══════════════════════════════════════════════════════
//  MENUS
// ═══════════════════════════════════════════════════════

fun menuPrincipal() {
    while (true) {
        println("\n╔════════════════════════════════════════╗")
        println("║            MUNHU - INÍCIO              ║")
        println("╠════════════════════════════════════════╣")
        println("║  1. Login                              ║")
        println("║  2. Cadastrar                          ║")
        println("║  3. Ver Feed Público                   ║")
        println("║  4. Estatísticas                       ║")
        println("║  5. Sair                               ║")
        println("╚════════════════════════════════════════╝")
        
        print("\nEscolha uma opção: ")
        
        when (readln()) {
            "1" -> {
                if (login()) {
                    menuLogado()
                }
            }
            "2" -> cadastrar()
            "3" -> verFeed()
            "4" -> exibirEstatisticas()
            "5" -> {
                println("\n👋 Até logo! Volte sempre ao Munhu!")
                return
            }
            else -> println("❌ Opção inválida!")
        }
    }
}

fun menuLogado() {
    while (true) {
        val usuario = usuarioLogado ?: return
        
        println("\n╔════════════════════════════════════════╗")
        println("║         MUNHU - @${usuario.username.padEnd(22)}║")
        println("╠════════════════════════════════════════╣")
        println("║  1. Ver Feed                           ║")
        println("║  2. Criar Post                         ║")
        println("║  3. Interagir com Post                 ║")
        println("║  4. Meu Perfil                         ║")
        println("║  5. Buscar Usuários                    ║")
        println("║  6. Estatísticas                       ║")
        println("║  7. Logout                             ║")
        println("╚════════════════════════════════════════╝")
        
        print("\nEscolha uma opção: ")
        
        when (readln()) {
            "1" -> verFeed()
            "2" -> criarPost()
            "3" -> interagirComPost()
            "4" -> {
                verPerfil(usuario)
                print("\nDeseja editar? (s/n): ")
                if (readln().lowercase() == "s") {
                    editarPerfil()
                }
            }
            "5" -> buscarUsuarios()
            "6" -> exibirEstatisticas()
            "7" -> {
                logout()
                return
            }
            else -> println("❌ Opção inválida!")
        }
    }
}

// ═══════════════════════════════════════════════════════
//  MAIN - INÍCIO DO PROGRAMA
// ═══════════════════════════════════════════════════════

// Carregar dados demo
carregarDadosDemo()

// Mensagem de boas-vindas
println("\n🚀 Sistema inicializado com sucesso!")
println("📊 ${usuarios.size} usuários · ${posts.size} posts")
println("\n💡 DICA: Use as credenciais demo para testar:")
println("   Email: oficial@munhu.co.mz")
println("   Senha: demo123")

// Iniciar menu principal
menuPrincipal()
