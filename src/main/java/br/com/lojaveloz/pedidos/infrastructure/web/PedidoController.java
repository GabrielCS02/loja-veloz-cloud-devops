package br.com.lojaveloz.pedidos.infrastructure.web;

import br.com.lojaveloz.pedidos.application.dto.CriarPedidoRequest;
import br.com.lojaveloz.pedidos.application.dto.PedidoResponse;
import br.com.lojaveloz.pedidos.application.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<PedidoResponse> criar(
            @Valid @RequestBody CriarPedidoRequest request
    ) {
        PedidoResponse response = pedidoService.criar(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponse>> listar() {
        return ResponseEntity.ok(pedidoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> buscarPorId(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                pedidoService.buscarPorId(id)
        );
    }

    @PatchMapping("/{id}/iniciar-pagamento")
    public ResponseEntity<PedidoResponse> iniciarPagamento(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                pedidoService.iniciarPagamento(id)
        );
    }

    @PatchMapping("/{id}/confirmar-pagamento")
    public ResponseEntity<PedidoResponse> confirmarPagamento(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                pedidoService.confirmarPagamento(id)
        );
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<PedidoResponse> cancelar(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                pedidoService.cancelar(id)
        );
    }
}