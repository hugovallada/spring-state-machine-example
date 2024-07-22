package com.github.hugovallada.order_managment_ssm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class OrderService {
    
    @Autowired
    private StateMachineFactory<OrderState, OrderEvent> stateMachineFactory;

    private StateMachine<OrderState, OrderEvent> stateMachine;

    public void newOrder() {
        initOrderSaga();
        validateOrder();
    }

    private void validateOrder() {
        System.out.println("Validation order");
        stateMachine.sendEvent(Mono.just(
            MessageBuilder.withPayload(OrderEvent.VALIDATE).build()
        )).subscribe(result -> System.out.println(result.getResultType()));
        System.out.println("Final state: ");
    }


    void payOrder() {
        System.out.println("Validation order");
        stateMachine.sendEvent(Mono.just(
            MessageBuilder.withPayload(OrderEvent.PAY).build()
        )).subscribe(result -> System.out.println(result.getResultType()));
        System.out.println("Final state: ");
    }


    void shipOrder() {
        System.out.println("Validation order");
        stateMachine.sendEvent(Mono.just(
            MessageBuilder.withPayload(OrderEvent.SHIP).build()
        )).subscribe(result -> System.out.println(result.getResultType()));
        System.out.println("Final state: ");
    }

    void completeOrder() {
        System.out.println("Validation order");
        stateMachine.sendEvent(Mono.just(
            MessageBuilder.withPayload(OrderEvent.COMPLETE).build()
        )).subscribe(result -> System.out.println(result.getResultType()));
        System.out.println("Final state: ");
        stopOrderSaga();
    }

    private void initOrderSaga() {
        System.out.println("Initializing saga");
        stateMachine = stateMachineFactory.getStateMachine();
        stateMachine.startReactively().subscribe();
        System.out.println("Final state: " + stateMachine.getState().getId());
    }

    private void stopOrderSaga() {
        System.out.println("Stopping saga");
        stateMachine.stopReactively().subscribe();
    }

}
