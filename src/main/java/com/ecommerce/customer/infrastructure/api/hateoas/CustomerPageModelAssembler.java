package com.ecommerce.customer.infrastructure.api.hateoas;

import com.ecommerce.customer.infrastructure.api.controllers.CustomerController;
import com.perficient.shoppingcart.application.api.model.GetCustomerPageReq;
import com.ecommerce.customer.domain.valueobjects.CustomerPageDomain;
import com.ecommerce.customer.infrastructure.mappers.CustomerApiModelMapper;
import lombok.SneakyThrows;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CustomerPageModelAssembler extends
        RepresentationModelAssemblerSupport<CustomerPageDomain, GetCustomerPageReq> {

    public CustomerPageModelAssembler() {
        super(CustomerController.class, GetCustomerPageReq.class);
    }

    @SneakyThrows
    @Override
    public GetCustomerPageReq toModel(CustomerPageDomain customerPageDomain) {
        var id = UUID.randomUUID().toString();
        var resource = createModelWithId(id, customerPageDomain);
        var content = customerPageDomain.getCustomerDomains().stream()
                .map(CustomerApiModelMapper::convertFromDomain)
                .toList();

        var response = customerPageDomain.getPageResponseDomain();
        var request = customerPageDomain.getCustomerReqFilterDomain();

        resource.setContent(content);
        resource.setTotalItems(response.getTotalItems());
        resource.setTotalPages(response.getTotalPages());
        resource.setPageSize(request.getPageSize());
        resource.setPageNumber(request.getPageNumber());

        var filters = customerPageDomain.getCustomerReqFilterDomain();

        resource.add(
                linkTo(methodOn(CustomerController.class)
                    .getCustomers(filters.getPageNumber(), filters.getPageSize(),
                            filters.getFirstName(), filters.getLastName(),
                            filters.getEmail(), filters.getSort())
                ).withSelfRel());

        return resource;
    }
}
