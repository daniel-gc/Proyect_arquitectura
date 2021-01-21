package mx.tecnetia.architecture.security.service.aop;


import java.util.*;

import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;
import mx.tecnetia.architecture.security.aop.dto.AtributoCacheDTO;
import mx.tecnetia.architecture.security.aop.dto.AuditoriaMetodoMessageDTO;
import mx.tecnetia.architecture.security.aop.dto.ExpresionCacheDTO;
import mx.tecnetia.architecture.security.aop.dto.MetodoCacheDTO;
import mx.tecnetia.architecture.security.persistence.hibernate.entity.aop.AuditoriaAtributoObjetoEntity;
import mx.tecnetia.architecture.security.persistence.hibernate.entity.aop.AuditoriaObjetoEntity;
import ognl.Ognl;
import ognl.OgnlException;

@Log4j2
@Component
public class AuditAssemblerMessageService implements AuditAssemblerMessageServiceI 
{
    private static final Integer ID_EVENTO_METODO = Integer.valueOf(1);

    public AuditoriaObjetoEntity convertMessageVO(AuditoriaMetodoMessageDTO auditoriaMetodoMessageVO, MetodoCacheDTO metodoVO)
    {
    	AuditoriaObjetoEntity auditoriaObjeto = new AuditoriaObjetoEntity();
        Integer idMetodo = auditoriaMetodoMessageVO.getIdClase();
        
        if(metodoVO != null)
        {
        	Calendar cal = new GregorianCalendar();
        	Date hoy = cal.getTime();
        	
            if(log.isDebugEnabled())
            	log.debug((new StringBuilder()).append("Processing message for method [").append(idMetodo).append("]").toString());
            Set<AuditoriaAtributoObjetoEntity> auditoriaAtributos = this.processArguments(auditoriaMetodoMessageVO.getAtributos(), metodoVO.getAtributos(),hoy);
            auditoriaObjeto.setIdUsuario(auditoriaMetodoMessageVO.getIdUsuario());
            auditoriaObjeto.setIdEvento(ID_EVENTO_METODO);
            auditoriaObjeto.setIdMetodo(auditoriaMetodoMessageVO.getIdClase());
            auditoriaObjeto.setFecha(hoy);
            auditoriaObjeto.setAuditoriaAtributoObjeto(auditoriaAtributos);
            
            return auditoriaObjeto;
        } else
        {
            return null;
        }
    }

    private Set<AuditoriaAtributoObjetoEntity> processArguments(Object args[], Set<AtributoCacheDTO> configArguments, Date fechaObjeto)
    {
        Set<AuditoriaAtributoObjetoEntity> auditAtributos;

        auditAtributos = new HashSet<AuditoriaAtributoObjetoEntity>();
            if(configArguments == null)
            	 return auditAtributos;
            Iterator<AtributoCacheDTO> iter = configArguments.iterator();
            do
            {
            	AtributoCacheDTO configArg;
                do
                {
                    if(!iter.hasNext())
                    	return auditAtributos;
                    configArg = iter.next();
                } while(configArg.getExpresiones() == null);
                Iterator<ExpresionCacheDTO> iter2 = configArg.getExpresiones().iterator();
                do
                {
                    if(!iter2.hasNext())
                    	return auditAtributos;
                    ExpresionCacheDTO configExpr = (ExpresionCacheDTO)iter2.next();
                    Object arg = args[configArg.getIndice().byteValue() + 1];
                    Object exprValue;
                    try
                    {
                        Object expression = Ognl.parseExpression(configExpr.getExpresion());
                        exprValue = Ognl.getValue(expression, arg);
                        
                        //logger.debug("EXPRESION.......: "+arg);
                    }
                    catch(OgnlException e)
                    {
                        log.error((new StringBuilder()).append("Error al evaluar la expresion ").append(configExpr.getExpresion()).append(" con el atributo ").append(arg).toString(), e);
                        continue;
                    }
                    AuditoriaAtributoObjetoEntity auditoriaAtributo = new AuditoriaAtributoObjetoEntity();
                    auditoriaAtributo.setIdExpresion(configExpr.getIdExpresion());
                    auditoriaAtributo.setFecha(fechaObjeto);
                    auditoriaAtributo.setValorNuevo(String.valueOf(exprValue));
                    auditAtributos.add(auditoriaAtributo);
                } while(true);
            } while(true);
       
    }

}
