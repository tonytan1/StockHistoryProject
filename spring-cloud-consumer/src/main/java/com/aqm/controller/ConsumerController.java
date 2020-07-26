package com.aqm.controller;

import com.aqm.Entity.ResponseEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController

public class ConsumerController {
    private Logger logger = LoggerFactory.getLogger(ConsumerController.class);
    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/client/delete/{symbol}")
    public ResponseEntity deleteStockHistory(@PathVariable("symbol") String symbol) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "AQM666");// TODO: only for test

        HttpEntity<Object> request = new HttpEntity<>(null, headers);
        return restTemplate.postForObject("http://AQM-STOCKSERVER/aqm/delete/" + symbol, request, ResponseEntity.class);
    }

    @RequestMapping("/client/update")
    public ResponseEntity updateStockHistory(@RequestBody String requestJson) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "AQM666");//TODO: only for test

        HttpEntity<Object> request = new HttpEntity<>(requestJson, headers);
        return restTemplate.postForObject("http://AQM-STOCKSERVER/aqm/update/", request, ResponseEntity.class);
    }

    @RequestMapping("/client/get/{symbol}")
    public ResponseEntity getStockHistory(
        @PathVariable("symbol") String symbol, 
        @RequestParam(value = "start", required = true) String start,
        @RequestParam(value = "end", required = true) String end) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "AQM666");//TODO: only for test
        
        MultiValueMap<String, String> params= new LinkedMultiValueMap<>();
        params.add("start", start);
        params.add("end", end);
        logger.info("START: {} , END:{}", start, end);
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);

        // HttpEntity<Object> request = new HttpEntity<>(obj.toString(), headers);
        return restTemplate.postForObject("http://AQM-STOCKSERVER/aqm/get/" + symbol, requestEntity,
                ResponseEntity.class);
    }

}