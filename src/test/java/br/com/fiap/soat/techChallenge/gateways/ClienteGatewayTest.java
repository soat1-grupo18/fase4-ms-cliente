package br.com.fiap.soat.techChallenge.gateways;

import br.com.fiap.soat.techChallenge.entities.Cliente;
import br.com.fiap.soat.techChallenge.jpa.entities.ClienteJpaEntity;
import br.com.fiap.soat.techChallenge.jpa.repositories.ClienteRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
class ClienteGatewayTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteGateway clienteGateway;

    @Test
    void cadastra() {
        var cliente = new Cliente("Nome do Cliente", "12345678900", "32955557777");
        var clienteInserido = clienteGateway.cadastra(cliente);
        assertNotNull(clienteInserido.getId());
        assertEquals(cliente.getNome(), clienteInserido.getNome());
        assertEquals(cliente.getCpf(), clienteInserido.getCpf());
        assertEquals(cliente.getTelefone(), clienteInserido.getTelefone());
    }

    @Test
    void identificaPor() {
        var clienteJpa = clienteRepository.save(ClienteJpaEntity.fromDomain(new Cliente("Nome do Cliente", "12345678900", "32955557777")));
        var clienteIdentificado = clienteGateway.identificaPor(clienteJpa.getCpf()).get();
        assertNotNull(clienteIdentificado.getId());
        assertEquals(clienteJpa.getNome(), clienteIdentificado.getNome());
        assertEquals(clienteJpa.getCpf(), clienteIdentificado.getCpf());
        assertEquals(clienteJpa.getTelefone(), clienteIdentificado.getTelefone());
    }

    @Test
    void identificaPorId() {
        var clienteJpa = clienteRepository.save(ClienteJpaEntity.fromDomain(new Cliente("Nome do Cliente", "12345678900", "32955557777")));
        var clienteIdentificado = clienteGateway.identificaPorId(clienteJpa.getId()).get();
        assertNotNull(clienteIdentificado.getId());
        assertEquals(clienteJpa.getNome(), clienteIdentificado.getNome());
        assertEquals(clienteJpa.getCpf(), clienteIdentificado.getCpf());
        assertEquals(clienteJpa.getTelefone(), clienteIdentificado.getTelefone());
    }
}