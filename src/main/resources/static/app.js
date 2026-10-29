const API = "";

const form = document.getElementById("formProfessor");
const lista = document.getElementById("listaProfessores");
const total = document.getElementById("total");
const totalDepartamentos = document.getElementById("totalDepartamentos");
const mensagem = document.getElementById("mensagem");
const selectDepartamento = document.getElementById("departamento");
const statusLista = document.getElementById("statusLista");
let professoresVisiveis = [];
let editandoId = null;

async function requisicao(url, opcoes = {}) {
    const resposta = await fetch(`${API}${url}`, {
        ...opcoes,
        headers: { ...(opcoes.body ? { "Content-Type": "application/json" } : {}), ...(opcoes.headers || {}) }
    });
    if (!resposta.ok) {
        let detalhe = "";
        try {
            const dados = await resposta.json();
            detalhe = dados.detail || dados.message || dados.error || "";
        } catch (_) {
            try { detalhe = await resposta.text(); } catch (_) { }
        }
        throw new Error(detalhe || `A operação falhou (HTTP ${resposta.status}).`);
    }
    if (resposta.status === 204) return null;
    const texto = await resposta.text();
    return texto ? JSON.parse(texto) : null;
}

function mostrarMensagem(texto, erro = false) {
    mensagem.textContent = texto;
    mensagem.style.color = erro ? "#b91c1c" : "#15803d";
}

async function carregarDepartamentos() {
    try {
        const departamentos = await requisicao("/departamentos");
        selectDepartamento.innerHTML = '<option value="">Selecione um departamento</option>';
        departamentos.forEach(departamento => {
            const opcao = document.createElement("option");
            opcao.value = departamento.id;
            opcao.textContent = departamento.nome || `Departamento ${departamento.id}`;
            selectDepartamento.appendChild(opcao);
        });
        totalDepartamentos.textContent = departamentos.length;
        if (!departamentos.length) {
            selectDepartamento.innerHTML = '<option value="">Cadastre um departamento primeiro</option>';
            mostrarMensagem("Nenhum departamento cadastrado. Cadastre um pela API/Swagger antes de incluir professores.", true);
        }
    } catch (erro) {
        selectDepartamento.innerHTML = '<option value="">Erro ao carregar departamentos</option>';
        totalDepartamentos.textContent = "—";
        mostrarMensagem(`Não foi possível carregar os departamentos: ${erro.message}`, true);
    }
}

function adicionarCelula(linha, valor, classe = "") {
    const celula = document.createElement("td");
    if (classe) celula.className = classe;
    celula.textContent = valor ?? "—";
    linha.appendChild(celula);
    return celula;
}

function desenharProfessores(professores) {
    professoresVisiveis = professores;
    lista.innerHTML = "";
    total.textContent = professores.length;
    statusLista.textContent = `${professores.length} registro(s) exibido(s)`;
    if (!professores.length) {
        lista.innerHTML = '<tr><td colspan="5" class="estado">Nenhum professor encontrado.</td></tr>';
        return;
    }
    professores.forEach(professor => {
        const linha = document.createElement("tr");

        const celulaNome = document.createElement("td");
        const nome = document.createElement("div");
        nome.className = "prof-nome";
        nome.textContent = professor.nome || "Sem nome";
        const id = document.createElement("div");
        id.className = "prof-id";
        id.textContent = `ID: ${professor.id}`;
        celulaNome.append(nome, id);
        linha.appendChild(celulaNome);

        const celulaContato = document.createElement("td");
        const email = document.createElement("div");
        email.className = "contato-email";
        email.textContent = professor.email || "Sem e-mail";
        const telefone = document.createElement("div");
        telefone.className = "contato-tel";
        telefone.textContent = professor.telefone || "Sem telefone";
        celulaContato.append(email, telefone);
        linha.appendChild(celulaContato);

        adicionarCelula(linha, professor.area);
        const departamento = document.createElement("td");
        const tag = document.createElement("span");
        tag.className = "departamento-tag";
        tag.textContent = professor.departamentoNome || "Não informado";
        departamento.appendChild(tag);
        linha.appendChild(departamento);

        const celulaAcoes = document.createElement("td");
        const acoes = document.createElement("div");
        acoes.className = "acoes-tabela";
        const editar = document.createElement("button");
        editar.type = "button";
        editar.className = "btn-editar";
        editar.textContent = "Editar";
        editar.addEventListener("click", () => prepararEdicao(professor));
        const excluir = document.createElement("button");
        excluir.type = "button";
        excluir.className = "btn-excluir";
        excluir.textContent = "Excluir";
        excluir.addEventListener("click", () => excluirProfessor(professor));
        acoes.append(editar, excluir);
        celulaAcoes.appendChild(acoes);
        linha.appendChild(celulaAcoes);
        lista.appendChild(linha);
    });
}

