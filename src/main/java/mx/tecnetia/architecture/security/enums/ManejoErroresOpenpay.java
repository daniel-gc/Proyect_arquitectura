package mx.tecnetia.architecture.security.enums;

public enum ManejoErroresOpenpay {
	E_1000("Ocurrió un error interno en el servidor de Openpay"),
	E_1001("El formato de la petición no es JSON, los campos no tienen el formato correcto, o la petición no tiene campos que son requeridos."),
	E_1002("La llamada no esta autenticada o la autenticación es incorrecta."),
	E_1003("La operación no se pudo completar por que el valor de uno o más de los parámetros no es correcto."),
	E_1004("Un servicio necesario para el procesamiento de la transacción no se encuentra disponible."),
	E_1005("Uno de los recursos requeridos no existe."),
	E_1006("Ya existe una transacción con el mismo ID de orden."),
	E_1007("La transferencia de fondos entre una cuenta de banco o tarjeta y la cuenta de Openpay no fue aceptada."),
	E_1008("Una de las cuentas requeridas en la petición se encuentra desactivada."),
	E_1009("El cuerpo de la petición es demasiado grande."),
	E_1010("Se esta utilizando la llave pública para hacer una llamada que requiere la llave privada, o bien, se esta usando la llave privada desde JavaScript."),
	E_1011("Se solicita un recurso que esta marcado como eliminado."),
	E_1012("El monto transacción esta fuera de los limites permitidos."),
	E_1013("La operación no esta permitida para el recurso."),
	E_1014("La cuenta esta inactiva."),
	E_1015("No se ha obtenido respuesta de la solicitud realizada al servicio."),
	E_1016("El mail del comercio ya ha sido procesada."),
	E_1017("El gateway no se encuentra disponible en ese momento."),
	E_1018("El número de intentos de cargo es mayor al permitido."),
	E_1020("El número de dígitos decimales es inválido para esta moneda"),
	// Almacenamiento
	E_2001("La cuenta de banco con esta CLABE ya se encuentra registrada en el cliente."),
	E_2003("El cliente con este identificador externo (External ID) ya existe."),
	// Tarjetas
	E_2004("El número de tarjeta es invalido."),
	E_2005("La fecha de expiración de la tarjeta es anterior a la fecha actual."),
	E_2006("El código de seguridad de la tarjeta (CVV2) no fue proporcionado."),
	E_2007("El número de tarjeta es de prueba, solamente puede usarse en Sandbox."),
	E_2008("La tarjeta no es valida para pago con puntos."),
	E_2009("El código de seguridad de la tarjeta (CVV2) es inválido."),
	E_2010("Autenticación 3D Secure fallida."),
	E_2011("Tipo de tarjeta no soportada."),
	E_3001("La tarjeta fue declinada por el banco."),
	E_3002("La tarjeta ha expirado."),
	E_3003("La tarjeta no tiene fondos suficientes."),
	E_3004("La tarjeta ha sido identificada como una tarjeta robada."),
	E_3005("La tarjeta ha sido rechazada por el sistema antifraude. / Rechazada por coincidir con registros en lista negra."),
	E_3006("La operación no esta permitida para este cliente o esta transacción."),
	E_3009("La tarjeta fue reportada como perdida."),
	E_3010("El banco ha restringido la tarjeta."),
	E_3011("El banco ha solicitado que la tarjeta sea retenida. Contacte al banco."),
	E_3012("Se requiere solicitar al banco autorización para realizar este pago."),
	E_3201("Comercio no autorizado para procesar pago a meses sin intereses."),
	E_3203("Promoción no valida para este tipo de tarjetas."),
	E_3204("El monto de la transacción es menor al mínimo permitido para la promoción."),
	E_3205("Promoción no permitida."),
	// Cuentas
	E_4001("La cuenta de Openpay no tiene fondos suficientes."),
	E_4002("La operación no puede ser completada hasta que sean pagadas las comisiones pendientes."),
	// Webhooks
	E_6001("El webhook ya ha sido procesado."),
	E_6002("No se ha podido conectar con el servicio de webhook."),
	E_6003("El servicio respondió con errores.");
	
    private String error;
    
    ManejoErroresOpenpay(String error) {
        this.error = error;
    }
 
    public String getError() {
        return this.error;
    } 
}
