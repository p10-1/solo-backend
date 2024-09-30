package org.solo.product.scheduler;

import org.solo.product.service.ProductService;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ProductScheduler {
    private final ProductService productService;
    private final TaskScheduler taskScheduler;

    public ProductScheduler(ProductService productService, TaskScheduler taskScheduler) {
        this.productService = productService;
        this.taskScheduler = taskScheduler;
    }

    @PostConstruct
    public void scheduleFetchProducts(){
        System.out.println("scheduling fetch product");
        taskScheduler.scheduleAtFixedRate(() -> {
            productService.fetchDeposit();
            productService.fetchSaving();
            System.out.println("Fetched products at: " + System.currentTimeMillis());
        },  60 * 60 * 1000); // 1시간에 한번 패치
    }
}