async function carregarProfessores() {
    lista.innerHTML = '<tr><td colspan="5" class="estado">Carregando professores...</td></tr>';
    try {
        const professores = await requisicao("/professores");
        desenharProfessores(professores);
        mostrarMensagem("Lista atualizada.", false);
    } catch (erro) {
        lista.innerHTML = '<tr><td colspan="5" class="estado">Não foi possível carregar os professores.</td></tr>';
        total.textContent = "—";
        statusLista.textContent = erro.message;
        mostrarMensagem(erro.message, true);
    }
}

async function buscarProfessores() {
    const termo = document.getElementById("termoBusca").value.trim();
    const tipo = document.getElementById("tipoBusca").value;
    if (!termo) {
        await carregarProfessores();
        return;
    }
    lista.innerHTML = '<tr><td colspan="5" class="estado">Pesquisando...</td></tr>';
    try {
        const resultado = await requisicao(`/professores/${tipo}/${encodeURIComponent(termo)}`);
        desenharProfessores(resultado);
        statusLista.textContent = `Pesquisa por ${tipo === "nome" ? "nome" : "área"}: “${termo}”`;
        mostrarMensagem(`${resultado.length} resultado(s) encontrado(s).`, false);
    } catch (erro) {
        lista.innerHTML = '<tr><td colspan="5" class="estado">Falha na pesquisa.</td></tr>';
        mostrarMensagem(erro.message, true);
    }
}

function prepararEdicao(professor) {
    editandoId = professor.id;
    document.getElementById("nome").value = professor.nome || "";
    document.getElementById("email").value = professor.email || "";
    document.getElementById("area").value = professor.area || "";
    document.getElementById("telefone").value = professor.telefone || "";
    selectDepartamento.value = professor.departamentoId ?? "";
    document.getElementById("tituloFormulario").textContent = `Editar professor #${professor.id}`;
    document.getElementById("modoBadge").textContent = "MODO DE EDIÇÃO";
    document.getElementById("btnSalvar").textContent = "Salvar alterações";
    document.getElementById("btnCancelar").classList.remove("escondido");
    mostrarMensagem(`Editando ${professor.nome}. Altere os campos e salve.`, false);
    form.scrollIntoView({ behavior: "smooth", block: "start" });
}

function cancelarEdicao() {
    editandoId = null;
    form.reset();
    document.getElementById("tituloFormulario").textContent = "Cadastrar professor";
    document.getElementById("modoBadge").textContent = "NOVO CADASTRO";
    document.getElementById("btnSalvar").textContent = "Salvar professor";
    document.getElementById("btnCancelar").classList.add("escondido");
}

async function salvarProfessor(evento) {
    evento.preventDefault();
    const departamentoId = selectDepartamento.value;
    if (!departamentoId) {
        mostrarMensagem("Selecione um departamento antes de salvar.", true);
        return;
    }
    const professor = {
        nome: document.getElementById("nome").value.trim(),
        email: document.getElementById("email").value.trim(),
        area: document.getElementById("area").value.trim(),
        telefone: document.getElementById("telefone").value.trim(),
        departamentoId: Number(departamentoId)
    };
    if (!professor.nome || !professor.email || !professor.area || !professor.telefone) {
        mostrarMensagem("Preencha todos os campos obrigatórios.", true);
        return;
    }

    const botao = document.getElementById("btnSalvar");
    botao.disabled = true;
    try {
        if (editandoId !== null) {
            await requisicao(`/professor/${editandoId}`, { method: "PUT", body: JSON.stringify(professor) });
            mostrarMensagem("Professor atualizado com sucesso.", false);
        } else {
            await requisicao("/professores", { method: "POST", body: JSON.stringify(professor) });
            mostrarMensagem("Professor cadastrado com sucesso.", false);
        }
        cancelarEdicao();
        document.getElementById("termoBusca").value = "";
        await carregarProfessores();
    } catch (erro) {
        mostrarMensagem(erro.message, true);
    } finally {
        botao.disabled = false;
    }
}

async function excluirProfessor(professor) {
    const confirmar = window.confirm(`Deseja realmente excluir o professor "${professor.nome}" (ID ${professor.id})?`);
    if (!confirmar) return;
    try {
        await requisicao(`/professor/${professor.id}`, { method: "DELETE" });
        if (editandoId === professor.id) cancelarEdicao();
        mostrarMensagem("Professor excluído com sucesso.", false);
        await carregarProfessores();
    } catch (erro) {
        mostrarMensagem(`Não foi possível excluir: ${erro.message}`, true);
    }
}

form.addEventListener("submit", salvarProfessor);
document.getElementById("btnAtualizar").addEventListener("click", carregarProfessores);
document.getElementById("btnBuscar").addEventListener("click", buscarProfessores);
document.getElementById("termoBusca").addEventListener("keydown", evento => {
    if (evento.key === "Enter") {
        evento.preventDefault();
        buscarProfessores();
    }
});
document.getElementById("btnLimparBusca").addEventListener("click", async () => {
    document.getElementById("termoBusca").value = "";
    await carregarProfessores();
});
document.getElementById("btnCancelar").addEventListener("click", cancelarEdicao);

async function iniciarTela() {
    await carregarDepartamentos();
    await carregarProfessores();
}
iniciarTela();
