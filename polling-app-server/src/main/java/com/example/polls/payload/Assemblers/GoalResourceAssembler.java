package com.example.polls.payload.Assemblers;

import com.example.polls.controller.GoalsController;
import com.example.polls.model.Goal;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GoalResourceAssembler implements RepresentationModelAssembler<Goal, EntityModel<Goal>> {

    @Override
    public EntityModel<Goal> toModel(Goal goal) {

        return new EntityModel<>(goal,
                linkTo(methodOn(GoalsController.class).getOne(goal.getId())).withSelfRel(),
                linkTo(methodOn(GoalsController.class).delete(goal.getId())).withRel("delete_goal"));
    }
}

