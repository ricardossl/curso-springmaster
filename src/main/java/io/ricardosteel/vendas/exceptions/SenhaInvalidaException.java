package io.ricardosteel.vendas.exceptions;

public class SenhaInvalidaException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public SenhaInvalidaException(String msg) {
		super(msg);
	}
	
	public SenhaInvalidaException() {
		super("Senha inválida.");
	}
}
