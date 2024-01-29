package br.com.fiap.soat.techChallenge.bdd;

import br.com.fiap.soat.techChallenge.entities.Cliente;
import br.com.fiap.soat.techChallenge.interfaces.gateways.ClienteGatewayPort;
import br.com.fiap.soat.techChallenge.usecases.IdentificarClienteUseCase;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.junit.jupiter.api.BeforeEach;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class IdentificarClienteStepDefinition {

    private ClienteGatewayPort clienteGateway;
    private IdentificarClienteUseCase identificarClienteUseCase;

    private Cliente resultado;
    private String cpf;
    private Cliente clienteRetorno;

    @Dado("que eu tenha um cliente cadastrado")
    public void queEuTenhaUmClienteCadastrado() {
        cpf = "123.456.789-01";
        clienteRetorno = new Cliente("John Doe", cpf, "123-456-789");
        clienteGateway = mock(ClienteGatewayPort.class);
        identificarClienteUseCase = new IdentificarClienteUseCase(clienteGateway);
        // Configurando o comportamento do mock
        when(clienteGateway.identificaPor(cpf)).thenReturn(Optional.of(clienteRetorno));
    }

    @Quando("eu consultar o cliente pelo CPF")
    public void euConsultarOClientePeloCPF() {
        resultado = identificarClienteUseCase.execute(cpf).get();
    }

    @Então("o cliente é retornado com sucesso")
    public void oClienteÉRetornadoComSucesso() {
        // Verificando se o método do gateway foi chamado com os parâmetros corretos
        verify(clienteGateway, times(1)).identificaPor(cpf);

        // Verificando se o resultado é o esperado
        assertEquals(clienteRetorno, resultado);
    }
}
