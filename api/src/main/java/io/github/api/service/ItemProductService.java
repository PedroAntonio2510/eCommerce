package io.github.api.service;

import io.github.api.domain.ItemProduct;
import io.github.api.repositories.ItemProductRepositoy;
import io.github.api.validator.ItemProductValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemProductService {

    private final ItemProductRepositoy repositoy;
    private final ItemProductValidator validator;

    public ItemProduct saveItem(ItemProduct itemProduct){
        validator.validate(itemProduct);
        return repositoy.save(itemProduct);
    }

    public List<ItemProduct> getAllItens() {
        return repositoy.findAll();
    }


}
