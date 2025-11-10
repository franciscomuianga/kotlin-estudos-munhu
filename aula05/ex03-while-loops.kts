println("═══════════════════════════════════════")
println("    MUNHU - WHILE LOOPS")
println("═══════════════════════════════════════")

// ========== WHILE BÁSICO ==========

println("\n=== CONTAGEM COM WHILE ===")

var contador = 1

while (contador <= 5) {
    println("Contador: $contador")
    contador++
}

// ========== WHILE COM CONDIÇÃO COMPLEXA ==========

println("\n=== 📈 SIMULAÇÃO DE CRESCIMENTO ===")

var seguidores = 100
var dias = 0

println("Meta: 1000 seguidores")
println("Início: $seguidores seguidores\n")

while (seguidores < 1000) {
    dias++
    val crescimento = (10..50).random()  // Cresce aleatoriamente
    seguidores += crescimento
    
    println("Dia $dias: +$crescimento seguidores (Total: $seguidores)")
}

println("\n🎉 Meta alcançada em $dias dias!")

// ========== DO-WHILE (executa pelo menos 1 vez) ==========

println("\n=== 🎮 MENU DO MUNHU ===")

var opcao: String

do {
    println("\n╔════════════════════════╗")
    println("║    MENU PRINCIPAL      ║")
    println("╠════════════════════════╣")
    println("║ 1. Ver Feed            ║")
    println("║ 2. Notificações        ║")
    println("║ 3. Perfil              ║")
    println("║ 4. Configurações       ║")
    println("║ 5. Sair                ║")
    println("╚════════════════════════╝")
    
    print("\nEscolha uma opção: ")
    opcao = readln()
    
    when (opcao) {
        "1" -> println("\n📱 Carregando feed...")
        "2" -> println("\n🔔 Você tem 3 novas notificações!")
        "3" -> println("\n👤 Abrindo seu perfil...")
        "4" -> println("\n⚙️ Configurações do app")
        "5" -> println("\n👋 Até logo!")
        else -> println("\n❌ Opção inválida!")
    }
    
} while (opcao != "5")

// ========== WHILE COM VALIDAÇÃO ==========

println("\n=== ✅ VALIDAÇÃO DE SENHA ===")

var senhaCorreta = false
var tentativas = 0
val senhaReal = "munhu2025"

while (!senhaCorreta && tentativas < 3) {
    tentativas++
    
    print("\nTentativa $tentativas/3 - Digite a senha: ")
    val senhaDigitada = readln()
    
    if (senhaDigitada == senhaReal) {
        senhaCorreta = true
        println("✅ Senha correta! Acesso liberado.")
    } else {
        val restantes = 3 - tentativas
        if (restantes > 0) {
            println("❌ Senha incorreta. $restantes tentativa(s) restante(s).")
        } else {
            println("🔒 Conta bloqueada temporariamente!")
        }
    }
}

// ========== APLICAÇÃO: CARREGAMENTO DE DADOS ==========

println("\n=== 📥 CARREGANDO POSTS... ===")

var postsCarregados = 0
val totalPosts = 20

while (postsCarregados < totalPosts) {
    postsCarregados += 5
    
    val porcentagem = (postsCarregados.toDouble() / totalPosts * 100).toInt()
    val barraCarregamento = "█".repeat(porcentagem / 5) + "░".repeat(20 - porcentagem / 5)
    
    print("\r[$barraCarregamento] $porcentagem%")
    
    // Simular delay (comentado pra não atrasar)
    // Thread.sleep(500)
}

println("\n\n✅ $totalPosts posts carregados com sucesso!")

// ========== APLICAÇÃO: SCROLL INFINITO ==========

println("\n=== 📜 SIMULAÇÃO DE SCROLL ===")

var posicaoScroll = 0
var scrolls = 0

println("Simulando 10 scrolls no feed...\n")

while (scrolls < 10) {
    scrolls++
    posicaoScroll += 100  // pixels
    
    println("Scroll $scrolls:")
    println("  Posição: ${posicaoScroll}px")
    println("  Carregando mais posts...")
    
    if (scrolls % 3 == 0) {
        println("  📢 [ANÚNCIO] Patrocinado")
    }
}

println("\n📊 Total scrollado: ${posicaoScroll}px")
