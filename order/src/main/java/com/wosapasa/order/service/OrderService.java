package com.wosapasa.order.service;

import com.wosapasa.order.dto.OrderLineItemsDto;
import com.wosapasa.order.dto.OrderRequest;
import com.wosapasa.order.model.Order;
import com.wosapasa.order.model.OrderLineItems;
import com.wosapasa.order.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;


    public void placeOrder(OrderRequest orderRequest){
       Order order = new Order();
       order.setOrderNumber(UUID.randomUUID().toString());
       List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
               .stream()
               .map(orderLineItemsDto -> mapToDto(orderLineItemsDto))
               .toList();
       order.setOrderLineItemsList(orderLineItems);

       orderRepository.save(order);
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
