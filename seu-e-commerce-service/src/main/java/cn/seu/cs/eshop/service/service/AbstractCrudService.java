package cn.seu.cs.eshop.service.service;

import cs.seu.cs.eshop.common.sdk.entity.req.BaseCrudRequest;

import java.io.Serializable;

import static cs.seu.cs.eshop.common.sdk.enums.CrudOperationTypeEnum.*;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/11/7
 */
public abstract class AbstractCrudService<DTO extends Serializable> {

    public long crudOperation(BaseCrudRequest<DTO> request){
        if (INSERT.getType() == request.getAction()) {
            return insert(request.getData());
        } else if (DELETE.getType() == request.getAction()) {
            return delete(request.getData());
        } else if (UPDATE.getType() == request.getAction()) {
            return update(request.getData());
        }
        return 0L;
    }

    public abstract long insert(DTO data);

    public abstract long delete(DTO data);

    public abstract long update(DTO data);
}
