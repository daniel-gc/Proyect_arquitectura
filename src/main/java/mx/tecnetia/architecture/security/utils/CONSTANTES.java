package mx.tecnetia.architecture.security.utils;

public enum CONSTANTES {

	MENSAJE_HEADER("mensaje");

	private final String valor;

	CONSTANTES(String valor) {
		this.valor = valor;
	}

	public String getValor() {
		return this.valor;
	}
}
