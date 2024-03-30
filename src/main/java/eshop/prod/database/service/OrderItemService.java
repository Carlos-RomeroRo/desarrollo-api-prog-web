package eshop.prod.database.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eshop.prod.database.entities.OrderItem;
import eshop.prod.database.entities.dto.OrderItemDTO;
import eshop.prod.database.entities.mappers.OrderItemMapper;
import eshop.prod.database.repository.OrderItemRepository;
import eshop.prod.database.repository.OrderRepository;
import eshop.prod.database.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderItemService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    /** get all OrderItems */
    public List<OrderItemDTO> getAllOrderItems() {

        try {
            List<OrderItem> orderItems = orderItemRepository.findAll();
            return orderItems.stream().map(OrderItemMapper.INSTANCE::orderItemToOrderItemDTO).toList();
        } catch (Exception e) {
            log.error("Error getting all OrderItems", e);
        }

        return List.of();
    }

    /** get OrderItem by id */
    public OrderItemDTO getOrderItemById(Long id) {
        try {
            OrderItem orderItem = orderItemRepository.findById(id).orElse(null);
            return OrderItemMapper.INSTANCE.orderItemToOrderItemDTO(orderItem);
        } catch (Exception e) {
            log.error("Error getting OrderItem by id", e);
        }

        return null;
    }

    /** get OrderItem by order id */
    public List<OrderItemDTO> getOrderItemsByOrderId(Long id) {
        try {
            List<OrderItem> orderItems = orderItemRepository.findByOrderId(id);
            return orderItems.stream().map(OrderItemMapper.INSTANCE::orderItemToOrderItemDTO).toList();
        } catch (Exception e) {
            log.error("Error getting OrderItems by order id", e);
        }

        return List.of();
    }

    /** get OrderItem by product id */
    public List<OrderItemDTO> getOrderItemsByProductId(Long id) {
        try {
            List<OrderItem> orderItems = orderItemRepository.findByProductId(id);
            return orderItems.stream().map(OrderItemMapper.INSTANCE::orderItemToOrderItemDTO).toList();
        } catch (Exception e) {
            log.error("Error getting OrderItems by product id", e);
        }

        return List.of();
    }

    /** get total sell by product id */
    public Integer getTotalSellByProductId(Long id) {
        try {
            return orderItemRepository.findTotalSellByProductId(id);
        } catch (Exception e) {
            log.error("Error getting total sell by product id", e);
        }
        return 0;
    }

    /** create OrderItem */
    public OrderItemDTO createOrderItem(OrderItemDTO orderItemDTO) {
        try {
            if (orderItemDTO.getId_order_item() != null) {
                throw new IllegalArgumentException("Id will be generated by database");
            }
            OrderItem orderItem = OrderItemMapper.INSTANCE.orderItemDTOToOrderItem(
                    orderItemDTO,
                    orderRepository,
                    productRepository);
            OrderItem savedOrderItem = orderItemRepository.save(orderItem);
            return OrderItemMapper.INSTANCE.orderItemToOrderItemDTO(savedOrderItem);
        } catch (Exception e) {
            log.error("Error creating OrderItem", e);
        }
        return null;
    }

    /** update OrderItem */
    public OrderItemDTO updateOrderItem(Long id, OrderItemDTO orderItemDTO) {
        try {
            OrderItem orderItemFromDB = orderItemRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("OrderItem does not exist"));
            OrderItem orderItem = OrderItemMapper.INSTANCE.orderItemDTOToOrderItem(
                    orderItemDTO,
                    orderRepository,
                    productRepository);
            orderItemFromDB = orderItemFromDB.updateOnllyNecesary(orderItemFromDB, orderItem);
            OrderItem savedOrderItem = orderItemRepository.save(orderItemFromDB);
            return OrderItemMapper.INSTANCE.orderItemToOrderItemDTO(savedOrderItem);
        } catch (Exception e) {
            log.error("Error updating OrderItem", e);
        }
        return null;
    }

    /** delete OrderItem */
    public boolean deleteOrderItem(Long id) {
        try {
            orderItemRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("Error deleting OrderItem", e);
        }
        return false;
    }
}
