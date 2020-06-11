package com.health.dairy.client;

import com.health.food.api.FoodApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/5/6 23:17
 */
@FeignClient("food-service")
public interface DairyClient extends FoodApi {
}
