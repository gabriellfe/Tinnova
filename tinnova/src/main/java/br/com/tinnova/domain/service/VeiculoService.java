package br.com.tinnova.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.tinnova.domain.exception.EntidadeNaoEncontradaException;
import br.com.tinnova.domain.model.Veiculo;
import br.com.tinnova.domain.repository.VeiculoRepository;

/**
 * Classe Service para Controller de Veiculos
 * 
 * @author Gabriell Marques de Felipe {11/12/2021}
 * @version 1.0
 *
 */

@Service
public class VeiculoService {

	@Autowired
	private VeiculoRepository veiculoRepository;

	/**
	 * Metodo para salvar Veículo e validar a marca
	 */

	public Veiculo salvar(Veiculo veiculo) {
		// Validando marca
		if (validaMarca(veiculo.getMarca())) {
			throw new EntidadeNaoEncontradaException(String.format("Marca inválida"));
		}
		if (veiculo.getDescricao() == null || veiculo.getDescricao().isEmpty()) {
			throw new EntidadeNaoEncontradaException(String.format("Descrição inválida !!"));
		}
		if (veiculo.getVeiculo() == null || veiculo.getVeiculo().isEmpty()) {
			throw new EntidadeNaoEncontradaException(String.format("Nome do veículo inválido !!"));
		}
		
		veiculo.setDescricao(veiculo.getDescricao().toUpperCase());
		veiculo.setMarca(veiculo.getMarca().toUpperCase());
		veiculo.setVeiculo(veiculo.getVeiculo().toUpperCase());

		return veiculoRepository.save(veiculo);
	}

	/**
	 * Metodo para excluir Veículo pelo ID
	 */

	public void excluir(Long veiculoId) {
		try {
			veiculoRepository.deleteById(veiculoId);

		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format("Não existe um cadastro com código %d", veiculoId));

		}
	}

	/**
	 * Metodo para buscar veículos por qualquer atributo
	 */

	public List<Veiculo> find(Veiculo veiculo) {
		return veiculoRepository.find(veiculo.getAno(), veiculo.getCreated(), veiculo.getDescricao(),
				veiculo.getMarca(), veiculo.getUpdated(), veiculo.getVeiculo(), veiculo.getVendido());
	}

	/**
	 * Metodo para validar marcas
	 */

	public boolean validaMarca(String marca) {
		return veiculoRepository.findMarca(marca).isEmpty();
	}
}
