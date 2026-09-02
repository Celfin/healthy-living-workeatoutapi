package co.zw.celfin.healthyliving.mapper;

import co.zw.celfin.healthyliving.dto.GroceryItemDto;
import co.zw.celfin.healthyliving.entity.GroceryItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroceryItemMapper {

    GroceryItemDto toDto(GroceryItem item);
}
