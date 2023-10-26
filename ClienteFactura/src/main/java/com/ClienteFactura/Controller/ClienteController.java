package com.ClienteFactura.Controller;

import com.ClienteFactura.Exception.AlreadyExistsException;
import com.ClienteFactura.Exception.IsEmptyException;
import com.ClienteFactura.Exception.ClienteNotFoundException;
import com.ClienteFactura.Service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @PostMapping ("/clientes")
    public ResponseEntity<String> anyadirCliente(@Valid @RequestBody ClienteInput clienteInput){
        try {
            clienteService.anyadirCliente(clienteInput);
            return ResponseEntity.ok().build();
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @RequestMapping(value = "/clientes", params = {"premium", "pais"})
    public ResponseEntity<List<ClienteOutput>> getClientesByPremiumYPais(@RequestParam boolean premium, @RequestParam String pais){
        try {
            List<ClienteOutput> clientesByPremiumYPais = clienteService.getClientesByPremiumYPais(premium, pais);
            return ResponseEntity.ok(clientesByPremiumYPais);
        } catch (IsEmptyException e) {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/clientes")
    public ResponseEntity<List<ClienteOutput>> getClientes(){
        List<ClienteOutput> clientes = null;
        try {
            clientes = clienteService.getClientes();
            return ResponseEntity.ok(clientes);

        } catch (IsEmptyException e) {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping ("/clientes/facturas")
    public ResponseEntity<String> anyadirFactura(@Valid @RequestBody FacturaInput facturaInput){
        try {
            clienteService.anyadirFacturaACliente(facturaInput);
            return ResponseEntity.ok().build();
        } catch (ClienteNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/clientes/{dni}/facturas")
    public ResponseEntity<List<FacturaOutputCodYTotal>> getFacturasCliente(@PathVariable String dni){
        try {
            List<FacturaOutputCodYTotal> facturasCliente = clienteService.getFacturasCliente(dni);
            return ResponseEntity.ok(facturasCliente);
        } catch (ClienteNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IsEmptyException e) {
            return ResponseEntity.noContent().build();
        }
    }
}