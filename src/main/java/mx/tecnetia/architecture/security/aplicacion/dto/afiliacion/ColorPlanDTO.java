package mx.tecnetia.architecture.security.aplicacion.dto.afiliacion;

import lombok.Data;

@Data
public class ColorPlanDTO {
	private Integer idColorPlan;
    private Integer idPlan;
    private String colorHex;
    private String colorRGBA;
    private String usoPara;
    private Integer orden;
	private PlanDTO planEntity;
}
