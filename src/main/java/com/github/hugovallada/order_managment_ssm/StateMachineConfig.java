package com.github.hugovallada.order_managment_ssm;

import java.util.EnumSet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.transition.Transition;

@Configuration
@EnableStateMachineFactory
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<OrderState, OrderEvent>{
    
    @Override
    public void configure(StateMachineStateConfigurer<OrderState, OrderEvent> states) throws Exception {
        states
            .withStates()
            .initial(OrderState.NEW)
            .states(EnumSet.allOf(OrderState.class))
            .end(OrderState.COMPLETED)
            .end(OrderState.CANCELLED);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<OrderState, OrderEvent> transitions) throws Exception {
        transitions
        .withExternal().source(OrderState.NEW).target(OrderState.VALIDATED).event(OrderEvent.VALIDATE)
        .action(validateOrderAction())
        .and()
        .withExternal().source(OrderState.VALIDATED).target(OrderState.PAID).event(OrderEvent.PAY)
        .action(payOrderAction())
        .and()
        .withExternal().source(OrderState.PAID).target(OrderState.SHIPPED).event(OrderEvent.SHIP)
        .action(shipOrderAction())
        .and()
        .withExternal().source(OrderState.SHIPPED).target(OrderState.COMPLETED).event(OrderEvent.COMPLETE)
        .and()
        .withExternal().source(OrderState.VALIDATED).target(OrderState.CANCELLED).event(OrderEvent.CANCEL)
        .and()
        .withExternal().source(OrderState.PAID).target(OrderState.CANCELLED).event(OrderEvent.CANCEL);
        }

    @Override
    public void configure(StateMachineConfigurationConfigurer<OrderState, OrderEvent> config) throws Exception {
        config.withConfiguration().listener(stateMachineListener());
    }


    @Bean
    StateMachineListener<OrderState, OrderEvent> stateMachineListener() {
        return new StateMachineListenerAdapter<>() {
            @Override
            public void transition(Transition<OrderState, OrderEvent> transition) {
                System.out.println("Transtioning from " + transition.getSource().getId() + " to " + transition.getTarget().getId());
            }
        };
    }

    @Bean
    Action<OrderState, OrderEvent> shipOrderAction() {
        return context -> {
            System.out.println("Shipping order");
        };
    }

    @Bean
    Action<OrderState, OrderEvent> payOrderAction() {
        return context -> {
            System.out.println("Paying order");
        };
    }

    @Bean
    Action<OrderState, OrderEvent> validateOrderAction() {
        return context -> {
            System.out.println("Validate order");
        };
    }
}
