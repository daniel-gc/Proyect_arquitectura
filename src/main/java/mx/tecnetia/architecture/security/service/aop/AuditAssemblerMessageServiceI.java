package mx.tecnetia.architecture.security.service.aop;


import mx.tecnetia.architecture.security.aop.dto.AuditoriaMetodoMessageDTO;
import mx.tecnetia.architecture.security.aop.dto.MetodoCacheDTO;
import mx.tecnetia.architecture.security.persistence.hibernate.entity.aop.AuditoriaObjetoEntity;

public interface AuditAssemblerMessageServiceI
{

    public abstract AuditoriaObjetoEntity convertMessageVO(AuditoriaMetodoMessageDTO auditoriaMetodoMessageVO, MetodoCacheDTO metodoVO);
}
