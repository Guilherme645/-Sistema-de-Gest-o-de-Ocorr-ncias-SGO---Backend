package com.sgo.sgo_backend.service;

import com.sgo.sgo_backend.model.Ocorrencia;
import com.sgo.sgo_backend.repository.OcorrenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service // Anotação que marca esta classe como um componente de serviço gerenciado pelo Spring
public class OcorrenciaService {

    @Autowired // Injeção de Dependência: O Spring vai nos fornecer uma instância do OcorrenciaRepository
    private OcorrenciaRepository ocorrenciaRepository;

    // Método para criar uma nova ocorrência
    public Ocorrencia criarOcorrencia(Ocorrencia ocorrencia) {
        // Regra de negócio: Definir a data de criação no momento exato da chamada
        ocorrencia.setDataCriacao(LocalDateTime.now());
        // Utiliza o repositório para salvar a ocorrência no banco de dados
        return ocorrenciaRepository.save(ocorrencia);
    }

    // Método para listar todas as ocorrências
    public List<Ocorrencia> listarTodas() {
        return ocorrenciaRepository.findAll();
    }

    // Método para buscar uma ocorrência pelo seu ID
    public Optional<Ocorrencia> buscarPorId(UUID id) {
        return ocorrenciaRepository.findById(id);
    }

    // Método para atualizar uma ocorrência
    public Optional<Ocorrencia> atualizarOcorrencia(UUID id, Ocorrencia ocorrenciaAtualizada) {
        return ocorrenciaRepository.findById(id).map(ocorrenciaExistente -> {
            // Atualiza os campos necessários
            ocorrenciaExistente.setTitulo(ocorrenciaAtualizada.getTitulo());
            ocorrenciaExistente.setDescricao(ocorrenciaAtualizada.getDescricao());
            ocorrenciaExistente.setStatus(ocorrenciaAtualizada.getStatus());
            // Define a data de atualização
            ocorrenciaExistente.setDataAtualizacao(LocalDateTime.now());
            // Salva e retorna a ocorrência atualizada
            return ocorrenciaRepository.save(ocorrenciaExistente);
        });
    }

    // Método para deletar uma ocorrência pelo seu ID
    public void deletarOcorrencia(UUID id) {
        ocorrenciaRepository.deleteById(id);
    }
}
