package br.com.alura.spring.data.service;

import java.util.Collection;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Cargo;
import br.com.alura.spring.data.repository.CargoRepository;
import br.com.alura.spring.data.utils.utilitarios;

@Service
public class CrudCargoService {

	private final CargoRepository cargoRepository;
	private boolean system = true;
	Integer idEscolhido;

	public CrudCargoService(CargoRepository cargoRepository) {
		this.cargoRepository = cargoRepository;
	}

	public void inicial(Scanner scanner) {
		while (system == true) {
			System.out.println("Escolha uma opção:");
			System.out.println("0 - Sair");
			System.out.println("1 - Criar novo");
			System.out.println("2 - Editar");
			System.out.println("3 - Ver todos");
			System.out.println("4 - Apagar");
			int action = scanner.nextInt();

			switch (action) {
			case 1:
				utilitarios.clearConsole();
				salvar(scanner);
				break;
			case 2:
				utilitarios.clearConsole();
				atualizar(scanner);
				break;
			case 3:
				utilitarios.clearConsole();
				exibirTodos();
				break;
			case 4:
				utilitarios.clearConsole();
				apagar(scanner);
			default:
				system = false;
				break;
			}

		}

	}

	// Cria um novo registro de cargo.
	private void salvar(Scanner scanner) {
		System.out.println("Descricao do cargo");
		String descricao = scanner.next();
		Cargo cargo = new Cargo();
		cargo.setDescricao(descricao);
		cargoRepository.save(cargo);
		System.out.println("Salvo");
	}

	// Exibe todos os registros.
	private void exibirTodos() {
		var cargos = cargoRepository.findAll();

		// Percorrer todo o iterable.
		for (Cargo c : cargos) {
			System.out.println(c.getId() + " - " + c.getDescricao());
		}
	}

	// Atualiza os dados de um registro.
	private void atualizar(Scanner scanner) {
		Cargo cargo = new Cargo();
		var cargos = cargoRepository.findAll();
		String descricao;
		Integer quantidade = 0;

		System.out.println("Digite o ID que deseja editar:");
		idEscolhido = scanner.nextInt();

		if (idEscolhido < 1 || cargoRepository.findById(idEscolhido) == null) {
			System.out.println("Valor escolhido invalido!");
		} else {
			cargo.setId(idEscolhido);
			System.out.println("Digite a nova descrição:");
			descricao = scanner.next();
			cargo.setDescricao(descricao);
			cargoRepository.save(cargo);
		}

	}

	// Devolve o tamanho máximo de um iterable
	private int tamanho(Iterable<Cargo> cargos) {

		if (cargos instanceof Collection) {
			return ((Collection<?>) cargos).size();
		}
		return 0;
	}

	private void apagar(Scanner scanner) {

		var cargos = cargoRepository.findAll();

		System.out.println("Digite o ID que deseja apagar:");
		idEscolhido = scanner.nextInt();

		if (idEscolhido < 1 || cargoRepository.findById(idEscolhido) == null) {
			System.out.println("Valor escolhido invalido!");
		} else {
			cargoRepository.deleteById(idEscolhido);
			System.out.println("Registro excluido");

		}

	}

}
