package io.github.api.validator;

import io.github.api.domain.ItemProduct;
import io.github.api.exceptions.ObjectDuplicateException;
import io.github.api.repositories.ItemProductRepositoy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ItemProductValidator {

    private final ItemProductRepositoy repository;

    public void validate(ItemProduct itemProduct){
        if(existsItemProduct(itemProduct)){
            throw new ObjectDuplicateException("The product "
                    + itemProduct.getProduct().getName() +
                    " is registered on the database");
        }
    }

    private boolean existsItemProduct(ItemProduct itemProduct) {
        Optional<ItemProduct> itemProductOptional = repository
                .findItemProductByProduct_Name(itemProduct.getProduct().getName());
        if (itemProduct.getId() == null){
            return itemProductOptional.isPresent();
        }
        return itemProductOptional
                .map(ItemProduct::getId)
                .stream()
                .anyMatch(id -> !id.equals(itemProduct.getId()));
    }
}
