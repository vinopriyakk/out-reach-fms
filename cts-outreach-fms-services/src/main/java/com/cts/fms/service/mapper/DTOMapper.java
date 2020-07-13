package com.cts.fms.service.mapper;

import java.util.List;
import java.util.stream.Collectors;

public interface DTOMapper<D, V> {
	
	V apply(D d);
	
	default List<V> convertToList(final List<D> input) {
        return input.stream().map(this::apply).collect(Collectors.toList());
    }

}
