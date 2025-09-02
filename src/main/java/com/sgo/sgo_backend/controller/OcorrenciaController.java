package com.sgo.sgo_backend.controller;

import com.sgo.sgo_backend.model.Ocorrencia;
import com.sgo.sgo_backend.service.OcorrenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController // Combina @Controller e @ResponseBody, simplificando a criação de APIs REST
@RequestMapping("/api/ocorrencias") // Define o prefixo da URL para todos os métodos neste controller
public class OcorrenciaController {

    @Autowired // Injeta o nosso OcorrenciaService
    private OcorrenciaService ocorrenciaService;

    // Endpoint para criar uma nova ocorrência (POST /api/ocorrencias)
    @PostMapping
    public Ocorrencia criarOcorrencia(@RequestBody Ocorrencia ocorrencia) {
        return ocorrenciaService.criarOcorrencia(ocorrencia);
    }

    // Endpoint para listar todas as ocorrências (GET /api/ocorrencias)
    @GetMapping
    public List<Ocorrencia> listarOcorrencias() {
        return ocorrenciaService.listarTodas();
    }

    // Endpoint para buscar uma ocorrência por ID (GET /api/ocorrencias/{id})
    @GetMapping("/{id}")
    public ResponseEntity<Ocorrencia> buscarOcorrenciaPorId(@PathVariable UUID id) {
        return ocorrenciaService.buscarPorId(id)
                .map(ResponseEntity::ok) // Se encontrar, retorna 200 OK com a ocorrência
                .orElse(ResponseEntity.notFound().build()); // Se não, retorna 404 Not Found
    }

    // Endpoint para atualizar uma ocorrência (PUT /api/ocorrencias/{id})
    // Note que este endpoint só será acessível pelo ADMIN, conforme a regra de segurança
    @PutMapping("/{id}")
    public ResponseEntity<Ocorrencia> atualizarOcorrencia(@PathVariable UUID id, @RequestBody Ocorrencia ocorrenciaAtualizada) {
        return ocorrenciaService.atualizarOcorrencia(id, ocorrenciaAtualizada)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para deletar uma ocorrência por ID (DELETE /api/ocorrencias/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarOcorrencia(@PathVariable UUID id) {
        ocorrenciaService.deletarOcorrencia(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content, indicando sucesso
    }
}
