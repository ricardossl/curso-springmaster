package io.ricardosteel.vendas.exceptions;

@SuppressWarnings("serial")
public class RegraNegocioException extends RuntimeException {
	public RegraNegocioException(String msg) {
		super(msg);
	}
}
