println("═══════════════════════════════════════")
println("    MUNHU - FOR LOOPS BÁSICOS")
println("═══════════════════════════════════════")

// ========== FOR COM RANGE (1..10) ==========

println("\n=== CONTAGEM DE 1 ATÉ 10 ===")
for (i in 1..10) {
    println("Número: $i")
}

// ========== FOR COM UNTIL (não inclui o último) ==========

println("\n=== CONTAGEM DE 0 ATÉ 9 (until) ===")
for (i in 0 until 10) {
    println("Índice: $i")
}

// ========== FOR COM STEP (pulos) ==========

println("\n=== NÚMEROS PARES (0 a 20) ===")
for (i in 0..20 step 2) {
    println("Par: $i")
}

println("\n=== NÚMEROS ÍMPARES (1 a 19) ===")
for (i in 1..20 step 2) {
    println("Ímpar: $i")
}

// ========== FOR DECRESCENTE (downTo) ==========

println("\n=== CONTAGEM REGRESSIVA ===")
for (i in 10 downTo 1) {
    println("$i...")
}
println("🚀 LANÇAMENTO!")

// ========== FOR COM CARACTERES ==========

println("\n=== ALFABETO (A-J) ===")
for (letra in 'A'..'J') {
    println("Letra: $letra")
}

// ========== APLICAÇÃO PRÁTICA: Notificações ==========

println("\n=== 📬 MUNHU - ENVIANDO NOTIFICAÇÕES ===")

val totalUsuarios = 5

for (usuario in 1..totalUsuarios) {
    println("Enviando notificação para usuário #$usuario...")
    println("   ✅ Notificação enviada!")
}

println("\n🎉 Total enviado: $totalUsuarios notificações")

// ========== APLICAÇÃO PRÁTICA: Gerar IDs ==========

println("\n=== 🆔 GERANDO IDs DE POSTS ===")

for (postId in 1001..1010) {
    println("Post criado com ID: MUNHU-$postId")
}

// ========== APLICAÇÃO PRÁTICA: Timeline ==========

println("\n=== 📅 ÚLTIMAS 7 DIAS ===")

for (dia in 7 downTo 1) {
    if (dia == 1) {
        println("Hoje")
    } else if (dia == 2) {
        println("Ontem")
    } else {
        println("Há $dia dias")
    }
}
