package com.example.band_budget.external.kafka;

import com.example.band_budget.PayMember.PayMember;
import com.example.band_budget.PayMember.event.PayMemberEvent;
import com.example.band_budget.budget.event.BudgetEvent;
import com.example.band_budget.external.JsonUtil;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @Value("${spring.kafka.template.data-topic}")
    private String dataTopic;



    public String sendBudgetEventToKafka(BudgetEvent event){

        ObjectNode node = JsonUtil.toObjectNode(event);
        node.put("type", event.getClass().getSimpleName());
        String message = JsonUtil.toJson(node);

        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(dataTopic, message);

        future.whenComplete((result, ex) -> {
            if(ex != null){
                log.error("Error: " + ex.getMessage());
            } else{
                log.info("Success: " + result.getRecordMetadata());
            }
        });

        return message;
    }

    public String sendPayMemberEventToKafka(PayMemberEvent event){

        ObjectNode node = JsonUtil.toObjectNode(event);
        node.put("type", event.getClass().getSimpleName());
        String message = JsonUtil.toJson(node);

        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(dataTopic, message);

        future.whenComplete((result, ex) -> {
            if(ex != null){
                log.error("Error: " + ex.getMessage());
            } else{
                log.info("Success: " + result.getRecordMetadata());
            }
        });

        return message;
    }
}
