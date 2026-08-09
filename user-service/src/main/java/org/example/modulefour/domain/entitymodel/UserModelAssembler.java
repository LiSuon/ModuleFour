package org.example.modulefour.domain.entitymodel;

import org.example.modulefour.controllers.UserController;
import org.example.modulefour.domain.dto.UserDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelAssembler {
    public CustomEntityModel toModel(UserDTO user, boolean includeLinks) {
        if (user == null) {
            return null;
        }

        CustomEntityModel model = new CustomEntityModel(user);
        Link selfLink = linkTo(methodOn(UserController.class).getUser(user.getId())).withSelfRel();
        Link updateUserLink = linkTo(methodOn(UserController.class).updateUser(user.getId(), null)).withRel("update");
        Link deleteUserLink = linkTo(methodOn(UserController.class).deleteUser(user.getId())).withRel("delete");
        model.addLinks(List.of(selfLink, updateUserLink, deleteUserLink));

        if (includeLinks) {
            Link createUserLink = linkTo(methodOn(UserController.class).createUser(null)).withRel("create");
            Link allUsersLink = linkTo(methodOn(UserController.class).getAllUsers()).withRel("all users");
            model.addLinks(List.of(createUserLink, allUsersLink));
        }

        return model;
    }

    public CollectionModel<CustomEntityModel> toCollectionModel(List<UserDTO> users) {
        List<CustomEntityModel> models = users.stream().map(i -> toModel(i, false)).collect(Collectors.toList());

        CollectionModel<CustomEntityModel> collectionModel = CollectionModel.of(models);
        collectionModel.add(linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());
        collectionModel.add(linkTo(methodOn(UserController.class).createUser(null)).withRel("create"));
        return collectionModel;
    }
}
