package org.example.appliancestore.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.appliancestore.aspect.Loggable;
import org.example.appliancestore.dto.ApplianceForm;
import org.example.appliancestore.dto.EmployeeDto;
import org.example.appliancestore.dto.OrderDto;
import org.example.appliancestore.dto.OrderRowDto;
import org.example.appliancestore.mapper.OrderMapper;
import org.example.appliancestore.model.Client;
import org.example.appliancestore.model.Employee;
import org.example.appliancestore.model.Order;
import org.example.appliancestore.model.User;
import org.example.appliancestore.service.ApplianceService;
import org.example.appliancestore.service.ClientService;
import org.example.appliancestore.service.EmployeeService;
import org.example.appliancestore.service.OrderService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Set;


@Controller
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ClientService clientService;
    private final EmployeeService employeeService;
    private final ApplianceService applianceService;
    private final UserDetailsService userDetailsService;
    private final OrderMapper orderMapper;


    @Loggable
    @GetMapping
    public String getAll(Model model, Principal principal,
                         @RequestParam(name = "page", defaultValue = "0") Integer page,
                         @RequestParam(name = "size", defaultValue = "5") Integer size,
                         @RequestParam(name = "sort", required = false) String sort) {
        String userName = principal.getName();
        User user = (User) userDetailsService.loadUserByUsername(userName);
        if (user instanceof Client) {
            model.addAttribute("orders",
                    orderService.getAllByClientId(user.getId(), page, size, sort));
            model.addAttribute("totalPages",
                    orderService.getPagesCountByClientId(user.getId(), page, size));
        } else if (user instanceof Employee) {
            model.addAttribute("orders", orderService.getAll(page, size, sort));
            model.addAttribute("totalPages",
                    orderService.getPagesCount(page, size));
        } else {
            throw new AccessDeniedException("You do not have access to view orders.");
        }
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        return "order/orders";
    }


    @Loggable
    @GetMapping("/add")
    public String add(Model model, Principal principal) {
        OrderDto order = new OrderDto();
        String userName = principal.getName();
        User user = (User) userDetailsService.loadUserByUsername(userName);
        if (user instanceof Client) {
            model.addAttribute("order", order);
            model.addAttribute("clients", List.of(user));
            model.addAttribute("employees", employeeService.getAll(null, null, null));
        } else if (user instanceof Employee) {
            model.addAttribute("order", order);
            model.addAttribute("clients", clientService.getAll(null, null, null));
            model.addAttribute("employees", employeeService.getAll(null, null, null));
        } else {
            throw new AccessDeniedException("You do not have access to view orders.");
        }
        return "order/newOrder";
    }

    @Loggable
    @PostMapping("/add")
    @PreAuthorize("hasRole('EMPLOYEE') or hasRole('CLIENT')")
    public String add(@Validated @ModelAttribute OrderDto order, BindingResult result, Principal principal) {
        String userName = principal.getName();
        User user = (User) userDetailsService.loadUserByUsername(userName);
        if (user instanceof Client && order.getClient().getId() != user.getId()) {
            throw new AccessDeniedException("You only can create orders for yourself!");
        } else if (user instanceof Client && order.getApproved()) {
            throw new AccessDeniedException("You can not create approved order!");
        }
        if (result.hasErrors()) {
            return "order/newOrder";
        }
        Order orderEntity = orderMapper.mapToEntity(order);
        orderService.add(orderEntity);
        return "redirect:/orders";
    }


    @Loggable
    @GetMapping("/{id}/edit")
    @PreAuthorize("hasRole('EMPLOYEE') or @orderServiceImpl.isOwnerOrThrow(#id, authentication.principal.id)")
    public String update(@PathVariable("id") Long id, Model model) {
        OrderDto orderDto = orderService.readById(id);
        Set<OrderRowDto> rows = orderService.getRowsByOrderId(id);
        List<EmployeeDto> employees = employeeService.getAll(null, null, null);
        model.addAttribute("orderDto", orderDto);
        model.addAttribute("rows", rows);
        model.addAttribute("employees", employees);
        return "order/editOrder";
    }

    @Loggable
    @PostMapping("/{id}/edit")
    @PreAuthorize("hasRole('EMPLOYEE') or @orderServiceImpl.isOwnerOrThrow(#id, authentication.principal.id)")
    public String update(@PathVariable("id") Long id, @Validated @ModelAttribute("orderDto") OrderDto orderDto, BindingResult result,
                         Principal principal) {
        String userName = principal.getName();
        User user = (User) userDetailsService.loadUserByUsername(userName);
        if (user instanceof Client && orderService.readById(id).getApproved() != orderDto.getApproved()) {
            throw new AccessDeniedException("You can not approve or unapprove orders!");
        } else if (user instanceof Client && orderDto.getClient().getId() != user.getId()) {
            throw new AccessDeniedException("You can not assign this order to another client!");
        }
        if (result.hasErrors()) {
            return "order/editOrder";
        }
        orderService.updateById(id, orderDto);
        return "redirect:/orders";
    }


    @Loggable
    @GetMapping("/{id}/appliance/add")
    @PreAuthorize("hasRole('EMPLOYEE') or @orderServiceImpl.isOwnerOrThrow(#id, authentication.principal.id)")
    public String addAppliance(@PathVariable("id") Long id, Model model) {
        OrderDto order = orderService.readById(id);
        model.addAttribute("order", order);
        model.addAttribute("appliances", applianceService.getAll(null, null, null));
        model.addAttribute("applianceForm", new ApplianceForm());
        return "order/choiceAppliance";
    }

    @Loggable
    @PostMapping("/{id}/appliance/add")
    @PreAuthorize("hasRole('EMPLOYEE') or @orderServiceImpl.isOwnerOrThrow(#id, authentication.principal.id)")
    public String addAppliance(@PathVariable("id") Long id,
                               @Valid @ModelAttribute ApplianceForm applianceForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("order", orderService.readById(id));
            model.addAttribute("appliances", applianceService.getAll(null, null, null));
            return "order/choiceAppliance";
        }
        Order orderToUpdate = orderService.addApplianceToOrder(id, applianceForm.getApplianceId(), applianceForm.getQuantity());
        orderService.update(orderToUpdate);
        return "redirect:/orders/" + id + "/edit";
    }


    @Loggable
    @GetMapping("/{id}/approved")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public String approve(@PathVariable("id") Long id) {
        Order order = orderService.approveById(id);
        orderService.update(order);
        return "redirect:/orders";
    }


    @Loggable
    @GetMapping("/{id}/unapproved")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public String unApprove(@PathVariable("id") Long id) {
        Order order = orderService.unApproveById(id);
        orderService.update(order);
        return "redirect:/orders";
    }

    @Loggable
    @GetMapping("/{id}/delete")
    @PreAuthorize("hasRole('EMPLOYEE') or @orderServiceImpl.isOwnerOrThrow(#id, authentication.principal.id)")
    public String delete(@PathVariable("id") Long id) {
        orderService.delete(id);
        return "redirect:/orders";
    }
}
