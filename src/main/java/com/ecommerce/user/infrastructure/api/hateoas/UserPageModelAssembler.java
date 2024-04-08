package com.ecommerce.user.infrastructure.api.hateoas;

import com.ecommerce.user.infrastructure.api.controllers.UserController;
import com.ecommerce.user.domain.valueobjects.UserPageDomain;
import com.ecommerce.user.infrastructure.mappers.UserApiModelMapper;
import com.perficient.shoppingcart.application.api.model.GetUsersPageReq;
import lombok.SneakyThrows;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserPageModelAssembler extends
        RepresentationModelAssemblerSupport<UserPageDomain, GetUsersPageReq> {

    public UserPageModelAssembler() {
        super(UserController.class, GetUsersPageReq.class);
    }

    @SneakyThrows
    @Override
    public GetUsersPageReq toModel(UserPageDomain userPageDomain) {
        var id = UUID.randomUUID().toString();
        var resource = createModelWithId(id, userPageDomain);
        var content = userPageDomain.getUserDomains().stream()
                .map(UserApiModelMapper::convertFromDomain)
                .toList();

        var response = userPageDomain.getPageResponseDomain();
        var request = userPageDomain.getUserReqFilterDomain();

        resource.setContent(content);
        resource.setTotalItems(response.getTotalItems());
        resource.setTotalPages(response.getTotalPages());
        resource.setPageSize(request.getPageSize());
        resource.setPageNumber(request.getPageNumber());

        var filters = userPageDomain.getUserReqFilterDomain();

        resource.add(
                linkTo(methodOn(UserController.class)
                    .getUsers(filters.getPageNumber(), filters.getPageSize(),
                            filters.getFirstName(), filters.getLastName(),
                            filters.getEmail(), filters.getSort())
                ).withSelfRel());

        return resource;
    }
}
